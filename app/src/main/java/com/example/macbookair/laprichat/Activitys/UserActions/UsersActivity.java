package com.example.macbookair.laprichat.Activitys.UserActions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbookair.laprichat.Models.Users;
import com.example.macbookair.laprichat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {
    private static final String TAG = "UsersActivity";

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        find();
    }

    private void find() {
//        userName = (TextView)findViewById(R.id.user_name);
//        userStatus = (TextView)findViewById(R.id.user_status);
        mToolbar = (Toolbar) findViewById(R.id.activity_users_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mRecyclerView = (RecyclerView) findViewById(R.id.activity_users_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>
                ( Users.class,
                R.layout.user_layout,
                UsersViewHolder.class,
                mRef )
        {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(model.getThumb_img(),getApplicationContext());

                final String uid = getRef(position).getKey();

                mRecyclerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(UsersActivity.this,ProfileActivity.class);
                        i.putExtra("user_id",uid);
                        startActivity(i);
                    }
                });

            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView mText = (TextView)mView.findViewById(R.id.user_name);
            mText.setText(name);
        }

        public void setStatus(String status) {
            TextView mText = (TextView)mView.findViewById(R.id.user_status);
            mText.setText(status);
        }

        public void setImage(String thumbUrl, Context ctx) {
            CircleImageView mImage = (CircleImageView) mView.findViewById(R.id.user_avatar);
            Picasso.with(ctx).load(thumbUrl).placeholder(R.drawable.default_avatar).into(mImage);
        }

    }
}
