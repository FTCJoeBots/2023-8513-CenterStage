package teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Bucket {
    static final double IntakeSide = 0.95;//0.6
    static double OutputSide = -0.6;

    static boolean BucketInB = false;
    private static final double ClosedBucketGate = 0.5;
    private static final double OpenBucketGate = 0.5;
    static boolean BucketGateClosedB = false;
    static Servo Bucket ;
    static Servo BucketGate ;

    public void init(HardwareMap hwMap) {
        Bucket = hwMap.get(Servo.class,"Bucket");
        BucketGate = hwMap.get(Servo.class,"BucketGate");

        Bucket.setPosition(IntakeSide);
        BucketGate.setPosition(2.3);

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

    public static void BucketSet(){
        Bucket.setPosition(IntakeSide);
        BucketInB=true;
    }

    public static void BucketOut(){
        Bucket.setPosition(OutputSide);
        BucketInB=true;
    }
    public static void BucketIn(){
        Bucket.setPosition(IntakeSide);
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
        BucketGate.setPosition(ClosedBucketGate);
        BucketGateClosedB=true;

    }
    public static void BucketGateIn(){
        BucketGate.setPosition(2.3);
        BucketGateClosedB=false;
    }
    public static void ToggleBucketGate(){
        if(BucketGateClosedB){
            BucketGateIn();
        }else{
            BucketGateOut();
        }
    }
   /* public void BucketGate(int BucketGatePos) {

        switch(BucketGatePos){
            case 1:
                BucketGate.setPosition(OpenBucket);
                break;
            case 2:
                BucketGate.setPosition(ClosedBucket);
                break;

        }
    }*/
   public class OutB implements Action {
       public void init() {
           BucketIn();}
       public boolean loop(TelemetryPacket packet) {return false;}
       @Override
       public boolean run(TelemetryPacket telemetryPacket) {
           ToggleBucket();
           return false;}
   }

    public class OutBG implements Action {
        public void init() {
            ToggleBucketGate();}
        public boolean loop() {return false;}
        @Override
        public boolean run( TelemetryPacket telemetryPacket) {
            ToggleBucketGate();
            return false;}
    }



    public Action BucketOutA() {
        return new OutB();
    }
    public Action BucketGateOutA() {
        return new OutBG();
    }

}