# DBSCAN
During my fall 2022 school term I worked on developing a simple implementation of the DBSCAN (density-based spatial clustering of applications with noise) algorithm. It can be used in computer vision applications to group together points in a spatial dataset (point cloud) to identify objects in the scene. This implementation takes data in the form of a csv of 3D points and returns a csv of the points with corresponding colour data to indicate which object the point belongs to.

# Running the Application
The program can be run from the **DbScan.java** file which expects an **epsilon** and **minimum points** parameters to be supplied.

> The **epsilon** value controls the maximum distance between two points for them to be considered as a part of an object.
>
> The **minimum points** value is used to filter out noise in the dataset by specifying the minimum number of nearby points required for a point to be considered as a part of an object.

The program expects as input a *csv* file with the following format:
```csv
x,y,z
0,0,0
```
Every row after the *0,0,0* coordinate should be a point in the point cloud dataset.

# Visualization
The returned point cloud data with colour information can be visualized with the python Open3D library as found in the files of the **Visualization folder**.