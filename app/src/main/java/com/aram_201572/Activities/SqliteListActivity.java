package com.aram_201572.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aram_201572.Database.DBHelper;
import com.aram_201572.R;
import com.aram_201572.Models.Student;
import com.aram_201572.Adapters.StudentAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class SqliteListActivity extends AppCompatActivity {


    ListView listView;
    StudentAdapter itemsAdapter;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_list);

        db= new DBHelper(SqliteListActivity.this);
        listView= findViewById(R.id.listview);
        List<Student> list = db.getAllStudents();
        itemsAdapter =new StudentAdapter(SqliteListActivity.this,list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toasty.info(SqliteListActivity.this, list.get(i).getName()+" "+list.get(i).getSurname(), Toast.LENGTH_SHORT, true).show();
           }
        });

    }
}