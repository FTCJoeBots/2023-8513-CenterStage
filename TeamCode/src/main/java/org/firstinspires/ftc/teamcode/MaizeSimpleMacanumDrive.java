package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="MaizeSimpleMacanumDrive", group="0")

public class MaizeSimpleMacanumDrive extends LinearOpMode {

    HardwareMap hwmap;
    static final double MAX_SPEED = 0.6;
    static final double TURTLE_SPEED = MAX_SPEED / 2;
    private double strafe = 0;
    boolean prevstateRB;
    boolean currentstateRB;
    boolean currentStateB;
    boolean prevStateB;
    boolean currentstateDR;
    boolean prevstateDR;
    boolean  currStateGP2LeftBumper;
boolean prevStateGP2LeftBumper;
boolean currentstatedpadleft;
boolean previousStatedpadleft;

    @Override
    public void runOpMode() throws InterruptedException {
        //HardwareMap hardwareMap=null;

        MecanumDrive drive = new MecanumDrive(hardwareMap , new Pose2d(0, 0, 0));

        Intake intake = new Intake();
        Lift lift = new Lift();
        Shooter shooter = new Shooter();
        Bucket bucketFunctions = new Bucket();

        int stateIntake = 0;
        int stateLift = 0;


        boolean preLift = false;
        boolean nowLift;
        boolean previoiusPressedLift = false;
        boolean preIntake = false;
        boolean nowIntake;
        boolean previoiusPressedIntake = false;

        boolean aPrev = false;
        //boolean yPrev = false;
        boolean bPrev = false;
        // boolean xPrev = false;

        boolean curRB;
        boolean prevRB;

        //high post is 2950
        // medium is 2100
        //low is 1230
        // ground is 200

        intake.init(hardwareMap);
        lift.init(hardwareMap);
        //shooter.init(hardwareMap);
        //bucketFunctions.init(hardwareMap, Bucket.BucketStartPosition.IN);

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            //drive.setDrivePowers(new PoseVelocity2d( new Vector2d(-gamepad1.left_stick_y, -gamepad1.left_stick_x), -gamepad1.right_stick_x));
            drive.setDrivePowers(new PoseVelocity2d( new Vector2d(-gamepad1.left_stick_y, 0), -gamepad1.right_stick_x));
            drive.updatePoseEstimate();
            
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
 

            
            /*strafe = gamepad1.left_trigger -gamepad1.right_trigger;

            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * MAX_SPEED,
                            strafe*MAX_SPEED,
                            -gamepad1.right_stick_x*MAX_SPEED
                    )
            );

            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();


            if(gamepad2.dpad_up){
                lift.raiseLiftManual();
            }
            if(gamepad2.dpad_down){
                lift.lowerLiftManual();
            }

            if(gamepad2.left_bumper){
                lift.move_shoulder(2);
            }


            if(gamepad1.left_bumper){
                clamp.clamp_close();
            }

            if(gamepad1.right_bumper){
                clamp.clamp_open();
            }

            if(gamepad2.y){
                lift.Lift_To_Position(3);
            }
            if(gamepad2.x){
                lift.Lift_To_Position(2);
            }
            if(gamepad2.b){
                lift.Lift_To_Position(1);
            }
            if(gamepad2.a){
                lift.Lift_To_Position(0);
            }
            if(gamepad2.right_bumper){
                lift.Lift_To_Position(9);
            }




            currentstateRB=gamepad1.right_bumper;
            if(currentstateRB != prevstateRB){
                clamp.toggleclamp();
            }
            prevstateRB=currentstateRB;

            currStateGP2LeftBumper = gamepad2.left_bumper;
            if(currStateGP2LeftBumper && !prevStateGP2LeftBumper){
                lift.move_shoulder(2);
            }
            prevStateGP2LeftBumper = currStateGP2LeftBumper;

            currentstateDR = gamepad2.dpad_right;
            if(currentstateDR !=prevstateDR){
                lift.move_shoulder(1);
            }
            prevstateDR = currentstateDR;

            currentstatedpadleft = gamepad2.dpad_left;
            if(currentstatedpadleft && ! previousStatedpadleft){
                lift.move_shoulder(3);
            }
            previousStatedpadleft = currentstatedpadleft;





            lift.controller();


telemetry.addData("liftSafe",lift.isLiftSafe());
telemetry.addData("ServoSafe",lift.isServoSafe());
telemetry.addData("Current Lift Position: ", lift.getLiftPosition());
telemetry.addData("Target Lift Position: ", lift.getLiftTargetPosition());
telemetry.addData("ServoPosition",lift.getServoPosition());
telemetry.speak("NERDS!!!");

telemetry.update();
*/
        }
    }
}



