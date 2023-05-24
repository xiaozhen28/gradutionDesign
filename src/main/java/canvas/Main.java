package canvas;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Main.java
 * @Description TODO
 * @createTime 2022年03月07日 14:59:00
 */
public class Main extends Application {
    public static final double WIDTH=400,HEIGTH=300;
    private double x,y,x1,y1;
    private Canvas canvas =new Canvas(WIDTH,HEIGTH);
    private GraphicsContext graphicsContext=canvas.getGraphicsContext2D(); // 获取canvas 对象
    DF tt=new DF(graphicsContext);

    public Main() throws IOException {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        canvas.setOnMousePressed(event -> {
            x=event.getX();
            y=event.getY();
        });
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x1=event.getX();
                y1=event.getY();
                graphicsContext.strokeLine(x,y,x1,y1);
                x=x1;
                y=y1;
            }
        });
        AnchorPane root =new AnchorPane(canvas);
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
