package com.example.dhktpm15att_nhom2_ha_thuan_huu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    Context context;
    ArrayList<Student> students;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student,parent,false);




        return new StudentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {

        Student student = students.get(position);
        holder.ten.setText(student.ten);
        holder.ten.setText(student.lop);
        holder.ten.setText(student.email);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView ten, lop, email;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            ten = itemView.findViewById(R.id.txtName);
            lop = itemView.findViewById(R.id.txtLop);
            email = itemView.findViewById(R.id.txtEmail_LV);
        }
    }

}