package cn.com.core.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import cn.com.core.R;
import cn.com.core.tool.PaintSurfaceView;
import cn.com.core.tool.SysTool;
import cn.com.core.ui.login.CaseActivity;

public class Case6Fragment extends BaseFragment {
    private Activity            mContext;
    private View                view;
    private TextView            btnUp,btnNext,btnFirst,btnExtract;
    private FragmentManager     fManager;
    private FragmentTransaction ftransaction;

    private PaintSurfaceView    mSurfaceView;
    private ImageView           mShowIv;
    private Button              btnSave,btnClear,btnCallBack;
    private String              signature;
    private ProgressDialog      mProgressDialog;

    //  进行相应的数据控件;
    private SysTool sysTool;

    //  进行相应数据传输的功能模块;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3:
                    String result = (String) msg.obj;
                    if(result!=null){
                        Log.i("MyLog","提交成功="+result);
                        Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();

                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                            mProgressDialog=null;
                            //  关闭框;
                            CaseActivity.mContext.finish();
                        }
                    }else
                        Toast.makeText(getActivity(),R.string.app_oper_fail,Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_case_6, null, false);
        //  初始化相应的控件;
        initView(view);
        //  初始化相应的参数;
        initConfig();

        return view;
    }
    //  初试化控件;
    private void initView(View view){
        btnUp    = view.findViewById(R.id.btnUp);
        btnNext  = view.findViewById(R.id.btnNext);
        btnFirst = view.findViewById(R.id.btnFirst);
        btnExtract= view.findViewById(R.id.btnExtract);
        //  插件声明;
        mSurfaceView= view.findViewById(R.id.test_hand_write);
        mShowIv     = view.findViewById(R.id.iv_test_image);
        btnSave     = view.findViewById(R.id.btnSave);
        btnClear    = view.findViewById(R.id.btnClean);
        btnCallBack = view.findViewById(R.id.btnCallBack);
    }
    //  初始化参数;
    private void initConfig(){
        fManager        =   getActivity().getSupportFragmentManager();
        ftransaction    =   fManager.beginTransaction();
        //  进行控件的声明;
        sysTool         =   new SysTool(getActivity());
        mContext        =   getActivity();
    }

    //  进行图像的实时处理;
    @Override
    public void onResume() {
        super.onResume();
        setListener();
    }

    //  进行相应的事件监听;
    private void setListener(){
        CaseActivity.tvPage.setText("第6页");
        //  辅助功能设置为上传文件;
        btnExtract.setText("上\r\r传");

        //  上传文件;
        btnExtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  进行文件相应的上传处理;
                String httpUrl  = null;

                String parameter= null;

                //  照片;
                String      p1  = CaseActivity.submitperson.getProofpath();

                //  状态;
                String      p2  = signature;

                try {
                    parameter =
                    "operType=0&name="+ URLEncoder.encode(CaseActivity.submitperson.getName(),"utf-8") +"&" +
                    "birthday="+URLEncoder.encode(CaseActivity.submitperson.getBirthday(),"utf-8")+"&" +
                    "idcard="+CaseActivity.submitperson.getIdcard()+"&" +

                    "sex="+URLEncoder.encode(CaseActivity.submitperson.getSex(),"utf-8")+"&" +
                    "nation="+URLEncoder.encode(CaseActivity.submitperson.getNation(),"utf-8")+"&" +
                    "phone="+URLEncoder.encode(CaseActivity.submitperson.getPhone(),"utf-8")+"&" +

                    "householdaddress="+URLEncoder.encode(CaseActivity.submitperson.getHouseholdaddress(),"utf-8")+"&" +
                    "serviceaddress="+URLEncoder.encode(CaseActivity.submitperson.getServiceaddress(),"utf-8")+"&" +
                    "zipcode="+CaseActivity.submitperson.getZipcode()+"&" +

                    "urgentperson="+URLEncoder.encode(CaseActivity.submitperson.getUrgentperson(),"utf-8")+"&" +
                    "relationship="+URLEncoder.encode(CaseActivity.submitperson.getRelationship(),"utf-8")+"&" +
                    "relationtel="+CaseActivity.submitperson.getRelationtel()+"&" +

                    "unitname="+URLEncoder.encode(CaseActivity.submitperson.getUnitname(),"utf-8")+"&" +
                    "unitboss="+URLEncoder.encode(CaseActivity.submitperson.getUnitboss(),"utf-8")+"&" +
                    "unitright="+URLEncoder.encode(CaseActivity.submitperson.getUnitright(),"utf-8")+"&" +

                    "unitaddress1="+URLEncoder.encode(CaseActivity.submitperson.getUnitaddress1(),"utf-8")+"&" +
                    "unitaddress2="+URLEncoder.encode(CaseActivity.submitperson.getUnitaddress2(),"utf-8")+"&" +
                    "unitcontactperson="+URLEncoder.encode(CaseActivity.submitperson.getUnitcontactperson(),"utf-8")+"&" +


                    "unitcontactright="+URLEncoder.encode(CaseActivity.submitperson.getUnitcontactright(),"utf-8")+"&" +
                    "belongdept="+URLEncoder.encode(CaseActivity.submitperson.getBelongdept(),"utf-8")+"&" +
                    "deptphone="+URLEncoder.encode(CaseActivity.submitperson.getDeptphone(),"utf-8")+"&" +

                    "submitreason="+URLEncoder.encode(CaseActivity.submitperson.getSubmitreason(),"utf-8")+"&" +
                    "submitcontent="+URLEncoder.encode(CaseActivity.submitperson.getSubmitcontent(),"utf-8")+"&" +
                    "toaddress="+URLEncoder.encode(CaseActivity.submitperson.getToaddress(),"utf-8")+"&" +

                    "tozipcode="+CaseActivity.submitperson.getTozipcode()+"&" +
                    "tocontactphone="+CaseActivity.submitperson.getTocontactphone()+"&" +
                    "tomailaddress="+URLEncoder.encode(CaseActivity.submitperson.getTomailaddress(),"utf-8")+"&" +

                    "towechat="+CaseActivity.submitperson.getTowechat()+"&" +
                    "tocollector="+URLEncoder.encode(CaseActivity.submitperson.getTocollector(),"utf-8")+"&" +
                    "tocollectorphone="+CaseActivity.submitperson.getTocollectorphone()+"&" +

                    "proofpath="+new File(CaseActivity.submitperson.getProofpath()).getName()+"&" +
                    "signature="+new File(signature).getName();
                    //  将相应的数据进行打包处理;
                    httpUrl     =     sysTool.getHttpUrl("Case",parameter);
                    //  Log.i("MyLog","信息内容="+httpUrl);
                    ArrayList<String>  paths    =   new ArrayList<>();
                    paths.add(CaseActivity.submitperson.getProofpath());
                    paths.add(signature);
                    //  传输提示框的建立;
                    mProgressDialog =   sysTool.progressDialogLoading(mContext,"人脸采样完成","正在进行数据传输");
                    mProgressDialog.create();
                    //  提示栏目的显示;
                    mProgressDialog.show();

                    submitInfo(httpUrl,paths);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        //  点击首页的按钮;
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftransaction.setCustomAnimations(R.anim.cu_push_left_in,R.anim.cu_push_right_out);
                ftransaction.replace(R.id.container1, new Case1Fragment());
                ftransaction.addToBackStack("case1");
                ftransaction.commitAllowingStateLoss();
            }
        });
        //  上一页的按钮;
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  从左向右进行页面的平移
                ftransaction.setCustomAnimations(R.anim.cu_push_up_in,R.anim.cu_push_down_out);
                ftransaction.replace(R.id.container1, new Case5Fragment());
                ftransaction.addToBackStack("case5");
                ftransaction.commitAllowingStateLoss();
            }
        });
        btnNext.setText("已末页");
        btnNext.setEnabled(false);
        //  保存;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap=mSurfaceView.getAreaImage();
                if (bitmap!=null){
                    mShowIv.setImageBitmap(bitmap);
                    signature=sysTool.saveBitmap(bitmap);
                }
            }
        });
        //  清除方法;
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.clearAreaImage();
                mShowIv.setImageBitmap(null);
            }
        });

        btnCallBack.setVisibility(View.GONE);
        //  回滚方法;
        btnCallBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSurfaceView.doReplay();
            }
        });
    }

    //  进行数据传输的
    //  进行消息的提交;
    private void submitInfo(final String httpUrl, final ArrayList<String> paths){
        new Thread(){
            @Override
            public void run() {
                String result   = sysTool.httpPostResponse2(httpUrl,paths);
                Message message = mHandler.obtainMessage(3);
                message.obj     = result;
                mHandler.sendMessage(message);
            }
        }.start();
    }
}
