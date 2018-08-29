package com.example.alina.seprojecttracker;

/**
 * Created by Alina on 09-May-17.
 */

public class Information {
    int IconId;
    String title;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
