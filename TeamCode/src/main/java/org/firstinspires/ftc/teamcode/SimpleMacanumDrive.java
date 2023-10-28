package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

@TeleOp(name="Simple Mecanum Drive", group="TeleOp")

public class SimpleMacanumDrive extends OpMode {

    MecanumDrive drive = new MecanumDrive(hardwareMap , new Pose2d(0, 0, 0));
    Intake intake = new Intake();
    Lift lift = new Lift();
    Shooter shooter = new Shooter();
    Bucket bucketFunctions = new Bucket();

    int stateIntake = 0;
    int stateLift = 0;


    boolean preLift;
    boolean nowLift;
    boolean previoiusPressedLift = false;
    boolean preIntake;
    boolean nowIntake;
    boolean previoiusPressedIntake = false;

    boolean aPrev = false;
    //boolean yPrev = false;
    boolean bPrev = false;
   // boolean xPrev = false;

    boolean curRB;
    boolean prevRB;



    @Override
    public void init() {
        intake.init(hardwareMap);
       lift.init(hardwareMap);
       shooter.init(hardwareMap);
      bucketFunctions.init(hardwareMap, Bucket.BucketStartPosition.IN, Bucket.BucketGateStartPosition.CLOSE);
    }

    @Override
    public void loop() {

        // DRIVER CODE

        //drive.setDrivePowers(new PoseVelocity2d( new Vector2d(-gamepad1.left_stick_y, -gamepad1.left_stick_x), -gamepad1.right_stick_x));
        drive.setDrivePowers(new PoseVelocity2d( new Vector2d(-gamepad1.left_stick_y, 0), -gamepad1.right_stick_x));
        drive.updatePoseEstimate();



/**
        // OPERATOR CODE

        //Intake: Y
        //Lift: X
        //Bucket: Right Bumper
        //BucketGate: B
        //Shooter: A

        //Intake
        nowIntake = gamepad2.y;
        if (nowIntake && nowIntake != preIntake) {
            // Normal suction
            if (stateIntake == 1) {
                intake.Intake_start();

            }
            // Rejection
            else if (stateIntake == 2) {
                intake.Intake_stop();
                intake.Intake_inverse();
            }
            // Stop
            else if (stateIntake == 3) {
                intake.Intake_stop();
            }
            stateIntake += 1;
            if (stateIntake > 3) {
                stateIntake = 1;
            }
            previoiusPressedIntake = true;
        }
        preIntake = nowIntake;

        //Lift


        //Manual LIFT (we don't accually need this)
        if(gamepad2.dpad_up){
            lift.raiseLiftManual();
        }
        if(gamepad2.dpad_down){
            lift.lowerLiftManual();
        }


        // States LIFT
        nowLift = gamepad2.x;
        if (nowLift && nowLift != preLift) {
            // ground
            if (stateLift == 1) {
                lift.Lift_To_Position(1);

            }
            // low
            else if (stateLift == 2) {
                lift.Lift_To_Position(2);
            }
            // med
            else if (stateLift == 3) {
                lift.Lift_To_Position(3);
            }
            // high
            else if (stateLift == 4) {
                lift.Lift_To_Position(3);
            }
            stateLift += 1;
            if (stateLift > 4) {
                stateLift = 1;
            }
            previoiusPressedLift = true;
        }
        preLift = nowLift;


/**      We dont need this but still if they complain and stuff
        if(gamepad2.x){
            lift.Lift_To_Position(3);
        }

        if(gamepad2.x){
            lift.Lift_To_Position(3);
        }

        if(gamepad2.x){
            lift.Lift_To_Position(3);
        }

        if(gamepad2.x){
            lift.Lift_To_Position(3);
        }
 **/
/**

        //Bucket

        if(gamepad1.right_bumper != prevRB){
            Bucket.ToggleBucket();
        }
        prevRB = gamepad1.right_bumper;

        //Cur = gamepad1.j;
        //if(Cur != Prev){ Motor.Toggle }
        // Prev = Cur


        //BucketGate

        if (gamepad2.b && !bPrev) {
            Bucket.ToggleBucketGate();
        }
        bPrev = gamepad2.b;


        //Shooter

        if (gamepad2.a && !aPrev) {
            shooter.Toggle();
        }
        aPrev = gamepad2.a;
**/


        telemetry.addData("x", drive.pose.position.x);
        telemetry.addData("y", drive.pose.position.y);
        telemetry.addData("heading", drive.pose.heading);
        telemetry.update();

    }
}
