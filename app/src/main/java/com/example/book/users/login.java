package com.example.book.users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.book.HomeActivity;
import com.example.book.R;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class login extends AppCompatActivity {

    private static final String TAG = "login";
    private EditText lg_id, lg_pass;
    private Button btn_login, btn_register;
    private ImageView api_Button; // 카카오 API 로그인 버튼 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // XML에서 버튼들과 연결
        lg_id = findViewById(R.id.lg_id);
        lg_pass = findViewById(R.id.lg_password);
        btn_login = findViewById(R.id.logbtn);
        btn_register = findViewById(R.id.regbtn);
        api_Button = findViewById(R.id.api); // 카카오 API 버튼 연결

        // 카카오 로그인 콜백 설정
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    // 카카오 로그인 성공 시 사용자 정보 요청
                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {
                            if (user != null) {
                                // 카카오 로그인 성공 처리
                                Toast.makeText(login.this, "카카오 로그인 성공", Toast.LENGTH_SHORT).show();

                                // 사용자 정보 로그
                                Log.d(TAG, "카카오 사용자 정보: " + user.getKakaoAccount().getProfile().getNickname());
                                Log.d(TAG, "카카오 이메일: " + user.getKakaoAccount().getEmail());

                                // 사용자 정보를 SharedPreferences에 저장
                                SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("kakaoNickname", user.getKakaoAccount().getProfile().getNickname());
                                editor.putString("kakaoEmail", user.getKakaoAccount().getEmail());
                                editor.apply();

                                // 다음 액티비티로 이동하며 사용자 정보 전달
                                Intent intent = new Intent(login.this, HomeActivity.class);
                                intent.putExtra("kakaoNickname", user.getKakaoAccount().getProfile().getNickname());
                                intent.putExtra("kakaoEmail", user.getKakaoAccount().getEmail());
                                startActivity(intent);
                                finish(); // 현재 Activity 종료
                            } else {
                                Log.e(TAG, "사용자 정보 요청 실패: " + throwable.getMessage());
                            }
                            return null;
                        }
                    });
                } else if (throwable != null) {
                    // 로그인 실패 처리
                    Log.e(TAG, "카카오 로그인 실패: " + throwable.getMessage());
                    Toast.makeText(login.this, "카카오 로그인 실패: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };

        api_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(login.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(login.this, callback);
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(login.this, callback);
                }
            }
        });
        updateKakaoLoginUi();

        // 회원가입 버튼 클릭 시 RegisterActivity로 이동
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 기존 로그인 버튼 클릭 시 일반 로그인 처리
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사용자가 입력한 아이디와 패스워드 정보 가져오기
                String userID = lg_id.getText().toString();
                String userPassword = lg_pass.getText().toString();

                // 서버로 전송할 데이터를 JSON 형식으로 만들기
                JSONObject requestData = new JSONObject();
                try {
                    requestData.put("userID", userID);
                    requestData.put("userPassword", userPassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(login.this, "JSON 생성 오류", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 서버로 데이터 전송 및 응답 처리
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("LoginResponse", response); // 서버 응답 로그 출력
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                // 로그인 성공 처리
                                String userID = jsonObject.getString("userID");
                                String userPassword = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                String userAge = jsonObject.getString("userAge");
                                String userGender = jsonObject.getString("userGender");

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, HomeActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                intent.putExtra("userName", userName);
                                intent.putExtra("userAge", userAge);
                                intent.putExtra("userGender", userGender);

                                // 사용자 정보를 SharedPreferences에 저장
                                SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("userID", userID);
                                editor.putString("userPass", userPassword);
                                editor.putString("userName", userName);
                                editor.putString("userAge", userAge);
                                editor.putString("userGender", userGender);
                                editor.apply();

                                startActivity(intent);
                                finish(); // 현재 Activity 종료
                            } else {
                                // 로그인 실패 처리
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(login.this, "응답 처리 오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                // LoginRequest와 RequestQueue 부분 추가
                try {
                    LoginRequest loginRequest = new LoginRequest(requestData, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(login.this);
                    queue.add(loginRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(login.this, "로그인 요청 생성 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateKakaoLoginUi() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    Log.i(TAG, "invoke: id=" + user.getId());
                } else {
                    Log.i(TAG, "로그인이 안되어있습니다.");
                }
                return null;
            }
        });
    }
}
