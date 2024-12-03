package com.example.book.mypage;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.book.R;
import com.google.android.material.snackbar.Snackbar;

public class serjung extends AppCompatActivity {

    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private Handler chatHandler = new Handler(); // 채팅 알림용 핸들러
    private Handler noticeHandler = new Handler(); // 공고 알림용 핸들러
    private Runnable chatNotificationRunnable;
    private Runnable noticeNotificationRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serjung);

        switch1 = findViewById(R.id.switch1); // 채팅 알림 스위치
        switch2 = findViewById(R.id.switch2); // 공고 알림 스위치
        switch3 = findViewById(R.id.switch3);

        Button saveButton = findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(serjung.this, "변경사항이 저장되었습니다", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 채팅 알림 스위치 상태에 따라 알림 설정
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startChatNotification(); // 30초마다 채팅 알림 시작
            } else {
                stopChatNotification(); // 채팅 알림 중지
            }
        });

        // 공고 알림 스위치 상태에 따라 알림 설정
        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startNoticeNotification(); // 40초마다 공고 알림 시작
            } else {
                stopNoticeNotification(); // 공고 알림 중지
            }
        });
    }

    private void startChatNotification() {
        chatNotificationRunnable = new Runnable() {
            @Override
            public void run() {
                Snackbar.make(findViewById(android.R.id.content), "30초마다 뜨는 채팅 알림입니다!", Snackbar.LENGTH_SHORT).show();
                chatHandler.postDelayed(this, 30000); // 30초 후 다시 실행
            }
        };
        chatHandler.post(chatNotificationRunnable); // 처음 실행
    }

    private void stopChatNotification() {
        if (chatNotificationRunnable != null) {
            chatHandler.removeCallbacks(chatNotificationRunnable);
        }
    }

    private void startNoticeNotification() {
        noticeNotificationRunnable = new Runnable() {
            @Override
            public void run() {
                Snackbar.make(findViewById(android.R.id.content), "40초마다 뜨는 공고 알림입니다!", Snackbar.LENGTH_SHORT).show();
                noticeHandler.postDelayed(this, 40000); // 40초 후 다시 실행
            }
        };
        noticeHandler.post(noticeNotificationRunnable); // 처음 실행
    }

    private void stopNoticeNotification() {
        if (noticeNotificationRunnable != null) {
            noticeHandler.removeCallbacks(noticeNotificationRunnable);
        }
    }
}
