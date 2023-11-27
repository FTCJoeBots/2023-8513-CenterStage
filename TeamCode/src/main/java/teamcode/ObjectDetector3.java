package teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

// Commit the file
public class ObjectDetector3 extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();
    Mat bluemat = new Mat();
    public enum Location {
        LEFT,
        RIGHT,
        NOT_FOUND
    }
    public Location location;
    private int IntLocation=-1;

    //draws rectangles(make sure x max is 320 and y is 240(in ObjectDetectAuto webcam.startstreaming))
    //Rectangles for detecting if object of a certain color is in it and what percentage it is at
    static final Rect LEFT_ROI_B = new Rect(
            new Point(150, 120),
            new Point(260, 245)
            //new Point(0, 120),
            //new Point(130, 300)

    );
    static final Rect RIGHT_ROI_B = new Rect(
            new Point(480, 140),
            new Point(610, 315));

    /*left rect. 80, 240
190, 300
left rect. 440, 220
570, 395
 */
    static final Rect RIGHT_BLUE_B = new Rect(
            new Point(300, 130),
            new Point(410, 255));

    static final Rect LEFT_BLUE_B = new Rect(
            new Point(0, 60),
            new Point(70, 255));
    // new Point(360, 120),
    //new Point(490, 300));



    //determines what percentage has to fill box
    static double PERCENT_COLOR_THRESHOLD = 0.2;

    public ObjectDetector3(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {
        //color stuff
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        //Scalar lowHSV = new Scalar(23, 50, 70);
        // Scalar highHSV = new Scalar(32, 255, 255);



        Scalar redlowHSV = new Scalar(0, 30, 0);
        Scalar redhighHSV = new Scalar(30, 305, 305);




//160,250
        //V was 150

        Core.inRange(mat, redlowHSV, redhighHSV, mat);

        Mat left = mat.submat(LEFT_ROI_B);
        Mat right = mat.submat(RIGHT_ROI_B);



        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI_B.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI_B.area() / 255;

        left.release();
        right.release();


        //telemtry for values and percents
        telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
        telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
        telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
        telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");

        telemetry.update();

        //boolean stating that element are either right or left if the box is filled to more than 'PERCENT_COLOR_THRESHOLD'
        boolean elementLeft = leftValue > PERCENT_COLOR_THRESHOLD;
        boolean elementRight = rightValue > PERCENT_COLOR_THRESHOLD;


        //tells location of element(left right or not found(looks for stuff in rectangles))
        if (!elementLeft && !elementRight){
            //if element not found, it say not found
            location = Location.NOT_FOUND;
            IntLocation=3;
            //  telemetry.addData("Element Location", "not found; on left side");
        }
        else if (elementLeft || elementLeft) {
            //if 40% or more of element in left rectangle, it say left
            location = Location.LEFT;
            IntLocation=1;
            //telemetry.addData("Element Location", "middle");
        }
        else {
            //if 40% or more of element in right rectangle, it say right
            //center
            location = Location.RIGHT;
            IntLocation=2;
            // telemetry.addData("Element Location", "right");
        }
        //telemetry.update();

        //turns everything that not the color we look for into black

        //blue mat first for blue side
        //  Imgproc.cvtColor(bluemat, mat, Imgproc.COLOR_GRAY2RGB);
        Imgproc.cvtColor( mat, bluemat, Imgproc.COLOR_GRAY2RGB);

        // color of rectangles in camera stream
        Scalar colorStone = new Scalar(255, 0, 0);
        Scalar colorSkystone = new Scalar(0, 255, 0);

        Imgproc.rectangle(mat, LEFT_ROI_B, location == Location.LEFT? colorSkystone:colorStone);
        Imgproc.rectangle(mat, RIGHT_ROI_B, location == Location.RIGHT? colorSkystone:colorStone);
        Imgproc.rectangle(bluemat, LEFT_ROI_B, location == Location.LEFT? colorSkystone:colorStone);
        Imgproc.rectangle(bluemat, RIGHT_ROI_B, location == Location.RIGHT? colorSkystone:colorStone);

        return mat;
    }

    //takes location of element and gives it to us
    public Location getLocation() {
        return location;
    }
    public int getIntLocation() {return IntLocation;}
}