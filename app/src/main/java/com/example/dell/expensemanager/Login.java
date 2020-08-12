package com.example.dell.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class Login extends AppCompatActivity {

    Button login;
    EditText email, password;
    CheckBox keep_Me_Login;
    TextView createAccount;


    //    Validation
    AwesomeValidation mAwesomeValidation;

//    Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccount = findViewById(R.id.createAccount);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        keep_Me_Login = findViewById(R.id.keepMeLogin);

        login = findViewById(R.id.login_Button);

        firebaseAuth = FirebaseAuth.getInstance();


        mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(this, R.id.email, android.util.Patterns.EMAIL_ADDRESS, R.string.email);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        mAwesomeValidation.addValidation(this, R.id.password, regexPassword, R.string.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAwesomeValidation.validate()){

                    if (keep_Me_Login.isChecked()){
                        //                    if keep me login then insert data to database Table ...
                        startKeepSignIn();
                        finish();
                    } else {
                        startSignIn();
                        Toast.makeText(Login.this,"thai che rah jo.. ;)",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


//        for create Account..
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,CreateAccount.class));
            }
        });
    }

    private void startSignIn() {
        String useremail = email.getText().toString();
        String userpass = password.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(useremail, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(Login.this, Dashboard.class));
                } else {
                    Toast.makeText(Login.this, "Invalid credentials !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startKeepSignIn() {
        String useremail = email.getText().toString();
        String userpass = password.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(useremail, userpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                    If task successful then store data in SQLite .................
                    SplashScreen.sqLite_login.insertData(email.getText().toString().trim(),password.getText().toString().trim()); // inserting data to TABLE
                    startActivity(new Intent(Login.this, Dashboard.class));
                } else {
                    Toast.makeText(Login.this, "Invalid credentials !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
