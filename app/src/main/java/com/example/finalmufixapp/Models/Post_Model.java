package com.example.finalmufixapp.Models;

public class Post_Model {
    public String Email;
    public String Username;
    public String Text_Tittle;
    public String Text_Post;
    public String Image_Post;
    public String P_Image;
    public String Date;
    public String Time;

    public Post_Model(String email, String username, String text_Tittle, String text_Post, String image_Post, String p_Image, String date_Post,String time_Post) {
        Email=email;
        Username = username;
        Text_Tittle = text_Tittle;
        Text_Post = text_Post;
        Image_Post = image_Post;
        P_Image = p_Image;
        Date=date_Post;
        Time=time_Post;
    }
}
