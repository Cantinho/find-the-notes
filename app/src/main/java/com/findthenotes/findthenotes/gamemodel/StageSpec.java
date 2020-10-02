package com.findthenotes.findthenotes.gamemodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.findthenotes.findthenotes.model.Stage;
import com.findthenotes.findthenotes.utils.FindTheNotesApplicationUtil;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Random;

import static com.findthenotes.findthenotes.utils.GameMath.shuffleIntArray;

/**
 * Created by samirtf on 17/06/16.
 */
@Table(name = "stagespecs")
public class StageSpec extends Model {

    public static final String ID = "STAGE_SPEC_ID";

    @Column(name = "tag")
    private String tag;

    @Column(name = "name", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String name;

    @Column(name = "size")
    private int size;

    @Column(name = "play_notes_amount")
    private int playNotesAmount;

    @Column(name = "key_type")
    private int keyType;

    @Column(name = "available_keys_amount")
    private int availableKeysAmount;

    @Column(name = "scales_available")
    private int scalesAvailable;

    @Column(name = "stage_notes")
    private String stageNotes;

    @Column(name = "can_be_shuffled")
    private boolean canBeShuffled;

    @Column(name = "shuffle_last_notes")
    private int shuffleLastNotes = 0;

    public static final String TRAINING = "training";

    public StageSpec(){}

    public StageSpec(boolean saveFlag, String tag, String name, int size, int playableNotesAmount, int keyType, int availableKeysAmount, int scalesAvailable, int[] stageNotes, boolean canBeShuffled, int shuffleLastNotes) {
        this.tag = tag; // It refers to the group of stages
        this.name = name;
        this.size = size; //
        this.playNotesAmount = playableNotesAmount; //playable notes
        this.keyType = keyType; // key type
        this.availableKeysAmount = availableKeysAmount; // available keys
        this.scalesAvailable = scalesAvailable;
        this.stageNotes = Arrays.toString(stageNotes);
        this.canBeShuffled = canBeShuffled;
        this.shuffleLastNotes = shuffleLastNotes;

        StageSpec self = new Select()
                .from(StageSpec.class)
                .where("name = ?", name)
                .executeSingle();

        if(self == null && saveFlag) {
            this.save();
        }


    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPlayNotesAmount() {
        return playNotesAmount;
    }

    public void setPlayNotesAmount(int playNotesAmount) {
        this.playNotesAmount = playNotesAmount;
    }

    public int getKeyType() {
        return this.keyType;
    }

    public void setKeyType(int keyType) {
        this.keyType = keyType;
    }

    public int getAvailableKeysAmount() {
        return availableKeysAmount;
    }

    public void setAvailableKeysAmount(int availableKeysAmount) {
        this.availableKeysAmount = availableKeysAmount;
    }

    public int getScalesAvailable() {
        return scalesAvailable;
    }

    public void setScalesAvailable(int scalesAvailable) {
        this.scalesAvailable = scalesAvailable;
    }

    public int[] getStageNotes() {
        return new Gson().fromJson(stageNotes, int[].class);
    }

    public void setStageNotes(int[] stageNotes) {
        this.stageNotes = Arrays.toString(stageNotes);
    }

    public int getStageNotesLength() {
        return new Gson().fromJson(stageNotes, int[].class).length;
    }

    public void setCanBeShuffled(boolean canBeShuffled) {
        this.canBeShuffled = canBeShuffled;
    }

    public boolean isCanBeShuffled() {
        return  canBeShuffled;
    }

    public void shuffle() {
        if(isCanBeShuffled()) {
            int[] stageNotes = getStageNotes();
            shuffleIntArray(stageNotes);
            setStageNotes(stageNotes);
        }
    }

    public void shuffleAnyway() {
        int[] stageNotes = getStageNotes();
        shuffleIntArray(stageNotes);
        setStageNotes(stageNotes);
    }

    public int getShuffleLastNotes() {
        return shuffleLastNotes;
    }

    public void setShuffleLastNotes(int shuffleLastNotes) {
        this.shuffleLastNotes = shuffleLastNotes;
    }


    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(StageSpec.class.getName() + "::[");

        strBuilder.append("tag:{" + tag + "}");
        strBuilder.append("size:{" + size + "}");
        strBuilder.append("playNotesAmount:{" + playNotesAmount + "}");
        strBuilder.append("keyType:{" + keyType + "}");
        strBuilder.append("availableKeysAmount:{" + availableKeysAmount + "}");
        strBuilder.append("scalesAvailable:{" + scalesAvailable + "}");
        strBuilder.append("stageNotes:{");

        int i;
        int stageNotesLength = new Gson().fromJson(stageNotes, int[].class).length;
        for (i = 0; i < stageNotesLength - 1; i++) {
            strBuilder.append(getStageNotes()[i] + " ");
        }
        strBuilder.append(getStageNotes()[i]);

        strBuilder.append("}");

        strBuilder.append("]");

        return strBuilder.toString();
    }

    public void generateLastNotesRandomly() {
        int[] stageNotes = getStageNotes();
        int stageNotesIndex = getStageNotes().length - 1;
        int counter = getShuffleLastNotes();
        Random random = new Random();
        while(counter > 0) {
            int index = random.nextInt(FindTheNotesApplicationUtil.SOUNDS.length);
            stageNotes[stageNotesIndex] = FindTheNotesApplicationUtil.SOUNDS[index];
            stageNotesIndex--;
            counter--;
        }
        setStageNotes(stageNotes);
    }
}
