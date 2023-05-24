package arithmetic;

import tools.Commen;
import tools.point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import static java.lang.Math.abs;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Arithmetic.java
 * @Description TODO 基本绘制图形算法
 * @createTime 2022年03月07日 16:21:00
 */
public class Draw2D {
    GraphicsContext gc;
    TextArea textArea;
    public int size;
    public  int speed;


    public Commen commen; // 绘制点的函数
    point2D p0,p1;
    public Draw2D(GraphicsContext gc,TextArea textArea){
        this.gc=gc;
        this.textArea=textArea;
        commen =new Commen(this.gc,textArea,20,Color.BLACK);
    }


    public Draw2D(GraphicsContext gc,TextArea textArea,int size,Color color){
        this.gc=gc;
        this.textArea=textArea;
        this.size=size;
        commen =new Commen(this.gc,textArea,20,size,color);
    }

    /**
     * 直线的中点BreaSENHAM算法
     * @param P0 起点
     * @param P1 终点
     */
    public void BreshamsLine(point2D P0, point2D P1, Color clr)
    {
        textArea.appendText("==========开始执行直线的中点Breaseham算法绘制==========\n");
        point2D p,t;
        if( abs(P0.x-P1.x) < 1e-6)
        {
            if(P0.y>P1.y)//交换顶点,使得起始点低于终点
            {
                t=P0;P0=P1;P1=t;
            }
            for(p=P0;p.y<P1.y;p.y++)
            {
                commen.drawPoint(p,clr);
            }
        }
        else
        {
            double k,d;
            k=(P1.y-P0.y)/(P1.x-P0.x);
            if(k>1.0)//绘制k>1
            {
                if(P0.y>P1.y)
                {
                    t=P0;P0=P1;P1=t;
                }
                d=1-0.5*k;
                for(p=P0;p.y<P1.y;p.y++)
                {
                    commen.drawPoint(p,clr);
                    if(d>=0)
                    {
                        p.x++;
                        d+=1-k;
                    }
                    else
                        d+=1;
                }
            }
            if(0.0<=k && k<=1.0)//绘制0<=k<=1
            {
                if(P0.x>P1.x)
                {
                    t=P0;P0=P1;P1=t;
                }
                d=0.5-k;
                for(p=P0;p.x<P1.x;p.x++)
                {
                    commen.drawPoint(p,clr);
                    if(d<0)
                    {
                        p.y++;
                        d+=1-k;
                    }
                    else
                        d-=k;
                }
            }
            if(k>=-1.0 && k<0.0)//绘制-1<=k<0
            {
                if(P0.x>P1.x)
                {
                    t=P0;P0=P1;P1=t;
                }
                d=-0.5-k;
                for(p=P0;p.x<P1.x;p.x++)
                {
                    commen.drawPoint(p,clr);
                    if(d>0)
                    {
                        p.y--;
                        d-=1+k;
                    }
                    else
                        d-=k;
                }
            }
            if(k<-1.0)//绘制k<-1
            {
                if(P0.y<P1.y)
                {
                    t=P0;P0=P1;P1=t;
                }
                d=-1-0.5*k;
                for(p=P0;p.y>P1.y;p.y--)
                {
                    commen.drawPoint(p,clr);
                    if(d<0)
                    {
                        p.x++;
                        d-=1+k;
                    }
                    else
                        d-=1;
                }
            }
        }
        P0=P1;
    }


    /**
     * @param Midpoint 圆心
     * @throws
     * @title
     * @description
     * @author zhenjie
     * @updateTime 2022/3/7 22:21
     */
    public void CirclePoint(point2D Midpoint, double x, double y, GraphicsContext gc) throws InterruptedException//八分法画圆子函数
    {
        commen.drawPoint(Midpoint.getX() + x, Midpoint.getY() + y); //x,y
        commen.drawPoint(Midpoint.getX() + y, Midpoint.getY() + x); //y,x
        commen.drawPoint( Midpoint.getX() + y, Midpoint.getY() - x); //y,-x
        commen.drawPoint( Midpoint.getX() + x, Midpoint.getY() - y);//x,-y
        commen.drawPoint( Midpoint.getX() - x, Midpoint.getY() - y); //-x,-y
        commen.drawPoint(Midpoint.getX() - y, Midpoint.getY() - x);//-y,-x
        commen.drawPoint(Midpoint.getX() - y, Midpoint.getY() + x);//-y,x
        commen.drawPoint(Midpoint.getX() - x, Midpoint.getY() + y); //-x,y
    }

    /**
     * @param point1 圆心 point2 计算半径
     * @throws
     * @title 中点Breasham 画圆算法
     * @description
     * @author zhenjie
     * @updateTime 2022/3/7 22:32
     */
    public void MBCircle(point2D point1, point2D point2, GraphicsContext gc, TextArea textArea) throws InterruptedException//圆中点Bresenham算法
    {
        // 根据两点求出半径
        double R = Math.sqrt(abs((point1.getX() - point2.getX()) * (point1.getX() - point2.getX()) + (point1.getY() - point2.getY()) * (point1.getY() - point2.getY())));;
        double y, d, x;
        d = 1.25 - R;
        x = 0;
        y = R;
        for (x = 0; x <= y; x++) {
            this.CirclePoint(point2, x, y, gc);//调用八分法画圆子函数
            if (d < 0)
                d += 2 * x + 3;
            else {
                d += 2 * (x - y) + 5;
                y--;
            }
            textArea.appendText("(" + (int) x + "," + (int) y + ")\n");
        }
    }

