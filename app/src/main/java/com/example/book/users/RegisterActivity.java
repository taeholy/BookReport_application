package com.example.book.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.book.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText user_id, user_pass, user_name, user_age;
    RadioGroup user_gender;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // 아이디값 찾아주기
        user_id = findViewById(R.id.new_id);
        user_pass = findViewById(R.id.new_password);
        user_name = findViewById(R.id.new_name);
        user_age = findViewById(R.id.new_age);
        user_gender = findViewById(R.id.new_gender);

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RegisterActivity", "-----------a");

                String userID = user_id.getText().toString();
                String userPass = user_pass.getText().toString();
                String userName = user_name.getText().toString();
                int userAge = Integer.parseInt(user_age.getText().toString());
                // 선택된 라디오 버튼의 ID를 통해 성별을 가져옴
                int selectedRadioButtonId = user_gender.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String userGender = selectedRadioButton.getText().toString();

                Log.d("RegisterActivity", "-----------b");

                // 서버 응답 처리
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RegisterActivity", "Response: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // 회원가입 성공시
                            if (success) {
                                Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, login.class);
                                startActivity(intent);
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
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAge, userGender, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
