package cn.com.core.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.com.core.R;
import cn.com.core.fragment.ListFragment;
import cn.com.core.tool.SysTool;

public class QueryActivity extends AppCompatActivity {
    private QueryActivity  mContext;
    private SysTool        sysTool;

    private Button         btnBack;
    private TextView       tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_query);
        initConfig();
        initView();
        setListener();
    }
    //  空间初始化;
    private void initView(){
        tvTitle  = findViewById(R.id.tvTitle);
        btnBack  = findViewById(R.id.btnBack);
    }
    //  参数初始化;
    private void initConfig(){
        mContext = this;
        sysTool  = new SysTool();
        sysTool.setHiddenTitle(mContext,SysTool.COLOR2);

        //  进行相应的数据信息更新;
        FragmentManager     fragmentManager     =   getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =   fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container2,new ListFragment()).commit();
    }

    //  设置监听;
    private void setListener(){
        tvTitle.setText("案件查询");

        //  添加返回事件;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
