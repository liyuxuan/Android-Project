package cn.com.core.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.com.core.R;
import cn.com.core.tool.SysTool;

public class ResignIdCardActivity extends AppCompatActivity {
    public static ResignIdCardActivity mContext;
    //  参数;
    //  界面主要按钮;
    private TextView btnConfirm,btnFont,btnBack,btnPhotoUpload;
    private TextView tvStateFont,tvStateBack;
    private TextView tvFontInfo,tvBackInfo;
    private ImageView ivHead;
    //  系统的工具;
    private SysTool sysTool;

    //  对话框的调节;
    private ProgressDialog mProgressDialog;

    //  显示信息内容;
    private String address = null;
    private String birthday = null;
    private String idcard = null;
    private String name = null;
    private String nation = null;
    private String sex = null;
    private String type = null;
    private String issue_authority = null;
    private String valid_period = null;
    private String head_portrait = null;

    //  图像处理路径;
    private String imgpath=null;

    // 控件;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String result = (String) msg.obj;

            //  结果不全对;
            if(result!=null){
                //  结果出错;
                if(!result.equals(SysTool.ERROR_IO)){
                    JSONObject jsonObject = null;
                    Log.i("MyLog","xx="+result);
                    try {
                        jsonObject = new JSONObject(result);
                        String data = jsonObject.getJSONObject("data").toString();
                        jsonObject = new JSONObject(data);

                        switch (msg.what) {
                            case SysTool.REQUEST_IDCARD_FONT:
                                tvStateFont.setBackgroundColor(Color.GREEN);
                                tvFontInfo.setText("正面");
                                address = jsonObject.getString("address").toString();
                                birthday = jsonObject.getString("birthday").toString();
                                idcard = jsonObject.getString("idcard").toString();
                                name = jsonObject.getString("name").toString();
                                nation = jsonObject.getString("nation").toString();
                                sex = jsonObject.getString("sex").toString();
                                head_portrait = jsonObject.getJSONObject("head_portrait").getString("image").toString();
                                Log.i("MyLog","1.头像字符串长"+head_portrait.length());

                                Bitmap bitmap = sysTool.base64ToBitmap(head_portrait);

                                result = "姓名：" + name + "\r\n" +
                                        "身份证编号：" + idcard + "\r\n" +
                                        "生日：" + birthday + "\r\n" +
                                        "民族：" + nation + "\r\n" +
                                        "性别：" + sex + "\r\n" +
                                        "住址：" + address;
                                tvFontInfo.setText(result);
                                ivHead.setImageBitmap(bitmap);

                                break;
                            case SysTool.REQUEST_IDCARD_BACK:
                                //  背面状态颜色;
                                tvStateBack.setBackgroundColor(Color.GREEN);
                                //  背面信息;
                                tvBackInfo.setText("反面");
                                type = jsonObject.getString("type").toString();
                                issue_authority = jsonObject.getString("issue_authority").toString();
                                valid_period = jsonObject.getString("valid_period").toString();
                                result = "类型：" + type + "\r\n" +
                                        "所属分局：" + issue_authority + "\r\n" +
                                        "有效时间：" + valid_period;

                                tvBackInfo.setText(result);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                    Toast.makeText(mContext,R.string.app_time_out,Toast.LENGTH_SHORT).show();
            }

            if(mProgressDialog!=null){
                mProgressDialog.dismiss();
                mProgressDialog=null;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_resign_idcard);
        //  参数初始化;
        initConfig();
        //  初始化控件;
        initView();
        //  事件监听;
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  事件监听;
        setListener();
    }

    //  初始化参数;
    private void initConfig(){
        //  声明内容;
        mContext    =   this;
        //  声明系统工具;
        sysTool     =   new SysTool(mContext);
        sysTool.setHiddenTitle(mContext,SysTool.COLOR);

    }
    //  控件初始化;
    private void initView(){
        btnConfirm      =   findViewById(R.id.btnConfirm);
        btnFont         =   findViewById(R.id.btnFontPhoto);
        btnBack         =   findViewById(R.id.btnBackPhoto);
        btnPhotoUpload  =   findViewById(R.id.btnPhotoUpload);
        tvStateFont     =   findViewById(R.id.stateFont);
        tvStateBack     =   findViewById(R.id.stateBack);
        tvFontInfo      =   findViewById(R.id.tvFontInfo);
        tvBackInfo      =   findViewById(R.id.tvBackInfo);
        ivHead          =   findViewById(R.id.ivHead);
    }
    //  添加事件监听;
    private void setListener(){
        //  确认按钮;
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(head_portrait!=null){
                    Intent intent=new Intent(mContext,ResignFaceActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("idcard", idcard);
                    bundle.putString("name", name);
                    bundle.putString("birthday", birthday);
                    bundle.putString("nation", nation);
                    bundle.putString("sex", sex);
                    bundle.putString("address", address);
                    bundle.putString("issue_authority", issue_authority);
                    bundle.putString("valid_period", valid_period);
                    bundle.putString("head_portrait", head_portrait);
                    bundle.putString("img_path",imgpath);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }else
                    Toast.makeText(mContext,"请重新扫描身份证！",Toast.LENGTH_SHORT).show();
                    ;

            }
        });
        btnFont.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //  身份证正面信息的获取;
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, SysTool.REQUEST_IDCARD_FONT);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  身份证反面信息的获取;
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, SysTool.REQUEST_IDCARD_BACK);
            }
        });
        //  照片上传;
        btnPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, SysTool.REQUEST_PHOTO_SET);
            }
        });
    }
    // 进行数据的重新处理;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && null != data) {
            String imgPath = sysTool.getPathFromPhotoSet(mContext,data);
            switch (requestCode){
                case SysTool.REQUEST_IDCARD_FONT:
                case SysTool.REQUEST_IDCARD_BACK:
                    //  进行身份证件的校验;
                    String httpUrl = sysTool.getHttpUrl("CheckIdCard",null);
                    Log.i("MyLog","http:"+httpUrl);
                    //  传输提示框的建立;
                    //  TODO
                    mProgressDialog =   sysTool.progressDialogLoading(mContext,"身份证信息采集","正在进行数据传输");
                    mProgressDialog.create();
                    //  提示栏目的显示;
                    mProgressDialog.show();

                    //  数据上传;
                    upload(requestCode,httpUrl,imgPath);
                        break;
                case SysTool.REQUEST_PHOTO_SET:
                        imgpath=imgPath;
                        btnPhotoUpload.setText("选择完毕 √");
                        break;
            }


        }
    }
    private void upload(final int oper, final String httpUrl, final String path) {
        Log.i("MyLog",httpUrl);
        new Thread() {
            @Override
            public void run() {
                //  结果串;
                String result = null;
                try {
                    result = new String(sysTool.httpPostResponse(httpUrl,path).getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Message message = mHandler.obtainMessage(oper);
                message.obj = result;
                mHandler.sendMessage(message);
            }
        }.start();
    }
    public void listenerNo(View view){
        finish();
    }
}
