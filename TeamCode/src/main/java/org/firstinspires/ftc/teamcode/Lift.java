package org.firstinspires.ftc.teamcode;

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
    final int low     = 1000;
    final int med     = 2000;
    final int high    = 3000;
    final int initPos = 0;

    final int aH      = 415;
    final int aMH     = 325;
    final int aM      = 144;
    final int aLM     = 140;
    final int aL      = 0;

    final int MaxLiftH = 3500;
    final int MinLiftH = 0;
    double MANUAL_LIFT_SPEED = 0.4;
    double POSITION_LIFT_SPEED = 0.3;
    private int lift_target_position = 0;


    DcMotor liftM;

    //public testLift(HardwareMap hwMap) {
    public void init(HardwareMap hwMap) {

        liftM = hwMap.get(DcMotor.class, "liftM");
        liftM.setPower(0);

        liftM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftM.setTargetPosition(MinLiftH);
        liftM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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




    public void raiseLiftManual(){
        lift_target_position = liftM.getCurrentPosition()+75;
    }
    public void lowerLiftManual(){
        lift_target_position=liftM.getCurrentPosition()-75;
    }
    public int getLiftTargetPosition(){
        return lift_target_position;
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

    public double getLiftPosition(){ return liftM.getCurrentPosition(); }

}
