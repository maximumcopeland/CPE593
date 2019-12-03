/*
    GrowArray.java
    Author: Max Copeland
    "I pledge my honor that I have abided by the Stevens Honor System"
*/
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class GrowArray {
    private Point2D[] data;
    private int used;

    private void grow() {
        // Double the capacity of the array
        int newCapacity = data.length*2;
        Point2D[] newData = new Point2D[newCapacity];
        System.arraycopy(data, 0, newData, 0, used);
        // Overwrite old array
        data = newData;
    }

    public GrowArray() {
        // First constructor, creates an empty array of capacity 1
        used = 0;
        data = new Point2D[1];
    }

    public GrowArray(int initialSize) {
        // Second constructor, creates an empty array with specified initial capacity
        used = 0;
        data = new Point2D[initialSize];
    }

    public void addEnd(Point2D v) {
        // Adds integer v to the end of the array
        if (used == data.length) {
            // Grow the array if needed
            grow();
        }

        // Append new data
        data[used] = v;
        used++;
    }

    public void addStart(Point2D v) {
        // Adds integer v to the beginning of the array
        if (used == data.length) {
            // Grow the array if needed
            grow();
        }

        for (int i = used-1; i > 0; i--) {
            // Shift contents one to the right
            data[i] = data[i-1];
        }
        
        // Add new data
        data[0] = v;
        used++;
    }

    public void insert(int i, Point2D v) {
        // Adds integer v to position i and shifts everything to the right
        if (used == data.length) {
            // Grow the array if needed
            grow();
        }

        for (int j = used-1; j > i; j--) {
            // Shift contents right of v to the right
            data[j] = data[j-1];
        }
        
        // Add new data
        data[i] = v;
        used++;
    }

    public void removeEnd() {
        // Removes last object from the end of the list
        used--;
    }

    public void removeStart() {
        // Removes first object from the beginning of the list
        for (int i = 0; i < used-1; i++) {
            // Shift contents one to the left
            data[i] = data[i+1];
        }
        removeEnd();
    }

    public void remove(int i) {
        // Removes object from index i
        for (int j = i; j < used-1; j++) {
            // Shift contents right of i to the left
            data[j] = data[j+1];
        }
        removeEnd();
    }

    public int size() {
        // Returns how many elements in the list
        return used;
    } 
    
    public Point2D get(int i) {
        // Returns point from position i
        return data[i];
    }

    public static void main(String[] args) throws IOException {
        int n = 16;
    
        // Read in the data
        GrowArray points = new GrowArray();
        Scanner data = new Scanner(new File("./convexhullpoints.dat"));
        double x = data.nextDouble(), xmin = x, xmax = x;
        double y = data.nextDouble(), ymin = y, ymax = y;
        points.addEnd(new Point2D.Double(x, y));
        while (data.hasNextDouble()) {
            x = data.nextDouble();
            // Check for new mins and maxes
            if (x < xmin) {xmin = x;}
            if (x > xmax) {xmax = x;}

            y = data.nextDouble();
            // Check for new mins and maxes
            if (y < ymin) {ymin = y;}
            if (y > ymax) {ymax = y;}

            points.addEnd(new Point2D.Double(x, y));
        }
        data.close();

        // Create a 2D grid of GrowArrays of size n*n
        GrowArray[][] grid = new GrowArray[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Initialize each GrowArray
                grid[i][j] = new GrowArray();
            }
        }
        double XperBox = 1/(xmax-xmin);
        double YperBox = 1/(ymax-ymin);

        // Create the grid
        int i, j;
        for (int k = 0; k < points.size(); k++) {
            i = (int)((points.get(k).getX() - xmin) * XperBox);
            j = (int)((points.get(k).getY() - ymin) * YperBox);
            grid[i][j].addEnd(new Point2D.Double(points.get(k).getX(), points.get(k).getY()));
        }
    
        // Print the grid
        for (j = 0; j < n; j++) {
            for (i = 0; i < n; i++) {
                System.out.print(grid[i][j].size() + " ");
            }
            System.out.println("");
        }
    }
}