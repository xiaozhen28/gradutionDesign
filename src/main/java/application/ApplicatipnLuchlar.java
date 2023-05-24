package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName Main.java
 * @Description TODO 项目启动类，加载主界面出来
 * @createTime 2022年03月07日 15:37:00
 */
public class ApplicatipnLuchlar extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        primaryStage.setTitle("图形算法可视化交互系统");
        primaryStage.getIcons().add(new Image("/icon/board.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }
}
