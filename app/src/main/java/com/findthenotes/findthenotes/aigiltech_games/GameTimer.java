package com.findthenotes.findthenotes.aigiltech_games;


import android.content.Context;
import android.widget.TextView;

/**
 * Created by samir on 03/12/2015.
 */
public class GameTimer extends Timer {

    private TextView textViewCallBack;
    private Context context;
    private GameTimerListener gameTimerListener;

    public GameTimer() {
        super();
    }

    public GameTimer(long interval, long duration){
        super(interval, duration);
    }

    public void setGameTimerListener(final GameTimerListener gameTimerListener) {
        this.gameTimerListener = gameTimerListener;
    }

    public void setTextViewCallBack(final TextView textViewCallBack, Context context) {
        this.textViewCallBack = textViewCallBack;
        this.context = context;
    }

    @Override
    protected void onTick() {
        //System.out.println("onTick called!");
        if(null != textViewCallBack) {
//            try {
//                ((Activity) context).runOnUiThread(new Thread(new Runnable() {
//                    public void run() {
//                        textViewCallBack.setText(String.valueOf((int)(getRemainingTime()/1000)));
//                    }
//                }));
//            } catch (Exception e) {
//
//            }

        }

        if(null != gameTimerListener) {
            Object[] vector = {getRemainingTime()};
            gameTimerListener.onTick(vector);
        }
    }


    @Override
    protected void onFinish() {
        System.out.println("onFinish called!");
        if(null != gameTimerListener) {
            Object[] vector = {getRemainingTime()};
            gameTimerListener.onFinish(vector);
        }
    }
}

