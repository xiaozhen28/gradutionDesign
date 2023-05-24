package tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;


import static java.lang.Thread.sleep;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Conmen.java
 * @Description TODO
 * @createTime 2022年03月07日 16:43:00
 */
public class Commen {

    public TextArea textArea;

    //画笔尺寸和颜色
    GraphicsContext g;
    public Color color;
    public int size;
    public int speed;//速度

    // 画布尺寸
    int Width=460;
    int Length=263;

    public Commen(GraphicsContext g, TextArea textArea, int speed) {
        this.textArea = textArea;
        this.g = g;
        size=1;
        this.speed=speed;
    }
    public Commen(GraphicsContext g, TextArea textArea, int speed,Color color) {
        this.textArea = textArea;
        this.g = g;
        size=1;
        this.speed=speed;
        this.color=color;
    }

    public Commen(GraphicsContext g, TextArea textArea, int speed, int size ,Color color) {
        this.textArea = textArea;
        this.g = g;
        this.size=size;
        this.speed=speed;
        this.color=color;
    }
    // 绘制一个点的方法
    public void drawPoint(point2D point2D) {
        drawPoint(point2D.getX(), point2D.getY());
    }
    public void drawPoint(point2D point2D, Color color) {
        drawPoint(point2D.getX(), point2D.getY(),color);
    }
    public void drawPoint(double x, double y) {
        drawPoint(x, y,Color.BLACK); // 默认颜色为黑色
    }

    public void drawPoint(double x, double y,Color color) {
        x=Math.ceil(x)*size+Width;
        y=Math.ceil(y)*size+Length;
        g.setFill(color);
        g.fillRect(x,y,size,size); //用【】表示点
        sleep(20);

        x=x/size-Width;
        y=y/size-Length;
        this.textArea.appendText("("+x+","+y+")"+'\n');
        System.out.println("("+x+","+y+")");
    }

    // 速度
    public void sleep(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
