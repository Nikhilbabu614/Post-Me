package com.example.postme.models;

import java.util.ArrayList;

public class Post {
    public Post() {
    }

    public String text;
    public User user = new User();
    public Long time;
    public ArrayList<String> like = new ArrayList<>();

    public Post(String text, User user, Long time, ArrayList<String> like) {
        this.text = text;
        this.user = user;
        this.time = time;
        this.like = like;
    }
}
