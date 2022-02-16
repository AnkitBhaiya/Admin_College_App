package com.ankitsharma.admincollegeapp.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ankitsharma.admincollegeapp.NoticeData;
import com.ankitsharma.admincollegeapp.R;
import com.ankitsharma.admincollegeapp.UploadImage;
import com.ankitsharma.admincollegeapp.UploadNotice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTeacher extends AppCompatActivity {

    private ImageView addTeacherImage;
    private EditText addTeacherName,addTeacherEmail,addTeacherPost;
    private Spinner addTeacherCategory;
    private Button addTeacherButton;
    private final int REQ = 1;
    private Bitmap bitmap;
    private String category;
    private String name,email,post,downloadUrl="";
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_add_teacher);
        addTeacherImage = findViewById (R.id.addTeacherImage);
        addTeacherName = findViewById (R.id.teacherName);
        addTeacherEmail = findViewById (R.id.teacherEmail);
        addTeacherPost = findViewById (R.id.teacherPost);
        addTeacherCategory = findViewById (R.id.addTeacherCategory);
        addTeacherButton = findViewById (R.id.addTeacherButton);
        addTeacherImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        storageReference = FirebaseStorage.getInstance ().getReference ();
        databaseReference =FirebaseDatabase.getInstance ().getReference ();
        progressDialog = new ProgressDialog (this);
        String[] items = new String[]{"Select Department","Civil ","Mechanical","Electrical","Electronics and Communication","Computer Science","Physics","Chemistry"};
        addTeacherCategory.setAdapter (new ArrayAdapter<String> (this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items));

        addTeacherCategory.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addTeacherCategory.getSelectedItem ().toString ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addTeacherButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {

        name = addTeacherName.getText ().toString ();
        email = addTeacherEmail.getText ().toString ();
        post = addTeacherPost.getText ().toString ();

        if(name.isEmpty ()){
            addTeacherName.setError ("Empty");
            addTeacherName.requestFocus ();
        }else if(email.isEmpty ()){
            addTeacherEmail.setError ("Empty");
            addTeacherEmail.requestFocus ();
        }else if(post.isEmpty ()){
            addTeacherPost.setError ("Empty");
            addTeacherPost.requestFocus ();
        }else if (category.equals ("Select Department")){
            Toast.makeText (this, "Please provide the Department of the Teacher...", Toast.LENGTH_SHORT).show ();
        }else if (bitmap == null){
            progressDialog.setMessage ("Uploading");
            progressDialog.show ();
           insertData();
        }else{
            progressDialog.setMessage ("Uploading");
            progressDialog.show ();
            insertImage();
        }
    }

    private void insertData() {
        databaseReference = databaseReference.child ("Teachers");
        final String uniqueKey = databaseReference.push ().getKey ();
        TeacherData teacherData = new TeacherData (name,email,post,downloadUrl,uniqueKey);
        databaseReference.child (uniqueKey).setValue (teacherData).addOnSuccessListener (new OnSuccessListener<Void> () {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss ();
                Toast.makeText (AddTeacher.this, "Data uploaded successfully....", Toast.LENGTH_SHORT).show ();
            }
        }).addOnFailureListener (new OnFailureListener () {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss ();
                Toast.makeText (AddTeacher.this, "Something went wrong.....", Toast.LENGTH_SHORT).show ();
            }
        });

    }

    private void insertImage() {
        progressDialog.setMessage ("Uploading.....");
        progressDialog.show ();
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        bitmap.compress (Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg =baos.toByteArray ();
        final StorageReference filePath;
        filePath = storageReference.child ("Teachers").child (finalImg+"jpg");
        final UploadTask uploadTask =filePath.putBytes (finalImg);
        uploadTask.addOnCompleteListener (AddTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful ()){
                    uploadTask.addOnSuccessListener (new OnSuccessListener<UploadTask.TaskSnapshot> () {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl ().addOnSuccessListener (new OnSuccessListener<Uri> () {
                                @Override
                                public void onSuccess(Uri uri) {
                                    progressDialog.dismiss ();
                                    downloadUrl = String.valueOf (uri);
                                    insertData ();
                                }
                            });
                        }
                    });
                }else {
                    progressDialog.dismiss ();
                    Toast.makeText (AddTeacher.this, "Something went wrong....", Toast.LENGTH_SHORT).show ();
                }
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
            addTeacherImage.setImageBitmap (bitmap);
        }

    }
}