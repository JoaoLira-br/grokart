package com.example.grokart.utils;

public class BaseMessage {
    String username;
    String message;
    String date;
    String time;

    public BaseMessage(String username, String message, String date, String time) {
        this.username = username;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    String getUsername() {
        return username;
    }

    String getMessage() {
        return message;
    }

    String getDate() {
        return date;
    }

    String getTime() {
        return time;
    }

    int getViewType(String user) {
        if(username.equals(user)) {
            return 1;
        }
        else {
            return 2;
        }
    }
}
