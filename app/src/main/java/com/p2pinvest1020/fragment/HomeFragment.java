package com.p2pinvest1020.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.p2pinvest1020.R;
import com.p2pinvest1020.bean.HomeBean;
import com.p2pinvest1020.command.AppNetConfig;
import com.p2pinvest1020.ui.MyProgress;
import com.p2pinvest1020.utils.LoadNet;
import com.p2pinvest1020.utils.LoadNetHttp;
import com.p2pinvest1020.utils.ThreadPool;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/10.
 */
public class HomeFragment extends Fragment {

    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.base_back)
    ImageView baseBack;
    @Bind(R.id.base_setting)
    ImageView baseSetting;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tv_home_product)
    TextView tvHomeProduct;
    @Bind(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @Bind(R.id.home_progress)
    MyProgress homeProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListener();
    }

    private void initListener() {
        baseTitle.setText("首页");
        baseSetting.setVisibility(View.INVISIBLE);
        baseBack.setVisibility(View.INVISIBLE);

    }

    private void initData() {

        /*
        * 二次封装
        * 为什么要二次封装
        *
        * 第一  调用的方便
        * 第二  修改和维护方便
        * */
        LoadNet.getDataPost(AppNetConfig.INDEX, new LoadNetHttp() {
            @Override
            public void success(String context) {
//                Log.i("http", "success: " + context);
                HomeBean homeBean = JSON.parseObject(context, HomeBean.class);
                tvHomeYearrate.setText(homeBean.getProInfo().getYearRate() + "%");
                tvHomeProduct.setText(homeBean.getProInfo().getName());
                //展示UI
                initProgress(homeBean.getProInfo());
                initBanner(homeBean);
            }

            @Override
            public void failure(String error) {
                Log.i("http", "failure: " + error);
            }
        });
    }

    private void initProgress(final HomeBean.ProInfoBean proInfo) {
        ThreadPool.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                int integer = Integer.parseInt(proInfo.getProgress());
                for (int i = 0; i < integer; i++) {
                    SystemClock.sleep(20);
                    homeProgress.setProgress(i);
                }
            }
        });
    }

    private void initBanner(HomeBean homeBean) {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //转成url集合
        ArrayList<String> url = new ArrayList<>();

        for (int i = 0; i < homeBean.getImageArr().size(); i++) {
            url.add(AppNetConfig.BASE_URL + homeBean.getImageArr().get(i).getIMAURL());
        }

        //设置图片
        banner.setImages(url);
        //banner设置方法调用完毕第最后调用
        banner.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);
        }
    }
}
