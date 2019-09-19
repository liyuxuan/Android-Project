package cn.com.core.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.JsonObject;
import com.sxu.refreshlayout.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cn.com.core.R;
import cn.com.core.data.model.Submitperson;
import cn.com.core.tool.ListAdapter;
import cn.com.core.tool.MyContent;
import cn.com.core.tool.SysTool;

//import cn.com.core.R;

public class ListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    //  界面的上下文内容;
    private View                    view;
    //  上下文的内容;
    private Activity                context;
    //  页面控制跳转的内容;
    private FragmentManager         fManager;
    //  跳转控制器;
    private FragmentTransaction     ftransaction;
    //  显示的列表;
    private ListView listView;
    private ArrayList<MyContent>    mDatas;
    private ArrayList<Submitperson> list;
    private ListAdapter             listAdapter;
    private RefreshLayout           refreshLayout;

//    private BaseAdapter adapter;
    //  对话框的调节;
//    private ProgressDialog mProgressDialog;
    //  系统的方法;
    private SysTool sysTool;
    //  数据初始化;
    private String  idcard;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result=  "";
            mDatas.clear();
            list.clear();
//            if (msg.what == 100) {
//                refreshLayout.refreshComplete();
//            } else
            if(msg.what==3){
                result   = (String) msg.obj;
                result   = sysTool.getStringUTF8(result);
                JSONArray jsonArray =   null;
                try {
                    //  进行数据的解析;
                    jsonArray       =   new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        String autoid=object.getString("auto_id");
                        String name=object.getString("name");
                        String birthday=object.getString("birthday");
                        String idcard=object.getString("idcard");
                        String sex=object.getString("sex");
                        String nation=object.getString("nation");
                        String phone=object.getString("phone");
                        String householdaddress=object.getString("householdaddress");
                        String serviceaddress=object.getString("serviceaddress");
                        String zipcode=object.getString("zipcode");
                        String urgentperson=object.getString("urgentperson");
                        String relationship=object.getString("relationship");
                        String relationtel=object.getString("relationtel");
                        String unitname=object.getString("unitname");
                        String unitboss=object.getString("unitboss");
                        String unitright=object.getString("unitright");
                        String unitaddress1=object.getString("unitaddress1");
                        String unitaddress2=object.getString("unitaddress2");
                        String unitcontactperson=object.getString("unitcontactperson");
                        String unitcontactright=object.getString("unitcontactright");
                        String belongdept=object.getString("belongdept");
                        String deptphone=object.getString("deptphone");
                        String submitreason=object.getString("submitreason");
                        String submitcontent=object.getString("submitcontent");
                        String toaddress=object.getString("toaddress");
                        String tozipcode=object.getString("tozipcode");
                        String tocontactphone=object.getString("tocontactphone");
                        String tomailaddress=object.getString("tomailaddress");
                        String towechat=object.getString("towechat");
                        String tocollector=object.getString("tocollector");
                        String tocollectorphone=object.getString("tocollectorphone");
                        String proofpath=object.getString("proofpath");
                        String signature=object.getString("signature");
                        String datetime=object.getString("datetime");

                        datetime=sysTool.getData(datetime);

                        list.add(new Submitperson(autoid,name, birthday, idcard, sex, nation, phone, householdaddress, serviceaddress, zipcode, urgentperson, relationship, relationtel, unitname, unitboss, unitright, unitaddress1, unitaddress2, unitcontactperson, unitcontactright, belongdept, deptphone,  submitreason, submitcontent, toaddress, tozipcode, tocontactphone, tomailaddress, towechat, tocollector, tocollectorphone, proofpath, signature,datetime));
                        mDatas.add(new MyContent(datetime+" 案件"));
                        //  适配器中的数据的传递;
                        listAdapter = new ListAdapter(getActivity(), mDatas,list,ftransaction, new ItemFragment(), R.id.container2);
                        //  将列表信息添加到适配器;
                        listView.setAdapter(listAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //  消除加载控件;
            if( refreshLayout!=null){
                refreshLayout.refreshComplete();
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_list_slide, null, false);

        //  初始化相应的参数;
        initConfig();
        //  初始化相应的控件;
        initView(view);

        //  进行数据的初始化;
        setListener();
        //  及时刷新内容;
        initLoad();

        return view;
    }

    //  初试化控件;
    private void initView(View view) {

        refreshLayout   =   view.findViewById(R.id.refresh_layout);

        listView        =   view.findViewById(R.id.main_list);
    }

    //  初始化参数;
    private void initConfig() {
        //  上下文的内容获取;
        context      = getActivity();
        fManager     = getActivity().getSupportFragmentManager();
        ftransaction = fManager.beginTransaction();
        //  进行系统的方法;
        sysTool      = new SysTool(context);
        //  进行相应的初始化控件的显示;
        idcard       = (String) sysTool.getSParameter(context,"login","idcard");

        //  加载数据集合;
        mDatas       = new ArrayList<>();
        list         = new ArrayList<>();
    }

    //  进行图像的实时处理;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();

    }

    //  进行相应的事件监听;
    private void setListener() {
        //  刷新的按钮;
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onRefresh(View headerView) {
//                refreshInfo();
                String parameter= "operType=1&idcard="+idcard;
                String httpUrl  = sysTool.getHttpUrl("Case",parameter);
                refreshInfo(httpUrl,null);
            }

            @Override
            public void onLoad(View footerView) {
                // TODO 内容的重新加载;
            }
        });

    }
    //  进行数据的初始化更新;
    @SuppressLint("NewApi")
    private void initLoad(){
        //  进行身份证件的校验;
        String parameter= "operType=1&idcard="+idcard;
        String httpUrl  = sysTool.getHttpUrl("Case",parameter);
//        Log.i("MyLog","http:"+httpUrl);
        //  传输提示框的建立;
//        mProgressDialog =   sysTool.progressDialogLoading(context,"数据更新","正在进行数据查询");
//
//        mProgressDialog.create();
//        //  提示栏目的显示;
//        mProgressDialog.show();
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

    //  进行消息的更新;
    private void refreshInfo(final String httpUrl, final String path){
        new Thread(){
            @Override
            public void run() {
            String result = sysTool.httpPostResponse(httpUrl,path);
            Message message = mHandler.obtainMessage(3);
            message.obj = result;
            mHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public void onRefresh() {
//        tv_simple.setText("正在刷新");
//        mHandler.postDelayed(mRefresh, 1000);
    }
}
