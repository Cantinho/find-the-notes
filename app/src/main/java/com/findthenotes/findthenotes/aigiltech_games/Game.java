package com.findthenotes.findthenotes.aigiltech_games;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.findthenotes.findthenotes.gamemodel.GameListener;
import com.findthenotes.findthenotes.gamemodel.Notes;
import com.findthenotes.findthenotes.gamemodel.StageSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 03/12/2015.
 */
public class Game {

    enum GameState {
        IDLE,
        PLAYING,
        STOPPED,
        PAUSED
    }

    private GameState gameState = GameState.IDLE;
    private GameTimer timer;
    private Handler handler;
    private int lastUsedConstructor = 0;
    private long interval;
    private long duration;
    private GameManager.GameMode gameMode;
    private StageSpec stageSpec;
    private GameListener gameListener;
    private int shakes;

    private int hearts = 2;
    private int currentRound;
    private int currentCell;
    private int[] cells;

    public void initGame() {
        hearts = 2;
        currentRound = 0;
        currentCell = 0;
        if(stageSpec.isCanBeShuffled()) {

        } else {

        }
        cells = stageSpec.getStageNotes();
        System.out.println(stageSpec);
    }

    private void goToNextRound() {
        currentRound++;
        currentCell = 0;
        //notify next round transition
        if(gameListener != null) {
            gameListener.onNextRound();
        }

    }

    private void goToNextCell() {
        currentCell++;
        if(currentCell >= getTotalCellByRound()) {
            System.out.println("currentCell >= getTotalCellByRound: " + currentCell);
            goToNextRound();
        }

    }

    public int getCurrentRound() {
        return currentRound;
    }

    private int getCurrentCell() {
        return currentCell;
    }

    private void resetCurrentCell() {
        currentCell = 0;
    }

    public int getTotalRounds() {
        return stageSpec.getSize();
    }

    private int getTotalCellByRound() {
        return stageSpec.getPlayNotesAmount();
    }

    private boolean processNote(final int note) {
        System.out.println("PROCESS NOTE");
        System.out.println("---- int note: " + note);
        System.out.println("---- index: " + (getCurrentRound() * getTotalCellByRound() + getCurrentCell()) );
        System.out.println("---- cell note: " + cells[getCurrentRound() * getTotalCellByRound() + getCurrentCell()]);
        System.out.println("---- current round: " + getCurrentRound());
        System.out.println("---- current cell: " + getCurrentCell());
        System.out.println("---- current total cells by round: " + getTotalCellByRound());
        System.out.println("---- current total rounds: " + getTotalRounds());

        return (note == cells[getCurrentRound() * getTotalCellByRound() + getCurrentCell()]);
    }

    private int onWrongAnswer() {
        if (!StageSpec.TRAINING.equals(stageSpec.getTag())) {
            hearts--;
        }
        resetCurrentCell();
        if(hearts == -1) {
            gameOver();
            //notify game over
            if(gameListener != null) {
                gameListener.onGameOver();
            }
        }
        return hearts;
    }

    public void answerQuestion(int note) {
        if(getGameState() == GameState.PLAYING && null != timer) {
            boolean accepted = processNote(note);
            if(accepted) {
                if(gameListener != null) {
                    gameListener.onAnswerCorrect(note, getCurrentCell());
                }
                goToNextCell();
                if(currentRound >= getTotalRounds()) {
                    System.out.println("VENCEU!!!");
                    // venceu
                    if(gameListener != null) {
                        gameListener.onWin();
                    }
                }
            } else {
                int tries = onWrongAnswer();
                //notify wrong answer
                if(gameListener != null) {
                    gameListener.onAnswerWrong(tries);
                }
            }

            Log.d("Game", "answerQuestion");
        }
    }

    public List<String> getGameString() {
        List<String> string = new ArrayList<>();

        Log.e("FTN DEBUG","cells length: " + cells.length);

        for (Integer i: cells) {
            Log.e("FTN DEBUG","" + i);
            Log.e("FTN DEBUG","DO: " + Notes._DO);
            Log.e("FTN DEBUG","DO#: " + Notes._DO_SUS);
            Log.e("FTN DEBUG","RE: " + Notes._RE);
            Log.e("FTN DEBUG","RE#: " + Notes._RE_SUS);
            Log.e("FTN DEBUG","MI: " + Notes._MI);
            Log.e("FTN DEBUG","FA: " + Notes._FA);
            Log.e("FTN DEBUG","FA#: " + Notes._FA_SUS);
            Log.e("FTN DEBUG","SOL: " + Notes._SOL);
            Log.e("FTN DEBUG","SOL#: " + Notes._SOL_SUS);
            Log.e("FTN DEBUG","LA: " + Notes._LA);
            Log.e("FTN DEBUG","LA#: " + Notes._LA_SUS);
            Log.e("FTN DEBUG","SI: " + Notes._SI);
            System.out.println("FTN DEBUG new line");

            if (i == Notes._DO) {
                string.add("C");
            } else if (i == Notes._DO_SUS){
                string.add("C#");
            } else if (i == Notes._RE){
                string.add("D");
            } else if (i == Notes._RE_SUS){
                string.add("D#");
            } else if (i == Notes._MI ){
                string.add("E");
            } else if (i == Notes._FA ){
                string.add("F");
            } else if (i == Notes._FA_SUS ){
                string.add("F#");
            } else if (i == Notes._SOL ){
                string.add("G");
            } else if (i == Notes._SOL_SUS ){
                string.add("G#");
            } else if (i == Notes._LA ){
                string.add("A");
            } else if (i == Notes._LA_SUS ){
                string.add("A#");
            } else if (i == Notes._SI ){
                string.add("B");
            } else {
                System.out.println("FTN DEBUG NOTA: " + i);
            }
        }

        return string;
    }

//    public void answerQuestion(Notes note) {
//        if(getGameState() == GameState.PLAYING && null != timer) {
//            Thread increment15Thread = new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    timer.incrementDuration(15000);
//                }
//            };
//            increment15Thread.start();
//            Log.d("Game", "answerQuestion");
//        }
//    }

