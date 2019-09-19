package cn.com.core.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import cn.com.core.R;
import cn.com.core.data.model.Reason;
import cn.com.core.fragment.BaseFragment;
import cn.com.core.fragment.Case1Fragment;
import cn.com.core.fragment.Case2Fragment;
import cn.com.core.fragment.Case3Fragment;
import cn.com.core.fragment.Case4Fragment;
import cn.com.core.fragment.Case5Fragment;
import cn.com.core.fragment.Case6Fragment;
import cn.com.core.tool.SysTool;

public class WelActivity extends AppCompatActivity{
    //  参数;
    public static WelActivity mContext;
    public static Reason reason;
    public static ArrayList<Reason> listReasons;
    private TextView tvCountId;
    public static BaseFragment case1Fragment,case2Fragment,case3Fragment,case4Fragment,case5Fragment,case6Fragment;
    //  信息参数;
    private SysTool sysTool;

    //  刷新控件的内容;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int count = (int) msg.obj;
            switch (msg.what) {
                case 1:
                    tvCountId.setText("剩余"+count+"秒");
                    if(count==0){
                        Intent intent=new Intent(mContext,MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wel);
        //  进行参数初始化;
        initConfig();
        //  进行控件初始化;
        initView();
        //  初始化方法;
        wel();
    }

    //  初始化控件;
    private void initView(){
        tvCountId=findViewById(R.id.tvCountId);
        //  进行fragment的初始化加载;
        case1Fragment=new Case1Fragment();
        case2Fragment=new Case2Fragment();
        case3Fragment=new Case3Fragment();
        case4Fragment=new Case4Fragment();
        case5Fragment=new Case5Fragment();
        case6Fragment=new Case6Fragment();
    }
    //  初始化参数;
    private void initConfig(){
        //  相应的内容信息;
        mContext    =   this;
        sysTool     =   new SysTool(mContext);
        sysTool.checkInitNetInfo();
        //  将提示框栏目进行隐藏;
        sysTool.setHiddenTitle(mContext,SysTool.COLOR);

        //  进行相应的数据初始化;
        reason      =   new Reason();
        listReasons =   reason.getListDatas();
    }

    // 页面跳转;
    private void wel(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                int count=3;
                while (count>=0){

                    Message message =   mHandler.obtainMessage(1);

                    //  将反馈集合承装message发送给handler;
                    message.obj     =   count;
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                        count--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