    // 椭圆中点Bresenham算法
    public void MBEllipse(point2D p0, point2D p1) {
        this.p0=p0;
        this.p1=p1;
        double x, y, d1, d2, a, b;
        a = abs(p1.getX() - p0.getX()) / 2;
        b = abs(p1.getY() - p0.getY()) / 2;
        x = 0;
        y = b;
        d1 = b * b + a * a * (-b + 0.25);
        EllipsePoint(x,y);
        while (b * b * (x + 1) < a * a * (y - 0.5))//椭圆AC弧段
        {
            if (d1 < 0) {
                d1 += b * b * (2 * x + 3);
            } else {
                d1 += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
            }
            x++;
            EllipsePoint(x,y);
        }
        d2 = b * b * (x + 0.5) * (x + 0.5) + a * a * (y - 1) * (y - 1) - a * a * b * b;//椭圆CB弧段
        while (y > 0) {
            if (d2 < 0) {
                d2 += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else {
                d2 += a * a * (-2 * y + 3);
            }
            y--;
            EllipsePoint(x,y);
        }
    }

    // 四分法画椭圆子函数
    void EllipsePoint(double x, double y) {
        point2D Midpoint=new point2D((p0.x+p1.x)/2.0,(p0.y+p1.y)/2.0);        //椭圆中心坐标
        Color clr;//定义椭圆的颜色
        commen.drawPoint(Midpoint.getX() + x, Midpoint.getY() + y); //x,y
        commen.drawPoint(Midpoint.getX() -x, Midpoint.getY() + y); //y,x
        commen.drawPoint(Midpoint.getX() + x, Midpoint.getY() - y); //y,-x
        commen.drawPoint(Midpoint.getX() - x, Midpoint.getY() - y);//x,-y
        gc.moveTo(Midpoint.x,Midpoint.y);
    }


    /**y
     * @title 直线Wu反走样
     * @description P0起点 P1中点
     * @author zhenjie
     * @updateTime 2022/3/18 16:10
     */
    public  void WuLine(point2D P0, point2D P1) {
        Color wuColor=Color.color(0.2,0.4,0.7);
        int red= 255;
        int green=255;
        int blue= 255;
        Color c0,c1;
        point2D p, t;
        if (abs(P0.x - P1.x) == 0)      //绘制垂线
        {
            if (P0.y > P1.y)           //交换顶点,使得起始点低于终点顶点
            {
                t = P0;
                P0 = P1;
                P1 = t;
            }

            for (p = P0; p.y < P1.y; p.y++) {
                commen.drawPoint(p, Color.rgb(p.color.getRed()*red,p.color.getGreen()*green,p.color.getBlue()*blue));
            }
        } else {
            double k, e = 0;
            k = (P1.y - P0.y) / (P1.x - P0.x);
            if (k > 1.0)            //绘制k＞1
            {
                if (P0.y > P1.y) {
                    t = P0;
                    P0 = P1;
                    P1 = t;
                }
                for (p = P0; p.y < P1.y; p.y++) {
                    c0=Color.rgb((int)(e*red),(int)(e*green),(int)(e*blue));
                    c1=Color.rgb((int)((1.0-e)*red),(int)((1.0-e)*green),(int)((1.0-e)*blue));

                    commen.drawPoint(p.x,p.y,c0);
                    commen.drawPoint(p.x+1,p.y+1,c1);
                    e = e + 1/k;
                    if (e >= 1.0) {
                        p.x++;
                        e--;
                    }
                }
            }
            if (0.0 <= k && k <= 1.0)     //绘制0≤k≤1
            {
                if (P0.x > P1.x) {
                    t = P0;
                    P0 = P1;
                    P1 = t;
                }
                for (p = P0; p.x < P1.x; p.x++) {
                    System.out.println(e+","+k);
                    c0=Color.rgb((int)(e*red),(int)(e*green),(int)(e*blue));
                    c1=Color.rgb((int)((1.0-e)*red),(int)((1.0-e)*green),(int)((1.0-e)*blue));

                    commen.drawPoint(p.x,p.y,c0);
                    commen.drawPoint(p.x,p.y+1,c1);
                    e = e + k;
                    if (e >= 1.0) {
                        p.y++;
                        e--;
                    }
                }
            }
            if (k >= -1.0 && k < 0.0)     //绘制-1≤k＜0
            {
                if (P0.x > P1.x) {
                    t = P0;
                    P0 = P1;
                    P1 = t;
                }
                for (p = P0; p.x < P1.x; p.x++) {
                    c0=Color.rgb((int)(e*red),(int)(e*green),(int)(e*blue));
                    c1=Color.rgb((int)((1.0-e)*red),(int)((1.0-e)*green),(int)((1.0-e)*blue));
                    commen.drawPoint(p.x,p.y,c0);
                    commen.drawPoint(p.x,p.y-1,c1);
                    e = e - k;
                    if (e >= 1.0) {
                        p.y--;
                        e--;
                    }
                }
            }
            if (k < -1.0)           //绘制k＜-1
            {
                if (P0.y < P1.y) {
                    t = P0;
                    P0 = P1;
                    P1 = t;
                }
                for (p = P0; p.y > P1.y; p.y--) {
                    c0=Color.rgb((int)(e*red),(int)(e*green),(int)(e*blue));
                    c1=Color.rgb((int)((1.0-e)*red),(int)((1.0-e)*green),(int)((1.0-e)*blue));
                    commen.drawPoint(p.x,p.y,c0);
                    commen.drawPoint(p.x+1,p.y,c1);
                    e = e - 1 / k;
                    if (e >= 1.0) {
                        p.x++;
                        e--;
                    }
                }
            }
        }
        P0 = P1;
    }
}