package app.quickgigs.com.quickgigs;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static app.quickgigs.com.quickgigs.OpenJobs.OPEN_JOBS;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private String madeKey;

    private EditText mName;
    private EditText mPay;
    private EditText mDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_bar_nav);
        BottomBarListener bottomBarListener = new BottomBarListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomBarListener);

        mName = (EditText) findViewById(R.id.input_name);
        mPay = (EditText) findViewById(R.id.input_pay);
        mDetails = (EditText) findViewById(R.id.input_details);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(OPEN_JOBS);

        Button createJob = (Button) findViewById(R.id.btn_create_job);
        createJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String pay = mPay.getText().toString();
                String details = mDetails.getText().toString();
                if (name.length() == 0 || name.length() > 16 || pay.length() == 0 ||
                        details.length() == 0 || details.length() > 140) {
                    Toast.makeText(view.getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference newPostRef = myRef.push();
                Job job = new Job(newPostRef.getKey(), name, Integer.parseInt(pay), details);
                myRef.child(job.getUid()).setValue(job);
                Toast.makeText(view.getContext(), "Job created", Toast.LENGTH_SHORT).show();
                // Clear the fields out after creating job
                mName.setText("");
                mPay.setText("");
                mDetails.setText("");
            }
        });

//        Button create = (Button) findViewById(R.id.create_job);
//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "job created");
//                DatabaseReference newPostRef = myRef.push();
//                newPostRef.setValue(new Job("Wipe Butt", 10));
//                madeKey = newPostRef.getKey();
//                Log.d(TAG, newPostRef.getKey());
//            }
//        });
//        Button delete = (Button) findViewById(R.id.delete_job);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "job deleted");
//                DatabaseReference delRef = database.getReference("test-jobs/"+madeKey);
//                delRef.removeValue(new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        Log.d(TAG, "Removed that value");
//                    }
//                });
//            }
//        });

        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }
}
