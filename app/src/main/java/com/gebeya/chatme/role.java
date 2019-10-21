package com.gebeya.chatme;

public class role {

    public String bot;
    public String user;
    public String message;
    public int image;
    boolean isMe;

    public role() {
    }

    public role(String message, boolean isMe) {
        this.message = message;
        this.isMe = isMe;
    }


    public String getBot() {
        return bot;
    }

    public void setBot(String bot) {
        this.bot = bot;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
