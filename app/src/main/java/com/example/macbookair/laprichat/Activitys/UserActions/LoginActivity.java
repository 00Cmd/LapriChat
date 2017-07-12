package com.example.macbookair.laprichat.Activitys.UserActions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.macbookair.laprichat.Activitys.MainActivity;
import com.example.macbookair.laprichat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputLayout inputMail,inputPass;
    private Button loginBtn;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        find();
        btnOnClick();
    }

    private void btnOnClick() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail ="";
                String pass = "";
                try {
                    mail = inputMail.getEditText().getText().toString();
                    pass = inputPass.getEditText().getText().toString();
                } catch (NullPointerException e) {
                    e.getMessage();
                }
                    if (!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass)) {
//                        Toast.makeText(LoginActivity.this, mail + " = mail, " + pass + " = pass", Toast.LENGTH_SHORT).show();
//
                        mProgressDialog.setTitle("Loging in user ...");
                        mProgressDialog.setMessage("Please wait while we find you'r account.");
                        mProgressDialog.setCanceledOnTouchOutside(true);
                        mProgressDialog.show();

                        signInUser(mail, pass);
                    } else {
                        Toast.makeText(LoginActivity.this, "All fields need to be filled!", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }


    private void signInUser(String eMail,String uPass) {
        mAuth.signInWithEmailAndPassword(eMail,uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    mProgressDialog.hide();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                } else {
                    mProgressDialog.hide();
                    Toast.makeText(LoginActivity.this, "Email or password is inncorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void find() {
        mProgressDialog = new ProgressDialog(this);
        inputMail = (TextInputLayout) findViewById(R.id.login_activity_email);
        inputPass = (TextInputLayout) findViewById(R.id.login_activity_pass);
        loginBtn = (Button) findViewById(R.id.login_activity_login_btn);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null ) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
