package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotMotors {
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    public RobotMotors(HardwareMap hardwareMap){
        frontLeftDrive = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRightDrive = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeftDrive = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRightDrive = hardwareMap.get(DcMotorEx. class, "backRight");
    }
}
