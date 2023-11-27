package teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;


/**
 * This is sample code used to explain how to write an autonomous code
 *
 */

@Autonomous(name="WING_Red50", group="red")

public class RedWing25 extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetector2 OD = new ObjectDetector2(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {

        View relativeLayout;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        relativeLayout.post(() -> relativeLayout.setBackgroundColor(Color.rgb(214, 41, 41)));

        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));
        Pose2d waitPose = new Pose2d(1, 1, Math.toRadians(-90));

        Pose2d startPoser = new Pose2d(-3, 0, Math.toRadians(-91));
        Pose2d Pose1 = new Pose2d(40.1, -5, Math.toRadians(-85));
        Pose2d Pose1c = new Pose2d(54,17.5, Math.toRadians(-84));
        Pose2d Pose1l = new Pose2d(52, 24.2958, Math.toRadians(-83));
        Pose2d Pose1r = new Pose2d(47.5,-8.1, Math.toRadians(-83));
       // Pose2d Pose2l = new Pose2d(21.94,-73.869, Math.toRadians(106));
        Pose2d Pose15l = new Pose2d(52.555,-77.5455, Math.toRadians(106)); //52.5,-90.5
        Pose2d Pose15r = new Pose2d(52.5,-90.5, Math.toRadians(106)); //52.5,-90.5
        Pose2d Pose2l = new Pose2d(52.55,-77.545, Math.toRadians(106));
        Pose2d Pose3l = new Pose2d(51.5,-101.9, Math.toRadians(105));
        Pose2d Pose4l = new Pose2d(23.45,-79.8, Math.toRadians(104));
        Pose2d Pose5l = new Pose2d(25.3851,-103.7629341, Math.toRadians(104));
        Pose2d EndPose;

        Intake intake = new Intake();
        Bucket BucketAuto = new Bucket();

        AutoCords Cords = new AutoCords();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive driveWait = new MecanumDrive(hardwareMap, waitPose);

        MecanumDrive drive15l = new MecanumDrive(hardwareMap, Pose15l);
        MecanumDrive drive15r = new MecanumDrive(hardwareMap, Pose15r);
        MecanumDrive drive2l = new MecanumDrive(hardwareMap, Pose2l);
        MecanumDrive drive3l = new MecanumDrive(hardwareMap, Pose3l);
        MecanumDrive drive4l = new MecanumDrive(hardwareMap, Pose4l);
        MecanumDrive drive5l = new MecanumDrive(hardwareMap, Pose5l);
        MecanumDrive drive1r = new MecanumDrive(hardwareMap, Pose1r);

        MecanumDrive drive1c = new MecanumDrive(hardwareMap, Pose1c);

        MecanumDrive drive1l = new MecanumDrive(hardwareMap, Pose1l);

        Lift lift = new Lift();

        lift.init(hardwareMap);
        intake.init(hardwareMap);
        BucketAuto.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(OD);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);}
            @Override
            public void onError(int errorCode) {}});

        while(!isStarted()) {
            if (OD.getIntLocation() == 2) {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Center");
                telemetry.update();
                sleep(30);
            } else if (OD.getIntLocation() == 1) {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Left");
                telemetry.update();
                sleep(30);

            } else {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Right");
                telemetry.update();
                sleep(30);
            }
        }
        waitForStart();
        telemetry.addLine("Running Auto");
        telemetry.update();

        //

        //sleep(5000);

        if (OD.getIntLocation() == 3){ // Right (GONE)

            /*Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(43,-3),Math.toRadians(-85))
                            .strafeToLinearHeading(new Vector2d(43.5,-7.1),Math.toRadians(-85))
                            .build(),
                    // Drop the purple Pixel
                    intake.inverse(),

                    drive.actionBuilder(drive1r.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(47,5.1),Math.toRadians(-85))
                            // Move the robot to the center
                            .strafeToLinearHeading(new Vector2d(78.1,5.23),Math.toRadians(-74)) //x = 85,80
                            // Mover the robot near the Backdrop
                            .strafeToLinearHeading(new Vector2d(82.2,-92.97),Math.toRadians(-73)) //x = 85,80
                            // Turn the robot so that the bucket faces the backdrop
                            .strafeToLinearHeading(new Vector2d(52.5,-90.5),Math.toRadians(110))
                            .build(),

                    drive.actionBuilder(drive15r.pose)
                            .waitSeconds(1)

                            // Move the robot forward to drop the pixel
                            .strafeToLinearHeading(new Vector2d(45.323,-80.3569),Math.toRadians(120))//
                            .strafeToLinearHeading(new Vector2d(32.723,-80.3969),Math.toRadians(120))//

                            //.strafeToLinearHeading(new Vector2d(18.323,-54.6569),Math.toRadians(120))//71
                            .build(),
                    // Set the bucket in the position to drop the pixel
                    lift.Pos1(),
                    // Sets the Bucket
                    BucketAuto.BucketOutA(),

                    drive.actionBuilder(drive2l.pose)
                            .waitSeconds(3)

                            // Move the robot forward to drop the pixel
                            //.strafeToLinearHeading(new Vector2d(22.323,-10.6969),Math.toRadians(121))//71
                            .build(),

                    // DROPS PIXEL
                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(drive3l.pose)
                            .waitSeconds(1)
                            .build(),
                    // Align to Park
                    drive.actionBuilder(drive4l.pose)
                            .waitSeconds(1)
                            .strafeToLinearHeading(new Vector2d(20.585,-40.892939),Math.toRadians(121))
                            .build(),

                    lift.Pos0(),
                    // Parks
                    drive.actionBuilder(drive5l.pose)
                            .waitSeconds(2)
                            // .strafeToLinearHeading(new Vector2d(15,-101.2),Math.toRadians(116))
                            .build()
            ));*/
///*
            int run3=0;

            while (run3 <= 12){//This is dif
//This is dif
//This is dif

                drive.actionBuilder(drive.pose)
                        .strafeToLinearHeading(new Vector2d(Cords.RedB3(run3, "x"), Cords.RedB3(run3, "y")),Math.toRadians(Cords.RedB3(run3, "deg")))
                        .build();

                run3+=1;
            }

            drive.actionBuilder(driveWait.pose)
                    .waitSeconds(2)
                    .build();
//*/
        } else if (OD.getIntLocation() == 2) { // Center

           /* Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(57.75,13),Math.toRadians(-85))//16 y

                            .build(),
                    // Drop the purple Pixel
                    intake.inverse(),

                    drive.actionBuilder(drive1l.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            // Move the robot to the center
                            .strafeToLinearHeading(new Vector2d(75.1,15.23),Math.toRadians(-74)) //x = 85,80
                            // Mover the robot near the Backdrop
                            .strafeToLinearHeading(new Vector2d(75.2,-84.97),Math.toRadians(-73)) //x = 85,80
                            // Turn the robot so that the bucket faces the backdrop
                            .strafeToLinearHeading(new Vector2d(52.5,-89.5),Math.toRadians(110))
                            .build(),

                    drive.actionBuilder(drive15l.pose)
                            .waitSeconds(1)

                            // Move the robot forward to drop the pixel
                            .strafeToLinearHeading(new Vector2d(45.323,-63.9569),Math.toRadians(120))// y = 58, 54, 52 || x = 22, 28
                            .strafeToLinearHeading(new Vector2d(40.323,-63.9969),Math.toRadians(120))// y = 58, 54, 52 || x = 22, 28

                            //.strafeToLinearHeading(new Vector2d(18.323,-54.6569),Math.toRadians(120))//71
                            .build(),
                    // Set the bucket in the position to drop the pixel
                    lift.Pos1(),
                    // Sets the Bucket
                    BucketAuto.BucketOutA(),

                    drive.actionBuilder(drive2l.pose)
                            .waitSeconds(3)

                            // Move the robot forward to drop the pixel
                            //.strafeToLinearHeading(new Vector2d(22.323,-10.6969),Math.toRadians(121))//71
                            .build(),

                    // DROPS PIXEL
                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(drive3l.pose)
                            .waitSeconds(1)
                            .build(),
                    // Align to Park
                    drive.actionBuilder(drive4l.pose)
                            .waitSeconds(1)
                            .strafeToLinearHeading(new Vector2d(20.585,-40.892939),Math.toRadians(121))
                            .build(),

                    lift.Pos0(),
                    // Parks
                    drive.actionBuilder(drive5l.pose)
                            .waitSeconds(2)
                            // .strafeToLinearHeading(new Vector2d(15,-101.2),Math.toRadians(116))
                            .build()));*/
///*

            int run2=0;

            while (run2 <= 10){
                drive.actionBuilder(drive.pose)
                        .strafeToLinearHeading(new Vector2d(Cords.RedB2(run2, "x"), Cords.RedB2(run2, "y")),Math.toRadians(Cords.RedB2(run2, "deg")))
                        .build();

                run2+=1;
            }

            drive.actionBuilder(driveWait.pose)
                    .waitSeconds(2)
                    .build();
//*/

        } else { // Left


           /* Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            // Take the robot to the Spike mark
                            .strafeToLinearHeading(new Vector2d(75.594,12.7),Math.toRadians(-85))
                            .strafeToLinearHeading(new Vector2d(52.594,22.7),Math.toRadians(-85))
                            .build(),
                    // Drop the purple Pixel
                    intake.inverse(),

                    drive.actionBuilder(drive1l.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            // Move the robot to the center
                            .strafeToLinearHeading(new Vector2d(85.1,10.23),Math.toRadians(-74))
                            // Mover the robot near the Backdrop
                            .strafeToLinearHeading(new Vector2d(82.5,-90.97),Math.toRadians(-73))
                            // Turn the robot so that the bucket faces the backdrop
                            .strafeToLinearHeading(new Vector2d(52.5,-97.5),Math.toRadians(110))
                            .build(),
                    // Set the bucket in the position to drop the pixel
                    lift.Pos1(),
                    // Sets the Bucket
                    BucketAuto.BucketOutA(),

                    drive.actionBuilder(drive2l.pose)
                            .waitSeconds(1)

                            // Move the robot forward to drop the pixel
                            .strafeToLinearHeading(new Vector2d(23.323,-74.06734),Math.toRadians(115))//78
                            .build(),

                    // Opens the bucket gate
                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(drive3l.pose)
                            .waitSeconds(1)
                            .build(),
                    // Align to Park
                    drive.actionBuilder(drive4l.pose)
                            .waitSeconds(1)
                            .strafeToLinearHeading(new Vector2d(25.585,-103.792939),Math.toRadians(116))//25
                            .build(),

                    lift.Pos0(),
                    // Parks
                    drive.actionBuilder(drive5l.pose)
                            .waitSeconds(2)
                           // .strafeToLinearHeading(new Vector2d(15,-101.2),Math.toRadians(116))
                            .build()

                    ));*/
///*/

            int run1=0;

            while (run1 <= 11){
                drive.actionBuilder(drive.pose)
                        .strafeToLinearHeading(new Vector2d(Cords.RedB1(run1, "x"), Cords.RedB1(run1, "y")),Math.toRadians(Cords.RedB1(run1, "deg")))
                        .build();

                run1+=1;
            }

            drive.actionBuilder(driveWait.pose)
                    .waitSeconds(2)
                    .build();

//*/

        } if(isStopRequested()) return;}}