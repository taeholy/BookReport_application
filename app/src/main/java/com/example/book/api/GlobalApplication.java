package com.example.book.api;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Kakao SDK 초기화
        KakaoSdk.init(this, "522a35fb9f2bd40c1207f9e955f2f3b9"); // NATIVE_APP_KEY에 실제 앱 키를 입력하세요
    }
}
