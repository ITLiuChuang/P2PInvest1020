package com.p2pinvest1020.fragment;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.p2pinvest1020.R;
import com.p2pinvest1020.activity.MainActivity;
import com.p2pinvest1020.base.UserInfo;
import com.p2pinvest1020.command.AppNetConfig;
import com.p2pinvest1020.utils.BitmapUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/10.
 */
public class PropertyFragment extends BaseFragment {


    @Bind(R.id.tv_settings)
    TextView tvSettings;
    @Bind(R.id.iv_me_icon)
    ImageView ivMeIcon;
    @Bind(R.id.rl_me_icon)
    RelativeLayout rlMeIcon;
    @Bind(R.id.tv_me_name)
    TextView tvMeName;
    @Bind(R.id.rl_me)
    RelativeLayout rlMe;
    @Bind(R.id.recharge)
    ImageView recharge;
    @Bind(R.id.withdraw)
    ImageView withdraw;
    @Bind(R.id.ll_touzi)
    TextView llTouzi;
    @Bind(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @Bind(R.id.ll_zichan)
    TextView llZichan;

    @Override
    protected String getChildUrl() {
        return null;
    }

    @Override
    protected void initData(String json) {
        MainActivity mainActivity = (MainActivity) getActivity();
        UserInfo user = mainActivity.getUser();
        //设置用户名
        tvMeName.setText(user.getData().getName());
        //设置头像
        Picasso.with(getActivity())
                .load(AppNetConfig.BASE_URL + "/images/tx.png")
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap bitmap) {
                        Bitmap circleBitmap = BitmapUtils.circleBitmap(bitmap);
                        bitmap.recycle(); //必须把原来的释放掉
                        return circleBitmap;
                    }

                    @Override
                    public String key() {
                        return "";
                    }
                }).into(ivMeIcon);

    }

    @Override
    protected void initListener() {

    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_property;
    }


}
