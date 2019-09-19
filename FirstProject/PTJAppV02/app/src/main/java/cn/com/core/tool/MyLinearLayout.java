package cn.com.core.tool;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class MyLinearLayout extends ViewGroup {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * getMeasuredWidth()与getWidth;
     * getMeasuredWidth在onMeasure结束后获取到。但getWidth在onLayout获取到
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight=MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode=MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode=MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth=0;//记录每一行宽累加
        int lineHeight=0;//记录每一行高
        int height=0;//记录父布局占的高累加
        int widht=0;//父布局所占的宽
        int count=getChildCount();
        for (int i=0;i<count;i++){
            View child=getChildAt(i);
            //////////
            measureChild(child,widthMeasureSpec,heightMeasureSpec);//测量子的布局
            //获取边距
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            /**
             * 累计宽度。如果累计的宽度加上child的宽度大于父布局的宽度就换行
             */
            if (lineWidth+childWidth>measureWidth){
                //换行
                widht=Math.max(lineWidth,widht);
                //父布局累加高计算总高度
                height+=lineHeight;
                //换行添加一行高度
                lineHeight=childHeight;
                //重新设置linewidth当前的宽度
                lineWidth=childWidth;

            }
            else {
                //计算当前行的最大高度
                lineHeight=Math.max(lineHeight,childHeight);
                lineWidth+=childWidth;
            }

            //如果没有这行最后一个不会显示 最后一个肯定不可能超过一行
            if (i==count-1){
                height+=lineHeight;
                widht=Math.max(widht,lineWidth);
            }
        }

        setMeasuredDimension((measureWidthMode==MeasureSpec.EXACTLY)?measureWidth:widht,(measureHeightMode==MeasureSpec.EXACTLY)?measureHeight:height);
    }



    /**
     * 如果想要获取边距使用MarginLayoutParams
     * @return
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return  new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 从指定的xml获取宽高的值
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {

        return new MarginLayoutParams(lp);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count=getChildCount();
        int lineWidth=0;//累计行宽
        int lineHeight=0;//行高
        int top=0,left=0;//记录top  和 left  的坐标

        for (int i=0;i<count;i++){
            View child=getChildAt(i);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+lp.rightMargin+lp.leftMargin;
            int childHeight=child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;

            if (childWidth+lineWidth>getMeasuredWidth()){
                //换行
                top+=lineHeight;
                left=0;
                //初始化
                lineHeight=childHeight;
                lineWidth=childWidth;
            }
            else {
                lineHeight=Math.max(lineHeight,childHeight);
                lineWidth+=childWidth;
            }

            //计算childview 的坐标位置  需要非常注意的是margin不是padding，margin的距离是不绘制的控件内部的，而是控件间的间隔
            int lc = left + lp.leftMargin;//左坐标+左边距是控件的开始位置
            int tc = top + lp.topMargin;//同样，顶坐标加顶边距
            int rc = lc+child.getMeasuredWidth();
            int bc = tc+child.getMeasuredHeight();
            child.layout(lc,tc,rc,bc);//将获取到的坐标进行绘制
            //设置下个子View的起点
            left  += childWidth;

        }
    }
}
