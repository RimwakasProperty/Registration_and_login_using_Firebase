package com.example.catii;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    Button logout;
    ListView myList;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout = findViewById(R.id.button4);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
                Intent intent6 = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(intent6);
                finish();
            }
        });
        final ArrayList <String> arraylist = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arraylist);
        myList = findViewById(R.id.list);
        myList.setAdapter(arrayAdapter);

        myRef = FirebaseDatabase.getInstance().getReference("CAT_II");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arraylist.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //registerActivity message = dataSnapshot.getValue(registerActivity.class);
                    //String value = dataSnapshot.child("FirstName").getValue(String.class);
                    //arraylist.getValue(value);
                    arraylist.add(Objects.requireNonNull(snapshot.getValue()).toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                arrayAdapter.notifyDataSetChanged();

            }
        });


    }
}