package com.findthenotes.findthenotes.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by samirtf on 23/06/16.
 */
public class LollipopSoundPool {

    private Activity mActivity;
    private ISoundPoolLoaded callback;
    private Map<Integer, SoundPool> soundPoolMap;
    private List<Boolean> onLoadCompleteListenerFlags;
    private List<Integer> sounds;
    private HashMap<Integer, SoundSampleEntity> hashMap;
    private boolean isPlaySound;


    public LollipopSoundPool(Activity activity, final ISoundPoolLoaded callback, List<Integer> rawAudioIntegers) throws Exception {
        if (rawAudioIntegers == null || rawAudioIntegers.size() == 0) {
            throw new Exception("Sounds not set");
        }
        mActivity = activity;
        this.callback = callback;
        createSoundPoolMap();

        for( Integer raw : rawAudioIntegers ) {
            SoundPool soundPool = createNewSoundPool();
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    SoundSampleEntity entity = getEntity(sampleId);
                    if (entity != null) {
                        entity.setLoaded(status == 0);
                    }

                    if (sampleId == maxSampleId()) {
                        try {
                            doSuccess();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            soundPoolMap.put(raw, soundPool);
            sounds.add(raw);
            onLoadCompleteListenerFlags.add(false);
        }

        int length = sounds.size();
        hashMap = new HashMap<>();
        int index;
        for (index = 0; index < length; index++) {
            hashMap.put(sounds.get(index), new SoundSampleEntity(0, false));
        }
        index = 0;
        for (Map.Entry<Integer, SoundSampleEntity> entry : hashMap.entrySet()) {
            index++;
            entry.getValue().setSampleId(soundPoolMap.get(entry.getKey()).load(mActivity, entry.getKey(), index));
        }

    }

    private void doSuccess() throws Exception {
        if(onLoadCompleteListenerFlags != null) {
            if(onLoadCompleteListenerFlags.size() == 0) {
                callback.onSuccess();
            } else {
                onLoadCompleteListenerFlags.remove(0);
            }
        } else {
            throw new Exception("onLoadCompleteListenerFlags must be initialize correctly.");
        }
    }

    private void createSoundPoolMap() {
        soundPoolMap = new TreeMap<>();
        onLoadCompleteListenerFlags = new ArrayList<>();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected SoundPool createNewSoundPool(){

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

    }

    @SuppressWarnings("deprecation")
    protected SoundPool createOldSoundPool(){
        //soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
        return new SoundPool(1, AudioManager.STREAM_MUSIC, 100);

    }

    public List<Integer> getSounds() {
        return sounds;
    }

    public void setSounds(List<Integer> sounds) {
        this.sounds = sounds;
    }


    private int maxSampleId() {
        int sampleId = 0;
        for (Map.Entry<Integer, SoundSampleEntity> entry : hashMap.entrySet()) {
            SoundSampleEntity entity = entry.getValue();
            sampleId = entity.getSampleId() > sampleId ? entity.getSampleId() : sampleId;
        }
        return sampleId;
    }

    private SoundSampleEntity getEntity(int sampleId) {
        for (Map.Entry<Integer, SoundSampleEntity> entry : hashMap.entrySet()) {
            SoundSampleEntity entity = entry.getValue();
            if (entity.getSampleId() == sampleId) {
                return entity;
            }
        }
        return null;
    }

    public boolean isPlaySound() {
        return isPlaySound;
    }

    public void setPlaySound(boolean isPlaySound) {
        this.isPlaySound = isPlaySound;
    }

    public void playSound(int resourceId) {
        if (isPlaySound()) {
            SoundSampleEntity entity = hashMap.get(resourceId);
            if (entity.getSampleId() > 0 && entity.isLoaded()) {
                soundPoolMap.get(resourceId).play(entity.getSampleId(), .99f, .99f, 1, 0, 1f);
            }
        }
    }

    public void release() {
        for(Integer key : sounds) {
            SoundPool soundPool = soundPoolMap.get(key);
            if (soundPool != null) {
                soundPool.release();
            }
        }
    }

    public void stop() {
        for(Integer key : sounds) {
            SoundPool soundPool = soundPoolMap.get(key);
            if (soundPool != null) {
                for (Map.Entry<Integer, SoundSampleEntity> entry : hashMap.entrySet()) {
                    SoundSampleEntity entity = entry.getValue();
                    soundPool.stop(entity.getSampleId());
                }
            }
        }
    }

    // TODO play all the notes
    public void playSounds(ArrayList<Integer> notes) {

    }

    private class SoundSampleEntity {
        private int sampleId;
        private boolean isLoaded;

        public SoundSampleEntity(int sampleId, boolean isLoaded) {
            this.isLoaded = isLoaded;
            this.sampleId = sampleId;
        }

        public int getSampleId() {
            return sampleId;
        }

        public void setSampleId(int sampleId) {
            this.sampleId = sampleId;
        }

        public boolean isLoaded() {
            return isLoaded;
        }

        public void setLoaded(boolean isLoaded) {
            this.isLoaded = isLoaded;
        }
    }


}
