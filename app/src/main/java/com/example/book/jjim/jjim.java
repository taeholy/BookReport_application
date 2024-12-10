package com.example.book.jjim;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.book.HomeActivity;
import com.example.book.R;
import com.example.book.book_write.book_write_main;
import com.example.book.categori.cate1;
import com.example.book.mypage.mypage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class jjim extends AppCompatActivity {

    private static final String PHP_SCRIPT_URL = "http://192.168.33.224/jjim.php";

    private TextView bookTitle1, bookAuthor1;
    private ImageView bookImage1;
    private ImageButton heartButton1;

    private TextView bookTitle2, bookAuthor2;
    private ImageView bookImage2;
    private ImageButton heartButton2;

    private TextView bookTitle3, bookAuthor3;
    private ImageView bookImage3;
    private ImageButton heartButton3;

    private TextView bookTitle4, bookAuthor4;
    private ImageView bookImage4;
    private ImageButton heartButton4;

    public static class ImageLoader {
        public static void load(final String url, final ImageView view) {
            ExecutorService executors = Executors.newSingleThreadExecutor();
            executors.execute(() -> {
                Bitmap image;
                try {
                    image = BitmapFactory.decodeStream(new URL(url).openStream());
                    final Bitmap finalImage = image;
                    view.post(() -> view.setImageBitmap(finalImage));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jjim);

        findViewById(R.id.bar_category).setOnClickListener(v -> startActivity(new Intent(this, cate1.class)));
        findViewById(R.id.bar_dookreading).setOnClickListener(v -> startActivity(new Intent(this, book_write_main.class)));
        findViewById(R.id.bar_home).setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        findViewById(R.id.bar_mypage).setOnClickListener(v -> startActivity(new Intent(this, mypage.class)));

        initializeViews();

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(new StringRequest(Request.Method.GET, PHP_SCRIPT_URL,
                this::handleResponse, this::handleError));
    }

    private void initializeViews() {
        bookTitle1 = findViewById(R.id.bookname1);
        bookAuthor1 = findViewById(R.id.writer1);
        bookImage1 = findViewById(R.id.image1);
        heartButton1 = findViewById(R.id.heartButton);

        bookTitle2 = findViewById(R.id.bookname2);
        bookAuthor2 = findViewById(R.id.writer2);
        bookImage2 = findViewById(R.id.image2);
        heartButton2 = findViewById(R.id.heartButton2);

        bookTitle3 = findViewById(R.id.bookname3);
        bookAuthor3 = findViewById(R.id.writer3);
        bookImage3 = findViewById(R.id.image3);
        heartButton3 = findViewById(R.id.heartButton3);

        bookTitle4 = findViewById(R.id.bookname4);
        bookAuthor4 = findViewById(R.id.writer4);
        bookImage4 = findViewById(R.id.image4);
        heartButton4 = findViewById(R.id.heartButton4);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            if (jsonArray.length() > 0) setRecordData(jsonArray.getJSONObject(0), bookTitle1, bookAuthor1, bookImage1, heartButton1);
            if (jsonArray.length() > 1) setRecordData(jsonArray.getJSONObject(1), bookTitle2, bookAuthor2, bookImage2, heartButton2);
            if (jsonArray.length() > 2) setRecordData(jsonArray.getJSONObject(2), bookTitle3, bookAuthor3, bookImage3, heartButton3);
            if (jsonArray.length() > 3) setRecordData(jsonArray.getJSONObject(3), bookTitle4, bookAuthor4, bookImage4, heartButton4);

        } catch (JSONException e) {
            Log.e("JSON Parsing", "Error parsing JSON response", e);
        }
    }

    private void handleError(VolleyError error) {
        Log.e("Error", "Error occurred", error);
    }

    private void setRecordData(JSONObject record, TextView titleView, TextView authorView, ImageView imageView, ImageButton heartButton) {
        try {
            String title = record.getString("title");
            String author = record.getString("author");
            String imgUrl = record.getString("img");
            String bookId = record.getString("book_id");

            titleView.setText(title);
            authorView.setText(author);
            ImageLoader.load(imgUrl, imageView);

            boolean isJjim = record.getInt("jjim") == 1;
            heartButton.setImageResource(isJjim ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
            heartButton.setTag(isJjim);

            heartButton.setOnClickListener(view -> showDeleteConfirmationDialog(bookId));

            imageView.setOnClickListener(view -> {
                Intent intent = new Intent(jjim.this, com.example.book.bookinfo.book_info.class);
                intent.putExtra("BOOK_ID", bookId);
                intent.putExtra("BOOK_TITLE", title);
                intent.putExtra("BOOK_AUTHOR", author);
                intent.putExtra("BOOK_IMG", imgUrl);
                startActivity(intent);
            });

        } catch (JSONException e) {
            Log.e("RecordData", "Error parsing record data", e);
            Toast.makeText(this, "Error loading book data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteConfirmationDialog(String bookId) {
        new AlertDialog.Builder(this)
                .setTitle("삭제 확인")
                .setMessage("찜 삭제하시겠습니까?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    updateJjimStatus(bookId, 0);
                    refreshPage();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void refreshPage() {
        finish();
        startActivity(getIntent());
    }

    private void updateJjimStatus(String bookId, int status) {
        new UpdateJjimTask().execute(bookId, status);
    }

    private class UpdateJjimTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            String bookId = (String) params[0];
            int status = (int) params[1];

            try {
                URL url = new URL("http://192.168.33.224/update_jjim.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                String postData = "book_id=" + bookId + "&jjim=" + status;
                connection.getOutputStream().write(postData.getBytes());
                connection.getOutputStream().flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) result.append(line);

                reader.close();
                return result.toString();

            } catch (Exception e) {
                Log.e("UpdateJjimTask", "Error: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (!"Success".equals(result)) {
                Toast.makeText(jjim.this, "Error updating jjim status", Toast.LENGTH_SHORT).show();
            }
        }
    }
}