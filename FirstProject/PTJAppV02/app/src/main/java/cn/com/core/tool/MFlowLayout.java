package cn.com.core.tool;

import android.app.Activity;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MFlowLayout {
    //  进行相应的属性内容;
    private Activity activity;

    ////进行数据分析的相应内容;
    private ArrayList<MFlowLayout> listFlows;
    //  进行数据的内容;
    private String content;
    //  长度内容;
    private int     width;
    //  控件的内容;
    private View    view;
    //  判断符;
    private String  TAG;
    //  纠错位数;
    private int     offset;

    //  含有参数的构造函数;
    public MFlowLayout(Activity activity,String TAG) {
        //  上下文的主要内容;
        this.activity= activity;
        //  承装数据的载体;
        listFlows    = new ArrayList<>();
        //  进行分割的标签;
        this.TAG     = TAG;
        //  外层框架的处理;
        this.offset  = 20;

    }
    //  含有参数的构造函数;
    public MFlowLayout(String content, int width,View view) {
        this.content = content;
        this.width   = width;
        this.view    = view;
    }
    //  属性的相应的内容——得到;
    public ArrayList<MFlowLayout> getListFlows() {
        return listFlows;
    }

    public String getContent() {
        return content;
    }

    public int getWidth() {
        return width;
    }

    public View getView() {
        return view;
    }

    //  添加线性控件的内容;
    public void addLinearLayoutView(String line){
        //  获得相应各个字节的内容;
        Log.i("MyLog","获取的数据信息="+line);
        //  字符串的总个数;
        int numTotal        =   line.length();
        Log.i("MyLog","字符串的总个数="+numTotal);
        //  获得
        TextView  tv        =   new TextView(this.activity);
        //  字符串的内容设置;
        tv.setText(line);
        TextPaint textPaint = tv.getPaint();
        //  获得有字控件的总长度;
        int widthTotal      = (int) textPaint.measureText(tv.getText().toString());
        Log.i("MyLog","字符串的总长度="+widthTotal);
        //  获得单个字的长度;
        int width           = widthTotal/numTotal;
        Log.i("MyLog","单个字的分长度="+width);
        //  获得所有的单个的字;
        char[]  chs         = line.toCharArray();
        //  将控件里的数据信息进行相应插入;
        MFlowLayout mFlowLayout=null;
        for(char c:chs){
            String str      = String.valueOf(c);
            //  根据字符串添加相应的控件信息;
//            Log.i("MyLog","内容包括:"+str);
            //  当输入部分为文字部分;
            if(!str.equals(this.TAG)){
                //  进行新对象的声明——声明对象为显示框;
                mFlowLayout=new MFlowLayout(str,width,new TextView(activity));
            }
            //  当输入部分应该为输入框;
            else {
                //  进行新对象的声明——声明对象为显示框;
                mFlowLayout=new MFlowLayout(null,width,new EditText(activity));
            }
            //  将所有的控件形成对象插入;
            listFlows.add(mFlowLayout);
        }
    }

    //  放入到相应的列表中;
    public void getLinearLayoutView(LinearLayout parent){
        //  设置父亲的方向;
        parent.setOrientation(LinearLayout.VERTICAL);
        //  进行相应的父亲控件的测试;
        int            threshold = parent.getWidth()-this.offset;
//        Log.i("MyLog","外围的控件临界="+threshold);
        //  获得控件的列表;
        int            max   = listFlows.size();
//        Log.i("MyLog","控件的长度="+max);

        //  初始化的步长信息;
        int            step   = 0;
        LinearLayout line=new LinearLayout(this.activity);
        line.setOrientation(LinearLayout.HORIZONTAL);
        int            index  = 0;
        //  进行逐个内容的寻迹;
        while (index<max){
            //  单个对象的处理;
            MFlowLayout item=listFlows.get(index);
            //  内容的相应信息;
            //  控件内容;
            String   content=item.getContent();
            //  控件长度;
            int      width  =item.getWidth();
            //  控件本身;
            View     view   =item.getView();

            //  对控件进行添加;
            if(content!=null){
                TextView tv = (TextView) view;
                tv.setText(content);
                line.addView(tv);
                //  根据步长进行检测;
                step+=width;
            }else{
                EditText et= (EditText) view;
                //  对输入的数字个数进行限制;
                et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(4*width, LinearLayout.LayoutParams.WRAP_CONTENT);
                et.setText(content);
                et.setLayoutParams(params);
//                et.setGravity(Grav);
                line.addView(et);
                //  根据步长进行检测;
                step+=4*width;
            }
            if(step>=threshold-width){
//                Log.i("MyLog","越界="+step);
                //  删除此个控件;
                line.removeView(view);
                //  将辞控件放入其内;
                parent.addView(line);
                index=index-1;
                step=0;

                //  设定相应的长度;
                line=new LinearLayout(this.activity);
                line.setOrientation(LinearLayout.HORIZONTAL);
            }
            index++;
        }
        //  将子控件放入到父亲目录下;
        parent.addView(line);
    }

}
