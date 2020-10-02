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

@Table(name = "stages")
public class Stage extends Model {

    public static final String ID = "STAGE_ID";

    @Column(name = "number")
    private int number;

    @Column(name = "name", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String name;

    @Column(name = "stars")
    private int stars;

    @Column(name = "unlocked")
    private boolean unlocked = false;

    @Column(name = "world", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private World world;

    public Stage (int number, int stars, boolean unlocked, World world) {
        this.number = number;
        String tempName = world.getTag() + number;
        this.name = tempName;
        this.stars = stars;
        this.unlocked = unlocked;
        this.world = world;

        Stage self = new Select()
                .from(Stage.class)
                .where("name = ?", tempName)
                .executeSingle();
        if (self == null) {
            this.save();
        }
//        else {
//            self.number = number;
//            self.name = tempName;
//            self.stars = stars;
//            self.unlocked = unlocked;
//            self.save();
//        }
    }

    public Stage() {
        super();
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public World getWorld() {
        return world;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void updateStars(int stars) {
        this.stars = stars;
        this.save();
    }

    public void unlock() {
        this.unlocked = true;
        this.save();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(Stage.class.getName() + "::[");

        strBuilder.append("number:{" + number + "}");
        strBuilder.append("name:{" + name + "}");
        strBuilder.append("stars:{" + stars + "}");
        strBuilder.append("isUnlocked:{" + unlocked + "}");
        strBuilder.append("world:{" + world + "}");

        strBuilder.append("]");

        return strBuilder.toString();
    }

    public Stage getNextStage() {
        String tempName = world.getTag() + (number + 1);
        Log.i("NEXT NAME", tempName);
        return new Select()
                .from(Stage.class)
                .where("name = ?", tempName)
                .executeSingle();
    }

    public static int getAllStars() {
        int allStars = 0;

        List<Stage> stages =  new Select()
                .from(Stage.class)
                .execute();

        for (Stage stage : stages) {
            allStars += stage.getStars();
        }

        return  allStars;
    }

    public static int getTotalStars() {
        List<Stage> stages =  new Select()
                .from(Stage.class)
                .execute();

        return  (stages.size() * 3);
    }

}
