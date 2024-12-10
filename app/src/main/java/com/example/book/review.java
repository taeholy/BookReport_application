package com.example.book;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class review extends AppCompatActivity {

    private TextView bookTitleView;
    private TextView topTitleView;
    private ImageView bookImageView;
    private EditText reviewEditText;
    private Button submitReviewButton;
    private LinearLayout reviewContainer;
    private ScrollView scrollView;

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
        scrollView = findViewById(R.id.scrollView);

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
                new SubmitReviewTask().execute(bookTitle, reviewText); // DB에 저장
            } else {
                Toast.makeText(review.this, "리뷰를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // 기존 리뷰 가져오기
        fetchReviews();
    }

    private class SubmitReviewTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String title = params[0];
            String review = params[1];

            try {
                String urlString = "http://192.168.33.198/submit_review.php"; // PHP 파일 경로
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String postData = "title=" + title + "&review=" + review;
                connection.getOutputStream().write(postData.getBytes());
                connection.getOutputStream().flush();
                connection.getOutputStream().close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "리뷰가 성공적으로 저장되었습니다.";
                } else {
                    return "리뷰 저장 실패: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "리뷰 저장 중 오류 발생: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(review.this, result, Toast.LENGTH_SHORT).show();
            if (result.contains("성공적으로 저장")) {
                reviewEditText.setText("");
                fetchReviews(); // 리뷰 목록 갱신
            }
        }
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

                    // JSON 응답 파싱
                    JSONArray jsonArray = new JSONArray(result.toString());
                    String[] reviews = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        reviews[i] = jsonArray.getString(i);  // 각 리뷰를 배열에 담음
                    }
                    return reviews;
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
                            addReviewToContainer(review.trim());
                        }
                    }
                } else {
                    Toast.makeText(review.this, "리뷰를 불러오는 중 오류 발생.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    // 리뷰를 LinearLayout에 한 줄씩 추가하고, ScrollView가 하단으로 자동 스크롤되게 처리
    private void addReviewToContainer(String reviewText) {
        TextView reviewTextView = new TextView(this);
        reviewTextView.setText(reviewText);
        reviewTextView.setTextSize(16);
        reviewTextView.setPadding(16, 16, 16, 16);
        reviewTextView.setGravity(Gravity.LEFT);
        reviewTextView.setBackgroundResource(android.R.drawable.divider_horizontal_bright);

        reviewContainer.addView(reviewTextView);

        // ScrollView 하단으로 자동 스크롤
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}
