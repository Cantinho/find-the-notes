package com.findthenotes.findthenotes.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.adapter.StageAdapter;
import com.findthenotes.findthenotes.model.Stage;
import com.findthenotes.findthenotes.model.World;
import com.findthenotes.findthenotes.utils.ActiveAndroidUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

import butterknife.ButterKnife;

public class WorldActivity extends AppCompatActivity {

    private RecyclerView stageRecyclerView;
    private World mWorld;
    private InterstitialAd mInterstitialAd;
    private ImageView worldTitle;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

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

        Bundle extras = getIntent().getExtras();
        long worldID = 0;

        if (extras != null) {
            worldID = extras.getLong(World.ID);
        }

        worldTitle = (ImageView) findViewById(R.id.world_title);

        mWorld = ActiveAndroidUtils.getWorld(worldID);

        switch (mWorld.getTag()) {

            case (World.EASILY):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_1_note));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_1_note));
                }
                break;

            case (World.EASY_ONE):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_1_note));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_1_note));
                }
                break;

            case (World.EASY_TWO):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_2_notes));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_2_notes));
                }
                break;

            case (World.MEDIUM_ONE):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_3_notes));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_3_notes));
                }
                break;

            case (World.MEDIUM_TWO):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_4_notes));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_4_notes));
                }
                break;

            case (World.HARD_ONE):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_5_notes));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_5_notes));
                }
                break;

            case (World.HARD_TWO):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_6_notes));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_6_notes));
                }
                break;

            case (World.EXPERT):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_expert));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_expert));
                }
                break;

            case (World.EXTREME):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_extreme));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_extreme));
                }
                break;

            case (World.ABSOLUTE):
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    worldTitle.setImageDrawable(getDrawable(R.mipmap.img_absolute));
                } else {
                    worldTitle.setImageDrawable(getResources().getDrawable(R.mipmap.img_absolute));
                }
                break;

            default:
                break;
        }

        List<Stage> stages = ActiveAndroidUtils.getStages(mWorld);

        StageAdapter stageAdapter = new StageAdapter(stages, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);

        stageRecyclerView = (RecyclerView) findViewById(R.id.stage_recycler_view);

        if (stageRecyclerView != null) {
            stageRecyclerView.setLayoutManager(gridLayoutManager);
            stageRecyclerView.setAdapter(stageAdapter);
        }

        // Create the BannerAd and set the adUnitId (defined in values/strings.xml).
        mInterstitialAd = newInterstitialAd();
        //loadInterstitial();
        loadBannerAd();
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

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            Log.d("WorldActivity", "GoToNextLevel");
        }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        //mNextLevelButton.setEnabled(false);
        Log.d("WorldActivity - onAdLoaded", "Disable a button");
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
