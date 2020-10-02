package com.findthenotes.findthenotes.view;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AboutActivity extends AppCompatActivity {

    private RecyclerView worldRecyclerView;
    private TextView points;
    private TextView stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

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

        // This is just Mock TODO Remove
        List<World> worlds = ActiveAndroidUtils.listWorlds();
        if(null == worlds) {
            worlds = new ArrayList<>();
        }


        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
