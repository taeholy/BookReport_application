package com.example.book.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class Naeyoung extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.naeyoung);

        TextView inquiryTitleTextView = findViewById(R.id.inquiry_content1);
        TextView inquiryContentTextView = findViewById(R.id.inquiry_content);
        ImageButton backButton = findViewById(R.id.back_botton);

        // 인텐트로부터 전달된 문의 내용 가져오기
        String inquiryContent = getIntent().getStringExtra("INQUIRY_CONTENT");
        if (inquiryContent != null) {
            String[] lines = inquiryContent.split("\n", 2);
            inquiryTitleTextView.setText(lines[0]);
            if (lines.length > 1) {
                inquiryContentTextView.setText(lines[1]);
            } else {
                inquiryContentTextView.setText("");
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Naeyoung.this, Sangdam.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
