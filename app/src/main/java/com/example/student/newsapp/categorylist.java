package com.example.student.newsapp;

/**
 * Created by chivu on 29/6/17.
 */

public class categorylist {

    int image_id;
    String category;

    public String getCategory() {
        return category;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }


    public categorylist(int image_id , String category) {
        this.image_id = image_id;
        this.category = category;
    }
}
