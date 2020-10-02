package com.findthenotes.findthenotes.sound;

import java.util.List;

/**
 * Created by samirtf on 24/06/16.
 */
public class XSoundPoolManager {

    public interface IXSoundPool {
        List<Integer> getSounds();
        boolean isPlaySound();
        void setPlaySound(boolean isPlaySound);
        void playSound(int resourceId);
        void release();
        void stop();
        void playSounds(int[] resourceIdArray, long[] resourceIdDurationArray, int delay);
        void stopSounds();
        void setIXSoundPoolListener(IXSoundPoolListener ixSoundPoolListenerX);
    }

    private static XSoundPoolManager instance;
    private IXSoundPool ixSoundPool;

    public synchronized static XSoundPoolManager getInstance() {
        return instance;
    }

    public XSoundPoolManager(IXSoundPool ixSoundPool) {
        this.ixSoundPool = ixSoundPool;
    }

    public static void createInstance(IXSoundPool ixSoundPool) {
        if(instance == null) {
            instance = new XSoundPoolManager(ixSoundPool);
        }
    }

    private void checkIxSoundPoolNullity() throws Exception {
        if(ixSoundPool == null)
            throw new Exception("ixSoundPool must be set");
    }

    public void setIXSoundPoolListener(IXSoundPoolListener ixSoundPoolListenerX) throws Exception {
        checkIxSoundPoolNullity();
        ixSoundPool.setIXSoundPoolListener(ixSoundPoolListenerX);
    }

    public List<Integer> getSounds() throws Exception {
        checkIxSoundPoolNullity();
        return ixSoundPool.getSounds();
    }

    public boolean isPlaySound() throws Exception {
        checkIxSoundPoolNullity();
        return ixSoundPool.isPlaySound();
    }

    public void setPlaySound(boolean isPlaySound) throws Exception {
        checkIxSoundPoolNullity();
        ixSoundPool.setPlaySound(isPlaySound);
    }

    public void playSound(int resourceId) throws Exception {
        checkIxSoundPoolNullity();
        ixSoundPool.playSound(resourceId);
    }

    public void release() throws Exception {
        checkIxSoundPoolNullity();
        ixSoundPool.release();
    }

    public void stop() throws Exception {
        checkIxSoundPoolNullity();
        ixSoundPool.stop();
    }

    public void playSounds(int[] resourceIdArray, long[] resourceIdDurationArray, int delay) throws Exception{
        checkIxSoundPoolNullity();
        ixSoundPool.playSounds(resourceIdArray, resourceIdDurationArray, delay);
    }

    public void stopSounds() throws Exception {
        checkIxSoundPoolNullity();
        ixSoundPool.stopSounds();
    }


}
