package com.example.macbookair.laprichat.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.macbookair.laprichat.Activitys.UserActions.LoginActivity;
import com.example.macbookair.laprichat.R;
import com.example.macbookair.laprichat.Activitys.UserActions.RegisterActivity;

public class StartActivity extends AppCompatActivity {
    private Button btnCreate,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        find();
        onClickBtn();
    }

    private void onClickBtn() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void find() {

        btnCreate = (Button) findViewById(R.id.start_activity_create_btn);
        btnLogin = (Button) findViewById(R.id.start_activity_login_btn);
    }
}
