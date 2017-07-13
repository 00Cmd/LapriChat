package com.example.macbookair.laprichat.Activitys.UserActions;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.macbookair.laprichat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    private CircleImageView circleImageView;
    private TextView userName,userStatus;
    private Button btnChangeStatus,btnChangeImage;
    private static final int GALLERY_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        find();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("Name").getValue().toString();
                String image = dataSnapshot.child("Image").getValue().toString();
                String status = dataSnapshot.child("Status").getValue().toString();
                String thumb = dataSnapshot.child("Thub_img").getValue().toString();

                userName.setText(name);
                userStatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        onClickFunctions();
    }

    private void onClickFunctions() {
        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mStatus = userStatus.getText().toString();
                Intent i = new Intent(SettingsActivity.this,StatusActivity.class);
                i.putExtra("status",mStatus);
                startActivity(i);
            }
        });
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent,"Select image"),GALLERY_REQUEST);
            }
        });
    }

    private void find() {
        circleImageView = (CircleImageView)findViewById(R.id.activity_settings_circle_image_view);
        userName = (TextView) findViewById(R.id.activity_settings_username);
        userStatus = (TextView) findViewById(R.id.activity_settings_user_status);
        btnChangeStatus = (Button) findViewById(R.id.activity_settings_status_btn);
        btnChangeImage = (Button) findViewById(R.id.activity_settings_image_btn);
    }
}
