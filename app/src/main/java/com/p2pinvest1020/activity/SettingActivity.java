package com.p2pinvest1020.activity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.p2pinvest1020.R;
import com.p2pinvest1020.utils.BitmapUtils;
import com.p2pinvest1020.utils.UiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @Bind(R.id.tv_user_change)
    TextView tvUserChange;
    @Bind(R.id.btn_user_logout)
    Button btnUserLogout;
    @Bind(R.id.base_title)
    TextView baseTitle;
    @Bind(R.id.base_back)
    ImageView baseBack;
    @Bind(R.id.base_setting)
    ImageView baseSetting;

    private static final int CAMERA = 100;
    private static final int PICTURE = 200;
    private File filesDir;
    private FileOutputStream os;


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    protected void initTitle() {
        baseTitle.setText("设置");
        baseSetting.setVisibility(View.GONE);

    }

    @Override
    public int getLayoutid() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.iv_user_icon, R.id.base_back, R.id.btn_user_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                new AlertDialog.Builder(this).setTitle("选择来源").setItems(new String[]{"拍照", "图库"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA);
                                break;
                            case 1:
                                //打开系统图库程序，选择图片
                                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(picture, PICTURE);
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.base_back:
                finish();
                break;
            case R.id.btn_user_logout:
                /**
                 将SP清除
                 将File删除
                 销毁所有的Activity
                 重新进入主界面
                 */
                clearFile();
                clearSp();
                removeAllActivity();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
                break;
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA && resultCode == RESULT_OK && data != null) {
            //拍照
            Bundle bundle = data.getExtras();
            //获取相机传回的数据，转换成图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            //修剪
            bitmap = BitmapUtils.zoom(bitmap, UiUtils.dp2px(62), UiUtils.dp2px(62));
            Bitmap circleBitmap = BitmapUtils.circleBitmap(bitmap);
            //设置图片
            ivUserIcon.setImageBitmap(circleBitmap);
            //保存到本地
            saveImage(circleBitmap);

        } else if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            //图库 解析图库的操作，跟android系统有很大相关性。不同的系统使用uri的authority有很大不同。
            Uri dataData = data.getData();
            //得到图库的uri
            String path = getPath(dataData);
            //储存---内存
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            //裁剪
            Bitmap circleImage = BitmapUtils.circleBitmap(bitmap);
            //设置图片
            ivUserIcon.setImageBitmap(circleImage);
            //保存
            saveImage(bitmap);

        }
    }

    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }


        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    private void saveImage(Bitmap circleBitmap) {
        try {
            //判断sd卡是否处于挂载状态
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                //sdcard/Android/data/应用包名/file/...jpg
                filesDir = getExternalFilesDir("");
            } else {
                filesDir = getFilesDir();
            }
            File file = new File(filesDir, "icon.png");
            os = new FileOutputStream(file);
            circleBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            //保存当前是否有更新
            saveImage(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
