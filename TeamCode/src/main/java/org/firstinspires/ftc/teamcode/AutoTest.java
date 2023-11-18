package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import java.lang.Math;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.tensorflow.lite.task.vision.detector.ObjectDetector;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import com.acmerobotics.roadrunner.*;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.HolonomicController;
import com.acmerobotics.roadrunner.MecanumKinematics;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.MotorFeedforward;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.TimeTrajectory;
import com.acmerobotics.roadrunner.TimeTurn;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.FlightRecorder;
import com.acmerobotics.roadrunner.ftc.LynxFirmware;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;

@Disabled
public class AutoTest extends LinearOpMode
{

    private DcMotor motor0;
    private DcMotor motor1;
    private DcMotor motor2;
    private DcMotor motor3;
    OpenCvCamera webcam;
    ObjectDetector1 OD = new ObjectDetector1(telemetry);

    private ElapsedTime runtime = new ElapsedTime();

    private double AngleUnit = 50;


    public void runOpMode(){

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        Pose2d startPose = new Pose2d(-65, 36, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        Lift lift = new Lift();
        Intake intake = new Intake();
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened(){webcam.startStreaming(640,480, OpenCvCameraRotation.UPRIGHT);}

            @Override
            public void onError(int errorCode){}

        });

        while(!isStarted()){
            if (OD.getIntLocation() == 2){
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("RIGHT");
                telemetry.update();
                sleep(30);
            }
            else if (OD.getIntLocation() == 1){
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("CENTER");
                telemetry.update();
                sleep(30);
            }
            else{
                telemetry.addData("Location", OD.getIntLocation());
                telemetry.addLine("LEFT");
                telemetry.update();
                sleep(30);
            }
        }


        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(drive.pose)
/*
                       // .splineTo(new Vector2d(0, 36), Math.toRadians(180))

                        //For strafe, add 10 to heading, for line, subtract 10 for heading

                        .setTangent(0)
                        .strafeToLinearHeading(new Vector2d(-40,28),Math.toRadians(80)) //more
                        .setTangent(0)
                        //.waitSeconds(2)
                        .strafeToLinearHeading(new Vector2d(-6,30),Math.toRadians(74))
                        .setTangent(0)
                        .strafeToLinearHeading(new Vector2d(-8,150),Math.toRadians(65)) //less
                        //.lineToXLinearHeading(100,Math.toRadians(180))

*/
                        .build());
                if(isStopRequested()) return;
    }
}