    public Game(StageSpec stageSpec, GameListener gameListener) {
        timer = new GameTimer();
        lastUsedConstructor = 0;
        shakes = 0;
        gameMode = GameManager.GameMode.FREESTYLE;
        this.stageSpec = stageSpec;
        this.gameListener = gameListener;
    }

    public Game(GameManager.GameMode gameMode, StageSpec stageSpec, GameListener gameListener) {
        timer = new GameTimer();
        lastUsedConstructor = 0;
        shakes = 0;
        this.gameMode = gameMode;
        this.stageSpec = stageSpec;
        this.gameListener = gameListener;
        initGame();
    }

    public Game(long interval, long duration) {
        timer = new GameTimer(interval, duration);
        lastUsedConstructor = 1;
        shakes = 0;
    }

    public Game(long interval, long duration, GameManager.GameMode gameMode, StageSpec stageSpec, GameListener gameListener) {
        this.gameMode = gameMode;
        if (this.gameMode == GameManager.GameMode.FREESTYLE) {
            timer = new GameTimer();
            lastUsedConstructor = 0;
        } else {
            timer = new GameTimer(interval, duration);
            lastUsedConstructor = 1;
        }
        shakes = 0;
        this.stageSpec = stageSpec;
        this.gameListener = gameListener;
    }

    public void setGameTimerListener(GameTimerListener gameTimerListener) {
        timer.setGameTimerListener(gameTimerListener);
    }

    public void setTextViewCallBack(TextView textViewCallBack, Context context) {
        timer.setTextViewCallBack(textViewCallBack, context);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void start() {
        setGameState(GameState.PLAYING);
        timer.start();
        shakes = 0;
    }

    public void pause() {
        setGameState(GameState.PAUSED);
        timer.pause();
    }

    public void resume() {
        setGameState(GameState.PLAYING);
        timer.resume();
    }

    private void gameOver() {
        setGameState(GameState.STOPPED);
        timer.pause();
    }

    public void stop() {
        setGameState(GameState.STOPPED);
        timer.pause();
        System.out.println("Game Stopped - Time elapsed:" + timer.getElapsedTime() + " shakes:" + shakes);
        shakes = 0;
    }


    public void reset() {
        setGameState(GameState.IDLE);
        timer.cancel();
        if (lastUsedConstructor == 0) {
            timer = new GameTimer();
            initGame();
        } else if (lastUsedConstructor == 1) {
            timer = new GameTimer(interval, duration);
            initGame();
        } else {
            timer = new GameTimer();
            initGame();
        }
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    public void consumeBonus(BonusType bonusType) {
        switch (bonusType) {
            case MORE_5_SECONDS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Thread increment15Thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            timer.incrementDuration(15000);
                        }
                    };
                    increment15Thread.start();
                    Log.d("Game", "consumeBonus - incrementDuration = 15 seconds");
                }
                break;
            case MORE_10_SECONDS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Thread increment15Thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            timer.incrementDuration(30000);
                        }
                    };
                    increment15Thread.start();
                    Log.d("Game", "consumeBonus - incrementDuration = 30 seconds");
                }
                break;
            case MORE_15_SECONDS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Thread increment15Thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            timer.incrementDuration(60000);
                        }
                    };
                    increment15Thread.start();
                    Log.d("Game", "consumeBonus - incrementDuration = 60 seconds");
                }
                break;
            case DOUBLE_SPERM_SCORE_FOR_10_SECONDS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Log.d("Game", "");
                }
                break;
            case TRIPLICATE_SPERM_SCORE_FOR_10_SECONDS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Log.d("Game", "");
                }
                break;
            case ADD_03_PERCENT_TO_SPERM_BALLS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Log.d("Game", "");
                }
                break;
            case ADD_05_PERCENT_TO_SPERM_BALLS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Log.d("Game", "");
                }
                break;
            case ADD_10_PERCENT_TO_SPERM_BALLS:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Log.d("Game", "");
                }
                break;
            case DOUBLE_PERCENTAGE_OF_CUMSHOT_ON_CONSUME:
                if(getGameState() == GameState.PLAYING && null != timer) {
                    Log.d("Game", "");
                }
                break;
        }
    }

}

