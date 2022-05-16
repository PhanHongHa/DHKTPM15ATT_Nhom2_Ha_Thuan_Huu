package com.example.dhktpm15att_nhom2_ha_thuan_huu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateStudent extends AppCompatActivity {
    private EditText txtEmail,txtLop,txtTen;
    private Button btnUpdate;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        db = FirebaseFirestore.getInstance();
        txtEmail=findViewById(R.id.txtEmail_edit);
        txtTen=findViewById(R.id.txtName_edit);
        txtLop=findViewById(R.id.txtLop_edit);
        btnUpdate=findViewById(R.id.btn_edit_1);

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("student");
        txtLop.setText(student.getLop());
        txtTen.setText(student.getTen());
        txtEmail.setText(student.getEmail());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentLop = txtLop.getText().toString().trim();
                String studentTen = txtTen.getText().toString().trim();
                String studentEmail = txtEmail.getText().toString().trim();
                Student studentUpdate= new Student(studentTen,studentLop,studentEmail);
                db.collection("students").document(student.getId())
                        .set(studentUpdate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(UpdateStudent.this, "Cập nhật thành công !!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateStudent.this,RecyclerViewActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateStudent.this, "Cập nhật thất bại :(", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}