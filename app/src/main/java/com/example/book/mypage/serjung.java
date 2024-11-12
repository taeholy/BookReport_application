package com.example.book.mypage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // 추가된 줄
import android.widget.ImageButton; // 추가된 줄
import android.widget.Switch; // 추가된 줄
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class serjung extends AppCompatActivity {

    private Switch switch1;
    private Switch switch2;
    private Switch switch3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serjung); // XML 파일 이름은 hwan으로 설정합니다.

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        loadSettings(); // 액티비티가 생성될 때 설정을 로드합니다.

        Button saveButton = findViewById(R.id.button2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 설정을 저장합니다.
                saveSettings();

                // 저장 완료 메시지 표시
                Toast.makeText(serjung.this, "변경사항이 저장되었습니다", Toast.LENGTH_SHORT).show();
            }
        });


        ImageButton backButton = findViewById(R.id.back_botton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings(); // 뒤로 가기 버튼을 눌렀을 때도 설정을 저장합니다.
                finish(); // HwanActivity를 종료하고 메인 페이지로 돌아갑니다.
            }
        });
    }

    private void saveSettings() {
        // SharedPreferences를 사용하여 설정을 저장합니다
        boolean isSwitch1Checked = switch1.isChecked();
        boolean isSwitch2Checked = switch2.isChecked();
        boolean isSwitch3Checked = switch3.isChecked();

        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("switch1", isSwitch1Checked);
        editor.putBoolean("switch2", isSwitch2Checked);
        editor.putBoolean("switch3", isSwitch3Checked);
        editor.apply();
    }

    private void loadSettings() {
        // SharedPreferences를 사용하여 설정을 로드합니다
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isSwitch1Checked = sharedPreferences.getBoolean("switch1", false);
        boolean isSwitch2Checked = sharedPreferences.getBoolean("switch2", false);
        boolean isSwitch3Checked = sharedPreferences.getBoolean("switch3", false);

        switch1.setChecked(isSwitch1Checked);
        switch2.setChecked(isSwitch2Checked);
        switch3.setChecked(isSwitch3Checked);
    }
}
