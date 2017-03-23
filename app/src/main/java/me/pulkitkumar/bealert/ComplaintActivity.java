package me.pulkitkumar.bealert;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ComplaintActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String TAG = ComplaintActivity.class.getName();
    private String userId;

    EditText name, college, date, culprit;
    String nameStr, collegeStr, dateStr, culpritStr;

    Button sumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        name = (EditText) findViewById(R.id.complain_name);
        college = (EditText) findViewById(R.id.complain_college);
        date = (EditText) findViewById(R.id.complain_date);
        culprit = (EditText) findViewById(R.id.complain_culprit);

        sumbit = (Button) findViewById(R.id.complain_submit);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("ragging");
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    userId = user.getUid();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.complain_submit:
                        getData();
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("name",nameStr);
                        hashMap.put("college",collegeStr);
                        hashMap.put("date",dateStr);
                        hashMap.put("culprit",culpritStr);
                        databaseReference.child(userId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ComplaintActivity.this, "Registered complain!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void getData(){
        nameStr = name.getText().toString().trim();
        collegeStr = college.getText().toString().trim();
        dateStr = date.getText().toString().trim();
        culpritStr = culprit.getText().toString().trim();
    }
}
