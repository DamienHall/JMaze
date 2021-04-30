package com.github.LilZcrazyG.generationMethods;

import com.github.LilZcrazyG.mazeCore.Cell;
import com.github.LilZcrazyG.mazeCore.Grid;
import com.github.LilZcrazyG.mazeCore.Save;
import com.github.LilZcrazyG.tools.Graphics;
import com.github.LilZcrazyG.tools.Utilities;
import com.github.LilZcrazyG.tools.Window;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class RandomisedPrimsAlgorithm {

    // private variables
    private boolean renderOnly;
    private Window window;
    private Grid grid;
    private Cell currentCell;
    private ArrayList<Cell> candidates = new ArrayList<>();
    private double visitedCells;
    private double totalCells;
    private int completion;
    private int[] focus = new int[] { 0, 0 };
    private int[] oldFocus = new int[] { 0, 0 };

    /**
     * Constructor for the Randomised Prims Algorithm.
     * @param window The window to use when rendering.
     * @param cellSize The size of each cell.
     */
    public RandomisedPrimsAlgorithm(Window window, int cellSize ) {
        this.window = window;
        this.grid = new Grid( window, cellSize );
        this.renderOnly = false;
        this.currentCell = this.grid.getCell( this.grid.getRows()/2, this.grid.getColumns()/2 );
        this.window.setTitle("Randomised Prims Algorithm");
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
        this.setCellWeights();
        this.setCandidates();
    }

    public RandomisedPrimsAlgorithm(Window window, int rows, int columns, int cellSize ) {
        this.window = window;
        this.grid = new Grid( rows, columns, cellSize );
        this.renderOnly = false;
        this.currentCell = this.grid.getCell( this.grid.getRows()/2, this.grid.getColumns()/2 );
        this.focus = this.currentCell.getPosition();
        this.window.setTitle("Randomised Prims Algorithm");
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
        this.setCellWeights();
        this.setCandidates();
    }

    /**
     * Sets the weights for every cell on the grid.
     */
    private void setCellWeights() {
        for ( int x = 0; x < grid.getRows(); x++ ) {
            for ( int y = 0; y < grid.getColumns(); y++ ) {
                Cell cell = grid.getCell( x, y );
                cell.setWeight( Utilities.randomInt( 0, 255 ) );
                cell.setBackgroundColor( new Color( cell.getWeight(), cell.getWeight(), cell.getWeight() ) );
            }
        }
    }

    /**
     * Sets the candidates.
     */
    private void setCandidates() {
        int unvisitedNeighborCount = currentCell.getNeighbors()[1];
        ArrayList<Cell> unvisitedNeighbors = currentCell.getUNArray();
        for ( int i = 0; i < unvisitedNeighborCount; i++ ) {
            //unvisitedNeighbors.get(i).setBackgroundColor( new Color( 255, 182, 193 ) );
            if ( !candidates.contains( unvisitedNeighbors.get(i) ) ) {
                candidates.add( unvisitedNeighbors.get(i) );
            }
        }
    }

    /**
     * Gets the lowest weighted candidate in the candidates array list.
     * @return the lowest weighted candidate.
     */
    private Cell getLowestWeightedCandidate() {
        Cell lowestWeightedCandidate = candidates.get(0);
        for ( int i = 0; i < candidates.size(); i++ ) {
            if ( candidates.get(i).getWeight() < lowestWeightedCandidate.getWeight() ) {
                lowestWeightedCandidate = candidates.get(i);
            }
        }
        return lowestWeightedCandidate;
    }

    /**
     * Ticks the maze.
     */
    public void tick() {
        if (!renderOnly) {
            currentCell.setVisited(true);
            double oldCompletion = completion;
            visitedCells += 1;
            completion = (int) Math.round((visitedCells/totalCells)*100);
            if ( completion > oldCompletion ) {
                window.setTitle("Randomised Prims Algorithm "+completion+"%");
            }

            if ( currentCell.getNeighbors()[2] > 0 ) {
                grid.removeWalls( currentCell, currentCell.getRandomNeighbor().get(2) );
            }
            setCandidates();
            if ( candidates.size() > 0 ) {
                Cell lowestWeightedCandidate = getLowestWeightedCandidate();
                candidates.remove( lowestWeightedCandidate );
                currentCell = lowestWeightedCandidate;
            } else {
                renderOnly = true;
                Save.imageToNewFile("RandomisedPrimsAlgorithm"+grid.getRows()+","+grid.getColumns(), Save.asImage( grid.getRows()*grid.getCellSize(), grid.getColumns()*grid.getCellSize(), grid ) );
                window.getSelf().dispatchEvent(new WindowEvent(window.getSelf(), WindowEvent.WINDOW_CLOSING));
            }
            if ( grid.getCell( currentCell.getPosition()[0] + (window.getWidth()/grid.getCellSize())/2, currentCell.getPosition()[1] ) != null ) {
                if ( grid.getCell( currentCell.getPosition()[0] - (window.getWidth()/grid.getCellSize())/2, currentCell.getPosition()[1] ) != null ) {
                    focus[0] = currentCell.getPosition()[0];
                }
            }
            if ( grid.getCell( currentCell.getPosition()[0], currentCell.getPosition()[1] + (window.getWidth()/grid.getCellSize())/2 ) != null ) {
                if ( grid.getCell( currentCell.getPosition()[0], currentCell.getPosition()[1] - (window.getWidth()/grid.getCellSize())/2 ) != null ) {
                    focus[1] = currentCell.getPosition()[1];
                }
            }
        }
    }

    /**
     * Renders the maze.
     */
    public void render() {
        grid.render( focus[0], focus[1], (window.getWidth() / grid.getCellSize()) / 2);
    }
}
