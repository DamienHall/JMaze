package com.github.LilZcrazyG;

import com.github.LilZcrazyG.mazeCore.MazeGenerator;
import com.github.LilZcrazyG.tools.Graphics;
import com.github.LilZcrazyG.tools.Window;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    // variables
    static final int CELLSIZE = 3*2, TICKSPEED = 1000/60;
    static Window WINDOW = new Window( 520, 520, "Maze Window", false );
    static Config configWindow = new Config();
    static MazeGenerator mazeGenerator;
    static boolean running = true;
    static Timer timer = new Timer();
    static TimerTask task = new TimerTask() {
        public void run() {
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
    };

    public static void start( boolean saveAsBinary, boolean saveAsJSON, boolean saveAsGIF, boolean saveAsJPEG, int mazeType, int rows, int columns, int cellSize, Window window ) {
        Graphics.initialize( WINDOW );
        Graphics.setBackgroundColor( Color.BLACK );
        WINDOW.setVisibility( true );
        mazeGenerator = new MazeGenerator( saveAsBinary, saveAsJSON, saveAsGIF, saveAsJPEG, mazeType, rows, columns, cellSize, window );
        timer.scheduleAtFixedRate(task, 0, TICKSPEED);
        while (running) {
            render();
        }
    }

    public static void tick() throws Exception {
        mazeGenerator.tick();
    }

    public static void render() {
        Graphics.clearScreen();
        mazeGenerator.render();
        Graphics.show();
    }
}

