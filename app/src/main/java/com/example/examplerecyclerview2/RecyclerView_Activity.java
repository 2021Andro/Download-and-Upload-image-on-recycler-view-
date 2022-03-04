package com.example.examplerecyclerview2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.example.examplerecyclerview2.Classes.PersonDemo;
import com.example.examplerecyclerview2.Classes.RecyclerEventListener_1;
import com.example.examplerecyclerview2.RecAdapter.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecyclerView_Activity extends AppCompatActivity  implements RecyclerEventListener_1 {

    private DatabaseReference myRef;
    private StorageReference myStorage;

    private RecyclerView rvList;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PersonDemo> personDemoArrayList = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        rvList = findViewById(R.id.rvPerson);
        rvList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);


        rvList.setLayoutManager(layoutManager);


        myStorage = FirebaseStorage.getInstance().getReference("Upload");
        myRef = FirebaseDatabase.getInstance().getReference("Upload");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                    PersonDemo personDemo = dataSnapshot.getValue(PersonDemo.class);

                    personDemoArrayList.add(personDemo);

                }
                myAdapter = new MyAdapter(RecyclerView_Activity.this);

                myAdapter.setPersonList(personDemoArrayList);

                rvList.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void setOnRecItemClick(int position) {

    }
}