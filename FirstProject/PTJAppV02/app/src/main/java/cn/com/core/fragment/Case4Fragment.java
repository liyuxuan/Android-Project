package cn.com.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.com.core.R;
import cn.com.core.ui.login.CaseActivity;
import cn.com.core.ui.login.WelActivity;

public class Case4Fragment extends BaseFragment {
    private View view;
    private TextView btnUp,btnNext,btnFirst,btnExtract;
    private Context context;
    private Bundle savedInstanceState;
    private FragmentManager     fManager;
    private FragmentTransaction ftransaction;
    private EditText toaddress,tozipcode,tocontactphone,tomailaddress,towechat,tocollector,tocollectorphone;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_case_4, null, false);
        this.savedInstanceState=savedInstanceState;
        //  初始化相应的控件;
        initView(view);
        //  初始化相应的参数;
        initConfig();

        return view;
    }
    //  初试化控件;
    private void initView(View view){
        btnUp     = view.findViewById(R.id.btnUp);
        btnNext   = view.findViewById(R.id.btnNext);
        btnFirst  = view.findViewById(R.id.btnFirst);
        btnExtract= view.findViewById(R.id.btnExtract);
        //  第4页的数据内容;
        toaddress = view.findViewById(R.id.toaddress);
        tozipcode = view.findViewById(R.id.tozipcode);
        tocontactphone=view.findViewById(R.id.tocontactphone);
        tomailaddress =view.findViewById(R.id.tomailaddress);
        towechat   = view.findViewById(R.id.towechat);
        tocollector= view.findViewById(R.id.tocollector);
        tocollectorphone= view.findViewById(R.id.tocollectorphone);
    }
    //  初始化参数;
    private void initConfig(){
        //  上下文的内容获取;
        context         =   getContext();
        fManager        =   getActivity().getSupportFragmentManager();
        ftransaction    =   fManager.beginTransaction();
    }

    //  进行图像的实时处理;
    @Override
    public void onResume() {
        super.onResume();
        setListener();
    }

    //  进行相应的事件监听;
    private void setListener(){
        CaseActivity.tvPage.setText("第4页");
        //  进行数据的相应输入;
        toaddress.setText(CaseActivity.submitperson.getToaddress());

        tozipcode.setText(CaseActivity.submitperson.getTozipcode());
        tocontactphone.setText(CaseActivity.submitperson.getTocontactphone());
        tomailaddress.setText(CaseActivity.submitperson.getTomailaddress());
        towechat.setText(CaseActivity.submitperson.getTowechat());
        tocollector.setText(CaseActivity.submitperson.getTocollector());
        tocollectorphone.setText(CaseActivity.submitperson.getTocollectorphone());

        //  进行设置为空;
        btnExtract.setVisibility(View.GONE);
        //  点击首页的按钮;
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftransaction.setCustomAnimations(R.anim.cu_push_left_in,R.anim.cu_push_right_out);
                ftransaction.replace(R.id.container1, WelActivity.case1Fragment);
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
                ftransaction.replace(R.id.container1, WelActivity.case3Fragment);
                ftransaction.addToBackStack("case3");
                ftransaction.commitAllowingStateLoss();
            }
        });
        //  下一个的按钮;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  进行内容的添加;
                String address=toaddress.getText().toString();
                String zipcode=tozipcode.getText().toString();
                String contactphone=tocontactphone.getText().toString();

                //  都不为空的情况下进行相应的页面跳转;
                if(!address.equals("")&&!zipcode.equals("")&&!contactphone.equals("")){

                    //  进行下一页的数据的提交;
                    CaseActivity.submitperson.setToaddress(address);
                    CaseActivity.submitperson.setTozipcode(zipcode);
                    CaseActivity.submitperson.setTocontactphone(contactphone);
                    CaseActivity.submitperson.setTomailaddress(tomailaddress.getText().toString());
                    CaseActivity.submitperson.setTowechat(towechat.getText().toString());
                    CaseActivity.submitperson.setTocollector(tocollector.getText().toString());
                    CaseActivity.submitperson.setTocollectorphone(tocollectorphone.getText().toString());

                    //  从左向右进行页面的平移
                    ftransaction.setCustomAnimations(R.anim.cu_push_down_in,R.anim.cu_push_up_out);
                    ftransaction.replace(R.id.container1, WelActivity.case5Fragment);
                    ftransaction.addToBackStack("case5");
                    ftransaction.commitAllowingStateLoss();
                }else
                    Toast.makeText(getActivity(),"请将信息补充完整",Toast.LENGTH_LONG).show();


            }
        });
    }
}
