package cn.com.core.ui.login;

//import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
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
//import android.widget.TextView;
//import android.widget.Toast;
import com.tju.library.CodeEditView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.com.core.R;
import cn.com.core.tool.SysTool;

public class MessageActivity extends Activity {

    public static MessageActivity mContext;
    // 验证码的值;
//    private CodeEditView etCheckCode;
    private EditText etCheckCode;

    // 相应的输入框的控件;
    private EditText etPhoneNumber;
    //  验证码的发送按钮;
    private TextView btnCheckCode,btnOk;
    //  系统的方法;
    private SysTool  sysTool;
    //  临时的参数;
    private String   tempCode;
    private String   temp;


    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 网络发送验证码的部分;
                case 1:

                    String   result = (String) msg.obj;
                    String   info   = null;
                    //  输出的数据为IO流的错误;
                    if(!result.equals(SysTool.ERROR_IO)){
                        //  将动画显示结果与自身信息结果进行分离;
                        String[] temps= result.split("%");
                        result        = temps[0];
                        info          = temps[1];
                        temp          = sysTool.getStringUTF8(info);

                        //  临时存放验证码的参数;
                        tempCode      = result;
//                        Log.i("MyLog","tmp="+temp);
                    }else
                        Toast.makeText(mContext,R.string.app_time_out,Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(mContext,"注冊成功",Toast.LENGTH_SHORT).show();
                    finish();

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  先去除应用程序标题栏  注意：一定要在setContentView之前
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_message);
        //  设置键盘不会被遮挡;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //  初始化参数;
        initConfig();
        //  初始化控件;
        initView();
        //  添加事件监听;
        setListener();
    }
    //  初始化参数;
    private void initConfig(){
        mContext    =   this;
        sysTool     =   new SysTool(mContext);
        sysTool.setHiddenTitle(mContext);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 进行相应的数据信息的处理;
        setListener();
    }

    //  初始化控件;
    private void initView() {
        //  验证码的输入框;
//        etCheckCode   = (CodeEditView) findViewById(R.id.etCheckCode);
        etCheckCode   = findViewById(R.id.etCheckCode);
        //  电话输入码;
        etPhoneNumber = findViewById(R.id.etPhone);
        //  发送验证码的按钮;
        btnCheckCode  = findViewById(R.id.btnCheckCode);
        //  确认的短信验证的按钮;
        btnOk         = findViewById(R.id.btnOk);

//        etCheckCode.setOnInputEndCallBack(new CodeEditView.inputEndListener() {
//            @Override
//            public void input(String text) { //输入完毕后的回调
//                Toast.makeText(MessageActivity.this, text, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void afterTextChanged(String text) { //输入内容改变后的回调
////                Toast.makeText(MessageActivity.this, text, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //  事件监听;
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
                    String httpUrl=sysTool.getHttpUrl("CheckMessage","operType=1&mobile="+mobile);
                    sendMessage(httpUrl);
                }else
                    Toast.makeText(mContext,R.string.app_nonullable_input,Toast.LENGTH_LONG).show();
            }
        });
        //  确认的按钮;
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCheckCode.getText().toString().equals(tempCode)){
//                    Log.i("MyLog","信息="+temp);
                    //  进行数据解析;
                    try {
                        JSONArray   array =new JSONArray(temp);
                        JSONObject  object= array.getJSONObject(0);

                        sysTool.setSParameter(mContext,"login","idcard",object.getString("idcard").toString());
                        sysTool.setSParameter(mContext,"login","name",object.getString("name").toString());
                        sysTool.setSParameter(mContext,"login","birthday",object.getString("birthday").toString());
                        sysTool.setSParameter(mContext,"login","sex",object.getString("sex").toString());
                        sysTool.setSParameter(mContext,"login","nation",object.getString("nation").toString());
                        sysTool.setSParameter(mContext,"login","address",object.getString("address").toString());
                        sysTool.setSParameter(mContext,"login","belong",object.getString("belong").toString());
                        sysTool.setSParameter(mContext,"login","phone",object.getString("phone").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //  进行页面的跳转;
                    Toast.makeText(mContext,R.string.app_pass,Toast.LENGTH_LONG).show();
                    //  进入主界面;
                    Intent intent   =   new Intent(mContext,MenuActivity.class);
                    startActivity(intent);
                    finish();

                }else
                    //  进行页面的跳转;
                    Toast.makeText(mContext,R.string.app_nopass,Toast.LENGTH_LONG).show();
            }
        });
    }

    //  进行相应的参数
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
                //  将信息进行发送;
                Message message = mHandler.obtainMessage(1);
                message.obj     = result;
                mHandler.sendMessage(message);
            }
        }.start();
    }

}
