package com.app.quickgigs.quickgigs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private ListView listView;
    private DatabaseReference mDatabase;
    private List<Job> listJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Open Jobs");
        listView = (ListView) findViewById(R.id.job_items);
        listView.setOnItemClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("open-jobs");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listJobs = new ArrayList<Job>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Job j = userSnapshot.getValue(Job.class);
                    j.address = "7311 Decker Ln, Austin, TX 78724";
                    listJobs.add(j);
                }
                SwingBottomInAnimationAdapter adapter =
                        new SwingBottomInAnimationAdapter(new JobAdapter(MainActivity.this, listJobs));
                adapter.setAbsListView(listView);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, JobActivity.class);
        Job c = listJobs.get(i);
        intent.putExtra("NAME", c.name);
        intent.putExtra("UID", c.uid);
        intent.putExtra("PAY", c.pay);
        intent.putExtra("ADDRESS", c.address);
        intent.putExtra("DETAILS", c.details);
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listJobs = new ArrayList<Job>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Job j = userSnapshot.getValue(Job.class);
                    j.address = "7311 Decker Ln, Austin, TX 78724";
                    listJobs.add(j);
                }
                SwingBottomInAnimationAdapter adapter =
                        new SwingBottomInAnimationAdapter(new JobAdapter(MainActivity.this, listJobs));
                adapter.setAbsListView(listView);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
