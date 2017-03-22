package me.pulkitkumar.bealert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText fullName, email, password;
    String fullNameStr, emailStr, passwordStr;
    Button signUp;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String TAG = RegisterActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullName = (EditText) findViewById(R.id.full_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signUp = (Button) findViewById(R.id.sign_up);
        signUp.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void getData(){
        fullNameStr = fullName.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up:
                getData();
                if (emailStr.equals("") || !emailStr.contains("@"))
                    ((TextInputLayout) findViewById(R.id.email_layout)).setError("Invalid email address");
                else if (passwordStr.length() < 6)
                    ((TextInputLayout) findViewById(R.id.password_layout)).setError("Invalid password");
                else if(fullName.equals("")){
                    ((TextInputLayout) findViewById(R.id.full_name_layout)).setError("Invalid Name");
                }
                else {
                    firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Auth failed",
                                                Toast.LENGTH_SHORT).show();
                                        Log.d(TAG,task.getException().getMessage().toString());
                                    } else {
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
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
