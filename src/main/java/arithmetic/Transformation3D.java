package arithmetic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import tools.Face;
import tools.point2D;
import tools.point3D;


import static java.lang.Math.*;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Transformation3D.java
 * @Description TODO
 * @createTime 2022年04月23日 21:36:00
 */


public class Transformation3D {
    public double T[][]=new double[4][4]; // 变换矩阵

    public TextArea textArea;
    public GraphicsContext gc;

    // 画布尺寸
    int Width=460;
    int Length=263;

    //绘制的图形
    int num;
    public point3D POld[]=new point3D[8];//点表
    Face F[]=new Face[6];//面表
    point2D ScreenP=new point2D();//屏幕坐标系的二维坐标点

    public Transformation3D(GraphicsContext gc,TextArea textArea) {
        //实例化对象数组
        for (int i = 0; i <this.POld.length ; i++) {
            this.POld[i] = new point3D(0,0,0,1);
        }
        for (int i = 0; i < this.F.length; i++) {
            this.F[i]=new Face();
        }
        ReadPoint();
        ReadFace();
        this.num=8;
        this.gc=gc;
        this.textArea=textArea;
    }

    void Draw3D()//绘制坐标轴
    {
        Image image=new Image("/img/aix1.png");
        this.gc.drawImage(image,0,0);
        this.textArea.appendText("界面刷新\n");
    }

    public void DrawPolygon()//绘制立方体线框模型
    {
        gc.beginPath();
        for(int nFace=0;nFace<6;nFace++)
        {
            point2D t = new point2D();
            for(int nVertex=0;nVertex<F[nFace].vN;nVertex++)//顶点循环
            {
                ObliqueProject(POld[F[nFace].vI[nVertex]]);//斜等测投影
                if(0==nVertex)
                {
                    gc.moveTo(ScreenP.x+Width,ScreenP.y+Length);
                    t=ScreenP;
                }
                else
                    gc.lineTo(ScreenP.x+Width,ScreenP.y+Length);
            }
            gc.lineTo(t.x+Width,t.y+Length);//闭合多边形
            gc.stroke();
        }
        gc.closePath();
    }
    public void DrawPolygon(Color color)//绘制立方体线框模型
    {
        gc.beginPath();
        gc.setStroke(color);
        for(int nFace=0;nFace<6;nFace++)
        {
            point2D t = new point2D();
            for(int nVertex=0;nVertex<F[nFace].vN;nVertex++)//顶点循环
            {
                ObliqueProject(POld[F[nFace].vI[nVertex]]);//斜等测投影
                if(0==nVertex)
                {
                    gc.moveTo(ScreenP.x+Width,ScreenP.y+Length);
                    t=ScreenP;
                }
                else
                    gc.lineTo(ScreenP.x+Width,ScreenP.y+Length);
            }
            gc.lineTo(t.x+Width,t.y+Length);//闭合多边形
            gc.stroke();
            textArea.appendText("立体线框图绘制完毕\n");
        }
        gc.closePath();
    }

    public void ReadPoint()//点表
    {
        double a=100;//立方体边长为a
        //顶点的三维坐标(x,y,z)
        POld[0].x=0;POld[0].y=0;POld[0].z=0;
        POld[1].x=a;POld[1].y=0;POld[1].z=0;
        POld[2].x=a;POld[2].y=a;POld[2].z=0;
        POld[3].x=0;POld[3].y=a;POld[3].z=0;
        POld[4].x=0;POld[4].y=0;POld[4].z=a;
        POld[5].x=a;POld[5].y=0;POld[5].z=a;
        POld[6].x=a;POld[6].y=a;POld[6].z=a;
        POld[7].x=0;POld[7].y=a;POld[7].z=a;
    }

    public void ReadFace()//面表
    {
        //面的边数、面的顶点编号
        F[0].SetNum(4);F[0].vI[0]=4;F[0].vI[1]=5;F[0].vI[2]=6;F[0].vI[3]=7;//前面
        F[1].SetNum(4);F[1].vI[0]=0;F[1].vI[1]=3;F[1].vI[2]=2;F[1].vI[3]=1;//后面
        F[2].SetNum(4);F[2].vI[0]=0;F[2].vI[1]=4;F[2].vI[2]=7;F[2].vI[3]=3;//左面
        F[3].SetNum(4);F[3].vI[0]=1;F[3].vI[1]=2;F[3].vI[2]=6;F[3].vI[3]=5;//右面
        F[4].SetNum(4);F[4].vI[0]=2;F[4].vI[1]=3;F[4].vI[2]=7;F[4].vI[3]=6;//顶面
        F[5].SetNum(4);F[5].vI[0]=0;F[5].vI[1]=1;F[5].vI[2]=5;F[5].vI[3]=4;//底面
    }
    public void ObliqueProject(point3D p)//斜等测变换
    {
        ScreenP.x=p.x-p.z/sqrt(2.0);
        ScreenP.y=p.y-p.z/sqrt(2.0);
    }

