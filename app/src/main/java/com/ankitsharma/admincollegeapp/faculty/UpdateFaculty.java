package com.ankitsharma.admincollegeapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ankitsharma.admincollegeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView csDepartment,mechanicalDepartment,electricalDepartment,electronicsDepartment,civilDepartment,physics,chemistry;
    private LinearLayout csNodata,civilNodata,mechanicalNodata,electricalNodata,electronicNodata,physicsnoData,chemistrynoData;
    private List<TeacherData> list1,list2,list3,list4,list5,list6,list7;
    private DatabaseReference reference,dbRef;
    private TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_update_faculty);
        fab = findViewById (R.id.fab);
        reference =FirebaseDatabase.getInstance ().getReference ().child ("Teachers");
        csDepartment = findViewById (R.id.CsDepartment);
        mechanicalDepartment = findViewById (R.id.MechanicalDepartment);
        electricalDepartment = findViewById (R.id.ElectricalDepartment);
        electronicsDepartment = findViewById (R.id.ElectronicsDepartment);
        civilDepartment = findViewById (R.id.CivilDepartment);
        physics = findViewById (R.id.PhysicsDepartment);
        chemistry = findViewById (R.id.ChemistryDepartment);
        csNodata = findViewById (R.id.csNodata);
        civilNodata = findViewById (R.id.civilNodata);
        mechanicalNodata = findViewById (R.id.mechanicalNodata);
        electricalNodata = findViewById (R.id.electricalNodata);
        electronicNodata = findViewById (R.id.electronicsNodata);
        physicsnoData = findViewById (R.id.physicsNodata);
        chemistrynoData = findViewById (R.id.chemistryNodata);
        csDepartment();
        civilDepartment();
        mechanicalDepartment();
        electricalDepartment();
        electronicsDepartment();
        Physics();
        Chemistry();
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (UpdateFaculty.this,AddTeacher.class));
            }
        });
    }

    private void csDepartment() {

        dbRef = reference.child ("Computer Science");
        dbRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              list1 = new ArrayList<> ();
              if (!snapshot.exists ()){
                  csNodata.setVisibility (View.VISIBLE);
                  csDepartment.setVisibility (View.GONE);
              }else {
                  csNodata.setVisibility (View.GONE);
                  csDepartment.setVisibility (View.VISIBLE);
                 for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                     TeacherData data = dataSnapshot.getValue (TeacherData.class);
                     list1.add (data);
                 }
                 csDepartment.setHasFixedSize (true);
                 csDepartment.setLayoutManager (new LinearLayoutManager (UpdateFaculty.this));
                 adapter = new TeacherAdapter (list1,UpdateFaculty.this,"Computer Science");
                 csDepartment.setAdapter (adapter);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (UpdateFaculty.this, error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });

    }
    private void civilDepartment() {

        dbRef = reference.child ("Civil");
        dbRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2 = new ArrayList<> ();
                if (!snapshot.exists ()){
                    civilNodata.setVisibility (View.VISIBLE);
                    civilDepartment.setVisibility (View.GONE);
                }else {
                    civilNodata.setVisibility (View.GONE);
                    civilDepartment.setVisibility (View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue (TeacherData.class);
                        list2.add (data);
                    }
                    civilDepartment.setHasFixedSize (true);
                    civilDepartment.setLayoutManager (new LinearLayoutManager (UpdateFaculty.this));
                    adapter = new TeacherAdapter (list2,UpdateFaculty.this,"Civil");
                    civilDepartment.setAdapter (adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (UpdateFaculty.this, error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });

    }
    private void mechanicalDepartment() {

        dbRef = reference.child ("Mechanical");
        dbRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3 = new ArrayList<> ();
                if (!snapshot.exists ()){
                    mechanicalNodata.setVisibility (View.VISIBLE);
                    mechanicalDepartment.setVisibility (View.GONE);
                }else {
                    mechanicalNodata.setVisibility (View.GONE);
                    mechanicalDepartment.setVisibility (View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue (TeacherData.class);
                        list3.add (data);
                    }
                    mechanicalDepartment.setHasFixedSize (true);
                    mechanicalDepartment.setLayoutManager (new LinearLayoutManager (UpdateFaculty.this));
                    adapter = new TeacherAdapter (list3,UpdateFaculty.this,"Mechanical");
                    mechanicalDepartment.setAdapter (adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (UpdateFaculty.this, error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });

    }
    private void electricalDepartment() {

        dbRef = reference.child ("Electrical");
        dbRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4 = new ArrayList<> ();
                if (!snapshot.exists ()){
                    electricalNodata.setVisibility (View.VISIBLE);
                    electricalDepartment.setVisibility (View.GONE);
                }else {
                    electricalNodata.setVisibility (View.GONE);
                    electricalDepartment.setVisibility (View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue (TeacherData.class);
                        list4.add (data);
                    }
                    electricalDepartment.setHasFixedSize (true);
                    electricalDepartment.setLayoutManager (new LinearLayoutManager (UpdateFaculty.this));
                    adapter = new TeacherAdapter (list4,UpdateFaculty.this,"Electrical");
                    electricalDepartment.setAdapter (adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (UpdateFaculty.this, error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });

    }
    private void electronicsDepartment() {

        dbRef = reference.child ("Electronics and Communication");
        dbRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list5 = new ArrayList<> ();
                if (!snapshot.exists ()){
                    electronicNodata.setVisibility (View.VISIBLE);
                    electronicsDepartment.setVisibility (View.GONE);
                }else {
                    electronicNodata.setVisibility (View.GONE);
                    electronicsDepartment.setVisibility (View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue (TeacherData.class);
                        list5.add (data);
                    }
                   electronicsDepartment.setHasFixedSize (true);
                    electronicsDepartment.setLayoutManager (new LinearLayoutManager (UpdateFaculty.this));
                    adapter = new TeacherAdapter (list5,UpdateFaculty.this,"Electronics and Communication");
                    electronicsDepartment.setAdapter (adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (UpdateFaculty.this, error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });

    }
    private void Physics() {

        dbRef = reference.child ("Physics");
        dbRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list6 = new ArrayList<> ();
                if (!snapshot.exists ()){
                    physicsnoData.setVisibility (View.VISIBLE);
                    physics.setVisibility (View.GONE);
                }else {
                    physicsnoData.setVisibility (View.GONE);
                    physics.setVisibility (View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue (TeacherData.class);
                        list6.add (data);
                    }
                    physics.setHasFixedSize (true);
                    physics.setLayoutManager (new LinearLayoutManager (UpdateFaculty.this));
                    adapter = new TeacherAdapter (list6,UpdateFaculty.this,"Physics");
                    physics.setAdapter (adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (UpdateFaculty.this, error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });

    }
    private void Chemistry() {

        dbRef = reference.child ("Chemistry");
        dbRef.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list7 = new ArrayList<> ();
                if (!snapshot.exists ()){
                    chemistrynoData.setVisibility (View.VISIBLE);
                    chemistry.setVisibility (View.GONE);
                }else {
                   chemistrynoData.setVisibility (View.GONE);
                    chemistry.setVisibility (View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue (TeacherData.class);
                        list7.add (data);
                    }
                    chemistry.setHasFixedSize (true);
                    chemistry.setLayoutManager (new LinearLayoutManager (UpdateFaculty.this));
                    adapter = new TeacherAdapter (list7,UpdateFaculty.this,"Chemistry");
                    chemistry.setAdapter (adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (UpdateFaculty.this, error.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        });

    }

}