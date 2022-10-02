package com.example.catii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    private EditText first, last, gender, dob, username, password, email, nationality;
    private Button register6;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        first = findViewById(R.id.first);
        last = findViewById(R.id.last);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        username = findViewById(R.id.username);
        password = findViewById(R.id.Password);
        email = findViewById(R.id.email);
        register6 = findViewById(R.id.button3);
        nationality = findViewById(R.id.nationality);

        auth = FirebaseAuth.getInstance();
        register6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firsttxt = first.getText().toString();
                String lasttxt = last.getText().toString();
                String dobtxt = dob.getText().toString();
                String gendertxt = gender.getText().toString();
                String usernametxt = username.getText().toString();
                String passwordtxt = password.getText().toString();
                String emailtxt = email.getText().toString();
                String nationtxt = nationality.getText().toString();
                if (TextUtils.isEmpty(firsttxt) || TextUtils.isEmpty(lasttxt) || TextUtils.isEmpty(dobtxt) || TextUtils.isEmpty(gendertxt) || TextUtils.isEmpty(usernametxt) || TextUtils.isEmpty(passwordtxt) || TextUtils.isEmpty(emailtxt ))
                {
                    Toast.makeText(registerActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
                if (passwordtxt.length() < 8){
                    Toast.makeText(registerActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(passwordtxt, emailtxt);
                    //firsttxt, lasttxt, dobtxt, gendertxt, usernametxt
                    FirebaseDatabase.getInstance().getReference().child("CAT_II").child("FirstName").setValue(firsttxt);
                    FirebaseDatabase.getInstance().getReference().child("CAT_II").child("LastName").setValue(lasttxt);
                    FirebaseDatabase.getInstance().getReference().child("CAT_II").child("DateOfBirth").setValue(dobtxt);
                    FirebaseDatabase.getInstance().getReference().child("CAT_II").child("Gender").setValue(gendertxt);
                    FirebaseDatabase.getInstance().getReference().child("CAT_II").child("Username").setValue(usernametxt);
                    FirebaseDatabase.getInstance().getReference().child("CAT_II").child("Nationality").setValue(nationtxt);
//                    FirebaseDatabase.getInstance().getReference().child("CAT_II").push().child("FirstName").setValue(firsttxt);
                        //FirebaseDatabase.getInstance().getReference().child("ProgrammingLearning").child("Android").setValue("a,b,c,d");
                }
                }
        });
    }//username, last, first, dob, gender
    //String first, String last, String dob, String gender, String username,
    private void registerUser( String password, String email) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                        Toast.makeText(registerActivity.this, "Registering successful", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), StartActivity.class);
                        startActivity(intent2);
                        finish();

                } else{
                    Toast.makeText(registerActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}