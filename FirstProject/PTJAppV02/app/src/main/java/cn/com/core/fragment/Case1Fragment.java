package cn.com.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.com.core.R;
import cn.com.core.tool.SysTool;
import cn.com.core.ui.login.CaseActivity;
import cn.com.core.ui.login.WelActivity;

public class Case1Fragment extends BaseFragment {
    //  进行相应的初始化菜单内容;
    private View                view;
    private Context             context;
    private SysTool             sysTool;

    //  系统的功能模块;
    private FragmentTransaction ftransaction;
    private FragmentManager     fManager;

    //  控件的按钮;
    private TextView            btnUp,btnNext,btnFirst,btnExtract;
    //  进行相应的Checkbox的选择;
    private CheckBox[] checkBoxes   =   new CheckBox[2];
    //  此页面的相应内容;
    private EditText etname,etbirthday,etidcard,etnation,etphone,householdaddress,serviceaddress,zipcode,urgentperson,relationship,relationtel;
    private String idcard,name,birthday,sex,nation,address,belong,phone,tmp;
    private String[] sexs={"男","女"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_case_1, null, false);
        //  初始化相应的参数;
        initConfig();
        //  初始化相应的控件;
        initView(view);
        setListener();
        return view;
    }
    //  初试化控件;
    private void initView(View view){
        btnExtract= view.findViewById(R.id.btnExtract);
        btnUp     = view.findViewById(R.id.btnUp);
        btnFirst  = view.findViewById(R.id.btnFirst);
        btnNext   = view.findViewById(R.id.btnNext);
        //  将checkbox放入集合中;
        checkBoxes[0]= view.findViewById(R.id.cb1);
        checkBoxes[1]= view.findViewById(R.id.cb2);
        etname       = view.findViewById(R.id.etname);
        //  相应的数据内容;
        etbirthday   = view.findViewById(R.id.etbirthday);
        etidcard     = view.findViewById(R.id.etidcard);
        etnation     = view.findViewById(R.id.etnation);
        etphone      = view.findViewById(R.id.etphone);
        householdaddress= view.findViewById(R.id.householdaddress);
        serviceaddress  = view.findViewById(R.id.serviceaddress);
        zipcode      = view.findViewById(R.id.zipcode);
        urgentperson = view.findViewById(R.id.urgentperson);
        relationship = view.findViewById(R.id.relationship);
        relationtel  = view.findViewById(R.id.relationtel);

        //  进行相应的初始化控件的显示;
        idcard  = (String) sysTool.getSParameter(context,"login","idcard");
        name    = (String) sysTool.getSParameter(context,"login","name");
        birthday= (String) sysTool.getSParameter(context,"login","birthday");
        sex     = (String) sysTool.getSParameter(context,"login","sex");
        nation  = (String) sysTool.getSParameter(context,"login","nation");
        address = (String) sysTool.getSParameter(context,"login","address");
        belong  = (String) sysTool.getSParameter(context,"login","belong");
        phone   = (String) sysTool.getSParameter(context,"login","phone");
    }
    //  初始化参数;
    private void initConfig(){
        //  上下文的内容获取;
        context         =   getActivity();
        //  相应的系统方法声明;
        sysTool         =   new SysTool();
        fManager        =   getActivity().getSupportFragmentManager();
        ftransaction    =   fManager.beginTransaction();
        CaseActivity.tvPage.setText("第1页");
    }

    //  进行图像的实时处理;
    @Override
    public void onResume() {
        super.onResume();
        setListener();
    }

    //  进行相应的事件监听;
    private void setListener(){
        //  初始化的功能;
        if(CaseActivity.submitperson.isInit()){
            CaseActivity.submitperson.setName(name);
            CaseActivity.submitperson.setSex(sex);
            CaseActivity.submitperson.setBirthday(birthday);
            CaseActivity.submitperson.setIdcard(idcard);
            CaseActivity.submitperson.setNation(nation);
            CaseActivity.submitperson.setHouseholdaddress(address);
            CaseActivity.submitperson.setName(name);
            CaseActivity.submitperson.setInit(false);
        }
        //  显示界面的初始化;
        //  界面的内容显示;
        etname.setText(CaseActivity.submitperson.getName());
        //  男女的判断;
        if(CaseActivity.submitperson.getSex().equals("男")){
            checkBoxes[0].setChecked(true);
        }else
            checkBoxes[1].setChecked(true);
        //  出生年月;
        etbirthday.setText(CaseActivity.submitperson.getBirthday());
        //  身份证号;
        etidcard.setText(CaseActivity.submitperson.getIdcard());
        //  民族
        etnation.setText(CaseActivity.submitperson.getNation());
        //  联系电话;
        etphone.setText(CaseActivity.submitperson.getPhone());
        //  户籍地址;
        householdaddress.setText(CaseActivity.submitperson.getHouseholdaddress());
        //  送达地址;
        serviceaddress.setText(CaseActivity.submitperson.getServiceaddress());
        //  邮政编码
        zipcode.setText(CaseActivity.submitperson.getZipcode());
        //  紧急联系人;
        urgentperson.setText(CaseActivity.submitperson.getUrgentperson());
        //  与之联系的电话;
        relationship.setText(CaseActivity.submitperson.getRelationship());
        //  联系电话;
        relationtel.setText(CaseActivity.submitperson.getRelationtel());
        ////////////////////////////////////////////////////
        //  点击首页的按钮;
        btnExtract.setVisibility(View.GONE);
        //  上一页的按钮;
        btnUp.setVisibility(View.GONE);
        btnFirst.setText("已首页");

        btnFirst.setEnabled(false);
        //  辅助功能没有
        btnExtract.setVisibility(View.GONE);

        //  下一个的按钮;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  姓名设置;
                CaseActivity.submitperson.setName(etname.getText().toString());
                //  男女的判断;
                CaseActivity.submitperson.setSex(sex);
                //  出生年月;
                CaseActivity.submitperson.setBirthday(etbirthday.getText().toString());
                //  身份证号;
                CaseActivity.submitperson.setIdcard(idcard);
                //  民族
                CaseActivity.submitperson.setNation(etnation.getText().toString());
                //  联系电话;
                CaseActivity.submitperson.setPhone(etphone.getText().toString());
                //  户籍地址;
                CaseActivity.submitperson.setHouseholdaddress(householdaddress.getText().toString());
                //  送达地址;
                CaseActivity.submitperson.setServiceaddress(serviceaddress.getText().toString());
                //  邮政编码
                CaseActivity.submitperson.setZipcode(zipcode.getText().toString());
                //  紧急联系人;
                CaseActivity.submitperson.setUrgentperson(urgentperson.getText().toString());
                //  与之联系的电话;
                CaseActivity.submitperson.setRelationship(relationship.getText().toString());
                //  联系电话;
                CaseActivity.submitperson.setRelationtel(relationtel.getText().toString());


                //  从左向右进行页面的平移
                ftransaction.setCustomAnimations(R.anim.cu_push_down_in,R.anim.cu_push_up_out);
                ftransaction.replace(R.id.container1, WelActivity.case2Fragment);
                ftransaction.addToBackStack("case2");
                //   transaction.commit();
                ftransaction.commitAllowingStateLoss();

            }
        });
        //  进行相应的监听设置;
        checkBoxes[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedChanged(buttonView, isChecked, 0);
            }
        });
        checkBoxes[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedChanged(buttonView,isChecked,1);
            }
        });
    }
    //  进行按钮的单选;
    public void checkedChanged(CompoundButton compoundButton, boolean isChecked,int index) {
        if(isChecked){
            compoundButton.isChecked();
            for(int i=0;i<checkBoxes.length;i++){
                if(i==index)
                    checkBoxes[i].setChecked(true);
                else
                    checkBoxes[i].setChecked(false);
            }
            sex=sexs[index];
        }
    }
}
