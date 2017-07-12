package com.example.macbookair.laprichat.Activitys.UserActions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout inputName,inputMail,inputPass;
    private Button btnCreate;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        find();
        btnOnClick();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Register account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void btnOnClick() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getEditText().getText().toString();
                String mail = inputMail.getEditText().getText().toString();
                String pass = inputPass.getEditText().getText().toString();
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mail)  && !TextUtils.isEmpty(pass)) {
                    mProgressDialog.setTitle("Registrating user ...");
                    mProgressDialog.setMessage("Please wait while we create you'r Account.");
                    mProgressDialog.setCanceledOnTouchOutside(true);
                    mProgressDialog.show();
                    registerUser(name, mail, pass);
                }
                Toast.makeText(RegisterActivity.this, "All fields have to be filled!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(String userName, String userMail, String userPass) {
        mAuth.createUserWithEmailAndPassword(userMail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                    mProgressDialog.hide();
                } else {
                    mProgressDialog.hide();
                    Toast.makeText(RegisterActivity.this, "Error .....", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void find() {
        mProgressDialog = new ProgressDialog(this);
        mToolbar = (Toolbar) findViewById(R.id.activity_register_toolbar);
        inputName = (TextInputLayout) findViewById(R.id.register_activity_name_field);
        inputMail = (TextInputLayout) findViewById(R.id.register_activity_mail_field);
        inputPass= (TextInputLayout) findViewById(R.id.register_activity_pass_field);
        btnCreate = (Button) findViewById(R.id.register_activity_btn_create);
    }
}
