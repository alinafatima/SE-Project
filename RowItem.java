package com.example.acer.hlloworld;

/**
 * Created by Acer on 4/23/2017.
 */

public class RowItem {
    private String notifications;
    private int profile_pic_id;

    public RowItem(String notifications, int profile_pic_id)
    {
        this.notifications = notifications;
        this.profile_pic_id = profile_pic_id;
    }
    public String getNotifications () {return notifications;}

    public void setNotifications(String notifications) {this.notifications = notifications;}

    public int getProfile_pic_id(){return profile_pic_id;}

    public void setProfile_pic_id(int profile_pic_id) {this.profile_pic_id = profile_pic_id;}
}
