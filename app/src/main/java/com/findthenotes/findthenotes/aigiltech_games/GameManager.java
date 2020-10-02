package com.findthenotes.findthenotes.aigiltech_games;


import android.content.Context;
import android.widget.TextView;

import com.findthenotes.findthenotes.gamemodel.GameListener;
import com.findthenotes.findthenotes.gamemodel.Notes;
import com.findthenotes.findthenotes.gamemodel.StageSpec;

import java.util.List;

/**
 * Created by samir on 26/12/2015.
 */
public class GameManager {

    public void setCallBack(TextView textViewCallBack, Context context) {
        //game.setTextViewCallBack(textViewCallBack, context);
    }

    public void setGameTimerListener(GameTimerListener gameTimerListener) {
        game.setGameTimerListener(gameTimerListener);
    }

    public void setGameState(Game.GameState gameState) {
        game.setGameState(gameState);
    }

    enum ResultStatusGame {
        SUCCESS,
        FORCE_SUCCESS,
        INTERRUPT,
        STOPPED,
        PAUSED
    }

    enum GameMode {
        DAILY_TRAINING,
        FREESTYLE,
        INFINITY_CUMSHOT
    }

    enum DailyTrainingTime {
        S15,
        S30,
        S60
    }

    private static GameManager instance = null;
    private static Object mutex = new Object();

    private Context context;
    private Game game = null;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new GameManager();
                    instance.game = new Game(null, null);
                }
            }
        }
        return instance;
    }

    public void setContext(Context newContext) {
        context = newContext;
    }

    public void newGame(StageSpec stageSpec, GameListener gameListener) {
        if (null != game) {
            // TODO reset game correctly
        }
        game = new Game(stageSpec, gameListener);

    }

    public void newGame(GameMode gameMode, StageSpec stageSpec, GameListener gameListener) {
        if (null != game) {
            // TODO reset game correctly
        }
        game = new Game(gameMode, stageSpec, gameListener);

    }

    public void consumeBonus(final BonusType bonusType) {
        if(null != game) {
            game.consumeBonus(bonusType);
        }
    }

    public void startGame() {
        if (null != game && game.getGameState() == Game.GameState.IDLE) {
            game.initGame();
            game.start();
        }
    }

    public void newGame(long interval, long duration) {
        if (null != game) {
            // TODO reset game correctly
        }
        game = new Game(interval, duration);

    }

    public void newGame(long interval, long duration, GameManager.GameMode gameMode, StageSpec stageSpec, GameListener gameListener) {
        if (null != game) {
            // TODO reset game correctly
        }
        game = new Game(interval, duration, gameMode, stageSpec, gameListener);

    }

    public List<String> getGameString() {
        if(game != null) {
            return game.getGameString();
        }
        return null;
    }


    public boolean getInGame() {
        return null != game && game.getGameState() == Game.GameState.PLAYING;
    }

    public Game.GameState getGameState() {
        return game.getGameState();
    }

    public void stopGame(final ResultStatusGame result) {
        switch (result) {
            case SUCCESS:
                if (null != game) {
                    game.stop();
                }
                break;
            case FORCE_SUCCESS:
                if(null != game) {
                    game.stop();
                }
                break;
            case INTERRUPT:
                if (null != game) {
                    game.stop();
                    game.reset();
                }
                break;
            case STOPPED:
                break;
            case PAUSED:
                break;
            default:
                if (null != game) {
                    game.stop();
                }
                break;
        }
    }

    public void resetGame() {
        if (null != game) {
            game.reset();
        }
    }

    public void answerQuestion(final int note) {
        if(null != game) {
            game.answerQuestion(note);
        }
    }

    public int getCurrentRound() {
        if(null != game) {
            return game.getCurrentRound();
        }
        return -1;
    }

    public int getTotalRounds() {
        if(null != game) {
            return game.getTotalRounds();
        }
        return -1;
    }

}

