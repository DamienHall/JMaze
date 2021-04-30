package com.github.LilZcrazyG.mazeCore;

public class Edge {

    // private variables
    private int weight;
    private Cell[] cells = new Cell[2];

    /**
     * Creates an Edge object.
     * @param a The first cell.
     * @param b The second cell.
     * @param weight The weight of this edge.
     */
    public Edge( Cell a, Cell b, int weight ) {
        this.cells[0] = a;
        this.cells[1] = b;
        this.weight = weight;
    }

    /**
     * Get the cells for this edge.
     * @return the cells for this edge.
     */
    public Cell[] getCells() {
        return cells;
    }

    /**
     * Get the weight of this edge.
     * @return the weight of this edge.
     */
    public int getWeight() {
        return weight;
    }
}
