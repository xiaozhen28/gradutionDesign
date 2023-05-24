package controller;

import arithmetic.Fill2D;
import arithmetic.Transformation2D;
import arithmetic.Transformation3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tools.point2D;
import tools.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import arithmetic.Draw2D;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName MainController.java
 * @Description TODO
 * @createTime 2022年03月07日 15:37:00
 */
public class MainController implements Initializable {
    @FXML
    private AnchorPane Apane;

    @FXML
    private Canvas canvas;

    @FXML
    private TreeView<String> tabletree;

    @FXML
    private TextArea consoletext;

    @FXML
    private MenuItem openGL; //

    @FXML
    private AnchorPane canvas_AnchorPane; //canvas的容器

    @FXML
    private TabPane leftTabPane;

    @FXML
    private AnchorPane leftAnchorpane;

    @FXML
    private AnchorPane consoleAnChorPane;

    @FXML
    private ColorPicker getcolorpicker;

    @FXML
    private TextField startx;

    @FXML
    private TextField starty;

    @FXML
    private TextField endx;

    @FXML
    private TextField endy;

    @FXML
    private TextField penX;

    @FXML
    private TextField penY;

    @FXML
    private Slider pointSize;

    @FXML
    private Button buttonTranslate;
    @FXML
    private Slider speed;

    // 面板输入框
    // 二维变换
    @FXML
    private TextField input2_tx;

    @FXML
    private TextField input2_ty;

    @FXML
    private TextField input2_sx;
    @FXML
    private TextField input2_sy;
    @FXML
    private TextField input2_Beta;
    @FXML
    private ToggleGroup toggglGroup2DRotate;
    @FXML
    private ToggleGroup toggglGroup2DReflect;
    @FXML
    private TextField input2_b;
    @FXML
    private TextField input2_c;


    //三维变换框
    @FXML
    private TextField input3_tx;

    @FXML
    private TextField input3_ty;

    @FXML
    private TextField input3_tz;

    @FXML
    private TextField input3_sx;

    @FXML
    private TextField input3_sy;
    @FXML
    private TextField input3_sz;
    @FXML
    private TextField input3_Beta;
    @FXML
    private TextField input3_sheer1;
    @FXML
    private TextField input3_sheer2;
    @FXML
    private ToggleGroup toggglGroup3DRotate;
    @FXML
    private ToggleGroup toggglGroup3DReflect;
    @FXML
    private ToggleGroup toggglGroup3DShear;


    //算法实现类
    Draw2D draw2d;
    Fill2D fill2D;
    Transformation2D transformation2D;
    Transformation3D transformation3D;

    // 画布尺寸
    int Width=460;
    int Length=263;

