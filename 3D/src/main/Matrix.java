package main;

import java.awt.*;

public class Matrix
{
    private double[][] matrix;
    private Drawable[] faces;

    public Matrix(double[][] mmatrix)
    {
        matrix = mmatrix;
    }

    //for making cubes easier
    public Matrix(double sideLength, Point corner)
    {
        matrix = new double[3][8];

        //establishing corners of the cube
        for(int z = 0; z < 2; z++)
        {
            for(int y = 0; y < 2; y++)
            {
                for(int x = 0; x < 2; x++)
                {
                    matrix[0][x + (2*y) + (2*2*z)] = (x * sideLength) + corner.getx();
                    matrix[1][x + (2*y) + (2*2*z)] = (y * sideLength) + corner.gety();
                    matrix[2][x + (2*y) + (2*2*z)] = (z * sideLength) + corner.getz();
                }
            }
        }

        //establishes faces
        faces = new Drawable[6];
        //for each face that needs to be made
        for(int i = 0; i < 6; i++)
        {
            int index = 0;
            double[][] tempFace = new double[3][4];

            //for each edge in the matrix cube
            for(int point = 0; point < 8; point++)
            {
                //faces will all have one axis value in common (x = 1 or z = 0)
                //faces will all have 4 edges out of the 8 in the matrix
                if(matrix[i/2][point] == i % 2)
                {
                    for(int axis = 0; axis < 3; axis++)
                    {
                        tempFace[axis][index] = matrix[axis][point];
                    }
                    index++;
                }
            }

            faces[i] = new Drawable(tempFace, true, new Color(i *256 * 256 * 256 / 6));
        }
    }

    public Matrix(Point[] mmatrix)
    {
        matrix = new double[3][mmatrix.length];

        for(int x = 0; x < mmatrix.length; x++)
        {
            for(int y = 0; y < 3; y ++)
            {
                matrix[y][x] = mmatrix[x].getPoint()[y];
            }
        }
    }

    public void transform(Matrix t) { matrix = multiply(t, this).getMatrix(); }

    public void translate(Point p)
    {
        for(int i=0; i<matrix[0].length; i++)
        {
            matrix[0][i] += p.getx();
            matrix[1][i] += p.gety();
            matrix[2][i] += p.getz();
        }
    }

    public static Matrix multiply(Matrix A, Matrix B) //length of rows must equal height of columns
    {
        if(A.getRowsSize() != B.getCollumnsSize())
        {
            System.out.println("incompatible matrix sizes");
            return null;
        }
        double[][] rreturn = new double[A.getCollumnsSize()][B.getRowsSize()];
        double sum;
        for(int x=0; x<B.getRowsSize(); x++)
        {
            for(int y=0; y<A.getCollumnsSize(); y++)
            {
                sum=0;
                for(int i=0; i<A.getRowsSize(); i++)
                {
                    sum+= A.getNumber(y, i) * B.getNumber(i, x);
                }
                rreturn[y][x] = sum;
            }
        }
        return new Matrix(rreturn);
    }

    public int getCollumnsSize() { return matrix.length; }
    public int getRowsSize() { return matrix[0].length; }
    public double getNumber(int row, int column) { return matrix[row][column]; }
    public Point averagePoint()
    {
        double x=0;
        double y=0;
        double z=0;
        for(int i=0; i<getRowsSize(); i++)
        {
            x+= getNumber(0, i);
            y+= getNumber(1, i);
            z+= getNumber(2, i);
        }
        return new Point(x/getRowsSize(), y/getRowsSize(), z/getRowsSize());
    }

    //for debugging
    public void display()
    {
        for(int y=0; y<getCollumnsSize(); y++)
        {
            for(int x=0; x<getRowsSize(); x++)
            {
                System.out.print(getNumber(y, x)+" ");
            }
            System.out.print("\n");
        }
    }

    public Point toPoint(int column) { return new Point(getNumber(0, column), getNumber(1, column), getNumber(2, column)); }

    //converts given points into something that can be drawn (a face of a shape)
    public Drawable toDrawable(int[] points, boolean fill, Color c)
    {
        double[][] newMatrix = new double[3][points.length];
        for(int i=0; i<points.length; i++)
        {
            for(int x=0; x<3; x++)
            {
                newMatrix[x][i] = getNumber(x, points[i]);
            }
        }
        if(c==null) { return new Drawable(newMatrix, fill); }
        else { return  new Drawable(newMatrix, fill, c); }
    }

    public Drawable toDrawable(boolean fill, Color c) { return new Drawable(matrix, fill, c); }

    public double[][] getMatrix() { return matrix; }
    public void resetMatrix(double[][] mmatrix) { matrix = mmatrix; }

    /*  M = [0, 0]  [0, 1]  [0, 2]
            [1, 0]  [1, 1]  [1, 2]
            [2, 0]  [2, 1]  [2, 2]
            [3, 0]  [3, 1]  [3, 2]
            4 by 3 matrix
            row its in followed by column its in
            matrix shape is more correct but it makes making matrices harder
     */

    public Drawable[] getFaces() { return faces; }
}
