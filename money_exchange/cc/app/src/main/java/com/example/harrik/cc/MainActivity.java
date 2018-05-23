package com.example.harrik.cc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText edtUsd;
    EditText edtEu;
    EditText edtVnd;
    EditText edtBath;
    Switch swActive;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("usd").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    edtUsd.setText(dataSnapshot.getValue().toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("bath").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    edtBath.setText(dataSnapshot.getValue().toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("eu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    edtEu.setText(dataSnapshot.getValue().toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("vnd").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    edtVnd.setText(dataSnapshot.getValue().toString());

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        swActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edtUsd.setEnabled(false);
                    edtVnd.setEnabled(false);
                    edtEu.setEnabled(false);
                    edtBath.setEnabled(false);
                    try {
                        myRef.child("usd").setValue(edtUsd.getText().toString());
                        myRef.child("vnd").setValue(edtVnd.getText().toString());
                        myRef.child("bath").setValue(edtBath.getText().toString());
                        myRef.child("eu").setValue(edtEu.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    edtUsd.setEnabled(true);
                    edtVnd.setEnabled(true);
                    edtEu.setEnabled(true);
                    edtBath.setEnabled(true);
                }
            }
        });
    }

    void setControl() {
        edtBath = findViewById(R.id.editTextBath);
        edtEu = findViewById(R.id.editTextEu);
        edtVnd = findViewById(R.id.editTextVnd);
        edtUsd = findViewById(R.id.editTextUsd);
        swActive = findViewById(R.id.switchActive);
    }
}
