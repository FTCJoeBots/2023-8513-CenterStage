package teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Objects;

public class AutoCords {



    Intake intake = new Intake();
    Lift lift = new Lift();
    Bucket bucket = new Bucket();

    Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));
    MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);


    public void init(HardwareMap hwMap) {

        intake.init(hwMap);
        lift.init(hwMap);
        bucket.init(hwMap);

    }

    public double RedB1(int CordListNumber1, String type1) {

        //  double rb3_x[] = new double[]{ 75.594, 52.594, 85.1, 82.5, 52.5, 23.323, 25.585 };
        double[] rb1_x = new double[]{
                75.594, 52.594,
                100,
                85.1, 82.5, 52.5,
                101,
                23.323,
                102,
                25.585,
                103,
        }; //LIMITS: 23.323 - 85.1

        double[] rb1_y = new double[]{
                12.7, 22.7,
                200,
                10.23, -90.97, -97.5,
                201,
                -74.06734,
                202,
                -103.792939,
                203
        }; //LIMITS: -103.792939 - 22.7

        double[] rb1_deg = new double[]{
                -85, -85,
                300,
                -74, -73, 110,
                301,
                115,
                302,
                116,
                303
        }; //LIMITS: -85 - 116

        double Return_rb1_x = rb1_x[CordListNumber1];
        double Return_rb1_y = rb1_y[CordListNumber1];
        double Return_rb1_deg = rb1_deg[CordListNumber1];

        if (Return_rb1_x == 100 && Return_rb1_y == 200 && Return_rb1_deg == 300) {

            intake.inverse();
            Return_rb1_x = (rb1_x[CordListNumber1-1]) + 0.01;
            Return_rb1_y = (rb1_y[CordListNumber1-1]) + 0.01;
            Return_rb1_deg = rb1_deg[CordListNumber1-1];

        } else if (Return_rb1_x == 101 && Return_rb1_y == 201 && Return_rb1_deg == 301) {

            lift.Pos1();
            bucket.BucketOutA();
            Return_rb1_x = (rb1_x[CordListNumber1-1]) + 0.01;
            Return_rb1_y = (rb1_y[CordListNumber1-1]) + 0.01;
            Return_rb1_deg = rb1_deg[CordListNumber1-1];

        } else if (Return_rb1_x == 102 && Return_rb1_y == 202 && Return_rb1_deg == 302) {

            bucket.BucketGateOutA();
            Return_rb1_x = (rb1_x[CordListNumber1-1]) + 0.01;
            Return_rb1_y = (rb1_y[CordListNumber1-1]) + 0.01;
            Return_rb1_deg = rb1_deg[CordListNumber1-1];

        } else if (Return_rb1_x == 103 && Return_rb1_y == 203 && Return_rb1_deg == 303) {

            lift.Pos0();
            Return_rb1_x = (rb1_x[CordListNumber1-1]) + 0.01;
            Return_rb1_y = (rb1_y[CordListNumber1-1]) + 0.01;
            Return_rb1_deg = rb1_deg[CordListNumber1-1];

        }

        if (Objects.equals(type1, "x")) {return Return_rb1_x;}
        else if (Objects.equals(type1, "y")) {return Return_rb1_y;}
        else if (Objects.equals(type1, "deg")) {return Return_rb1_deg;}
        else {return 0;}
    }



    public double RedB2(int CordListNumber2, String type2) {

        //  double rb3_x[] = new double[]{ 75.594, 52.594, 85.1, 82.5, 52.5, 23.323, 25.585 };
        double[] rb2_x = new double[]{
                57.75,
                100,
                75.1, 75.2, 52.5, 45.323, 40.323,
                101,
                20.585,
                102,

        }; //LIMITS: 23.323 - 85.1

        double[] rb2_y = new double[]{
                13,
                200,
                15.23, -84.97, -89.5, 63.9569, 63.9969,
                201,
                -40.892939,
                202
        }; //LIMITS: -103.792939 - 22.7

        double[] rb2_deg = new double[]{
                -85,
                300,
                -74, -73, 110, 120, 120,
                301,
                121,
                302,
        }; //LIMITS: -85 - 116

        double Return_rb2_x = rb2_x[CordListNumber2];
        double Return_rb2_y = rb2_y[CordListNumber2];
        double Return_rb2_deg = rb2_deg[CordListNumber2];

        if (Return_rb2_x == 100 && Return_rb2_y == 200 && Return_rb2_deg == 300) {

            Return_rb2_x = (rb2_x[CordListNumber2-1]) + 0.01;
            Return_rb2_y = (rb2_y[CordListNumber2-1]) + 0.01;
            Return_rb2_deg = rb2_deg[CordListNumber2-1];

            intake.inverse();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(1.5)
                    .build();

        } else if (Return_rb2_x == 101 && Return_rb2_y == 201 && Return_rb2_deg == 301) {

            Return_rb2_x = (rb2_x[CordListNumber2-1]) + 0.01;
            Return_rb2_y = (rb2_y[CordListNumber2-1]) + 0.01;
            Return_rb2_deg = rb2_deg[CordListNumber2-1];

            lift.Pos1();
            bucket.BucketOutA();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(2)
                    .build();
            bucket.BucketGateOutA();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(1)
                    .build();

        } else if (Return_rb2_x == 102 && Return_rb2_y == 202 && Return_rb2_deg == 302) {

            Return_rb2_x = (rb2_x[CordListNumber2-1]) + 0.01;
            Return_rb2_y = (rb2_y[CordListNumber2-1]) + 0.01;
            Return_rb2_deg = rb2_deg[CordListNumber2-1];

            lift.Pos0();
            bucket.BucketOutA();
            bucket.BucketGateOutA();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(2.5)
                    .build();
        }

        if (Objects.equals(type2, "x")) {return Return_rb2_x;}
        else if (Objects.equals(type2, "y")) {return Return_rb2_y;}
        else if (Objects.equals(type2, "deg")) {return Return_rb2_deg;}
        else {return 0;}
    }




    public double RedB3(int CordListNumber3, String type3) {

        //  double rb3_x[] = new double[]{ 75.594, 52.594, 85.1, 82.5, 52.5, 23.323, 25.585 };
        double rb3_x[] = new double[]{
                43, 43.5,
                100,
                47, 78.1, 82.2, 52.5, 45.323, 32.723,
                101,
                20.585,
                102,
        }; //LIMITS: 23.323 - 85.1

        double rb3_y[] = new double[]{
                -3, -7.1,
                200,
                5.1, 5.23, -92.97, -90.5, -80.3569, -80.3967,
                201,
                -40.892939,
                202//This is dif//This is dif


        }; //LIMITS: -103.792939 - 22.7

        double rb3_deg[] = new double[]{
                -85, -85,
                300,
                -85, -74, -73, 110, 120, 120,
                301,
                121,
                302,

        }; //LIMITS: -85 - 116

        double Return_rb3_x = rb3_x[CordListNumber3];
        double Return_rb3_y = rb3_y[CordListNumber3];
        double Return_rb3_deg = rb3_deg[CordListNumber3];

        double Return_rb3_xMinus = (rb3_x[CordListNumber3-1]) + 0.01;
        double Return_rb3_yMinus = (rb3_y[CordListNumber3-1]) + 0.01;
        double Return_rb3_degMinus = rb3_deg[CordListNumber3-1];

        if (Return_rb3_x == 100 && Return_rb3_y == 200 && Return_rb3_deg == 300) {

            intake.inverse();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(1.5)
                    .build();
            Return_rb3_x = Return_rb3_xMinus;
            Return_rb3_y = Return_rb3_yMinus;
            Return_rb3_deg = Return_rb3_degMinus;

        } else if (Return_rb3_x == 101 && Return_rb3_y == 201 && Return_rb3_deg == 301) {

            lift.Pos1();
            bucket.BucketOutA();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(2)
                    .build();
            bucket.BucketGateOutA();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(1)
                    .build();
            Return_rb3_x = Return_rb3_xMinus;
            Return_rb3_y = Return_rb3_yMinus;
            Return_rb3_deg = Return_rb3_degMinus;

        } else if (Return_rb3_x == 102 && Return_rb3_y == 202 && Return_rb3_deg == 302) {

            lift.Pos0();
            bucket.BucketOutA();
            bucket.BucketGateOutA();
            drive.actionBuilder(drive.pose)
                    .waitSeconds(2.25)
                    .build();
            Return_rb3_x = Return_rb3_xMinus;
            Return_rb3_y = Return_rb3_yMinus;
            Return_rb3_deg = Return_rb3_degMinus;

        }

        if (Objects.equals(type3, "x")) {return Return_rb3_x;}
        else if (Objects.equals(type3, "y")) {return Return_rb3_y;}
        else if (Objects.equals(type3, "deg")) {return Return_rb3_deg;}
        else {return 0;}
    }
}