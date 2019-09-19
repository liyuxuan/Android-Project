package cn.com.core.ui.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.sxu.refreshlayout.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.core.R;
import cn.com.core.tool.MyService;
import cn.com.core.tool.SysTool;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    //  系统功能部件;
    public static MenuActivity    mContext    =  null;
    private TextView              tvLoginPerson,tvName,tvIdcard,btnFlush,tvContent;
    private Toolbar               toolbar     =  null;
    private DrawerLayout          drawer      =  null;
    private NavigationView navigationView     =  null;
    private ActionBarDrawerToggle toggle      =  null;
    private SysTool               sysTool     =  null;
    private String                idcard,name =  null;
    private MenuItem              itemLogin,itemResign;
    private int                   count       =  0;
    private Banner                mBanner     =  null;
    private RefreshLayout         refreshLayout;

    private TextView btnCase,btnQuery,btnContact;


    private String title="";
    private String content="";
    private ArrayList<String>     imgpaths  =   null;
    private int                   TIME_SLEEP=   3000;
    private MyThread              myThread  =   null;
    private Thread                thread    =   null;
    private int                   oper      =   0;
    private MyBroadcastReceiver   mReceiver =   null;

    //  进行相应的跑马灯变换
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            oper    =   msg.what;
//            Log.i("MyLog","oper="+oper);
            //  操作信息的内容;
            if (oper == 100) {
//                Log.i("MyLog","刷新数据");
                //  关闭窗口线程;
                refreshLayout.refreshComplete();
                //  开启读取的线程;
                loadImages();
            }else{
                //  进行内容的显示;
                String   result   =   (String) msg.obj;
//                Log.i("MyLog","文字="+result);
                // 进行相应的句柄的转换;
                JSONObject object = null;
                try {
                    //  msg获得数据的方式;
                    final int index=msg.getData().getInt("index");

                    object        = new JSONObject(result);
                    title         = object.getString("title").toString();
                    content       = object.getString("content").toString();
//                    Log.i("MyLog","title="+title);
//                    Log.i("MyLog","content="+content);
                    tvContent.setText(title);

                    if(!title.equals("")&&title!=null){
                        tvContent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String path     =   imgpaths.get(index);
                                Intent intent   =   new Intent(mContext,NoteItemActivity.class);
                                Bundle bundle   =   new Bundle();
                                bundle.putString("title",title);
                                bundle.putString("content",content);
                                bundle.putString("path",path);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menu);
        //  初始化参数;
        initConfig();
        //  初始化控件;
        initView();
        //  设置按钮;
        setListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  检测存储的权限;
        sysTool.checkStoreRight(mContext);
        setListener();
    }

    //  初始化参数;
    private void initConfig(){
        //  将此事件进行添加;
        mContext    =   this;
        //  进行系统的初始化的操作;
        sysTool     =   new SysTool(mContext);
        sysTool.setHiddenTitle(mContext,SysTool.COLOR2);
        //  检测存储的权限;
        sysTool.checkStoreRight(mContext);

        //  进行首选项内容的编辑;
        if(sysTool.getSParameter(mContext,"login","idcard")!=null){
            idcard  =   sysTool.getSParameter(mContext,"login","idcard").toString();
            name    =   sysTool.getSParameter(mContext,"login","name").toString();
        }
        //
        // 自定义的广播接收器
        mReceiver           = new MyBroadcastReceiver();
        // 过滤器，其中传入一个action
        IntentFilter filter = new IntentFilter(SysTool.BROADCAST_MESSAGE);
        // 注册广播
        registerReceiver(mReceiver,filter);

    }
    //  初始化控件;
    private void initView(){
        //  title按钮;
        toolbar         =   findViewById(R.id.toolbar);
        //  侧栏的按钮;
        drawer          =   findViewById(R.id.drawer_layout);
        //  身份的标签;
        tvLoginPerson   =   findViewById(R.id.loginPerson);
        //  侧栏的控件外层;
        navigationView  =   findViewById(R.id.nav_view);
        //  主界面的按钮;
        btnCase         =   findViewById(R.id.btnCase);
        btnQuery        =   findViewById(R.id.btnQuery);
        btnContact      =   findViewById(R.id.btnContact);
        tvContent       =   findViewById(R.id.content);

        //  侧栏位的按钮;
        tvName          =   navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvIdcard        =   navigationView.getHeaderView(0).findViewById(R.id.tvIdcard);

        //  登录的条目钮;
        itemLogin       =   navigationView.getMenu().findItem(R.id.nav_login);
        //  注册的条目钮;
        itemResign      =   navigationView.getMenu().findItem(R.id.nav_resign);

        //  进行轮播的加载;
        mBanner         =   findViewById(R.id.mbanner);
        //  将两个控件放置在1个组件上;
        toggle          =   new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //  进行相应的页面刷新控件;
        btnFlush        =   findViewById(R.id.btnFlush);

        //  刷新控件的定义;
        refreshLayout   =   findViewById(R.id.refresh_layout);

    }

    private void loadImages(){
        //  展示线程的关闭;
        //  线程关闭的内容;
        //  嵌套的相应的Thread->MyThread,先关外层，再关闭内层;
        flag =  false;
        if(thread!=null){
            thread.interrupt();
            thread=null;
        }
        if(myThread!=null){
            flag=false;
            myThread=null;
        }

        //  从文件夹里重新加载内容;
        sysTool.getContentFromFile();
        //
        imgpaths                      =   sysTool.listImgPath;
        ArrayList<String> listContent =   sysTool.listContent;

        //  浏览框信息加载;
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });

        //  设置轮播时间;
        mBanner.setDelayTime(TIME_SLEEP);

        int size    =   imgpaths.size();

