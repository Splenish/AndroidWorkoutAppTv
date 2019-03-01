package com.example.kb.lab4_2;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int ADD_NEW_WORKOUT_REQ_ID = 666;

    ArrayList<WorkoutPartBase> parts = new ArrayList<>();

    ListView listView = null;

    private int totalLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        findViewById(R.id.start_button).setOnClickListener(this);
        findViewById(R.id.new_workout_button).setOnClickListener(this);
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.new_workout_menu_item) {
            Intent addWorkoutActivity = new Intent(this, AddWorkoutActivity.class);
            startActivityForResult(addWorkoutActivity, ADD_NEW_WORKOUT_REQ_ID);
        }
        return true;
    }
    */

    @Override
    protected void onResume() {
        super.onResume();
        WorkoutArrayAdapter adapter = new WorkoutArrayAdapter(this, parts);
        TextView no_parts = findViewById(R.id.no_parts_text);
        TextView time_display = findViewById(R.id.total_time_text);
        if(parts.size() > 0) {
            no_parts.setVisibility(View.INVISIBLE);
        } else {
            no_parts.setVisibility(View.VISIBLE);
        }
        totalLength = 0;
        for(int i = 0; i < parts.size(); i++) {
            totalLength += parts.get(i).getLength();
        }
        time_display.setText("Total length " + (int)java.lang.Math.floor((double)totalLength/60) + " minutes and " + totalLength%60 + " seconds");
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NEW_WORKOUT_REQ_ID && resultCode == RESULT_OK) {
            WorkoutPartBase workout = (WorkoutPartBase)data.getSerializableExtra("WORKOUT");
            parts.add(workout);
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.start_button) {
            if(parts.size() == 0) {
                Toast.makeText(this, "Add parts", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, CountDownActivity.class);
                intent.putExtra("workoutParts", parts);
                startActivity(intent);
            }


        } else if(v.getId() == R.id.new_workout_button) {
            Intent addWorkoutActivity = new Intent(this, AddWorkoutActivity.class);
            startActivityForResult(addWorkoutActivity, ADD_NEW_WORKOUT_REQ_ID);
        }
    }
}
