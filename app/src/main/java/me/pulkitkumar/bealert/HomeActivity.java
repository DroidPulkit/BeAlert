package me.pulkitkumar.bealert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button register, add, signOut;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String TAG = HomeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        register = (Button) findViewById(R.id.home_register);
        add = (Button) findViewById(R.id.home_add);
        signOut = (Button) findViewById(R.id.sign_out);
        register.setOnClickListener(this);
        add.setOnClickListener(this);
        signOut.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_register:
                Intent intent = new Intent(HomeActivity.this,ComplaintActivity.class);
                startActivity(intent);
                break;
            case R.id.home_add:
                Intent intent1 = new Intent(HomeActivity.this,EmergencyActivity.class);
                startActivity(intent1);
                break;
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                HomeActivity.this.finish();
                break;
        }
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

}
