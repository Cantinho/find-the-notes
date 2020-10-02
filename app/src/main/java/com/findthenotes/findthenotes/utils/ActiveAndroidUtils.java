package com.findthenotes.findthenotes.utils;

import android.content.Context;
import android.util.Log;

import com.activeandroid.query.Select;
import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.gamemodel.Notes;
import com.findthenotes.findthenotes.gamemodel.StageGenerator;
import com.findthenotes.findthenotes.gamemodel.StageSpec;
import com.findthenotes.findthenotes.model.Level;
import com.findthenotes.findthenotes.model.Stage;
import com.findthenotes.findthenotes.model.World;

import java.util.List;

/**
 * Created by samirtf on 08/06/16.
 */
public class ActiveAndroidUtils {

    public static final int TOTAL_STAGES_BY_WORLD = 24;

    public static void initializeDatabase(Context context) {

        // EASY WORLDS
        World trainingWorld = new World(context.getResources().getString(R.string.training_world),
                "TrainingWorld - Description",
                Level.TRAINING,
                true, World.TRAINING);


        // EASILY WORLD
        World easilyWorld = new World(context.getResources().getString(R.string.easy_world_one),
                "EasilyWorld - Description",
                Level.EASILY,
                true, World.EASILY);


        // / EASY WORLDS
        World easyWorldOne = new World(context.getResources().getString(R.string.easy_world_one),
                "EasyWorldOne - Description",
                Level.EASY,
                true, World.EASY_ONE);

//        easyWorldOne.save();

        World easyWorldTwo = new World(context.getResources().getString(R.string.easy_world_two),
                "EasyWorldTwo - Description",
                Level.EASY,
                false, World.EASY_TWO);

//        easyWorldTwo.save();


        // MEDIUM WORLDS
        World mediumWorldOne = new World(context.getResources().getString(R.string.medium_world_one),
                "MediumWorldOne - Description",
                Level.MEDIUM,
                true, World.MEDIUM_ONE);

//        mediumWorldOne.save();
        World mediumWorldTwo = new World(context.getResources().getString(R.string.medium_world_two),
                "MediumWorldTwo - Description",
                Level.MEDIUM,
                false, World.MEDIUM_TWO);

//        mediumWorldTwo.save();

        // HARD WORLDS
        World hardWorldOne = new World(context.getResources().getString(R.string.hard_world_one),
                "HardWorldOne - Description",
                Level.HARD,
                true, World.HARD_ONE);

//        hardWorldOne.save();

        World hardWorldTwo = new World(context.getResources().getString(R.string.hard_world_two),
                "HardWorldTwo - Description",
                Level.HARD,
                false, World.HARD_TWO);

//        hardWorldTwo.save();


        // EXPERT WORLDS
        World expertWorld = new World(context.getResources().getString(R.string.expert_world),
                "ExpertWorldOne - Description",
                Level.EXPERT,
                true, World.EXPERT);

//        expertWorldOne.save();

        World extremeWorld = new World(context.getResources().getString(R.string.extreme_world),
                "ExtremeWorld - Description",
                Level.EXTREME,
                false, World.EXTREME);

        World absoluteWorld = new World(context.getResources().getString(R.string.absolute_world),
                "AbsoluteWorld - Description",
                Level.ABSOLUTE,
                false, World.ABSOLUTE);

//        expertWorldTwo.save();

        World[] worlds = {
                easilyWorld, easyWorldOne, easyWorldTwo,
                mediumWorldOne, mediumWorldTwo,
                hardWorldOne, hardWorldTwo,
                expertWorld, extremeWorld, absoluteWorld
        };



        for (World world : worlds) {
            for (int i = 1; i < TOTAL_STAGES_BY_WORLD + 1; i++) {
//                int number, int stars, boolean unlocked, World world
                if (i == 1) {
                    Stage stage = new Stage(i, 0, true, world);
                } else {
                    Stage stage = new Stage(i, 0, false, world);
                }
            }
        }

        List<Stage> queryResults = new Select().from(Stage.class).execute();
        Log.d("ActiveAndroidUtils", queryResults.toString());
    }

    public static List<Stage> listStages() {
        return new Select()
                .from(Stage.class)
                .execute();
    }

    public static List<World> listWorlds() {
        return new Select()
                .from(World.class)
                .execute();
    }

    public static List<Stage> getStages(World world) {
        return new Select()
                .from(Stage.class)
                .where("world = ?", world.getId())
                .execute();
    }

