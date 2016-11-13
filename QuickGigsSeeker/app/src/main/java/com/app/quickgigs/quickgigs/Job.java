package com.app.quickgigs.quickgigs;

/**
 * Created by Jonathon Chu on 11/13/2016.
 */

public class Job {

    public String name;
    public int pay;
    public String address;
    public String details;
    public String uid;

    public Job(){

    }

    public Job(String uid, String name, int pay){
        this.uid = uid;
        this.name = name;
        this.pay = pay;
    }

    public Job(String uid, String name, int pay, String details){
        this.uid = uid;
        this.name = name;
        this.pay = pay;
        this.details = details;
    }

}
