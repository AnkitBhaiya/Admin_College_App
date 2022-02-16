package com.ankitsharma.admincollegeapp.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ankitsharma.admincollegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {

    private static final int REQ=1;
    private ImageView updateTeacherImage;
    private EditText updateTeacherName,updateTeacherEmail,updateTeacherPost;
    private Button updateTeacherButton,deleteTeacherButton;
    private Bitmap bitmap = null;
    private String name,email,image,post;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String downloadUrl,category,uniquekey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_update_teacher);
        category = getIntent ().getStringExtra ("category");
        uniquekey = getIntent ().getStringExtra ("key");
        name = getIntent ().getStringExtra ("name");
        email = getIntent ().getStringExtra ("email");
        image = getIntent ().getStringExtra ("image");
        post = getIntent ().getStringExtra ("post");
       databaseReference =FirebaseDatabase.getInstance ().getReference ();
        storageReference =FirebaseStorage.getInstance ().getReference ();
        updateTeacherImage = findViewById (R.id.updateTeacherImage);
        updateTeacherName = findViewById (R.id.updateTeacherName);
        updateTeacherEmail = findViewById (R.id.updateTeacherEmail);
        updateTeacherPost = findViewById (R.id.updateTeacherPost);
        updateTeacherButton = findViewById (R.id.updateTeacherButton);
        deleteTeacherButton = findViewById (R.id.deleteTeacherButton);

        try {
            Picasso.get ().load (image).into (updateTeacherImage);
        }catch (Exception e){
            e.printStackTrace ();
        }
        updateTeacherName.setText (name);
        updateTeacherEmail.setText (email);
        updateTeacherPost.setText (post);
      updateTeacherImage.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              openGallery ();
          }
      });
      updateTeacherButton.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick(View v) {
          checkValidation();
          }
      });
      deleteTeacherButton.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              deleteData();
          }
      });
    }
    private void openGallery() {
        Intent pickImage = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult (pickImage,REQ);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK){
            Uri uri = data.getData ();
            try {
                bitmap = MediaStore.Images.Media.getBitmap (getContentResolver (),uri);
            } catch (IOException e) {
                e.printStackTrace ();
            }
            updateTeacherImage.setImageBitmap (bitmap);
        }

    }
    private void checkValidation() {

        name = updateTeacherName.getText ().toString ();
        email = updateTeacherEmail.getText ().toString ();
        post = updateTeacherPost.getText ().toString ();

        if(name.isEmpty ()){
            updateTeacherName.setError ("Empty");
            updateTeacherName.requestFocus ();
        }else if(email.isEmpty ()){
            updateTeacherEmail.setError ("Empty");
            updateTeacherEmail.requestFocus ();
        }else if(post.isEmpty ()){
            updateTeacherPost.setError ("Empty");
            updateTeacherPost.requestFocus ();
        }else if (bitmap == null){

            updateData(image);
        }else{
            updateImage();
        }
    }

    private void updateData(String s) {
        HashMap hp = new HashMap ();
        hp.put ("name",name);
        hp.put ("email",email);
        hp.put ("post",post);
        hp.put ("image" ,s);

        databaseReference.child (category).child (uniquekey).updateChildren (hp).addOnSuccessListener (new OnSuccessListener () {
            @Override
            public void onSuccess(Object o) {
                Intent intent = new Intent (UpdateTeacherActivity.this,UpdateFaculty.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity (intent);
                Toast.makeText (UpdateTeacherActivity.this, "Teacher Updated Successfully.....", Toast.LENGTH_SHORT).show ();
            }
        }).addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText (UpdateTeacherActivity.this, "Something went wrong.....", Toast.LENGTH_SHORT).show ();
            }
        });
    }
    private void deleteData(){
        databaseReference.child (category).child (uniquekey).removeValue ()
                .addOnCompleteListener (new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent (UpdateTeacherActivity.this,UpdateFaculty.class);
                        intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity (intent);
                        Toast.makeText (UpdateTeacherActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show ();
                    }
                }).addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText (UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show ();
            }
        });
    }
    private void updateImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        bitmap.compress (Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg =baos.toByteArray ();
        final StorageReference filePath;
        filePath = storageReference.child ("Teachers").child (finalImg+"jpg");
        final UploadTask uploadTask =filePath.putBytes (finalImg);
        uploadTask.addOnCompleteListener (UpdateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful ()){
                    uploadTask.addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> () {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf (uri);
                                    updateData (downloadUrl);
                                }
                            });
                        }
                    });
                }else {
                    Toast.makeText (UpdateTeacherActivity.this, "Something went wrong....", Toast.LENGTH_SHORT).show ();
                }
            }
        });

    }
}