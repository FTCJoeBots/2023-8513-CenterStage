package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;

/**
 * This is sample code used to explain how to write an autonomous code
 *
 */

@Autonomous(name="AprilTagTestNo", group="Pushbot")

public class AprilTagTestAutoImp extends LinearOpMode {

    /* Declare OpMode members. */
    OpenCvCamera webcam;
    ObjectDetector2 OD = new ObjectDetector2(telemetry);
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    private ElapsedTime     runtime = new ElapsedTime();
    /**
     * The variable to store our instance of the AprilTag processor.
     */
    public AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    public VisionPortal visionPortal;

    /**
     * Initialize the AprilTag processor.
     */
    public void initAprilTag() {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)

                // ... these parameters are fx, fy, cx, cy.
                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "AprilTagCam"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableCameraMonitoring(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);

    }   // end method initAprilTag()


    @Override
    public void runOpMode() {

        initAprilTag();

        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));
        Pose2d startPoser = new Pose2d(-3, 0, Math.toRadians(-91));
        Pose2d Pose1 = new Pose2d(40.1, -5, Math.toRadians(-85));
        Pose2d Pose1c = new Pose2d(54,17.5, Math.toRadians(-84));
        Pose2d Pose1l = new Pose2d(52, 24.2958, Math.toRadians(-83));
        Pose2d Pose2l = new Pose2d(21.94,-73.869, Math.toRadians(106));
        Pose2d Pose3l = new Pose2d(51.5,-101.9, Math.toRadians(105));
        Pose2d Pose4l = new Pose2d(21.89,-79.8, Math.toRadians(104));
        Pose2d EndPose;

        Intake intake = new Intake();
        Bucket BucketAuto = new Bucket();

        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        MecanumDrive drive2l = new MecanumDrive(hardwareMap, Pose2l);
        MecanumDrive drive3l = new MecanumDrive(hardwareMap, Pose3l);
        MecanumDrive drive4l = new MecanumDrive(hardwareMap, Pose4l);
        MecanumDrive drive1r = new MecanumDrive(hardwareMap, Pose1);

        MecanumDrive drive1c = new MecanumDrive(hardwareMap, Pose1c);

        MecanumDrive drive1l = new MecanumDrive(hardwareMap, Pose1l);

        Lift lift = new Lift();

        boolean Found5 = false;
        double addStrafe = -1.0;

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



        } else if (OD.getIntLocation() == 2) { // Center

            // Drop the pixel, drive past the stage door and rotate
            Actions.runBlocking( new SequentialAction (
                    drive.actionBuilder(drive.pose)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(57.3,17.8),Math.toRadians(-85))
                            .build(),


                    intake.inverse(),

                    drive.actionBuilder(drive1l.pose)
                            .waitSeconds(1)
                            .setTangent(0)
                            .strafeToLinearHeading(new Vector2d(85.1,16.23),Math.toRadians(-88))

                            .strafeToLinearHeading(new Vector2d(82.5,-100.97),Math.toRadians(-88))
                            .strafeToLinearHeading(new Vector2d(52.5,-102.3),Math.toRadians(110))
                            .build()

            ));
            // Strafe right until we see the april tag
            while(!Found5){
                Actions.runBlocking( new SequentialAction (
                        drive.actionBuilder(drive.pose)
                                .setTangent(0)
                                .strafeToLinearHeading(new Vector2d(57.3,-102.5 + addStrafe),Math.toRadians(-85))
                                .build()));

                List<AprilTagDetection> currentDetections = aprilTag.getFreshDetections();
                // Step through the list of detections and display info for each one.
                for (AprilTagDetection detection : currentDetections) {
                    if (detection.metadata != null) {
                        telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                        telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                        telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                        telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                        if(detection.id == 5){
                            break;
                        }
                    } else {
                        telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                        telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
                    }
                }   // end for() loop
                Found5 = true;
                addStrafe++;

            }

        } else { // Left
        } if(isStopRequested()) return;}}

