/*
 * Student Name: Tom Latimer
 * Student Number: 300250278
 * 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DBScan {

    /**
     * sentinel value for noise label
     */
    private static final int NOISE = 0;

    /**
     * sentinel value for undefined label
     */
    private static final int UNDEFINED = -1;

    /**
     * delimiter used to parse the results of reading the input cvs file
     */
    private static final String stringDelimiter = ",";

    // instance variables
    private int clusterCount;

    private double eps;
    private double minPts;
    private List<Point3D> point3DList;

    /**
     * Constructs an instance of the DBScan
     * 
     * @param list The list of 3D points to be processed
     */
    public DBScan(List<Point3D> list) {
        point3DList = list;
        clusterCount = 0;
    }

    // setters
    /**
     * Sets the epsilon value.
     * 
     * @param eps The input epsilon value
     */
    public double setEps(double eps) {
        this.eps = eps;
        return this.eps;
    }

    /**
     * Sets the threshold of points required to
     * determin if a point belongs to a dense region
     * 
     * @param minPts The minimum amount of point for the threshold
     */
    public double setMinPts(double minPts) {
        this.minPts = minPts;
        return this.minPts;
    }

    //getters
    /**
     * Returns the epsilon value
     * 
     * @return the epsilon value
     * 
     */
    public double getEps() {
        return this.eps;
    }

    /**
     * Returns the threshold value
     * 
     * @return the minimum amount of points for the threshold
     * 
     */
    public double getMinPts() {
        return this.minPts;
    }

    /**
     * Returns the list of processed input 3D points
     * 
     * @return a list of Point3D objects
     * 
     */
    public List<Point3D> getPoints() {
        return point3DList;
    }

    /**
     * Returns the total number of clusters in the DBScan
     * 
     * @return the total amount of clusters
     * 
     */
    public int getNumberOfClusters() {
        return clusterCount;
    }

    // Instance methods

    /**
     * Generates the filename for the cvs file that will be created
     * 
     * @param filename The file name the input file
     * @return The output file name
     */
    public String fileNameGenerator(String filename) {

        // remove the file extension from the string
        String file = filename.substring(0, filename.indexOf('.'));

        // format string as filename_clusters_eps_minPts_nclusters.csv
        return file + "_clusters_" + getEps() + "_"
        + getMinPts() + "_" + getNumberOfClusters()+".csv";
    }

    /**
     * Saves and writes the results of the DBScan to a new file
     * 
     * @param filename The name of the output file (which will be generated)
     */
    public void save(String filename) {
        // create class that will assign rgb values
        rgbPicker colour = new rgbPicker();

        //create output file
        File outputFile = new File(filename);
        try {
            PrintWriter writer = new PrintWriter(outputFile);

            //write column headers to file
            writer.println("x,y,z,C,R,G,B");

            //write the coordinates, cluster label and rgb values of each point to file
            for (Point3D point : getPoints()) {
                writer.printf("%f,%f,%f,%d,%s\n", point.getX(), point.getY(), point.getZ(),
                        point.getLabel(), colour.rgbOutput(point.getLabel()));
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the DBScan algorithm on the list of inputted 3D points
     */
    public void findClusters() {
        for (Point3D point : getPoints()) {
            if (point.getLabel() != UNDEFINED) {
                continue;
            }

            NearestNeighbors N = new NearestNeighbors(getPoints());
            List<Point3D> neighbours = N.rangeQuery(getEps(), point);

            if (neighbours.size() < getMinPts()) {
                point.setLabel(NOISE);
                continue;
            }

            clusterCount++;
            point.setLabel(clusterCount);

            Stack<Point3D> neighbourStack = new Stack<Point3D>();
            for (Point3D pt : neighbours) {
                neighbourStack.push(pt);
            }

            while (!neighbourStack.isEmpty()) {
                Point3D neighbourPoint = neighbourStack.pop();
                if (neighbourPoint.getLabel() == NOISE) {
                    neighbourPoint.setLabel(clusterCount);
                }
                if (neighbourPoint.getLabel() != UNDEFINED) {
                    continue;
                }
                neighbourPoint.setLabel(clusterCount);
                neighbours = N.rangeQuery(getEps(), neighbourPoint);

                if (neighbours.size() >= getMinPts()) {
                    for (Point3D pt : neighbours) {
                        neighbourStack.push(pt);
                    }
                }
            }

        }
    }

    // static methods

    /**
     * Reads the given file and creates list of 3D points
     * 
     * @param filename The name of the input file to be read
     * @return a list of Point3D objects
     * 
     */
    public static List<Point3D> read(String filename) {
        //string array to hold the contents of each line to be used in object creation
        String[] temp;

        //string used to temporarily hold the contens of each line
        String tempLine = " ";

        //will contain the processed 3D points
        List<Point3D> outputList = new ArrayList<Point3D>();

        try {
            File file = new File(filename);
            BufferedReader buffReader = new BufferedReader(new FileReader(file));

            // skip 'x,y,z' header
            tempLine = buffReader.readLine();

            //read and split each line according to delimiter
            while ((tempLine = buffReader.readLine()) != null) {
                temp = tempLine.split(stringDelimiter);

                //create new Point3D object from parsed information and add to list
                outputList.add(new Point3D(Double.parseDouble(temp[0]),
                        Double.parseDouble(temp[1]), Double.parseDouble(temp[2])));
            }
            buffReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        //return list of 3D points
        return outputList;
    }

    public static void main(String[] args) {

        //read input file
        DBScan db = new DBScan(DBScan.read(args[0]));

        db.setEps(Double.parseDouble(args[1]));
        db.setMinPts(Double.parseDouble(args[2]));

        //run DBScan
        db.findClusters();

        //create new cvs file and save the DBScan results to it
        db.save(db.fileNameGenerator(args[0]));

        //get the size of each cluster
        int noiseCount = 0;
        int[] clusterSizeArr = new int[db.getNumberOfClusters() + 1];

        for (Point3D point: db.getPoints()) {
            int tempLabel = point.getLabel();
            if (tempLabel == NOISE) {
                noiseCount++;
            } else {
                clusterSizeArr[tempLabel]++;
            }
        }

        Arrays.sort(clusterSizeArr);
        
        for (int i = 1; i < clusterSizeArr.length/2 + 1; i++) {
            int temp = clusterSizeArr[i];
            clusterSizeArr[i] = clusterSizeArr[clusterSizeArr.length - i];
            clusterSizeArr[clusterSizeArr.length - i] = temp;

        }
        
        
        System.out.println("Total Amount of Clusters: " + db.getNumberOfClusters());
        //print cluster sizes
        for (int i = 1; i < clusterSizeArr.length; i++) {
            System.out.println(clusterSizeArr[i]);
        }
        System.out.println("Number of noise points: " + noiseCount);
        
    }
}