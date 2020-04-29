package com.horenberger.workoutapp;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Exercise implements java.io.Serializable{
    //exercise name
    String name;

    //maximum weight ever
    int max_weight;

    //preferred reps and sets
    int sets = 0;
    int reps = 0;
    int weight = 0;

    //current reps and sets
    int curSets = 0;
    int curReps = 0;
    int curWeight = 0;

    //is exercise completed
    boolean done = false;

    //constructor
    public Exercise() {}
    //constructor
    public Exercise(String name) {
        this.name = name;
    }

    //a billion getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCurSets() {return curSets;}

    public void setCurSets(int curSets) {this.curSets = curSets;}

    public int getCurReps() {return curReps;}

    public void setCurReps(int curReps) {this.curReps = curReps;}

    public int getCurWeight() {return curWeight;}

    public void setCurWeight(int curReps) {this.curWeight = curWeight;}

    //reset cur reps and sets
    public void resetCur() {this.curReps = 0; this.curSets = 0; this.curWeight = 0;}

    public boolean getDone(){return done;}

    public void setDone(boolean done) {
        this.done = done;
    }

    //incrementers for reps and sets
    public void incReps(){
        this.reps += 1;
    }
    public void decReps(){
        if(this.reps > 0)
            this.reps -= 1;
    }

    public void incSets(){
        this.sets += 1;
    }
    public void decSets(){
        if(this.sets > 0)
            this.sets -= 1;
    }

    public void incWeight(){
        this.weight += 1;
    }
    public void decWeight(){
        if(this.weight > 0)
            this.weight -= 1;
    }

    public Exercise clone(){
        Exercise e = new Exercise();
        e.setDone(this.done);
        e.setSets(this.sets);
        e.setReps(this.reps);
        e.setWeight(this.weight);
        e.setName(this.name);

        return e;
    }

    public int save(Context context){
        try {
            if (name.matches("[a-zA-Z ]+")) {
                File dir = new File(context.getFilesDir(), "Exercises");
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
            FileInputStream instream = new FileInputStream (new File(context.getFilesDir()+"/Exercises/"+file));
            ObjectInputStream objstream = new ObjectInputStream(instream);
            Exercise newObject = (Exercise) objstream.readObject();
            name = newObject.getName();
            reps = newObject.getReps();
            sets = newObject.getSets();
            weight = newObject.getWeight();
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
