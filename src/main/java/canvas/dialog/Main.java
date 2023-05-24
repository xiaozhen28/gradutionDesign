package canvas.dialog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene=new Scene(root);
        Controller controller=fxmlLoader.getController();
        controller.setCiBind(scene);


        primaryStage.setTitle("app调用controller方法");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
