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

public class SoundPoolManager {
/*
    private static SoundPoolManager instance;
    private SoundPool soundPool;
    private List<Integer> sounds;
    private HashMap<Integer, SoundSampleEntity> hashMap;
    private boolean isPlaySound;

    public synchronized static SoundPoolManager getInstance() {
        return instance;
    }

    public static void CreateInstance() {
        if (instance == null) {
            instance = new SoundPoolManager();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        //soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
        soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);

    }

    public List<Integer> getSounds() {
        return sounds;
    }

    public void setSounds(List<Integer> sounds) {
        this.sounds = sounds;
    }

    public void InitializeSoundPool(Activity activity, final ISoundPoolLoaded callback) throws Exception {
        if (sounds == null || sounds.size() == 0) {
            throw new Exception("Sounds not set");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                SoundSampleEntity entity = getEntity(sampleId);
                if (entity != null) {
                    entity.setLoaded(status == 0);
                }

                if (sampleId == maxSampleId()) {
                    callback.onSuccess();
                }
            }
        });
        int length = sounds.size();
        hashMap = new HashMap<>();
        int index;
        for (index = 0; index < length; index++) {
            hashMap.put(sounds.get(index), new SoundSampleEntity(0, false));
        }
        index = 0;
        for (Map.Entry<Integer, SoundSampleEntity> entry : hashMap.entrySet()) {
            index++;
            entry.getValue().setSampleId(soundPool.load(activity, entry.getKey(), index));
        }
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
                soundPool.play(entity.getSampleId(), .99f, .99f, 1, 0, 1f);
            }
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
        }
    }

    public void stop() {
        if (soundPool != null) {
            for (Map.Entry<Integer, SoundSampleEntity> entry : hashMap.entrySet()) {
                SoundSampleEntity entity = entry.getValue();
                soundPool.stop(entity.getSampleId());
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
    */
}
