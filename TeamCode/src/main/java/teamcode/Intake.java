package teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    DcMotor motor;
    public final double INTAKE_SPEED = 1;
    int JOEY = 0;

    public void init(HardwareMap hwmap){

        motor = hwmap.get(DcMotor.class, "intakeM");
        motor.setPower(0);

    }
    public void Intake_stop(){ motor.setPower(0); }
    public void Intake_start(){ motor.setPower(-INTAKE_SPEED); }
    public void Intake_Sstart(){ motor.setPower(-INTAKE_SPEED*0.35); }
    public void Intake_inverse(){ motor.setPower(INTAKE_SPEED); }

    public void Slow_Start(){ motor.setPower(-INTAKE_SPEED * 0.25); }
    public void Slow_Inverse(){ motor.setPower(INTAKE_SPEED * 0.25); }
    public int JOEY(){ return JOEY; }

    public class ChangeJoey implements Action {
        public void init() {
            Intake_start();
        }
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            JOEY += 1;
            return false;}
    }

    public class EntakeOn implements Action {
        public void init() {
            Intake_start();
        }
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Intake_inverse();
            return false;}
    }

    public class EntakeBack implements Action {
        public void init() {Intake_Sstart();}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Intake_Sstart();
            return false;}
    }

    public class EntakeNo implements Action {
        public void init() {Intake_stop();}
        public boolean loop(TelemetryPacket packet) {return false;}
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            Intake_stop();
            return false;}
    }


    public Action inverse() {
        return new EntakeBack();
    }
    public Action start() {
        return new EntakeOn();
    }
    public Action stop() {
        return new EntakeNo();
    }
    public Action ChangeJOEY() {
        return new ChangeJoey();
    }

}
