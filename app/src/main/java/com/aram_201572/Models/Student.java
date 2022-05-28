package com.aram_201572.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class Student{
        @SerializedName("id")
        private String id;
        @SerializedName("name")
        private String name;
        @SerializedName("surname")
        private String surname;
        @SerializedName("fatherName")
        private String fatherName;
        @SerializedName("nationalId")
        private String nationalId;
        @SerializedName("dob")
        private String dob;
        @SerializedName("gender")
        private String gender;

        private String documentId;

    public Student() {

    }

    public Student(String name,String surname, String fatherName, String nationalId, String dob, String gender) {
        this.name = name;
        this.surname = surname;
        this.fatherName = fatherName;
        this.nationalId = nationalId;
        this.dob = dob;
        this.gender = gender;
    }

        public Student(String id, String name,String surname, String fatherName, String nationalId, String dob, String gender) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.fatherName = fatherName;
            this.nationalId = nationalId;
            this.dob = dob;
            this.gender = gender;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
        return name;
    }
        public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getFatherName() {
        return fatherName;
    }
    public void setFatherName(String name) {
        this.fatherName = name;
    }

    public String getNationalId() {
        return nationalId;
    }
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDocumentId() {
        return documentId;
    }
    public void setDocumentId(String docId) {
        this.documentId = docId;
    }

}
