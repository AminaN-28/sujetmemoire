package com.example.bloodlineapp;

public class User  {

    private String fullName;

    private String address;

    private String phone;

    private String password;

    private String blood;


    public User(){}

    public User(String fullName, String phone) {
        this.fullName = fullName;

        this.phone = phone;
    }

    public User(String fullName, String address, String phone,String password, String blood) {

        this.fullName = fullName;

        this.address = address;

        this.phone = phone;

        this.password = password;

        this.blood = blood;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getFullName() {
        return fullName;
    }

    public void setAddress(String address)
    {
        this.address =address;
    }
    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public String getBlood(){return blood;}
    public void setBlood(String blood){this.blood = blood;}




}
