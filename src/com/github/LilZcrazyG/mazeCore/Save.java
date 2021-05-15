package com.github.LilZcrazyG.mazeCore;

import com.github.LilZcrazyG.tools.Utilities;
import com.github.LilZcrazyG.tools.Window;
import com.github.LilZcrazyG.tools.Graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.IOException;

public class Save {

    // private variables
    private static ArrayList<BufferedImage> gifImages = new ArrayList<>();
    private static int lastPercentage = 0;
    private static ImageOutputStream output;

    static {
        try {
            String date = Utilities.getDate("yyyy-MM-dd HH:mm:ss");
            Utilities.createFile( "MazeGIF_["+date+"].gif", "" );
            output = new FileImageOutputStream(new File("MazeGIF_["+date+"].gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static GifSequenceWriter writer;

    static {
        try {
            writer = new GifSequenceWriter( output, BufferedImage.TYPE_INT_RGB, 1000/60, false );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the grid as a binary string.
     * @param grid The grid to save.
     * @return the grid as a binary string.
     */
    public static String asBinary( Grid grid ) {
        StringBuilder string = new StringBuilder();
        for ( int x = 0; x < grid.getRows(); x++ ) {
            for ( int y = 0; y < grid.getColumns(); y++ ) {
                Cell currentCell = grid.getCell( x, y );
                boolean[] currentCellWalls = currentCell.getWallsArray();
                if ( currentCell.isHighlighted() ) {
                    string.append("0001");
                } else {
                    string.append("0000");
                }
                if (currentCellWalls[0]) {
                    string.append("1");
                } else {
                    string.append("0");
                }
                if (currentCellWalls[1]) {
                    string.append("1");
                } else {
                    string.append("0");
                }
                if (currentCellWalls[2]) {
                    string.append("1");
                } else {
                    string.append("0");
                }
                if (currentCellWalls[3]) {
                    string.append("1");
                } else {
                    string.append("0");
                }
            }
            if ( x!=grid.getRows()-1 ) {
                string.append("\n");
            }
        }
        return string.toString();
    }

    /**
     * Saves the grid as a JSON string.
     * @param grid The grid to save.
     * @return the grid as a JSON string.
     */
    public static String asJSON( Grid grid ) {
        StringBuilder string = new StringBuilder();
        string.append("{\n\t\"rows\":"+grid.getRows()+",\n");
        string.append("\t\"columns\":"+grid.getColumns()+",\n");
        string.append("\t\"cells\":[");
        for ( int x = 0; x < grid.getRows(); x++ ) {
            string.append("\n\t\t[");
            for ( int y = 0; y < grid.getColumns(); y++ ) {
                Cell currentCell = grid.getCell( x, y );
                boolean[] currentCellWalls = currentCell.getWallsArray();
                string.append("\n\t\t\t{\n");
                string.append("\t\t\t\t\"highlighted\":"+currentCell.isHighlighted()+",\n");
                string.append("\t\t\t\t\"walls\":["+currentCellWalls[0]+","+currentCellWalls[1]+","+currentCellWalls[2]+","+currentCellWalls[3]+"]\n\t\t\t}");
                if ( y != grid.getColumns()-1 ) {
                    string.append(",");
                }
            }
            string.append("\n\t\t]");
            if ( x != grid.getRows()-1 ) {
                string.append(",");
            }
            string.append("\n");
        }
        string.append("\t]");
        string.append("\n}");
        return string.toString();
    }

    public static BufferedImage asImage( Window window, Grid grid ) {
        Canvas canvas = window.getCanvas();
        BufferedImage image=new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D imageGraphics=(Graphics2D)image.getGraphics();
        grid.renderAsImage(imageGraphics);
        return image;
    }

    public static void asGIF( Grid grid ) {
        BufferedImage image = new BufferedImage( grid.getRows()*grid.getCellSize(), grid.getColumns()*grid.getCellSize(), BufferedImage.TYPE_INT_RGB );
        Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
        grid.renderAsImage( imageGraphics );
        try {
            writer.writeToSequence( image );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void createGIF() throws Exception {
        writer.close();
        output.close();
    }

    public static BufferedImage asImage( int width, int height, Grid grid ) {
        BufferedImage image= new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
        grid.renderAsImage(imageGraphics);
        return image;
    }

    public static void stringToNewFile( String filename, String data, String fileType ) {
        String date = Utilities.getDate("yyyy-MM-dd HH:mm:ss");
//        Utilities.createFile(filename+"["+date+"]"+fileType, "");
        Utilities.writeToFile( filename+"_"+date+"]"+fileType, data );
    }

    public static void imageToNewFile( String filename, BufferedImage image ) {
        String date = Utilities.getDate("yyyy-MM-dd HH:mm:ss");
        try {
            ImageIO.write(image, "jpeg", new File(filename+"["+date+"].jpeg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Saved the maze!");
    }
}
