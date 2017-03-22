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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email, password;
    Button login;
    TextView signUp;
    String emailStr, passwordStr;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signUp = (TextView) findViewById(R.id.sign_up);

        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                getData();
                if (emailStr.equals("") || !emailStr.contains("@"))
                    ((TextInputLayout) findViewById(R.id.email_layout)).setError("Invalid email address");
                else if (passwordStr.length() < 6)
                    ((TextInputLayout) findViewById(R.id.password_layout)).setError("Invalid password");
                else {
                    firebaseAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "signInWithEmail:failed", task.getException());
                                        Toast.makeText(MainActivity.this, "Auth Failed",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
                break;
            case R.id.sign_up:
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getData(){
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
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
