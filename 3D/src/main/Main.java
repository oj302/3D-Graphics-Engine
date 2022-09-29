package main;

import java.awt.*;

public class Main
{
    public static final int width = 800;
    public static final int height = 600;
    public static final int frames = 30;
    public static void main(String[] args)
    {
        Matrix unitCube = new Matrix(new double[][] {{-.5, -.5, .5, .5, -.5, -.5, .5, .5}, {-.5, -.5, -.5, -.5, .5, .5, .5, .5}, {-.5, .5, .5, -.5, -.5, .5, .5, -.5}});
        Matrix icosahedron = new Matrix(new double[][]{ //also known as a D20
                        {0,         0,      -0.9511,-0.5,   0.5,    0.9511, -0.5,   -0.9511,0,      0.9511, 0.5,    0},
                        {-0.8043,   -0.5,   -0.5,   -0.5,   -0.5,   -0.5,   0.5,    0.5,    0.5,    0.5,    0.5,    0.8043},
                        {0,         -1,     -0.3090,0.6881, 0.6881, -0.3090,-0.6881,0.3090, 1,      0.3090, -0.6881,0}
        });
        double angle= 0.1* 2*Math.PI / frames ;
        Matrix yRotation = new Matrix(new double[][] {{Math.cos(angle), 0, Math.sin(angle)}, {0, 1, 0}, {-Math.sin(angle), 0, Math.cos(angle)}});
        Matrix identity = new Matrix(new double[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
        /*  0 0 1 1 0 0 1 1
            0 0 0 0 1 1 1 1
            0 1 1 0 0 1 1 0
        */
        //Point cameraPosition = new Point(4, 11, 10);
        //Point cameraPosition = new Point(2, 3, 2);
        Point cameraPosition = new Point(0, 0, 4);
        Camera c = new Camera(Math.PI/2, (double)(Main.width)/(double)(Main.height), cameraPosition);
        c.setCameraxAngle(0);
        c.setCamerayAngle(0);
        //c.setCameraxAngle(-Math.PI/4);
        //c.setCamerayAngle(-Math.PI/4);

        GUI g = new GUI(Main.width, Main.height, Main.frames, c);

        g.setTransformation(yRotation); //STANDARD
        //g.setTransformation(identity);

        /* STANDARD
        g.addToDrawList(unitCube.toDrawable(new int[] {0, 1, 2, 3}, true, new Color(0xFF0000)));
        g.addToDrawList(unitCube.toDrawable(new int[] {0, 1, 5, 4}, true, new Color(0x00FF00)));
        g.addToDrawList(unitCube.toDrawable(new int[] {1, 2, 6, 5}, true, new Color(0x0000FF)));
        g.addToDrawList(unitCube.toDrawable(new int[] {2, 3, 7, 6}, true, new Color(0xFFFF00)));
        g.addToDrawList(unitCube.toDrawable(new int[] {0, 3, 7, 4}, true, new Color(0xFF00FF)));
        g.addToDrawList(unitCube.toDrawable(new int[] {4, 5, 6, 7}, true, new Color(0x00FFFF)));
        //*/

        /* WALL
        Point[][] wall = new Point[10][10];

        //c.setCoordinates(new Point(5, 5, 2));
        //c.setCameraxAngle(- Math.PI /2);

        for(int y = 0; y < wall.length; y++)
        {
            for(int x = 0; x < wall[0].length; x++)
            {
                wall[y][x] = new Point(x, y, 0);
                if(y !=0 && x != 0)
                {
                    g.addToDrawList(new Matrix(new Point[] {wall[y -1][x -1], wall[y][x -1], wall[y][x], wall[y -1][x]}).toDrawable(true, new Color( (255 * 255 * 255 * y / wall.length) + (255 * 255 * x / wall[0].length) )));
                }
            }
        }

         //*/

        //* D20
        g.addToDrawList(icosahedron.toDrawable(new int[] {0, 1, 2}, true, new Color(0xFF0000)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {0, 2, 3}, true, new Color(0x00FF00)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {0, 3, 4}, true, new Color(0x0000FF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {0, 4, 5}, true, new Color(0xFFFF00)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {0, 5, 1}, true, new Color(0xFF00FF)));

        g.addToDrawList(icosahedron.toDrawable(new int[] {1, 10, 6}, true, new Color(0xFF0000)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {2, 6, 7}, true, new Color(0x00FF00)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {3, 7, 8}, true, new Color(0x0000FF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {4, 8, 9}, true, new Color(0xFFFF00)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {5, 9, 10}, true, new Color(0xFF00FF)));

        g.addToDrawList(icosahedron.toDrawable(new int[] {6, 1, 2}, true, new Color(0x00FFFF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {7, 2, 3}, true, new Color(0xFF00FF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {8, 3, 4}, true, new Color(0xFFFF00)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {9, 4, 5}, true, new Color(0x0000FF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {10, 5, 1}, true, new Color(0x00FF00)));

        g.addToDrawList(icosahedron.toDrawable(new int[] {11, 10, 6}, true, new Color(0x00FFFF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {11, 6, 7}, true, new Color(0xFF00FF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {11, 7, 8}, true, new Color(0xFFFF00)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {11, 8, 9}, true, new Color(0x0000FF)));
        g.addToDrawList(icosahedron.toDrawable(new int[] {11, 9, 10}, true, new Color(0x00FF00)));


    }
}
