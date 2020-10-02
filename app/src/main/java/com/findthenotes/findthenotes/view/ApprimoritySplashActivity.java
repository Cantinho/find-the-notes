package com.findthenotes.findthenotes.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.utils.FindTheNotesApplicationUtil;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ApprimoritySplashActivity extends AppCompatActivity {

    private Thread tipsThread;
    private boolean exit = false;
    private TextView tipsTextView;
    private int tipCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_apprimority_splash);
        tipsTextView = (TextView) findViewById(R.id.tips_content);
        startAnim();
        hide();
        try {
            FindTheNotesApplicationUtil.getInstance().init(this);
            FindTheNotesApplicationUtil.getInstance().setLoadingApplicationListener(new FindTheNotesApplicationUtil.LoadingApplicationListener() {
                @Override
                public void onLoaded() {
                    stopAnim();
                    exit = true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tipsThread = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!isInterrupted() || exit == true) {
                            Thread.sleep(2000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tipCounter = (tipCounter+1) % getResources().getStringArray(R.array.tips_array).length;
                                    tipsTextView.setText(getResources().getStringArray(R.array.tips_array)[tipCounter]);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                    }
                }
            };

            tipsThread.start();
        }catch (Exception e) {

        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    void startAnim() {
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.VISIBLE);
    }

    void stopAnim() {
        findViewById(R.id.avloadingIndicatorView).setVisibility(View.GONE);
    }

}
