package com.findthenotes.findthenotes.model;

import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by samirtf on 08/06/16.
 */

@Table(name = "worlds")
public class World extends Model {

    public static final String ID = "WORLD_ID";
    public static final String TAG = "WORLD_TAG";
    public static final String TRAINING = "TR_";
    public static final String EASILY = "E0_";
    public static final String EASY_ONE = "E1_";
    public static final String EASY_TWO = "E2_";

    public static final String MEDIUM_ONE = "M1_";
    public static final String MEDIUM_TWO = "M2_";

    public static final String HARD_ONE = "H1_";
    public static final String HARD_TWO = "H2_";

    public static final String EXPERT = "EXP_";
    public static final String EXTREME = "EXT_";

    public static final String ABSOLUTE = "ABS_";

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private Level level;

    @Column(name = "unlocked")
    private boolean unlocked = false;

    @Column(name = "tag", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String tag;

    public World() {
        super();
    }

    public World(String name, String description, Level level, boolean unlocked, String tag) {
        super();
        this.name = name;
        this.description = description;
        this.level = level;
        this.unlocked = unlocked;
        this.tag = tag;

        World self = new Select()
                .from(World.class)
                .where("tag = ?", tag)
                .executeSingle();
        if (self == null) {
            this.save();
        } else {
            self.name = name;
            self.description = description;
            self.level = level;
            self.unlocked = unlocked;
            self.tag = tag;
            self.save();
        }
    }

    public World(String name, String description, Level level, String tag) {
        this(name, description, level, false, tag);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Level getLevel() {
        return level;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Stage> listStages() {
        return getMany(Stage.class, "World");
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(World.class.getName() + "::[");

        strBuilder.append("name:{" + name + "}");
        strBuilder.append("description:{" + description + "}");
        strBuilder.append("level:{" + level + "}");
        strBuilder.append("isUnlocked:{" + unlocked + "}");

        strBuilder.append("]");

        return strBuilder.toString();
    }

    public int getPointMultiplier() {
        switch (tag) {
            case EASILY:
                return 1;
            case EASY_ONE:
                return 1;
            case EASY_TWO:
                return 2;
            case MEDIUM_ONE:
                return 3;
            case MEDIUM_TWO:
                return 4;
            case HARD_ONE:
                return 5;
            case HARD_TWO:
                return 6;
            case EXPERT:
                return 7;
            case EXTREME:
                return 8;
            case ABSOLUTE:
                return 9;
            default:
                return 0;
        }
    }
}
