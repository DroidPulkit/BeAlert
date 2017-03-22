package me.pulkitkumar.bealert;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EmergencyActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String TAG = EmergencyActivity.class.getName();
    private String userID;

    private static final int RESULT_PICK_CONTACT_1 = 35500;
    private static final int RESULT_PICK_CONTACT_2 = 45500;
    private static final int RESULT_PICK_CONTACT_3 = 55500;

    TextView contact1, contact2, contact3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        contact1 = (TextView) findViewById(R.id.contact_detail_1_name_number);
        contact2 = (TextView) findViewById(R.id.contact_detail_2_name_number);
        contact3 = (TextView) findViewById(R.id.contact_detail_3_name_number);

        contact1.setOnClickListener(this);
        contact2.setOnClickListener(this);
        contact3.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    userID = user.getUid().toString();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contact_detail_1_name_number:
                Intent contactPickerIntent1 = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent1, RESULT_PICK_CONTACT_1);
                break;

            case R.id.contact_detail_2_name_number:
                Intent contactPickerIntent2 = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent2, RESULT_PICK_CONTACT_2);
                break;

            case R.id.contact_detail_3_name_number:
                Intent contactPickerIntent3 = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent3, RESULT_PICK_CONTACT_3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT_1:
                    contactPicked_1(data);
                    break;
                case RESULT_PICK_CONTACT_2:
                    contactPicked_2(data);
                    break;
                case RESULT_PICK_CONTACT_3:
                    contactPicked_3(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     * @param data
     */
    private void contactPicked_1(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            // Set the value to the textviews
            contact1.setText(name + " - " + phoneNo);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name",name);
            hashMap.put("phone",phoneNo);
            databaseReference.child(userID).child("contact1").setValue(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void contactPicked_2(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            // Set the value to the textviews
            contact2.setText(name + " - " + phoneNo);
            HashMap<String, String> hashMap2 = new HashMap<>();
            hashMap2.put("name",name);
            hashMap2.put("phone",phoneNo);
            databaseReference.child(userID).child("contact2").setValue(hashMap2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void contactPicked_3(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            // Set the value to the textviews
            contact3.setText(name + " - " + phoneNo);
            HashMap<String, String> hashMap3 = new HashMap<>();
            hashMap3.put("name",name);
            hashMap3.put("phone",phoneNo);
            databaseReference.child(userID).child("contact3").setValue(hashMap3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
