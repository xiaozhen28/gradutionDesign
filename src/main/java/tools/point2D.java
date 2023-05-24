package tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Point.java
 * @Description TODO
 * @createTime 2022年03月07日 16:50:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class point2D {
    public double x; // 横坐标
    public double y; // 纵坐标
    public double w; // 齐次规范系数
    public Color color;
    public point2D(double x, double y) {
        this.x=x;
        this.y=y;
    }
    public point2D(double x, double y, double w) {
        this.x=x;
        this.y=y;
        this.w=w;
    }

    @Override
    public String toString() {
        return "point2D{" +
                "x=" + x +
                ", y=" + y +
                ", W=" + w +
                ", color=" + color +
                '}';
    }
}
