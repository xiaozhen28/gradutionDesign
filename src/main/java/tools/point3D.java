package tools;

import java.awt.*;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName point3D.java
 * @Description TODO
 * @createTime 2022年04月24日 21:45:00
 */
public class point3D {
    public double x; // 横坐标
    public double y; // 纵坐标
    public double z; // z坐标
    public double w; // 齐次规范系数
    public Color color;

    public point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public point3D(double x, double y, double z,double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w ;
    }

    public point3D() {

    }

    @Override
    public String toString() {
        return "point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                ", color=" + color +
                '}';
    }
}
