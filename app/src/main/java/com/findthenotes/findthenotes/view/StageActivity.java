package com.findthenotes.findthenotes.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.aigiltech_games.GameManager;
import com.findthenotes.findthenotes.gamemodel.GameListener;
import com.findthenotes.findthenotes.gamemodel.Notes;
import com.findthenotes.findthenotes.gamemodel.StageSpec;
import com.findthenotes.findthenotes.model.Stage;
import com.findthenotes.findthenotes.model.World;
import com.findthenotes.findthenotes.sound.IXSoundPoolListener;
import com.findthenotes.findthenotes.sound.XSoundPoolManager;
import com.findthenotes.findthenotes.utils.ActiveAndroidUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.liangfeizc.RubberIndicator;

import java.util.List;

import static com.findthenotes.findthenotes.utils.FindTheNotesApplicationUtil.getSoundNoteDurationsInMillis;

public class StageActivity extends AppCompatActivity implements GameListener {


    static class FourNotesViewHolder {
        @BindView(R.id.card_note_one) TextView cardNoteOne;
        @BindView(R.id.card_note_two) TextView cardNoteTwo;
        @BindView(R.id.card_note_three) TextView cardNoteThree;
        @BindView(R.id.card_note_four) TextView cardNoteFour;
    }

    static class EightNotesViewHolder {
        @BindView(R.id.card_note_one) TextView cardNoteOne;
        @BindView(R.id.card_note_two) TextView cardNoteTwo;
        @BindView(R.id.card_note_three) TextView cardNoteThree;
        @BindView(R.id.card_note_four) TextView cardNoteFour;
        @BindView(R.id.card_note_five) TextView cardNoteFive;
        @BindView(R.id.card_note_six) TextView cardNoteSix;
        @BindView(R.id.card_note_seven) TextView cardNoteSeven;
        @BindView(R.id.card_note_eight) TextView cardNoteEight;
    }

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

    @BindView(R.id.play_note) ImageView playNoteButton;

    @BindView(R.id.notes_container) LinearLayout cardsNoteContainer;

    @BindView(R.id.heart_second) ImageView secondHeart;
    @BindView(R.id.heart_first) ImageView firstHeart;
    @BindView(R.id.cards_count) TextView cardCounts;

    @BindView(R.id.rubber) RubberIndicator mRubberIndicator;

    private InterstitialAd mInterstitialAd;
    protected static int adsenseVideoCounter = 0;
    protected static final int MAXIMUM_ADSENSE_VIDEO_COUNTER = 2;
    private Stage mStage;
    private StageSpec mStageSpec;

    private EightNotesViewHolder eightNotesViewHOlder;
    private FourNotesViewHolder fourNotesViewHOlder;

    private int stars = 3;

    private Context mContext;
    private int soundStatus;
    private int stageSize;
    private int notesAmount;
    private static final int PLAYING = 1;
    private static final int STOPPED = 0;

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
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

        // create video adsense to be called later
        createInterstitialAd();

        // Load Stage
        Bundle extras = getIntent().getExtras();
        long stageId = 0;

        if (extras != null) {
            stageId = extras.getLong(Stage.ID);
        }

        mStage = ActiveAndroidUtils.getStage(stageId);

        //initSoundButtons();
        mStageSpec = initStageSpec();
        stageSize = mStageSpec.getSize();
        notesAmount = mStageSpec.getPlayNotesAmount();

        //initGame();
        initGameEngine(mStageSpec);

        initUI();

