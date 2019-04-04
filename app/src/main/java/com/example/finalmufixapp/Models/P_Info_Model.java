package com.example.finalmufixapp.Models;

public class P_Info_Model {

    public String Username;
    public String P_Email;
    public String P_Password;
    public String P_Image;

    public P_Info_Model(String email, String password) {
        P_Email = email;
        P_Password = password;
    }
}
