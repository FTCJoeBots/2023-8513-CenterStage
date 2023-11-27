package teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;


@TeleOp(name="A_SimpleMecanumDrive8513", group="0"/*, color = "red"*/)

public class SimpleMecanumDrive8513 extends LinearOpMode {

    private ElapsedTime timer = new ElapsedTime();
    private ElapsedTime OpmodeTimer = new ElapsedTime();

    //Drive Var
    private double strafe = 0;
    private double rotate = 0;
    private double forward = 0;
    //Switch Cases
    int stateIntake = 0;
    int driveStyle=1;
    boolean preIntake = false;
    boolean nowIntake;
    //boolean previoiusPressedIntake = false;

    //Boolean Gards for buttons
    boolean xPrev = false;
    boolean xCur;
    boolean yPrev = false;
    boolean yCur;
    boolean yPrev1 = false;
    boolean yCur2;
    boolean yPrev2 = false;
    boolean yCur1;
    boolean aPrev = false;
    boolean aCur;
    boolean TurtleMode=false;
    boolean Previous1A=false;
    boolean nowDrive;
    boolean SwichMOde=false;
    boolean Previous1RB=false;
    boolean prevReg = false;
    boolean curReg;
    boolean prevServe = false;
    boolean curServe;
    boolean prevRocket = false;
    boolean curRocket;
    boolean prevLstickB = false;




