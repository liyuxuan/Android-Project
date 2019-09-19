package cn.com.core.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import cn.com.core.R;
import cn.com.core.tool.SysTool;

public class NoteItemActivity extends Activity {
    private NoteItemActivity mContext;
    private SysTool          sysTool;
    private TextView         btnBack,tvContent;
    private ImageView        ivImg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  先去除应用程序标题栏  注意：一定要在setContentView之前
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_menu_note_item);
        //  控件的初始化;
        initView();
        //  数据参数的初始化;
        initConfig();
        //  增加事件监听;
        setListener();
    }
    private void initView(){
        btnBack    = findViewById(R.id.btnBack);
        tvContent  = findViewById(R.id.tvContent);
        ivImg      = findViewById(R.id.ivImg);
    }
    private void initConfig(){
        mContext    =   this;
        sysTool     =   new SysTool();
        sysTool.setHiddenTitle(mContext);

        Intent intent   =   getIntent();
        Bundle bundle   =   intent.getExtras();
        String title    =   bundle.getString("title");
        String content  =   bundle.getString("content");
        String path     =   bundle.getString("path");
        //  将图片进行显示;
        ivImg.setImageURI(Uri.fromFile(new File(path)));
        String str      =   title+"\r\n"+content;
        tvContent.setText(str);
    }
    private void setListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}