package com.example.book.mypage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class QA_In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qa_in_page);

        // 뒤로가기 버튼 설정 - 기존 코드
        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // 현재 액티비티 종료하고 이전 화면으로 돌아가기
            }
        });
    }
}
