package com.example.finalmufixapp.Send_Data;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Add_Post_Data extends StringRequest {

    private static final String Add_Post_Data="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Upload_Posts.php";
    private Map<String,String> map_array;


    public Add_Post_Data(String Email, String Post_Tittle, String Post_Text, String Post_Image, String Date,String Time, Response.Listener<String> listener) {
        super(Method.POST, Add_Post_Data, listener, null);

        map_array=new HashMap<>();


        map_array.put("email",Email);
        map_array.put("post_tittle",Post_Tittle);
        map_array.put("text_post",Post_Text);
        map_array.put("image_post",Post_Image);
        map_array.put("date_post",Date);
        map_array.put("time_post",Time);

    }

    @Override
    protected Map<String, String> getParams(){
        return map_array;
    }
}
