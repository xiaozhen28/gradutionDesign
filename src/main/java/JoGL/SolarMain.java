package JoGL;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName SolarMain.java
 * @Description TODO
 * @createTime 2022年05月02日 22:01:00
 */
public class SolarMain extends JFrame{
    public GLRender listener=new GLRender();
    static FPSAnimator animator=null;
    public SolarMain() throws HeadlessException {
        super("点光源演示");
        setSize(600,480);
        GLCapabilities glcaps=new GLCapabilities(null);          //这里和之前章节的代码有区别.
        GLCanvas canvas=new GLCanvas(glcaps);
        canvas.addGLEventListener(listener);
        //canvas.addMouseListener(listener);
        getContentPane().add(canvas, BorderLayout.CENTER);
        animator=new FPSAnimator(canvas,300,true);

        centerWindow(this);
    }
    private void centerWindow(Component frame) { // 居中窗体
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        frame.setLocation((screenSize.width - frameSize.width) >> 1,
                (screenSize.height - frameSize.height) >> 1);

    }

    public void run(float sunPos[],float color[]){
        final SolarMain app = new SolarMain();
        app.listener.sunPos=sunPos;
        app.listener.sourceLight=color;
        // 显示窗体
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app.setVisible(true);
            }
        });
        // 动画线程开始
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                animator.start();
            }
        });
    }
    public static void main(String[] args) {
        final SolarMain app = new SolarMain();
        // 显示窗体
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                app.setVisible(true);
            }
        });
        // 动画线程开始
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                animator.start();
            }
        });
    }

}
