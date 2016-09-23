package com.example.nanfu.libs;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.nanfu.libs.view.CircleMenuLayout;
import com.example.nanfu.libs.view.CustomRateView;
import com.example.nanfu.libs.view.RangeSliderView;
import com.example.nanfu.mylib.app.BaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class TestActivity extends Activity {

    private CustomRateView mCustomRateView;

    private List<Float> persent= Arrays.asList(30f,50f,60f,80f,10f,70f);
    private List<Integer> colors=Arrays.asList(Color.RED,Color.BLACK,Color.BLUE,Color.GREEN,Color.YELLOW,Color.GRAY);

    private RangeSliderView rangeSliderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        mCustomRateView= (CustomRateView) findViewById(R.id.customRateView);
//        mCustomRateView.setPersentAndColor(persent,colors);

        rangeSliderView= (RangeSliderView) findViewById(R.id.rangeSliderView);
        rangeSliderView.setSchedule(6);
    }
}
