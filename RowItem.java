package com.example.acer.tasks;

/**
 * Created by Acer on 4/23/2017.
 */

public class RowItem {
    private String tasks_given;
    private int profile_pic_id;

    public RowItem(String tasks_given, int profile_pic_id)
    {
        this.tasks_given = tasks_given;
        this.profile_pic_id = profile_pic_id;
    }
    public String getTasks_given () {return tasks_given;}

    public void setTasks_given(String tasks_given) {this.tasks_given = tasks_given;}

    public int getProfile_pic_id(){return profile_pic_id;}

    public void setProfile_pic_id(int profile_pic_id) {this.profile_pic_id = profile_pic_id;}
}
