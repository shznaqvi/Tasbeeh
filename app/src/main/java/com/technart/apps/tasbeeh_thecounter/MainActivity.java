package com.technart.apps.tasbeeh_thecounter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;




public class MainActivity extends Activity implements OnClickListener {

    Button btn1;
    Button btn2;
    TextView scoreText;
    int counter = 0;
    Vibrator vibe;
    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    EditText limitText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home);

        btn1 = (Button) findViewById(R.id.addButton);

        btn2 = (Button) findViewById(R.id.resetButton);
        scoreText = (TextView) findViewById(R.id.txtCount);
        limitText = (EditText) findViewById(R.id.countLimit);


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);


        scoreText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 110);
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
        Typeface digitTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/Digital.ttf");
        scoreText.setTypeface(digitTypeface);
    }


    static final String SCORE_TEXT = "scoreText";

    @Override
    public void onClick(View v) {
        String cLimit = limitText.getText().toString();

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v == btn1) {


                counter++;
                scoreText.setText(Integer.toString(counter));

            vibe.vibrate(100);
                toneG.startTone(ToneGenerator.TONE_PROP_BEEP, 200);


                if ((counter >= 0) && (counter == Integer.parseInt(cLimit))) {
                    scoreText.setTextColor(Color.RED);
                    vibe.vibrate(1000);
                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 2000);
                    toneG.startTone(ToneGenerator.TONE_SUP_ERROR, 2000);
                    btn1.setClickable(false);
                    vibe.vibrate(1000);
                }

        }
        if (v == btn2) {
            counter=0;
            btn1.setClickable(true);
            scoreText.setText(Integer.toString(counter));
            scoreText.setTextColor(Color.GREEN);
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
            btn1.setClickable(false);
        }

    }
}
