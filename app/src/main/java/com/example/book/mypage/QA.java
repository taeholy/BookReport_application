package com.example.book.mypage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class QA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qapage);

        // 뒤로가기 버튼 설정 (ID를 다시 확인하고 수정)
        ImageButton backButton = findViewById(R.id.back_botton); // R.id.back_button일 경우 여기를 수정
        if (backButton != null) { // null 확인 추가
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), mypage.class); // 클래스 이름 확인 후 수정
                    startActivity(intent);
                }
            });
        }

        // 공지사항 수 계산 (실제 공지사항 데이터를 기반으로 계산 필요)
        int noticeCount = calculateNoticeCount();  // 공지사항 수를 계산하는 메서드 호출

        // 공지사항 수를 TextView에 표시
        TextView noticeCountTextView = findViewById(R.id.notice_count);
        String noticeText = "총 " + noticeCount + "건의 공지사항이 있습니다.";
        SpannableString spannableString = new SpannableString(noticeText);

        // 숫자 부분만 색상 변경
        int start = noticeText.indexOf(String.valueOf(noticeCount));
        int end = start + String.valueOf(noticeCount).length();
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        noticeCountTextView.setText(spannableString);

        // 각 업데이트 내역 항목에 클릭 리스너 설정
        setNoticeItemClickListener(R.id.comic);
        setNoticeItemClickListener(R.id.comi2);
        setNoticeItemClickListener(R.id.comi3);
        setNoticeItemClickListener(R.id.comi4);
        setNoticeItemClickListener(R.id.comi5);
        setNoticeItemClickListener(R.id.comi6);
        setNoticeItemClickListener(R.id.comi7);
    }

    // 공지사항 개수를 계산하는 메서드
    private int calculateNoticeCount() {
        // 실제 데이터베이스나 서버에서 공지사항 데이터를 가져와서 개수를 계산해야 함
        // 현재는 예시로 7개로 고정
        return 7;  // 예시 값, 실제로는 공지사항 리스트의 크기를 반환해야 함
    }

    // 업데이트 내역 항목에 클릭 리스너를 설정하는 메서드
    private void setNoticeItemClickListener(int textViewId) {
        TextView noticeItem = findViewById(textViewId);
        if (noticeItem != null) { // null 확인 추가
            noticeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 시 QA_In 액티비티로 이동
                    Intent intent = new Intent(QA.this, QA_In.class);
                    startActivity(intent);
                }
            });
        }
    }
}
