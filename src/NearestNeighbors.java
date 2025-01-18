/*
 * Student Name: Tom Latimer
 * Student Number: 300250278
 * 
 */
import java.util.ArrayList;
import java.util.List;

public class NearestNeighbors {

    //instance variables
    private List<Point3D> inputList;

    //constructor
    public NearestNeighbors(List<Point3D> list) {
        inputList = list;
    }
    
    //getters
    public List<Point3D> getInput() {
        return inputList;
    }

    
    public List<Point3D> rangeQuery(double eps, Point3D P) {
        List<Point3D> neighbourList = new ArrayList<Point3D>();

        for (Point3D point: getInput()) {
            if (P.distance(point) <= eps) {
                neighbourList.add(point);
            }
        }

        return neighbourList;
    }
}