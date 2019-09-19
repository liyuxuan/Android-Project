package cn.com.core.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.core.R;
import cn.com.core.tool.CircleProgress;
import cn.com.core.tool.SysTool;

import static cn.com.core.tool.SysTool.REQUEST_PHOTO_SET;

public class LoginActivity extends AppCompatActivity {
    //  参数;
    public static LoginActivity mContext;
    //  系统的类;
    private SysTool         sysTool;

    // 控件的声明;
    // 进行按钮-采样，登录，注册;
    private TextView        btnPhoto,btnLogin,btnResign,btnMessage;
    // 照片的识别状态;
    private TextView        recognizationState,errorCounts;
    // 进行显示部分;
    private RelativeLayout  layCircle;
    // 图片的显示控件;
    private ImageView       facePhoto;
    //
    private Bitmap          bitmap;
    //  图像的路径;
    private String          bmppath;

    //  对话框的调节;
    private ProgressDialog  mProgressDialog;
    //  环形界面显示;
    private CircleProgress  circleProgress;
    private int             max=0;
    private Timer           timer;
    private int             process,errorCount,period;
    private String          temp;

    //  刷新控件的内容;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String   result = (String) msg.obj;
            String   info   = null;

            if(result!=null){
                if(!result.equals(SysTool.ERROR_IO)){

                    //  将动画显示结果与自身信息结果进行分离;
                    String[] temps= result.split("%");
                    result        = temps[0];
                    info          = temps[1];
                    temp          = sysTool.getStringUTF8(info);

                    JSONObject  jsonObject = null;
                    float       confidence = 0f;

                    try {
                        //  数据的结果内容;
                        jsonObject  = new JSONObject(result);
                        String data = jsonObject.getString("confidence").toString();

                        confidence  = Float.valueOf(data);
                        max         =(int)confidence;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //  数值显示的区域;
                    if(max>50)
                        period  =   max/3;
                    else if(max>=20&&max<=50)
                        period  =   max/2;
                    else
                        period  =   max;
                    //  返回值结果的内容;
                    switch (msg.what) {
                        case 1:
                            //  将显示的闭环进行关闭;
                            if(layCircle.getVisibility()==View.GONE)
                                layCircle.setVisibility(View.VISIBLE);

                            //  进行计数的标签;
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(circleProgress.getProgress()< max){
                                                //  计算步长;
                                                process     =   circleProgress.getProgress()+1;
                                                circleProgress.setProgress(process);
                                                recognizationState.setText(process+"%");

                                                //  小于30为红色;
                                                if(process<=30)
                                                    recognizationState.setTextColor(Color.RED);

                                                //  30<x<50为黄色;
                                                else if(process>30&&process<=50)
                                                    recognizationState.setTextColor(Color.YELLOW);

                                                //  x>50为蓝色;
                                                else
                                                    recognizationState.setTextColor(Color.GREEN);

                                                String  tag        = "";
//                                                if(process==max)

                                                //  当步长满足最大值时;
                                                if(process==max){
                                                    timer.cancel();
                                                    if(max>50){
                                                        tag=process+"%\r\n通过";

                                                        try {
                                                            JSONArray array = new JSONArray(temp);

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

                                                        // 匹配直接跳转;
                                                        Intent intent=new Intent(mContext,MenuActivity.class);
                                                        startActivity(intent);
                                                        MenuActivity.mContext.finish();
                                                        finish();

                                                    }else{
                                                        tag=process+"%\r\n未通过";
                                                        errorCount=errorCount+1;
                                                        errorCounts.setText("3次机会！第"+errorCount+"失败！请重试！");
                                                        errorCounts.setTextColor(Color.RED);
                                                    }
                                                    recognizationState.setText(tag);
                                                }
                                            }
                                        }
                                    });

                                }
                            },0,period);
                            break;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        //  设置键盘不会被遮挡;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 初始化参数;
        initConfig();
        // 初始化控件;
        initView();
        // 调用监听;
        setListener();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // 调用监听;
        setListener();
    }

    //  初始化参数;
    private void initConfig(){
        //  进行相应的连接;
        mContext    =   this;

        //  声明工具类;
        sysTool     =   new SysTool(mContext);
        sysTool.setHiddenTitle(mContext,SysTool.COLOR2);
        sysTool.checkStoreRight(mContext);

    }

    //  初始化控件;
    private void initView(){
        // 进行按钮-采样，登录，注册，短信;
        btnPhoto    =   findViewById(R.id.btnPhoto);
        btnLogin    =   findViewById(R.id.btnLogin);
        btnResign   =   findViewById(R.id.btnResign);
        btnMessage  =   findViewById(R.id.btnMessage);
        // 照片的识别状态;
        recognizationState=findViewById(R.id.recognizationState);
        errorCounts =   findViewById(R.id.errorCounts);
        // 显示人脸的照片;
        facePhoto   =   findViewById(R.id.facePhoto);

        //  圆形进度形状;
        circleProgress= (CircleProgress) findViewById(R.id.progress);
        //  进行布局;
        layCircle     = findViewById(R.id.lay3);

        //  注册按钮;
        btnResign.setText(Html.fromHtml("<u>"+"注册"+"</u>"));

    }
    //  设置监听;
    private void setListener(){
        //  采样按钮获得信息;
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  如果布局已经显示了，则取消
                if(layCircle.getVisibility()==View.VISIBLE){
                    max        =       0;
                    layCircle.setVisibility(View.GONE);
                    circleProgress.setProgress(1);
                }

                //  临时-相册按钮;
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, REQUEST_PHOTO_SET);
            }
        });
        //  登录按钮获得信息;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //  errorCount=3,错误次数的上限;
                //  如果布局已经显示了，则取消
                if(layCircle.getVisibility()==View.VISIBLE){
                    max        =       0;
                    layCircle.setVisibility(View.GONE);
                    circleProgress.setProgress(1);
                }
                //  非进行短信验证的情况下;
                if(errorCount<3){
                    //  首先监测网络环境
                    boolean isNetEnvironment= sysTool.isNetWorkEnvironment(mContext);
                    boolean isWifiNet       = sysTool.isWifiEnable(mContext);
                    Log.i("MyLog","网络环境="+isNetEnvironment+" | wifi数据="+isWifiNet);
                    //  网络环境的监测;
                    if(isNetEnvironment){
                        //  采样图像的监测;
                        if(bitmap!=null){
                            max             =   0;
                            timer           =   new Timer();
//                            String  idcard  = (String) sysTool.getSParameter(mContext,"user","IDCARD");
                            String idcard="120101198611043532";
                            if(idcard!=null){
                                //  传输提示框的建立;
                                mProgressDialog =   sysTool.progressDialogLoading(mContext,"人脸采样完成","正在进行数据传输");
                                mProgressDialog.create();
                                //  提示栏目的显示;
                                mProgressDialog.show();
                                faceIdentify(idcard,bmppath);
                            }else
                                Toast.makeText(mContext,R.string.app_resign_first,Toast.LENGTH_SHORT).show();
                                ;

                        }else
                            Toast.makeText(mContext,R.string.app_photo_adjust,Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(mContext,R.string.app_net_adjust,Toast.LENGTH_LONG).show();
                }
                //  进行短信验证的部分;
                else{
                    Intent intent=new Intent(mContext,MessageActivity.class);
                    startActivity(intent);
                }
            }
        });
        //  短信验证;
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,MessageActivity.class);
                startActivity(intent);
            }
        });

        //  注册按钮;
        btnResign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ResignIdCardActivity.class);
                startActivity(intent);
            }
        });

    }
    // 进行数据的加载;
    private void faceIdentify(final String idcard,final String filestr){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //  组合参数;
                String parameter   =   "idcard="+idcard;
                String httpUrl     =   sysTool.getHttpUrl("CheckFace",parameter);
                //  结果反馈集合;
                String result      =   sysTool.httpPostResponse(httpUrl,filestr);
                Message message    =   mHandler.obtainMessage(1);

                //  将反馈集合承装message发送给handler;
                message.obj     =   result;
                mHandler.sendMessage(message);
            }
        }.start();
    }


    // 数据的返回内容;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                // 相册形成的图像;
                case SysTool.REQUEST_PHOTO_SET:
                    //  获得相册结果;
                    //  TODO
                    bmppath =   sysTool.getPathFromPhotoSet(mContext,data);
                    bitmap  =   sysTool.getBitmapFormPath(bmppath);
                    facePhoto.setImageDrawable(sysTool.getBitmapByDealWith(mContext,bitmap,60f));

                    break;

                //  拍照形成的图像;
                case SysTool.REQUEST_PHOTO_TAKE:
                    Bundle bundle=data.getExtras();
                    //  获得照相的结果;
                    //  TODO
                    bitmap = (Bitmap) bundle.get("data");
                    facePhoto.setImageDrawable(sysTool.getBitmapByDealWith(mContext,bitmap,60f));

                    break;
            }
        }
    }
    public void listenerNo(View view){
        finish();
    }
}
