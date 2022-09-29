package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI
{
    private final int width;
    private final int height;
    private final int frames;
    private ArrayList<Drawable> drawList = new ArrayList<>();

    private Camera c;
    private Matrix transformation;

    public GUI(int wwidth, int hheight, int fframes, Camera cc)
    {
        width = wwidth;
        height = hheight;
        frames = fframes;
        c=cc;

        JFrame window = new JFrame("3D?");
        JPanel canvas = new JPanel()
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                for(Drawable m : drawList)
                {
                    m.paint(c, g);
                }
            }
        };

        window.add(canvas);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(width, height));
        window.pack();
        window.setVisible(true);

        Thread loop = new Thread()
        {
            public void run()
            {
                while(true)
                {
                    try { Thread.sleep(1000 / frames); }
                    catch(InterruptedException e) { e.printStackTrace(); }
                    super.run();
                    //where transformation happens
                    sortDrawList();
                    //c.moveCamera(new double[] {0, -0.01, 0});

                    canvas.repaint();
                    //System.out.println("Looping");
                }

            }
        };

        loop.start();
    }

    public void addToDrawList(Drawable m) { drawList.add(m); }

    private void sortDrawList() //sort furthest to nearest from camera, works (I think), also does transformations
    {
        double[] priority = new double[drawList.size()];
        for(int i=0; i<drawList.size(); i++)
        {
            //drawList.get(i).translate(new Point(0.5/frames, 0, 0.5/frames)); //temporary
            drawList.get(i).transform(transformation); //standard
            //drawList.get(i).display(); //debug
            priority[i] = drawList.get(i).averagePoint().distanceTo(c.getPoint());
        }
        ArrayList<Drawable> temp = new ArrayList<>();
        double highest;
        int index=0;
        for(int y=0; y<drawList.size(); y++)
        {
            highest = 0; //points average may be below 0, don't think so, distances
            for(int x=0; x<drawList.size(); x++)
            {
                if(priority[x]>highest)
                {
                    //System.out.println("Furthest is shape index "+index);
                    highest = priority[x];
                    index = x;
                }
            }
            //System.out.println(index+" ordered in draw list");
            priority[index] = 0;    //same problem here, or not?
            temp.add(drawList.get(index));
        }
        drawList = temp;
    }

    public void setTransformation(Matrix t) { transformation = t; }

}
