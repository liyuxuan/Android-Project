package cn.com.core.ui.login;

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

import cn.com.core.R;
import cn.com.core.tool.SysTool;

public class ContactActivity extends AppCompatActivity {
    private ContactActivity mContext;
    private SysTool         sysTool;

    private Button          btnBack;
    private TextView        tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_contact);
        initView();
        initConfig();
        setListener();
    }

    private void initView(){
        btnBack=findViewById(R.id.btnBack);
        tvTitle=findViewById(R.id.tvTitle);
    }

    private void initConfig(){
        mContext    =   this;
        sysTool     =   new SysTool(mContext);
        sysTool.setHiddenTitle(mContext,SysTool.COLOR2);

    }
    private void setListener(){
        tvTitle.setText("联系我们");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
