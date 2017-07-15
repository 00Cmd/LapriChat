package com.example.macbookair.laprichat.Activitys.UserActions;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbookair.laprichat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView userName,userStatus,userFirendCount;
    private Button btnFriendRequest;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        find();
        String uid = getIntent().getStringExtra("user_id");
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mProgressDialog.setTitle("Loading user data ...");
        mProgressDialog.setMessage("Please wait while data is being loaded...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String displayName = dataSnapshot.child("Name").getValue().toString();
                String displayStatus = dataSnapshot.child("Status").getValue().toString();
                String displayImage = dataSnapshot.child("Image").getValue().toString();

                userName.setText(displayName);
                userStatus.setText(displayStatus);

                Picasso.with(ProfileActivity.this).load(displayImage).placeholder(R.drawable.default_avatar).into(mImageView);
                mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void find() {
        mProgressDialog = new ProgressDialog(this);
        mImageView = (ImageView)findViewById(R.id.activity_profile_image_view);
        userName = (TextView) findViewById(R.id.activity_profile_username);
        userStatus = (TextView) findViewById(R.id.activity_profile_status);
        userFirendCount = (TextView) findViewById(R.id.activity_profile_friends_count);
        btnFriendRequest = (Button)findViewById(R.id.activity_profile_request_btn);


    }
}
