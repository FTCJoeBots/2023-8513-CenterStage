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

public class Shooter {

    //declare motors
    public DcMotor Shooter;

    //Hardware map stuff
    HardwareMap hwMap;
    private ElapsedTime period = new ElapsedTime();

    private boolean WheelOn = false;
    private boolean driverOverride = false;

    public double currentPower = 0;
    public double shooterMaxPower = 0.3;
    boolean rampingUp = false;

    private static final double INCREMENT = 0.05;
    private static final double CYCLE = 50;

    private ElapsedTime rampTime = new ElapsedTime();
    private double rampCurrTime = 0;



    //constants

    //constructor
    public Shooter(){

    }

    //init
    public void init(HardwareMap hwMap) {

        Shooter = hwMap.get(DcMotor.class,"Shooter");
        stopMotor();
        Shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    ////=======================

    //stop
    public void stopMotor(){
        currentPower = 0;
    }

    public void setDriverOverride(){
        driverOverride=true;
        currentPower = 1;
    }
    public void disableDriveOverride(){
        driverOverride=false;
        currentPower = 0;
    }

    //ramp up
    public void startRamp(){
        rampingUp = true;
    }
    public void rampController(){
        if(rampTime.milliseconds()-rampCurrTime >= CYCLE) {
            currentPower += INCREMENT;
            }
        if(currentPower>=shooterMaxPower){
            rampingUp = false;
        }

    }
    //reverse

    public void ManualDrive(double newSpeed){
        currentPower = newSpeed;
    }

    //max power
    public void powerOn(){
        if(!driverOverride) {
            currentPower = shooterMaxPower;
        }
    }
    public void Toggle(){
        if (WheelOn) {
            currentPower = 0;
        } else {
            currentPower = shooterMaxPower;
        }

    }

    public void spinnerController(){
        //sets a variable to control the current action of the motor
        if(currentPower>0 || currentPower<0){
            //the motor is running. Check if ramping
            if(rampingUp){
                rampController();
            }

            WheelOn = true;
        } else{
            WheelOn = false;
            currentPower = 0;
        }
        Shooter.setPower(currentPower);
    }
}
