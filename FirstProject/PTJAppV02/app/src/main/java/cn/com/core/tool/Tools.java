package cn.com.core.tool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

//  工具的接口内容;
public interface Tools {
    //  获得HttpUrl的方法;
    public String getHttpUrl(String program,String parameter);
    //  Http-Post方法;
    public String httpPostResponse(String httpUrl,String path);

    //  从相册中获图片路径;
    public String getPathFromPhotoSet(Activity activity, Intent data);
    //  从相册中获图片内容;
    public Bitmap getBitmapFormPhotoSet(Activity activity, Intent data);
    //  对图片进行圆角处理;
    public RoundedBitmapDrawable getBitmapByDealWith(Activity activity, Bitmap bitmap, float f);
    //  进行相应的网络的监测处理;
    public boolean isNetWorkEnvironment(Activity context);
    //  进行wifi的测试;
    public boolean isWifiEnable(Activity context);
    // 进行相应的加载框的对象;
    public ProgressDialog progressDialogLoading(Activity context, String title, String message);

    //  将Base64的字符串转换为图片;
    public Bitmap base64ToBitmap(String base64Data);
    //  将Bitmap转换为字符串;
    public String BitmapToString(Bitmap bitmap);
    //  控件是否隐藏;
    public boolean isViewCover(View view);

    //  首选项内容-获得参数;
    public Object getSParameter(Context context, String parents, String key);
    //  首选项内容-设置参数;
    public boolean setSParameter(Context context,String parents,String key, String value);

    //  文件的监测方法;
    public boolean checkFile(String path);
    //  将Base64的内容进行保存;
    public void base64StringToFile(String path,String content);
    //  将Base64的图片进行保存;
    public boolean base64ImgToFile(String path,String imgStr);
    //  图像的调整;
    public Bitmap imageScale(Bitmap bitmap,int dst_w,int dst_h);
    //  进行图片的保存;
    public String saveBitmap(Bitmap bitmap);
    //  进行数据的处理;
    public String getStringUTF8(String str);
    //  获得时间;
    abstract String getData(String str);
    //  网络信息的初始化;
    abstract void checkInitNetInfo();
    //  检测下载的文件内容是否为空;
    abstract boolean checkFiledownload();
    //  检测android显示栏目的上边框的隐藏;
    abstract void setHiddenTitle(AppCompatActivity context, String color);
    //  存储权限的内容;
    abstract void checkStoreRight(Activity context);

}
