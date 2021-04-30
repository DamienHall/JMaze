package com.github.LilZcrazyG.generationMethods;

import com.github.LilZcrazyG.mazeCore.Cell;
import com.github.LilZcrazyG.mazeCore.Grid;
import com.github.LilZcrazyG.mazeCore.Save;
import com.github.LilZcrazyG.tools.Graphics;
import com.github.LilZcrazyG.tools.Utilities;
import com.github.LilZcrazyG.tools.Window;

import java.awt.*;
import java.awt.event.WindowEvent;

public class HuntAndKill {

    // private final variables
    private final int DEFAULT_MAX_WALK_DISTANCE = 10;
    private final int MIN = 100, MAX = 255;
    // private variables
    private boolean renderOnly;
    private Window window;
    private Grid grid;
    private Cell currentCell;
    private int maxWalkDistance = 0;
    private int[] target = new int[] { 0, 0 };
    private int[] focus = new int[] { 0, 0 };
    private double visitedCells;
    private double totalCells;
    private int completion;

    /**
     * Constructor for the Hunt And Kill method.
     * @param window The window to draw too.
     * @param cellSize The size of each cell.
     */
    public HuntAndKill( Window window, int cellSize ) {
        this.window = window;
        this.grid = new Grid( window, cellSize );
        this.renderOnly = false;
        this.maxWalkDistance = DEFAULT_MAX_WALK_DISTANCE;
        this.currentCell = this.grid.getCell( 0, 0 );
        this.window.setTitle("Hunt And Kill");
        this.focus[1] = grid.getColumns()/2;
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
    }

    public HuntAndKill( Window window, int rows, int columns, int cellSize ) {
        this.window = window;
        this.grid = new Grid( rows, columns, cellSize );
        this.renderOnly = false;
        this.maxWalkDistance = DEFAULT_MAX_WALK_DISTANCE;
        this.currentCell = this.grid.getCell( 0, 0 );
        this.window.setTitle("Hunt And Kill");
        this.focus[1] = grid.getColumns()/2;
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
    }

    /**
     * Constructor for the Hunt And Kill method.
     * @param window The window to use when rendering.
     * @param cellSize The size of each cell.
     * @param maxWalkDistance The max walk distance for the inner Depth First Search method.
     */
    public HuntAndKill( Window window, int cellSize, int maxWalkDistance ) {
        this.window = window;
        this.grid = new Grid( window, cellSize );
        this.renderOnly = false;
        this.maxWalkDistance = maxWalkDistance;
        this.currentCell = this.grid.getCell( 0, 0 );
        this.window.setTitle("Hunt And Kill");
        this.focus[1] = grid.getColumns()/2;
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
    }

    /**
     * Does a random walk similar to Depth First Search.
     */
    private void randomWalk() {
        int distanceWalked = 1;
        Color randomFillColor = Utilities.getRandomColor(MIN,MAX);
        while (currentCell.getNeighbors()[1]>0&&distanceWalked<maxWalkDistance) {
            distanceWalked++;
            currentCell.setVisited(true);
            double oldCompletion = completion;
            visitedCells += 1;
            completion = (int) Math.round((visitedCells/totalCells)*100);
            if ( completion > oldCompletion ) {
                this.window.setTitle("Hunt And Kill "+completion+"%");
            }
            //currentCell.setFillColor(randomFillColor);
            Cell nextCell = currentCell.getRandomNeighbor().get(1);
            if (nextCell!=null) {
                nextCell.setVisited(true);
                grid.removeWalls( currentCell, nextCell );
                currentCell = nextCell;
            }
        }
        //currentCell.setFillColor(randomFillColor);
    }

    /**
     * Hunts for the next cell to base the random walk on.
     * @return the cell that is going to be used for the random walk.
     */
    private Cell hunt() {
        for (int row = target[0]; row < grid.getRows(); row++) {
            for (int column = target[1]; column < grid.getColumns(); column++) {
                Cell selectedCell = grid.getCell(row, column);
                if (!selectedCell.hasBeenVisited()) {
                    target = new int[] { row, column };
                    selectedCell.setVisited(true);
                    double oldCompletion = completion;
                    visitedCells += 1;
                    completion = (int) Math.round((visitedCells/totalCells)*100);
                    if ( completion > oldCompletion ) {
                        this.window.setTitle("Hunt And Kill "+completion+"%");
                    }
                    return selectedCell;
                }
            }
            target[1] = 0;
        }
        return null;
    }

    /**
     * Ticks the maze.
     */
    public void tick() {
        if (!renderOnly) {
            currentCell.getNeighbors();
            if (currentCell != null && currentCell.getUNArray().size()>0) {
                randomWalk();
            } else {
                //currentCell.setFillColor(Utilities.getRandomColor(MIN,MAX));
            }
            currentCell = hunt();
            if (currentCell != null) {
                if (currentCell.getNeighbors()[2]>0) {
                    grid.removeWalls(currentCell, currentCell.getRandomNeighbor().get(2));
                }
            } else {
                renderOnly = true;
                Save.imageToNewFile("HuntAndKill"+grid.getRows()+","+grid.getColumns(), Save.asImage( grid.getRows()*grid.getCellSize(), grid.getColumns()*grid.getCellSize(), grid ) );
                Save.stringToNewFile( "HuntAndKill"+grid.getRows()+","+grid.getColumns()+"BIN", Save.asBinary( grid ));
                Save.stringToNewFile( "HuntAndKill"+grid.getRows()+","+grid.getColumns()+".json", Save.asJSON( grid ));
                window.getSelf().dispatchEvent(new WindowEvent(window.getSelf(), WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    /**
     * Renders the maze.
     */
    public void render() {
        if ( currentCell != null ) {
            if ( currentCell.getPosition()[0] > 0 && currentCell.getPosition()[0] < grid.getRows() ) {
                focus[0] = currentCell.getPosition()[0];
            }
            grid.render( focus[0], focus[1], (window.getWidth()/grid.getCellSize())/2 );
        } else {
            grid.render();
        }
    }
}
