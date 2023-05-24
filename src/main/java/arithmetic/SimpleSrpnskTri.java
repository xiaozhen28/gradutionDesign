package arithmetic;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName terst.java
 * @Description TODO
 * @createTime 2022年03月09日 13:38:00
 */

public class SimpleSrpnskTri{

    private Canvas canvas;
    private GraphicsContext gc;
    private TextArea consoletext;
    private final int PADDING = 5;
    private static int numberOfLevels=10; // 生成垫片的数量

    /**
     * @Title 构造方法
     * @param canvas
     */
    public SimpleSrpnskTri(Canvas canvas){
        this.canvas=canvas;
        gc=canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        Triangles triangles=new Triangles();
    }

    class Triangles {
        private static final int PANEL_WIDTH =600, PANEL_HEIGHT = 600;
        private static final int TRI_WIDTH= 500, TRI_HEIGHT= 500;
        private static final int SIDE_GAP = (PANEL_WIDTH - TRI_WIDTH)/2;
        private static final int TOP_GAP = (PANEL_HEIGHT - TRI_HEIGHT)/2;
        private int countTriangles;
        private long startTime;
        private Point2D top, left, right;

        Triangles(){
            draw(numberOfLevels);
        }

        void draw(int numberLevels) {

            Platform.runLater(new Runnable() {

                @Override
                public void run() {

                    canvas.getGraphicsContext2D().fillText("Working....",5,15);
                    setStartPoints();

                    startTime = System.currentTimeMillis();
                    countTriangles = 0;

                    RunTask task = new RunTask(numberLevels, top, left, right);
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();
                }
            });
        }

        private void drawTriangle( int levels, Point2D top, Point2D left, Point2D right) {

            if(levels < 0) {//add stop criteria
                return ;
            }

            gc.strokePolygon( //implementing with strokeLine did not make much difference
                    new double[]{
                            top.getX(),left.getX(),right.getX()
                    },
                    new double[]{
                            top.getY(),left.getY(), right.getY()
                    },
                    3);

            countTriangles++;

            //Get the midpoint on each edge in the triangle
            Point2D p12 = midpoint(top, left);
            Point2D p23 = midpoint(left, right);
            Point2D p31 = midpoint(right, top);

            // recurse on 3 triangular areas
            drawTriangle(levels - 1, top, p12, p31);
            drawTriangle(levels - 1, p12, left, p23);
            drawTriangle(levels - 1, p31, p23, right);
        }

        private void setStartPoints() {

            top = new Point2D(canvas.getWidth()/2, TOP_GAP);
            left = new Point2D(SIDE_GAP, TOP_GAP + TRI_HEIGHT);
            right = new Point2D(SIDE_GAP + TRI_WIDTH, TOP_GAP + TRI_WIDTH);
        }

        private Point2D midpoint(Point2D p1, Point2D p2) {

            return new Point2D((p1.getX() + p2.getX()) /
                    2, (p1.getY() + p2.getY()) / 2);
        }

        private void updateGraphics(boolean success){
            if(success) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.fillText("Number of triangles: "+ countTriangles,5,15);
                gc.fillText("Time : "+ (System.currentTimeMillis()- startTime )+ " mili seconds", 5,35);
                gc.fillText("Levels: "+ numberOfLevels,5,55);
            }

        }


        class RunTask extends Task<Void>{

            private int levels;
            private Point2D top, left;
            private Point2D right;

            RunTask(int levels, Point2D top, Point2D left, Point2D right){

                this.levels = levels;
                this.top = top;
                this.left = left;
                this.right = right;

                startTime = System.currentTimeMillis();
                countTriangles = 0;
            }

            @Override public Void call() {

                drawTriangle(levels,top, left, right);
                return null;
            }

            @Override
            protected void succeeded() {

                updateGraphics(true);
                super.succeeded();
            }

            @Override
            protected void failed() {

                updateGraphics(false);
            }
        }
    }
}