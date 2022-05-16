package com.example.dhktpm15att_nhom2_ha_thuan_huu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    ArrayList<Student> students;
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
        students = new ArrayList<Student>();
        studentAdapter = new StudentAdapter(RecyclerViewActivity.this,students);

        recyclerView.setAdapter(studentAdapter);
        ReadData();
//        layDuLieu();
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener((view) -> {
            Intent intent = new Intent(this, AddStudent.class);
            startActivity(intent);
        });



    }

    private void ReadData() {
        db.collection("students").orderBy("id", Query.Direction.ASCENDING).orderBy("ten", Query.Direction.ASCENDING).orderBy("lop", Query.Direction.ASCENDING).orderBy("email", Query.Direction.ASCENDING)
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
    public void layDuLieu() {

        db.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                students.clear();
                for (DocumentSnapshot snapshot : task.getResult()){
                    Student student = new Student(snapshot.getString("id"), snapshot.getString("ten"), snapshot.getString("lop"), snapshot.getString("email"));
                    students.add(student);
                }
                studentAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RecyclerViewActivity.this, "Firebase error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}