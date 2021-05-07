package com.github.LilZcrazyG;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class Config {

    private JFrame window = new JFrame();
    private JPanel panel1;
    private JCheckBox binaryCheckBox;
    private JCheckBox JSONCheckBox;
    private JCheckBox GIFCheckBox;
    private JCheckBox JPEGCheckBox;
    private JRadioButton depthFirstSearchRadioButton;
    private JRadioButton huntAndKillRadioButton;
    private JRadioButton randomisedKruskalsAlgRadioButton;
    private JRadioButton randomisedPrimsAlgRadioButton;
    private JSpinner rowsSpinner;
    private JSpinner columnsSpinner;
    private JSpinner cellSizeSpinner;
    private JButton startButton;
    private JButton resetButton;
    private JButton cancelButton;

    private boolean saveAsBinary = false;
    private boolean saveAsJSON = false;
    private boolean saveAsGIF = false;
    private boolean saveAsJPEG = false;
    private int mazeType = 0;
    private int rows = 0;
    private int columns = 0;
    private int cellSize = 0;

    public Config() {

        window.setTitle( "Configuration Menu" );
        window.setResizable( false );
        window.setVisible( true );
        window.setDefaultCloseOperation( window.DISPOSE_ON_CLOSE );
        window.add( panel1 );
        window.pack();

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if ( source == binaryCheckBox ) {
                    saveAsBinary = binaryCheckBox.isSelected();
                }
                if ( source == JSONCheckBox ) {
                    saveAsJSON = JSONCheckBox.isSelected();
                }
                if ( source == GIFCheckBox ) {
                    saveAsGIF = GIFCheckBox.isSelected();
                }
                if ( source == JPEGCheckBox ) {
                    saveAsJPEG = JPEGCheckBox.isSelected();
                }
                if ( source == depthFirstSearchRadioButton ) {
                    mazeType = 0;
                    huntAndKillRadioButton.setSelected( false );
                    randomisedKruskalsAlgRadioButton.setSelected( false );
                    randomisedPrimsAlgRadioButton.setSelected( false );
                }
                if ( source == huntAndKillRadioButton ) {
                    mazeType = 1;
                    depthFirstSearchRadioButton.setSelected( false );
                    randomisedKruskalsAlgRadioButton.setSelected( false );
                    randomisedPrimsAlgRadioButton.setSelected( false );
                }
                if ( source == randomisedKruskalsAlgRadioButton ) {
                    mazeType = 2;
                    depthFirstSearchRadioButton.setSelected( false );
                    huntAndKillRadioButton.setSelected( false );
                    randomisedPrimsAlgRadioButton.setSelected( false );
                }
                if ( source == randomisedPrimsAlgRadioButton ) {
                    mazeType = 3;
                    depthFirstSearchRadioButton.setSelected( false );
                    huntAndKillRadioButton.setSelected( false );
                    randomisedKruskalsAlgRadioButton.setSelected( false );
                }
                if ( source == startButton ) {
                    Main.start( saveAsBinary, saveAsJSON, saveAsGIF, saveAsJPEG, mazeType, rows, columns, cellSize, Main.WINDOW );
                }
                if ( source == resetButton ) {
                    binaryCheckBox.setSelected( false );
                    JSONCheckBox.setSelected( false );
                    GIFCheckBox.setSelected( false );
                    JPEGCheckBox.setSelected( false );
                    depthFirstSearchRadioButton.setSelected( false );
                    huntAndKillRadioButton.setSelected( false );
                    randomisedKruskalsAlgRadioButton.setSelected( false );
                    randomisedPrimsAlgRadioButton.setSelected( false );
                    rowsSpinner.setValue( 0 );
                    columnsSpinner.setValue( 0 );
                    cellSizeSpinner.setValue( 0 );
                }
            }
        };
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Object source = e.getSource();
                if ( source == rowsSpinner ) {
                    try {
                        rowsSpinner.commitEdit();
                    } catch ( java.text.ParseException err ) {
                        err.printStackTrace();
                    }
                    if ( (Integer) rowsSpinner.getValue() < 0 ) {
                        rowsSpinner.setValue( 0 );
                    }
                    rows = (Integer) rowsSpinner.getValue();
                }
                if ( source == columnsSpinner ) {
                    try {
                        columnsSpinner.commitEdit();
                    } catch ( java.text.ParseException err ) {
                        err.printStackTrace();
                    }
                    if ( (Integer) columnsSpinner.getValue() < 0 ) {
                        columnsSpinner.setValue( 0 );
                    }
                    columns = (Integer) columnsSpinner.getValue();
                }
                if ( source == cellSizeSpinner ) {
                    try {
                        cellSizeSpinner.commitEdit();
                    } catch ( java.text.ParseException err ) {
                        err.printStackTrace();
                    }
                    if ( (Integer) cellSizeSpinner.getValue() < 0 ) {
                        cellSizeSpinner.setValue( 0 );
                    }
                    cellSize = (Integer) cellSizeSpinner.getValue();
                }
            }
        };

        // check boxes
        binaryCheckBox.addActionListener(actionListener);
        JSONCheckBox.addActionListener(actionListener);
        GIFCheckBox.addActionListener(actionListener);
        JPEGCheckBox.addActionListener(actionListener);

        // radio buttons
        depthFirstSearchRadioButton.addActionListener(actionListener);
        huntAndKillRadioButton.addActionListener(actionListener);
        randomisedKruskalsAlgRadioButton.addActionListener(actionListener);
        randomisedPrimsAlgRadioButton.addActionListener(actionListener);

        // spinners
        rowsSpinner.addChangeListener(changeListener);
        columnsSpinner.addChangeListener(changeListener);
        cellSizeSpinner.addChangeListener(changeListener);

        // buttons
        startButton.addActionListener(actionListener);
        resetButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);

    }
}
