/*
 * Student Name: Tom Latimer
 * Student Number: 300250278
 * 
 */

public class rgbPicker {

    /**
     * The current red value
     */
    private double red;

    /**
     * The current green value
     */
    private double green;

    /**
     * The current blue value
     */
    private double blue;

    /**
     * The previous cluster number.
     * Used to determine if a new colour 
     * should be generated.
     */
    private int prev;

    /**
     * Constructs an instance of rgbPicker.
     * Sets the previous cluster lavel to 0.
     */
    public rgbPicker() {
        prev = 0;
    }

    /**
     * Generates the red colour value
     * 
     * @param clusterNumber the current cluster number of the point
     * @return the red value (between 0.0 and 1.0)
     * 
     */
    private double getR(int clusterNumber) {
        if (clusterNumber == 0) {
            return 0;
        } else if (clusterNumber != prev) {
            red = Math.random();
        }
        return red;
    }

    /**
     * Generates the green colour value
     * 
     * @param clusterNumber the current cluster number of the point
     * @return the green value (between 0.0 and 1.0)
     * 
     */
    private double getG(int clusterNumber) {
        if (clusterNumber == 0) {
            return 0;
        } else if (clusterNumber != prev) {
            green = Math.random();
        }
        return green;
    }

    /**
     * Generates the blue colour value
     * 
     * @param clusterNumber the current cluster number of the point
     * @return the blue value (between 0.0 and 1.0)
     * 
     */
    private double getB(int clusterNumber) {
        if (clusterNumber == 0) {
            return 0;
        } else if (clusterNumber != prev) {
            blue = Math.random();
        }
        return blue;
    }

    /**
     * Formats the rgb values with a comma delimiter
     * 
     * @param clusterNumber the current cluster number of the point
     * @return a string of the rgb values seperated by a comma delimiter
     * 
     */
    public String rgbOutput (int clusterNumber) {
        String output = getR(clusterNumber) + "," + getG(clusterNumber) + "," + getB(clusterNumber);
        prev = clusterNumber;
        return output;
    }
}
