package com.example.book.mypage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book.HomeActivity;
import com.example.book.R;
import com.example.book.book_write.book_write_main;
import com.example.book.categori.cate1;
import com.example.book.jjim.jjim;
import com.example.book.users.login;

public class mypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mypage);

        // 로그아웃 버튼 설정
       Button logoutButton = findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자 로그아웃 - SharedPreferences 초기화
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                preferences.edit().clear().apply();

                // 로그인 화면으로 이동
                Intent intent = new Intent(mypage.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        // 카테고리 버튼 설정
        ImageButton categoryButton = findViewById(R.id.bar_category);
        categoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, cate1.class);
            startActivity(intent);
        });

        // 독서 버튼 설정
        ImageButton dookreadingButton = findViewById(R.id.bar_dookreading);
        dookreadingButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, book_write_main.class);
            startActivity(intent);
        });

        // 홈 버튼 설정
        ImageButton HomeButton = findViewById(R.id.bar_home);
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, HomeActivity.class);
            startActivity(intent);
        });

        // 찜 목록 버튼 설정
        ImageButton heartButton = findViewById(R.id.bar_heart);
        heartButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, jjim.class);
            startActivity(intent);
        });

        // 사용자 이름 가져오기 및 설정
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "No user ID");
        TextView myID = findViewById(R.id.myid);
        myID.setText(userName);

        // Window Insets 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 설정 버튼 클릭 이벤트
        Button settingsButton = findViewById(R.id.button);
        settingsButton.setOnClickListener(v -> {
            Log.d("SharedPreferences", "userName: " + userName);
            Intent intent = new Intent(mypage.this, serjung.class);
            startActivity(intent);
        });

        // 계정 편집 버튼 클릭 이벤트
        Button accountEditButton = findViewById(R.id.button5);
        accountEditButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, Edit_userActivity.class);
            startActivity(intent);
        });

        // ChatBot 버튼 클릭 이벤트
        Button zzimButton = findViewById(R.id.button6);
        zzimButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, ChatBOT.class);
            startActivity(intent);
        });

        // 고객 센터 버튼 클릭 이벤트
        Button yachaButton = findViewById(R.id.button3);
        yachaButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, gogaksenter.class);
            startActivity(intent);
        });

        // QA 버튼 클릭 이벤트
        Button QAButton = findViewById(R.id.button2);
        QAButton.setOnClickListener(v -> {
            Intent intent = new Intent(mypage.this, QA.class);
            startActivity(intent);
        });
    }
}
