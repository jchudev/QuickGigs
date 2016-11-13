package app.quickgigs.com.quickgigs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClaimedJobs extends AppCompatActivity {

    public static final String TAG = "ClaimedJobs";
    private static final String CLAIMED_JOBS = "claimed-jobs";
    SwingBottomInAnimationAdapter jobAdapter;
    List<Job> listJobs;
    private ListView jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claimed_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_bar_nav);
        BottomBarListener bottomBarListener = new BottomBarListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomBarListener);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(CLAIMED_JOBS);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listJobs.add(dataSnapshot.getValue(Job.class));
                jobAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                listJobs.remove(dataSnapshot.getValue(Job.class));
                jobAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        jobs = (ListView) findViewById(R.id.claimed_jobs_list);
        listJobs = new ArrayList<>();
        jobAdapter = new SwingBottomInAnimationAdapter(new JobAdapter(this, listJobs));
        jobAdapter.setAbsListView(jobs);
        jobs.setAdapter(jobAdapter);
        jobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int jobIndex, long l) {
                new AlertDialog.Builder(view.getContext())
                        .setCancelable(true)
                        .setTitle("Completed - '" + listJobs.get(jobIndex).name + "'")
                        .setMessage("If you were unhappy with the service provided you will not be charged.")
                        .setNegativeButton("Refund", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int x) {
                                new AlertDialog.Builder(view.getContext())
                                        .setTitle("What went wrong?")
                                        .setSingleChoiceItems(new String[]{"Unhelpful", "Too slow", "Mean"}, 0,
                                                              new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int y) {
                                                // Cool
                                            }
                                        })
                                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int z) {
                                                Toast.makeText(view.getContext(), "Thank you for your feedback",
                                                               Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, Integer.toString(jobIndex));
                                                DatabaseReference delRef = database.getReference(CLAIMED_JOBS + "/" + listJobs.get(jobIndex).getUid());
                                                delRef.removeValue(new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                        Log.d(TAG, "job deleted");
                                                    }
                                                });
                                                listJobs.remove(jobIndex);
                                                jobAdapter.notifyDataSetChanged();
                                            }
                                        }).create().show();
                            }
                        })
                        .setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                Log.d(TAG, Integer.toString(jobIndex));
                                DatabaseReference delRef = database.getReference(CLAIMED_JOBS + "/" + listJobs.get(jobIndex).getUid());
                                delRef.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Log.d(TAG, "job deleted");
                                    }
                                });
                                listJobs.remove(jobIndex);
                                jobAdapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
