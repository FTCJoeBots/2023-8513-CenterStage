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

@Autonomous(name="BackBoardRed25", group="Pushbot")

public class RedBackboard25 extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetector3 OD = new ObjectDetector3(telemetry);
    private ElapsedTime     runtime = new ElapsedTime();



    @Override
    public void runOpMode() {


        Pose2d startPose = new Pose2d(0.0, 0.0, Math.toRadians(-90));
        Pose2d l1 = new Pose2d(36.21,2.751, Math.toRadians(95));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive drivel1 = new MecanumDrive(hardwareMap, l1);


        Lift lift = new Lift();
        Intake intake = new Intake();
        Bucket BucketAuto = new Bucket();

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
                telemetry.addLine("Right");
                telemetry.update();
                sleep(30);

            } else {
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("Left");
                telemetry.update();
                sleep(30);
            }
        }
        waitForStart();
        telemetry.addLine("Running Auto");
        telemetry.update();

        //

        //sleep(5000);

        if (OD.getIntLocation() == 3){ // Left

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(10.5,-10.5),Math.toRadians(-90)) // y4
                            .waitSeconds(0.8)
                            .strafeToLinearHeading(new Vector2d(46.25,-5.34),Math.toRadians(0)) // y-4 Rotates
                            .strafeToLinearHeading(new Vector2d(45.343,-4.9),Math.toRadians(95)) // y-4 Rotates
                            .waitSeconds(0.8)
                            .strafeToLinearHeading(new Vector2d(41.25,-15.25),Math.toRadians(97)) // y-13 Rotates
                            //.strafeToLinearHeading(new Vector2d(41.25,-15.25),Math.toRadians(98)) // y6 Rotates
                            .build(),

                    intake.inverse(), // Drops pixel

                    drive.actionBuilder(drivel1.pose)
                            .setTangent(0)
                            .waitSeconds(1.2)
                            .strafeToLinearHeading(new Vector2d(-17.324,-58.75),Math.toRadians(95))//10-50 // Parks
                            .build()));

        } else if (OD.getIntLocation() == 2) { // Center

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(10.52,-10.53),Math.toRadians(-90)) // y4
                            .waitSeconds(0.3)
                            .strafeToLinearHeading(new Vector2d(47.254,-4.342),Math.toRadians(98)) // y6 Rotates
                            .strafeToLinearHeading(new Vector2d(33.258,-25.753),Math.toRadians(98)) // y6 Rotates
                            .build(),

                    intake.inverse(), // Drops pixel

                    drive.actionBuilder(drivel1.pose)
                            .setTangent(0)
                            .waitSeconds(0.3)
                            .strafeToLinearHeading(new Vector2d(-20.3241,-59.751),Math.toRadians(95))//10-50 // Parks
                            .build()));

        } else { // Left

            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(10.51,-10.51),Math.toRadians(-90)) // y4
                            .waitSeconds(0.3)
                            .strafeToLinearHeading(new Vector2d(47.251,-4.341),Math.toRadians(98)) // y6 Rotates
                            .strafeToLinearHeading(new Vector2d(36.251,-19.3),Math.toRadians(98)) // y6 Rotates
                            .build(),

                    intake.inverse(), // Drops pixel

                    drive.actionBuilder(drivel1.pose)
                            .setTangent(0)
                            .waitSeconds(0.3)
                            .strafeToLinearHeading(new Vector2d(-20.3241,-59.751),Math.toRadians(95))//10-50 // Parks
                            .build()));

                            //.strafeToLinearHeading(new Vector2d(0.15,-75.1),Math.toRadians(-85))

        } if(isStopRequested()) return;}}