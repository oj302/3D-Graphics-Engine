package main;

public class Point {

    private double[] point;

    public Point(double x, double y, double z) { point = new double[]{x, y, z}; }

    public double getx() { return point[0]; }
    public double gety() { return point[1]; }
    public double getz() { return point[2]; }
    public double[] getPoint() { return point; }

    public double distanceTo(Point p) { return Math.sqrt( Math.pow(point[0]-p.getx(), 2) + Math.pow(point[1]-p.gety(), 2) + Math.pow(point[2]-p.getz(), 2) ); }

    public Matrix toMatrix() { return new Matrix(new double[][]{{getx()}, {gety()}, {getz()}}); }

    public static boolean intersects(Point[] lineA, Point[] lineB)
    {
        //assumes they are on the same plane (can be checked)

        //if two nodes on either line are the same lines do not intersect
        for(int p1 = 0; p1 < 2; p1++)
        {
            for(int p2 = 0; p2 < 2; p2++)
            {
                boolean same = true;
                for(int axis = 0; axis < 3; axis++)
                {
                    if(lineA[p1].getPoint()[axis] != lineB[p2].getPoint()[axis])
                    {
                        same = false;
                        break;
                    }
                }
                if(same) { return false; }
            }
        }

        //ignores z value (because they are in the same plane) and finds the x value of where the lines intersect
        double intersectX = intersectValue2D(new double[][] {{lineA[0].getx(), lineA[0].gety()}, {lineA[1].getx(), lineA[1].gety()} },
                new double[][] {{lineB[0].getx(), lineB[0].gety()}, {lineB[1].getx(), lineB[1].gety()} });

        //if the x intersect value is inbetween all points then lines intersect between points
        return inbetween(lineA[0].getx(), lineA[1].getx(), intersectX) && inbetween(lineB[0].getx(), lineB[1].getx(), intersectX);

    }

    //for 2D lines
    public static double intersectValue2D(double[][] line1, double[][] line2) //returns x value of intersection of lines, doesnt have to be inbetween both points
    {
        double m1 = gradient2D(line1);
        double m2 = gradient2D(line2);

        return (line2[0][1] - line1[0][1] + (m1 * line1[0][0]) - (m2 * line2[0][0])) / (m1 - m2);
    }
    public static double gradient2D(double[][] line)
    {
        return (double)(line[1][1] - line[0][1]) / (double)(line[1][0] - line[0][0]);
    }

    public static boolean inbetween(double p1, double p2, double middle)
    { return ((p1 < middle) && (middle <= p2)) || ((p2 < middle) && (middle <= p1)); }
}