//        //  判断文件的个数内容;
//        Log.i("MyLog","下载文件夹中的个数="+size);
        //  当数据个数为0时;
        if(size==0){
//            Log.i("MyLog","文件数量==0");
            //  初始化通知;
            int[]         imageReID  =   new int[]{R.mipmap.a1,R.mipmap.a2,R.mipmap.a3,R.mipmap.a4};
            List<Integer> imageLists =   new ArrayList<>();
            for(int id:imageReID){
                imageLists.add(id);
            }
            //  设置图片资源;
            mBanner.setImages(imageLists);
        }
        //  当数据个数非0时;
        else{
//            Log.i("MyLog","文件数量>0");
//            Log.i("MyLog","文字内容的长度="+listContent.size());

            // 字幕的表示;
            flag      = true;
            myThread  = new MyThread(listContent);
            thread    = new Thread(myThread);
            thread.start();

            //  设置图片资源;
            mBanner.setImages(imgpaths);
        }
        mBanner.start();
    }

    //  事件监听的添加;
    private void setListener(){
        //  加载图像内容;
        loadImages();

        //  对抽屉添加监听;
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //  进行相应的内容;
        String title="欢迎使用 仲裁应用！";
        if(idcard!=null)
            title="欢迎 "+name+" 使用！";
        tvLoginPerson.setText(title);

        //  侧栏位进行显示;
        if(name!=null)
            tvName.setText(name);
        else
            tvName.setText("您处于身份未登录状态");

        if (idcard!=null)
            tvIdcard.setText(idcard);
        else
            tvIdcard.setText("请进行身份登录");

        if(idcard!=null)
            itemLogin.setTitle("退出登录");
        else
            itemLogin.setTitle("用户登录");;

        //  选择案件;
        btnCase.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
            //  判断用户是否进行登录;
            if(idcard!=null&&!idcard.equals("")){
                Intent intent=new Intent(mContext,CaseActivity.class);
                startActivity(intent);
            }else
                Toast.makeText(mContext,"请用户先登录",Toast.LENGTH_SHORT).show();

            }
        });
        //  案件查询;
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  判断用户是否进行登录;
                if(idcard!=null&&!idcard.equals("")){
                    Intent intent=new Intent(mContext,QueryActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(mContext,"请用户先登录",Toast.LENGTH_SHORT).show();
            }
        });
        //  刷新按钮隐藏;
        btnFlush.setVisibility(View.GONE);

        //  联系我们按钮;
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext,ContactActivity.class);
                startActivity(intent);
            }
        });
        //  控件刷新内容;
        //  刷新的按钮;
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onRefresh(View headerView) {
                //  新声明的服务;
                Intent intent = new Intent(mContext,MyService.class);
                //  新服务的开启;
                startService(intent);
            }

            @Override
            public void onLoad(View footerView) {
                // TODO 内容的重新加载;
            }
        });
    }
    //  跑马灯的显示内容信息;
    private boolean flag=false;
    //  进行页面的刷新;
    @Override
    public void onRefresh() {
        // TODO
    }

    //  进行获取相应内容;
    public class MyBroadcastReceiver extends BroadcastReceiver {

        //需要实现的方法
        @Override
        public void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra("id");
            //  进行数据的刷新;
            if(id.equals("no")){
                //  进行图谱重新加载;
                Message message = mHandler.obtainMessage(100);
                mHandler.sendMessage(message);
            }
        }
    }

    //  显示内容;
    private class MyThread implements Runnable{
        private ArrayList<String> list;
        public  MyThread(ArrayList<String> list) {
            this.list=list;
        }
        @Override
        public void run() {
            int count=0;
            int size=list.size();

            while (flag){
                //  每一行的数据内容;
                String temp = list.get(count);
                try {

                    Message     msg    =   mHandler.obtainMessage(200);
                    msg.obj            =   temp;
                    //  进行数据传递的方式;
                    Bundle      bundle =   new Bundle();
                    bundle.putInt("index",count);
                    msg.setData(bundle);

                    //  传递数据的内容;
                    mHandler.sendMessage(msg);
                    //  线程沉睡的动作;
                    Thread.sleep(SysTool.TIME_STEP);

                    //  进行刷新计数的内容;
                    count++;
                    if(count==size)
                        count=0;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mReceiver!=null)
            unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(mContext,"select",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //  登录的主界面;
        if (id == R.id.nav_home) {
            // Handle the camera action
        }
        //  注册操作
        else if (id == R.id.nav_resign) {
            Intent intent=new Intent(mContext,ResignIdCardActivity.class);
            startActivity(intent);
        }
        //  登录操作;
        else if (id == R.id.nav_login) {
            String kind=itemLogin.getTitle().toString();
            if(kind.equals("用户登录")){
                Intent intent=new Intent(mContext,LoginActivity.class);
                startActivity(intent);
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);//内部类
                builder.setTitle("友情提示");
                builder.setMessage("您确定要退出吗?");
                //确定按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //  身份参数的重置;
                    sysTool.setSParameter(mContext,"login","idcard",null);
                    sysTool.setSParameter(mContext,"login","name",null);
                    sysTool.setSParameter(mContext,"login","birthday",null);
                    sysTool.setSParameter(mContext,"login","sex",null);
                    sysTool.setSParameter(mContext,"login","nation",null);
                    sysTool.setSParameter(mContext,"login","address",null);
                    sysTool.setSParameter(mContext,"login","belong",null);
                    sysTool.setSParameter(mContext,"login","phone",null);

                    //  关闭当前的窗口;
                    finish();
                    }
                });
                //点取消按钮
                builder.setNegativeButton("取消", null);
                builder.create();
                builder.show();
            }
        }
        //  分享操作;
        else if (id == R.id.nav_share) {
            //  进行设定页面的跳转;
            Intent intent=new Intent(mContext,SetActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    long l1=0;
    long l2=0;
    //  系统返回按钮的事件监听;
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            count++;
            //  第1次点击返回键的事件;
            if(count==1){
                Toast.makeText(mContext,"再点击1次，退出软件!",Toast.LENGTH_SHORT).show();
                l1  =   System.currentTimeMillis();

            }
            //  第2次点击返回键的事件;
            if(count==2){
                l2  =   System.currentTimeMillis();

                long ld  =   l2-l1;
                //  两次间隔<2秒
                if(ld<2000){
                    Toast.makeText(mContext,"退出软件",Toast.LENGTH_SHORT).show();
                    finish();
                }
                //  两次间隔>2秒
                else{
                    Toast.makeText(mContext,"两次间隔过长",Toast.LENGTH_SHORT).show();
                    count=0;
                }
            }
//            Log.i("MyLog","点击次数="+count);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
