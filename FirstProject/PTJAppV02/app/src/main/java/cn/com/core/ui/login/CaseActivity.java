package cn.com.core.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;

import cn.com.core.R;
import cn.com.core.data.model.Submitperson;
import cn.com.core.fragment.Case1Fragment;
import cn.com.core.fragment.Case5Fragment;
import cn.com.core.tool.SysTool;

public class CaseActivity extends AppCompatActivity {
    public static CaseActivity mContext;
    //  系统的方法;
    public SysTool sysTool;

    private Button btnBack;
    private TextView tvTitle;
    public static TextView tvPage;
    public static Submitperson submitperson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_case);
        initConfig();
        initView();
        setListener();
    }

    private void initView(){

        btnBack =findViewById(R.id.btnBack);
        tvTitle =findViewById(R.id.tvTitle);
        tvPage  =findViewById(R.id.tvPage);
    }

    private void initConfig(){
        mContext    =   this;
        sysTool     =   new SysTool(mContext);
        sysTool.setHiddenTitle(mContext,SysTool.COLOR2);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container1,new Case1Fragment()).commit();
        //  进行初始化的相应的数据内容;
        submitperson=   new Submitperson();
    }

    private void setListener(){

        tvTitle.setText("立案申请");
        //  返回键的设置;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgpath=submitperson.getProofpath();
                File   file   =new File(imgpath);
                //  文件的删除;
                if(file.exists())
                    file.delete();
                finish();
            }
        });
    }
    // 进行相应的照相机的选取;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bmp=null;
        if (resultCode == Activity.RESULT_OK ) {
            //获取图片并显示
            if (requestCode == SysTool.REQUEST_PROOF_TAKE) {
                //  数据的传输;
                Case5Fragment fragment = new Case5Fragment();
                Bundle bundle = new Bundle();
                //  数据包插入;
                bundle.putString("data","OK");
                //  数据传递到fragment中
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container1,fragment);
                fragmentTransaction.addToBackStack("case5");
                //  忽略异常的提交;
                fragmentTransaction.commitAllowingStateLoss();
            }else{
                Log.i("MyLog","不能成功获得失败!");
            }
        }
    }
    //  系统返回按钮的事件监听;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
