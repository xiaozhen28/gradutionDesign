package arithmetic;

import javafx.scene.control.TextArea;
import tools.Commen;
import tools.point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Stack;

import static java.lang.Math.round;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Fill.java
 * @Description TODO 各种填充算法
 * @createTime 2022年03月21日 15:33:00
 */
public class Fill2D {
    Commen commen; // 绘制点的函数
    TextArea textArea;
    GraphicsContext gc; // canvas上的画笔


    public int nPoints; // 顶点数量
    public point2D[] P; // 要填充的点的集合
    public Color BClr;//边界颜色 填充色
    public Color FClr;// 填充色

    boolean bFill=false;//填充标志
    double MaxX, MinX, MaxY, MinY;//包围盒的对角点
    Draw2D draw2D;

    // 画布尺寸
    int Width=460;
    int Length=263;

    public Fill2D(GraphicsContext gc, TextArea textArea) {
        this.gc = gc;
        this.textArea=textArea;
        commen=new Commen(gc,textArea,20);
        this.BClr = Color.RED; // 线框色
        this.FClr=Color.GREEN;// 填充色
        draw2D=new Draw2D(gc,textArea,2,this.BClr);
        nPoints = 7;
        this.init();
    }

    public void init() { // 初始化 点表
        this.BClr =Color.RED;
        P =new point2D[nPoints];
        for (int i = 0; i <nPoints ; i++) {
            P[i]=new point2D();
        }
        this.ReadPoint();
    }
    void ReadPoint()//点表
    {
        P[0].x=50;  P[0].y=100;
        P[1].x=-150;P[1].y=300;
        P[2].x=-250;P[2].y=50;
        P[3].x=-150;P[3].y=-250;
        P[4].x=0;   P[4].y=-50;
        P[5].x=100; P[5].y=-250;
        P[6].x=300; P[6].y=150;
    }
    public void DrawPolygon()//绘制多边形边界
    {
        gc.beginPath();
        gc.setStroke(this.BClr);
        MinX = MaxX = P[0].x;
        MinY = MaxY = P[0].y;
        for(int i=0;i<nPoints;i++)                         //计算多边形边界
        {
            if(P[i].x>MaxX)
                MaxX= P[i].x;
            if(P[i].x<MinX)
                MinX= P[i].x;
            if(P[i].y>MaxY)
                MaxY= P[i].y;
            if(P[i].y<MinY)
                MinY= P[i].y;
        }
        point2D t = new point2D();
        for(int i=0;i<nPoints;i++)                          //绘制多边形
        {
            if(i==0)
            {
                gc.moveTo(P[i].x+Width, P[i].y+Length);
                t= P[i];
            }
            else
            {
                gc.lineTo(P[i].x+Width, P[i].y+Length);
            }
        }
        gc.lineTo(t.x+Width,t.y+Length); //闭合多边形
        gc.moveTo(MinX+Width,MinY+Length);//绘制包围盒
        gc.lineTo(MinX+Width,MaxY+Length);
        gc.lineTo(MaxX+Width,MaxY+Length);
        gc.lineTo(MaxX+Width,MinY+Length);
        gc.lineTo(MinX+Width,MinY+Length);
        gc.stroke();
        gc.closePath();
        textArea.appendText("绘制带包围盒的多边形线框\n");
    }

    public void DrawPolygonWithNo()//绘制多边形边界
    {
        gc.beginPath();
        gc.setStroke(this.BClr);
        gc.setLineWidth(3);
        point2D t = new point2D();
        for(int i=0;i<7;i++)//绘制多边形
        {
            if(i==0)
            {
                gc.moveTo(P[i].x+Width,P[i].y+Length);
                t=P[i];
            }
            else
            {
                gc.lineTo(P[i].x+Width,P[i].y+Length);
            }
        }
        gc.lineTo(t.x+Width,t.y+Length);//闭合多边形
        gc.stroke();
        gc.closePath();
        textArea.appendText("绘制多边形边界\n");
    }
    public void FillPolygon(WritableImage writableImage) throws InterruptedException//填充多边形
    {
        Color BClr = Color.WHITE;//背景色
        Color FClr =this.FClr; // 填充色
        Color color;
        int ymin, ymax; // 边的最小y值与最大y值
        double x, y, k; // x,y当前点，k斜率的倒数
        for (int i = 0; i < this.nPoints; i++) // 循环多边形所有边
        {
            int j = (i + 1) % this.nPoints;
            k = (P[i].x - P[j].x) / (P[i].y - P[j].y); // 计算1/k
            if (P[i].y< P[j].y)//得到每条边y的最大值与最小值
            {
                ymin = (int) round(P[i].y)+Length;
                ymax = (int) round(P[j].y)+Length;
                x = P[i].x+Width; //得到x|ymin
            } else {
                ymin = (int) round(P[j].y)+Length;
                ymax = (int) round(P[i].y)+Length;
                x = P[j].x+Width;
            }
            for (y = ymin; y < ymax; y++)//沿每一条边循环扫描线
            {
                for (int m = (int) round(x); m < MaxX+Width; m++)       //对每一条扫描线与边的交点的右侧像素循环
                {
                    color=writableImage.getPixelReader().getColor(m,(int) round(y));
                    if (FClr.equals(color)) {  // 如果是填充色
                        writableImage.getPixelWriter().setColor(m,(int) round(y) ,BClr); // 置为背景色
                        gc.getPixelWriter().setColor(m, (int) round(y), BClr); // 同步到画布上背景色
                    }
                    else {
                        writableImage.getPixelWriter().setColor(m,(int) round(y),FClr); // 置为填充色
                        gc.getPixelWriter().setColor(m, (int) round(y), FClr); //同步到画布上
                    }
                }
                x += k;   //计算下一条扫描线的x起点坐标
                Thread.sleep(10);
            }
            textArea.appendText("边缘填充算法===>下一扫描区\n");
        }
        textArea.appendText("边缘填充算法演示完毕\n");
    }


