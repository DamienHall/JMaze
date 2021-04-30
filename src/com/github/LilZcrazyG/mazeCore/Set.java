package com.github.LilZcrazyG.mazeCore;

import com.github.LilZcrazyG.tools.Utilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Set {

    // private variables
    private ArrayList<Cell> cells = new ArrayList<>();
    private Color color = Utilities.getRandomColor();

    /**
     * Constructor for a Set of cells.
     * @param color The color of this set.
     * @param cells The cells to be in this set.
     */
    public Set(Color color, int setID, Cell... cells) {
        this.cells.addAll( Arrays.asList( cells ) );
        this.color = color;
    }

    /**
     * Gets all of the cells in this set.
     * @return the cells in this set.
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }

    /**
     * Adds cells to this set.
     * @param cells The cell(s) to add to this set.
     * @return the size of the arraylist after adding said cells.
     */
    public int addCells( Cell... cells ) {
        this.cells.addAll( Arrays.asList( cells ) );
        return this.cells.size();
    }

    /**
     * Gets the color of this set.
     * @return the color of this set.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of this set.
     * @param color the color to set this set to.
     */
    public void setColor( Color color ) {
        this.color = color;
    }

    /**
     * Merges two sets together keeping the parent's color.
     * @param set the set to merge this set to.
     */
    public void merge( Set set ) {
        int sizeA = cells.size();
        int sizeB = set.getCells().size();
        cells.addAll( set.getCells() );
        if ( sizeA >= sizeB ) {
            for ( Cell cell : cells ) {
                cell.setSetID( cells.get(0).getSetID() );
                cell.setFillColor( color );
            }
        } else {
            for ( Cell cell : cells ) {
                cell.setSetID( cells.get(0).getSetID() );
                cell.setFillColor( set.getColor() );
            }
        }
    }
}
