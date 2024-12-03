package com.example.book;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class review extends AppCompatActivity {

    private TextView bookTitleView;
    private TextView topTitleView;
    private ImageView bookImageView;
    private EditText reviewEditText;
    private Button submitReviewButton;
    private LinearLayout reviewContainer;

    private String bookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);

        // UI 요소 초기화
        topTitleView = findViewById(R.id.top_title); // 상단바 책 제목
        bookTitleView = findViewById(R.id.titlebookname);
        bookImageView = findViewById(R.id.book_img);
        reviewEditText = findViewById(R.id.et_review);
        submitReviewButton = findViewById(R.id.btn_submit_review);
        reviewContainer = findViewById(R.id.review_list_container);

        // 뒤로 가기 버튼 클릭 이벤트 추가
        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(v -> onBackPressed());  // 뒤로 가기 기능

        // Intent로부터 데이터 수신
        bookTitle = getIntent().getStringExtra("BOOK_TITLE");
        String bookImageUrl = getIntent().getStringExtra("BOOK_IMAGE_URL");

        // 데이터 표시
        if (bookTitle != null) {
            bookTitleView.setText(bookTitle);
            topTitleView.setText(bookTitle); // 상단바에 책 제목 설정
        }

        if (bookImageUrl != null) {
            Picasso.get().load(bookImageUrl).into(bookImageView);
        }

        // 리뷰 제출 버튼 클릭 이벤트
        submitReviewButton.setOnClickListener(v -> {
            String reviewText = reviewEditText.getText().toString().trim();
            if (!reviewText.isEmpty()) {
                addReviewToContainer(reviewText); // 입력된 리뷰를 아래에 추가
                reviewEditText.setText(""); // 리뷰 입력 후 입력란 초기화
            } else {
                Toast.makeText(review.this, "리뷰를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 기존 리뷰 가져오기
        fetchReviews();
    }

    private void fetchReviews() {
        new AsyncTask<Void, Void, String[]>() {
            @Override
            protected String[] doInBackground(Void... voids) {
                try {
                    String urlString = "http://192.168.33.198/get_reviews.php?title=" + bookTitle;
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line).append("\n");
                    }
                    reader.close();

                    return result.toString().split("\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String[] reviews) {
                if (reviews != null) {
                    reviewContainer.removeAllViews(); // 기존 리뷰 제거
                    for (String review : reviews) {
                        if (!review.trim().isEmpty()) { // 빈 리뷰는 무시
                            addReviewToContainer(review.trim()); // 리뷰를 추가
                        }
                    }
                } else {
                    Toast.makeText(review.this, "리뷰를 불러오는 중 오류 발생.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    // 리뷰를 LinearLayout에 한 줄씩 추가
    private void addReviewToContainer(String reviewText) {
        TextView reviewTextView = new TextView(this);
        reviewTextView.setText(reviewText);
        reviewTextView.setTextSize(16);
        reviewTextView.setPadding(16, 16, 16, 16);
        reviewTextView.setGravity(Gravity.LEFT);
        reviewTextView.setBackgroundResource(android.R.drawable.divider_horizontal_bright); // 리뷰 항목 구분선

        // 새로운 리뷰를 아래로 추가
        reviewContainer.addView(reviewTextView);
    }
}
