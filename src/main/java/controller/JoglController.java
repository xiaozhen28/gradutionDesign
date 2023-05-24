package controller;

import JoGL.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import lombok.SneakyThrows;
import tools.StageManager;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ResourceBundle;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName JoglController.java
 * @Description TODO
 * @createTime 2022年03月16日 10:00:00
 */
public class JoglController implements Initializable{
    @FXML
    private TextArea houseCode;
    @FXML
    private TextArea cubeCode;
    @FXML
    private TextArea textureCode;
    @FXML
    private TextArea triangle3dCode;
    @FXML
    private Button textureButton;
    @FXML
    private ImageView textureImage;
    @FXML
    private TextField x_input;
    @FXML
    private TextField y_input;
    @FXML
    private TextField z_input;
    @FXML
    private ColorPicker colorPicker;
    // 变量定义
    File selectedFile;
    //
    House house=new House();
    Cube cube=new Cube();;
    CubeTexture cubeTexture=new CubeTexture();
    Triangle3d triangle3d=new Triangle3d();
    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedFile =new File(getPath("/img/textute01.jpg"));
        textureImage.setImage(new Image(selectedFile.toURI().toString()));
        readCode(triangle3dCode,getPath("/joglCode/Triangle3d.txt"));
        readCode(houseCode,getPath("/joglCode/House.txt"));
        readCode(cubeCode,getPath("/joglCode/Cube.txt"));
        readCode(textureCode,getPath("/joglCode/CubeTexture.txt"));
    }

    String getPath(String path) throws UnsupportedEncodingException {
        path=getClass().getResource(path).getPath();
        path= URLDecoder.decode(path,"UTF-8");
        return path;
    }
    @FXML
    void RunHouse(ActionEvent event) {
        house.run();
    }
    @FXML
    void RunCube(ActionEvent event) {
        cube.run();
    }
    @FXML
    void RunTexture(ActionEvent event) {
        cubeTexture.run(selectedFile);
    }
    @FXML
    void getFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(StageManager.STAGE.get("opengl"));
        if(!selectedFile.toURI().toString().isEmpty())
            textureImage.setImage(new Image(selectedFile.toURI().toString()));
    }

    @FXML
    void RunTriangle3d(ActionEvent event) {
        triangle3d.run();
    }
    @FXML
    void RunLight(ActionEvent event){
        SolarMain solarMain=new SolarMain();
        float pos[]={Float.parseFloat(this.x_input.textProperty().getValue()),Float.parseFloat(this.y_input.textProperty().getValue()),Float.parseFloat(this.z_input.textProperty().getValue())};
        Color color= this.colorPicker.getValue();
        float myColor[]={(float) color.getRed(), (float) color.getGreen(), (float) color.getBlue(),1.0f};
        solarMain.run(pos,myColor);
    }
    void readCode(TextArea textArea,String URL){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(URL)), "UTF-8"));
            String lineTxt = null;
            int count = 0;
            // 逐行读取
            while ((lineTxt = br.readLine()) != null) {
                // 输出内容到控制台
                textArea.appendText(lineTxt+"\n");
                count++;
            }
            br.close();
        } catch (Exception e) {
            cubeCode.appendText("Error Message :"+e);
        }
    }
}
