package org.firstinspires.ftc.teamcode;

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

@Autonomous(name="BackBoardBlue25", group="Pushbot")

public class BlueBackboard25 extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetector1 OD = new ObjectDetector1(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {

        Pose2d startPose = new Pose2d(-65, 36, Math.toRadians(-90));
        Pose2d Pose1 = new Pose2d(-20, 32, Math.toRadians(-90));
        Pose2d Pose1c = new Pose2d(-25,43.4, Math.toRadians(16));
        Pose2d Pose1l = new Pose2d(-33.0, 54.0, Math.toRadians(-85));
        Pose2d EndPose;

        Intake intake = new Intake();
        Bucket BucketAuto = new Bucket();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive drive1r = new MecanumDrive(hardwareMap, Pose1);

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
                telemetry.addLine("RIGHT");
                telemetry.update();
                sleep(30);
            } else if (OD.getIntLocation() == 1) {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("CENTER");
                telemetry.update();
                sleep(30);

            } else {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("LEFT");
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
                            .strafeToLinearHeading(new Vector2d(-25,30.5),Math.toRadians(-75))
                            .build(),

                    intake.inverse(),

                    drive.actionBuilder(drive1r.pose)
                            .waitSeconds(2)
                            .setTangent(0)
                            .splineToLinearHeading(new Pose2d(8.0,82.0, Math.toRadians(-82)),0)
                            .build()
                   // lift.Pos1(),
                  // BucketAuto.BucketOutA(),
                  // BucketAuto.BucketGateOutA(),

            ));

        } else if (OD.getIntLocation() == 2) { // Center

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-25.9,49.0),Math.toRadians(16)) // -27.2,42.4 & 16
                            .build(),

                    intake.inverse(),

                    drive.actionBuilder(drive1c.pose)
                            .waitSeconds(1)
                            .build(),

                    intake.stop(),
                   // BucketAuto.BucketGateOutA(),

                    drive.actionBuilder(drive1c.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(-45.0,38),Math.toRadians(16))
                            .strafeToLinearHeading(new Vector2d(-50,90),Math.toRadians(32))
                            .strafeToLinearHeading(new Vector2d(-40,110),Math.toRadians(32))//-45, 132.4
                            //BOARD POS -50,90 (32)
                            .build()));
        } else { // Left

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                           .strafeToLinearHeading(new Vector2d(-21,61.8),Math.toRadians(-85))
                            .build(),

                    intake.inverse(),

                    drive.actionBuilder(drive1l.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(5,75),Math.toRadians(-79))
                            .build()));

        } if(isStopRequested()) return;}}