        try {
            XSoundPoolManager.getInstance().setPlaySound(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        mRubberIndicator.setCount(stageSize, 0);

        cardCounts.setText("99");

        createNoteCardsView();

        try {
            XSoundPoolManager.getInstance().setIXSoundPoolListener(new IXSoundPoolListener() {
                @Override
                public void onStop() {
                    StageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updatePlayButtonUI(STOPPED);
                        }
                    });
                }

                @Override
                public void onEndSound() {
                    StageActivity.this.runOnUiThread(new Runnable() {
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
                    //game.answer(Notes._DO);
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

        //TODO THIS IS MOCK!! REMOVE IT!!!
        List<String> notes = gameManager.getGameString();
        String mock = "";
        for (String note : notes) {
            mock += "--" +note;
        }

        TextView tvMock = (TextView) findViewById(R.id.mock);
        tvMock.setText(mock);

    }

    private void createNoteCardsView() {
        cardsNoteContainer.removeAllViews();

        if (notesAmount > 4) {
            fourNotesViewHOlder = null;
            eightNotesViewHOlder = new EightNotesViewHolder();
            getLayoutInflater().inflate(R.layout.view_eight_notes, cardsNoteContainer, true);
            ButterKnife.bind(eightNotesViewHOlder, cardsNoteContainer);

            if (notesAmount <= 7) {
                eightNotesViewHOlder.cardNoteEight.setVisibility(View.GONE);
            }

            if (notesAmount <= 6) {
                eightNotesViewHOlder.cardNoteSeven.setVisibility(View.GONE);
            }

            if (notesAmount <= 5) {
                eightNotesViewHOlder.cardNoteSix.setVisibility(View.GONE);
            }

        } else {
            eightNotesViewHOlder = null;
            fourNotesViewHOlder = new FourNotesViewHolder();
            getLayoutInflater().inflate(R.layout.view_four_notes, cardsNoteContainer, true);
            ButterKnife.bind(fourNotesViewHOlder, cardsNoteContainer);

            if (notesAmount <= 3) {
                fourNotesViewHOlder.cardNoteFour.setVisibility(View.GONE);
            }

            if (notesAmount <= 2) {
                fourNotesViewHOlder.cardNoteThree.setVisibility(View.GONE);
            }

            if (notesAmount <= 1) {
                fourNotesViewHOlder.cardNoteTwo.setVisibility(View.GONE);
            }

        }
    }

    private void clearNoteCardsView() {
        if (notesAmount > 4) {
            eightNotesViewHOlder.cardNoteOne.setText("");
            eightNotesViewHOlder.cardNoteTwo.setText("");
            eightNotesViewHOlder.cardNoteThree.setText("");
            eightNotesViewHOlder.cardNoteFour.setText("");
            eightNotesViewHOlder.cardNoteFive.setText("");
            eightNotesViewHOlder.cardNoteSix.setText("");
            eightNotesViewHOlder.cardNoteSeven.setText("");
            eightNotesViewHOlder.cardNoteEight.setText("");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                eightNotesViewHOlder.cardNoteOne.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteTwo.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteThree.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteFour.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteFive.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteSix.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteSeven.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteEight.setBackground(getDrawable(R.mipmap.bg_hidden_note));
            } else {
                eightNotesViewHOlder.cardNoteOne.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteTwo.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteThree.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteFour.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteFive.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteSix.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteSeven.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                eightNotesViewHOlder.cardNoteEight.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
            }

            if (notesAmount <= 7) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteEight.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                } else {
                    eightNotesViewHOlder.cardNoteEight.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                }
            }

            if (notesAmount <= 6) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteSeven.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                } else {
                    eightNotesViewHOlder.cardNoteSeven.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                }
            }

            if (notesAmount <= 5) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteSix.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                } else {
                    eightNotesViewHOlder.cardNoteSix.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                }
            }

        } else {
            fourNotesViewHOlder.cardNoteOne.setText("");
            fourNotesViewHOlder.cardNoteTwo.setText("");
            fourNotesViewHOlder.cardNoteThree.setText("");
            fourNotesViewHOlder.cardNoteFour.setText("");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fourNotesViewHOlder.cardNoteOne.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                fourNotesViewHOlder.cardNoteTwo.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                fourNotesViewHOlder.cardNoteThree.setBackground(getDrawable(R.mipmap.bg_hidden_note));
                fourNotesViewHOlder.cardNoteFour.setBackground(getDrawable(R.mipmap.bg_hidden_note));
            } else {
                fourNotesViewHOlder.cardNoteOne.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                fourNotesViewHOlder.cardNoteTwo.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                fourNotesViewHOlder.cardNoteThree.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
                fourNotesViewHOlder.cardNoteFour.setBackground(getResources().getDrawable(R.mipmap.bg_hidden_note));
            }
        }
    }

    public void revealCardNote(int note, int currentCell) {
        int actualNote  = currentCell + 1;
        if (notesAmount > 4) {
            if (actualNote == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteOne.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteOne.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteOne.setText(Notes.toString(note));
            } else if (actualNote == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteTwo.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteTwo.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteTwo.setText(Notes.toString(note));
            } else if (actualNote == 3) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteThree.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteThree.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteThree.setText(Notes.toString(note));
            } else if (actualNote == 4) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteFour.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteFour.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteFour.setText(Notes.toString(note));
            } else if (actualNote == 5) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteFive.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteFive.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteFive.setText(Notes.toString(note));
            } else if (actualNote == 6) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteSix.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteSix.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteSix.setText(Notes.toString(note));
            } else if (actualNote == 7) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteSeven.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteSeven.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteSeven.setText(Notes.toString(note));
            } else if (actualNote == 8) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    eightNotesViewHOlder.cardNoteEight.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    eightNotesViewHOlder.cardNoteEight.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                eightNotesViewHOlder.cardNoteEight.setText(Notes.toString(note));
            }
        } else {
            if (actualNote == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fourNotesViewHOlder.cardNoteOne.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    fourNotesViewHOlder.cardNoteOne.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                fourNotesViewHOlder.cardNoteOne.setText(Notes.toString(note));
            } else if (actualNote == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fourNotesViewHOlder.cardNoteTwo.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    fourNotesViewHOlder.cardNoteTwo.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                fourNotesViewHOlder.cardNoteTwo.setText(Notes.toString(note));
            } else if (actualNote == 3) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fourNotesViewHOlder.cardNoteThree.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    fourNotesViewHOlder.cardNoteThree.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                fourNotesViewHOlder.cardNoteThree.setText(Notes.toString(note));
            } else if (actualNote == 4) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    fourNotesViewHOlder.cardNoteFour.setBackground(getDrawable(R.mipmap.bg_show_note));
                } else {
                    fourNotesViewHOlder.cardNoteFour.setBackground(getResources().getDrawable(R.mipmap.bg_show_note));
                }
                fourNotesViewHOlder.cardNoteFour.setText(Notes.toString(note));
            }
        }
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
        StageSpec stageSpec = null;
        try {
            stageSpec = ActiveAndroidUtils.getStageSpec(mStage.getWorld().getTag() + (mStage.getNumber() - 1));
            stageSpec.generateLastNotesRandomly();
            stageSpec.shuffleAnyway();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stageSpec;
    }

    private void initGameEngine(final StageSpec stageSpec) {
        System.out.println("STAGE ACTIVITY");
        updatePlayButtonUI(STOPPED);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            firstHeart.setImageDrawable(getDrawable(R.mipmap.ic_heart_active));
            secondHeart.setImageDrawable(getDrawable(R.mipmap.ic_heart_active));
        } else {
            firstHeart.setImageDrawable(getResources().getDrawable(R.mipmap.ic_heart_active));
            secondHeart.setImageDrawable(getResources().getDrawable(R.mipmap.ic_heart_active));
        }

        gameManager = GameManager.getInstance();

        gameManager.newGame(stageSpec, this);
        gameManager.startGame();
    }

    @Override
    public void onNextRound() {
        if (gameManager.getCurrentRound() < gameManager.getTotalRounds()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clearNoteCardsView();
                    // simulate rubber indicator move to right
                    mRubberIndicator.moveToRight();
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
        clearNoteCardsView();
        stars--;
        if (tries == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                firstHeart.setImageDrawable(getDrawable(R.mipmap.ic_heart_inactive));
            } else {
                firstHeart.setImageDrawable(getResources().getDrawable(R.mipmap.ic_heart_inactive));
            }
        } else if (tries == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                secondHeart.setImageDrawable(getDrawable(R.mipmap.ic_heart_inactive));
            } else {
                secondHeart.setImageDrawable(getResources().getDrawable(R.mipmap.ic_heart_inactive));
            }
        }
    }

    public void createInterstitialAd() {
        mInterstitialAd = new InterstitialAd(StageActivity.this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.intersticial_find_the_notes_all_types));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {

            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        final AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    public void createFacebookShareButton(View view) {
        // share button
        final ShareButton shareButton = (ShareButton) view.findViewById(R.id.facebook_share_btn);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shareButton != null) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //share the image to Facebook
                            ShareDialog shareDialog;

                            FacebookSdk.sdkInitialize(mContext);
                            shareDialog = new ShareDialog((Activity) mContext);

                            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                    .setContentTitle(getResources().getString(R.string.app_title).toString())
                                    .setContentDescription(getResources().getString(R.string.app_content_description).toString())
                                    .setContentUrl(Uri.parse(getResources().getString(R.string.company_internet_address).toString()))
                                    .setImageUrl(Uri.parse(getResources().getString(R.string.app_icon_address).toString()))
                                    .build();

                            shareDialog.show(linkContent);
                        }
                    });
                    thread.start();

                }
            }
        });
    }

    @OnClick(R.id.give_up_button)
    public void giveUp() {
        // TODO ADD ADS =p
        Intent intent = new Intent(mContext, WorldActivity.class);
        intent.putExtra(World.ID, mStage.getWorld().getId());
        mContext.startActivity(intent);
        finish();
    }

    @Override
    public void onGameOver() {
        Log.d("GAME","GAME OVER");
        LayoutInflater factory = LayoutInflater.from(this);
        final View gameOverDialogView = factory.inflate(R.layout.dialog_loss, null);
        final AlertDialog gameOverDialog = new AlertDialog.Builder(this).create();

        // create facebook share button
        createFacebookShareButton(gameOverDialogView);
        requestNewInterstitial();

        adsenseVideoCounter++;
        if(mInterstitialAd != null && adsenseVideoCounter >= MAXIMUM_ADSENSE_VIDEO_COUNTER) {
            displayInterstitial();
            adsenseVideoCounter = 0;
            Log.d("StageActivity", "IS LOADED = TRUE");
        } else {
            Log.d("StageActivity", "IS LOADED = FALSE");

        }

        for (int i = 1; i <= stageSize; i++) {
            mRubberIndicator.moveToLeft();
        }

        gameOverDialog.setCancelable(false);
        gameOverDialog.setView(gameOverDialogView);

        gameOverDialogView.findViewById(R.id.bt_try_again).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stars = 3;
                clearNoteCardsView();
                initGameEngine(mStageSpec);
                gameOverDialog.dismiss();
            }
        });

        gameOverDialogView.findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameOverDialog.dismiss();
                Intent intent = new Intent(mContext, WorldActivity.class);
                intent.putExtra(World.ID, mStage.getWorld().getId());
                mContext.startActivity(intent);
                finish();
            }
        });

