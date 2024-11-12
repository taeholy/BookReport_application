package com.example.book.users;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    // 서버 URL 설정(php 파일 연동)
    // final static private String URL = "http://10.0.0.115/Login.php";
    // final static private String URL = "http://192.168.233.232/Login.php";
    final static private String URL = "http://192.168.33.198/Login.php";
    private Map<String, String> map;

    public LoginRequest(JSONObject requestData, Response.Listener<String> responseListener) throws JSONException {
        super(Method.POST, URL, responseListener, null);

        map = new HashMap<>();
        map.put("userID", requestData.getString("userID"));
        map.put("userPassword", requestData.getString("userPassword"));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
