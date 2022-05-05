package com.example.dhktpm15att_nhom2_ha_thuan_huu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddStudent extends AppCompatActivity {
    EditText txtName, txtLop, txtEmail;
    ProgressDialog dialog;
    FirebaseFirestore db;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        txtName = findViewById(R.id.txtName_a);
        txtLop = findViewById(R.id.txtLop_a);
        txtEmail = findViewById(R.id.txtEmail_a);
        btnAdd = findViewById(R.id.btnAdd_a);
        dialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("add Data");


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lop = txtLop.getText().toString().trim();
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                uploadData(lop, name, email);
            }
        });
    }


    private void uploadData(String lop, String name, String email) {
        dialog.setTitle("adding data to firebase");
        dialog.show();
        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("lop", lop);
        doc.put("name", name);
        doc.put("email", email);
        db.collection("students").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();

                Toast.makeText(AddStudent.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddStudent.this, RecyclerViewActivity.class));
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(AddStudent.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}