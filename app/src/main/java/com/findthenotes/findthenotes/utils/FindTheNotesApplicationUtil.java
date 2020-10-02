package com.findthenotes.findthenotes.utils;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.findthenotes.findthenotes.R;
import com.findthenotes.findthenotes.gamemodel.Notes;
import com.findthenotes.findthenotes.sound.XGenericSoundPool;
import com.findthenotes.findthenotes.sound.XLollipopSoundPool;
import com.findthenotes.findthenotes.sound.XSoundPoolManager;
import com.findthenotes.findthenotes.view.SplashActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by samirtf on 22/06/16.
 */
public class FindTheNotesApplicationUtil {

    private static final FindTheNotesApplicationUtil INSTANCE = new FindTheNotesApplicationUtil();

    public interface LoadingApplicationListener {
        void onLoaded();
    }

    private Context mContext;
    private LoadingApplicationListener listener;
    private boolean soundsLoaded = false;
    private boolean databaseLoaded = false;
    private boolean exit = false;
    private static final long MINIMUM_LOADING_TIME_IN_MILLIS = 4000;
    private static long scheduledStartMainMenuInMillis = -1;

    private Handler handler;
    private Runnable runnable;

    private Thread soundsLoaderThread;
    private Runnable soundsLoaderRunnable;

    private Thread databaseLoaderThread;
    private Runnable databaseLoaderRunnable;

    public static int counter = 0;

    public FindTheNotesApplicationUtil() {
        soundsLoaded = false;
        databaseLoaded = false;
    }

    public static FindTheNotesApplicationUtil getInstance() {
        return INSTANCE;
    }

    public void setLoadingApplicationListener(LoadingApplicationListener listener) {
        this.listener = listener;
    }

    private boolean checkApplicationLoaded() {
        return (soundsLoaded && databaseLoaded) || exit;
    }

    public void init(Context context) throws Exception {
        if(context instanceof Activity) {
            mContext = context;
        } else {
            throw new Exception("Context must be a instance of android.app.Activity");
        }
        handler = null;
        scheduledStartMainMenuInMillis = System.currentTimeMillis() + MINIMUM_LOADING_TIME_IN_MILLIS;

        runnable = new Runnable() {
            @Override
            public void run() {
                if(checkApplicationLoaded() && System.currentTimeMillis() > scheduledStartMainMenuInMillis && scheduledStartMainMenuInMillis != -1) {
                    if(listener != null) {
                        listener.onLoaded();
                    }
                    Intent intent = new Intent(mContext, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                    try{ ((Activity) mContext).finish();} catch (Exception e){}
                    scheduledStartMainMenuInMillis = -1;
                } else {
                    handler.postDelayed(this, 100);
                    counter++;
                    System.out.println("COUNTER: " + counter);
                }
            }
        };
        handler = new Handler();

        soundsLoaderThread = null;
        soundsLoaderRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    initSoundButtons();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        soundsLoaderThread = new Thread(soundsLoaderRunnable);

        databaseLoaderThread = null;
        databaseLoaderRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    initDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        databaseLoaderThread = new Thread(databaseLoaderRunnable);

        soundsLoaderThread.start();
        databaseLoaderThread.start();

        handler.postDelayed(runnable, 100);
    }


    public boolean getSoundsLoaded() {
        return soundsLoaded;
    }

    public void setSoundsLoaded(boolean soundsLoaded) {
        synchronized (this) {
            this.soundsLoaded = soundsLoaded;
        }
    }

    public boolean getDatabaseLoaded() {
        return databaseLoaded;
    }

    public void setDatabaseLoaded(boolean databaseLoaded) {
        synchronized (this) {
            this.databaseLoaded = databaseLoaded;
        }
    }

    public boolean getExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }



    private void initDatabase() {
        Log.i("DB INITIALIZE","DB INITIALIZE");
        ActiveAndroidUtils.generateStageSpecs();
        ActiveAndroidUtils.initializeDatabase(FindTheNotesApplication.getAppContext());
    }




    private void initSoundButtons() throws Exception {

        List<Integer> sounds = new ArrayList<>();

        sounds.add(R.raw.sound1);
        sounds.add(R.raw.sound2);
        sounds.add(R.raw.pickup_coin_1);
        sounds.add(R.raw.pickup_coin_2);
        sounds.add(Notes._LA);
        sounds.add(Notes._LA_SUS);
        sounds.add(Notes._SI);
        sounds.add(Notes._DO);
        sounds.add(Notes._DO_SUS);
        sounds.add(Notes._RE);
        sounds.add(Notes._RE_SUS);
        sounds.add(Notes._MI);
        sounds.add(Notes._FA);
        sounds.add(Notes._FA_SUS);
        sounds.add(Notes._SOL);
        sounds.add(Notes._SOL_SUS);

        if(XSoundPoolManager.getInstance() == null) {
            XSoundPoolManager.IXSoundPool ixSoundPool;
            ISoundPoolLoaded soundPoolCallback = new ISoundPoolLoaded() {
                @Override
                public void onSuccess() throws Exception {
                    Log.d("FTNAU", "AUDIO LOADED");
                    try {
                        XSoundPoolManager.getInstance().setPlaySound(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setSoundsLoaded(true);
                }
            };

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                ixSoundPool = new XLollipopSoundPool(FindTheNotesApplication.getAppContext(), soundPoolCallback, sounds);
            } else {
                ixSoundPool = new XGenericSoundPool(FindTheNotesApplication.getAppContext(), soundPoolCallback, sounds);
            }
            XSoundPoolManager.createInstance(ixSoundPool);
        }


    }

    public static long [] getSoundNoteDurationsInMillis(final int[] resourcesIdArray) {
        long [] durations = new long [resourcesIdArray.length];
        for(int i = 0; i < durations.length; i++) {
            durations[i] = getSoundNoteDurationInMillis(resourcesIdArray[i]);
        }
        return durations;
    }

    public static long getSoundNoteDurationInMillis(final int resourceId) {
        switch (resourceId) {
            case Notes._DO:
                return 1600;
            case Notes._DO_SUS:
                return 1600;
            case Notes._RE:
                return 1600;
            case Notes._RE_SUS:
                return 1600;
            case Notes._MI:
                return 1600;
            case Notes._FA:
                return 1600;
            case Notes._FA_SUS:
                return 1600;
            case Notes._SOL:
                return 1600;
            case Notes._SOL_SUS:
                return 1600;
            case Notes._LA:
                return 1600;
            case Notes._LA_SUS:
                return 1600;
            case Notes._SI:
                return 1600;
            default:
                return 0;
        }
    }

    public static final int[] SOUNDS = {
            Notes._LA,
            Notes._LA_SUS,
            Notes._SI,
            Notes._DO,
            Notes._DO_SUS,
            Notes._RE,
            Notes._RE_SUS,
            Notes._MI,
            Notes._FA,
            Notes._FA_SUS,
            Notes._SOL,
            Notes._SOL_SUS
    };


}
