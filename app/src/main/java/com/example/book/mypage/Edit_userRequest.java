package com.example.book.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Edit_userRequest extends StringRequest {

    // 서버 URL 설정(php 파일 연동)
    final static private String URL = "http://192.168.33.224/edit_user.php";
    private Map<String, String> map;

    public Edit_userRequest(String userID, String userPassword, String userName, int userAge, String userGender, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("userAge", String.valueOf(userAge));
        map.put("userGender", userGender);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
