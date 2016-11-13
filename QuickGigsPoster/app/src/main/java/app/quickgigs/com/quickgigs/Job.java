package app.quickgigs.com.quickgigs;

/**
 * Created by jhurt on 11/13/16.
 */

public class Job {
    protected String uid;
    public String name;
    public int pay;
    public String details;


    public Job() {

    }

    public Job(String uid, String name, int pay, String details) {
        this.uid = uid;
        this.name = name;
        this.pay = pay;
        this.details = details;
    }

    public String getUid() {
        return this.uid;
    }
    public String getName() {
        return this.name;
    }
    public int getPay() {
        return this.pay;
    }
    public String getDetails() {
        return this.details;
    }
}
