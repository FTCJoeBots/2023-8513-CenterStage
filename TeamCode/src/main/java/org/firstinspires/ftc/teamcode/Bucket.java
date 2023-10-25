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
    final double INITBucket = 0;
    final double INITGate = 0;

    final double IntakeSide = 0;
    final double OutputSide = 0;

    final double ClosedBucket = 0;
    final double OpenBucket = 0.;
    Servo Bucket = null;
    Servo BucketGate = null;
    public enum BucketStartPosition {
        FORWARD,
        BACK
    }

    public void init(HardwareMap hwMap, BucketStartPosition BSP) {
        Bucket = hwMap.get(Servo.class,"Bucket");
        BucketGate = hwMap.get(Servo.class,"BucketGate");

        if (BSP == BucketStartPosition.FORWARD) {
            Bucket.setPosition(IntakeSide);
        } else if (BSP == BucketStartPosition.BACK) {
            Bucket.setPosition(IntakeSide);
        }

    }

    public void BucketSet(int shoulderPosition) {

            switch(shoulderPosition){
                case 2:
                    Bucket.setPosition(IntakeSide);
                    break;
                case 1: // This is first
                    Bucket.setPosition(OutputSide);
                    break;

            }
        }

    public void BucketGate(int shoulderPosition) {

        switch(shoulderPosition){
            case 1:
                BucketGate.setPosition(OpenBucket);
                break;
            case 2:
                BucketGate.setPosition(ClosedBucket);
                break;

        }
    }

    public double getBucketPosition(){ return Bucket.getPosition(); }
    public double getGatePosition(){ return BucketGate.getPosition(); }

}