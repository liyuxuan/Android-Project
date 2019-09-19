package cn.com.core.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.IOException;

import cn.com.core.R;
import cn.com.core.tool.SysTool;
import cn.com.core.ui.login.CaseActivity;
import cn.com.core.ui.login.WelActivity;

public class Case5Fragment extends BaseFragment {
    private View view;
    private TextView btnUp,btnNext,btnFirst,btnPhoto,btnExtract;
    private Context context;
    private Bundle savedInstanceState;
    private FragmentManager     fManager;
    private FragmentTransaction ftransaction;
    private ImageView ivView;
    //  系统的方法;
    private SysTool   sysTool;
    private String    imgpath="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_case_5, null, false);
        this.savedInstanceState=savedInstanceState;
        //  初始化相应的控件;
        initView(view);
        //  初始化相应的参数;
        initConfig();
        //  设置监听器;
        setListener();
        return view;
    }
    //  初试化控件;
    private void initView(View view){
        btnUp       = view.findViewById(R.id.btnUp);
        btnNext     = view.findViewById(R.id.btnNext);
        btnFirst    = view.findViewById(R.id.btnFirst);
        btnPhoto    = view.findViewById(R.id.btnPhoto);
        ivView      = view.findViewById(R.id.proof);
        btnExtract  = view.findViewById(R.id.btnExtract);
        //  进行系统的设置;
        sysTool     = new SysTool();

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

    @Override
    public void onStart() {
        super.onStart();
        if(ivView!=null){
            //  进行图像的初始化;
            if(CaseActivity.submitperson.getProofpath()!=null&&!CaseActivity.submitperson.getProofpath().equals("")){
                Bitmap bmp  =   BitmapFactory.decodeFile(CaseActivity.submitperson.getProofpath());
                int    w_n  = (int) (SysTool.fSize*bmp.getWidth());
                int    h_n  = (int) (SysTool.fSize*bmp.getHeight());
                //  将图像进行显示;
                Bitmap mess =  sysTool.imageScale(bmp,w_n,h_n);
                ivView.setImageBitmap(mess);

                imgpath=CaseActivity.submitperson.getProofpath();
            };
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle =this.getArguments();//得到从Activity传来的数据

        if(bundle!=null){
            String status = bundle.getString("data");
            if(status.equals("OK")){
                //  将路径发送给前端的相框;
                imgpath     =   SysTool.PHOTO_PATH_TEMP;

                Bitmap bmp  =   BitmapFactory.decodeFile(imgpath);
                int    w_n  = (int) (SysTool.fSize*bmp.getWidth());
                int    h_n  = (int) (SysTool.fSize*bmp.getHeight());
                //  将图像进行显示;
                Bitmap mess =  sysTool.imageScale(bmp,w_n,h_n);

                ivView.setImageBitmap(mess);
                //  传输句柄的清空内容;
                bundle.clear();
            }
        }
    }

    //  进行相应的事件监听;
    private void setListener(){
        CaseActivity.tvPage.setText("第5页");
        //  辅助功能项进行设置为空;
        btnExtract.setVisibility(View.GONE);

        //  点击拍照按钮;
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  动态监测当前的拍摄权限;
                if (ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CAMERA},1);}
                else{
                    //  监测进行拍照文件的设置;
                    sysTool.checkFile(SysTool.PATH_PHOTO);
                    //  对拍摄的照片进行提交;
                    String  photopath = SysTool.PATH_PHOTO+ File.separator+System.currentTimeMillis()+".jpg";

                    File    newfile   = new File(photopath);
                    try {
                        newfile.createNewFile();
                    } catch (IOException e) {

                    }

                    //  临时的图片路径;
                    SysTool.PHOTO_PATH_TEMP = photopath;

                    Intent picture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //  进行FileProvider的图片共享;
                    Uri photoUri = FileProvider.getUriForFile(
                            getActivity(),
                            getActivity().getPackageName() + ".fileprovider",newfile);
                    //  将结果集合发送给相应的数据容器中;
                    picture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    //  带有锚的Intent;
                    getActivity().startActivityForResult(picture, SysTool.REQUEST_PROOF_TAKE);
                }

            }
        });

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
                ftransaction.replace(R.id.container1, WelActivity.case4Fragment);
                ftransaction.addToBackStack("case4");
                ftransaction.commitAllowingStateLoss();
            }
        });
        //  下一个的按钮;
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  将相应的图片进行赋值;
                CaseActivity.submitperson.setProofpath(imgpath);
                //  从左向右进行页面的平移
                ftransaction.setCustomAnimations(R.anim.cu_push_down_in,R.anim.cu_push_up_out);
                ftransaction.replace(R.id.container1, WelActivity.case6Fragment);
                ftransaction.addToBackStack("case6");
                ftransaction.commitAllowingStateLoss();

            }
        });
    }

}
