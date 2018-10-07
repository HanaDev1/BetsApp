package com.example.hanaalalawi.betsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    TextView toSignUp;
    EditText userEmailInput, userPassInput;
    String userEmail, userPassword;
    FirebaseAuth mAuth;
    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        toSignUp = (TextView) findViewById(R.id.toSignUpPage);
        userEmailInput = (EditText) findViewById(R.id.userEmailInput);
        userPassInput = (EditText) findViewById(R.id.userPassInput);
        signInBtn = (Button) findViewById(R.id.userSignInBtn);

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(SignInActivity.this,UserSignUpActivity.class));
            }
        });

        userEmail = userEmailInput.getText().toString().trim();
        userPassword = userPassInput.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    public void signIn(){

        if (TextUtils.isEmpty(userEmail)){
            userEmailInput.setError("Enter your email");
            userEmailInput.requestFocus();
        }
        if(TextUtils.isEmpty(userPassword)){
            userPassInput.setError("Enter password");
            userPassInput.requestFocus();
        }
        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Please check your email or password",Toast.LENGTH_LONG);
                }else{
                    //
                    Intent toHomePage = new Intent(SignInActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user_email", userEmail);
                    toHomePage.putExtras(bundle);
                    startActivity(toHomePage);
                }
            }
        });
    }
}
