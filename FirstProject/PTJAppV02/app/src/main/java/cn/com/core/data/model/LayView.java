package cn.com.core.data.model;

import android.app.Activity;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LayView {
    private View view;
    private int width;
    private int widthAll;
    //  承装列表;
    private ArrayList<LayView> listView;

    //  构造函数——不含参数;
    public LayView() {
        // 创建相应的控件列表;
        listView = new ArrayList<>();
    }

    //  构造函数——含有参数;
    public LayView(View view,int width) {
        this.view  = view;
        this.width = width;
    }
    //  进行相应的对象列表;
    public void addView(View view,String kind){
        //  进行相应的对象声明;
        int width=0;
        TextView v= (TextView) view;
        TextPaint textPaint = v.getPaint();
        if(kind!=null){
            if(!kind.equals("et")){
                //  进行内容的设置;
                v.setText(kind);
            }else{
                //  进行内容的设置;
                v.setText("    ");
            }
            width= (int) textPaint.measureText(v.getText().toString());
        }
        //  进行对象的声明;创建新的对象;
        LayView layView=new LayView(view,width);
        listView.add(layView);
    }
    //  获得相应的控件列表;
    public ArrayList<LayView> getListView() {
        return listView;
    }

    public View getView() {
        return view;
    }

    public int getWidth() {
        return width;
    }

    //  放入到相应的列表中;
    public void setLayout(LinearLayout parent, Activity activity){
        //  设置父亲的方向;
        parent.setOrientation(LinearLayout.VERTICAL);
        //  进行相应的父亲控件的测试;
        int            pwidth = parent.getWidth();
        Log.i("MyLog","相应的长度信息="+pwidth);
        //  获得已经的列表的内容;
        int            size   = listView.size();
//        Log.i("MyLog","控件的长度="+size);
        //  初始化的步长信息;
        int            step   = 0;
        LinearLayout line=new LinearLayout(activity);
        line.setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0;i<size;i++){
            LayView layView=listView.get(i);
            View view=layView.getView();
            int width=layView.getWidth();
            line.addView(view);
            step+=width;
            Log.i("MyLog","累计长度="+step);
            if(step>=pwidth-40){
                parent.addView(line);
                step=0;
                //  设定相应的长度;
                line=new LinearLayout(activity);
                line.setOrientation(LinearLayout.HORIZONTAL);
            }
        }
        //  将子控件放入到父亲目录下;
        parent.addView(line);
    }

}
