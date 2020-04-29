package com.horenberger.workoutapp;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//This is the thing that holds all the data for a workout session
public class Workout implements Serializable {

    //This is the list of exercises. You'll increment the curReps and curSets of these guys
    //And you'll access the reps/sets of them as well
    public ArrayList<Exercise> exercises;
    private String name = "Default";

    Workout(){}

    Workout(ArrayList<Exercise> exercises, String name) {
        this.exercises = exercises;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void resetDone(){
        for(int i = 0; i < this.exercises.size(); i++){
            exercises.get(i).setDone(false);
        }
    }

    public int save(Context context){
        try {
            if (name.matches("[a-zA-Z ]+")) {
                File dir = new File(context.getFilesDir(), "Workouts");
                dir.mkdirs();
                FileOutputStream fos = new FileOutputStream(new File(dir, name.replace(' ', '_')));
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(this);
                os.close();
                fos.close();
            }
            else
                return -1;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return -2;
        } catch (IOException e){
            e.printStackTrace();
            return -2;
        }
        return 1;
    }

    public int load(Context context, String file){
        try {
            FileInputStream instream = new FileInputStream (new File(context.getFilesDir()+"/Workouts/"+file));
            ObjectInputStream objstream = new ObjectInputStream(instream);
            Workout newObject = (Workout) objstream.readObject();
            this.name = newObject.getName();
            this.exercises = new ArrayList<Exercise>();
            System.out.print("BEEGY BOOO");
            for(Exercise e : newObject.exercises){
                System.out.print("BEEGY BOOGY");
                this.exercises.add(e.clone());
            }
            objstream.close();
            instream.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return -2;
        } catch (IOException e){
            e.printStackTrace();
            return -2;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return -2;
        }
        return 1;
    }
}
