package com.example.book;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.book.book_write.book_write_main;
import com.example.book.categori.cate1;
import com.example.book.databinding.MainBinding;
import com.example.book.databinding.MypageBinding;
import com.example.book.jjim.jjim;
import com.example.book.mypage.mypage;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {
    private static final String PHP_SCRIPT_URL = "http://192.168.33.224/home.php";

    private MainBinding binding;
    private int[] images = {R.drawable.advertisement1, R.drawable.advertisement2, R.drawable.advertisement3};
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;

    private ImageButton book1Button;
    private ImageButton book2Button;
    private ImageButton book3Button;


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

        // ActionBar 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // View Binding 설정
        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 이미지버튼 초기화
        book1Button = findViewById(R.id.book_1);
        book2Button = findViewById(R.id.book_2);
        book3Button = findViewById(R.id.book_3);

        // 데이터 가져오기
        fetchData();

        // 검색 바 클릭 이벤트 리스너 추가
        binding.searchbar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, search.class);
            startActivity(intent);
        });

        // 검색 TextView 클릭 리스너 설정
        TextView txtSearch = findViewById(R.id.txt_search);
        txtSearch.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, search.class);
            startActivity(intent);
        });

        // 이미지 버튼 클릭 리스너 추가
        book1Button.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, com.example.book.bookinfo.book_info1.class);
            startActivity(intent);
        });

        book2Button.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, com.example.book.bookinfo.book_info2.class);
            startActivity(intent);
        });

        book3Button.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, com.example.book.bookinfo.book_info3.class);
            startActivity(intent);
        });


        // ViewPager2 설정
        ViewPager2 viewPager2 = binding.viewPager;
        ImageAdapter adapter = new ImageAdapter(images);
        viewPager2.setAdapter(adapter);

        // TabLayout 설정
        TabLayout tabLayout = binding.tabLayout;
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    // Tab 설정, 여기서는 아무것도 설정하지 않음
                }).attach();

        // 자동 스와이프 설정
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager2.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // 3초마다 스와이프
            }
        };
        handler.postDelayed(runnable, 3000);

        ImageButton categoryButton = findViewById(R.id.bar_category);
        categoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, cate1.class);
            startActivity(intent);
        });

        ImageButton dookreadingButton = findViewById(R.id.bar_dookreading);
        dookreadingButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, book_write_main.class);
            startActivity(intent);
        });

        // Heart 버튼 클릭 이벤트 설정
        ImageButton heartButton = findViewById(R.id.bar_heart);
        heartButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, jjim.class);
            startActivity(intent);
        });

        ImageButton mypageButton = findViewById(R.id.bar_mypage);
        mypageButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, mypage.class);
            startActivity(intent);
        });

        // ic_n1 버튼 클릭 이벤트 설정
        ImageButton icN1Button = findViewById(R.id.ic_n1);
        icN1Button.setOnClickListener(v -> {
            showPopupWindow(v, "유저들이 독후감을 작성한 횟수에 따라 정렬됩니다.");
        });
    }

    private void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, PHP_SCRIPT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            // JSON 배열 데이터 파싱
                            JSONArray jsonArray = new JSONArray(response);


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
    }

    private void setRecordData(JSONObject record, TextView titleView, TextView authorView) throws Exception {
        String title = record.getString("title");
        titleView.setText(title);
        String author = record.getString("author");
        authorView.setText(author);
    }

    private void showPopupWindow(View anchorView, String message) {
        // 팝업 윈도우 레이아웃 설정
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        // 팝업 윈도우 텍스트 설정
        TextView messageTextView = popupView.findViewById(R.id.popup_text);
        messageTextView.setText(message);

        // 팝업 윈도우 생성
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // 팝업 윈도우 외부 클릭 시 닫히게 설정
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // 팝업 윈도우 배경 설정
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        // 팝업 윈도우 위치 설정
        popupWindow.showAsDropDown(anchorView, 0, 0, Gravity.END);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
