package com.example.finalmufixapp.Send_Data;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Update extends StringRequest {
    private static final String Update_USER_URL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Update.php";
    private Map<String,String> map_array;


    public Update( String Username , String Email, String Password, String P_image, String old_email, Response.Listener<String> listener) {
        super(Request.Method.POST, Update_USER_URL, listener, null);

        map_array=new HashMap<>();

        map_array.put("username",Username);
        map_array.put("email",Email);
        map_array.put("password",Password);
        map_array.put("new_p_image",P_image);
        map_array.put("old_email",old_email);

    }

    @Override
    protected Map<String, String> getParams(){
        return map_array;
    }
}
