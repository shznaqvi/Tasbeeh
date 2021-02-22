package com.technart.apps.tasbeeh_thecounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    TextView addButton;
    /* Button resetButton;
     Button muteButton;
     TextView scoreText;*/
    int counter = 0;
    Vibrator vibe;
    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    //  EditText limitText;
    Boolean sound;
    Boolean vibrate;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;
    String cLimit;
    LinearLayout options;
    private boolean lockClock;
    private final int interval = 3500; // 1 Second
    private Handler handler = new Handler();
    private Runnable runnable;
    ImageButton btnlockClock;
    private Animation blinkanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        prefEditor = sharedPreferences.edit();
        String name = sharedPreferences.getString("signature", "");
        super.onCreate(savedInstanceState);
lockClock = false;
        // mMode = false;


        setContentView(R.layout.activity_home);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        addButton = findViewById(R.id.addButton);
        options = findViewById(R.id.options);
        btnlockClock = findViewById(R.id.lockClock);
        addButton.setHeight(width - 48);
        //muteButton = findViewById(R.id.setMode);
/*        scoreText = findViewById(R.id.txtCount);
        limitText = findViewById(R.id.countLimit);*/


        addButton.setOnClickListener(this);
/*        resetButton.setOnClickListener(this);
        muteButton.setOnClickListener(this);*/


        //  scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 110);
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
        Typeface digitTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/Digital.ttf");
        addButton.setTypeface(digitTypeface);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        runnable = new Runnable(){
            public void run() {
              //  Toast.makeText(MainActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
                btnlockClock.setBackground(getResources().getDrawable(R.drawable.ic_lock));
                lockClock = false;
            }
        };

        blinkanimation= new AlphaAnimation(1, 0.5f); // Change alpha from fully visible to invisible
        blinkanimation.setDuration(110); // duration - half a second
        blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkanimation.setRepeatCount(1); // Repeat animation infinitely
        blinkanimation.setRepeatMode(Animation.REVERSE);
    }


    static final String SCORE_TEXT = "scoreText";



    @Override
    public void onClick(View v) {
        //   cLimit = limitText.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibe.vibrate(VibrationEffect.createOneShot(110, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {

            vibe.vibrate(20);

        }

        if(v.getId() == R.id.lockClock){

            btnlockClock.setBackground(getResources().getDrawable(R.drawable.ic_lock_clock));
            lockClock = true;
            handler.postAtTime(runnable, System.currentTimeMillis()+interval);
            handler.postDelayed(runnable, interval);
        }

        if (v.getId() == R.id.reset) {
            prefEditor.putString("counterlimit", "0");
            counter = 0;
            options.setVisibility(View.INVISIBLE);
            addButton.setTextColor(Color.GREEN);
            addButton.setText("0");
            addButton.setClickable(true);
            prefEditor.apply();
        }

        if (v.getId() == R.id.refresh) {
            counter = 0;
            options.setVisibility(View.INVISIBLE);
            addButton.setTextColor(Color.GREEN);
            addButton.setText("0");
            addButton.setClickable(true);


        }

        if (v == addButton) {

            addButton.startAnimation(blinkanimation);
            counter++;
            addButton.setText(Integer.toString(counter));

            //vibe.vibrate(1000);
            if (sound) {
                toneG.startTone(ToneGenerator.TONE_CDMA_PRESSHOLDKEY_LITE, 20);
            }
            Log.d("TAG", "onClick: counter " + counter);
            Log.d("TAG", "onClick: cLimit " + cLimit);
            if ((counter >= 0) && (counter == Integer.parseInt(cLimit))) {
                options.setVisibility(View.VISIBLE);
                addButton.setTextColor(Color.RED);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {

                    vibe.vibrate(500);

                }
                if (sound) {
                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 2000);
                    toneG.startTone(ToneGenerator.TONE_SUP_ERROR, 2000);
                }
                addButton.setClickable(false);

                if (vibrate) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        vibe.vibrate(500);

                    }
                }
            }

        }



    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(SCORE_TEXT, counter);

        super.onSaveInstanceState(savedInstanceState);
    }

/*    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        counter = savedInstanceState.getInt(SCORE_TEXT);

        String cLimit = limitText.getText().toString();

        if ((counter >= 0) && (counter == Integer.parseInt(cLimit))) {
            addButton.setTextColor(Color.RED);
            addButton.setClickable(false);
        }

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        if(lockClock) {
            switch (item.getItemId()) {
                case R.id.action_settings:
                    intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_reset:
                    counter = 0;
                    break;

            }
        } else {
            Toast.makeText(this, "Please unlock to use menu.", Toast.LENGTH_SHORT).show();
        }
            return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        cLimit = sharedPreferences.getString("counterlimit", "0");
        sound = sharedPreferences.getBoolean("sound", true);
        vibrate = sharedPreferences.getBoolean("vibrate", true);

        Log.d("TAG", "onResume counterLimit: " + sharedPreferences.getString("counterlimit", ""));
        Log.d("TAG", "onResume sounds: " + sharedPreferences.getBoolean("sounds", false));
        Log.d("TAG", "onResume vibrate: " + sharedPreferences.getBoolean("vibrate", false));

 /*
            if (!sound) {
                scoreText.setText("Sound OFF");
                muteButton.setText("Sound OFF");
                scoreText.setTextColor(Color.GRAY);
                muteButton.setTextColor(Color.GRAY);
              //  mMode = false;
                Toast.makeText(this, "Silent OFF", Toast.LENGTH_SHORT).show();
            } else if (sound) {

                scoreText.setText("Sound ON");
                muteButton.setText("Sound ON");
                scoreText.setTextColor(Color.GREEN);
                muteButton.setTextColor(Color.GREEN);
            //    mMode = true;
                Toast.makeText(this, "Silent ON", Toast.LENGTH_SHORT).show();

            }

       if (v == resetButton) {

            counter=0;
            addButton.setClickable(true);
            scoreText.setText(Integer.toString(counter));
            scoreText.setTextColor(Color.GREEN);
        }*/

    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        counter = savedInstanceState.getInt(SCORE_TEXT);

        String myString = savedInstanceState.getString("MyString");
    }
}
