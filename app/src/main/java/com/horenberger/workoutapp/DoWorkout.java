package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DoWorkout extends AppCompatActivity {

    private Workout curWorkout;

    //displays selected exercises and sets/reps
    private ListView listview;

    private Button finish;

    //used for updating the displayed list
    WorkoutAdapter adapter;
    //container for the strings that listview displays
    List<String> curExercises = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_workout);

        //The workout object with all the exercises you just selected and the desired reps/sets
        curWorkout = (Workout) getIntent().getSerializableExtra("workout");

        finish = (Button) findViewById(R.id.finish);
        listview =  (ListView) findViewById(R.id.workoutdisplay);

        //prepping the list to empty
        initializeList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> curAdapter, View arg1, int position, long id) {
                //flip the "Done" status of an exercise when it is clicked
                curWorkout.exercises.get(position).setDone(!curWorkout.exercises.get(position).getDone());
                adapter.notifyDataSetChanged();
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.finish) {
                    //Saving completed workouts would go here if we implement it
                    //ensure we have a folder created for saving workouts
                    File savedworkout = new File("Workouts");
                    savedworkout =  new File(savedworkout, curWorkout.getName().replace(' ', '_'));
                    if (!savedworkout.exists()) {
                        curWorkout.save(getBaseContext());
                    }

                    Toast.makeText(DoWorkout.this, getResources().getString(R.string.workout_complete), Toast.LENGTH_LONG).show();
                    Intent finishWorkout = new Intent(DoWorkout.this, WorkoutComplete.class);
                    startActivity(finishWorkout);
                    finish();
                }
            }
        });

    }

    //prepares the list of exercises to be displayed
    void initializeList(){
        //grabbing the exercises
        curExercises = new ArrayList<String>();
        System.out.print("Grabbed exercise names\n");
        //replacing spaces with _ and putting the strings into a List (for ease)
        for(int i = 0; i < curWorkout.exercises.size(); i++) {
            curExercises.add(curWorkout.exercises.get(i).getName() + " Sets: " +  curWorkout.exercises.get(i).getSets() + " Reps: " + curWorkout.exercises.get(i).getReps() + " Weight: " + curWorkout.exercises.get(i).getWeight());
        }

        //telling the adapter to use our List as its content
        adapter = new WorkoutAdapter(this, curWorkout.exercises);
        //connecting the adapter to the display
        listview.setAdapter(adapter);

    }
}
