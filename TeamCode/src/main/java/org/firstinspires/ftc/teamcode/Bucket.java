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

public class Bucket {
    final double IntakeSide = 0;
    final double OutputSide = 0.5;

    final double ClosedBucket = 0;
    final double OpenBucket = 0.5;

    private static final double InBucket = 0.5;
    private static final double OutBucket = 0.3;

    static boolean BucketInB = false;


    private static final double ClosedBucketGate = 0;
    private static final double OpenBucketGate = 0.5;

    static boolean BucketGateClosedB = false;


    static Servo Bucket ;
    static Servo BucketGate ;

    public enum BucketStartPosition {
        //Bucket Points In (towards the intake)
        IN,
        //Bucket Points Out (towards the backboard)

        OUT
    }

    public enum BucketGateStartPosition {
        //Bucket Points In (towards the intake)
        CLOSE,
        //Bucket Points Out (towards the backboard)

        OPEN
    }

    public void init(HardwareMap hwMap, BucketStartPosition BSP, BucketGateStartPosition BGSP) {
        Bucket = hwMap.get(Servo.class,"Bucket");
        BucketGate = hwMap.get(Servo.class,"BucketGate");

        if (BSP == BucketStartPosition.IN) {
            Bucket.setPosition(IntakeSide);
        } else if (BSP == BucketStartPosition.OUT) {
            Bucket.setPosition(OutputSide);
        }

        if (BGSP == BucketGateStartPosition.CLOSE) {
            BucketGateIn();
        } else if (BGSP == BucketGateStartPosition.OPEN) {
            BucketGateOut();
        }

    }

    //Bucket

    public void BucketSet(int BucketPos) {

            switch(BucketPos){
                case 2:
                    Bucket.setPosition(IntakeSide);
                    break;
                case 1: // This is first
                    Bucket.setPosition(OutputSide);
                    break;

            }
        }

    public static void BucketOut(){
        Bucket.setPosition(OutBucket);
        BucketInB=true;
    }
    public static void BucketIn(){
        Bucket.setPosition(InBucket);
        BucketInB=false;
    }

    public static void ToggleBucket(){
        if(BucketInB){
            BucketIn();
        }else{
            BucketOut();
        }
    }

    public static double getBucketPosition(){ return Bucket.getPosition(); }


    //BucketGate

    public static void BucketGateOut(){
        BucketGate.setPosition(OpenBucketGate);
        BucketGateClosedB=true;
    }
    public static void BucketGateIn(){
        BucketGate.setPosition(ClosedBucketGate);
        BucketGateClosedB=false;
    }

    public static void ToggleBucketGate(){
        if(BucketGateClosedB){
            BucketGateIn();
        }else{
            BucketGateOut();
        }
    }


    public void BucketGate(int BucketGatePos) {

        switch(BucketGatePos){
            case 1:
                BucketGate.setPosition(OpenBucket);
                break;
            case 2:
                BucketGate.setPosition(ClosedBucket);
                break;

        }
    }

    public static double getGatePosition(){ return BucketGate.getPosition(); }

}