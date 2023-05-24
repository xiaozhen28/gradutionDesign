package canvas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName tt.java
 * @Description TODO
 * @createTime 2022年04月28日 14:55:00
 */
public class DF {
    GraphicsContext gc;
    int width = 400, height = 300; //地图尺寸
    double[][] distanceFiled = new double[height][width];  // 场大小为500*500
    double max = 0;

    static int[] door = new int[]{0, 200}; // 位置
    static int[] windos = new int[]{360, 70};
    static int[] center = new int[]{200, 150};
    int[][] obj = new int[][]{door, windos, center};
    double[] wight = new double[]{0.2, 0.3, 0.5};


    // furniture1、2、3 width length  3x2
    int[][] furnitureSize=new int[][]{{100,50},{200,200},{30,20}};
    int[][] furnitureLoc=new int[3][2];//存放家具


    public DF(GraphicsContext gc) throws IOException {
        this.gc = gc;
        long timeStamp1 = System.currentTimeMillis();

        this.initDF();
        long timeStamp2 = System.currentTimeMillis();
        System.out.println("时间："+(timeStamp2-timeStamp1));
    }

    void initDF() throws IOException {
        int i, j;
        for (j = 0; j < this.height; j++) {
            for (i = 0; i < this.width; i++) {
                calculat(i, j);
                if (this.distanceFiled[j][i] > max) {
                    max = this.distanceFiled[j][i];
                }
                draw(i, j);
            }
        }
        //加入 门窗 参数改
        this.era(door[0],door[1],10,20);
        this.era(windos[0],windos[1],10,20);
        // 放置 门 窗
        this.findLoc(furnitureSize[0]);
        this.findLoc(furnitureSize[1]);
        this.findLoc(furnitureSize[1]);
        this.findLoc(furnitureSize[2]);
        this.findLoc(furnitureSize[2]);

//        File file = new File("E:/a.csv");//文件保存路径
//        FileWriter out = new FileWriter(file);
//
//        for (j = 0; j < this.height; j++) {
//            for (i = 0; i < this.width; i++) {
//                //System.out.print(distanceFiled[j][i]+" ");
//                out.write(distanceFiled[j][i]+",");//doubles[][]为二维数组
//            }
//            out.write("\r\n");
//            System.out.println();
//        }
//        out.close();
    }

    int[] findLoc(int []funiture){ //根据家具尺寸遍历寻找位置
        int loc[]=new int[]{0,0};
        int energy=0;
        int t;
        for (int j = 0; j < this.height -funiture[1]; j++) {
            for (int i = 0; i < this.width -funiture[0]; i++) {
                t=calculat(i,j,funiture[0],funiture[1]);
                if(t==-1){
//                    System.out.println("碰撞");
                    continue;
                }
                else if(energy<t){
                    loc[0]=i;
                    loc[1]=j;
                    energy=t;
                }
                //System.out.println(t+",("+i+","+j+")");
            }
        }
        era(loc[0],loc[1],funiture[0],funiture[1]);
        draw(loc[0],loc[1],funiture[0],funiture[1]);
        //System.out.println(loc[0]+","+loc[1]);
        return loc;
    }
    void era(int x,int y,int width,int height){ // 在能量场上标记位置
        width+=x;
        height+=y;
        for (int j = y; j <height; j++) {
            for (int i = x; i < width; i++) {
               this.distanceFiled[j][i]=-1;
            }
        }

    }
    void calculat(int i, int j) { //计算最初的能量场
        for (int z = 0; z < this.wight.length; z++) {
            this.distanceFiled[j][i] += this.wight[z] * Math.sqrt(Math.pow(i - this.obj[z][0], 2) + Math.pow(j - this.obj[z][1], 2));
        }
    }

    int calculat(int x, int y,int width,int height) { // 计算家具能量场
        width+=x;
        height+=y;
        int Energy=0;
        for (int j = y; j <height; j++) {
            for (int i = x; i < width; i++) {
                if(this.distanceFiled[j][i]==-1){
                    return -1;
                }
                Energy+=this.distanceFiled[j][i];
            }
        }
        return Energy;
    }

    void draw(int i, int j) { //绘制能量场
        double c = this.distanceFiled[j][i] / max;
        Color color = Color.color(1-c, 1-c, 1-c);
        gc.setFill(color);
        gc.fillOval(i, j, 1, 1);
    }
    void draw(int i, int j,int width,int height) { //绘制家具
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.RED);
        gc.fillRect(i,j,width,height);
        //gc.strokeRect(i,j,width,height);
    }
}
