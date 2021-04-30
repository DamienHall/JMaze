package com.github.LilZcrazyG.mazeCore;

import com.github.LilZcrazyG.generationMethods.DepthFirstSearch;
import com.github.LilZcrazyG.generationMethods.HuntAndKill;
import com.github.LilZcrazyG.generationMethods.RandomisedKruskalsAlgorithm;
import com.github.LilZcrazyG.generationMethods.RandomisedPrimsAlgorithm;
import com.github.LilZcrazyG.tools.Window;

public class MazeGenerator {

    // private variables
    private int genMethod = 0, cellSize, rows, columns;
    private DepthFirstSearch DFS;
    private HuntAndKill HAK;
    private RandomisedPrimsAlgorithm RPA;
    private RandomisedKruskalsAlgorithm RKA;
    private Window window;

    /**
     * Constructor for the Maze Generator.
     * @param generationType The type of maze to generate.
     * @param cellSize The size of each cell.
     * @param window The window to use when rendering.
     */
    public MazeGenerator( int generationType, int cellSize, Window window ) {
        this.genMethod = generationType;
        this.cellSize = cellSize;
        this.window = window;
        this.rows = window.getWidth()/cellSize;
        this.columns = window.getHeight()/cellSize;
        generate();
    }

    public MazeGenerator( int generationType, int rows, int columns, int cellSize, Window window ) {
        this.genMethod = generationType;
        this.cellSize = cellSize;
        this.window = window;
        this.rows = rows;
        this.columns = columns;
        generate();
    }

    /**
     * Stes the generation type.
     * @param genMethod The generation type.
     */
    public void setGenerationType( int genMethod ) {
        // 0 = Depth First Search
        // 1 = Hunt And Kill
        // 2 = Randomized Prims Algorithm
        // 3 = Randomized Kruskals Algorithm
        // 4 = Aldous Broder Algorithm

        this.genMethod = genMethod;
    }

    /**
     * Generates the maze.
     */
    public void generate() {
        switch (genMethod) {
            case (0) -> DFS = new DepthFirstSearch(window, this.rows, this.columns, cellSize);
            case (1) -> HAK = new HuntAndKill(window, this.rows, this.columns, cellSize);
            case (2) -> RPA = new RandomisedPrimsAlgorithm(window, this.rows, this.columns, cellSize);
            case (3) -> RKA = new RandomisedKruskalsAlgorithm(window, this.rows, this.columns, cellSize);
        }
    }

    /**
     * Ticks the maze.
     */
    public void tick() throws Exception {
        switch (genMethod) {
            case (0) -> DFS.tick();
            case (1) -> HAK.tick();
            case (2) -> RPA.tick();
            case (3) -> RKA.tick();
        }
    }

    /**
     * Renders the maze.
     */
    public void render() {
        switch (genMethod) {
            case (0) -> DFS.render();
            case (1) -> HAK.render();
            case (2) -> RPA.render();
            case (3) -> RKA.render();
        }
    }
}
