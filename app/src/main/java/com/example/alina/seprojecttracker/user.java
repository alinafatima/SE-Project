package com.example.alina.seprojecttracker;

/**
 * Created by Alina on 09-May-17.
 */

public class user {

    public String username;
    public String email;
    public String password;
    public String companyName;
    public String designation;
    public String phoneNumber;
    public  String status;
    public double latitude;
    public double longitude;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public user( String mail,double lat, double longi)
    {
        this.username=mail;
        this.latitude=lat;
        this.longitude=longi;


    }

    public user(String username, String email,String myPassword, String myDesignation, String mycompanyname, String myPhone, String myStatus)
    {
        this.username = username;
        this.email = email;
        this.password=myPassword;
        this.companyName=mycompanyname;
        this.designation=myDesignation;
        this.phoneNumber=myPhone;
        this.status=myStatus;
    }


}
