package com.example.dhktpm15att_nhom2_ha_thuan_huu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    com.example.dhktpm15att_nhom2_ha_thuan_huu.StudentAdapter studentAdapter;
    ArrayList<com.example.dhktpm15att_nhom2_ha_thuan_huu.Student> students;
    FirebaseFirestore db;
    ProgressDialog dialog;

    private ImageButton btnAdd;
    private Button btnDelete;
    private com.example.dhktpm15att_nhom2_ha_thuan_huu.StudentAdapter.StudentViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Fetching Data...");
        dialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        students = new ArrayList<com.example.dhktpm15att_nhom2_ha_thuan_huu.Student>();
        studentAdapter = new com.example.dhktpm15att_nhom2_ha_thuan_huu.StudentAdapter(RecyclerViewActivity.this,students);

        recyclerView.setAdapter(studentAdapter);
        ReadData();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener((view) -> {
            Intent intent = new Intent(this, com.example.dhktpm15att_nhom2_ha_thuan_huu.AddStudent.class);
            startActivity(intent);
        });



    }

    private void ReadData() {
        db.collection("students").orderBy("ten", Query.Direction.ASCENDING).orderBy("lop", Query.Direction.ASCENDING).orderBy("email", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){
                            if(dialog.isShowing())
                                dialog.dismiss();
                            Log.e("FireStore error",error.getMessage() );
                            return;
                        }
                        for(DocumentChange  doc: value.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED){
                                students.add(doc.getDocument().toObject(com.example.dhktpm15att_nhom2_ha_thuan_huu.Student.class));
                            }
                            studentAdapter.notifyDataSetChanged();
                            if(dialog.isShowing())
                                dialog.dismiss();
                        }

                    }
                });

    }
}