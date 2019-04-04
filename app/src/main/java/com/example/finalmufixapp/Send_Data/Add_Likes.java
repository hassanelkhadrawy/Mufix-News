package com.example.finalmufixapp.Send_Data;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Add_Likes extends StringRequest {

    private static final String ADD_USER_URL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Insert_Likes.php";
    private Map<String,String> map_array;


    public Add_Likes(String Email, String Date,String Time, Response.Listener<String> listener) {
        super(Method.POST, ADD_USER_URL, listener, null);

        map_array=new HashMap<>();


        map_array.put("email",Email);
        map_array.put("date",Date);
        map_array.put("time",Time);

    }

    @Override
    protected Map<String, String> getParams(){
        return map_array;
    }
}
