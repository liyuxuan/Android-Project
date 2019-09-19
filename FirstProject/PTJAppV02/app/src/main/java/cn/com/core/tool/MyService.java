package cn.com.core.tool;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.com.core.ui.login.MenuActivity;

//  进行相应处理的后台服务内容;
public class MyService extends Service {

    //////////////////////////////////////////////////////////////
    ///     单次：startService()->onCreate()->onStartCommand()
    ///     多次：startService()->onCreate()->onStartCommand()->onStartCommand()
    //////////////////////////////////////////////////////////////
    private SysTool sysTool=null;
    // 创建服务:只1次;
    @Override
    public void onCreate() {
        super.onCreate();
        Activity context= MenuActivity.mContext;
        sysTool=new SysTool(context);
    }

    //  当另一个组件通过调用startService()请求启动服务时，系统将调用此方法。
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyLog","调用服务");
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    //  绑定服务;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //  解绑服务;
    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }

    //  销毁服务;
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void startDownload() {
            new Thread(new Runnable() {
                @Override
                public void run() {

                //进行数据下载的内容;
                String httpUrl  =   sysTool.getHttpUrl("CheckFile",null);
                Log.i("MyLog","http="+httpUrl);
                //  结果反馈集合;
                String result   =   sysTool.httpPostResponse(httpUrl,null);
                Log.i("MyLog","res="+result.length());
                try {
                    JSONArray jsonArray=new JSONArray(result);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonbject=jsonArray.getJSONObject(i);

                        String      filename=jsonbject.getString("filename");
                        String      file    =jsonbject.getString("file");
                        String      path    =SysTool.PATH_DOWN+ File.separator+filename;
                        //  数据的存储路径;
                        if (filename.contains(".jpg"))
                            sysTool.base64ImgToFile(path,file);
                        else{
                            sysTool.base64StringToFile(path,file);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent();
                intent.setAction(SysTool.BROADCAST_MESSAGE);
                intent.putExtra("id","no");
                sendBroadcast(intent);
                }
            }).start();
        }

}
