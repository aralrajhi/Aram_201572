package com.aram_201572.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.aram_201572.R;
import com.aram_201572.Models.Student;
import com.aram_201572.Adapters.StudentAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FirebaseListActivity extends AppCompatActivity {


    ListView listView;
    StudentAdapter itemsAdapter;
    List<Student> myList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_list);


        listView= findViewById(R.id.firebaseListview);
        myList= new ArrayList<>();
        itemsAdapter =new StudentAdapter(FirebaseListActivity.this,myList);
        listView.setAdapter(itemsAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("students");
        databaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Student student =snapshot.getValue(Student.class);
                myList.add(student);
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Student student =snapshot.getValue(Student.class);
                updateStudent(student);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Student student =snapshot.getValue(Student.class);
                removeStudent(student);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void removeStudent(Student student){

        for (int i=0; i<myList.size(); i++){
            if(student.getId().equals(myList.get(i).getId())){
                myList.remove(i);
            }
        }
        itemsAdapter.notifyDataSetChanged();
    }

    private void updateStudent(Student student){

        for (int i=0; i<myList.size(); i++){
            if(student.getId()==myList.get(i).getId()){
                myList.set(i,student);
            }
        }
        itemsAdapter.notifyDataSetChanged();
    }
}