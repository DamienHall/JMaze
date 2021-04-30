package com.github.LilZcrazyG.mazeCore;

import com.github.LilZcrazyG.tools.Window;
import com.github.LilZcrazyG.tools.Graphics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Grid {

    // private variables
    private int columns, rows, cellSize;
    private ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
    private ArrayList<Cell> stack = new ArrayList<>();

    /**
     * Constructs the grid.
     * @param rows The amount of rows in the grid.
     * @param columns The amount of columns in the grid.
     * @param cellSize The size of each cell within the grid.
     */
    public Grid( int rows, int columns, int cellSize ) {
        this.rows = rows;
        this.columns = columns;
        this.cellSize = cellSize;
        this.createCells();
    }

    /**
     * Constructs the grid.
     * @param window The window to base grid cell size off of.
     * @param cellSize The size of each cell within the grid.
     */
    public Grid(Window window, int cellSize ) {
        this.rows = window.getWidth() / cellSize;
        this.columns = window.getHeight() / cellSize;
        this.cellSize = cellSize;
        this.createCells();
    }

    /**
     * Creates all the cells needed to fill the grid.
     */
    public void createCells() {
        for ( int x = 0; x < rows; x++ ) {
            cells.add( new ArrayList<Cell>() );
            for ( int y = 0; y < columns; y++ ) {
                cells.get( x ).add( new Cell( x, y, this ) );
            }
        }
    }

    /**
     * Removes the wall between two cells.
     * @param a The first cell.
     * @param b The second cell.
     */
    public void removeWalls( Cell a, Cell b ) {
        int[] aPos = a.getPixelPosition();
        int[] bPos = b.getPixelPosition();
        int x = aPos[0]/cellSize-bPos[0]/cellSize;
        int y = aPos[1]/cellSize-bPos[1]/cellSize;
        if (x==1) {
            a.getWallsArray()[3] = false;
            b.getWallsArray()[1] = false;
        } else if (x == -1) {
            a.getWallsArray()[1] = false;
            b.getWallsArray()[3] = false;
        }
        if (y == 1) {
            a.getWallsArray()[0] = false;
            b.getWallsArray()[2] = false;
        } else if (y == -1) {
            a.getWallsArray()[2] = false;
            b.getWallsArray()[0] = false;
        }
    }

    /**
     * Gets the cell size.
     * @return the cell size.
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * Gets the amount of rows in the grid.
     * @return the row count.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the amount of columns in the grid.
     * @return the column count.
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets the cell at index x, index y.
     * @param x The x index on the grid.
     * @param y The y index on the grid.
     * @return the cell at x, y.
     */
    public Cell getCell( int x, int y ) {
        if (x<0||x>=rows||y<0||y>=columns) {
            return null;
        }
        return cells.get(x).get(y);
    }

    /**
     * Gets the current stack array.
     * @return the stack array.
     */
    public ArrayList<Cell> getStackArray() {
        return stack;
    }

    /**
     * Renders the entire grid to the current window.
     */
    public void render() {
        for ( int x = 0; x < rows; x++) {
            for ( Cell cell : cells.get(x) ) {
                cell.render();
            }
        }
    }

    /**
     * Renders a section of the grid based on the cells indexed position.
     * @param cellX The x index of the cell to base the center on.
     * @param cellY The y index of the cell to base the center on.
     * @param radius The radius around the middle cell to render.
     */
    public void render( int cellX, int cellY, int radius ) {
        int[] pixelPosition = getCell( cellX, cellY ).getPixelPosition();
        Graphics.translate( -(pixelPosition[0]-(radius*cellSize)), -(pixelPosition[1]-(radius*cellSize)) );
        for ( int x = cellX - radius; x <= cellX + radius; x++ ) {
            for ( int y = cellY - radius; y <= cellY + radius; y++ ) {
                Cell cell = getCell( x, y );
                if ( cell != null ) {
                    cell.render();
                }
            }
        }
        Graphics.translate( (pixelPosition[0]-(radius*cellSize)), (pixelPosition[1]-(radius*cellSize)) );
    }

    /**
     * Renders a section of the grid based on the cells indexed position.
     * @param g The graphics object to draw too.
     */
    public void renderAsImage( Graphics2D g ) {
        for ( int x = 0; x <= getRows(); x++ ) {
            for ( int y = 0; y <= getColumns(); y++ ) {
                Cell cell = getCell( x, y );
                if ( cell != null ) {
                    cell.render( g );
                }
            }
        }
    }
}