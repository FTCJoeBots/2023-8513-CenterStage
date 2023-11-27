package teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

/**
 * For the Blue Wing side, a 48 point Auto and made by Divyang Verma
 */

@Autonomous(name="WING_Blue50", group="blue")

public class BlueWing50 extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetector4 OD = new ObjectDetector4(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {

        View relativeLayout;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        relativeLayout.post(() -> relativeLayout.setBackgroundColor(Color.rgb(42, 42, 222)));

        // Works for all (start)
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        // Works for 1 (Right)
        Pose2d Pose1lL = new Pose2d(-52.5948,26.28, Math.toRadians(-83));
        Pose2d Pose2lL = new Pose2d(-69.78,-91.53588, Math.toRadians(75));
        Pose2d Pose4lL = new Pose2d(-85.8,-152.058, Math.toRadians(75));
        Pose2d Pose5lL = new Pose2d(-25.3851,-103.7629341, Math.toRadians(75));

        // 1 (Right)
        MecanumDrive drive2lL = new MecanumDrive(hardwareMap, Pose2lL);
        MecanumDrive drive4lL = new MecanumDrive(hardwareMap, Pose4lL);
        MecanumDrive drive5lL = new MecanumDrive(hardwareMap, Pose5lL);
        MecanumDrive drive1lL = new MecanumDrive(hardwareMap, Pose1lL);


        // Works for 2 (Center)
        Pose2d Pose1c = new Pose2d(-46.751,12.952, Math.toRadians(-196));
        Pose2d Pose15c = new Pose2d(-46.752,12.951, Math.toRadians(-196));
        Pose2d Pose1l = new Pose2d(-52, 24.2958, Math.toRadians(-83));

        Pose2d Pose2l = new Pose2d(-28.7,-35.535, Math.toRadians(75));
        Pose2d Pose3l = new Pose2d(-51.5,-101.9, Math.toRadians(75));
        Pose2d Pose4l = new Pose2d(-8.71,-31.53352, Math.toRadians(75));
        Pose2d Pose5l = new Pose2d(-25.3851,-103.7629341, Math.toRadians(75));

       // 2 (Center)
        MecanumDrive drive2l = new MecanumDrive(hardwareMap, Pose2l);
        MecanumDrive drive3l = new MecanumDrive(hardwareMap, Pose3l);
        MecanumDrive drive4l = new MecanumDrive(hardwareMap, Pose4l);
        MecanumDrive drive5l = new MecanumDrive(hardwareMap, Pose5l);
        MecanumDrive drive1c = new MecanumDrive(hardwareMap, Pose1c);
        MecanumDrive drive15c = new MecanumDrive(hardwareMap, Pose15c);
        MecanumDrive drive1l = new MecanumDrive(hardwareMap, Pose1l);



        // Works for 3 (Left)
        Pose2d Pose1cR = new Pose2d(-46.7511,12.9521, Math.toRadians(-106));
        Pose2d Pose15cR = new Pose2d(-46.7521,12.9511, Math.toRadians(-106));
        Pose2d Pose2lR = new Pose2d(-28.71,-35.5351, Math.toRadians(75));
        Pose2d Pose4lR = new Pose2d(-8.711,-31.533521, Math.toRadians(75));
        Pose2d Pose5lR = new Pose2d(-25.38511,-103.762934111, Math.toRadians(75));

        // 3 (Left)
        MecanumDrive drive2lR = new MecanumDrive(hardwareMap, Pose2lR);
        MecanumDrive drive4lR = new MecanumDrive(hardwareMap, Pose4lR);
        MecanumDrive drive5lR = new MecanumDrive(hardwareMap, Pose5lR);
        MecanumDrive drive1cR = new MecanumDrive(hardwareMap, Pose1cR);
        MecanumDrive drive15cR = new MecanumDrive(hardwareMap, Pose15cR);


        Lift lift = new Lift();
        Intake intake = new Intake();
        Bucket BucketAuto = new Bucket();

        lift.init(hardwareMap);
        intake.init(hardwareMap);
        BucketAuto.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "AprilTagCam"), cameraMonitorViewId);
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


        if (OD.getIntLocation() == 3){ // Right (GONE)

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-43,-3.03),Math.toRadians(-95))
                           // .strafeToLinearHeading(new Vector2d(-43.5,-3.8),Math.toRadians(-95))
                            .build(),

                    // Drop the purple Pixel
                    intake.inverse(),

                    drive.actionBuilder(drive15cR.pose)
                            .waitSeconds(1.1)
                            .build(),

                    intake.stop(),


                    drive.actionBuilder(drive1cR.pose)
                            .waitSeconds(0.1)
                            .setTangent(0)
                            // Move the robot to the center
                            .strafeToLinearHeading(new Vector2d(-85.1,30.23),Math.toRadians(-107))
                            // Mover the robot near the Backdrop
                            .strafeToLinearHeading(new Vector2d(-86.5,-37.97),Math.toRadians(-108))
                            // Turn the robot so that the bucket faces the backdrop
                            .strafeToLinearHeading(new Vector2d(-28.7,-35.535),Math.toRadians(59))
                            .build(),

                    // Set the bucket in the position to drop the pixel
                    lift.Pos1(),
                    // Sets the Bucket
                    BucketAuto.BucketOutA(),

                    drive.actionBuilder(drive2lR.pose)
                            .waitSeconds(1)
                            // Move the robot forward to drop the pixel
                            .strafeToLinearHeading(new Vector2d(-1.3,-62.0000),Math.toRadians(88))// x =  ; y = 30, 43,
                            .build(),

                    // Opens the bucket gate
                    BucketAuto.BucketGateOutA(),


                    // Align to Park
                    drive.actionBuilder(drive4lR.pose)
                            .waitSeconds(1.8)
                            .strafeToLinearHeading(new Vector2d(-7.4,-67.535),Math.toRadians(87))//
                            .build(),

                    lift.Pos0(),
                    BucketAuto.BucketGateOutA(),
                    BucketAuto.BucketOutA(),

                    // Parks
                    drive.actionBuilder(drive5lR.pose)
                            .waitSeconds(2)
                            .build()
            ));

        } else if (OD.getIntLocation() == 2) { // Center

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-35.55,18.75),Math.toRadians(-200))
                            .build(),

                    // Drop the purple Pixel
                    intake.inverse(),

                    drive.actionBuilder(drive15c.pose)
                            .waitSeconds(1.75)
                            .build(),

                    intake.stop(),


                    drive.actionBuilder(drive1c.pose)
                            .waitSeconds(1)
                            .setTangent(0)

                            .strafeToLinearHeading(new Vector2d(-34.85,32.75),Math.toRadians(-200))//x =  ; y =   ; Deg =

                            // Move the robot to the center
                            .strafeToLinearHeading(new Vector2d(-85.1,30.23),Math.toRadians(-107))
                            // Mover the robot near the Backdrop
                            .strafeToLinearHeading(new Vector2d(-86.5,-37.97),Math.toRadians(-108))
                            // Turn the robot so that the bucket faces the backdrop
                            .strafeToLinearHeading(new Vector2d(-28.7,-35.535),Math.toRadians(59))//
                            .build(),

                    // Set the bucket in the position to drop the pixel
                    lift.Pos1(),
                    // Sets the Bucket
                    BucketAuto.BucketOutA(),

                    drive.actionBuilder(drive2l.pose)
                            .waitSeconds(1)
                            // Move the robot forward to drop the pixel
                            .strafeToLinearHeading(new Vector2d(-2.0,-23.5353),Math.toRadians(88))// x =  ; y =
                            .build(),

                    // Opens the bucket gate
                    BucketAuto.BucketGateOutA(),


                    // Align to Park
                    drive.actionBuilder(drive4l.pose)
                            .waitSeconds(1.8)
                            .strafeToLinearHeading(new Vector2d(-7.4,-63.535),Math.toRadians(87))//
                            .build(),

                    lift.Pos0(),
                    BucketAuto.BucketGateOutA(),
                    BucketAuto.BucketOutA(),

                    // Parks
                    drive.actionBuilder(drive5l.pose)
                            .waitSeconds(2)
                            .build()
            ));

        } else { // Left

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            // Take the robot to the Spike mark
                            .strafeToLinearHeading(new Vector2d(-77.594,12.7),Math.toRadians(-95))
                            .strafeToLinearHeading(new Vector2d(-52.594,28.02),Math.toRadians(-95)) // 26.2
                            .build(),
                    // Drop the purple Pixel
                    intake.inverse(),

                    drive.actionBuilder(drive15c.pose)
                            .waitSeconds(1.5)
                            .build(),

                    intake.stop(),

                    drive.actionBuilder(drive1lL.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            // Move the robot to the center
                            .strafeToLinearHeading(new Vector2d(-76.1,20.23),Math.toRadians(-105))
                            // Mover the robot near the Backdrop
                            .strafeToLinearHeading(new Vector2d(-79.5,-90.97),Math.toRadians(-106))
                            // Turn the robot so that the bucket faces the backdrop
                            //.strafeToLinearHeading(new Vector2d(-52.5,-91.5),Math.toRadians(75))//110
                            .strafeToLinearHeading(new Vector2d(-69.7,-91.535),Math.toRadians(75))//110
                            .build(),
                    // Set the bucket in the position to drop the pixel
                    lift.Pos1(),
                    // Sets the Bucket
                    BucketAuto.BucketOutA(),

                    drive.actionBuilder(drive2lL.pose)
                            .waitSeconds(1)
                            // Move the robot forward to drop the pixel
                            .strafeToLinearHeading(new Vector2d(-83.8,-153.0),Math.toRadians(79))
                            .strafeToLinearHeading(new Vector2d(-87.8,-155.0),Math.toRadians(65))
                            .build(),

                    // Opens the bucket gate
                    BucketAuto.BucketGateOutA(),

                    // Align to Park
                    drive.actionBuilder(drive4lL.pose)
                            .waitSeconds(2)
                            .strafeToLinearHeading(new Vector2d(-81.885,-180.792939),Math.toRadians(71))//y = 85.785, 84... (less W),
                            .build(),

                    lift.Pos0(),
                    // Parks
                    drive.actionBuilder(drive5lL.pose)
                            .waitSeconds(2)
                            .build()

            ));

        } if(isStopRequested()) return;}}

