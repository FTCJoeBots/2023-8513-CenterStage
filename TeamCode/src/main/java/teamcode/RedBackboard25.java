package teamcode;

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
 * This is sample code used to explain how to write an autonomous code
 *
 */

@Autonomous(name="BACKBOARD_Red50", group="red")

public class RedBackboard25 extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetector3 OD = new ObjectDetector3(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {
        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));// Starting position
        Pose2d dropPose = new Pose2d(-0.1, 0.1, Math.toRadians(-90)); // Where it goes before it drops
        Pose2d waitPose = new Pose2d(-5, 5, Math.toRadians(-90)); // Can be anything

        Pose2d Pose1r = new Pose2d(-46.6,-4.6, Math.toRadians(-90));//+ 0.1
        Pose2d Pose1c = new Pose2d(-37.9, 20.6, Math.toRadians(-192)); //+ 0.1
        Pose2d Pose1l = new Pose2d(-50.6,24.6, Math.toRadians(-85));//+ 0.1



        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive driveWait = new MecanumDrive(hardwareMap, waitPose);
        MecanumDrive driveDrop= new MecanumDrive(hardwareMap, dropPose);
        MecanumDrive driveEnd= new MecanumDrive(hardwareMap, dropPose);

        MecanumDrive drive1r = new MecanumDrive(hardwareMap, Pose1r);
        MecanumDrive drive1c = new MecanumDrive(hardwareMap, Pose1c);
        MecanumDrive drive1l = new MecanumDrive(hardwareMap, Pose1l);


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
                telemetry.addLine("Left");
                telemetry.update();
                sleep(30);
            } else if (OD.getIntLocation() == 1) {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Center");
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

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            //In position to drop the pixel
                            .strafeToLinearHeading(new Vector2d(-43.5,-4.5),Math.toRadians(-95))//6
                            .build(),

                    //Drops the pixel
                    intake.inverse(),

                    drive.actionBuilder(driveWait.pose)
                            //This lets the intake run for enough time for the pixel to drop
                            .waitSeconds(1.5)
                            .build(),

                    lift.Pos1(),
                    BucketAuto.BucketOutA(),
                    intake.stop(),


                    drive.actionBuilder(drive1r.pose)
                            .setTangent(0)
                            //Drop position
                            .strafeToLinearHeading(new Vector2d(-30.5,50.5),Math.toRadians(-97))//52.5
                            .waitSeconds(0.5)
                            .build(),

                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(driveDrop.pose)
                            .waitSeconds(0.5)
                            .setTangent(0)
                            //Parks
                            .strafeToLinearHeading(new Vector2d(25,-3),Math.toRadians(-97))
                            .build(),

                    lift.Pos0(),
                    BucketAuto.BucketOutA(),
                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(driveEnd.pose)
                            //Waits to lift to come down
                            .waitSeconds(1.5)
                            .build()

            ));

        } else if (OD.getIntLocation() == 1) { // Center

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            //In position to drop the pixel
                            .strafeToLinearHeading(new Vector2d(-36.75, 20.7),Math.toRadians(-192))//3
                            .build(),

                    //Drops the pixel
                    intake.inverse(),

                    drive.actionBuilder(driveWait.pose)
                            //This lets the intake run for enough time for the pixel to drop
                            .waitSeconds(1.5)
                            .build(),

                    lift.Pos1(),
                    BucketAuto.BucketOutA(),
                    intake.stop(),


                    drive.actionBuilder(drive1c.pose)
                            .setTangent(0)
                            //Drop position
                            .strafeToLinearHeading(new Vector2d(-35.5,25),Math.toRadians(-101))//6

                            .strafeToLinearHeading(new Vector2d(-39.0,61.75),Math.toRadians(-102))//52.5
                            .waitSeconds(0.5)
                            .build(),

                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(driveDrop.pose)
                            .waitSeconds(0.5)
                            .setTangent(0)
                            //Parks
                            .strafeToLinearHeading(new Vector2d(-33,-3),Math.toRadians(-102))
                            .build(),

                    lift.Pos0(),
                    BucketAuto.BucketOutA(),
                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(driveEnd.pose)
                            //Waits to lift to come down
                            .waitSeconds(1.5)
                            .build()));
        } else { // Left

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            //Pushes cone out of the way
                            .strafeToLinearHeading(new Vector2d(-58,24),Math.toRadians(-97))
                            //In position to drop the pixel
                            .strafeToLinearHeading(new Vector2d(-50.5,29.5),Math.toRadians(-99))
                            .build(),

                    //Drops the pixel
                    intake.inverse(),

                    drive.actionBuilder(driveWait.pose)
                            //This lets the intake run for enough time for the pixel to drop
                            .waitSeconds(1.5)
                            .build(),

                    lift.Pos1(),
                    BucketAuto.BucketOutA(),
                    intake.stop(),


                    drive.actionBuilder(drive1l.pose)
                            .setTangent(0)
                            //Drop position
                            .strafeToLinearHeading(new Vector2d(-60.5,51.5),Math.toRadians(-97))// y = 53.5, 54
                            .waitSeconds(0.5)
                            .build(),

                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(driveDrop.pose)
                            .waitSeconds(0.5)
                            .setTangent(0)
                            //Parks
                            .strafeToLinearHeading(new Vector2d(0.2,-10),Math.toRadians(-97))
                            .strafeToLinearHeading(new Vector2d(55,-11),Math.toRadians(-97))
                            .build(),

                    lift.Pos0(),
                    BucketAuto.BucketOutA(),
                    BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(driveEnd.pose)
                            //Waits to lift to come down
                            .waitSeconds(1.5)
                            .build()));

        } if(isStopRequested()) return;}}