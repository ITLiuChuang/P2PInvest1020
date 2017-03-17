package com.p2pinvest1020.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.p2pinvest1020.R;
import com.p2pinvest1020.activity.MainActivity;
import com.p2pinvest1020.activity.SettingActivity;
import com.p2pinvest1020.base.UserInfo;
import com.p2pinvest1020.command.AppNetConfig;
import com.p2pinvest1020.utils.BitmapUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/3/10.
 */
public class PropertyFragment extends BaseFragment {


    /* @Bind(R.id.tv_settings)
     TextView tvSettings;*/
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
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.base_back)
    ImageView baseBack;
    @Bind(R.id.base_setting)
    ImageView baseSetting;

    @Override
    protected String getChildUrl() {
        return null;
    }

    @Override
    protected void initData(String json) {
        baseTitle.setText("资产");
        baseBack.setVisibility(View.GONE);
        MainActivity mainActivity = (MainActivity) getActivity();
        UserInfo user = mainActivity.getUser();
        //设置用户名
        tvMeName.setText(user.getData().getName());
        //设置头像
        /*Picasso.with(getActivity())
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
                }).into(ivMeIcon);*/
        Picasso.with(getActivity()).load(AppNetConfig.BASE_URL + "/images/tx.png")
                .transform(new CropCircleTransformation())
                .transform(
                        new ColorFilterTransformation(Color
                                .parseColor("#66FFccff")))
                //第二个参数值越大越模糊
                .transform(new BlurTransformation(getActivity(), 10))
                .into(ivMeIcon);

    }

    @Override
    protected void initListener() {

    }

    @Override
    public int getLayoutid() {
        return R.layout.fragment_property;
    }


    @OnClick({R.id.base_setting, R.id.iv_me_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

                break;
            case R.id.iv_me_icon:
                new AlertDialog.Builder(getActivity()).setTitle("选择来源").setItems(new String[]{"拍照", "图库"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Toast.makeText(getActivity(), "aaaa", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(getActivity(), "bbbbb", Toast.LENGTH_SHORT).show();

                                        break;
                                }
                            }
                        }
                ).show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        boolean updatae = activity.isUpdatae();
        if (updatae) {
            //判断sd卡是否处于挂载状态
            File filesDir;
            FileInputStream is = null;
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //sdcard/Android/data/应用包名/file/...jpg
                    filesDir = getActivity().getExternalFilesDir("");
                } else {
                    filesDir = getActivity().getFilesDir();
                }
                File file = new File(filesDir, "icon.png");
                if (file.exists()) {
                    //输出流
                    is = new FileInputStream(file);
                    //第一个参数是图片的格式，第二个参数是图片的质量数值大的大质量高，第三个是输出流
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Bitmap circleBitmap = BitmapUtils.circleBitmap(bitmap);
                    ivMeIcon.setImageBitmap(circleBitmap);
                    activity.saveImage(false);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
