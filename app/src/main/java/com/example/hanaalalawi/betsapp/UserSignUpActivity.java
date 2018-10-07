package com.example.hanaalalawi.betsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static android.widget.Toast.LENGTH_SHORT;

public class UserSignUpActivity extends AppCompatActivity {

    //initialization views
    EditText userEmail, userPassword, userName;
    TextView toSignInPage;
    Button signUpBtn;

    //To authenticate user
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        //declare views
        userEmail = (EditText) findViewById(R.id.userEmailText);
        userName = (EditText) findViewById(R.id.userNameText);
        userPassword = (EditText) findViewById(R.id.userPassText);
        toSignInPage = (TextView) findViewById(R.id.toSignInPage);
        signUpBtn = (Button) findViewById(R.id.userSignUp);

        toSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(UserSignUpActivity.this,SignInActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userEmailStr = userEmail.getText().toString().trim();
                final String userPassStr = userPassword.getText().toString().trim();
                final String userNameStr = userName.getText().toString().trim();

                //Check user entries, email, pass, name
                if (TextUtils.isEmpty(userNameStr)) {
                    userName.setError("Enter your name ");
                    userName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(userEmailStr)) {
                    userEmail.setError("Enter your email ");
                    userEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(userPassStr)) {
                    userPassword.setError("Enter password ");
                    userPassword.requestFocus();
                    return;
                }
                //to create user account by using (Email and Password authentication)
                auth.createUserWithEmailAndPassword(userEmailStr, userPassStr).addOnCompleteListener(UserSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if user sign in successfully
                            //push username and email to user's table
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").push();
                            //Create user table
                            reference.child("full_name").setValue(userNameStr);
                            reference.child("user_email").setValue(userEmailStr);

                            Intent intent = new Intent(UserSignUpActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            //Sending user email and Password to HomePage by using bundle
                            bundle.putString("user_email", userEmailStr);
                            bundle.putString("user_name",userNameStr);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(UserSignUpActivity.this, "Authentication failed." + task.getException(),

                                    LENGTH_SHORT).show();
                            Log.e("the error", String.valueOf(task.getException()));
                        } else {
                            finish();
                        }
                    }
                });

            }
        });
    }
}
