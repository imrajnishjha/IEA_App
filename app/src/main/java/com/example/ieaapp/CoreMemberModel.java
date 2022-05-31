package com.example.ieaapp;

public class CoreMemberModel {

    String name, purl, role;

    CoreMemberModel()
    {

    }

    public CoreMemberModel(String name, String purl, String role) {
        this.name = name;
        this.purl = purl;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
