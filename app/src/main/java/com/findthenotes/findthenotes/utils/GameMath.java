package com.findthenotes.findthenotes.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by samirtf on 17/06/16.
 */
public class GameMath {

    public static List<int[]> listCombinations(int[] input, int k) {
//        int[] input = {10, 20, 30, 40, 50};    // input array
//        int k = 3;                             // sequence length

        List<int[]> subsets = new ArrayList<>();

        int[] indexSet = new int[k];    // here we'll keep indices
        // pointing to elements in input array


        if (k <= input.length) {
            // first index sequence: 0, 1, 2, ...
            for (int i = 0; (indexSet[i] = i) < k - 1; i++);
            subsets.add(getSubset(input, indexSet));
            for(;;) {
                int i;
                // find position of item that can be incremented
                for (i = k - 1; i >= 0 && indexSet[i] == input.length - k + i; i--);
                if (i < 0) {
                    break;
                } else {
                    indexSet[i]++;    // increment this item
                    for (++i; i < k; i++) {    // fill up remaining items
                        indexSet[i] = indexSet[i - 1] + 1;
                    }
                    subsets.add(getSubset(input, indexSet));
                }
            }
        }


        return subsets;
    }

    // generate actual subset by index sequence
    private static int[] getSubset(int[] input, int[] subset) {
        int[] result = new int[subset.length];
        for (int i = 0; i < subset.length; i++)
            result[i] = input[subset[i]];
        return result;
    }

    public static void shuffleIntArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

}
