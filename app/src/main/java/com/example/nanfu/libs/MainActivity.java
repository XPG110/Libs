package com.example.nanfu.libs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nanfu.libs.bean.MovieEntity;
import com.example.nanfu.libs.fragment.VpSimpleFragment;
import com.example.nanfu.libs.network.HttpResult;
import com.example.nanfu.libs.network.IHttpResult;
import com.example.nanfu.libs.network.MovieApi;
import com.example.nanfu.libs.network.ProgressDialogSubscribe;
import com.example.nanfu.libs.view.CustomIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/25.
 */
public class MainActivity extends FragmentActivity {

    private String data="";

    private Button toTestBtn;
    private Context mContext;

    @BindView(R.id.getMovie_BN)
    Button getMovieBtn;
    @BindView(R.id.result_TV)
    TextView result_TV;
    List<Fragment> mTabContents = new ArrayList<Fragment>();

    private List<String> mDatas = Arrays.asList("短信", "收藏", "推荐");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        Person person=new Person.Builder().age(10).build();

        ButterKnife.bind(this);
        result_TV= (TextView) findViewById(R.id.result_TV);

        Observable observable=Observable.just(data);
        observable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });

        toTestBtn= (Button) findViewById(R.id.toTest);
        toTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,TestActivity.class));
            }
        });
        testIndicator();
    }

    private void testIndicator(){
        CustomIndicator indicator= (CustomIndicator) findViewById(R.id.tabIndicator);
        indicator.setTabItemText(mDatas);
        ViewPager viewPager= (ViewPager) findViewById(R.id.vp);

        for (String data : mDatas) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);
        }
        FragmentPagerAdapter mAdapter;
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount()
            {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position)
            {
                return mTabContents.get(position);
            }
        };
        viewPager.setAdapter(mAdapter);
        indicator.setViewPager(viewPager,0);
    }
    @OnClick(R.id.getMovie_BN)
    public void getMovie(View view){

        MovieApi.getInstance().getMovie(new IHttpResult<MovieEntity>() {
            @Override
            public void onSuccess(MovieEntity movieEntity) {
//                Log.d("Tag",movieEntity.getTitle());
            }

            @Override
            public void onFailure(MovieEntity movieEntity) {

            }

            @Override
            public void onCancel() {

            }
        });

        MovieApi.getInstance().getMovie1(new ProgressDialogSubscribe<MovieEntity>(new IHttpResult<MovieEntity>() {

            @Override
            public void onSuccess(MovieEntity movieEntity) {
                Log.d("Tag", movieEntity.getTitle());
            }

            @Override
            public void onFailure(MovieEntity movieEntity) {

            }

            @Override
            public void onCancel() {

            }
        }, this));
    }


}
