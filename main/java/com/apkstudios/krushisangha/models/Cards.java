package com.apkstudios.krushisangha.models;

/**
 * Created by Admin on 21-12-2017.
 */

public class Cards {

    String title, body, type, time;

    public Cards(String title, String body, String type, String time) {
        this.title = title;
        this.body = body;
        this.type = type;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
