package com.findthenotes.findthenotes.sound;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.findthenotes.findthenotes.utils.ISoundPoolLoaded;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by samirtf on 24/06/16.
 */
public class XLollipopSoundPool implements XSoundPoolManager.IXSoundPool {

    private Map<Integer, SoundPool> soundPoolMap;
    private Map<Integer, SoundSampleEntity> loadedCallbackMap;
    private ISoundPoolLoaded callback;
    private boolean isPlaySound;
    private IXSoundPoolListener ixSoundPoolListenerX;

    public XLollipopSoundPool(Context context, final ISoundPoolLoaded callback, List<Integer> sounds) throws Exception {
        if(context == null) throw new Exception("Context not set");
        if(callback == null) throw new Exception("Callback not set");
        if(sounds == null || sounds.size() == 0) throw new Exception("Sounds not set");

        soundPoolMap = new HashMap<>();
        loadedCallbackMap = new HashMap<>();
        this.callback = callback;

        int priority = 1;
        for(final Integer sound : sounds) {
            SoundPool soundPool;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                soundPool = createNewSoundPool();
            } else {
                soundPool = createOldSoundPool();
            }

            soundPoolMap.put(sound, soundPool);

            final int soundID = soundPool.load(context, sound, priority);

            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    try {
                        Log.d("XLSP", "onLoadComplete - sound: " + sound);
                        loadedCallbackMap.put(sound, new SoundSampleEntity(sound, soundID, status == 0));
                        doLoadComplete(sound, soundID, status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Log.d("STF", "sound: " + sound);
            Log.d("STF", "soundID: " + soundID);
            priority++;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected SoundPool createNewSoundPool(){

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        return new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

    }

    @SuppressWarnings("deprecation")
    protected SoundPool createOldSoundPool(){
        return new SoundPool(16, AudioManager.STREAM_RING, 100);

    }

    public synchronized void doLoadComplete(int sound, int sampleId, int status) throws Exception {
        if(loadedCallbackMap != null) {
            System.out.println("XL - doLoadComplete - size: " + loadedCallbackMap.size() + " _ sound: " + sound + " _ sampleId: " + sampleId + " _ status: " + status);
            SoundSampleEntity soundSampleEntity = loadedCallbackMap.get(sound);
            soundSampleEntity.setLoaded(true);
            loadedCallbackMap.put(sound, soundSampleEntity);
        }
        Log.d("XLSP", "sampleID: " + sampleId);
        Iterator<Map.Entry<Integer, SoundSampleEntity>> entrySetIterator = loadedCallbackMap.entrySet().iterator();
        while(entrySetIterator.hasNext()) {
            if(entrySetIterator.next().getValue().isLoaded() == false) return;
        }
        Log.d("XLSP", "callback.onSuccess");
        callback.onSuccess();
    }

    public List<Integer> getSounds() {
        List<Integer> sounds = new ArrayList<>();
        Iterator<Integer> iterator = soundPoolMap.keySet().iterator();
        while(iterator.hasNext()) {
            sounds.add(iterator.next());
        }
        return sounds;
    }

    public boolean isPlaySound() {
        return isPlaySound;
    }

    public void setPlaySound(boolean isPlaySound) {
        this.isPlaySound = isPlaySound;
    }

    public void playSound(int resourceId) {
        if(isPlaySound()) {
            Log.d("XLSP - playSound", "isPlaySound == true");
            SoundSampleEntity soundSampleEntity = loadedCallbackMap.get(resourceId);
            if(soundSampleEntity.getSampleId() > 0 && soundSampleEntity.isLoaded()) {
                soundPoolMap.get(resourceId).play(soundSampleEntity.getSampleId(), .99f, .99f, 1, 0, 1f);
                Log.d("XLSP - playSound", " (getSampleId() > 0 && isLoaded) == TRUE");
                Log.d("XLSP - playSound", " sound: " + soundSampleEntity.getSoundId() +
                    " _ sampleId: " + soundSampleEntity.getSampleId() + " _ isLoaded: " + soundSampleEntity.isLoaded());
            } else {
                Log.d("XLSP - playSound", "( getSampleId() > 0 && isLoaded ) == FALSE");
            }
        } else {
            Log.d("XLSP - playSound", "isPlaySound == false");
        }

    }

    public void release() {
        Set<Map.Entry<Integer, SoundPool>> soundPoolEntrySet = soundPoolMap.entrySet();
        Iterator<Map.Entry<Integer, SoundPool>> soundPoolEntrySetIterator = soundPoolEntrySet.iterator();
        while(soundPoolEntrySetIterator.hasNext()) {
            SoundPool soundPool = soundPoolEntrySetIterator.next().getValue();
            if(soundPool != null) soundPool.release();
        }
        if(soundPoolMap != null) {
            soundPoolMap.clear();
            soundPoolMap = null;
        }
        if(loadedCallbackMap != null) {
            loadedCallbackMap.clear();
            loadedCallbackMap = null;
        }
    }

    public void stop() {
        Set<Map.Entry<Integer, SoundPool>> soundPoolEntrySet = soundPoolMap.entrySet();
        Iterator<Map.Entry<Integer, SoundPool>> soundPoolEntrySetIterator = soundPoolEntrySet.iterator();
        while(soundPoolEntrySetIterator.hasNext()) {
            Map.Entry<Integer, SoundPool> soundPoolEntry = soundPoolEntrySetIterator.next();
            SoundPool soundPool = soundPoolEntry.getValue();
            if(soundPool != null) soundPool.stop(soundPoolEntry.getKey());
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
                while(sResourceIdArrayCounter < resourceIdDurationArray.length && soundExit) {
                    playSound(resourceIdArray[sResourceIdArrayCounter]);
                    sCurrentSoundDuration = SystemClock.currentThreadTimeMillis() + resourceIdDurationArray[sResourceIdArrayCounter] + sDelay;
                    while(SystemClock.currentThreadTimeMillis() < sCurrentSoundDuration){
                        if(soundExit == false) break;
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


}
