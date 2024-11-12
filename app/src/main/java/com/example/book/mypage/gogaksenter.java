package com.example.book.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class gogaksenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gogaksenter);

        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gogaksenter.this, mypage.class);
                // 메인 액티비티가 스택의 최상위에 있음을 보장하고 다른 모든 액티비티를 제거
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // 현재 액티비티 종료
            }
        });

        // 1대1 문의 버튼 클릭 시 Sangdam1n1Activity로 이동
        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gogaksenter.this, Sangdam1n1.class);
                startActivity(intent);
            }
        });

        // 1대1 문의 조회 버튼 클릭 시 SangdamActivity로 이동
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gogaksenter.this, Sangdam.class);
                startActivity(intent);
            }
        });
    }
}
