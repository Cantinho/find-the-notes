package com.findthenotes.findthenotes.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.FacebookSdk;
import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.aigiltech_games.GameManager;
import com.findthenotes.findthenotes.gamemodel.GameListener;
import com.findthenotes.findthenotes.gamemodel.Notes;
import com.findthenotes.findthenotes.gamemodel.StageGenerator;
import com.findthenotes.findthenotes.gamemodel.StageSpec;
import com.findthenotes.findthenotes.model.World;
import com.findthenotes.findthenotes.sound.IXSoundPoolListener;
import com.findthenotes.findthenotes.sound.XSoundPoolManager;
import com.findthenotes.findthenotes.utils.FindTheNotesApplicationUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

import static com.findthenotes.findthenotes.utils.FindTheNotesApplicationUtil.getSoundNoteDurationsInMillis;

public class TrainingActivity extends AppCompatActivity implements GameListener {

    //Notes
    @BindView(R.id.do_button) Button doButton;
    @BindView(R.id.re_button) Button reButton;
    @BindView(R.id.mi_button) Button miButton;
    @BindView(R.id.fa_button) Button faButton;
    @BindView(R.id.sol_button) Button solButton;
    @BindView(R.id.la_button) Button laButton;
    @BindView(R.id.si_button) Button siButton;
    @BindView(R.id.do_sus) Button doSusButton;
    @BindView(R.id.re_sus) Button reSusButton;
    @BindView(R.id.fa_sus) Button faSusButton;
    @BindView(R.id.la_sus) Button laSusButton;
    @BindView(R.id.sol_sus) Button solSusButton;

    //Cards
    @BindView(R.id.card_note_one) TextView cardNoteOne;
    @BindView(R.id.card_note_two) TextView cardNoteTwo;
    @BindView(R.id.card_note_three) TextView cardNoteThree;
    @BindView(R.id.card_note_four) TextView cardNoteFour;

    @BindView(R.id.play_note) ImageView playNoteButton;

    @BindView(R.id.cards_count) TextView cardCounts;
    private InterstitialAd mInterstitialAd;
    private InterstitialAd mVideoInterstitialAd;

    private StageSpec mStageSpec;

    private Context mContext;
    private int soundStatus;
    private static final int PLAYING = 1;
    private static final int STOPPED = 0;

    private GameManager gameManager;

    private final int MINIMUM_CORRECT_ANSWER_ADSENSE_COUNTER = 5;
    private final int MINIMUM_WRONG_ANSWER_ADSENSE_COUNTER = 10;
    private int correctAnswerAdsenseCounter = 0;
    private int wrongAnswerAdsenseCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        mContext = this;

        ButterKnife.bind(this);

        //initialize facebook sdk
        try {
            FacebookSdk.sdkInitialize(getApplicationContext());
        } catch (Exception e){}

