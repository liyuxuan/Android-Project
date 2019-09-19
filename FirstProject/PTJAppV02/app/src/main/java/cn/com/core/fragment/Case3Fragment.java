package cn.com.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import cn.com.core.R;
import cn.com.core.data.model.Reason;
import cn.com.core.tool.MFlowLayout;
import cn.com.core.ui.login.CaseActivity;
import cn.com.core.ui.login.WelActivity;

public class Case3Fragment extends BaseFragment {
    private View view;
    private TextView btnUp,btnNext,btnFirst,btnExtract;
    private Context context;
    private Bundle savedInstanceState;
    private FragmentManager     fManager;
    private FragmentTransaction ftransaction;
    //  进行相应的数据包络的使用;
    private ListView    lvReasons;
    private MyAdapter   myAdapter;
    private ArrayList<Reason> listReasons;
//    private MyLinearLayout layCase3;
    private LinearLayout layContent;
    private LinearLayout layReason;
    //  进行内容的布局内容;
    private MFlowLayout mContentLayout;
    //  进行理由的布局内容;
    private MFlowLayout mReasonLayout;


    //  刷新控件的内容;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    //  清除之前的所有的布局内部的控件;
                    layContent.removeAllViews();
                    //  清除之前的所有的布局内部的控件;
                    layReason.removeAllViews();
                    //  获取当前的索引信息;
                    int      position = (int) msg.obj;
                    //  头部控件的添加;
                    String   title    =  listReasons.get(position).getTitle();
                    String   content  = listReasons.get(position).getContent();
                    String   reason   = listReasons.get(position).getReason();
                    //  进行相应的理由内容的按钮;
                    mContentLayout.addLinearLayoutView(content);
                    mContentLayout.getLinearLayoutView(layContent);

