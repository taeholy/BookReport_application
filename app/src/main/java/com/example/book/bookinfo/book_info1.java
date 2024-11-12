package com.example.book.bookinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class book_info1 extends AppCompatActivity {

    private boolean isHeartFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info1);

        // Back button functionality
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // Heart button functionality
        ImageButton heartButton = findViewById(R.id.heart_button);
        heartButton.setImageResource(R.drawable.heart); // 처음에는 검정 테두리 하트로 설정
        heartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHeartFilled = !isHeartFilled;
                if (isHeartFilled) {
                    heartButton.setImageResource(R.drawable.heert); // 빨간 하트
                } else {
                    heartButton.setImageResource(R.drawable.heart); // 검정 테두리 하트
                }
            }
        });

        // Butt button functionality
        Button buttButton = findViewById(R.id.butt);
        buttButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(book_info1.this, com.example.book.book_write.ShowMyData.class);
                startActivity(intent);
            }
        });
    }
}
