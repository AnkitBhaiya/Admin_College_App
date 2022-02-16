package com.ankitsharma.admincollegeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ankitsharma.admincollegeapp.faculty.UpdateFaculty;
import com.ankitsharma.admincollegeapp.notice.DeleteNoticeActivity;

public class MainActivity extends AppCompatActivity {
    CardView uploadNotice,addGallery,addEbook,faculty,deleteNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        uploadNotice = findViewById (R.id.addNotice);
        addGallery = findViewById (R.id.addGallery);
        addEbook = findViewById (R.id.addEbook);
        faculty = findViewById (R.id.faculty);
        deleteNotice = findViewById (R.id.delete);
        uploadNotice.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (MainActivity.this,UploadNotice.class));
            }
        });
        addGallery.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this,UploadImage.class);
                startActivity (intent);
            }
        });
        addEbook.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (MainActivity.this,UploadPdfActivity.class));
            }
        });
        faculty.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (MainActivity.this, UpdateFaculty.class));
            }
        });
        deleteNotice.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (MainActivity.this, DeleteNoticeActivity.class));
            }
        });
    }

}