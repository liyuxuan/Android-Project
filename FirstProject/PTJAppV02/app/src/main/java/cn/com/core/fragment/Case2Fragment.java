package cn.com.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.com.core.R;
import cn.com.core.ui.login.CaseActivity;
import cn.com.core.ui.login.WelActivity;

public class Case2Fragment extends BaseFragment {
    private View view;
    private TextView btnUp,btnNext,btnFirst,btnExtract;
    private Context context;
    private Bundle savedInstanceState;
    private FragmentManager     fManager;
    private FragmentTransaction ftransaction;
    private EditText unitname,unitboss,unitright,unitaddress1,unitaddress2,unitcontactperson,unitcontactright,belongdept,deptphone;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_case_2, null, false);
        this.savedInstanceState=savedInstanceState;
        //  初始化相应的控件;
        initView(view);
        //  初始化相应的参数;
        initConfig();

        return view;
    }
    //  初试化控件;
    private void initView(View view){
        btnUp   =view.findViewById(R.id.btnUp);
        btnNext =view.findViewById(R.id.btnNext);
        btnFirst=view.findViewById(R.id.btnFirst);
        btnExtract=view.findViewById(R.id.btnExtract);

        //  第二页的内容填空;
        unitname=view.findViewById(R.id.unitname);
        unitboss=view.findViewById(R.id.unitboss);
        unitright=view.findViewById(R.id.unitright);
        unitaddress1=view.findViewById(R.id.unitaddress1);
        unitaddress2=view.findViewById(R.id.unitaddress2);
        unitcontactperson=view.findViewById(R.id.unitcontactperson);
        unitcontactright=view.findViewById(R.id.unitcontactright);
        belongdept=view.findViewById(R.id.belongdept);
        deptphone=view.findViewById(R.id.deptphone);
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
        //  辅助功能设置为新增;
        btnExtract.setText("新\r\r增");
        CaseActivity.tvPage.setText("第2页");
        //  进行数据提取的操作;

            unitname.setText(CaseActivity.submitperson.getUnitname());
            unitboss.setText(CaseActivity.submitperson.getUnitboss());

            unitright.setText(CaseActivity.submitperson.getUnitright());

            unitaddress1.setText(CaseActivity.submitperson.getUnitaddress1());

            unitaddress2.setText(CaseActivity.submitperson.getUnitaddress2());

            unitcontactperson.setText(CaseActivity.submitperson.getUnitcontactperson());

            unitcontactright.setText(CaseActivity.submitperson.getUnitcontactright());

            belongdept.setText(CaseActivity.submitperson.getBelongdept());

            deptphone.setText(CaseActivity.submitperson.getDeptphone());


        //  点击首页的按钮;
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftransaction.setCustomAnimations(R.anim.cu_push_left_in,R.anim.cu_push_right_out);
                ftransaction.replace(R.id.container1,WelActivity.case1Fragment);
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
                ftransaction.replace(R.id.container1, WelActivity.case1Fragment);
                ftransaction.addToBackStack("case1");
                ftransaction.commitAllowingStateLoss();;
            }
        });
        //  下一个的按钮;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  数据信息的插入;
                CaseActivity.submitperson.setUnitname(unitname.getText().toString());
                CaseActivity.submitperson.setUnitboss(unitboss.getText().toString());
                CaseActivity.submitperson.setUnitright(unitright.getText().toString());
                CaseActivity.submitperson.setUnitaddress1(unitaddress1.getText().toString());
                CaseActivity.submitperson.setUnitaddress2(unitaddress2.getText().toString());
                CaseActivity.submitperson.setUnitcontactperson(unitcontactperson.getText().toString());
                CaseActivity.submitperson.setUnitcontactright(unitcontactright.getText().toString());
                CaseActivity.submitperson.setBelongdept(belongdept.getText().toString());
                CaseActivity.submitperson.setDeptphone(deptphone.getText().toString());

                //  从左向右进行页面的平移
                ftransaction.setCustomAnimations(R.anim.cu_push_down_in,R.anim.cu_push_up_out);
                ftransaction.replace(R.id.container1, WelActivity.case3Fragment);
                ftransaction.addToBackStack("case3");
                ftransaction.commitAllowingStateLoss();

            }
        });
    }
    //
}
