package com.technart.apps.tasbeeh_thecounter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn1;
    Button btn2;
    Button btn3;
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

        btn1 = findViewById(R.id.addButton);

        btn2 = findViewById(R.id.resetButton);
        btn3 = findViewById(R.id.setMode);
        limitText = findViewById(R.id.countLimit);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);


        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
        Typeface digitTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/Digital.ttf");
        btn1.setTypeface(digitTypeface);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }


    static final String SCORE_TEXT = "scoreText";


    @Override
    public void onClick(View v) {
        String cLimit = limitText.getText().toString();
        btn1.setTextSize(96);

        if (cLimit.equals("110") && (counter == 4 || counter == 11 || counter == 13)) {
            Toast.makeText(this, "This Digital Tasbeeh is developed by Hassan Naqvi. (shznaqvi@gmail.com) \r\n 2019", Toast.LENGTH_LONG).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibe.vibrate(VibrationEffect.createOneShot(55, 2));
        } else {

            // vibe.vibrate(55);

        }

        if (v == btn1) {


                counter++;
            btn1.setText(Integer.toString(counter));

            vibe.vibrate(110);
            if (!mMode) {
                toneG.startTone(ToneGenerator.TONE_PROP_BEEP, 200);
            }

                if ((counter >= 0) && (counter == Integer.parseInt(cLimit))) {
                    btn1.setTextColor(Color.RED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibe.vibrate(VibrationEffect.createOneShot(55, 2));
                    } else {

                        // vibe.vibrate(55);

                    }
                    if (!mMode) {
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 2000);
                        toneG.startTone(ToneGenerator.TONE_SUP_ERROR, 2000);
                    }
                    btn1.setClickable(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibe.vibrate(VibrationEffect.createOneShot(55, 2));
                    } else {

                        // vibe.vibrate(55);

                    }
                }

        }
        if (v == btn2) {

            counter=0;
            btn1.setClickable(true);
            btn1.setText(Integer.toString(counter));
            btn1.setTextColor(Color.GREEN);
        }

        if (v == btn3) {

            if (mMode) {
                btn1.setTextSize(48);
                btn1.setText("Sound ON");
                btn1.setTextColor(Color.GREEN);
                v.setBackgroundResource(R.drawable.ic_volume_up_black_24dp);

                v.setBackgroundResource(R.drawable.ic_volume_mute_black_24dp);

                mMode = false;
                Toast.makeText(this, "Silent OFF", Toast.LENGTH_SHORT).show();
            } else if (!mMode) {
                v.setBackgroundResource(R.drawable.ic_volume_mute_black_24dp);

                btn1.setText("Sound OFF");
                btn1.setTextSize(48);
                btn1.setTextColor(Color.GRAY);
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
            btn1.setTextColor(Color.RED);
            btn1.setClickable(false);
        }
    }
}
