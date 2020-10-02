package com.findthenotes.findthenotes.gamemodel;

import android.util.Log;

import com.findthenotes.findthenotes.utils.Combinations;
import com.findthenotes.findthenotes.utils.GameMath;
import com.findthenotes.findthenotes.utils.KPermutations;
import com.findthenotes.findthenotes.utils.Permutations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samirtf on 17/06/16.
 */
public class StageGenerator {


    public static final int WHITE_KEYS_PER_SCALE = 7;
    public static final int BLACK_KEYS_PER_SCALE = 5;
    public static final int KEY_TYPE_WHITE = 0;
    public static final int KEY_TYPE_BLACK = 1;
    public static final int KEY_TYPE_BLACK_AND_WHITE = 2;

    public List<StageSpec> generate(String tag, int size, int playableNotesAmount, int keyType, int keysAmount, int scalesAvailable ) throws Exception {
        validadeGeneratorParameters(playableNotesAmount, keyType, keysAmount, scalesAvailable);

        int[] whiteNotes = {Notes._DO, Notes._FA, Notes._LA, Notes._MI, Notes._RE, Notes._SI, Notes._SOL};
        int[] blackNotes = {Notes._DO_SUS, Notes._FA_SUS, Notes._LA_SUS, Notes._RE_SUS, Notes._SOL_SUS};
        int[] blackAndWhiteNotes = {Notes._DO, Notes._DO_SUS, Notes._FA, Notes._FA_SUS, Notes._LA,
                Notes._LA_SUS, Notes._MI, Notes._RE, Notes._RE_SUS, Notes._SI, Notes._SOL, Notes._SOL_SUS};

        int[] inputNotes;
        if(keyType == KEY_TYPE_WHITE) {
            inputNotes = whiteNotes;
        } else if(keyType == KEY_TYPE_BLACK) {
            inputNotes = blackNotes;
        } else {
            inputNotes = blackAndWhiteNotes;
        }

        List<StageSpec> games = new ArrayList<>(size);

        List<int[]> combinations = GameMath.listCombinations(inputNotes, playableNotesAmount);
        int combinationsSize = combinations.size();
        for (int i = 0; i < combinationsSize; i++) {
            StageSpec stageSpec = new StageSpec(false, tag, "NAME", size, playableNotesAmount, keyType, keysAmount, scalesAvailable, combinations.get(i), false, 0);
            games.add(stageSpec);
        }

        return games;
    }


    public List<StageSpec> generateAll(String tag, int size, int playableNotesAmount, int keyType, int keysAmount, int scalesAvailable ) throws Exception {
        validadeGeneratorParameters(playableNotesAmount, keyType, keysAmount, scalesAvailable);

        //int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] test = {1, 2, 3, 4, 5, 6, 7};
        int[] whiteNotes = {Notes._DO, Notes._FA, Notes._LA, Notes._MI, Notes._RE, Notes._SI, Notes._SOL};
        int[] blackNotes = {Notes._DO_SUS, Notes._FA_SUS, Notes._LA_SUS, Notes._RE_SUS, Notes._SOL_SUS};
        int[] blackAndWhiteNotes = {Notes._DO, Notes._DO_SUS, Notes._FA, Notes._FA_SUS, Notes._LA,
                Notes._LA_SUS, Notes._MI, Notes._RE, Notes._RE_SUS, Notes._SI, Notes._SOL, Notes._SOL_SUS};

        int[] inputNotes;
        if(keyType == KEY_TYPE_WHITE) {
            inputNotes = whiteNotes;
        } else if(keyType == KEY_TYPE_BLACK) {
            inputNotes = blackNotes;
        } else if(keyType == KEY_TYPE_BLACK_AND_WHITE) {
            inputNotes = blackAndWhiteNotes;
        } else {
            inputNotes = test;
        }

        List<StageSpec> games = new ArrayList<>();

        // example C(12, 1) = 12
        List<int[]> combinations = GameMath.listCombinations(inputNotes, playableNotesAmount);

        Combinations<int[]> kPermutations = new Combinations<>(combinations, size);
        for (List<int[]> kPermutation : kPermutations) {
            int stageSize = kPermutation.size();
            int stageArraySize = kPermutation.get(0).length;

            //size = stage size
            //playable notes = stage array size

            int stageMapSize = stageSize * stageArraySize;
            int[] stageMap = new int[stageMapSize];
            for(int i = 0; i < stageSize; i++) {
                int[] stage = kPermutation.get(i);

                for(int e = 0; e < stageArraySize; e++) {
                    stageMap[i*(stageArraySize-1) + e] = stage[e];
                }

            }
            StageSpec stageSpec = new StageSpec(false, tag, "NAME", size, playableNotesAmount, keyType, keysAmount, scalesAvailable, stageMap, false, 0);
            games.add(stageSpec);

        }

        System.out.println("games size: " + games.size());
        for(Integer id : games.get(0).getStageNotes()) {
            Log.d("FTA", "cell of stage map: " + id);
        }
        return games;
    }

