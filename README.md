# 3D-Graphics-Engine

##Description
A graphics engine that can draw and transform 3D shapes given in matrix form in real time.

Input a 3D shape in matrix form, select which faces are used and set the transformation.
If the camera is facing at the shape then the window will depict the shape transforming indefinately

Examples are given in the Main class that can be run immediately

##Overview of Classes

###Matrix
* Holds a 2D array represeting the objects matrix and its values.
* This class also has various utility functions that can be used on matrices (e.g. multiplication, addition, translations)
* A Matrix can represent an abitrary matrix, a shape or a transformation.
* Can create new Drawables by using the indexs of the points in the matrix (toDrawable function)

###Drawable
* Inherits from Matrix
* Used to represent faces of shapes as an array of points
* Stores the faces colour and whether it is filled

###Camera
* Turns 2D faces in the 3D field into 2D visual on the GUI window (mapTo2d function)
* Stores the vertical and horisontal field of view as well as the direction the camera is facing and its coordinates

###Main
* Standard main class, creates appropriate objects to run the code
* Only this class should need to be edited when experimenting with code

###GUI
* Used to create a window to show visuals.
* Refreshes window every 1/frames seconds.
* Contains a drawList which holds all of the faces of shapes that should be painted on the canvas.
* Also holds a Camera which tells it what to draw, GUI scales this information for the window.
* Holds a transformation which is applied to the drawList faces every time the window is refreshed.

###Point
* Holds an x, y and z value for its 3D location
* Has some utility functions (e.g. distance between, whether lines intersect)
* Mostly used for setting and changing a Cameras location

##Future Improvements
* Make a function in the Matrix class to automatically generate faces of a shape and give colours so they dont have to be input manually
* Clean up code (this project was started in 2019)
