/*
 * Student Name: Tom Latimer
 * Student Number: 300250278
 * 
 */
public class Point3D {

    //Instance Variables

    /**
     * The x coordinate of the point
     */
    private double x;

    /**
     * The y coordinate of the point
     */
    private double y;

    /**
     * The z coordinate of the point
     */
    private double z;

    /**
     * the label identifying which cluster the
     * point belongs to, if any
     */
    private int clusterLabel;

    /**
     * Constructs an instance of Point3D
     * 
     * @param x the x coodinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     * 
     */
    public Point3D (double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        clusterLabel = -1;
    }

    //Getters
    /**
     * Getter for x coordinate
     * 
     * @return the x coordinate of the point
     * 
     */
    public double getX () {
        return x;
    }

    /**
     * Getter for the y coordinate
     * 
     * @return the y coordinate of the point
     * 
     */
    public double getY () {
        return y;
    }

    /**
     * Getter for the z coordinate
     * 
     * @return the z coordinate of the point
     * 
     */
    public double getZ() {
        return z;
    }

    /**
     * Getter for the cluster label
     * 
     * @return the cluster label
     * 
     */
    public int getLabel() {
        return clusterLabel;
    }

    //setters
    /**
     * Sets the label identifying
     * which cluster the point belongs
     * to, if any
     * 
     * @param label the cluster label of the point
     * 
     */
    public void setLabel(int label) {
        clusterLabel = label;
    }

    //Instance methods
    /**
     * determines the distance between the current
     * point and a given point
     * 
     * @param pt the point to find the distance to
     * @return the distance between the two points
     * 
     */
    public double distance(Point3D pt) {

        return Math.sqrt(Math.pow(pt.getX() - this.getX(), 2) 
        + Math.pow(pt.getY() - this.getY(), 2) 
        + Math.pow(pt.getZ() - this.getZ(), 2));
    }
    
}