    public List<StageSpec> generateAll1(String tag, int size, int playableNotesAmount, int keyType, int keysAmount, int scalesAvailable ) throws Exception {
        //validadeGeneratorParameters(playableNotesAmount, keyType, keysAmount, scalesAvailable);

        //int[] test = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] test = {1, 2, 3, 4, 5, 6, 7};
        int[] whiteNotes = {Notes._DO, Notes._FA, Notes._LA, Notes._MI, Notes._RE, Notes._SI, Notes._SOL};
        int[] blackNotes = {Notes._DO_SUS, Notes._FA_SUS, Notes._LA_SUS, Notes._RE_SUS, Notes._SOL_SUS};
        int[] blackAndWhiteNotes = {Notes._DO, Notes._DO_SUS, Notes._FA, Notes._FA_SUS, Notes._LA,
                Notes._LA_SUS, Notes._MI, Notes._RE, Notes._RE_SUS, Notes._SI, Notes._SOL, Notes._SOL_SUS};

        int[] inputNotes;
        if(keyType == KEY_TYPE_WHITE) {
            inputNotes = whiteNotes;
        } else if(keyType == KEY_TYPE_BLACK) {
            inputNotes = blackNotes;
        } else if(keyType == KEY_TYPE_BLACK_AND_WHITE) {
            inputNotes = blackAndWhiteNotes;
        } else {
            inputNotes = test;
        }

        List<StageSpec> games = new ArrayList<>();

        // example C(12, 1) = 12
        List<int[]> combinations = GameMath.listCombinations(inputNotes, playableNotesAmount);

        KPermutations<int[]> kPermutations = new KPermutations<>(combinations, size);
        int kPermutationsCounter = 0;
        for (List<int[]> kPermutation : kPermutations) {
            kPermutationsCounter++;
            int stageSize = kPermutation.size();
            int stageMapSize = size * playableNotesAmount;
            int[] stageMap = new int[stageMapSize];
            int i;
            //System.out.println("stage size: " + stageSize);
            for(i = 0; i < stageSize; i++) {
                int[] stage = kPermutation.get(i);
                for(int e = 0; e < stage.length; e++) {
                    stageMap[i] = stage[e];
                    //System.out.print("STAGE[E] = " + stage[e] + " ");
                }
                //System.out.print(kPermutation.get(x) + " ");
                //System.out.println();
            }
            StageSpec stageSpec = new StageSpec(false, tag, "NAME", size, playableNotesAmount, keyType, keysAmount, scalesAvailable, stageMap, false, 0);
            games.add(stageSpec);
            for(Integer id : stageMap) {
                System.out.println("cell of stage map: " + id);
            }
            //System.out.println("\\");
            //System.out.println(kPermutation);
        }
        //System.out.println("--- kPermutations counter: " + kPermutationsCounter);
//        int combinationsSize = combinations.size();
//        int[] multiNoteCombinations = new int[combinationsSize];
//        for( int idx = 0; idx < multiNoteCombinations.length; idx++) {
//            multiNoteCombinations[idx] = idx;
//        }
//        for (int i = 0; i < combinationsSize; i++) {
//            StageSpec stageSpec = new StageSpec(tag, size, playableNotesAmount, keyType, keysAmount, scalesAvailable, combinations.get(i));
//            games.add(stageSpec);
//        }

        //

        System.out.println("games size: " + games.size());
        return games;
    }

    private void validadeGeneratorParameters(int playNotesAmount, int keyType, int availableKeysAmount, int scalesAvailable) throws Exception {
        if( playNotesAmount <= 0 ) throw new Exception("Invalid play notes amount");
        if( keyType != KEY_TYPE_WHITE && keyType != KEY_TYPE_BLACK &&
                keyType != KEY_TYPE_BLACK_AND_WHITE ) throw new Exception("Invalid key type");
        if( keyType == KEY_TYPE_WHITE && (availableKeysAmount <= 0 ||
                availableKeysAmount > scalesAvailable * WHITE_KEYS_PER_SCALE) ) throw new Exception("Invalid white keys amount");
        if( keyType == KEY_TYPE_BLACK && (availableKeysAmount <= 0 ||
                availableKeysAmount > scalesAvailable * BLACK_KEYS_PER_SCALE) ) throw new Exception("Invalid black keys amount");
        if( keyType == KEY_TYPE_BLACK && (availableKeysAmount <= 0 ||
                availableKeysAmount > scalesAvailable * (WHITE_KEYS_PER_SCALE + BLACK_KEYS_PER_SCALE)) ) throw new Exception("Invalid black and white keys amount");
    }

}
