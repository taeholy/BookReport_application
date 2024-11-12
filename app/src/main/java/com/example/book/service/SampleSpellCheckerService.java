package com.example.book.service; // 클래스 위치에 맞게 경로 수정

import android.service.textservice.SpellCheckerService;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

public class SampleSpellCheckerService extends SpellCheckerService {
    @Override
    public Session createSession() {
        return new SpellCheckerSession();
    }

    private static class SpellCheckerSession extends SpellCheckerService.Session {
        @Override
        public void onCreate() {
            // 초기화 작업 수행
        }

        @Override
        public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {
            // 여기에 맞춤법 검사 결과를 반환하는 로직을 구현
            return new SuggestionsInfo(SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO, new String[]{"suggestion"});
        }
    }
}
