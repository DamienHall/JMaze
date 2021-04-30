package com.github.LilZcrazyG.tools;

import com.github.LilZcrazyG.tools.Window;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;


public class Graphics {

    // static variables
    private static Window window;
    private static BufferStrategy bufferStrategy;
    private static Graphics2D graphics;
    private static BufferedImage bufferedImage;

    public static void initialize( Window win, int width, int height ) {
        window = win;
        if ( window.getCanvas() == null ) {
            window.addCanvas( width, height );
        }
        bufferStrategy = window.getCanvas().getBufferStrategy();
        if ( bufferStrategy == null ) {
            window.getCanvas().createBufferStrategy( 3 );
            bufferStrategy = window.getCanvas().getBufferStrategy();
        }
        graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
    }
    public static void initialize( Window win ) {
        window = win;
        if ( window.getCanvas() == null ) {
            window.addCanvas();
            System.out.println("Canvas created");
        }
        bufferStrategy = window.getCanvas().getBufferStrategy();
        if ( bufferStrategy == null ) {
            window.getCanvas().createBufferStrategy( 3 );
            bufferStrategy = window.getCanvas().getBufferStrategy();
        }
        graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    public static Graphics2D getGraphics() {
        return graphics;
    }

    public static BufferedImage createImage( int width, int height ) {
        bufferedImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
        return bufferedImage;
    }

    public static Graphics2D getImageGraphics() {
        return (Graphics2D) bufferedImage.getGraphics();
    }

    public static void show() {
        bufferStrategy.show();
    }

    public static void dispose() {
        bufferStrategy.dispose();
    }

    public static AffineTransform translate( int x, int y ) {
        AffineTransform oldTransform = graphics.getTransform();
        graphics.translate( x, y );
        return oldTransform;
    }

    public static AffineTransform resetTranslation( AffineTransform transform ) {
        AffineTransform oldTransform = graphics.getTransform();
        graphics.translate( -transform.getTranslateX(), -transform.getTranslateY() );
        return oldTransform;
    }

    public static void scale( double sx, double sy ) {
        graphics.scale( sx, sy );
    }

    public static void clearRect( int x, int y, int width, int height ) {
        graphics.clearRect( x, y, width, height );
    }

    public static void clearScreen() {
        graphics.clearRect( 0, 0, window.getWidth(), window.getHeight() );
    }

    public static Color setColor( Color color ) {
        Color oldColor = graphics.getColor();
        graphics.setColor( color );
        return oldColor;
    }

    public static Color setBackgroundColor( Color color ) {
        Color oldColor = graphics.getBackground();
        graphics.setBackground( color );
        return oldColor;
    }

    public static void text( int x, int y, String str ) {
        graphics.drawString( str, x, y );
    }

    public static void line( int x1, int y1, int x2, int y2 ) {
        graphics.drawLine( x1, y1, x2, y2 );
    }

    public static void rectangle( int x, int y, int width, int height ) {
        graphics.drawRect( x, y, width, height );
    }

    public static void rectangleFilled( int x, int y, int width, int height ) {
        graphics.fillRect( x, y, width, height );
    }

    public static void oval( int x, int y, int width, int height ) {
        graphics.drawOval( x, y, width, height );
    }

    public static void ovalFilled( int x, int y, int width, int height ) {
        graphics.fillOval( x, y, width, height );
    }

    public static void circle( int x, int y, int radius ) {
        graphics.drawOval( x, y, radius*2, radius*2 );
    }

    public static void circleFilled( int x, int y, int radius ) {
        graphics.fillOval( x, y, radius*2, radius*2 );
    }
}
