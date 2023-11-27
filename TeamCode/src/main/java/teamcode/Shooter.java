package teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

//J.A.L, Joebotical Airline Launcher -or- Joebotical Automatic Launcher
        public class Shooter {

            private static final double Loaded = 1;
            private static final double Out = 0.5;
            static boolean ShooterLoaded = false;
            static Servo shooter ;

            public void init(HardwareMap hwMap){
                shooter = hwMap.get(Servo.class,"Shooter");
                shooter.setPosition(Loaded);
            }

            public static void shoot(){
                shooter.setPosition(Out);
                ShooterLoaded=false;
            }
        }