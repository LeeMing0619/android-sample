package com.app.flex.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("result")
    @Expose
    private User user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
