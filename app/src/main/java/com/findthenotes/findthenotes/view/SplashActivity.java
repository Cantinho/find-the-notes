package com.findthenotes.findthenotes.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.findthenotes.findthenotes.service.GameService;
import com.findthenotes.findthenotes.sound.XSoundPoolManager;
import com.findthenotes.findthenotes.R;

public class SplashActivity extends AppCompatActivity {

    public static CallbackManager facebookCallbackManager;
    private LoginButton facebookLoginButton;
    private Button loginButton;


    private void startService() {
        // use this to start and trigger a service
        Intent i= new Intent(this, GameService.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        this.startService(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO : EXAMPLE
        startService();

        //Facebook SDK initialize
        try {
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
            Log.d("FTN", "Facebook app activated");
        } catch (Exception e) {
            Log.d("FTN", e.getMessage());
        }

        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.login_button);

        if (loginButton != null) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startMainMenuIntent = new Intent(SplashActivity.this, MainMenu.class);
                    //startMainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(startMainMenuIntent);
                    if(null != XSoundPoolManager.getInstance()) {
                        try {
                            XSoundPoolManager.getInstance().playSound(R.raw.pickup_coin_1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    finish();
                }
            });
        }

        try{
            getActionBar().hide();
        } catch (Exception ignored){}

        facebookCallbackRegistration();
        try {
            if(isFacebookLoggedIn()) {
                Intent startMainMenuIntent = new Intent(SplashActivity.this, MainMenu.class);
                //startMainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startMainMenuIntent);
                if(null != XSoundPoolManager.getInstance()) {
                    try {
                        XSoundPoolManager.getInstance().playSound(R.raw.pickup_coin_1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void facebookCallbackRegistration() {
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        facebookLoginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);

        if (facebookLoginButton != null) {
            facebookLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != XSoundPoolManager.getInstance()) {
                        try {
                            XSoundPoolManager.getInstance().playSound(R.raw.pickup_coin_2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        // Callback manager
        facebookCallbackManager = CallbackManager.Factory.create();

        // Callback registration
        facebookLoginButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FTN", "onSuccess");
                Intent startMainMenuIntent = new Intent(SplashActivity.this, MainMenu.class);
                //startMainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startMainMenuIntent);
                if(null != XSoundPoolManager.getInstance()) {
                    try {
                        XSoundPoolManager.getInstance().playSound(R.raw.pickup_coin_1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                finish();
            }

            @Override
            public void onCancel() {
                Log.d("FTN", "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FTN", "onError");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isFacebookLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }




}
