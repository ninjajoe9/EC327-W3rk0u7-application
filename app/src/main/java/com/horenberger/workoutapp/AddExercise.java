package com.horenberger.workoutapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class AddExercise extends Activity {

    private EditText exercisename;
    private Button finishadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exercisename = (EditText) findViewById(R.id.newexercisename);

        finishadd = (Button) findViewById(R.id.finishnewexercise);

        finishadd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.finishnewexercise) {
                    //Gets inputted name
                    String new_name = exercisename.getText().toString();

                    if(new_name.equals(""))
                    {
                        Toast.makeText(AddExercise.this, getResources().getString(R.string.error_exercise_name), Toast.LENGTH_LONG).show();
                        return;
                    }

                    Exercise newExercise = new Exercise(new_name);
                    int result = newExercise.save(getBaseContext());
                    if (result == 1)
                        Toast.makeText(AddExercise.this, getResources().getString(R.string.add_exercise_confirmation), Toast.LENGTH_LONG).show();
                    if (result == -1)
                        Toast.makeText(AddExercise.this, getResources().getString(R.string.add_exercise_bad_chars), Toast.LENGTH_LONG).show();
                    if (result == -2)
                        Toast.makeText(AddExercise.this, getResources().getString(R.string.general_error), Toast.LENGTH_LONG).show();

                        finish();
                }
            }
        });

    }
}
