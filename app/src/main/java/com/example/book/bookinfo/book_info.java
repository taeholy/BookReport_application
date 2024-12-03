package com.example.book.bookinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;
import com.example.book.book_write.WriteDiaryActivity;
import com.example.book.review;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class book_info extends AppCompatActivity {

    private boolean isHeartFilled = false;
    private ImageView imageView;
    private String bookId, bookImageUrl;
    private ImageButton heartButton;

    // ImageLoader 클래스 추가
    public static class ImageLoader {
        public static void load(final String url, final ImageView view) {
            ExecutorService executors = Executors.newSingleThreadExecutor();
            executors.execute(() -> {
                Bitmap image;
                try {
                    Log.d("ImageLoader", "Loading image from URL: " + url);
                    image = BitmapFactory.decodeStream(new URL(url).openStream());
                    final Bitmap finalImage = image;
                    view.post(() -> view.setImageBitmap(finalImage));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ImageLoader", "Error loading image: " + e.getMessage());
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        imageView = findViewById(R.id.book_img);
        heartButton = findViewById(R.id.heart_button);

        Intent intent = getIntent();
        bookId = intent.getStringExtra("BOOK_ID");

        if (bookId != null) {
            new FetchBookDetailsTask().execute(bookId);
        }

        heartButton.setOnClickListener(v -> showJjimConfirmationDialog());

        ImageButton backButton = findViewById(R.id.back_botton);
        backButton.setOnClickListener(v -> finish());

        Button buttButton = findViewById(R.id.butt);
        Button butt2Button = findViewById(R.id.butt2);

        // Write Diary 버튼
        buttButton.setOnClickListener(v -> {
            TextView titleView = findViewById(R.id.titlebookname);
            String bookTitle = titleView.getText().toString();

            Intent intent1 = new Intent(book_info.this, WriteDiaryActivity.class);
            intent1.putExtra("BOOK_TITLE", bookTitle);
            startActivity(intent1);
        });

        // Review 버튼
        butt2Button.setOnClickListener(v -> {
            TextView titleView = findViewById(R.id.titlebookname);
            String bookTitle = titleView.getText().toString();

            Intent intent2 = new Intent(book_info.this, review.class);
            intent2.putExtra("BOOK_TITLE", bookTitle);
            intent2.putExtra("BOOK_IMAGE_URL", bookImageUrl); // 동적 URL 전달
            startActivity(intent2);
        });
    }

    private void showJjimConfirmationDialog() {
        String message = isHeartFilled ? "찜 삭제하시겠습니까?" : "찜 등록하시겠습니까?";
        new AlertDialog.Builder(this)
                .setTitle(isHeartFilled ? "찜 삭제" : "찜 등록")
                .setMessage(message)
                .setPositiveButton("Yes", (dialog, which) -> {
                    isHeartFilled = !isHeartFilled;
                    heartButton.setImageResource(isHeartFilled ? R.drawable.heert : R.drawable.heart);
                    updateJjimStatus(isHeartFilled ? 1 : 0);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void updateJjimStatus(int status) {
        if (bookId != null) {
            new UpdateJjimTask().execute(bookId, status);
        } else {
            Toast.makeText(this, "Error: Book ID is null", Toast.LENGTH_SHORT).show();
        }
    }

    private class UpdateJjimTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String bookId = (String) params[0];
            int status = (int) params[1];

            try {
                String urlString = "http://192.168.33.198/update_jjim.php";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String postData = "book_id=" + bookId + "&jjim=" + status;
                connection.getOutputStream().write(postData.getBytes());
                connection.getOutputStream().flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();

            } catch (Exception e) {
                Log.e("UpdateJjimTask", "Error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.equals("Success")) {
                Toast.makeText(book_info.this, "찜 상태 변경 완료", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(book_info.this, "Error updating jjim status", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class FetchBookDetailsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String bookId = params[0];
            String urlString = "http://192.168.33.198/getBookInfo.php?book_id=" + bookId;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                reader.close();
                return result.toString();

            } catch (Exception e) {
                Log.e("FetchBookDetailsTask", "Error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String title = jsonObject.getString("title");
                    String author = jsonObject.getString("author");
                    String genre = jsonObject.getString("genre");
                    bookImageUrl = jsonObject.getString("img"); // 이미지 URL 저장
                    int jjim = jsonObject.getInt("jjim");

                    TextView titleView = findViewById(R.id.titlebookname);
                    TextView authorView = findViewById(R.id.tt3);
                    TextView genreView = findViewById(R.id.tt4);
                    ImageView imageView1 = findViewById(R.id.book_img);

                    titleView.setText(title);
                    authorView.setText(author);
                    genreView.setText(genre);
                    ImageLoader.load(bookImageUrl, imageView1);

                    isHeartFilled = (jjim == 1);
                    heartButton.setImageResource(isHeartFilled ? R.drawable.heert : R.drawable.heart);

                } catch (JSONException e) {
                    Log.e("FetchBookDetailsTask", "JSON Parsing error: " + e.getMessage());
                    Toast.makeText(book_info.this, "Error parsing book details.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(book_info.this, "No book details received.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
