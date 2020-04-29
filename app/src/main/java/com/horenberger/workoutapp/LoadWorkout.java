package com.horenberger.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoadWorkout extends AppCompatActivity {


    //displays existing exercises
    private ListView listview;
    //used for updating the displayed list
    ArrayAdapter<String> adapter;
    //used to pull the existing exercises from storage
    List<String> files = new ArrayList<String>();

    private Button startworkout;
    private Button deleteworkout;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_load_workout);

        startworkout = (Button) findViewById(R.id.startworkout);
        deleteworkout = (Button) findViewById(R.id.deleteworkout);
        listview =  (ListView) findViewById(R.id.exerciselist);
        initializeList();

        //selecting exercise by touching it
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
            }
        });


        startworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, do the delete thing
                if (v.getId() == R.id.startworkout) {
                    if(selectedIndex != -1) {
                        //Start workout
                        Intent doWorkout = new Intent(LoadWorkout.this, DoWorkout.class);
                        //Creating a workout object and passing to the new activity
                        Workout curWorkout = new Workout();
                        curWorkout.load(getBaseContext(), files.get(selectedIndex).replace(' ', '_'));
                        curWorkout.resetDone();
                        doWorkout.putExtra("workout", (Serializable) curWorkout);
                        //Starting the new activity
                        startActivity(doWorkout);
                        //finishing the current activity since we won't be coming back here
                        finish();
                    }
                }
            }
        });

        deleteworkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you click the button, do the delete thing
                if (v.getId() == R.id.deleteworkout) {
                    if(selectedIndex != -1) {
                        //Delete file
                        File dir = new File(getBaseContext().getFilesDir(), "Workouts");
                        File to_delete = new File(dir, files.get(selectedIndex).replace(' ', '_'));
                        to_delete.delete();
                        refreshList();
                    }
                    //reset the selection if necessary
                    if (selectedIndex >= files.size()) {
                        listview.setItemChecked(-1, true);
                        selectedIndex = -1;
                        refreshList();
                    }

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

    }

    //refreshes the list being displayed after new items were added
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        refreshList();
    }

    //prepares the list of workouts to be displayed
    void initializeList(){
        //grabbing the files
        File dir = new File(getBaseContext().getFilesDir(), "Workouts");
        files = new ArrayList<String>();
        //putting them into a string array
        String[] cur_files = dir.list();
        //replacing spaces with _ and putting the strings into a List (for ease)
        for(int i = 0; i < cur_files.length; i++) {
            cur_files[i] = cur_files[i].replace('_', ' ');
            files.add(cur_files[i]);
            System.out.print(cur_files[i]);
        }

        //telling the adapter to use our List as its content
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, files);
        //connecting the adapter to the display
        listview.setAdapter(adapter);

    }

    //refreshes the List of workout names and tells the adapter to refresh. Similar to initializeList
    void refreshList() {
        File dir = new File(getBaseContext().getFilesDir(), "Workouts");
        String[] cur_files = dir.list();
        files.clear();
        for(int i = 0; i < cur_files.length; i++) {
            cur_files[i] = cur_files[i].replace('_', ' ');
            files.add(cur_files[i]);
            System.out.print(cur_files[i]);
        }
        adapter.notifyDataSetChanged();
    }
}
