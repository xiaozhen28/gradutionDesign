package canvas.dialog;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;

public class Controller {

    @FXML
    private Circle ci;

    public void setCiBind(Scene scene) {
        ci.centerXProperty().bind(scene.widthProperty().divide(2));
        ci.centerYProperty().bind(scene.heightProperty().divide(2));
    }
}
