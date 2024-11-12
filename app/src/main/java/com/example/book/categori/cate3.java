package com.example.book.categori;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.book.jjim.jjim;
import com.example.book.mypage.mypage;
import com.example.book.search;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class cate3 extends AppCompatActivity {

    private static final String PHP_SCRIPT_URL = "http://192.168.33.198/cate3.php";

    private TextView recodeview1, recodeview2, recodeview3, recodeview4, recodeview5, recodeview6
            ,recodeview7, recodeview8, recodeview9, recodeview10, recodeview11, recodeview12
            ,recodeview13, recodeview14, recodeview15, recodeview16, recodeview17, recodeview18
            ,recodeview19,recodeview20;
    private ImageView imageview1, imageview2, imageview3, imageview4, imageview5, imageview6
            ,imageview7, imageview8, imageview9, imageview10, imageview11, imageview12
            ,imageview13, imageview14, imageview15, imageview16, imageview17, imageview18
            ,imageview19,imageview20;
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
        setContentView(R.layout.cate_3);

        ImageButton dookreadingButton = findViewById(R.id.bar_dookreading);
        dookreadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 독후감Activity로 이동
                Intent intent = new Intent(cate3.this, book_write_main.class);
                startActivity(intent);
            }
        });

        ImageButton homeButton = findViewById(R.id.bar_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mainActivity로 이동
                Intent intent = new Intent(cate3.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ImageButton HeartButton = findViewById(R.id.bar_heart);
        HeartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mainActivity로 이동
                Intent intent = new Intent(cate3.this, jjim.class);
                startActivity(intent);
            }
        });

        ImageButton mypageButton = findViewById(R.id.bar_mypage);
        mypageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mypageActivity로 이동
                Intent intent = new Intent(cate3.this, mypage.class);
                startActivity(intent);
            }
        });



        recodeview1 = findViewById(R.id.record1);
        recodeview2 = findViewById(R.id.record2);
        recodeview3 = findViewById(R.id.record3);
        recodeview4 = findViewById(R.id.record4);
        recodeview5 = findViewById(R.id.record5);
        recodeview6 = findViewById(R.id.record6);
        recodeview7 = findViewById(R.id.record7);
        recodeview8 = findViewById(R.id.record8);
        recodeview9 = findViewById(R.id.record9);
        recodeview10 = findViewById(R.id.record10);
        recodeview11 = findViewById(R.id.record11);
        recodeview12 = findViewById(R.id.record12);
        recodeview13 = findViewById(R.id.record13);
        recodeview14 = findViewById(R.id.record14);
        recodeview15 = findViewById(R.id.record15);
        recodeview16 = findViewById(R.id.record16);
        recodeview17 = findViewById(R.id.record17);
        recodeview18 = findViewById(R.id.record18);
        recodeview19 = findViewById(R.id.record19);
        recodeview20 = findViewById(R.id.record20);

        imageview1 = findViewById(R.id.book_pic1);
        imageview2 = findViewById(R.id.book_pic2);
        imageview3 = findViewById(R.id.book_pic3);
        imageview4 = findViewById(R.id.book_pic4);
        imageview5 = findViewById(R.id.book_pic5);
        imageview6 = findViewById(R.id.book_pic6);
        imageview7 = findViewById(R.id.book_pic7);
        imageview8 = findViewById(R.id.book_pic8);
        imageview9 = findViewById(R.id.book_pic9);
        imageview10 = findViewById(R.id.book_pic10);
        imageview11 = findViewById(R.id.book_pic11);
        imageview12 = findViewById(R.id.book_pic12);
        imageview13 = findViewById(R.id.book_pic13);
        imageview14 = findViewById(R.id.book_pic14);
        imageview15 = findViewById(R.id.book_pic15);
        imageview16 = findViewById(R.id.book_pic16);
        imageview17 = findViewById(R.id.book_pic17);
        imageview18 = findViewById(R.id.book_pic18);
        imageview19 = findViewById(R.id.book_pic19);
        imageview20 = findViewById(R.id.book_pic20);


        RequestQueue queue = Volley.newRequestQueue(this);

        // 카테고리 TextViews 초기화 및 클릭 리스너 설정
        setUpCategoryClickListeners();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, PHP_SCRIPT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            // JSON 배열 데이터 파싱
                            JSONArray jsonArray = new JSONArray(response);

                            // 각 레코드 처리
                            setRecordData(jsonArray.getJSONObject(0), recodeview1, imageview1);
                            setRecordData(jsonArray.getJSONObject(1), recodeview2, imageview2);
                            setRecordData(jsonArray.getJSONObject(2), recodeview3, imageview3);
                            setRecordData(jsonArray.getJSONObject(3), recodeview4, imageview4);
                            setRecordData(jsonArray.getJSONObject(4), recodeview5, imageview5);
                            setRecordData(jsonArray.getJSONObject(5), recodeview6, imageview6);
                            setRecordData(jsonArray.getJSONObject(6), recodeview7, imageview7);
                            setRecordData(jsonArray.getJSONObject(7), recodeview8, imageview8);
                            setRecordData(jsonArray.getJSONObject(8), recodeview9, imageview9);
                            setRecordData(jsonArray.getJSONObject(9), recodeview10, imageview10);
                            setRecordData(jsonArray.getJSONObject(10), recodeview11, imageview11);
                            setRecordData(jsonArray.getJSONObject(11), recodeview12, imageview12);
                            setRecordData(jsonArray.getJSONObject(12), recodeview13, imageview13);
                            setRecordData(jsonArray.getJSONObject(13), recodeview14, imageview14);
                            setRecordData(jsonArray.getJSONObject(14), recodeview15, imageview15);
                            setRecordData(jsonArray.getJSONObject(15), recodeview16, imageview16);
                            setRecordData(jsonArray.getJSONObject(16), recodeview17, imageview17);
                            setRecordData(jsonArray.getJSONObject(17), recodeview18, imageview18);
                            setRecordData(jsonArray.getJSONObject(18), recodeview19, imageview19);
                            setRecordData(jsonArray.getJSONObject(19), recodeview20, imageview20);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("JSON Parsing", "Error parsing JSON response", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Error occurred ", error);
            }
        });

        queue.add(stringRequest);

        // 검색 TextView 클릭 리스너 설정
        TextView txtSearch = findViewById(R.id.txt_search);
        txtSearch.setOnClickListener(view -> {
            Intent intent = new Intent(cate3.this, search.class);
            startActivity(intent);
        });
    }

    private void setUpCategoryClickListeners() {
        findViewById(R.id.novel).setOnClickListener(view -> startCategoryActivity(cate1.class));
        findViewById(R.id.comic).setOnClickListener(view -> startCategoryActivity(cate2.class));
        findViewById(R.id.education).setOnClickListener(view -> startCategoryActivity(cate3.class));
        findViewById(R.id.poem).setOnClickListener(view -> startCategoryActivity(cate4.class));
        findViewById(R.id.cook).setOnClickListener(view -> startCategoryActivity(cate5.class));
        findViewById(R.id.health).setOnClickListener(view -> startCategoryActivity(cate6.class));
        findViewById(R.id.hobby).setOnClickListener(view -> startCategoryActivity(cate7.class));
        findViewById(R.id.religion).setOnClickListener(view -> startCategoryActivity(cate8.class));
        findViewById(R.id.science).setOnClickListener(view -> startCategoryActivity(cate9.class));
        findViewById(R.id.magazine).setOnClickListener(view -> startCategoryActivity(cate10.class));
        findViewById(R.id.teenager).setOnClickListener(view -> startCategoryActivity(cate11.class));
    }

    private void setRecordData(JSONObject record, TextView textView, ImageView imageView) throws Exception {
        String title = record.getString("title");
        if (title.length() > 8) {
            title = title.substring(0, 5) + "\n" + title.substring(5);
        }
        textView.setText(title);
        String author = record.getString("author");
        textView.append("\n" + author);
        String imgUrl = record.getString("img");
        String bookId = record.getString("book_id"); // Assuming 'book_id' is the key for book ID in JSON response
        ImageLoader.load(imgUrl, imageView);

        Log.d("cate1", "Book ID: " + bookId); // Log the book ID

        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(cate3.this, com.example.book.bookinfo.book_info.class);
            intent.putExtra("BOOK_ID", bookId);
            Log.d("cate1", "Starting book_info activity with Book ID: " + bookId); // Log the intent data
            startActivity(intent);
        });
    }

    private void startCategoryActivity(Class<?> categoryClass) {
        Intent intent = new Intent(this, categoryClass);
        startActivity(intent);
    }
}