        // Add the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Remove the title on action bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Set home button enable
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Change home button icon
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);

        //initSoundButtons();
        mStageSpec = initStageSpec();

        //initGame();
        initGameEngine(mStageSpec);

        initUI();

        try {
            XSoundPoolManager.getInstance().setPlaySound(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Create the BannerAd and set the adUnitId (defined in values/strings.xml).
            mInterstitialAd = newInterstitialAd();
            //loadInterstitial();
            loadBannerAd();
        } catch (Exception e) {e.printStackTrace();}

        mVideoInterstitialAd = newVideoInterstitialAd();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.banner_interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //mNextLevelButton.setEnabled(true);
                Log.d("WorldActivity - onAdLoaded", "Enable a button");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //mNextLevelButton.setEnabled(true);
                Log.d("WorldActivity - onAdFailedToLoad - error", "Enable a button");
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                Log.d("WorldActivity - onAdClosed", "GoToNextLevel");
                //goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private InterstitialAd newVideoInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.intersticial_find_the_notes_all_types));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //mNextLevelButton.setEnabled(true);
                Log.d("WorldActivity - onAdLoaded", "Enable a button");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //mNextLevelButton.setEnabled(true);
                Log.d("WorldActivity - onAdFailedToLoad - error", "Enable a button");
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                Log.d("WorldActivity - onAdClosed", "GoToNextLevel");
                //goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void loadBannerAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //Begin Game, continue with app
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.loadAd(adRequest);
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        cardCounts.setText("99");
        clearCards();

        try {
            XSoundPoolManager.getInstance().setIXSoundPoolListener(new IXSoundPoolListener() {
                @Override
                public void onStop() {
                    TrainingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updatePlayButtonUI(STOPPED);
                        }
                    });
                }

                @Override
                public void onEndSound() {
                    TrainingActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updatePlayButtonUI(STOPPED);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        playNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playNotes();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        doButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._DO);
                    try {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    XSoundPoolManager.getInstance().playSound(Notes._DO);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._RE);
                    try {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    XSoundPoolManager.getInstance().playSound(Notes._RE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        miButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._MI);
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._MI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._FA);
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._FA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        solButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._SOL);
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._SOL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        laButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._LA);
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._LA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        siButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._SI);
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._SI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        doSusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._DO_SUS);
                    Log.i("TBN","DO SUS");
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._DO_SUS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        reSusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._RE_SUS);
                    Log.i("TBN","RE SUS");
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._RE_SUS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        faSusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._FA_SUS);
                    Log.i("TBN","FA SUS");
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._FA_SUS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        laSusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._LA_SUS);
                    Log.i("TBN","LA SUS");
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._LA_SUS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        solSusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != XSoundPoolManager.getInstance()) {
                    gameManager.answerQuestion(Notes._SOL_SUS);
                    Log.i("TBN","SOL SUS");
                    try {
                        XSoundPoolManager.getInstance().playSound(Notes._SOL_SUS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void playNotes() throws Exception {
        if (soundStatus == STOPPED) {
            updatePlayButtonUI(PLAYING);

            // Play the notes
            // TODO
            //getSoundNoteDurationInMillis
            int [] notes = new int[mStageSpec.getPlayNotesAmount()];
            for(int i = 0; i < notes.length; i++) {
                notes[i] = mStageSpec.getStageNotes()[gameManager.getCurrentRound() * mStageSpec.getPlayNotesAmount() + i];
            }
            long [] durations = getSoundNoteDurationsInMillis(notes);
            int delay = 100;
            XSoundPoolManager.getInstance().playSounds(notes, durations, delay);

            //TODO listen for the end of sound and back to stopped status
        } else if (soundStatus == PLAYING) {
            updatePlayButtonUI(STOPPED);
            // Stop the sound
            if(null != XSoundPoolManager.getInstance()) {
                XSoundPoolManager.getInstance().stop();
                XSoundPoolManager.getInstance().stopSounds();
            }
        }
    }

    private void updatePlayButtonUI(int status) {
        soundStatus = status;
        if (soundStatus == STOPPED) {
            // Change image
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                playNoteButton.setImageDrawable(getDrawable(R.mipmap.img_play));
            } else {
                playNoteButton.setImageDrawable(getResources().getDrawable(R.mipmap.img_play));
            }
        } else if (soundStatus == PLAYING) {
            // Change image
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                playNoteButton.setImageDrawable(getDrawable(R.mipmap.img_pause));
            } else {
                playNoteButton.setImageDrawable(getResources().getDrawable(R.mipmap.img_pause));
            }
        }
    }

    private StageSpec initStageSpec() {
        int[] notes = FindTheNotesApplicationUtil.SOUNDS;
        StageSpec stageSpec = new StageSpec(false, StageSpec.TRAINING, "training", 1, 4, StageGenerator.KEY_TYPE_BLACK_AND_WHITE, 12, 1, notes, false, 12);
        stageSpec.shuffleAnyway();

        return stageSpec;
    }

    private void initGameEngine(final StageSpec stageSpec) {
        System.out.println("STAGE ACTIVITY");
        updatePlayButtonUI(STOPPED);

        gameManager = GameManager.getInstance();

        gameManager.newGame(stageSpec, this);
        gameManager.startGame();
    }

    public void clearCards() {
        List<String> notes = gameManager.getGameString();
        for (int i = 0; i < notes.size(); i++) {
            if (i == 0) {
                cardNoteOne.setText(notes.get(0));
            } else if (i == 1) {
                cardNoteTwo.setText(notes.get(1));
            } else if (i == 2) {
                cardNoteThree.setText(notes.get(2));
            } else if (i == 3) {
                cardNoteFour.setText(notes.get(3));
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardNoteOne.setBackground(getDrawable(R.mipmap.bg_hidden_note_training));
            cardNoteTwo.setBackground(getDrawable(R.mipmap.bg_hidden_note_training));
            cardNoteThree.setBackground(getDrawable(R.mipmap.bg_hidden_note_training));
            cardNoteFour.setBackground(getDrawable(R.mipmap.bg_hidden_note_training));
        } else {
            cardNoteOne.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note_training));
            cardNoteTwo.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note_training));
            cardNoteThree.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note_training));
            cardNoteFour.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note_training));
        }
    }

    public void revealCardNote(int note, int currentCell) {
        int actualNote  = currentCell + 1;
        if (actualNote == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cardNoteOne.setBackground(getDrawable(R.mipmap.bg_show_note));
            } else {
                cardNoteOne.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
            }
            cardNoteOne.setText(Notes.toString(note));
        } else if (actualNote == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cardNoteTwo.setBackground(getDrawable(R.mipmap.bg_show_note));
            } else {
                cardNoteTwo.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
            }
            cardNoteTwo.setText(Notes.toString(note));
        } else if (actualNote == 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cardNoteThree.setBackground(getDrawable(R.mipmap.bg_show_note));
            } else {
                cardNoteThree.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
            }
            cardNoteThree.setText(Notes.toString(note));
        } else if (actualNote == 4) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cardNoteFour.setBackground(getDrawable(R.mipmap.bg_show_note));
            } else {
                cardNoteFour.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
            }
            cardNoteFour.setText(Notes.toString(note));
        }
    }

    @Override
    public void onNextRound() {
        if (gameManager.getCurrentRound() < gameManager.getTotalRounds()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //TODO MOSTRAR
                }

            },1000);
        }
    }

    @Override
    public void onAnswerCorrect(int note, int currentCell) {
        Log.d("GAME","CORRECT ");
        revealCardNote(note, currentCell);
    }

    @Override
    public void onAnswerWrong(int tries) {
        Log.d("GAME","LIFE LOST: " + tries);
        clearCards();
        wrongAnswerAdsenseCounter++;
        if(wrongAnswerAdsenseCounter > MINIMUM_WRONG_ANSWER_ADSENSE_COUNTER) {
            recreateAdVideoAdsense();
            correctAnswerAdsenseCounter = 0;
            wrongAnswerAdsenseCounter = 0;
        }
    }

    @OnClick(R.id.give_up_button)
    public void giveUp() {
        Intent intent = new Intent(mContext, MainMenu.class);
        //TODO ADD THIS
//        intent.putExtra(World.TAG, .getTag());
//        intent.putExtra(World.ID, .getId());
        mContext.startActivity(intent);
        finish();
    }

    @Override
    public void onGameOver() {
        Log.d("GAME","GAME OVER");
    }

    @Override
    public void onWin() {
        Log.d("GAME","YOU WIN");
        //initSoundButtons();
        mStageSpec = initStageSpec();

        //initGame();
        initGameEngine(mStageSpec);
        clearCards();

        correctAnswerAdsenseCounter++;
        if(correctAnswerAdsenseCounter > MINIMUM_CORRECT_ANSWER_ADSENSE_COUNTER) {
            recreateAdVideoAdsense();
            correctAnswerAdsenseCounter = 0;
            wrongAnswerAdsenseCounter = 0;
        }
        recreateBannerAdsense();

    }

    private void requestNewInterstitial() {
        final AdRequest adRequest = new AdRequest.Builder().build();
        mVideoInterstitialAd.loadAd(adRequest);
    }

    void displayInterstitial() {
        if (mVideoInterstitialAd.isLoaded()) {
            mVideoInterstitialAd.show();
        }
    }

    public void recreateAdVideoAdsense() {
        requestNewInterstitial();
        if(mVideoInterstitialAd != null) {
            displayInterstitial();
            Log.d("StageActivity", "IS LOADED = TRUE");
        } else {
            Log.d("StageActivity", "IS LOADED = FALSE");

        }
    }

    public void recreateBannerAdsense() {
        try {
            // Create the BannerAd and set the adUnitId (defined in values/strings.xml).
            mInterstitialAd = newInterstitialAd();
            //loadInterstitial();
            loadBannerAd();
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            giveUp();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        giveUp();
    }
}