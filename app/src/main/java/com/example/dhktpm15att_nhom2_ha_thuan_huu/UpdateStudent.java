package com.example.dhktpm15att_nhom2_ha_thuan_huu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.squareup.picasso.Picasso;

public class UpdateStudent extends AppCompatActivity {
    private EditText txtEmail,txtLop,txtTen;
    private Button btnUpdate, btnChoose;
    private FirebaseFirestore db;
    private static  final int PICK_IMAGE_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);
        db = FirebaseFirestore.getInstance();
        btnChoose=findViewById(R.id.btnChoose_u);
        txtEmail=findViewById(R.id.txtEmail_u);
        txtTen=findViewById(R.id.txtName_u);
        txtLop=findViewById(R.id.txtLop_u);
        btnUpdate=findViewById(R.id.btn_update);

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("student");
        txtLop.setText(student.getLop());
        txtTen.setText(student.getTen());
        txtEmail.setText(student.getEmail());


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==PICK_IMAGE_REQUEST && requestCode == RESULT_OK && data!=null && data.getData() !=null){
            /*imgURI = data.getData();
            Picasso.with(this).load(imgURI).into(img);*/

        }

    }
}