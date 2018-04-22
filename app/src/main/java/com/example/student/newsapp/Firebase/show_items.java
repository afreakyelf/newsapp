package com.example.student.newsapp.Firebase;


public class show_items  {
        private String Image_URL,Image_Title,Image_des;  //put this name same as Database Fields

        public show_items(String image_URL, String image_title , String image_des) {
            Image_URL = image_URL;
            Image_Title = image_title;
            Image_des  = image_des;

        }
        public show_items()
        {
            //Empty Constructor Needed
        }

        public String getImage_URL() {
            return Image_URL;
        }

        public void setImage_URL(String image_URL) {
            Image_URL = image_URL;
        }

        public String getImage_Title() {
            return Image_Title;
        }

        public void setTitle(String title) {
            Image_Title = title;

        }

    public String getImage_des() {
        return Image_des;
    }

    public void setImage_des(String image_des) {
        Image_des = image_des;
    }
}

