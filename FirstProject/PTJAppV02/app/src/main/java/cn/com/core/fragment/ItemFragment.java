package cn.com.core.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.core.R;
import cn.com.core.data.model.ShowItem;
import cn.com.core.data.model.Submitperson;
import cn.com.core.tool.ListAdapter;
import cn.com.core.tool.MyContent;
import cn.com.core.tool.SysTool;
import cn.com.core.ui.login.BigImgActivity;
import cn.com.core.ui.login.CaseActivity;

public class ItemFragment extends BaseFragment implements OnBannerListener {
    private View                view;
    private Activity            context;
    private Bundle              savedInstanceState;
    private FragmentManager     fManager;
    private FragmentTransaction ftransaction;
    private TextView            btnBack;
    //  接收的对象信息;
    private Submitperson        person;
    //  列表信息的显示;
    private ListView            lvDetail;
    //  进行列表的自定义内容;
    private MyAdapter           myAdapter;
    //  数据信息;
    private ArrayList<ShowItem> lists;
    //  图片内容的书写;
    private Banner              banner;
    private ArrayList<String>   list_path;
    //  进行数据的id编号;
    private String              auto_id;
    //  系统方法;
    private SysTool             sysTool;
    //  进行加载的对话框;
    private ProgressDialog mProgressDialog;

    //  窗口加载的内容;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=  "";
            list_path = new ArrayList<>();
            if(msg.what==3){
                result   = (String) msg.obj;

                JSONArray jsonArray;
                try {
                    jsonArray=new JSONArray(result);
                    for(int i=0;i<jsonArray.length();i++){
                        String item=jsonArray.get(i).toString();
                        list_path.add(item);
                        setBanner();
                        Log.i("MyLog","item="+item);
                    }
                    if(mProgressDialog!=null){
                        mProgressDialog.dismiss();
                        mProgressDialog=null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_item_slide_detail, null, false);
        this.savedInstanceState = savedInstanceState;
        //  初始化相应的参数;
        initConfig();
        //  初始化相应的控件;
        initView(view);

        //  加载相应的数据;
        loadImages();

        return view;
    }

    //  初试化控件;
    private void initView(View view) {
        //  返回键的按钮;
        btnBack     =   view.findViewById(R.id.btnBack);
        //  显示数据的列表;
        lvDetail    =   view.findViewById(R.id.lvDetail);
        //  轮播图点击按钮;
        banner      =   view.findViewById(R.id.banner);

        myAdapter   =   new MyAdapter(context,lists);
        //  适配器的添加;
        lvDetail.setAdapter(myAdapter);
    }

    //  初始化参数;
    private void initConfig() {
        //  上下文的内容获取;
        context  = getActivity();
        fManager = getActivity().getSupportFragmentManager();
        ftransaction = fManager.beginTransaction();
        //  系统方法;
        sysTool  = new SysTool(context);


        //  获得数据初始化内容;
        this.savedInstanceState=getArguments();
        if(this.savedInstanceState!=null){
            //  进行适配器声明;
            lists       =   new ArrayList<>();
            person      =   (Submitperson) this.savedInstanceState.getSerializable("person");
            auto_id     =   person.getAutoid();
            //  进行数据的添加;
            lists.add(new ShowItem("案件的编号",auto_id));
            lists.add(new ShowItem("提交日期",person.getDatetime()));
            lists.add(new ShowItem("申请人姓名",person.getName()));
            lists.add(new ShowItem("申请人生日",person.getBirthday()));
            lists.add(new ShowItem("身份证号码",person.getIdcard()));
            lists.add(new ShowItem("申请人性别",person.getSex()));
            lists.add(new ShowItem("申请人民族",person.getNation()));
            lists.add(new ShowItem("联系电话号",person.getPhone()));
            lists.add(new ShowItem("户籍地址",person.getHouseholdaddress()));
            lists.add(new ShowItem("送达地址",person.getServiceaddress()));
            lists.add(new ShowItem("邮政编码",person.getZipcode()));
            lists.add(new ShowItem("紧急联系人",person.getUrgentperson()));
            lists.add(new ShowItem("与之关系",person.getRelationship()));
            lists.add(new ShowItem("联系人电话",person.getRelationtel()));
            lists.add(new ShowItem("单位名称",person.getUnitname()));
            lists.add(new ShowItem("法定代表",person.getUnitboss()));
            lists.add(new ShowItem("人员职务",person.getUnitright()));
            lists.add(new ShowItem("单位注册地址",person.getUnitaddress1()));
            lists.add(new ShowItem("单位经营地址",person.getUnitaddress2()));
            lists.add(new ShowItem("单位联系人",person.getUnitcontactperson()));
            lists.add(new ShowItem("联系人职务",person.getUnitcontactright()));
            lists.add(new ShowItem("所在部门",person.getBelongdept()));
            lists.add(new ShowItem("单位电话",person.getDeptphone()));
            lists.add(new ShowItem("申请理由",person.getSubmitreason()));
            lists.add(new ShowItem("申请内容",person.getSubmitcontent()));
            lists.add(new ShowItem("送达地址",person.getToaddress()));
            lists.add(new ShowItem("邮政编码",person.getTozipcode()));
            lists.add(new ShowItem("联系电话",person.getTocontactphone()));
            lists.add(new ShowItem("联系地址",person.getTomailaddress()));
            lists.add(new ShowItem("微信号码",person.getTowechat()));
            lists.add(new ShowItem("代收人员",person.getTocollector()));
            lists.add(new ShowItem("联系电话",person.getTocollectorphone()));
//            lists.add(new ShowItem("户籍地址",person.getProofpath()));
//            lists.add(new ShowItem("户籍地址",person.getSignature()));
        }

    }
    //  加载图像数据信息;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void loadImages(){
        //  进行身份证件的校验;
        String parameter= "operType=2&auto_id="+auto_id;
        String httpUrl  = sysTool.getHttpUrl("Case",parameter);
        //  传输提示框的建立;
        mProgressDialog =   sysTool.progressDialogLoading(context,"图片加载，请稍等!","正在进行数据传输");
        mProgressDialog.create();
        //  提示栏目的显示;
        mProgressDialog.show();
        queryInfo(httpUrl,null);
    }

