
package com.gebeya.chatme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dialog {

    @SerializedName("speech")
    @Expose
    private String speech;

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

}
