package cn.com.core.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tju.library.CodeEditView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.com.core.R;
import cn.com.core.tool.SysTool;

public class ResignFaceActivity extends Activity {

    public static ResignFaceActivity mContext;
    private Intent mIntent;
    private Bundle  bundle;
    private String address=null;
    private String birthday=null;
    private String idcard=null;
    private String name=null;
    private String nation=null;
    private String sex=null;
    private String issue_authority=null;
    private String valid_period=null;
    private String head_portrait=null;
    private String img_path=null;

//    // 验证码的值;
    private EditText etCheckCode;
//    private CodeEditView etCheckCode;
    // 相应的输入框的控件;
    private EditText etPhoneNumber;
    //  验证码的发送按钮;
    private TextView btnCheckCode,btnOk;
    //  系统的方法;
    private SysTool sysTool;
    //  临时的参数;
    private String  tempCode;
    private String  tempMobile;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 网络发送验证码的部分;
                case 1:
                    tempCode= (String) msg.obj;
                    Log.i("MyLog", "返回验证码 " + tempCode);
                    if(tempCode.equals("null")){
                        Toast.makeText(mContext,R.string.app_nonullable_right,Toast.LENGTH_LONG).show();
                    }

                    break;
                // 进行网络数计时的部件;
                case 2:
                    int count = (int) msg.obj;
                    String tag = "发送验证码";

                    if (count != 0) {
                        tag = String.valueOf(count) + "秒";
                        btnCheckCode.setEnabled(false);
                        etPhoneNumber.setEnabled(false);
                        btnCheckCode.setTextColor(Color.parseColor("#8B8B7A"));
                        etPhoneNumber.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

                    } else{
                        btnCheckCode.setEnabled(true);
                        etPhoneNumber.setEnabled(true);
                        btnCheckCode.setTextColor(Color.parseColor("#000000"));
                        etPhoneNumber.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    }

                    btnCheckCode.setText(tag);
                    break;
                case 3:
                    String result = (String) msg.obj;
                    if(result!=null){
                        Log.i("MyLog","注册电话="+tempMobile);
                        Toast.makeText(mContext,"注冊成功",Toast.LENGTH_SHORT).show();
                        sysTool.setSParameter(mContext,"user","IDCARD",result);

                        finish();
                        ResignIdCardActivity.mContext.finish();
                    }else
                        Toast.makeText(mContext,R.string.app_oper_fail,Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_message);
        //  设置键盘不会被遮挡;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //  参数值的配置设置;
        initConfig();
        //  控件的初始化设置;
        initView();
        //  监听初始化;
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setListener();
    }

    //  控件的初试化;
    private void initView(){
        etCheckCode     =   findViewById(R.id.etCheckCode);
        etPhoneNumber   =   findViewById(R.id.etPhone);
        btnCheckCode    =   findViewById(R.id.btnCheckCode);
        btnOk           =   findViewById(R.id.btnOk);
        // TODO
//        etCheckCode.setOnInputEndCallBack(new CodeEditView.inputEndListener() {
//            @Override
//            public void input(String text) { //输入完毕后的回调
//                Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void afterTextChanged(String text) { //输入内容改变后的回调
////                Toast.makeText(MessageActivity.this, text, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    //  配置的初始化;
    private void initConfig(){
        mContext = this;
        sysTool  = new SysTool(mContext);
        sysTool.setHiddenTitle(mContext);

        mIntent = getIntent();
        bundle  = mIntent.getExtras();
        address = bundle.getString("address");
        birthday= bundle.getString("birthday");
        idcard  = bundle.getString("idcard");
        name    = bundle.getString("name");
        nation  = bundle.getString("nation");
        sex     = bundle.getString("sex");
        issue_authority=bundle.getString("issue_authority");
        valid_period   =bundle.getString("valid_period");
        head_portrait  =bundle.getString("head_portrait");
        img_path       =bundle.getString("img_path");


    }
    //  监听的初始化;
    private void setListener(){
        //  发送相应的验证码的内容;
        btnCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MyLog", "验证短信");
                String mobile=etPhoneNumber.getText().toString();
                if(!mobile.equals("")){

                    // 进行计数的线程;
                    countTimeThread();
                    // 手机电话号;
                    // 进行信息接收的按钮;
                    String httpUrl=sysTool.getHttpUrl("CheckMessage","operType=2&mobile="+mobile);
                    sendMessage(httpUrl);
                }else
                    Toast.makeText(mContext,R.string.app_nonullable_input,Toast.LENGTH_LONG).show();
            }
        });
        //  确认的按钮;
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempCode!=null){
                    if(tempCode.contains("%")){
                        String[] strs=tempCode.split("%");
                        String   code=strs[0];
                        tempMobile   =strs[1];

                        if(etCheckCode.getText().toString().equals(code)){
                            Log.i("MyLog","是同一个人");
                            Log.i("MyLog","两次验证码相同");
                            String httpUrl= null;
                            try {
                                String parameter=
                                        "address="+ URLEncoder.encode(address,"utf-8") +"&" +
                                        "birthday="+URLEncoder.encode(birthday,"utf-8")+"&" +
                                        "idcard="+idcard+"&" +
                                        "name="+URLEncoder.encode(name,"utf-8")+"&" +
                                        "nation="+URLEncoder.encode(nation,"utf-8")+"&" +
                                        "sex="+URLEncoder.encode(sex,"utf-8")+"&" +
                                        "issue_authority="+URLEncoder.encode(issue_authority,"utf-8")+"&" +
                                        "valid_period="+valid_period+"&" +
                                        "head_portrait="+head_portrait+"&" +
                                        "mobile="+tempMobile;
                                httpUrl=sysTool.getHttpUrl("UserManager",parameter);

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            if(img_path!=null){
                                submitInfo(httpUrl,img_path);
                            }else
                                Toast.makeText(mContext,"请提交相应图像信息",Toast.LENGTH_SHORT).show();

                        }else
                            //  进行页面的跳转;
                            Toast.makeText(mContext,R.string.app_nopass,Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(mContext,"验证短信发送失败",Toast.LENGTH_LONG).show();;
                }else {
                    Toast.makeText(mContext,"验证短信发送失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //  取消按钮;
    public void listenerNo(View view){
            finish();
    }

    //  进行计时的按钮;
    //  计数线程;
    private void countTimeThread() {
        new Thread() {
            @Override
            public void run() {
                int count = 60;
                while (count >= 0) {
                    try {
                        Message message = mHandler.obtainMessage(2);
                        message.obj = count;
                        mHandler.sendMessage(message);
                        Thread.sleep(1000);
                        count--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //  发送短信
    //  发送验证短信;
    private void sendMessage(final String httpUrl){
        new Thread(){
            @Override
            public void run() {
                String  result  = sysTool.httpPostResponse(httpUrl,null);
                Message message = mHandler.obtainMessage(1);
                message.obj     = result;
                mHandler.sendMessage(message);
            }
        }.start();
    }
    //  进行消息的提交;
    private void submitInfo(final String httpUrl, final String path){
        new Thread(){
            @Override
            public void run() {
                String result = sysTool.httpPostResponse(httpUrl,path);
                Message message = mHandler.obtainMessage(3);
                message.obj = result;
                mHandler.sendMessage(message);
            }
        }.start();
    }
}
