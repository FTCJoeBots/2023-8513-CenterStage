package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="SimpleMecanumDrive8513", group="0")

public class SimpleMecanumDrive8513 extends LinearOpMode {

    HardwareMap hwmap;
    private ElapsedTime waite = new ElapsedTime();

    static final double MAX_SPEED = 0.95;
    static final double TURTLE_SPEED = MAX_SPEED / 2;
    private double strafe = 0;
    private double rotate = 0;
    private double forward = 0;
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
    int stateIntake = 0;
    boolean preLift = false;
    boolean preIntake = false;
    boolean nowIntake;
    boolean previoiusPressedIntake = false;
    boolean xPrev = false;
    boolean xCur;


    int stateIntakeR = 0;
    boolean preIntakeR = false;
    boolean nowIntakeR;
    boolean previoiusPressedIntakeR = false;

    boolean yPrev = false;
    boolean yCur;
    boolean yPrev1 = false;
    boolean yCur1;
    boolean aPrev = false;
    boolean aCur;
    boolean bPrev = false;
    boolean bCur;
    boolean prevRB;
    boolean TurtleMode=false;
    boolean Previous1A=false;
    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap , new Pose2d(0, 0, 0));

        Intake intake = new Intake();
        Lift lift = new Lift();
        Bucket bucketFunctions = new Bucket();
        //Shooter shooter = new Shooter();

        intake.init(hardwareMap);
        lift.init(hardwareMap);
      //  shooter.init(hardwareMap);
        bucketFunctions.init(hardwareMap);

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            strafe = (gamepad1.left_trigger -gamepad1.right_trigger) * MAX_SPEED;
            rotate = gamepad1.right_stick_x;
            forward = -gamepad1.left_stick_y * MAX_SPEED;




           /* drive.setDrivePowers(
                    new PoseVelocity2d(new Vector2d(
                            -gamepad1.left_stick_y * MAX_SPEED,
                            strafe*MAX_SPEED
                    ), -gamepad1.right_stick_x
                    ));*/

            //drive.updatePoseEstimate();

            if(gamepad1.a&&!Previous1A) {
                TurtleMode=!TurtleMode;
                Previous1A=true;
            }
            if(!gamepad1.a) {
                Previous1A=false;
            }
            if(TurtleMode) {
                strafe*=0.3;
                forward*=0.3;
                rotate*=0.3;
            }

            drive.setDrivePowers(
                    new PoseVelocity2d(new Vector2d(
                            forward,
                            strafe
                    ), -rotate
                    ));

            yCur1 = gamepad1.y;
            if(gamepad1.y && !yPrev1){
                lift.Zero();
                lift.Hcheck();
            }
            yPrev1 = gamepad1.y;

            //Intake
            nowIntake = gamepad2.b;
            if (nowIntake && nowIntake != preIntake) {
                // Normal suction
                if (stateIntake == 1) {
                    intake.Intake_start();

                }
                // Stop
                else if (stateIntake == 2) {
                    intake.Intake_stop();
                }
                stateIntake += 1;
                if (stateIntake > 2) {
                    stateIntake = 1;
                }
                previoiusPressedIntake = true;
            }
            preIntake = nowIntake;

            nowIntakeR = gamepad2.a;
            if (nowIntakeR && nowIntakeR != preIntakeR) {
                // Reversal suction
                if (stateIntakeR == 1) {
                    intake.Intake_inverse();
                }
                // Stop
                else if (stateIntakeR == 2) {
                    intake.Intake_stop();
                }
                stateIntakeR += 1;
                if (stateIntakeR > 2) {
                    stateIntakeR = 1;
                }
                previoiusPressedIntakeR = true;
            }
            preIntakeR = nowIntakeR;


            //Intake: X (DRIVER)

            // OPERATOR CODE

            //Manual Lift: Right Bumper (up), Left Bumper (down)
            //Lift Postions: dpad down (ground), dpad left (1), dpad right (2), dpad up (3)
            //Bucket: x
            //BucketGate: _ (Ill intergrate it with the bucket)
            //Shooter: ??


            //Lift

            //Manual LIFT
            if(gamepad2.right_bumper){
                lift.raiseLiftManual();
                lift.check();
            }
            if(gamepad2.left_bumper){
                lift.lowerLiftManual();
                lift.check();
            }

            //Posisinal LIFT
            if(gamepad2.dpad_up){
                lift.Lift_To_Position(3);
                lift.check();
                bucketFunctions.BucketOut();
                bucketFunctions.BucketGateIn();

            }
            if(gamepad2.dpad_right){
                lift.Lift_To_Position(2);
                lift.check();
                bucketFunctions.BucketOut();
                bucketFunctions.BucketGateIn();
            }
            if(gamepad2.dpad_left){
                lift.Lift_To_Position(1);
                lift.check();
                bucketFunctions.BucketOut();
                bucketFunctions.BucketGateIn();
            }

            if(gamepad2.dpad_down){
                lift.Lift_To_Position(0);
                lift.check();
                bucketFunctions.BucketIn();
                bucketFunctions.BucketGateIn();
            }


        //Bucket Gate

            yCur = gamepad1.x;
            if(gamepad1.x && !yPrev){
                lift.toggleHanger();
                lift.Hcheck();
            }
            yPrev = gamepad1.x;

            xCur = gamepad2.x;
            if(gamepad2.x && !xPrev){
               bucketFunctions.ToggleBucketGate();
            }
            xPrev = gamepad2.x;

          /*  aCur = gamepad2.a;
            if(gamepad2.a && !aPrev){
                bucketFunctions.bucketMore();
            }
            aPrev = gamepad2.a;

            bCur = gamepad2.b;
            if(gamepad2.b && !bPrev){
                bucketFunctions.bucketLess();
            }
            bPrev = gamepad2.b;*/



        //BucketGate

         /*   if (gamepad2.b) {
                //lift.resetToZero();
            }*/



            telemetry.addData("LiftEncoders",lift.getLiftPosition());
            telemetry.addData("HangerEncoders",lift.getHangerPosition());

//telemetry.speak("NERDS!!!");

telemetry.update();

        }
    }
}



