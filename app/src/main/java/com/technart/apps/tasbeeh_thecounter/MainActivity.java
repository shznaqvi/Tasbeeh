package com.technart.apps.tasbeeh_thecounter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

    Button addButton;
    Button resetButton;
    Button muteButton;
    TextView scoreText;
    int counter = 0;
    Vibrator vibe;
    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    EditText limitText;
    Boolean mMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMode = false;


        setContentView(R.layout.activity_home);

        addButton = findViewById(R.id.addButton);

        resetButton = findViewById(R.id.resetButton);
        muteButton = findViewById(R.id.setMode);
        scoreText = findViewById(R.id.txtCount);
        limitText = findViewById(R.id.countLimit);


        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        muteButton.setOnClickListener(this);


        scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 110);
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
        Typeface digitTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/Digital.ttf");
        scoreText.setTypeface(digitTypeface);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }


    static final String SCORE_TEXT = "scoreText";


    @Override
    public void onClick(View v) {
        String cLimit = limitText.getText().toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibe.vibrate(VibrationEffect.createOneShot(110, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {

            vibe.vibrate(20);

        }

        if (v == addButton) {


                counter++;
                scoreText.setText(Integer.toString(counter));

            //vibe.vibrate(1000);
            if (mMode) {
                toneG.startTone(ToneGenerator.TONE_CDMA_PRESSHOLDKEY_LITE, 20);
            }

                if ((counter >= 0) && (counter == Integer.parseInt(cLimit))) {
                    scoreText.setTextColor(Color.RED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        vibe.vibrate(500);

                    }
                    if (mMode) {
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 2000);
                        toneG.startTone(ToneGenerator.TONE_SUP_ERROR, 2000);
                    }
                    addButton.setClickable(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        vibe.vibrate(500);

                    }
                }

        }
        if (v == resetButton) {

            counter=0;
            addButton.setClickable(true);
            scoreText.setText(Integer.toString(counter));
            scoreText.setTextColor(Color.GREEN);
        }

        if (v == muteButton) {

            if (mMode) {
                scoreText.setText("Sound OFF");
                muteButton.setText("Sound OFF");
                scoreText.setTextColor(Color.GRAY);
                muteButton.setTextColor(Color.GRAY);
                mMode = false;
                Toast.makeText(this, "Silent OFF", Toast.LENGTH_SHORT).show();
            } else if (!mMode) {

                scoreText.setText("Sound ON");
                muteButton.setText("Sound ON");
                scoreText.setTextColor(Color.GREEN);
                muteButton.setTextColor(Color.GREEN);
                mMode = true;
                Toast.makeText(this, "Silent ON", Toast.LENGTH_SHORT).show();

            }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt(SCORE_TEXT, counter);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        counter = savedInstanceState.getInt(SCORE_TEXT);

        String cLimit = limitText.getText().toString();

        if ((counter >= 0) && (counter == Integer.parseInt(cLimit))) {
            scoreText.setTextColor(Color.RED);
            addButton.setClickable(false);
        }

    }
}
