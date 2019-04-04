package com.example.finalmufixapp.Send_Data;

import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Add_Comments extends StringRequest {

    private static final String ADD_USER_URL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Insert_Comments.php";
    private Map<String,String> map_array;


    public Add_Comments(String Email,String Comment, String Time,String Date, Response.Listener<String> listener) {
        super(Method.POST, ADD_USER_URL, listener, null);

        map_array=new HashMap<>();

        map_array.put("email",Email);
        map_array.put("comment",Comment);
        map_array.put("time",Time);
        map_array.put("date",Date);

    }

    @Override
    protected Map<String, String> getParams(){
        return map_array;
    }
}
