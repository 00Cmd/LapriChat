package com.example.macbookair.laprichat.Activitys.UserActions;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.macbookair.laprichat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private TextInputLayout mText;
    private Button btnSave;
    private DatabaseReference mRef;
    private ProgressDialog mProgress;
    private Toolbar mToolbar;
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        find();
        onClickEvents();

    }

    private void onClickEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving changes...");
                mProgress.setMessage("Please wait while loading...");
                mProgress.show();

                String mStatus = mText.getEditText().getText().toString();

                mRef.child("Status").setValue(mStatus).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            mProgress.dismiss();
                            finish();
                        } else {
                            Toast.makeText(StatusActivity.this, "Something went wrong.....", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void find() {
        status = getIntent().getStringExtra("status");
        mText = (TextInputLayout)findViewById(R.id.activity_status_status_bar);
        mText.getEditText().setText(status);
        btnSave = (Button) findViewById(R.id.activity_status_save_btn);
        mToolbar = new Toolbar(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
