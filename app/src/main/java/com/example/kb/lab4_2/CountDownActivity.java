package com.example.kb.lab4_2;

import android.content.Intent;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class CountDownActivity extends AppCompatActivity {

    ArrayList<WorkoutPartBase> workoutParts = new ArrayList<>();
    private int iterator = 0;
    TextView text;
    TextToSpeech textToSpeech;
    TextView nextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        Intent intent = getIntent();
        workoutParts = (ArrayList<WorkoutPartBase>) intent.getSerializableExtra("workoutParts");
        text = findViewById(R.id.workout_text);
        nextText = findViewById(R.id.next_text);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                    speak();
                    while(textToSpeech.isSpeaking()){}
                } else {
                    Toast.makeText(CountDownActivity.this, "fubar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setCountdownText(workoutParts.get(0).getLength());
        setNextText(workoutParts.get(1).getLength());
        startTimer(workoutParts.get(iterator).getLength());
    }

    public void startTimer(int length) {
        speak();
        new CountDownTimer(length*1000, 1000) {
            @Override
            public void onFinish() {
                iterator++;
                if(iterator == workoutParts.size()) {
                    //workout done
                    textToSpeech.speak("Done", TextToSpeech.QUEUE_FLUSH, null);
                    Toast.makeText(CountDownActivity.this, "Workout done!", Toast.LENGTH_SHORT).show();
                    //text.setText("Workout\n0");
                    finish();
                    //return;
                } else {
                    //speak();

                    if(iterator == workoutParts.size()-1) {
                        nextText.setText("Last part");
                    } else {
                        setNextText(workoutParts.get(iterator+1).getLength());
                    }
                    startTimer(workoutParts.get(iterator).getLength());
                }
            }
            @Override
            public void onTick(long millisUntilFinished) {
                setCountdownText(millisUntilFinished/1000 + 1);
            }
        }.start();
    }

    public void setCountdownText(long timeLeft) {
        if(workoutParts.get(iterator).getType() == WorkoutPartBase.Type.PAUSE) {
            text.setText("Pause\n" + timeLeft);
        } else {
            text.setText("Workout\n" + timeLeft);
        }
    }
    public void setNextText(long timeLeft) {
        if(workoutParts.get(iterator+1).getType() == WorkoutPartBase.Type.PAUSE) {
            nextText.setText("Next up:\nPause\n" + timeLeft);
        } else {
            nextText.setText("Next up:\nWorkout\n" + timeLeft);
        }
    }

    public void speak() {
        if(workoutParts.get(iterator).getType() == WorkoutPartBase.Type.PAUSE) {
            textToSpeech.speak("Pause", TextToSpeech.QUEUE_FLUSH, null);
        } else {
            textToSpeech.speak("Workout", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
