package com.example.gogoroutine.activity_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gogoroutine.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.gogoroutine_icon_blue,
            R.drawable.ic_baseline_add_circle_outline_24
    };

    public String[] slide_texts={
            "안녕하세요 고고로틴입니다~\n간단한 사용법에 대해서 말씀드릴게요!",
            "먼저 메인화면인 루틴목록입니다.\n가장 처음에는 하단의 + 버튼을 누르고  "

    };
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
