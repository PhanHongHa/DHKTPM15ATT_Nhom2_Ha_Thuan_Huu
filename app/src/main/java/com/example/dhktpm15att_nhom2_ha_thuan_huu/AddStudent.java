package com.example.dhktpm15att_nhom2_ha_thuan_huu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddStudent extends AppCompatActivity {
    private EditText txtName, txtLop, txtEmail;
    private ProgressDialog dialog;
    FirebaseFirestore db;
    private Button btnAdd,btnChoose;
    private ImageView img;
    private Uri imgURI;
    private static  final int PICK_IMAGE_REQUEST=1;

    StorageReference storageRef;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        txtName = findViewById(R.id.txtName_a);
        txtLop = findViewById(R.id.txtLop_a);
        txtEmail = findViewById(R.id.txtEmail_a);
        btnAdd = findViewById(R.id.btnAdd_a);
        btnChoose = findViewById(R.id.btnChoose_a);
        dialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("add Data");


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==PICK_IMAGE_REQUEST && requestCode == RESULT_OK && data!=null && data.getData() !=null){
            imgURI = data.getData();
            Picasso.with(this).load(imgURI).into(img);

        }

    }

    private void uploadData(String lop, String name, String email) {
        dialog.setTitle("adding data to firebase");
        dialog.show();
        String id = UUID.randomUUID().toString();
        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("lop", lop);
        doc.put("ten", name);
        doc.put("email", email);
        db.collection("students").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();

                Toast.makeText(AddStudent.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddStudent.this, com.example.dhktpm15att_nhom2_ha_thuan_huu.RecyclerViewActivity.class));
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