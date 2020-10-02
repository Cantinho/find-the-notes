package com.findthenotes.findthenotes.gamemodel;

import com.findthenotes.findthenotes.R;

/**
 * Created by samirtf on 13/06/16.
 */
public class Notes {

    public static final int _LA = R.raw.piano_a;
    public static final int _LA_SUS = R.raw.piano_a_sus;
    public static final int _SI = R.raw.piano_b;
    public static final int _DO = R.raw.piano_c;
    public static final int _DO_SUS = R.raw.piano_c_sus;
    public static final int _RE = R.raw.piano_d;
    public static final int _RE_SUS = R.raw.piano_d_sus;
    public static final int _MI = R.raw.piano_e;
    public static final int _FA = R.raw.piano_f;
    public static final int _FA_SUS = R.raw.piano_f_sus;
    public static final int _SOL = R.raw.piano_g;
    public static final int _SOL_SUS = R.raw.piano_g_sus;

    public static String toString(int note) {
        switch (note) {
            case _LA:
                return "A";
            case _LA_SUS:
                return "A#";
            case _SI:
                return "B";
            case _DO:
                return "C";
            case _DO_SUS:
                return "C#";
            case _RE:
                return "D";
            case _RE_SUS:
                return "D#";
            case _MI:
                return "E";
            case _FA:
                return "F";
            case _FA_SUS:
                return "F#";
            case _SOL:
                return "G";
            case _SOL_SUS:
                return "G#";
            default:
                return null;
        }
    }

}
