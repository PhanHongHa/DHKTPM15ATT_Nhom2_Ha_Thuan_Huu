package com.example.dhktpm15att_nhom2_ha_thuan_huu;


import static android.widget.Toast.*;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    Context context;
    ArrayList<com.example.dhktpm15att_nhom2_ha_thuan_huu.Student> students;
    private Button btnDelete;
    private FirebaseFirestore db;

    public StudentAdapter(Context context, ArrayList<com.example.dhktpm15att_nhom2_ha_thuan_huu.Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student,parent,false);
        db = FirebaseFirestore.getInstance();



        return new StudentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        Student student = students.get(position);

        holder.ten.setText(student.ten);
        holder.lop.setText(student.lop);
        holder.email.setText(student.email);

        holder.btn_item_Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, UpdateStudent.class);
                intent.putExtra("student", (Serializable) student);
                context.startActivity(intent);
            }
        });
        holder.btn_item_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.ten.getContext());
                builder.setTitle("B???n c?? mu???n xo?? ?");
                builder.setMessage("Xo?? d??? li???u kh??ng th??? ho??n t??c l???i !");
                builder.setPositiveButton("Xo??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseFirestore docRef = FirebaseFirestore.getInstance();
                        DocumentReference selectedDoc = docRef.collection("students").document(student.getId());
                        selectedDoc.delete();
                        makeText(holder.ten.getContext(), "Xo?? th??nh c??ng", LENGTH_SHORT).show();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        makeText(holder.ten.getContext(), "Cancel", LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView ten, lop, email;
        Button btn_item_Xoa,btn_item_Sua;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            ten = itemView.findViewById(R.id.abcdd);
            lop = itemView.findViewById(R.id.txtLop);
            email = itemView.findViewById(R.id.txtEmail_LV);
            btn_item_Xoa=itemView.findViewById(R.id.btnDelete);
            btn_item_Sua=itemView.findViewById(R.id.btnEdit);
        }
    }

}