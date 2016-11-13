package app.quickgigs.com.quickgigs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OpenJobs extends AppCompatActivity {

    public static final String TAG = "OpenJobs";
    public static final String OPEN_JOBS = "open-jobs";
    SwingBottomInAnimationAdapter jobAdapter;
    List<Job> listJobs;
    private ListView jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_bar_nav);
        BottomBarListener bottomBarListener = new BottomBarListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomBarListener);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(OPEN_JOBS);
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

        jobs = (ListView) findViewById(R.id.open_jobs_list);
        listJobs = new ArrayList<>();
        jobAdapter = new SwingBottomInAnimationAdapter(new JobAdapter(this, listJobs));
        jobAdapter.setAbsListView(jobs);
        jobs.setAdapter(jobAdapter);
        jobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(view.getContext())
                        .setCancelable(true)
                        .setTitle("Delete?")
                        .setMessage("You will not be able to undo this action.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                Log.d(TAG, Integer.toString(i));
                                DatabaseReference delRef = database.getReference(OPEN_JOBS + "/" + listJobs.get(i).getUid());
                                delRef.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Log.d(TAG, "job deleted");
                                    }
                                });
                                listJobs.remove(i);
                                jobAdapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }
}
