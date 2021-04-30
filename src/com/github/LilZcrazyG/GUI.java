package com.github.LilZcrazyG;

import com.github.LilZcrazyG.mazeCore.MazeGenerator;
import com.github.LilZcrazyG.tools.Window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GUI implements ChangeListener, ActionListener {

    // private variables
    private Window GUIWindow;
    private int generationMethod = 0, rows = 1000, columns = 1000, cellSize = 6;
    private JLabel generationLabel = new JLabel("Maze generation method: "+generationMethod);
    private JSlider generationSlider = new JSlider( JSlider.HORIZONTAL, 0, 3, 0 );
    private JLabel rowsLabel = new JLabel("Rows: "+rows);
    private JSlider rowsSlider = new JSlider( JSlider.HORIZONTAL, 0, 3000, rows );
    private JLabel columnsLabel = new JLabel("Columns: "+columns);
    private JSlider columnsSlider = new JSlider( JSlider.HORIZONTAL, 0, 3000, columns );
    private JLabel cellSizeLabel = new JLabel( "Cell Size: "+cellSize);
    private JSlider cellSizeSlider = new JSlider( JSlider.HORIZONTAL, 0, 300, cellSize );
    private JButton startButton = new JButton("Begin Generation");

    /**
     * Constructor for the GUI.
     * @param width The Width of the GUI window.
     * @param height The Height of the GUI window.
     * @param title The Title of the GUI window.
     */
    public GUI( int width, int height, String title ) {
        GUIWindow = new Window( width, height, title, 0, 1 );
        GUIWindow.addToJPanel(generationLabel);
        GUIWindow.addToJPanel(generationSlider);
        GUIWindow.addToJPanel(rowsLabel);
        GUIWindow.addToJPanel(rowsSlider);
        GUIWindow.addToJPanel(columnsLabel);
        GUIWindow.addToJPanel(columnsSlider);
        GUIWindow.addToJPanel(cellSizeLabel);
        GUIWindow.addToJPanel(cellSizeSlider);
        GUIWindow.addToJPanel(startButton);
        generationSlider.addChangeListener(this);
        generationSlider.setMajorTickSpacing(1);
        generationSlider.setPaintTicks(true);
        generationSlider.setPaintLabels(true);
        rowsSlider.addChangeListener(this);
        rowsSlider.setMajorTickSpacing(500);
        rowsSlider.setMinorTickSpacing(100);
        rowsSlider.setPaintTicks(true);
        rowsSlider.setPaintLabels(true);
        columnsSlider.addChangeListener(this);
        columnsSlider.setMajorTickSpacing(500);
        columnsSlider.setMinorTickSpacing(100);
        columnsSlider.setPaintTicks(true);
        columnsSlider.setPaintLabels(true);
        cellSizeSlider.addChangeListener(this);
        cellSizeSlider.setMajorTickSpacing(50);
        cellSizeSlider.setMinorTickSpacing(10);
        cellSizeSlider.setPaintTicks(true);
        cellSizeSlider.setPaintLabels(true);
        startButton.addActionListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if ( source.equals(rowsSlider)) {
            rows = source.getValue();
            rowsLabel.setText("Rows: "+rows);
        }
        if ( source.equals(columnsSlider)) {
            columns = source.getValue();
            columnsLabel.setText("Columns: "+columns);
        }
        if ( source.equals(cellSizeSlider)) {
            cellSize = source.getValue();
            cellSizeLabel.setText("Cell Size: "+cellSize);
        }
        if ( source.equals(generationSlider) ){
            generationMethod = source.getValue();
            generationLabel.setText("Maze generation method: "+generationMethod);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GUIWindow.getSelf().dispose();
        Main.WINDOW = new Window( 3*110, 3*110 );
        Main.mazeGenerator = new MazeGenerator( generationMethod, rows, columns, cellSize, Main.WINDOW );
        Main.start();
    }
}