    //  进行消息的提交;
    private void queryInfo(final String httpUrl, final String path){
        new Thread(){
            @Override
            public void run() {
            String result = sysTool.httpPostResponse(httpUrl,path);
//            Log.i("MyLog","res="+result);
            Message message = mHandler.obtainMessage(3);
            message.obj = result;
            mHandler.sendMessage(message);
            }
        }.start();
    }


    //  进行图像的实时处理;
    @Override
    public void onResume() {
        super.onResume();
        setListener();
    }

    //  进行相应的事件监听;
    private void setListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  从左向右进行页面的平移
                ftransaction.setCustomAnimations(R.anim.cu_push_left_in,R.anim.cu_push_right_out);
                ftransaction.replace(R.id.container2, new ListFragment());
                ftransaction.addToBackStack("list");
                ftransaction.commitAllowingStateLoss();
            }
        });
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), BigImgActivity.class);
        intent.putStringArrayListExtra("imgData", list_path);
        intent.putExtra("clickPosition", position);
        startActivity(intent);
    }

    //自定义的图片加载器
    private class ImgLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }


    /**
     * 设置轮播图
     */
    private void setBanner() {

        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);

        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new ImgLoader());

        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
//        banner.setBannerAnimation(Transformer.ZoomOutSlide);

        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”
        banner.isAutoPlay(true);

        //设置图片网址或地址的集合
        banner.setImages(list_path);

        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }



    //  自定义的列表显示控件;
    public class MyAdapter extends BaseAdapter{

        private Context             context;
        private ArrayList<ShowItem> lists;
        private int                 size;

        public MyAdapter(Context context,ArrayList<ShowItem> lists) {
            this.context    =   context;
            this.lists      =   lists;
            this.size       =   this.lists.size();
        }

        @Override
        public int getCount() {
            return this.size;
        }

        @Override
        public Object getItem(int position) {
            return this.lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //  初始化控件;
            ViewHolder viewHolder=null;

            if (convertView == null){
                //  显示控件的相应内容;
                convertView = LayoutInflater.from(this.context).inflate(R.layout.act_item_4, null);
                viewHolder  = new ViewHolder();
                //  显示的标题;
                viewHolder.tvTitle      =   (TextView)convertView.findViewById(R.id.tvTitle);
                //  显示的内容;
                viewHolder.tvContent    =   (TextView) convertView.findViewById(R.id.tvContent);
                //  显示控件的标签;
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //  对象内容;
            ShowItem item   = lists.get(position);
            //  标题;
            String   title  = item.getTitle();
            //  内容;
            String   content= item.getContent();

            //  标题内容进行一一对应;
            viewHolder.tvTitle.setText(title);
            viewHolder.tvContent.setText("\t\t"+content);

            return convertView;


        }
    }
    public class ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
    }
}
