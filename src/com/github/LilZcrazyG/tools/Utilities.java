package com.github.LilZcrazyG.tools;

// imports
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utilities {

    // variables
    private static Random random = new Random();

    // Utility Methods
    public static void setInterval() {

    }

    // get date
    public static String getDate( String format ) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    // create file
    public static Boolean createFile( String name, String location ) {
        try {
            File file = new File(location + name);
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    // write to file
    public static void writeToFile( String filename, String strToWrite ) {
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write(strToWrite);
            fw.close();
        } catch (IOException e) {
            System.out.println("ERROR WRITING TO FILE");
            e.printStackTrace();
        }
    }

    // get random color
    public static Color getRandomColor( int min, int max ) {
        return new Color(randomInt(min,max),randomInt(min,max),randomInt(min,max));
    }

    public static Color getRandomColor() {
        return new Color(randomInt(1,255),randomInt(1,255),randomInt(1,255));
    }

    // get a color
    public static Color getColor( int r, int g, int b) {
        return new Color( r, g, b );
    }

    // map a value to another
    public static int mapInt( int[] rangeA, int[] rangeB, int input ) {
        return rangeB[0]+(rangeB[1]-rangeB[0])/(rangeA[1]-rangeA[0])*(input-rangeA[0]);
    }

    // random functions
    public static int randomInt(int min, int max) {
        return random.nextInt(( max - min ) + 1 ) + min;
    }

    public static double random(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static double random(int min, int max) {
        return min + (max - min) * random.nextDouble();
    }

    public static double random(double min, int max) {
        return min + (max - min) * random.nextDouble();
    }

    public static double random(int min, double max) {
        return min + (max - min) * random.nextDouble();
    }

}
