package com.example.dell.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;
import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class CreateAccount extends AppCompatActivity {

    EditText name, email, contact_no, password, confirmpassword;

    Button create_acc;

    Firebase firebase;

    //    Validation

    AwesomeValidation mAwesomeValidation;

    //    FireBase creating account
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact_no = findViewById(R.id.contact_no);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirm_password);

        create_acc = findViewById(R.id.create_acc_btn);

        mAuth = FirebaseAuth.getInstance();



        mAwesomeValidation = new AwesomeValidation(BASIC);
        AwesomeValidation.disableAutoFocusOnFirstFailure();

        mAwesomeValidation.addValidation(this, R.id.name, "[a-zA-Z\\s]+", R.string.name);
        mAwesomeValidation.addValidation(this, R.id.contact_no, "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$", R.string.contact);
        mAwesomeValidation.addValidation(this, R.id.email, android.util.Patterns.EMAIL_ADDRESS, R.string.email);
// to validate the confirmation of another field
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        mAwesomeValidation.addValidation(this, R.id.password, regexPassword, R.string.password);
// to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
        mAwesomeValidation.addValidation(this, R.id.confirm_password, R.id.password, R.string.not_match_password);


        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAwesomeValidation.validate()){
                    Toast.makeText(CreateAccount.this," thai gyu halo OK ",Toast.LENGTH_SHORT).show();
                    createAccount();
                }
            }
        });

    }

    private void createAccount() {
        String c_email = email.getText().toString().trim();
        String c_password = password.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(c_email, c_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(CreateAccount.this, "Authentication Successful.",Toast.LENGTH_SHORT).show();
                    storeData();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(CreateAccount.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = "";
        if (user != null) {
            // Name, email address, and profile photo Url
            String user_email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();
            Map<String,Object> userInfo = new HashMap<>();
            userInfo.put("Name",name.getText().toString().trim());
            userInfo.put("Email",email.getText().toString().trim());
            userInfo.put("Contact",contact_no.getText().toString().trim());
            userInfo.put("Password",password.getText().toString().trim());

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://expense-manager-9b616.firebaseio.com/"+uid+"");
            databaseReference.child("Personal Detail").setValue(userInfo);
            Toast.makeText(this,"THAI GYU BC...",Toast.LENGTH_LONG).show();
        }


    }
}
