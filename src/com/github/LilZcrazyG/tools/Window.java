package com.github.LilZcrazyG.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window {

    // final variables
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String DEFAULT_TITLE = "Untitled window";
    private static final Boolean DEFAULT_SHOW_WINDOW = true;
    private static final int DEFAULT_CLOSE_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    // fluid variables
    private int width, height;
    private String title;
    private Boolean showWindow;
    private int defaultCloseOperation;
    private JFrame window = new JFrame();
    private JPanel panel = new JPanel();
    private Canvas canvas = null;

    // class initiation
    // default initiator
    public Window() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        this.title = DEFAULT_TITLE;
        this.showWindow = DEFAULT_SHOW_WINDOW;
        this.defaultCloseOperation = DEFAULT_CLOSE_OPERATION;

        initDisplay();
    }

    // size initiator
    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        this.title = DEFAULT_TITLE;
        this.showWindow = DEFAULT_SHOW_WINDOW;
        this.defaultCloseOperation = DEFAULT_CLOSE_OPERATION;

        initDisplay();
    }

    // size and title initiator
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.showWindow = DEFAULT_SHOW_WINDOW;
        this.defaultCloseOperation = DEFAULT_CLOSE_OPERATION;

        initDisplay();
    }

    // size, title and visibility initiator
    public Window(int width, int height, String title, Boolean showWindow) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.showWindow = showWindow;
        this.defaultCloseOperation = DEFAULT_CLOSE_OPERATION;

        initDisplay();
    }

    public Window(int width, int height, String title, int rows, int columns ) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.showWindow = true;
        this.defaultCloseOperation = DEFAULT_CLOSE_OPERATION;

        initDisplay( rows, columns );
    }

    // initiate display
    private void initDisplay() {
        window.setSize(width, height);
        window.setTitle(title);
        window.setDefaultCloseOperation(defaultCloseOperation);
        window.setVisible(showWindow);
        window.setResizable(false);
        window.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent e ) {
                System.out.println("SLFJ:LSDKJF");
                System.exit(0);
            }
        });

        // setup panel
        panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panel.setLayout(new GridLayout( 0, 1));
        window.add(panel);
    }
    private void initDisplay( int rows, int columns ) {
        window.setSize(width, height);
        window.setTitle(title);
        window.setDefaultCloseOperation(defaultCloseOperation);
        window.setVisible(showWindow);
        window.setResizable(false);

        // setup panel
        panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panel.setLayout(new GridLayout( rows, columns));
        window.add(panel);
    }

    // class methods
    // add canvas
    public void addCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        panel.add(canvas);
        window.pack();
    }

    public void addCanvas( int width, int height ) {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        panel.add(canvas);
    }

    // add a JComponent
    public void add(JComponent jComponent) {
        window.add(jComponent);
    }

    // add to JPanel
    public void addToJPanel( JComponent jComponent ) {
        panel.add( jComponent );
    }

    // pack
    public void packWindow() {
        window.pack();
    }

    // Getters
    // return the x and y in an int array
    public int[] getSize() {
        return new int[] {width, height};
    }

    // return width only
    public int getWidth() {
        return width;
    }

    // return height only
    public int getHeight() {
        return height;
    }

    // return the title of the window
    public String getTitle() {
        return title;
    }

    // return the visibility variable of the window
    public Boolean getVisibility() {
        return showWindow;
    }

    // set the visibility
    public void setVisibility( Boolean visibility ) {
        showWindow = visibility;
        window.setVisible( visibility );
    }

    // return itself
    public JFrame getSelf() {
        return window;
    }

    // return canvas
    public Canvas getCanvas() {
        return canvas;
    }

    // setters
    // set the title of the window
    public void setTitle( String title ) { this.window.setTitle(title); }

}