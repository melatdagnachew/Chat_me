
package com.gebeya.chatme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReminderRequest {

    @SerializedName("dialog")
    @Expose
    private Dialog dialog;

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

}
