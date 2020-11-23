package com.example.bloodlineapp.Login;

public class User  {

    private String fullName;

    private String address;

    private String phone;

    private String password;

    private String blood;


    private String age;

    private String weight;

    private  String url;


    public User(){}

    public User(String fullName, String phone) {
        this.fullName = fullName;

        this.phone = phone;
    }

    public User(String fullName, String address, String phone,String password, String blood, String weight, String age, String url ) {

        this.age = age ;

        this.weight = weight;

        this.fullName = fullName;

        this.address = address;

        this.phone = phone;

        this.password = password;

        this.blood = blood;
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getAge(){ return age;}
    public void setAge (String age){this.age = age; }

    public String getWeight(){return  weight;}
    public void setWeight(String weight){this.weight = weight; }



}
