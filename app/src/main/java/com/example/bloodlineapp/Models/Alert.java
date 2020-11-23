package com.example.bloodlineapp.Models;

public class Alert {

    String alert;
    String url;
    String fullname;
    String bloodneeded;
    String datealert;

    public Alert(String alert, String url, String fullname, String bloodneeded, String datealert) {
        this.alert = alert;
        this.url = url;
        this.fullname = fullname;
        this.bloodneeded = bloodneeded;
        this.datealert = datealert;
    }

    public Alert() {
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBloodneeded() {
        return bloodneeded;
    }

    public void setBloodneeded(String bloodneeded) {
        this.bloodneeded = bloodneeded;
    }

    public String getDatealert() {
        return datealert;
    }

    public void setDatealert(String datealert) {
        this.datealert = datealert;
    }
}
