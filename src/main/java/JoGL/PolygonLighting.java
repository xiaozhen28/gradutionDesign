package JoGL;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

/**
 * @author zhenjie
 * @version 1.0.0
 * @ClassName PolygonLighting.java
 * @Description TODO
 * @createTime 2022年05月02日 21:05:00
 */

public class PolygonLighting implements GLEventListener {
    private float rpoly;

    @Override
    public void display( GLAutoDrawable drawable ) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glColor3f(1f,0f,0f); //applying red

        // Clear The Screen And The Depth Buffer
        gl.glClear( GL2.GL_COLOR_BUFFER_BIT |
                GL2.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();       // Reset The View
        gl.glRotatef( rpoly, 0.0f, 1.0f, 0.0f );

        gl.glBegin( GL2.GL_POLYGON );

        gl.glVertex3f( 0f,0.5f,0f );
        gl.glVertex3f( -0.5f,0.2f,0f );
        gl.glVertex3f( -0.5f,-0.2f,0f );
        gl.glVertex3f( 0f,-0.5f,0f );
        gl.glVertex3f( 0f,0.5f,0f );
        gl.glVertex3f( 0.5f,0.2f,0f );
        gl.glVertex3f( 0.5f,-0.2f,0f );
        gl.glVertex3f( 0f,-0.5f,0f );

        gl.glEnd();

        gl.glFlush();

        rpoly += 0.2f;  //assigning the angle

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable( GL2.GL_LIGHT0 );
        gl.glEnable( GL2.GL_NORMALIZE );

        float[] ambientLight = {1f, 0f, 0f, 0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

        float[] specularLight = {1f, 0f, 0f, 0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specularLight, 0);


        float[] diffuseLight = {1f, 0f, 0f, 0f};
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
    }

    @Override
    public void dispose( GLAutoDrawable arg0 ) {
        //method body
    }

    @Override
    public void init( GLAutoDrawable arg0 ) {
        // method body
    }

    @Override
    public void reshape( GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4 ) {
        // method body
    }

    public static void main( String[] args ) {

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get( GLProfile.GL2 );
        GLCapabilities capabilities = new GLCapabilities( profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas( capabilities );
        PolygonLighting polygonlighting = new PolygonLighting();
        glcanvas.addGLEventListener( polygonlighting );
        glcanvas.setSize( 400, 400 );

        //creating frame
        final JFrame frame = new JFrame (" Polygon lighting ");

        //adding canvas to it
        frame.getContentPane().add( glcanvas );
        frame.setSize( frame.getContentPane().getPreferredSize());
        frame.setVisible( true );

        //Instantiating and Initiating Animator
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true );
        animator.start();

    } //end of main

} //end of class