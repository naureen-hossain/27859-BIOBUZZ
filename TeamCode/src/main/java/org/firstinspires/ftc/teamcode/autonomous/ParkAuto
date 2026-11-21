package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "ParkAuto")
public class ParkAuto extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;

    private static final double DRIVE_POWER = 0.30;
    private static final long PARK_TIME_MS = 350;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "ParkAuto Ready");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        try {
            strafeRight(PARK_TIME_MS);
        } finally {
            stopDrive();
        }

        telemetry.addData("Status", "ParkAuto Complete");
        telemetry.update();
    }

    private void strafeRight(long duration) {
        drive(DRIVE_POWER, -DRIVE_POWER,
              -DRIVE_POWER, DRIVE_POWER, duration);
    }

    private void drive(double fl, double fr, double bl, double br, long duration) {
        frontLeft.setPower(fl);
        frontRight.setPower(fr);
        backLeft.setPower(bl);
        backRight.setPower(br);

        pause(duration);
        stopDrive();
    }

    private void stopDrive() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    private void pause(long duration) {
        long end = System.currentTimeMillis() + duration;

        while (opModeIsActive() && System.currentTimeMillis() < end) {
            idle();
        }
    }
}