    //中间变量
    point2D P0,P1;
    Boolean flage=false;
    /**
     * @title 界面初始化
     * @description 加载出网格
     * @author zhenjie
     * @param
     * @updateTime 2022/3/17 14:53
     * @throws
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setCanvasBackground(); // 初始化画板背景
        this.initTableTree();  // 给算法折叠树版绑定事件
        this.initMouse(); //监听鼠标点击事件
        this.consoletext.appendText("\n==============================控制台===========================\n");
        this.consoletext.addEventFilter(ActionEvent.ANY,event -> System.out.println(consoleAnChorPane.getWidth()));

        // 实例化绘图类
        // 绘制基本图元算法类
        draw2d=new Draw2D(this.canvas.getGraphicsContext2D(),consoletext);
        fill2D=new Fill2D(this.canvas.getGraphicsContext2D(),consoletext);
        //平面模型转换类
        transformation2D=new Transformation2D(canvas.getGraphicsContext2D(),consoletext);
        // 立体模型转换类
        transformation3D= new Transformation3D(canvas.getGraphicsContext2D(),consoletext);
    }

    // 绑定触发的事件
    private void initTableTree() {
        this.setTabletree(); // 初始化treeView
        consoletext.setEditable(false);
        tabletree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null && newValue != oldValue){
                System.out.println(observable.getValue().getValue());
                P0=new point2D(Integer.parseInt(startx.textProperty().getValue()),Integer.parseInt(starty.textProperty().getValue()));
                P1=new point2D(Integer.parseInt(endx.textProperty().getValue()),Integer.parseInt(endy.textProperty().getValue()));

                switch (observable.getValue().getValue()){
                    case "直线中点Bresenham":{
                        new Thread(()-> {
                            draw2d.commen.color=getcolorpicker.getValue();
                            this.draw2d.commen.size= (int) pointSize.getValue();
                            System.out.println(this.draw2d.commen.size);
                            this.draw2d.BreshamsLine(P0, P1, getcolorpicker.getValue());
                        }).start();
                        break;
                    }
                    case "圆中点Bresenham":{
                        this.consoletext.appendText("开始绘制");
                        new Thread(() -> {
                            try {
                                draw2d.MBCircle(this.P0, this.P1, canvas.getGraphicsContext2D(), consoletext);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        break;
                    }
                    case "椭圆中点Breasenham":{
                        new Thread(()-> {
                            this.draw2d.MBEllipse(this.P0,this.P1);
                        }).start();
                        break;
                    }
                    case "直线反走样Wus算法":{
                        new Thread(()->{
                            draw2d.WuLine(P0,P1);
                        }).start();
                        break;
                    }
                    case "区域四邻接点填充算法":{
                        try {
                            this.Fill4();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "多边形有效边填充":{
                        try {
                            this.FillebyEdge();
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "二维几何变换算法":{
                        flage=true;
                        new Thread(() -> {
                            transformation2D.DrawPolygon();
                        }).start();
                        break;
                    }
                    case "三维几何变换算法":{
                        flage=true;
                        transformation3D.DrawPolygon();
                        break;
                    }
                    default:{break;}
                }
            }
        });
    }

    // 鼠标拾取点
    public void initMouse(){
        this.canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            point2D temp=new point2D(event.getX()-this.Width,event.getY()-this.Length);
            this.penX.setText(String.valueOf(temp.x));
            this.penY.setText(String.valueOf(temp.y));
            System.out.println(event.toString()+"\n");
            consoletext.appendText("当前选中的点为("+event.getX()+","+event.getY()+")\n");
        });
    }


    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("/icon/model.png"))
    );

    public void test() throws InterruptedException {
        new Thread(()->{
//            MyPoint P0=new MyPoint(100,200);
//            MyPoint P1=new MyPoint(1,2);
//            draw2d.BreshamsLine(P0,P1);
        }).start();
//        SimpleSrpnskTri simpleSrpnskTri=new SimpleSrpnskTri(this.canvas);
    }

    // 边缘填充算法
    public void FillebyEdge() throws InterruptedException, IOException {
        fill2D.DrawPolygon();
        // 截取场景的快照
        SnapshotParameters parameters=new SnapshotParameters();
        parameters.setFill(Color.WHITE);
        WritableImage writableImage = canvas.snapshot(null,null);//获取场景图像的快照图片
        new Thread(()->{
            System.out.println("开始填充图形");
            try {
                fill2D.FClr =getcolorpicker.getValue();
                fill2D.FillPolygon(writableImage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 四邻接填充算法
    public void Fill4() throws IOException {
        fill2D.DrawPolygonWithNo();
        // 截取场景的快照
        SnapshotParameters parameters=new SnapshotParameters();
        parameters.setFill(Color.WHITE);
        WritableImage writableImage = canvas.snapshot(null,null);//获取场景图像的快照图片
        new Thread(()->{
            System.out.println("四邻接填充算法 开始填充图形");
            point2D point2D=new point2D(460,260);
            fill2D.FillPolygon4(point2D,writableImage);
        }).start();
    }
    // 二维变换按钮绑定
    public void Translate2D(){
        new Thread(()->{
            transformation2D.Translate(Double.parseDouble(input2_tx.textProperty().getValue()) ,Double.parseDouble(input2_ty.textProperty().getValue()));
            transformation2D.DrawPolygon(getcolorpicker.getValue());
        }).start();
    }

    public void Scale2D(){
        new Thread(()->{
            transformation2D.Scale(Double.parseDouble(input2_sx.textProperty().getValue()),Double.parseDouble(input2_sy.textProperty().getValue()));
            transformation2D.DrawPolygon(getcolorpicker.getValue());
        }).start();
    }

    public void Rotate2D(){
        transformation2D.Rotate(Double.parseDouble(input2_Beta.textProperty().getValue()));
        transformation2D.DrawPolygon(getcolorpicker.getValue());
    }
    public void Reflect2D(){
        String selected =((RadioButton) toggglGroup2DReflect.getSelectedToggle()).textProperty().getValue();
       // transformation2D.Rotate(Double.parseDouble(input2_Beta.textProperty().getValue()));
        if(selected.equals("x")){
            transformation2D.ReflectX();
        }else if (selected.equals("y")){
            transformation2D.ReflectY();
        }else if(selected.equals("") ||selected.equals("O")){
            transformation2D.ReflectOrg();
        }
        transformation2D.DrawPolygon(getcolorpicker.getValue());
    }
    public void Sheer2D(){
        new Thread(()->{
            transformation2D.Shear(Double.parseDouble(input2_b.textProperty().getValue()) ,Double.parseDouble(input2_c.textProperty().getValue()));
            transformation2D.DrawPolygon(getcolorpicker.getValue());
        }).start();
    }

    // 三维变换按钮绑定
    public void Translate3D(){
        new Thread(()->{
            transformation3D.Translate(Double.parseDouble(input3_tx.textProperty().getValue()) ,Double.parseDouble(input3_ty.textProperty().getValue()),Double.parseDouble(input3_tz.textProperty().getValue()));
            transformation3D.DrawPolygon(getcolorpicker.getValue());
        }).start();
    }

    public void Scale3D(){
        new Thread(()->{
            transformation3D.Scale(Double.parseDouble(input3_sx.textProperty().getValue()),Double.parseDouble(input3_sy.textProperty().getValue()),Double.parseDouble(input3_sz.textProperty().getValue()));
            transformation3D.DrawPolygon(getcolorpicker.getValue());
        }).start();
    }

    public void Rotate3D(){
        String selected =((RadioButton) toggglGroup3DRotate.getSelectedToggle()).textProperty().getValue();
        if(selected.equals("x")){
            transformation3D.RotateX(Double.parseDouble(input3_Beta.textProperty().getValue()));
        }else if(selected.equals("y")){
            transformation3D.RotateY(Double.parseDouble(input3_Beta.textProperty().getValue()));
        }else if(selected.equals("z")){
            transformation3D.RotateZ(Double.parseDouble(input3_Beta.textProperty().getValue()));
        }
        transformation3D.DrawPolygon(getcolorpicker.getValue());
    }
    public void Reflect3D(){
        String selected =((RadioButton) toggglGroup3DReflect.getSelectedToggle()).textProperty().getValue();
        if(selected.equals("x")){
            transformation3D.ReflectX();
        }else if (selected.equals("y")){
            transformation3D.ReflectY();
        }else if(selected.equals("z")){
            transformation3D.ReflectZ();
        }else if(selected.equals("xOy")){
            transformation3D.ReflectXOY();
        }else if(selected.equals("yOz")){
            transformation3D.ReflectYOZ();
        }else if(selected.equals("zOx")){
            transformation3D.ReflectZOX();
        }
        transformation3D.DrawPolygon(getcolorpicker.getValue());
    }
    public void Sheer3D(){
        String selected =((RadioButton) toggglGroup3DShear.getSelectedToggle()).textProperty().getValue();
        if(selected.equals("x")){
            transformation3D.ShearX(Double.parseDouble(input3_sheer1.textProperty().getValue()),Double.parseDouble(input3_sheer2.textProperty().getValue()));
        }else if (selected.equals("y")){
            transformation3D.ShearY(Double.parseDouble(input3_sheer1.textProperty().getValue()),Double.parseDouble(input3_sheer2.textProperty().getValue()));
        }else if(selected.equals("z")){
            transformation3D.ShearZ(Double.parseDouble(input3_sheer1.textProperty().getValue()),Double.parseDouble(input3_sheer2.textProperty().getValue()));
        }
        transformation3D.DrawPolygon(getcolorpicker.getValue());
    }

    public void setTabletree(){
        TreeItem<String> root1 = new TreeItem<String>("基本图元绘制算法");
        root1.setExpanded(false);
        root1.getChildren().addAll(
                new TreeItem<String>("直线中点Bresenham"),
                new TreeItem<String>("圆中点Bresenham"),
                new TreeItem<String>("椭圆中点Breasenham"),
                new TreeItem<String>("直线反走样Wus算法")
        );

        TreeItem<String> root2 = new TreeItem<String>("填充算法");
        root2.setExpanded(false);
        root2.getChildren().addAll(
                new TreeItem<String>("多边形有效边填充"),
                new TreeItem<String>("区域四邻接点填充算法")
        );

        TreeItem<String> root3 = new TreeItem<String>("二维几何变换算法");
        root3.setExpanded(false);
        root3.getChildren().addAll(
                new TreeItem<String>("平移变换"),
                new TreeItem<String>("比例变换"),
                new TreeItem<String>("旋转变换"),
                new TreeItem<String>("反射变换"),
                new TreeItem<String>("错切变换")
        );
        TreeItem<String> root4 = new TreeItem<String>("三维几何变换算法");
        root4.setExpanded(false);
        root4.getChildren().addAll(
                new TreeItem<String>("平移变换"),
                new TreeItem<String>("比例变换"),
                new TreeItem<String>("旋转变换"),
                new TreeItem<String>("反射变换"),
                new TreeItem<String>("错切变换")
        );
        TreeItem<String> roots=new TreeItem<String>("常见算法");
        roots.setExpanded(true);
        roots.getChildren().addAll(root1, root2, root3,root4);
        this.tabletree.setRoot(roots);
    }


    // 绘制坐标系
    @FXML
    public void setCanvasBackground(){
        Image image=new Image("/img/aix.png");

        this.canvas.getGraphicsContext2D().drawImage(image,0,0);
        System.out.println("绘制网格图 高度"+image.getHeight()+"宽度"+image.getWidth());
        if(flage==true) {
            transformation2D.ReadPoint();
            transformation3D.ReadPoint();
        }
    }
    public void openPixcelPanel() throws IOException {
        Stage stage=new Stage();
        Parent root=FXMLLoader.load(getClass().getResource("/fxml/opengl.fxml"));
        stage.setTitle("JOGL");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**
     * @title 打开第二个窗口
     * @description
     * @author zhenjie
     * @param
     * @updateTime 2022/3/17 14:54
     * @throws
     */
    public void open(ActionEvent event) throws Exception {
        Stage stage =new Stage();
        Parent root=FXMLLoader.load(getClass().getResource("/fxml/opengl.fxml"));
        stage.setTitle("真实感立体图形绘制窗口");
        stage.getIcons().add(new Image("/icon/board.png"));
        stage.setScene(new Scene(root));
        stage.show();
        // 第二个窗口保存到map中
        StageManager.STAGE.put("opengl",stage);

        //将本窗口保存到map中
        StageManager.CONTROLLER.put("mainController",this);
        /*
        首先需要new一个Stage然后加载fxml文件做一些相关的设置后显示,重点在两个备注中我们将刚刚new出来的stage保存到舞台管理类里面
        如果别的窗口不需要对打开的窗口进行操作可以不需要这一步,因为我需要通过按钮关闭窗口,所以需要这一步,
        再将第一个窗口的控制器保存到舞台管理类中(这一步是数据交互的时候使用,不需要可以可以不用这一步),
        这样我们打开第二个窗口就完事,正常显示我们第二个窗口了;
         */
    }

}
