package cn.com.core.data.model;

/**
 * Created by lijilong on 2018/2/23.
 */

public class PointTrackEntity {

    //轨迹的X坐标
    private float pointX;
    //轨迹的Y坐标
    private float pointY;

    //记录轨迹状态 是否是新的开始
    private int pointFlag;

    public float getPointX() {
        return pointX;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }

    public int getPointFlag() {
        return pointFlag;
    }

    public void setPointFlag(int pointFlag) {
        this.pointFlag = pointFlag;
    }
}
