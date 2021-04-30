package com.github.LilZcrazyG.generationMethods;

import com.github.LilZcrazyG.mazeCore.Cell;
import com.github.LilZcrazyG.mazeCore.Grid;
import com.github.LilZcrazyG.mazeCore.Save;
import com.github.LilZcrazyG.tools.Utilities;
import com.github.LilZcrazyG.tools.Window;
import com.github.LilZcrazyG.tools.Graphics;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DepthFirstSearch {

    // private variables
    private boolean renderOnly, solutionFound;
    private Window window;
    private Grid grid;
    private Cell currentCell;
    private ArrayList<Cell> solution = new ArrayList<>();
    private double visitedCells;
    private double totalCells;
    private int completion;

    /**
     * Constructor for the Depth First Search method.
     * @param window The window to use when rendering.
     * @param cellSize The size of each cell.
     */
    public DepthFirstSearch( Window window, int cellSize ) {
        this.window = window;
        this.grid = new Grid( window, cellSize );
        this.renderOnly = false;
        this.solutionFound = false;
        this.currentCell = this.grid.getCell( 0, 0 );
        this.currentCell.highlight();
        this.window.setTitle("Depth First Search");
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
    }

    public DepthFirstSearch( Window window, int rows, int columns, int cellSize ) {
        this.window = window;
        this.grid = new Grid( rows, columns, cellSize );
        this.renderOnly = false;
        this.solutionFound = false;
        this.currentCell = this.grid.getCell( 0, 0 );
        this.currentCell.highlight();
        this.window.setTitle("Depth First Search");
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
    }

    /**
     * Checks if a solution has been found during the creation of the maze.
     * @return a boolean, true if a solution has been found, false otherwise.
     */
    public boolean isSolutionFound() {
        return currentCell == grid.getCell(grid.getRows() - 1, grid.getColumns() - 1) && !solutionFound;
    }

    /**
     * Ticks the maze.
     */
    public void tick() {
        if (!renderOnly) {
            if (isSolutionFound()) {
                solution.addAll(grid.getStackArray());
                solution.add(grid.getCell(grid.getRows()-1,grid.getColumns()-1));
            }
            currentCell.setVisited(true);
            double oldCompletion = completion;
            visitedCells += 0.5;
            completion = (int) Math.round((visitedCells/totalCells)*100);
            if ( completion > oldCompletion ) {
                this.window.setTitle("Depth First Search "+completion+"%");
            }
            currentCell.setBias(1,1,1,1);
            currentCell.getNeighbors();
            Cell nextCell = currentCell.getRandomNeighbor().get(1);
            if (nextCell != null) {
                nextCell.highlight();
                grid.getStackArray().add(currentCell);
                currentCell.setInStack(true);
                //currentCell.highlight();
                grid.removeWalls(currentCell, nextCell);
                currentCell = nextCell;
            } else if (grid.getStackArray().size()>0) {
                currentCell.setInStack(false);
                currentCell.unhighlight();
                currentCell = grid.getStackArray().remove(grid.getStackArray().size()-1);
            } else {
                currentCell.unhighlight();
                renderOnly = true;
                for (Cell cell : solution) {
                    cell.highlight();
                }
                Save.imageToNewFile("DepthFirstSearch"+grid.getRows()+","+grid.getColumns(), Save.asImage( grid.getRows()*grid.getCellSize(), grid.getColumns()*grid.getCellSize(), grid ) );
                window.getSelf().dispatchEvent(new WindowEvent(window.getSelf(), WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    /**
     * Renders the maze.
     */
    public void render() {
        grid.render( currentCell.getPosition()[0], currentCell.getPosition()[1], (window.getWidth()/grid.getCellSize())/2 );
    }

}