//        gameOverDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                try {
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        gameOverDialog.show();

    }

    void displayInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onWin() {
        Log.d("GAME","YOU WIN");

        LayoutInflater factory = LayoutInflater.from(this);
        final View youWinDialogView = factory.inflate(R.layout.dialog_win, null);
        final AlertDialog youWinDialog = new AlertDialog.Builder(this).create();

        // create facebook share button
        createFacebookShareButton(youWinDialogView);


        // only update if a player bit the previous score
        int starsBefore = mStage.getStars();

        if (starsBefore < stars) {

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.points_preference), MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            int highScore = sharedPref.getInt(getString(R.string.points_preference), 0);

            if (starsBefore == 0) {
                highScore += (mStage.getWorld().getPointMultiplier() * stars);
                editor.putInt(getString(R.string.points_preference), highScore);
                editor.apply();
            } else if (starsBefore == 1) {
                highScore -= (mStage.getWorld().getPointMultiplier());
                highScore += (mStage.getWorld().getPointMultiplier() * stars);
                editor.putInt(getString(R.string.points_preference), highScore);
                editor.apply();
            } else if (starsBefore == 2) {
                highScore -= (mStage.getWorld().getPointMultiplier() * 2);
                highScore += (mStage.getWorld().getPointMultiplier() * stars);
                editor.putInt(getString(R.string.points_preference), highScore);
                editor.apply();
            }
        }

        mStage.updateStars(stars);

        final Stage next = mStage.getNextStage();
        if (next != null) {
            next.unlock();
        }

        youWinDialog.setCancelable(false);
        youWinDialog.setView(youWinDialogView);

        ImageView starLeft = (ImageView) youWinDialogView.findViewById(R.id.win_star_left);
        ImageView starMiddle = (ImageView) youWinDialogView.findViewById(R.id.win_star_middle);
        ImageView starRight = (ImageView) youWinDialogView.findViewById(R.id.win_star_right);

        if (stars == 3) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                starLeft.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
                starMiddle.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
                starRight.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
            } else {
                starLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
                starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
                starRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
            }
        } else if (stars == 2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                starLeft.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
                starMiddle.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
                starRight.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_inactive));
            } else {
                starLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
                starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
                starRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_inactive));
            }
        } else if (stars == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                starLeft.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_active));
                starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_inactive));
                starRight.setImageDrawable(mContext.getDrawable(R.mipmap.ic_star_center_inactive));
            } else {
                starLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_active));
                starMiddle.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_inactive));
                starRight.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_star_center_inactive));
            }
        }

        youWinDialogView.findViewById(R.id.bt_next_challenge).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                youWinDialog.dismiss();
                if (next == null) {
                    Intent intent = new Intent(mContext, WorldActivity.class);
                    intent.putExtra(World.ID, mStage.getWorld().getId());
                    mContext.startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(mContext, StageActivity.class);
                    intent.putExtra(Stage.ID, next.getId());
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }
            }
        });

        youWinDialogView.findViewById(R.id.bt_back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                youWinDialog.dismiss();
                Intent intent = new Intent(mContext, WorldActivity.class);
                intent.putExtra(World.ID, mStage.getWorld().getId());
                mContext.startActivity(intent);
                finish();
            }
        });
        youWinDialog.show();
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