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
    static Window WINDOW;
    static final GUI GUI = new GUI( 250, 600, "GUI TESTING" );
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

    public static void start() {
        Graphics.initialize( WINDOW );
        Graphics.setBackgroundColor( Color.BLACK );
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

