package com.github.LilZcrazyG.mazeCore;

import com.github.LilZcrazyG.tools.Graphics;
import com.github.LilZcrazyG.tools.Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Cell {

    // private static final variables
    private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.PINK;
    private static final Color DEFAULT_FILL_COLOR = Color.WHITE;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
    // private static variables
    private static Grid grid;
    // private variables
    private int x, y, indexX, indexY, weight, setID;
    private ArrayList<Cell> neighbors = new ArrayList<>();
    private ArrayList<Cell> unvisitedNeighbors = new ArrayList<>();
    private ArrayList<Cell> visitedNeighbors = new ArrayList<>();
    private boolean[] walls = new boolean[] { true, true, true, true }; // NESW order
    private int[] bias = new int[] { 1, 1, 1, 1 }; // NESW order
    private boolean visited, inStack, highlighted;
    private Color backgroundColor = Color.BLACK, fillColor = Color.WHITE;

    /**
     * The constructor for the Cell.
     * @param x The X value (as an index value, not pixel).
     * @param y The Y value (as an index value, not pixel).
     * @param GRID The Grid that this cell will be added to.
     */
    public Cell( int x, int y, Grid GRID ) {
        this.x = x*GRID.getCellSize();
        this.y = y*GRID.getCellSize();
        this.indexX = x;
        this.indexY = y;
        this.visited = false;
        this.inStack = false;
        grid = GRID;
    }

    /**
     * Gets the current neighbors and adds them to their proper lists.
     * @return an int array, each one being the size of an array.
     *      The order is total, unvisited, and visited neighbors.
     */
    public int[] getNeighbors() {
        neighbors.clear();
        unvisitedNeighbors.clear();
        visitedNeighbors.clear();
        Cell north = grid.getCell( indexX, indexY - 1 );
        Cell east = grid.getCell( indexX + 1, indexY );
        Cell south = grid.getCell( indexX, indexY + 1 );
        Cell west = grid.getCell( indexX - 1, indexY );
        neighbors.addAll( Arrays.asList( north, east, south, west ) );
        if (north!=null) {
            if (!north.hasBeenVisited()) {
                this.unvisitedNeighbors.add(north);
            } else {
                this.visitedNeighbors.add(north);
            }
        }
        if (east!=null) {
            if (!east.hasBeenVisited()) {
                this.unvisitedNeighbors.add(east);
            } else {
                this.visitedNeighbors.add(east);
            }
        }
        if (south!=null) {
            if (!south.hasBeenVisited()) {
                this.unvisitedNeighbors.add(south);
            } else {
                this.visitedNeighbors.add(south);
            }
        }
        if (west!=null) {
            if (!west.hasBeenVisited()) {
                this.unvisitedNeighbors.add(west);
            } else {
                this.visitedNeighbors.add(west);
            }
        }
        return new int[] { neighbors.size(), unvisitedNeighbors.size(), visitedNeighbors.size() };
    }

    /**
     * Checks if the cell has been visited.
     * @return a boolean
     */
    public boolean hasBeenVisited() {
        return this.visited;
    }

    /**
     * Get the neighbors array.
     * @return the neighbors array.
     */
    public ArrayList<Cell> getNArray() {
        return neighbors;
    }

    /**
     * Get the unvisited neighbors array.
     * @return the unvisited neighbors array.
     */
    public ArrayList<Cell> getUNArray() {
        return unvisitedNeighbors;
    }

    /**
     * Get the visited neighbors array.
     * @return the visited neighbors array.
     */
    public ArrayList<Cell> getVNArray() {
        return visitedNeighbors;
    }

    /**
     * Get a random cell from each array of cells.
     * @return an ArrayList of three random cells.
     *      The order is total, unvisited, and visited neighbors.
     */
    public ArrayList<Cell> getRandomNeighbor() {
        Cell randomNeighbor, randomUnvisitedNeighbor, randomVisitedNeighbor;
        if (neighbors.size()>0) {
            randomNeighbor = neighbors.get( Utilities.randomInt( 0, neighbors.size() - 1 ) );
        } else {
            randomNeighbor = null;
        }
        if (unvisitedNeighbors.size()>0) {
            randomUnvisitedNeighbor = unvisitedNeighbors.get( Utilities.randomInt( 0, unvisitedNeighbors.size() - 1 ) );
        } else {
            randomUnvisitedNeighbor = null;
        }
        if (visitedNeighbors.size()>0) {
            randomVisitedNeighbor = visitedNeighbors.get( Utilities.randomInt( 0, visitedNeighbors.size() - 1 ) );
        } else {
            randomVisitedNeighbor = null;
        }
        return new ArrayList<Cell>( Arrays.asList( randomNeighbor, randomUnvisitedNeighbor, randomVisitedNeighbor ) );
    }

    /**
     * Get the pixel position of the cell.
     * @return an int[] where int[0] is x, and int[1] is y in pixel terms.
     */
    public int[] getPixelPosition() {
        return new int[] { x, y };
    }

    /**
     * Get the index position of the cell.
     * @return an int[] where int[0] is x, and int[1] is y in index terms.
     */
    public int[] getPosition() {
        return new int[] { indexX, indexY };
    }

    /**
     * Gets the walls array.
     * @return the walls array.
     */
    public boolean[] getWallsArray() {
        return walls;
    }

    /**
     * Gets the weight of the cell.
     * @return the cells weight.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets whether or not a cell has been visited.
     * @param beenVisited The boolean variable stating whether or not a cell has been visited.
     */
    public void setVisited( boolean beenVisited ) {
        visited = beenVisited;
    }

    /**
     * Sets the bias of the cell.
     * @param northBias The bias in the North direction.
     * @param eastBias The bias in the East direction.
     * @param southBias The bias in the South direction.
     * @param westBias The bias in the West direction.
     */
    public void setBias( int northBias, int eastBias, int southBias, int westBias ) {
        bias = new int[] { northBias, eastBias, southBias, westBias };
    }

    /**
     * Sets the weight of a cell.
     * @param weight The weight of the cell.
     */
    public void setWeight( int weight ) {
        this.weight = weight;
    }

    /**
     * Sets the background color of the cell.
     * @param color The color to set as the background.
     */
    public void setBackgroundColor( Color color ) {
        this.backgroundColor = color;
    }

    /**
     * Sets whether or not a cell is in the stack.
     * @param isInStack The boolean variable stating whether or not a cell is in the stack.
     */
    public void setInStack(boolean isInStack) {
        inStack = isInStack;
    }

    /**
     * Sets the fill color of this cell.
     * @param fillColor The color to fill this cell in.
     */
    public void setFillColor( Color fillColor ) {
        this.fillColor = fillColor;
    }

    /**
     * Gets the set that this cell is in.
     * @return the set this cell is in as an int.
     */
    public int getSetID() {
        return setID;
    }

    /**
     * Sets the set that this cell is in.
     * @param setID The set ID this cell is in.
     */
    public void setSetID( int setID ) {
        this.setID = setID;
    }

    /**
     * Highlights the cell.
     */
    public void highlight() {
        highlighted = true;
    }

    /**
     * Unhighlights the cell.
     */
    public void unhighlight() {
        highlighted = false;
    }

    /**
     * Checks if the cell is highlighted.
     * @return a boolean, true if the cell is highlighted.
     */
    public boolean isHighlighted() {
        return highlighted;
    }

    /**
     * Renders the cell.
     */
    public void render() {
        int size = grid.getCellSize();
        if (highlighted) {
            com.github.LilZcrazyG.tools.Graphics.setColor(DEFAULT_HIGHLIGHT_COLOR);
            com.github.LilZcrazyG.tools.Graphics.rectangleFilled( x, y, size, size);
            drawWalls();
        } else if (visited) {
            com.github.LilZcrazyG.tools.Graphics.setColor(fillColor!=null?fillColor:DEFAULT_FILL_COLOR);
            com.github.LilZcrazyG.tools.Graphics.rectangleFilled( x, y, size, size);
            drawWalls();
        } else {
            com.github.LilZcrazyG.tools.Graphics.setColor(backgroundColor!=null?backgroundColor:DEFAULT_BACKGROUND_COLOR);
            com.github.LilZcrazyG.tools.Graphics.rectangleFilled( x, y, size, size);
        }
    }

    /**
     * Renders the cell.
     * @param g The graphics2d object to draw too.
     */
    public void render( Graphics2D g ) {
        int size = grid.getCellSize();
        if (highlighted) {
            g.setColor(DEFAULT_HIGHLIGHT_COLOR);
            g.fillRect( x, y, size, size);
            drawWalls( g );
        } else if (visited) {
            g.setColor(fillColor!=null?fillColor:DEFAULT_FILL_COLOR);
            g.fillRect( x, y, size, size);
            drawWalls( g );
        } else {
            g.setColor(backgroundColor!=null?backgroundColor:DEFAULT_BACKGROUND_COLOR);
            g.fillRect( x, y, size, size);
        }
    }

    /**
     * renders the cell walls.
     */
    private void drawWalls() {
        int size = grid.getCellSize();
        Graphics.setColor( Color.BLACK );
        if (this.walls[0]) {
            Graphics.line(x, y, x + size, y);
        }
        if (this.walls[1]) {
            Graphics.line(x-1 + size, y, x-1 + size, y + size);
        }
        if (this.walls[2]) {
            Graphics.line(x + size, y-1 + size, x, y-1 + size);
        }
        if (this.walls[3]) {
            Graphics.line(x, y-1 + size, x, y-1);
        }
    }

    /**
     * renders the cell walls.
     */
    private void drawWalls( Graphics2D g ) {
        int size = grid.getCellSize();
        g.setColor( Color.BLACK );
        if (this.walls[0]) {
            g.drawLine(x, y, x + size, y);
        }
        if (this.walls[1]) {
            g.drawLine(x-1 + size, y, x-1 + size, y + size);
        }
        if (this.walls[2]) {
            g.drawLine(x + size, y-1 + size, x, y-1 + size);
        }
        if (this.walls[3]) {
            g.drawLine(x, y-1 + size, x, y-1);
        }
    }
}