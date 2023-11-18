package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.ServoConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
public class Lift {


    final int groundlevel  = 0;
    final int low     = 2750;
    final int med     = 4050;
    final int high    = 6100;

    final int MaxLiftH = 7000;
    final int MinLiftH = 0;
    double MANUAL_LIFT_SPEED = 0.75;
    private int lift_target_position = 0;

    private int hanger_target_position = 0;
    double HANGER_SPEED = 0.75;
    static boolean HangerDown = false;



    DcMotor liftM;
    DcMotor hangerM;

    //public testLift(HardwareMap hwMap) {
    public void init(HardwareMap hwMap) {

        liftM = hwMap.get(DcMotor.class, "liftM");
        liftM.setPower(0);

        liftM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftM.setTargetPosition(groundlevel);
        liftM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftM.setPower(MANUAL_LIFT_SPEED);

        hangerM = hwMap.get(DcMotor.class, "hangerM");
        hangerM.setPower(0);

        hangerM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangerM.setTargetPosition(groundlevel);
        hangerM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangerM.setPower(HANGER_SPEED);

    }

    public void check() {
        if (lift_target_position < MinLiftH)
            lift_target_position = MinLiftH;

        if (lift_target_position > MaxLiftH)
            lift_target_position = MaxLiftH;

        liftM.setTargetPosition(lift_target_position);
        liftM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftM.setPower(MANUAL_LIFT_SPEED);
    }

    public void Hcheck() {
        hangerM.setTargetPosition(hanger_target_position);
        hangerM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangerM.setPower(HANGER_SPEED);
    }




    public void raiseLiftManual(){
        lift_target_position = liftM.getCurrentPosition()+75;
    }
    public void resetToZero(){
        lift_target_position = 0;
    }


    public void lowerLiftManual(){
        lift_target_position=liftM.getCurrentPosition()-75;
    }
    public int getLiftTargetPosition(){
        return lift_target_position;
    }




    public void resetHanger(){
        hanger_target_position = 1500;
        HangerDown=false;
    }
    public void upHanger(){
        hanger_target_position = 4400;
        HangerDown=true;

    }
    public void Zero(){
        hanger_target_position = 0;
    }
    public int getHangerTargetPosition(){
        return hanger_target_position;
    }

    public  void toggleHanger(){
        if(HangerDown){
            resetHanger();
        }else{
            upHanger();
        }
    }






    public void Lift_To_Position(int LiftPosition) {
        switch (LiftPosition) {
            case 0:
                lift_target_position = groundlevel;
                break;
            case 1:
                lift_target_position = low;
                break;
            case 2:
                lift_target_position = med;
                break;
            case 3:
                lift_target_position = high;
                break;


        }
    }

    public boolean LiftSafety(){
        if(liftM.getCurrentPosition() > MaxLiftH){
            return true;
        }else{
            return false;
        }
    }


    public class liftToOne implements Action {
        public void init() {Lift_To_Position(1);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Lift_To_Position(1);
            check();
            return false;}
    }

    public class liftToZero implements Action {
        public void init() {Lift_To_Position(0);}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Lift_To_Position(0);
            check();
            return false;}
    }


    public Action Pos0() {
        return new Lift.liftToZero();
    }
    public Action Pos1() {
        return new Lift.liftToOne();
    }

    public double getLiftPosition(){ return liftM.getCurrentPosition(); }
    public double getHangerPosition(){ return hangerM.getCurrentPosition(); }

}
