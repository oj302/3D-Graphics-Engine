package main;

public class Camera
{
    private double xfov;    //horizontal field of view, vertical is scaled using dimensions of window
    private double yfov;
    private double cameraxAngle;    //camera angles are relative to looking down the z axis horizontally (CW)
    private double camerayAngle;
    private Point coordinates; //only 3 by 1 matrices taken, coordinates of camera view point
    public Camera(double ffov, double wtohRatio, Point ccoordinates)
    {
        xfov = ffov;
        yfov = xfov / wtohRatio;
        coordinates = ccoordinates;
        //cameraxAngle = 0;
        //cameraxAngle = Math.PI /4; //standard
        //camerayAngle = -Math.PI /4; //standard
        cameraxAngle = 0;//-Math.PI / 4;
        camerayAngle = 0;//-Math.PI / 4;
    }

    //!!!!!!!!!!!!!!!!!!!!IMPORTANT BIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //these 4 functions dont work
    //public double findxAngle(Point point) { return Math.atan((point.getx() - coordinates.getx()) / Math.sqrt( Math.pow((point.getz() - coordinates.getz()), 2) + Math.pow((point.gety() - coordinates.gety()), 2)  )) - cameraxAngle; }
    //public double findyAngle(Point point) { return Math.atan((point.gety() - coordinates.gety()) / Math.sqrt( Math.pow((point.getz() - coordinates.getz()), 2) + Math.pow((point.getx() - coordinates.getx()), 2)  )) - camerayAngle; } //probably not correct
    //public double findxAngle(Point point) { return Math.atan((point.getx() - coordinates.getx()) / (point.getz() - coordinates.getz())) - cameraxAngle; }   //could get a /0 error
    //public double findyAngle(Point point) { return Math.atan((point.gety() - coordinates.gety()) / (point.getz() - coordinates.getz())) - camerayAngle; }

    //new system:
    //camera might also pick up whats behind it flipped
    public double findXAngle(Point point)
    {
        //equations might need to change based on cameraYAngle, seems dumb in retrospect
        //could cause math error problems
        double x;
        double z;
        //point coordinates relative to camera
        double pointX = point.getx() -coordinates.getx();
        double pointY = point.gety() -coordinates.gety();
        double pointZ = point.getz() -coordinates.getz();

        if(cameraxAngle == 0)
        {
            x = 0;
            z = point.getz() - coordinates.getz();
        }

        else
        {
            double m = -1 / Math.tan(cameraxAngle);
            x = (pointX + (m * pointZ)) / (Math.pow(m, 2) + 1);
            z = x * m;
        }

        //perpendicular points z will be the same as points z as they are on the same horizontal plane
        //maybe remove y factor?
        double distanceFromCamera = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2) + Math.pow(pointY, 2));

        double fovWidth = distanceFromCamera * Math.tan(xfov/2);

        //add y here too?
        //probably not as perpendicular point and point will have the same y, difference will be 0
        double pointDistanceFromCentre = Math.sqrt(Math.pow(x - pointX, 2) + Math.pow(z - pointZ, 2));

        //when getting absolute distance its always positive
        int mod = 1;
        if(x > pointX) { mod *= -1; }

        return mod * pointDistanceFromCentre / fovWidth;
    }

    public double findYAngle(Point point)
    {
        //some slight differences in each function
        double pointX = Math.sqrt(Math.pow(point.getx() - coordinates.getx(), 2) + Math.pow(point.getz() - coordinates.getz(), 2));
        double pointY = point.gety() - coordinates.gety();

        double m = Math.tan(camerayAngle);

        double x = (pointX + (m * pointY )) / (Math.pow(m, 2) + 1);
        double y = m * x;

        double distanceFromCamera = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        double fovWidth = distanceFromCamera * Math.tan(yfov/2);

        double pointDistanceFromCentre = Math.sqrt(Math.pow(pointX - x, 2) + Math.pow(pointY - y, 2));

        int mod = 1;
        if(y > pointY) { mod *= -1; }

        return mod * pointDistanceFromCentre / fovWidth;
    }

    public Point getPoint() { return coordinates; }
    public double[] mapTo2d(Point point) // (hopefully) gives numbers between -1 and 1 for points (if in fov)
    {
        //old system
        /*
        double x = findxAngle(point) / (2*xfov);
        double y = findyAngle(point) / (2*yfov);


         //*/

        //*new system:
        double x = findXAngle(point);
        double y = findYAngle(point);
        //System.out.println("x: "+x+" y: "+y);
         //*/
        return new double[] {x, y}; //makes more sense flipped, maybe????
    }

    public void setCameraxAngle(double ccameraxAngle) {cameraxAngle = ccameraxAngle; }
    public void setCamerayAngle(double ccamerayAngle) {camerayAngle = ccamerayAngle; }
    public void setCoordinates(Point ccoordinates) { coordinates = ccoordinates; }
    public void moveCamera(double[] vector) { coordinates = new Point(coordinates.getx() + vector[0], coordinates.gety() + vector[1], coordinates.getz() + vector[2]); }
}

