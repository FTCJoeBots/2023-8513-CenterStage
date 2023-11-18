package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

     DcMotor motor;
    public  final double SHOOTER_SPEED = 1;
     boolean shooterOn;

    public void init(HardwareMap hwmap){

        motor = hwmap.get(DcMotor.class, "intakeM");
        motor.setPower(0);

    }
    public void Shooter_stop(){ motor.setPower(0); shooterOn = false;}
    public void Shooter_start(){ motor.setPower(-SHOOTER_SPEED); shooterOn = true;}
    //public void Shooter_inverse(){ motor.setPower(SHOOTER_SPEED); }
    public void ToggleShooter(){
        if(shooterOn){
            Shooter_stop();
        }else{
            Shooter_start();
        }
    }
}
