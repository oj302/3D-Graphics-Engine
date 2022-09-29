package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//face of a shape
public class Drawable extends Matrix
{
    private boolean fill;
    private Color colour;

    public Drawable(double[][] points, boolean ffill, Color ccolour)
    {
        super(points);
        fill = ffill;
        colour = ccolour;

        //check if all points are on the same plane???

        /* re order points
            2 RULES:
            no intersects
            all points are included

            prioritising multiple solutions????
            some solutions will be the same
         */

        //3 points cannot, not be on the same plane
        if(points[0].length > 5) //CAUSES PROBLEMS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        { //usually > 3
            //randomly assigns points until it finds a solution (i dont want to make a tree for a matrix project)
            while(true)
            {
                ArrayList<double[]> unvisitedPoints = new ArrayList<>();
                double[][] orderedPoints = new double[3][points[0].length];
                Random r = new Random();

                for(int i = 0; i < points[0].length; i++)
                {
                    unvisitedPoints.add(new double[] {points[0][i], points[1][i], points[2][i]});
                }

                //randomly chooses from unvisited points, adds to ordered points and removes from list
                for(int i = 0; i < orderedPoints[0].length; i++)
                {
                    int index = r.nextInt(unvisitedPoints.size());
                    orderedPoints[i] = unvisitedPoints.get(index);
                    unvisitedPoints.remove(index);
                }

                //check no lines cross
                Matrix temp = new Matrix(orderedPoints);
                boolean intersects = false;
                for(int y = 0; y < orderedPoints.length -2; y++)
                {
                    for(int x = y +1; x < orderedPoints.length -1; x++)
                    {
                        if(Point.intersects(new Point[] {temp.toPoint(y), temp.toPoint(y +1)}, new Point[] {temp.toPoint(x), temp.toPoint(x+1)}))
                        {
                            intersects = true;
                            break;
                        }
                    }
                    if(intersects) { break; }
                }

                //also check "last line" (first point to last point)
                if(!intersects)
                {
                    for (int i = 0; i < orderedPoints.length - 1; i++)
                    {
                        if (Point.intersects(new Point[]{temp.toPoint(0), temp.toPoint(orderedPoints.length -1)}, new Point[]{temp.toPoint(i), temp.toPoint(i + 1)}))
                        {
                            intersects = true;
                            break;
                        }
                    }
                }

                //if none of the lines intersect, reset the matrix
                if(!intersects)
                {
                    resetMatrix(orderedPoints);
                }
            }
        }
    }

    public Drawable(double[][] points, boolean ffill) { this(points, ffill, new Color(0)); }

    public void paint(Camera c, Graphics g)
    {
        double[] toDraw;
        int[] xpoints = new int[getRowsSize()];
        int[] ypoints = new int[getRowsSize()];

        for(int i = 0; i<getRowsSize(); i++)
        {
            toDraw = c.mapTo2d(toPoint(i));
            //xpoints[i] = (int)((toDraw[0] + 1) * Main.width / 2);
            //ypoints[i] = (int)((toDraw[1] + 1) * Main.height / 2);
            xpoints[i] = (int)((0.5+toDraw[0]) * Main.width); //toDraw gives a number between -0.5 and 0.5 to represent how far down or across the point should be, .5 bit starts it in the corner
            ypoints[i] = (int)((0.5-toDraw[1]) * Main.height);
        }

        g.setColor(colour);
        if(fill)
        {
            g.fillPolygon(xpoints, ypoints, getRowsSize());
            //outline
            g.setColor(new Color(0));
            g.drawPolyline(xpoints, ypoints, getRowsSize());
        }
        else { g.drawPolyline(xpoints, ypoints, getRowsSize()); }
    }

    public boolean isFill() { return fill; }
    public Color getColour() { return colour; }
}
