package com.findthenotes.findthenotes.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.adapter.WorldAdapter;
import com.findthenotes.findthenotes.model.Stage;
import com.findthenotes.findthenotes.model.World;
import com.findthenotes.findthenotes.utils.ActiveAndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainMenu extends AppCompatActivity {

    private RecyclerView worldRecyclerView;
    private TextView points;
    private TextView stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Add the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Remove the title on action bar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // This is just Mock TODO Remove
        List<World> worlds = ActiveAndroidUtils.listWorlds();
        if(null == worlds) {
            worlds = new ArrayList<>();
        }

        WorldAdapter worldAdapter = new WorldAdapter(this, worlds);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        worldRecyclerView = (RecyclerView) findViewById(R.id.world_recycler_view);

        if (worldRecyclerView != null) {
            worldRecyclerView.setLayoutManager(gridLayoutManager);
            worldRecyclerView.setAdapter(worldAdapter);
        }

        initUI();

    }

    private void initUI() {
        points = (TextView) findViewById(R.id.player_points);
        stars = (TextView) findViewById(R.id.stars);
        updatePoints();
        updateStars();
    }

    private void updatePoints() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.points_preference), MODE_PRIVATE);
        int highScore = sharedPref.getInt(getString(R.string.points_preference), 0);
        points.setText(highScore + "");
    }

    private void updateStars() {
        int allStars = Stage.getAllStars();
        int totalStars = Stage.getTotalStars();
        String starsText = allStars + "/" + totalStars;
        stars.setText(starsText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.world_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutFromFacebook();
                Log.d("WorldActivity", "ACTION LOGOUT");
                break;
            case R.id.action_about:
                Log.d("WorldActivity", "ACTION ABOUT");
                Intent intent = new Intent(MainMenu.this, AboutActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                //finish();
                break;
            default:
                Log.d("WorldActivity", "ACTION DEFAULT");
        }
        return super.onOptionsItemSelected(item);
    }

    public void logoutFromFacebook() {
        try {
            FacebookSdk.sdkInitialize(getApplicationContext());
            if(isFacebookLoggedIn()) {
                LoginManager.getInstance().logOut();
            }
            Intent intent = new Intent(MainMenu.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            finish();
        } catch (Exception e) {}


    }

    public boolean isFacebookLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePoints();
        updateStars();
    }
}