    //区域四邻接点填充算法

    public void FillPolygon4(point2D Seed, WritableImage backgroundImage)//填充多边形
    {
        Color BoundaryClr=Color.RED;//边界色
        Color SeedClr=Color.GREEN;
        Color PixelClr;//当前像素的颜色
        Stack<point2D> pHead=new Stack<point2D>();
        bFill=false;//填充标志
        point2D PointLeft=new point2D(),PointTop=new point2D(),PointRight=new point2D(),PointBottom=new point2D();//种子及其四个邻接点
        pHead.push(Seed);//种子像素入栈
        int x,y,x0= (int) round(Seed.x),y0= (int) round(Seed.y);//x，y用于判断种子与图形的位置关系
        x=x0-1;
        while(BoundaryClr!=backgroundImage.getPixelReader().getColor(x,y0) && SeedClr!=backgroundImage.getPixelReader().getColor(x,y0))//左方判断
        {
            x--;
            if(x<=0)//到达客户区最左端
            {
                textArea.appendText("种子不在图形之内 左\n");
                return;
            }
        }
        y=y0+1;
        while(BoundaryClr!=backgroundImage.getPixelReader().getColor(x0,y) && SeedClr!=backgroundImage.getPixelReader().getColor(x0,y))//上方判断
        {
            y++;
            if(y>=gc.getCanvas().getHeight())//到达客户区最上端
            {
                textArea.appendText("种子不在图形之内 上\n");
                return;
            }
        }
        x=x0+1;
        while(BoundaryClr!=backgroundImage.getPixelReader().getColor(x,y0) && SeedClr!=backgroundImage.getPixelReader().getColor(x,y0))//右方判断
        {
            x++;
            if(x>=gc.getCanvas().getWidth())//到达客户区最右端
            {
                textArea.appendText("种子不在图形之内\n");
                return;
            }
        }
        y=y0-1;
        while(BoundaryClr!=backgroundImage.getPixelReader().getColor(x0,y) && SeedClr!=backgroundImage.getPixelReader().getColor(x0,y))//下方判断
        {
            y--;
            if(y<=0)//到达客户区最下端
            {
                textArea.appendText("种子不在图形之内\n");
                return;
            }
        }
        while(!pHead.empty())//如果栈不为空
        {
            point2D PopPoint;
            PopPoint=pHead.pop();
            if(SeedClr==backgroundImage.getPixelReader().getColor((int)round(PopPoint.x),(int)round(PopPoint.y)))
                continue;//加速
            gc.getPixelWriter().setColor((int)round(PopPoint.x),(int)round(PopPoint.y),SeedClr); //同步到可视画布上
            backgroundImage.getPixelWriter().setColor((int)round(PopPoint.x),(int)round(PopPoint.y),SeedClr); //画到背景画布上
            System.out.println("绘制到画布上");
            PointLeft.x=PopPoint.x-1;//搜索出栈结点的左方像素
            PointLeft.y=PopPoint.y;
            PixelClr=backgroundImage.getPixelReader().getColor((int)round(PointLeft.x),(int)round(PointLeft.y));
            if(BoundaryClr!=PixelClr && SeedClr!=PixelClr)//不是边界色并且未置成填充色
                pHead.push(PointLeft);//左方像素入栈
            PointTop.x=PopPoint.x;
            PointTop.y=PopPoint.y+1;//搜索出栈结点的上方像素
            PixelClr=backgroundImage.getPixelReader().getColor((int)round(PointTop.x),(int)round(PointTop.y));
            if(BoundaryClr!=PixelClr && SeedClr!=PixelClr)
                pHead.push(PointTop);	//上方像素入栈
            PointRight.x=PopPoint.x+1;//搜索出栈结点的右方像素
            PointRight.y=PopPoint.y;
            PixelClr=backgroundImage.getPixelReader().getColor((int)round(PointRight.x),(int)round(PointRight.y));
            if(BoundaryClr!=PixelClr && SeedClr!=PixelClr)
                pHead.push(PointRight);//右方像素入栈
            PointBottom.x=PopPoint.x;
            PointBottom.y=PopPoint.y-1;//搜索出栈结点的下方像素
            PixelClr=backgroundImage.getPixelReader().getColor((int)round(PointBottom.x),(int)round(PointBottom.y));
            if(BoundaryClr!=PixelClr && SeedClr!=PixelClr)
                pHead.push(PointBottom);//下方像素入栈
        }
        textArea.appendText("填充完毕\n");
    }
}
