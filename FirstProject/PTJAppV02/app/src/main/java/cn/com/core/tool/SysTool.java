package cn.com.core.tool;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import cn.com.core.R;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

public class SysTool implements Tools {
    //  系统的状态;
    public static int PROGRAM_STORAGE_PERMISSION = -1;
    public static final int EXTERNAL_STORAGE     = 1;
    //  Intent设置的跳转标签;
    //  系统调用相册;
    public static final int REQUEST_PHOTO_SET    = 2;
    //  系统调用相机;
    public static final int REQUEST_PHOTO_TAKE   = 3;
    //  身份证正面;
    public static final int REQUEST_IDCARD_FONT  = 4;
    //  身份证反面;
    public static final int REQUEST_IDCARD_BACK  = 5;
    //  证照件摄像;
    public static final int REQUEST_PROOF_TAKE   = 6;
    //  照相沉睡设置;
    public static final String COLOR    =   "#B4CDCD";
    public static final String COLOR2   =   "#483d8b";
    public static final int    TIME_STEP=   3000;

    //  参数的配置
    private static final int    TIME_OUT = 30* 1000;// 超时时间
    private final String CHARSET         = "utf-8";// 设置编码
    private final String httpProject     = "TestJ2ee";

    //  网络请求;
    public static final String ERROR_1   = "error1";
    public static final String ERROR_IO  = "error2";
    public static final String TEST_IO   = "test";
    //  系统文件夹;
    public static final String SYS_PATH   = Environment.getExternalStorageDirectory().getAbsoluteFile()+"";
    public static final String PATH_DOWN  = SYS_PATH+File.separator+"PTJ"+File.separator+"download";
    public static final String PATH_PHOTO = SYS_PATH+File.separator+"PTJ"+File.separator+"photo";

    //  进行数据传送的发包字符串;
    public static final String BROADCAST_MESSAGE="com.cn.c";

    public static String PHOTO_PATH_TEMP  = "";
    public static float fSize             = 0.5f;
    private Context context;

    public SysTool() {

    }

    public SysTool(Context context) {
        this.context = context;
    }

    // 进行字符串的组装;
    public String getHttpUrl(String program,String parameter){
        String httpAddressId =  getSParameter(this.context,"net_set","ip").toString();
        String httpPort      =  getSParameter(this.context,"net_set","port").toString();
        String head          =  "http://"+httpAddressId+":"+httpPort+"/"+httpProject;
//        Log.i("MyLog","head>>"+head);
        if(parameter==null) {
            return head+"/"+program;
        }
        return head+"/"+program+"?"+parameter;
    }
    public String getPathFromPhotoSet(Activity activity,Intent data){

        ContentResolver resolver     = activity.getContentResolver();
        if(data!=null){
            Uri             originalUri  = data.getData();
            String[]        proj         = {MediaStore.Images.Media.DATA};
            Cursor          cursor       = resolver.query(originalUri, proj, null, null, null);
            int             column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagepath= cursor.getString(column_index);
            if (!cursor.isClosed()) {
                cursor.close();
            }
            return imagepath;
        }
        return null;
    }

