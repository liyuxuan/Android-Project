package cn.com.core.ui.login;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.com.core.R;
import cn.com.core.tool.SysTool;

public class SetActivity extends AppCompatActivity {
    //  需要修改上下文内容;
    private SetActivity  mContext;
    //  返回按钮;
    private Button   btnBack,btnOk,btnReset;
    private TextView tvTitle;
    //  IP地址以及端口号的信息;
    private EditText etIp,etPort;
    //  系统的初始化信息;
    private SysTool  sysTool;
    //  Ip地址,端口地址;
    private String   ip,port;
    //  Ip地址_临时,端口地址_临时;
    private String   ip_temp,port_temp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_set);
        //  初始化参数;
        initConfig();
        //  初始化控件;
        initView();
        //  事件监听的初始化;
        setListener();
    }
    private void initConfig(){
        mContext    =   this;

        //  信息的初始化;
        sysTool     =   new SysTool(mContext);
        //  进行网络信息的初始化内容;
        sysTool.checkInitNetInfo();
        sysTool.setHiddenTitle(mContext,SysTool.COLOR2);
        //  临时的ip以及port内容信息;
        ip_temp     =   sysTool.getSParameter(mContext,"net_set","ip").toString();
        port_temp   =   sysTool.getSParameter(mContext,"net_set","port").toString();
    }

    private void initView(){
        //  返回键;
        btnBack =   findViewById(R.id.btnBack);
        //  标题栏;
        tvTitle =   findViewById(R.id.tvTitle);
        //  ip信息以及端口号;
        etIp    =   findViewById(R.id.etIp);
        etPort  =   findViewById(R.id.etPort);
        //  确认按钮;
        btnOk   =   findViewById(R.id.btnOk);
        //  重置按钮;
        btnReset=   findViewById(R.id.btnReset);
    }
    private void setListener(){
        //  返回键的事件监听;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //   标题栏目的内容;
        tvTitle.setText("软件参数设置");
        //  ip地址的内容;
        etIp.setText(ip_temp);
        //  端口信息的内容;
        etPort.setText(port_temp);
        //   确认按钮事件;
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  获得ip栏目的信息;
                ip  =   etIp.getText().toString();
                //  获得端口栏目的信息;
                port=   etPort.getText().toString();
                //  进行ip地址的设置信息;
                sysTool.setSParameter(mContext,"net_set","ip",ip);
                //  进行端口信息的设置信息;
                sysTool.setSParameter(mContext,"net_set","port",port);
                //  确认信息;
                Toast.makeText(mContext,"参数设置完毕",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        //   重置按钮事件;
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  将ip地址进行重置;
                etIp.setText(ip_temp);
                //  将端口号进行重置;
                etPort.setText(port_temp);
            }
        });
    }
}