    @Override
    public void runOpMode() throws InterruptedException {

        //Drive
        MecanumDrive drive = new MecanumDrive(hardwareMap , new Pose2d(0, 0, 0));

        //Sets features
        Intake intake = new Intake();
        Lift lift = new Lift();
        Bucket bucketFunctions = new Bucket();
        Shooter shooter = new Shooter();

        //Init
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        shooter.init(hardwareMap);
        bucketFunctions.init(hardwareMap);

        View relativeLayout;

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        relativeLayout.post(() -> relativeLayout.setBackgroundColor(Color.rgb(179, 160, 18)));

        //Init Loop
        // The User is pressing the Dpad to change the Drive Style
        while(opModeInInit() && !isStopRequested()){
        nowDrive = gamepad1.dpad_down;

            //Dpad Left for Regular
            curReg = gamepad1.dpad_left;
            if(gamepad1.dpad_left && !prevReg){
                driveStyle = 1;
                telemetry.addLine("Drive Style: Regular Drive");
                telemetry.update();
            }
            prevReg = gamepad1.dpad_left;

            //Dpad Down for Serve
            curServe = gamepad1.dpad_down;
            if(gamepad1.dpad_down && !prevServe){
                driveStyle = 2;
                telemetry.addLine("Drive Style: Serve Drive");
                telemetry.update();
            }
            prevServe = gamepad1.dpad_down;

            //Dpad Right for Rocket League
            curRocket = gamepad1.dpad_right;
            if(gamepad1.dpad_right && !prevRocket){
                driveStyle = 3;
                telemetry.addLine("Drive Style: Rocket League Drive");
                telemetry.update();
            }
            prevRocket = gamepad1.dpad_right;

        telemetry.update();
        }

        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            relativeLayout.post(() -> relativeLayout.setBackgroundColor(Color.rgb(179, 160, 18)));

            OpmodeTimer.startTime();



            if (driveStyle==1){
                //REG
                strafe = (gamepad1.left_trigger - gamepad1.right_trigger);
                rotate = gamepad1.right_stick_x;
                forward = -gamepad1.left_stick_y;

                telemetry.addData("Drive Style: Regular Drive", driveStyle);
                telemetry.update();

            }else if (driveStyle == 2){
                // Serve
                forward = -gamepad1.left_stick_y;
                strafe = -gamepad1.left_stick_x;
                rotate = gamepad1.right_stick_x;

                telemetry.addData("Drive Style: Serve Drive", driveStyle);
                telemetry.update();

            }else if (driveStyle == 3){
                // Rocket
                forward = -gamepad1.left_stick_y * gamepad1.right_trigger;
                strafe = -gamepad1.left_stick_x * gamepad1.right_trigger;
                rotate = gamepad1.right_stick_x * gamepad1.right_trigger;

                telemetry.addData("Drive Style: Rocket League", driveStyle);
                telemetry.update();

            }else{

                /* //REG
                strafe = (gamepad1.left_trigger - gamepad1.right_trigger);
                rotate = gamepad1.right_stick_x;
                forward = -gamepad1.left_stick_y;*/
                /*
                // Rocket
                forward = -gamepad1.left_stick_y * gamepad1.right_trigger;
                strafe = -gamepad1.left_stick_x * gamepad1.right_trigger;
                rotate = gamepad1.right_stick_x * gamepad1.right_trigger;*/
                /*// Serve
                forward = -gamepad1.left_stick_y;
                strafe = -gamepad1.left_stick_x;
                rotate = gamepad1.right_stick_x;*/

                gamepad1.rumble(1);
                telemetry.addLine("Did you click the dpad during init?");
                telemetry.update();
            }


            //DRIVER (gamepad 1)

             /*
             * Right Bumper: Switch Mode
             * Dpad Up: Shooter
             * A: Turtle Mode
             * B: Intake
             * X: Toggle Hanger
             * Y: Hanger Down
             */

            //Robot drives
            drive.setDrivePowers(
                    new PoseVelocity2d(new Vector2d(
                            forward,
                            strafe),
                            -rotate
                    ));

            //Starts the intake
            intake.Intake_start();

            //Driving controls (driver)

            //Slows down the robot for the driver
            if(gamepad1.a&&!Previous1A) {
                TurtleMode=!TurtleMode;
                Previous1A=true;
            }
            if(!gamepad1.a) {
                Previous1A=false;
            }
            if(TurtleMode) {
                strafe*=0.35;
                forward*=0.35;
                rotate*=0.35;
            }

            //Switches forwards and backwards for the driver
            if(gamepad1.right_bumper&&!Previous1RB) {
                SwichMOde=!SwichMOde;
                Previous1RB=true;
            }
            if(!gamepad1.right_bumper) {
                Previous1RB=false;
            }
            if(SwichMOde) {
                forward*=-0.8;
            }

            //Rotate the robot 180
            if(gamepad1.right_stick_button && !prevLstickB){
                rotate += 180;
                telemetry.addLine("180 Deg");
                telemetry.update();
            }
            prevLstickB = gamepad1.right_stick_button;

            //Function contol (driver)
            //Intake
            nowIntake = gamepad1.b;
            if (nowIntake && nowIntake != preIntake) {
                // Normal suction
                if (stateIntake == 1) {
                    intake.Intake_inverse();
                    telemetry.addData("Intake REVERSED;",stateIntake);
                }
                // Stop
                else if (stateIntake == 2) {
                    intake.Intake_stop();
                    telemetry.addData("Intake STOPPED;",stateIntake);
                }
                else if (stateIntake == 3) {
                    intake.Intake_start();
                    telemetry.addData("Intake ON;",stateIntake);
                }
                stateIntake += 1;
                if (stateIntake > 3) {
                    stateIntake = 1;
                }
            }
            preIntake = nowIntake;

            //End Game controls (driver)
            //Shooter
            if(gamepad1.dpad_up && !aPrev){
                Shooter.shoot();
                telemetry.speak("Shot");
            }
            aPrev = gamepad1.dpad_up;

            // Hanger
            if(gamepad1.x && !yPrev){
                intake.Intake_stop();
                lift.toggleHanger();
                lift.Hcheck();
                timer.reset();
                timer.startTime();
            }
            yPrev = gamepad1.x;

            yCur1 = gamepad1.y;
            if(gamepad1.y && !yPrev1){
                lift.Zero();
                lift.Hcheck();
            }
            yPrev1 = gamepad1.y;

            if(timer.seconds() == 15){
                timer.reset();
                requestOpModeStop();
            }

            if(OpmodeTimer.seconds() == 300){
                timer.reset();
                requestOpModeStop();
            }




            // OPERATOR (gamepad 2)
            /*
             * Dpad Up: Lift to 2
             * Dpad Left: Lift to 1
             * Dpad Right: Lift to 1
             * Dpad Down: Lift to 0
            *  X: BucketGate
            *  Y: Edit
            * */

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

            //Set Pos LIFT
            //Lift to high
            if(gamepad2.dpad_up){
                lift.Lift_To_Position(3);
                lift.check();
                bucketFunctions.BucketOut();
                bucketFunctions.BucketGateIn();
                intake.Intake_stop();
            }

            //Lift to middle
            if(gamepad2.dpad_right){
                lift.Lift_To_Position(2);
                lift.check();
                bucketFunctions.BucketOut();
                bucketFunctions.BucketGateIn();
                intake.Intake_stop();

            }
            if(gamepad2.dpad_left){
                lift.Lift_To_Position(2);
                lift.check();
                bucketFunctions.BucketOut();
                bucketFunctions.BucketGateIn();
                intake.Intake_stop();

            }

            //Lift to ground
            if(gamepad2.dpad_down){
                lift.Lift_To_Position(0);
                lift.check();
                bucketFunctions.BucketIn();
                bucketFunctions.BucketGateIn();
                intake.Intake_start();
            }

            //Edit Mode (lift)
            if(gamepad2.y && !yPrev2){
                lift.Lift_To_Position(1);
                lift.check();
                bucketFunctions.BucketOut();
                bucketFunctions.BucketGateIn();
                intake.Intake_stop();
            }
            yPrev2 = gamepad2.y;


            //Bucket
            if(gamepad2.x && !xPrev){
               bucketFunctions.ToggleBucketGate();
            }
            xPrev = gamepad2.x;


            //Encoder Positions
            telemetry.addData("LiftEncoders",lift.getLiftPosition());
            telemetry.addData("HangerEncoders",lift.getHangerPosition());

            //Seperator
            telemetry.addLine("");
            telemetry.addLine("_______________");
            telemetry.addLine("");

            //Drive (deadwheel) Positions
            telemetry.addLine("DRIVE POSITIONS");
            telemetry.addData("X pos:", drive.getPosX());
            telemetry.addData("Y pos:", drive.getPosY());
            telemetry.addData("Real Heading:", drive.getRealHeading());
            telemetry.addData("Imaginary Heading:", drive.getImaginaryHeading());
            telemetry.addData("Log Heading:", drive.getLogHeading());

            //telemetry.speak("NERDS!!!");
            telemetry.update();
        }
    }
}