    public void Identity()//单位矩阵
    {
        T[0][0]=1.0;T[0][1]=0.0;T[0][2]=0.0;T[0][3]=0.0;
        T[1][0]=0.0;T[1][1]=1.0;T[1][2]=0.0;T[1][3]=0.0;
        T[2][0]=0.0;T[2][1]=0.0;T[2][2]=1.0;T[2][3]=0.0;
        T[3][0]=0.0;T[3][1]=0.0;T[3][2]=0.0;T[3][3]=1.0;
    }

    public void Translate(double tx,double ty,double tz)//平移变换矩阵
    {
        Identity();
        T[3][0]=tx;
        T[3][1]=ty;
        T[3][2]=tz;
        MultiMatrix();
    }

    public void Scale(double sx,double sy,double sz)//比例变换矩阵
    {
        Identity();
        T[0][0]=sx;
        T[1][1]=sy;
        T[2][2]=sz;
        MultiMatrix();
    }

    public void Scale(double sx,double sy,double sz,point3D p)//相对于任意点的比例变换矩阵
    {
        Translate(-p.x,-p.y,-p.z);
        Scale(sx,sy,sz);
        Translate(p.x,p.y,p.z);
    }

    public void RotateX(double beta)//绕X轴旋转变换矩阵
    {
        Identity();
        double rad=beta*PI/180;
        T[1][1]=cos(rad); T[1][2]=sin(rad);
        T[2][1]=-sin(rad);T[2][2]=cos(rad);
        MultiMatrix();
    }

    public void RotateX(double beta,point3D p)//相对于任意点的绕X轴旋转变换矩阵
    {
        Translate(-p.x,-p.y,-p.z);
        RotateX(beta);
        Translate(p.x,p.y,p.z);
    }

    public void RotateY(double beta)//绕Y轴旋转变换矩阵
    {
        Identity();
        double rad=beta*PI/180;
        T[0][0]=cos(rad);T[0][2]=-sin(rad);
        T[2][0]=sin(rad);T[2][2]=cos(rad);
        MultiMatrix();
    }

    public void RotateY(double beta,point3D p)//相对于任意点的绕Y轴旋转变换矩阵
    {
        Translate(-p.x,-p.y,-p.z);
        RotateY(beta);
        Translate(p.x,p.y,p.z);
    }

    public void RotateZ(double beta)//绕Z轴旋转变换矩阵
    {
        Identity();
        double rad=beta*PI/180;
        T[0][0]=cos(rad); T[0][1]=sin(rad);
        T[1][0]=-sin(rad);T[1][1]=cos(rad);
        MultiMatrix();
    }

    public void RotateZ(double beta,point3D p)//相对于任意点的绕Z轴旋转变换矩阵
    {
        Translate(-p.x,-p.y,-p.z);
        RotateZ(beta);
        Translate(p.x,p.y,p.z);
    }

    public void ReflectX()//X轴反射变换矩阵
    {
        Identity();
        T[1][1]=-1;
        T[2][2]=-1;
        MultiMatrix();
    }

    public void ReflectY()//Y轴反射变换矩阵
    {
        Identity();
        T[0][0]=-1;
        T[2][2]=-1;
        MultiMatrix();
    }

    public void ReflectZ()//Z轴反射变换矩阵
    {
        Identity();
        T[0][0]=-1;
        T[1][1]=-1;
        MultiMatrix();
    }

    public void ReflectXOY()//XOY面反射变换矩阵
    {
        Identity();
        T[2][2]=-1;
        MultiMatrix();
    }

    public void ReflectYOZ()//YOZ面反射变换矩阵
    {
        Identity();
        T[0][0]=-1;
        MultiMatrix();
    }

    public void ReflectZOX()//ZOX面反射变换矩阵
    {
        Identity();
        T[1][1]=-1;
        MultiMatrix();
    }

    public void ShearX(double d,double g)//X方向错切变换矩阵
    {
        Identity();
        T[1][0]=d;
        T[2][0]=g;
        MultiMatrix();
    }

    public void ShearY(double b,double h)//Y方向错切变换矩阵
    {
        Identity();
        T[0][1]=b;
        T[2][1]=h;
        MultiMatrix();
    }

    public void ShearZ(double c,double f)//Z方向错切变换矩阵
    {
        Identity();
        T[0][2]=c;
        T[1][2]=f;
        MultiMatrix();
    }
    public void MultiMatrix()//矩阵相乘
    {
        for(int j=0;j<num;j++)
        {
            this.POld[j].x= POld[j].x*T[0][0]+ POld[j].y*T[1][0]+ POld[j].z*T[2][0]+ POld[j].w*T[3][0];
            this.POld[j].y= POld[j].x*T[0][1]+ POld[j].y*T[1][1]+ POld[j].z*T[2][1]+ POld[j].w*T[3][1];
            this.POld[j].z= POld[j].x*T[0][2]+ POld[j].y*T[1][2]+ POld[j].z*T[2][2]+ POld[j].w*T[3][2];
            this.POld[j].w= POld[j].x*T[0][3]+ POld[j].y*T[1][3]+ POld[j].z*T[2][3]+ POld[j].w*T[3][3];
        }
    }
}
