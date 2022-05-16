package com.example.dhktpm15att_nhom2_ha_thuan_huu;

import java.io.Serializable;

public class Student implements Serializable
{
    String id;
   String ten, lop, email;
   int picture;


    public Student(String id, String ten, String lop, String email) {
        this.id = id;
        this.ten = ten;
        this.lop = lop;
        this.email = email;

    }

    public Student(String ten, String lop, String email) {
        this.ten = ten;
        this.lop = lop;
        this.email = email;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", ten='" + ten + '\'' +
                ", lop='" + lop + '\'' +
                ", email='" + email + '\'' +
                ", picture=" + picture +
                '}';
    }
}
