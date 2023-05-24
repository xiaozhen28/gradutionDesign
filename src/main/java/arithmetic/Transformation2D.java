package arithmetic;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import tools.point2D;
import javafx.scene.canvas.GraphicsContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Change.java
 * @Description TODO 二维图形几何变换算法
 * @createTime 2022年03月21日 16:55:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transformation2D {
    public double T[][]=new double[3][3]; // 变换矩阵
    int num=7;
    public point2D[]POld;

    public TextArea textArea;
    public GraphicsContext gc;
    public Color color=Color.RED;
    // 画布尺寸
    int Width=460;
    int Length=263;

    public Transformation2D(GraphicsContext gc, TextArea textArea) {
        this.gc = gc;
        this.textArea=textArea;
        POld =new point2D[this.num];
        for (int i = 0; i <this.num ; i++) {
            POld[i]=new point2D(0,0,1);
        }
        this.ReadPoint();
    }
    void Draw2d()//绘制坐标轴
    {
        Image image=new Image("/img/aix.png");
        this.gc.drawImage(image,0,0);
        this.textArea.appendText("界面刷新\n");
    }

   public void ReadPoint()  //构造点表
    {
        POld[0].x=10;  POld[0].y=20;
        POld[1].x=-30;POld[1].y=60;
        POld[2].x=-50;POld[2].y=10;
        POld[3].x=-80;POld[3].y=-20;
        POld[4].x=0;   POld[4].y=-10;
        POld[5].x=20; POld[5].y=-50;
        POld[6].x=70; POld[6].y=30;
    }

    public void DrawPolygon(Color teemcolor)//绘制多边形线框模型 指定颜色
    {
        gc.beginPath();
        gc.setStroke(teemcolor);
        point2D temp=POld[0];     //起点
        for(int i=0;i<this.num;i++)//绘制多边形
        {
            if(i==0)
            {
                gc.moveTo(POld[i].x+Width,POld[i].y+ Length);
                temp=POld[i];
            }
            else
            {
                gc.lineTo(POld[i].x+Width,POld[i].y+Length);
            }
        }
        gc.lineTo(temp.x+Width,temp.y+Length);//闭合多边形
        gc.stroke();
        gc.closePath();
        this.textArea.appendText("绘制多边形线框模型 绘制完毕\n");
    }
    public void DrawPolygon()//绘制多边形线框模型默认颜色
    {
        gc.beginPath();
        gc.setStroke(this.color);
        point2D temp=POld[0];     //起点
        for(int i=0;i<this.num;i++)//绘制多边形
        {
            if(i==0)
            {
                gc.moveTo(POld[i].x+Width,POld[i].y+ Length);
                temp=POld[i];
            }
            else
            {
                gc.lineTo(POld[i].x+Width,POld[i].y+Length);
            }
        }
        gc.lineTo(temp.x+Width,temp.y+Length);//闭合多边形
        gc.stroke();
        gc.closePath();
        this.textArea.appendText("绘制完毕\n");
    }

    public void Identity()//单位矩阵 初始化
    {
        T[0][0]=1;T[0][1]=0;T[0][2]=0;
        T[1][0]=0;T[1][1]=1;T[1][2]=0;
        T[2][0]=0;T[2][1]=0;T[2][2]=1;
    }

    public void Translate(double tx,double ty)//平移变换矩阵
    {
        Identity();
        T[2][0]=tx;
        T[2][1]=ty;
        MultiMatrix();
    }

    public void Scale(double sx,double sy)//比例变换矩阵
    {
        Identity();
        T[0][0]=sx;
        T[1][1]=sy;
        MultiMatrix();
    }

    public void Scale(double sx, double sy, point2D p)//相对于任意点的整体比例变换矩阵
    {
        Translate(-p.x,-p.y);
        Scale(sx,sy);
        Translate(p.x,p.y);
    }

    public void Rotate(double beta)//旋转变换矩阵
    {
        Identity();
        double rad=beta*Math.PI/180;
        T[0][0]=Math.cos(rad); T[0][1]=Math.sin(rad);
        T[1][0]=-Math.sin(rad);T[1][1]=Math.cos(rad);
        MultiMatrix();
    }

    public void Rotate(double beta, point2D p)//相对于任意点的旋转变换矩阵
    {
        Translate(-p.x,-p.y);
        Rotate(beta);
        Translate(p.x,p.y);
    }

    public void ReflectOrg()//原点反射变换矩阵
    {
        Identity();
        T[0][0]=-1;
        T[1][1]=-1;
        MultiMatrix();
    }

    public void ReflectX()//X轴反射变换矩阵
    {
        Identity();
        T[0][0]=1;
        T[1][1]=-1;
        MultiMatrix();
    }

    public void ReflectY()//Y轴反射变换矩阵
    {
        Identity();
        T[0][0]=-1;
        T[1][1]=1;
        MultiMatrix();
    }

    public void Shear(double b,double c)//错切变换矩阵
    {
        Identity();
        T[0][1]=b;
        T[1][0]=c;
        MultiMatrix();
    }

    public void MultiMatrix()//矩阵相乘
    {
        for(int j=0;j<num;j++)
        {
            POld[j].x=POld[j].x*T[0][0]+POld[j].y*T[1][0]+POld[j].w *T[2][0];
            POld[j].y=POld[j].x*T[0][1]+POld[j].y*T[1][1]+POld[j].w *T[2][1];
            POld[j].w =POld[j].x*T[0][2]+POld[j].y*T[1][2]+POld[j].w *T[2][2];
        }
    }
}
