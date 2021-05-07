package com.github.LilZcrazyG.generationMethods;

import com.github.LilZcrazyG.mazeCore.*;
import com.github.LilZcrazyG.tools.Graphics;
import com.github.LilZcrazyG.tools.Utilities;
import com.github.LilZcrazyG.tools.Window;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;

public class RandomisedKruskalsAlgorithm {

    // private variables
    private boolean renderOnly;
    private Window window;
    private Grid grid;
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Set> sets = new ArrayList<>();
    private double visitedCells;
    private double totalCells;
    private int completion;

    /**
     * Constructor for the Randomised kruskals Algorithm.
     * @param window The window to draw too.
     * @param cellSize The size of each cell.
     */
    public RandomisedKruskalsAlgorithm(Window window, int cellSize ) {
        this.window = window;
        this.grid = new Grid( window, cellSize );
        this.renderOnly = false;
        this.window.setTitle("Randomised Kruskals Algorithm");
        this.createEdgeList();
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
    }

    public RandomisedKruskalsAlgorithm(Window window, int rows, int columns, int cellSize, boolean saveAsBinary, boolean saveAsJSON, boolean saveAsGIF, boolean saveAsJPEG ) {
        this.window = window;
        this.grid = new Grid( rows, columns, cellSize );
        this.renderOnly = false;
        this.window.setTitle("Randomised Kruskals Algorithm");
        this.createEdgeList();
        this.visitedCells = 0;
        this.totalCells = this.grid.getRows()*this.grid.getColumns();
        this.completion = 0;
    }


    /**
     * Creates a list of all edges in the grid and then sorts them by weight.
     */
    private void createEdgeList() {
        for ( int x = 0; x < grid.getRows(); x++ ) {
            for ( int y = 0; y < grid.getColumns(); y++ ) {
                sets.add( new Set( Utilities.getRandomColor(100,255), x+y, grid.getCell( x, y ) ) );
                grid.getCell( x, y ).setSetID( x+y );
                if ( grid.getCell( x + 1, y ) != null ) {
                    edges.add( new Edge( grid.getCell( x, y ), grid.getCell( x + 1, y ), Utilities.randomInt( 0, 255 ) ) );
                }
                if ( grid.getCell( x, y + 1 ) != null ) {
                    edges.add( new Edge( grid.getCell( x, y ), grid.getCell( x, y + 1 ), Utilities.randomInt( 0, 255 ) ) );
                }
            }
        }
        edges.sort( new Comparator<Edge>() {
            public int compare( Edge edgeOne, Edge edgeTwo ) {
                if ( edgeOne.getWeight() > edgeTwo.getWeight() ) {
                    return +1;
                } else if ( edgeOne.getWeight() < edgeTwo.getWeight() ) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    /**
     * Ticks the maze.
     */
    public void tick() {
        if (!renderOnly) {
            if ( edges.size() > 0 ) {
                Edge edge = edges.get( Utilities.randomInt( 0, edges.size() - 1 ) );
                Cell[] cells = edge.getCells();
                if ( cells[0].getSetID() != cells[1].getSetID() ) {
                    cells[0].setVisited(true);
                    cells[1].setVisited(true);
                    double oldCompletion = completion;
                    visitedCells += 1;
                    completion = (int) Math.round((visitedCells/totalCells)*100);
                    if ( completion > oldCompletion ) {
                        window.setTitle("Randomised Kruskals Algorithm "+completion+"%");
                    }
                    grid.removeWalls( cells[0], cells[1] );
                    Set[] setsToMerge = new Set[] { sets.get(0), sets.get(1) };
                    for ( Set set : sets ) {
                        if ( set.getCells().contains( cells[0] ) ) {
                            setsToMerge[0] = set;
                        }
                        if ( set.getCells().contains( cells[1] ) ) {
                            setsToMerge[1] = set;
                        }
                    }
                    setsToMerge[0].merge( setsToMerge[1] );
                    sets.remove( setsToMerge[1] );
                }
                edges.remove( edge );
            } else {
                renderOnly = true;
                Save.imageToNewFile("RandomisedKruskalsAlgorithm"+grid.getRows()+","+grid.getColumns(), Save.asImage( grid.getRows()*grid.getCellSize(), grid.getColumns()*grid.getCellSize(), grid ) );
                window.getSelf().dispatchEvent(new WindowEvent(window.getSelf(), WindowEvent.WINDOW_CLOSING));
            }
        }
    }

    /**
     * Renders the maze.
     */
    public void render() {
        grid.render( this.grid.getRows()/2, this.grid.getColumns()/2, (window.getWidth() / grid.getCellSize()) / 2 );
    }
}
