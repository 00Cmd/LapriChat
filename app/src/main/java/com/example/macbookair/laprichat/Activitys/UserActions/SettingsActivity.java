package com.example.macbookair.laprichat.Activitys.UserActions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbookair.laprichat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static android.R.attr.bitmap;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private DatabaseReference mRef;
    private CircleImageView circleImageView;
    private TextView userName,userStatus;
    private Button btnChangeStatus,btnChangeImage;
    private ProgressDialog mProgressDialog;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorageRef;
    private Uri imgUri = null;


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
                if (!name.equals("default")) {
                    Picasso.with(SettingsActivity.this).load(image).placeholder(R.drawable.default_avatar).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference();
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

//                CropImage.activity(imgUri)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(SettingsActivity.this);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imgUri = data.getData();
            CropImage.activity(imgUri)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mProgressDialog.setTitle("Uploading image...");
                mProgressDialog.setMessage("Please wait while image is being uploaded....");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                imgUri = result.getUri();



                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                File thumbFilePath = new File(imgUri.getPath());
                Bitmap thumbBitmap = null;
                try {
                     thumbBitmap = new Compressor(this).
                            setMaxWidth(200).setMaxHeight(200).setQuality(75).
                            compressToBitmap(thumbFilePath);
                } catch (IOException e) {
                    Log.d(TAG, "Settings Activity -> onActivityResult ->  " + e.getMessage());
                }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    final byte[] thumbByte = baos.toByteArray();

                StorageReference profileImageStorage = mStorageRef.child("profile_images").child(uid + ".jpg");
                final StorageReference thumbImageStorage = mStorageRef.child("profile_images").child("thumb_img").child(uid + ".jpg");


                profileImageStorage.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            final String downloadUrl = task.getResult().getDownloadUrl().toString();
                            UploadTask uploadTask = thumbImageStorage.putBytes(thumbByte);
                            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumbTask) {
                                    String thumbUrl = task.getResult().getDownloadUrl().toString();
                                    if (thumbTask.isSuccessful()) {
                                        Map updateHashMap = new HashMap();
                                        updateHashMap.put("Image",downloadUrl);
                                        updateHashMap.put("Thumb_img",thumbUrl);
                                        mRef.updateChildren(updateHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    mProgressDialog.dismiss();
                                                    Toast.makeText(SettingsActivity.this, "Success..", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    mProgressDialog.dismiss();
                                                    Toast.makeText(SettingsActivity.this, "Failed uploading", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(SettingsActivity.this, "Not working", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void find() {
        mProgressDialog = new ProgressDialog(SettingsActivity.this);
        circleImageView = (CircleImageView)findViewById(R.id.activity_settings_circle_image_view);
        userName = (TextView) findViewById(R.id.activity_settings_username);
        userStatus = (TextView) findViewById(R.id.activity_settings_user_status);
        btnChangeStatus = (Button) findViewById(R.id.activity_settings_status_btn);
        btnChangeImage = (Button) findViewById(R.id.activity_settings_image_btn);
    }


}
