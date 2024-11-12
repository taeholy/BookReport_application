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

        ImageButton backButton = findViewById(R.id.back_botton); // 추가된 줄, ID가 'view'인 ImageButton을 찾음
        backButton.setOnClickListener(new View.OnClickListener() { // 추가된 줄, 클릭 리스너 설정
            @Override
            public void onClick(View v) { // 추가된 줄
                finish(); // 추가된 줄, 현재 액티비티 종료하고 이전 액티비티로 돌아가기
            } // 추가된 줄
        }); // 추가된 줄
    }
}