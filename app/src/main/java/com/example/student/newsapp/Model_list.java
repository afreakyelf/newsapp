package com.example.student.newsapp;


public class Model_list {

    String str_name, str_channel;
    int image_id;
    int count;

    public int getCount() {
        return count;
    }

    public int setCount(int count) {
        this.count = count;
        return count;
    }



    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getStr_channel() {
        return str_channel;
    }

    public void setStr_channel(String str_channel) {
        this.str_channel = str_channel;
    }

    public String getStr_name() {
        return str_name;
    }

    public void setStr_name(String str_name) {
        this.str_name = str_name;
    }

    public Model_list(String str_name, String str_channel, int image_id) {
        this.str_name = str_name;
        this.str_channel = str_channel;
        this.image_id = image_id;
    }
}
