package com.github.LilZcrazyG.tools;

import java.awt.*;
import java.awt.image.BufferStrategy;

public interface GraphicsInterface {
    // variables
    Window window = null;
    BufferStrategy bufferStrategy = null;
    Graphics2D graphics = null;
    boolean running = true;
}