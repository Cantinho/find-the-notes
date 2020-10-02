package com.findthenotes.findthenotes.sound;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.SystemClock;

import android.util.Log;
import com.findthenotes.findthenotes.utils.ISoundPoolLoaded;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XGenericSoundPool implements XSoundPoolManager.IXSoundPool {
    
    private SoundPool soundPool;
    private List<Integer> sounds;
    private HashMap<Integer, SoundSampleEntity> hashMap;
    private boolean isPlaySound;
    private IXSoundPoolListener ixSoundPoolListenerX;


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

    public XGenericSoundPool(Context context, final ISoundPoolLoaded callback, List<Integer> sounds) throws Exception {
        if (sounds == null || sounds.size() == 0) {
            throw new Exception("Sounds not set");
        }
        this.sounds = sounds;

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
                    try {
                        callback.onSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            entry.getValue().setSampleId(soundPool.load(context, entry.getKey(), index));
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

    private static int sDelay;
    private Thread playSoundThreads;
    private static long sCurrentSoundDuration = 0;
    private static int sResourceIdArrayCounter = 0;
    private static boolean soundExit = true;
    @Override
    public void playSounds(final int[] resourceIdArray, final long[] resourceIdDurationArray, final int delay) {
        if( playSoundThreads != null ) {
            // release before start sounds
            soundExit = false;
            try {
                playSoundThreads.interrupt();
            } catch (Exception e) {
                // exception expected
            }
            playSoundThreads = null;
        }
        sCurrentSoundDuration = 0;
        sResourceIdArrayCounter = 0;
        sDelay = delay;
        soundExit = true;
        playSoundThreads = new Thread(new Runnable() {
            @Override
            public void run() {
                while(sResourceIdArrayCounter < resourceIdArray.length && soundExit) {
                    playSound(resourceIdArray[sResourceIdArrayCounter]);
                    sCurrentSoundDuration = SystemClock.currentThreadTimeMillis() + resourceIdDurationArray[sResourceIdArrayCounter] + sDelay;

                    while(SystemClock.currentThreadTimeMillis() < sCurrentSoundDuration){
                        if(!soundExit) break;
                    }

                    sResourceIdArrayCounter++;
                }
                if(ixSoundPoolListenerX != null) {
                    ixSoundPoolListenerX.onEndSound();
                }
            }
        });
        playSoundThreads.start();
    }

    @Override
    public void stopSounds() {
        soundExit = false;
        stop();
        if(ixSoundPoolListenerX != null) {
            ixSoundPoolListenerX.onEndSound();
        }
    }

    @Override
    public void setIXSoundPoolListener(IXSoundPoolListener ixSoundPoolListenerX) {
        this.ixSoundPoolListenerX = ixSoundPoolListenerX;
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