    //  相册选择结果内容;
    public Bitmap getBitmapFormPhotoSet(Activity activity, Intent data){
        Bitmap          bitmap       = null;
        FileInputStream fis          = null;

        String          imgPath      = getPathFromPhotoSet(activity,data);

        //  图上的容量载体;
        byte[]          buf          = new byte[1024 * 1024];// 1M

        try {
            fis                      = new FileInputStream(imgPath);
//            Log.i("MyLog",fis.toString());
            int         len          = fis.read(buf, 0, buf.length);
            bitmap                   = BitmapFactory.decodeByteArray(buf, 0, len);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public Bitmap getBitmapFormPath(String imgPath){
        Bitmap          bitmap       = null;
        FileInputStream fis          = null;
        //  图上的容量载体;
        byte[]          buf          = new byte[1024 * 1024];// 1M

        try {
            fis                      = new FileInputStream(imgPath);
//            Log.i("MyLog",fis.toString());
            int         len          = fis.read(buf, 0, buf.length);
            bitmap                   = BitmapFactory.decodeByteArray(buf, 0, len);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    //  对图像进行圆角的处理;
    public RoundedBitmapDrawable getBitmapByDealWith(Activity activity,Bitmap bitmap,float f){
        RoundedBitmapDrawable dbDrawable=null;
        try {
            if (bitmap!=null){
                dbDrawable = RoundedBitmapDrawableFactory.create(activity.getResources(),bitmap);//传入bitmap
                //设置圆角角度
                dbDrawable.setCornerRadius(f);
            }

        }catch (Exception e){
            return null;
        }
        return dbDrawable;
    }
    //  进行相应的网络的监测处理;
    public boolean isNetWorkEnvironment(Activity context) {
        //  监测是不是可以运行;
        if (context != null) {
            //  网络连接的帮助类;
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo         info    = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }
    //  进行wifi的测试;
    public boolean isWifiEnable(Activity context){

        //  监测是否可以运行;
        if(context!=null){
            WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            //如果WiFi开启就关闭并弹出Toast提示已关闭
            if(manager.isWifiEnabled())
                return true;
            else
                return false;
        }
        return false;
    }

    // 进行相应的加载框的对象;
    public ProgressDialog progressDialogLoading(Activity context, String title, String message){
        //实例化
        ProgressDialog mypDialog=new ProgressDialog(context);
        //设置进度条风格，风格为圆形，旋转的
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置ProgressDialog 标题
        mypDialog.setTitle(title);
        //设置ProgressDialog 提示信息
        mypDialog.setMessage(message);
        //设置ProgressDialog 提示图标;
        mypDialog.setIcon(R.mipmap.icon);
        //设置ProgressDialog 的进度条是否不明确
        mypDialog.setIndeterminate(false);
        mypDialog.setCancelable(true);
        return mypDialog;
    }
    //  网络处理的方法;
    //  Post数据的上传;
    public String httpPostResponse(String httpUrl,String path){
        //  结果字符串;
        String  result       = null;
        //  URL的连接操作;
        HttpURLConnection conn=null;

        // 边界标识 随机生成
        String  BOUNDARY     = UUID.randomUUID().toString();
        String  PREFIX       = "--",
                LINE_END     = "\r\n";
        // 内容类型
        String  CONTENT_TYPE = "multipart/form-data";
        // 如果请求连接为空则清空;
        if(httpUrl==null)
            return null;

        try{

            URL url =   new URL(httpUrl);
            conn    =  (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);

            // 允许输入流
            conn.setDoInput(true);
            // 允许输出流
            conn.setDoOutput(true);
            // 不允许使用缓存
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");// 请求方式
            conn.setRequestProperty("Charset", CHARSET);// 设置编码
            conn.setRequestProperty("connection","keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE +";boundary=" + BOUNDARY);

            //  文件信息的监测;
            File file   =   null;
            try{
                file    =   new File(path);
            }catch(Exception e){
                file    =   null;
            }

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            if(file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                StringBuffer     sb  = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                        + file.getName() +"\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+ CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is =new FileInputStream(file);
                byte[] bytes =new byte[1024];
                int len = 0;
                while((len = is.read(bytes)) != -1) {
                    dos.write(bytes,0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);

            }
            dos.flush();
            //  关闭dos;
            dos.close();
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            InputStream input = conn.getInputStream();
            StringBuffer sb1 =new StringBuffer();
            int ss;
            while((ss = input.read()) != -1) {
                sb1.append((char) ss);
            }
            result = sb1.toString();

            //  关闭网络链接;
            if (input!=null){
                input.close();
            }

        }catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("MyLog","数据1");
            return ERROR_1;
        }catch (IOException e) {
            e.printStackTrace();
            Log.i("MyLog","数据IO出错");
            return ERROR_IO;
        }finally {
            //  断开网络连接;
            conn.disconnect();
        }
        return result;
    }


    //  Post数据的上传多个文件;
    public String httpPostResponse2(String httpUrl,ArrayList<String> paths){
        //  结果字符串;
        String  result       = null;
        //  URL的连接操作;
        HttpURLConnection conn=null;

        // 边界标识 随机生成
        String  BOUNDARY     = UUID.randomUUID().toString();
        String  PREFIX       = "--",
                LINE_END     = "\r\n";
        // 内容类型
        String  CONTENT_TYPE = "multipart/form-data";
        // 如果请求连接为空则清空;
        if(httpUrl==null)
            return null;

        try{

            URL url =   new URL(httpUrl);
            conn    =  (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);

            // 允许输入流
            conn.setDoInput(true);
            // 允许输出流
            conn.setDoOutput(true);
            // 不允许使用缓存
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");// 请求方式
            conn.setRequestProperty("Charset", CHARSET);// 设置编码
            conn.setRequestProperty("connection","keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE +";boundary=" + BOUNDARY);

            OutputStream     outputSteam=   conn.getOutputStream();
            DataOutputStream dos        =   new DataOutputStream(outputSteam);
            //  共用部分;
            if(paths!=null){
                //  进行多文件的上传;
                for(String path:paths){
                    //  单条文件路径;
                    File file=new File(path);
                    /**
                     * 当文件不为空，把文件包装并且上传
                     */
                    StringBuffer     sb  = new StringBuffer();
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    /**
                     * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                     * filename是文件的名字，包含后缀名的 比如:abc.png
                     */
                    sb.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                            + file.getName() +"\"" + LINE_END);
                    sb.append("Content-Type: application/octet-stream; charset="+ CHARSET + LINE_END);
                    sb.append(LINE_END);
                    dos.write(sb.toString().getBytes());
                    InputStream is =new FileInputStream(file);
                    byte[] bytes =new byte[1024];
                    int len = 0;
                    while((len = is.read(bytes)) != -1) {
                        dos.write(bytes,0, len);
                    }
                    is.close();
                    dos.write(LINE_END.getBytes());
                }

                //  公用的部分;
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);

            }
            dos.flush();
            //  关闭dos;
            dos.close();
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            InputStream input = conn.getInputStream();
            StringBuffer sb1 =new StringBuffer();
            int ss;
            while((ss = input.read()) != -1) {
                sb1.append((char) ss);
            }
            result = sb1.toString();

            //  关闭网络链接;
            if (input!=null){
                input.close();
            }

        }catch (MalformedURLException e) {
            e.printStackTrace();
            Log.i("MyLog","数据1");
            return ERROR_1;
        }catch (IOException e) {
            e.printStackTrace();
            Log.i("MyLog","数据IO出错");
            return ERROR_IO;
        }finally {
            //  断开网络连接;
            conn.disconnect();
        }
        return result;
    }

    //  将Base64的字符串转换为图片;
    public Bitmap base64ToBitmap(String base64Data){
        //base64转为bitmap
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    //  将Base64图片转化为jpg;
    public boolean base64ImgToFile(String path,String imgStr) {
        //  对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        //  文件的更新模块;
        File file=new File(path);
        if(file.exists())
            file.delete();

        try {
            //  Base64解码
            byte[] b = Base64.decode(imgStr, Base64.DEFAULT);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
//            Log.i("MyLog","图片已经修改完毕");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix,true);
        return dstbmp;
    }

    @Override
    public String saveBitmap(Bitmap bitmap) {
        Log.e("MyLog", "保存图片");
        String path=PATH_PHOTO+File.separator+System.currentTimeMillis()+".jpg";
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.i("MyLog", "已经保存");
            return path;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getStringUTF8(String str) {
        String result="";
        try {
            result=new String(str.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getData(String str) {
        long l=Long.parseLong(str);
        Date date=new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    //  将Bitmap转换为字符串;
    public String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }
    //  判断控件是否隐藏;
    public boolean isViewCover(View view) {
        boolean cover = false;
        Rect    rect  = new Rect();

        if(view!=null){
            cover         = view.getGlobalVisibleRect(rect);
        }
        return cover;
    }
    //  创建一个SharedPreferences类似于创建一个数据库，库名为 data
    public SharedPreferences share(Context context,String parents){
        SharedPreferences sharedPreferences = context.getSharedPreferences(parents, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
    //  首选项的数据的获取方式;
    public Object getSParameter(Context context,String parents,String key){
        return share(context,parents).getString(key,null);
    }
    //  首选项的数据的设置方式;
    public boolean setSParameter(Context context,String parents,String key, String value){
        SharedPreferences.Editor e = share(context,parents).edit();
        e.putString(key,value);
        Boolean bool = e.commit();
        return bool;
    }
    //  对指定的文件夹进行检测的方法;
    @Override
    public boolean checkFile(String path) {
        //  文件的初始化;
        File file=new File(path);
        //  进行文件监测;
        if(!file.exists())
            file.mkdirs();
        return false;
    }
    //  将Base64的字符串转换为文件进行处理;
    @Override
    public void base64StringToFile(String path, String content) {
        FileOutputStream fos = null;
        //  文件的更新模块;
        File file=new File(path);
        if(file.exists())
            file.delete();

        try {
            content=URLDecoder.decode(content,"utf-8");
            fos=new FileOutputStream(path);
            fos.write(content.getBytes());
            fos.close();
//            Log.i("MyLog","文字已经修改完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> listImgPath;
    public ArrayList<String> listContent;

    //  将读取的图像转换为Bitmap图像;
    public void getContentFromFile() {
        checkFile(PATH_DOWN);
        //  将图像放到相应的列表中;
        listImgPath = new ArrayList<>();
        listContent = new ArrayList<>();

        File[] allFiles = new File(SysTool.PATH_DOWN).listFiles();

        //  当文件夹下边有文件时;
        if(allFiles!=null){
            for (File file : allFiles) {
                String name = file.getName();
                String path = file.getAbsolutePath();
                String content = "";

                if (name.contains(".jpg")) {
                    listImgPath.add(path);
                } else {
                    InputStream instream = null;
                    try {
                        instream = new FileInputStream(file);
                        if (instream != null) {
                            InputStreamReader inputreader = new InputStreamReader(instream);
                            BufferedReader buffreader = new BufferedReader(inputreader);

                            String line = "";
                            while ((line = buffreader.readLine()) != null) {
                                content += line + "\n";
                            }
                        }
    //                    Log.i("MyLog","文件内容:"+content);
                        //  添加到列表中;
                        listContent.add(content);
                        instream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //  检测网络信息的初始化内容;
    @Override
    public void checkInitNetInfo() {
        //  服务器ip地址;
        Object ip=getSParameter(context,"net_set","ip");
        //  服务端口信息;
        Object port=getSParameter(context,"net_set","port");
        if(ip==null){
            setSParameter(context,"net_set","ip","192.168.99.147");
        }
        if(port==null){
            setSParameter(context,"net_set","port","8080");
        }

    }

    @Override
    public boolean checkFiledownload() {
        return false;
    }

    @Override
    public void setHiddenTitle(AppCompatActivity context,String color) {
        //  去除页面顶部的颜色;
        if (context.getSupportActionBar() != null){
            context.getSupportActionBar().hide();
        }
        //  因为不是所有的系统都可以设置颜色的，在4.4以下就不可以。。有的说4.1，所以在设置的时候要检查一下系统版本是否是4.1以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = context.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setHiddenTitle(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    @Override
    public void checkStoreRight(Activity context) {
        //  手机的权限;
        SysTool.PROGRAM_STORAGE_PERMISSION = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //  目标的权限;
        int target     = PackageManager.PERMISSION_GRANTED;
        Log.i("MyLog","存储权限="+SysTool.PROGRAM_STORAGE_PERMISSION+"|系统包的内容="+target);
        if (SysTool.PROGRAM_STORAGE_PERMISSION != target) {
            // We don't have permission so prompt the u
            ActivityCompat.requestPermissions(context, PERMISSIONS_STORAGE,SysTool.EXTERNAL_STORAGE);
        }
        Log.i("MyLog","存储权限="+SysTool.PROGRAM_STORAGE_PERMISSION+"|系统包的内容="+target);
    }
}