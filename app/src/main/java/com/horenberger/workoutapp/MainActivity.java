package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.File;

//This is the main menu and starting location of the app

public class MainActivity extends AppCompatActivity implements OnClickListener {

    //initializing the three buttons
    private Button newworkout;
    private Button manageexercises;
    private Button loadworkout;

    //things to do at the moment this menu opens
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ensure we have a folder created for saving exercises
        File folder = new File("Exercises");
        boolean create_result= true;
        if (!folder.exists()) {
            create_result = folder.mkdirs();
            create_basic_exercises();
        }
        //ensure we have a folder created for saving workouts
        folder = new File("Workouts");
        create_result= true;
        if (!folder.exists()) {
            create_result = folder.mkdirs();
        }

        //preparing the buttons
        newworkout = (Button) findViewById(R.id.startnewworkout);
        manageexercises = (Button) findViewById(R.id.manageexercises);
        loadworkout = (Button) findViewById(R.id.loadworkout);

        newworkout.setOnClickListener(this);
        manageexercises.setOnClickListener(this);
        loadworkout.setOnClickListener(this);

    }

    //handling button clicks
    @Override
    public void onClick(View v) {
        //takes you to the "ManageExercises" menu
        if(v.getId() == R.id.manageexercises) {
            Intent exerciseManagerActivity = new Intent(MainActivity.this, ManageExercises.class);
            startActivity(exerciseManagerActivity);
        }
        if(v.getId() == R.id.startnewworkout) {
            Intent newWorkoutActivity = new Intent(MainActivity.this, NewWorkout.class);
            startActivity(newWorkoutActivity);
        }
        if(v.getId() == R.id.loadworkout){
            Intent loadWorkoutActivity = new Intent(MainActivity.this, LoadWorkout.class);
            startActivity(loadWorkoutActivity);
        }
    }

    private void create_basic_exercises(){
        //Simple exercise objects get made and saved here.
        Exercise test_exercise1 = new Exercise("BENCH PRESS");
        test_exercise1.save(getBaseContext());

        Exercise test_exercise2 = new Exercise("SQUATS");
        test_exercise2.save(getBaseContext());

        Exercise test_exercise3 = new Exercise("BICEP CURLS");
        test_exercise3.save(getBaseContext());

        Exercise test_exercise4 = new Exercise("LUNGES");
        test_exercise4.save(getBaseContext());

        Exercise test_exercise5 = new Exercise("DEAD LIFTS");
        test_exercise5.save(getBaseContext());

        Exercise test_exercise6 = new Exercise("BENT OVER ROW");
        test_exercise6.save(getBaseContext());

        Exercise test_exercise7 = new Exercise("PULL DOWNS");
        test_exercise7.save(getBaseContext());

        Exercise test_exercise8 = new Exercise("LEG PRESS");
        test_exercise8.save(getBaseContext());

        Exercise test_exercise9 = new Exercise("HAMMER CURLS");
        test_exercise9.save(getBaseContext());

        Exercise test_exercise10 = new Exercise("CALF RAISES");
        test_exercise10.save(getBaseContext());


    }

}
