package com.example.dhktpm15att_nhom2_ha_thuan_huu;

public class Student {
   String ten, lop, email;
   int picture;



    public Student(String ten, String lop, String email, int picture) {
        this.ten = ten;
        this.lop = lop;
        this.email = email;
        this.picture = picture;
    }

    public Student() {
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
}
