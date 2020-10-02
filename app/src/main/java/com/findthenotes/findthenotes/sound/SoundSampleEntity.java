package com.findthenotes.findthenotes.sound;

/**
 * Created by samirtf on 24/06/16.
 */
public class SoundSampleEntity implements Comparable<SoundSampleEntity> {
    private int soundId = 0;
    private int sampleId = 0;
    private boolean isLoaded = false;

    public SoundSampleEntity(int soundId, int sampleId, boolean isLoaded) {
        this.soundId = soundId;
        this.isLoaded = isLoaded;
        this.sampleId = sampleId;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
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

    @Override
    public boolean equals(Object o) {
        SoundSampleEntity soundSampleEntity = (SoundSampleEntity) o;
        return this.getSoundId() == soundSampleEntity.getSoundId();
    }

    @Override
    public int compareTo(SoundSampleEntity another) {
        return getSoundId() - another.getSoundId();
    }
}
