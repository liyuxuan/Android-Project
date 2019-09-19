package cn.com.core.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import cn.com.core.R;
import cn.com.core.tool.PhotoPagerAdapter;
import cn.com.core.tool.SysTool;
import cn.com.core.tool.ViewPagerFixed;

public class BigImgActivity extends AppCompatActivity {
    //  进行读取的上下文内容;
    private BigImgActivity   mContext;
    private ViewPagerFixed   viewPager;
    private TextView         tvNum;
    //  系统的声明对象;
    private SysTool           sysTool;
    //  返回键按钮声明;
    private TextView          btnBack;
    //  承装数据的控件;
    private ArrayList<String> imgData;
    //  进行索引标记的内容;
    private int               clickPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);
        //  初始化参数配置;
        initConfig();
        //  初始化控件内容;
        initView();
        //  设置事件监听;
        setListener();
    }

    private void initConfig(){
        //  进行相应的系统的控件;
        mContext    = this;
        //  系统对象的声明信息;
        sysTool     = new SysTool(mContext);
        //  设置控件隐藏标题;
        sysTool.setHiddenTitle(mContext,SysTool.COLOR2);

        //接收图片数据及位置
        imgData       = getIntent().getStringArrayListExtra("imgData");
        clickPosition = getIntent().getIntExtra("clickPosition", 0);
    }
    private void initView() {
        //  进行转换pager的按钮;
        viewPager   = findViewById(R.id.viewpager);
        tvNum       = findViewById(R.id.tv_num);
        //  返回键按钮内容;
        btnBack     = findViewById(R.id.btnBack);

        //添加适配器
        PhotoPagerAdapter viewPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), imgData);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(clickPosition);//设置选中图片位置


    }
    //  设置监听的控件;
    private void setListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvNum.setText(String.valueOf(position + 1) + "/" + imgData.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
