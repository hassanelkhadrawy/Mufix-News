package com.example.finalmufixapp.Send_Data;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ADD_USER extends StringRequest {

    private static final String ADD_USER_URL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Add_User.php";
    private Map<String,String> map_array;


    public ADD_USER(String Username , String Email, String Password, Response.Listener<String> listener) {
        super(Method.POST, ADD_USER_URL, listener, null);

        map_array=new HashMap<>();

        map_array.put("username",Username);
        map_array.put("email",Email);
        map_array.put("password",Password);
    }

    @Override
    protected Map<String, String> getParams(){
        return map_array;
    }
}
