package com.example.ieaapp;

public class MembersDirectoryModel {

    String company, name2, purl2;

    MembersDirectoryModel(){

    }

    public MembersDirectoryModel(String company, String name2, String purl2) {
        this.company = company;
        this.name2 = name2;
        this.purl2 = purl2;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getPurl2() {
        return purl2;
    }

    public void setPurl2(String purl2) {
        this.purl2 = purl2;
    }
}
