package tools;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName StageManager.java
 * @Description TODO 管理多窗口的控制器
 * @createTime 2022年03月17日 14:41:00
 */
public class StageManager {
    public static Map<String, Stage> STAGE=new HashMap<String, Stage>();
    public static Map<String, Object> CONTROLLER=new HashMap<String, Object>();
}
