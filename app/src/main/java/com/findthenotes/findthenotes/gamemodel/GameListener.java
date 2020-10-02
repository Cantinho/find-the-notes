package com.findthenotes.findthenotes.gamemodel;

public interface GameListener {
    void onNextRound();
    void onAnswerCorrect(int note, int currentCell);
    void onAnswerWrong(int tries);
    void onGameOver();
    void onWin();
}