    public static World getWorld(long id) {
        return new Select()
                .from(World.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static Stage getStage(long id) {
        return new Select()
                .from(Stage.class)
                .where("id = ?", id)
                .executeSingle();
    }

    public static StageSpec getStageSpec(String name) {
        return new Select()
                .from(StageSpec.class)
                .where("name = ?", name)
                .executeSingle();
    }

    public static void generateWorldEasilyStageSpecs() {

        int[][] easilyWorld = new int[][]{
                {Notes._DO, Notes._RE},
                {Notes._SI, Notes._RE},
                {Notes._DO, Notes._RE},
                {Notes._SI, Notes._RE},
                {Notes._DO_SUS, Notes._RE},
                {Notes._LA_SUS, Notes._RE},
                {Notes._DO_SUS, Notes._RE},
                {Notes._LA_SUS, Notes._RE},
                {Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE},
                {Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE},
                {Notes._RE_SUS, Notes._RE},
                {Notes._SOL_SUS, Notes._RE},
                {Notes._RE_SUS, Notes._RE},
                {Notes._SOL_SUS, Notes._RE},
                {Notes._MI, Notes._RE},
                {Notes._SOL, Notes._RE},
                {Notes._MI, Notes._RE},
                {Notes._SOL, Notes._RE},
                {Notes._FA, Notes._RE},
                {Notes._FA, Notes._RE},
                {Notes._FA_SUS, Notes._RE},
                {Notes._FA_SUS, Notes._RE}

        };


        for( int i = 0; i < easilyWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.EASILY, World.EASILY + i , 2, 1, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, easilyWorld[i], true, 1);
        }

    }

    public static void generateWorldEasyOneStageSpecs() {

        int[][] easyOneWorld = new int[][]{
                {Notes._DO, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE}
        };


        for( int i = 0; i < easyOneWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.EASY_ONE, World.EASY_ONE + i , 3, 1, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, easyOneWorld[i], true, 2);
        }

    }


    public static void generateWorldEasyTwoStageSpecs() {

        // It can not be shuffled.
        int[][] easyTwoWorld = new int[][]{
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}
        };


        for( int i = 0; i < easyTwoWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.EASY_TWO, World.EASY_TWO + i , 3, 2, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, easyTwoWorld[i], true, 5);
        }

    }

    public static void generateWorldMediumOneStageSpecs() {

        int[][] mediumOneWorld = new int[][]{
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}
        };


        for( int i = 0; i < mediumOneWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.MEDIUM_ONE, World.MEDIUM_ONE + i , 3, 3, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, mediumOneWorld[i], true, 8);
        }

    }


    public static void generateWorldMediumTwoStageSpecs() {

        // Black keys.
        int[][] mediumTwoWorld = new int[][]{
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}

        };


        for( int i = 0; i < mediumTwoWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.MEDIUM_TWO, World.MEDIUM_TWO + i , 3, 4, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, mediumTwoWorld[i], true, 11);
        }

    }


    public static void generateWorldHardOneStageSpecs() {

        // all notes 3 x 1
        int[][] hardOneWorld = new int[][]{
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}
        };


        for( int i = 0; i < hardOneWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.HARD_ONE, World.HARD_ONE + i , 3, 5, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, hardOneWorld[i], true, 14);
        }

    }



    public static void generateWorldHardTwoStageSpecs() {


        int[][] hardTwoWorld = new int[][]{
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}
        };


        for( int i = 0; i < hardTwoWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.HARD_TWO, World.HARD_TWO + i , 3, 6, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, hardTwoWorld[i], true, 17);
        }

    }


    public static void generateWorldExpertOneStageSpecs() {

        int[][] expertOneWorld = new int[][] {
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}
        };


        for( int i = 0; i < expertOneWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.EXPERT, World.EXPERT + i , 3, 7, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, expertOneWorld[i], true, 20);
        }

    }


    public static void generateWorldExpertTwoStageSpecs() {

        int[][] expertTwoWorld = new int[][] {
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}

        };


        for( int i = 0; i < expertTwoWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.EXTREME, World.EXTREME + i , 4, 7, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, expertTwoWorld[i], true, 27);
        }

    }


    public static void generateWorldAbsoluteStageSpecs() {

        // 4x4. It can not be shuffled
        int[][] absoluteWorld = {

                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._DO_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._RE_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._MI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._FA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SOL_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._LA_SUS, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE},
                {Notes._SI, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE, Notes._RE}
        };


        for( int i = 0; i < absoluteWorld.length; i++ ) {
            StageSpec stageSpec = new StageSpec(true, World.ABSOLUTE, World.ABSOLUTE + i , 4, 8, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, absoluteWorld[i], true, 31);
        }

    }


    public static void generateStageSpecs() {

        StageSpec stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.EASILY + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldEasilyStageSpecs();
        }


        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.EASY_ONE + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldEasyOneStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.EASY_TWO + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldEasyTwoStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.MEDIUM_ONE + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldMediumOneStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.MEDIUM_TWO + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldMediumTwoStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.HARD_ONE + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldHardOneStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.HARD_TWO + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldHardTwoStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.EXPERT + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldExpertOneStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.EXTREME + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldExpertTwoStageSpecs();
        }

        stageSpec = new Select()
                .from(StageSpec.class)
                .where("name = ?", World.ABSOLUTE + 2)
                .executeSingle();

        if(stageSpec == null) {
            generateWorldAbsoluteStageSpecs();
        }

        FindTheNotesApplicationUtil.getInstance().setDatabaseLoaded(true);
        //public StageSpec(boolean saveFlag, String tag, String name, int size, int playableNotesAmount, int keyType, int availableKeysAmount, int scalesAvailable, int[] stageNotes)
    }


}