                    //  进行相应的
                    mReasonLayout.addLinearLayoutView(reason);
                    mReasonLayout.getLinearLayoutView(layReason);
                    break;

            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_case_3, null, false);
        this.savedInstanceState=savedInstanceState;
        //  自动调节键盘不会将输入框进行遮挡;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //  初始化相应的控件;
        initView(view);
        //  初始化相应的参数;
        initConfig();

        return view;
    }
    //  初试化控件;
    private void initView(View view){
        btnUp       =   view.findViewById(R.id.btnUp);
        btnNext     =   view.findViewById(R.id.btnNext);
        btnFirst    =   view.findViewById(R.id.btnFirst);
        btnExtract  =   view.findViewById(R.id.btnExtract);
        //  进行布局的框架填写;
        layContent   =   view.findViewById(R.id.layContent);
        layReason    =   view.findViewById(R.id.layReason);

    }
    //  初始化参数;
    private void initConfig(){
        //  上下文的内容获取;
        context         =   getContext();
        fManager        =   getActivity().getSupportFragmentManager();
        ftransaction    =   fManager.beginTransaction();
        //  显示相应的内容;
        lvReasons       =   view.findViewById(R.id.list3);
        listReasons     =   WelActivity.listReasons;
        //  进行控件的设置;
//        layView         =   new LayView();
        mContentLayout  =   new MFlowLayout(getActivity(),"X");
        mReasonLayout   =   new MFlowLayout(getActivity(),"X");

    }

    //  进行图像的实时处理;
    @Override
    public void onResume() {
        super.onResume();
        setListener();
    }

    //  进行相应的事件监听;
    private void setListener(){
        //  修改按钮;
        btnExtract.setText("修\r\r改");
        CaseActivity.tvPage.setText("第3页");
        //  数据内容的初始化信息;
        myAdapter=new MyAdapter(context);
        lvReasons.setAdapter(myAdapter);


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
                ftransaction.replace(R.id.container1, WelActivity.case2Fragment);
                ftransaction.addToBackStack("case2");
                ftransaction.commitAllowingStateLoss();
            }
        });
        //  下一个的按钮;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Fragment点击下一页的按钮的事件触发的内容;
                //  申请内容的框架内容;
                ArrayList<MFlowLayout>  listContents=mContentLayout.getListFlows();
                Log.i("MyLog","用户点击了相应内容");
                String content = "";
                String reason  = "";
                for(MFlowLayout item:listContents){
                    String cont=item.getContent();
                    View view=item.getView();
                    String txt="";
                    if(cont!=null){
                        TextView tv= (TextView) view;
                        txt        = tv.getText().toString();
                    }else{
                        EditText et= (EditText) view;
                        txt        = et.getText().toString();
                    }
                    content+=txt;
                }

                //  申请的理由的框架内容;
                ArrayList<MFlowLayout>  listReasons=mReasonLayout.getListFlows();
                for(MFlowLayout item:listReasons){
                    String cont=item.getContent();
                    View view=item.getView();
                    String txt="";
                    if(cont!=null){
                        TextView tv= (TextView) view;
                        txt        = tv.getText().toString();
                    }else{
                        EditText et= (EditText) view;
                        txt        = et.getText().toString();
                        //  当内容含有空格的时候，标志位标写为"null";
                        if(txt.equals(""))
                            txt="null";
                    }
                    reason+=txt;
                }

                if(!content.equals("")&&!reason.equals("")){
                    if(!content.contains("null")&&!reason.contains("null")){
                        //  将相应的理由内容信息进行填入;
                        CaseActivity.submitperson.setSubmitcontent(content);
                        //  将相应的理由原因信息写入;
                        CaseActivity.submitperson.setSubmitreason(reason);
                        //  从左向右进行页面的平移
                        ftransaction.setCustomAnimations(R.anim.cu_push_down_in,R.anim.cu_push_up_out);
                        ftransaction.replace(R.id.container1, WelActivity.case4Fragment);
                        ftransaction.addToBackStack("case4");
                        ftransaction.commitAllowingStateLoss();
                    }else{
                        Toast.makeText(getActivity(),"请填写相应信息",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"请选择相应内容",Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    class MyAdapter extends BaseAdapter {
        private Context context;
        private int size;

        public MyAdapter(Context context) {
            this.context=context;
            this.size=listReasons.size();
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public Object getItem(int position) {
            return listReasons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater mInflater=LayoutInflater.from(this.context);
                convertView = mInflater.inflate(R.layout.act_item_3,null); //加载布局
                holder = new ViewHolder();
                //  Checkbox单选框;
                holder.cb        = (CheckBox) convertView.findViewById(R.id.cb);
                //  内容的相应内容;
                holder.tvContent = (TextView) convertView.findViewById(R.id.content);
                //  进行标记;
                convertView.setTag(holder);

            } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
               holder = (ViewHolder) convertView.getTag();
            }
            //  进行处理的单个对象;
            Reason reason = listReasons.get(position);
            //  对于单选复选框的相应的点选;
            holder.cb.setText((position+1)+"."+reason.getTitle());
            //  对应注释内容的填写;
            holder.tvContent.setText("\t\t"+reason.getContent());
            if(reason.isChecked())
                holder.cb.setChecked(true);
            else
                holder.cb.setChecked(false);

            holder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  原来点选;
                    if(listReasons.get(position).isChecked()){
                        listReasons.get(position).setChecked(false);
                    }else{
                        for(int i=0;i<size;i++){
                            if(i!=position){
                                listReasons.get(i).setChecked(false);
                            }else{
                                //  清空相应的操作;
                                mContentLayout.getListFlows().clear();
                                mReasonLayout.getListFlows().clear();
                                listReasons.get(position).setChecked(true);
                                //  进行相应的事件触发;
                                Message message =   mHandler.obtainMessage(1);
                                message.obj     =   position;
                                mHandler.sendMessage(message);
                            }
                        }
                    }
                    notifyDataSetChanged();

                }
            });

            return convertView;
        }

//        这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
         CheckBox cb;
         TextView tvContent;
     }
   }
}
