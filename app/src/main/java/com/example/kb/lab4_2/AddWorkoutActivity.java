package com.example.kb.lab4_2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        findViewById(R.id.add_button).setOnClickListener(this);
    }

    /*
    public void onRadioButtonClicked(View view) {
        Boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.pause_radio_button:
                if(checked) {
                    //ADD PAUSE TO WORKOUT
                }
                break;
            case R.id.work_out_radio_button:
                if(checked) {
                    //ADD WORKOUT
                }
                break;
        }
    }*/

    @Override
    public void onClick(View v) {
        int time =  Integer.parseInt(((EditText)findViewById(R.id.time_edit_text)).getText().toString());
        if(v.getId() == R.id.add_button) {
            if(((RadioButton)findViewById(R.id.pause_radio_button)).isChecked()) {
                if(time > 0) {
                    //add pause
                    PausePart pause = new PausePart(time);
                    returnData(pause);
                } else {
                    Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
                }
            } else if(((RadioButton)findViewById(R.id.work_out_radio_button)).isChecked()) {
                if(time > 0) {
                    //add workout
                    WorkoutPart workOut = new WorkoutPart(time);
                    returnData(workOut);
                } else {
                    Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void returnData(WorkoutPartBase data) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("WORKOUT", data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
