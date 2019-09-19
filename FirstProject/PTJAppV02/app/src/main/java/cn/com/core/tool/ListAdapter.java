package cn.com.core.tool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;
import java.util.ArrayList;

import cn.com.core.R;
import cn.com.core.data.model.Submitperson;

public class ListAdapter extends BaseAdapter {

    private Activity activity;
    private Context content;
    private ArrayList<MyContent>    datas;
    private ArrayList<Submitperson> lists;
    private FragmentTransaction transaction;
    private Fragment fragment;
    private int viewId;


    public ListAdapter(Context context, ArrayList<MyContent> datas,ArrayList<Submitperson> lists,FragmentTransaction transaction,Fragment fragment,int viewId) {
        this.content    = context;
        this.datas      = datas;
        this.lists      = lists;
        this.transaction= transaction;
        this.fragment   = fragment;
        this.viewId     = viewId;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null){

            convertView = LayoutInflater.from(content).inflate(R.layout.act_item_slide, null);
            viewHolder = new ViewHolder();
            viewHolder.tvNum        =   (TextView)convertView.findViewById(R.id.num);
            viewHolder.contentView  =   (TextView) convertView.findViewById(R.id.content1);
            viewHolder.menuView     =   (TextView) convertView.findViewById(R.id.menu);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvNum.setText(String.valueOf(position+1));

        viewHolder.contentView.setText(datas.get(position).getContent());

        viewHolder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  从左向右进行页面的平移
                transaction.setCustomAnimations(R.anim.cu_push_right_in,R.anim.cu_push_left_out);

                Submitperson person=lists.get(position);
                //  进行数据传递的内容;
                Bundle argz = new Bundle();
                argz.putSerializable("person", (Serializable) person);
                fragment.setArguments(argz);

                //  页面加载的部件;
                transaction.replace(viewId, fragment);
                transaction.addToBackStack("item");
                transaction.commitAllowingStateLoss();
            }
        });
        final MyContent myContent = datas.get(position);
        viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(myContent);
                notifyDataSetChanged();
            }
        });

        SlideLayout slideLayout = (SlideLayout) convertView;
        slideLayout.setOnStateChangeListener(new ListAdapter.MyOnStateChangeListener());


        return convertView;
    }

    public SlideLayout slideLayout = null;
    class MyOnStateChangeListener implements SlideLayout.OnStateChangeListener{

        @Override
        public void onOpen(SlideLayout layout) {

            slideLayout = layout;
        }

        @Override
        public void onMove(SlideLayout layout) {
            if (slideLayout != null && slideLayout !=layout){
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onClose(SlideLayout layout) {
            if (slideLayout == layout){
                slideLayout = null;
            }
        }
    }
    public class ViewHolder{
        public TextView tvNum;
        public TextView contentView;
        public TextView menuView;
    }
}
