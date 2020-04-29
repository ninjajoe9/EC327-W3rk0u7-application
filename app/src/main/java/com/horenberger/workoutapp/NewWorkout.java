package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class NewWorkout extends AppCompatActivity {

    //displays selected exercises and sets/reps
    private ListView listview;
    //used for updating the displayed list
    ArrayAdapter<String>adapter;
    //container for the strings that listview displays
    ArrayList<String> selectedexercises = new ArrayList<String>();
    //holds the actual exercises classes we'll put into the workout object
    ArrayList<Exercise> exercises;

    int selectedIndex = -1;

    private EditText workoutname;
    private Button addexercise;
    private Button removeexercise;
    private Button startworkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_workout);

        workoutname = (EditText) findViewById(R.id.workoutname);
        startworkout = (Button) findViewById(R.id.startworkout);
        addexercise = (Button) findViewById(R.id.addexercise);
        removeexercise = (Button) findViewById(R.id.removeexercise);
        listview =  (ListView) findViewById(R.id.exerciselist);

        //prepping the list to empty
        initializeList();

        addexercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, go to the "AddExercise" menu
                if (v.getId() == R.id.addexercise) {
                    Intent addExercise = new Intent(NewWorkout.this, AddToWorkout.class);
                    //waits for the AddExercise activity to complete, then runs onActivityResult below
                    startActivityForResult(addExercise, 1);
                }
            }
        });

        startworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_name = workoutname.getText().toString();
                //if you click the button, go to the actual workout screen
                if (v.getId() == R.id.startworkout && exercises.size() > 0 && !new_name.equals("")) {
                    Intent doWorkout = new Intent(NewWorkout.this, DoWorkout.class);
                    //Creating a workout object and passing to the new activity
                    Workout curWorkout = new Workout(exercises, new_name);
                    doWorkout.putExtra("workout", (Serializable) curWorkout);
                    //Starting the new activity
                    startActivity(doWorkout);
                    //finishing the current activity since we won't be coming back here
                    finish();
                }
                if(new_name.equals(""))
                {
                    Toast.makeText(NewWorkout.this, getResources().getString(R.string.error_exercise_name), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        //selecting exercise by touching it
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
            }
        });

        removeexercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, delete the currently selected exercise
                if (v.getId() == R.id.removeexercise) {
                    if(selectedIndex != -1) {
                        selectedexercises.remove(selectedIndex);
                        exercises.remove(selectedIndex);
                    }
                        //reset the selection if necessary
                        if (selectedIndex >= selectedexercises.size()){
                            listview.setItemChecked(-1, true);
                            selectedIndex = -1;
                        }

                        refreshList();

                    }
            }
        });
    }

    //refreshes the list being displayed after new items were added
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            String[] results = data.getStringExtra("new_exercise").split("/");

            String new_exercise_name = results[0];
            Exercise new_exercise = new Exercise();
            new_exercise.load(NewWorkout.this, new_exercise_name);

            new_exercise.setSets(Integer.parseInt(results[1]));
            new_exercise.setReps(Integer.parseInt(results[2]));
            new_exercise.setWeight(Integer.parseInt(results[3]));
            exercises.add(new_exercise);
            selectedexercises.add(new_exercise.getName() + " Sets: " + results[1] + " Reps: " + results[2] + " Weight: " + results[3]);
            refreshList();
        }
    }

    //prepares the list of exercises to be displayed and object list
    void initializeList(){
        exercises = new ArrayList<Exercise>();
        selectedexercises = new ArrayList<String>();

        //telling the adapter to use our List as its content
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, selectedexercises);
        //connecting the adapter to the listview display
        listview.setAdapter(adapter);

    }

    //refreshes the List of exercise names and tells the adapter to refresh.
    void refreshList() {
        adapter.notifyDataSetChanged();
    }
}

