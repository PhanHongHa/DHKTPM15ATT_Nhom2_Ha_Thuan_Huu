package com.example.dhktpm15att_nhom2_ha_thuan_huu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

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

        com.example.dhktpm15att_nhom2_ha_thuan_huu.Student student = students.get(position);
        holder.ten.setText(student.ten);
        holder.lop.setText(student.lop);
        holder.email.setText(student.email);

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView ten, lop, email;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            ten = itemView.findViewById(R.id.abcdd);
            lop = itemView.findViewById(R.id.txtLop);
            email = itemView.findViewById(R.id.txtEmail_LV);
        }
    }

}