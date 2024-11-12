package com.example.book.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.book.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Edit_userActivity extends AppCompatActivity {

    private EditText user_id, user_pass, user_name, user_age, user_gender;
    private Button btn_register;
    private String userID; // 사용자 ID를 저장할 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myjungbo);

        user_id = findViewById(R.id.new_id);
        user_pass = findViewById(R.id.new_password);
        user_name = findViewById(R.id.new_name);
        user_age = findViewById(R.id.new_age);
        user_gender = findViewById(R.id.new_gender);

        // SharedPreferences에서 사용자 정보 가져오기
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        userID = sharedPref.getString("userID", "");
        String userPass = sharedPref.getString("userPass", "");
        String userName = sharedPref.getString("userName", "");
        String userAge = sharedPref.getString("userAge", "");
        String userGender = sharedPref.getString("userGender", "");

        // 디버깅을 위한 로그 추가
        Log.d("Edit_userActivity", "SharedPreferences userID: " + userID);
        Log.d("Edit_userActivity", "SharedPreferences userPass: " + userPass);
        Log.d("Edit_userActivity", "SharedPreferences userName: " + userName);
        Log.d("Edit_userActivity", "SharedPreferences userAge: " + userAge);
        Log.d("Edit_userActivity", "SharedPreferences userGender: " + userGender);

        // 가져온 값들을 EditText에 설정
        user_id.setText(userID);
        user_pass.setText(userPass);
        user_name.setText(userName);
        user_age.setText(userAge);
        user_gender.setText(userGender);

        btn_register = findViewById(R.id.button2);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = user_id.getText().toString();
                String userPass = user_pass.getText().toString();
                String userName = user_name.getText().toString();
                int userAge;
                try {
                    userAge = Integer.parseInt(user_age.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(Edit_userActivity.this, "나이는 숫자로 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userGender = user_gender.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Edit_userActivity", "Server response: " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "계정정보 수정 완료", Toast.LENGTH_SHORT).show();

                                // 사용자 정보를 SharedPreferences에 저장
                                SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("userID", userID);
                                editor.putString("userPass", userPass);
                                editor.putString("userName", userName);
                                editor.putString("userAge", String.valueOf(userAge));
                                editor.putString("userGender", userGender);
                                editor.apply();

                                Intent intent = new Intent(Edit_userActivity.this, mypage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "응답 처리 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청
                try {
                    Edit_userRequest registerRequest = new Edit_userRequest(userID, userPass, userName, userAge, userGender, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Edit_userActivity.this);
                    queue.add(registerRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "요청 생성 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
