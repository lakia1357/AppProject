package com.example.appproject;


import java.io.Serializable;

public class Message implements Serializable {

    String userId;
    String mailid;
    String mailTitle;
    String mailContains;
    String date;

    public Message(String userId, String mailid, String mailTitle, String mailContains, String date) {
        this.userId = userId;
        this.mailid = mailid;
        this.mailTitle = mailTitle;
        this.mailContains = mailContains;
        this.date = date;
    }

    public String getUserId(){ return  userId; }

    public void setUserId(){this.userId = userId; }

    public String getmailid() {
        return mailid;
    }

    public void setmailid(String mailid) {
        this.mailid = mailid;
    }

    public String getmailTitle() {
        return mailTitle;
    }

    public void setmailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getmailContains() {
        return mailContains;
    }

    public void setmailContains(String mailContains) {
        this.mailContains = mailContains;
    }

    public String getdate() { return date; }

    public void setdate(String date) {
        this.date = date;
    }

}
