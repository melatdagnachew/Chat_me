package com.gebeya.chatme.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class role {

    @PrimaryKey(autoGenerate = true)
    private int id;


    public String message;
    public String time;

    boolean isMe;

    public role() {
    }

    public role(String message) {
        this.message = message;
    }

    public role(String message,String time, boolean isMe) {
        this.message = message;
        this.isMe = isMe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", isMe='" + isMe + '\'' +
                '}';
    }
}
