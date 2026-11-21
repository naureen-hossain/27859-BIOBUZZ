package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name = "TipAuto")
public class TipAuto extends LinearOpMode {

    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotor intake, buffer;
    private DcMotorEx flywheel;

    private static final double DRIVE_POWER = 0.35;
    private static final double TURN_POWER = 0.30;
    private static final double INTAKE_POWER = -1.0;
    private static final double BUFFER_POWER = 0.5;
    private static final double FLYWHEEL_SPEED = 911;

    private static final long SLIGHT_FORWARD_MS = 350;
    private static final long AFTER_SHOOT_FORWARD_MS = 500;
    private static final long DOUBLE_FORWARD_MS = AFTER_SHOOT_FORWARD_MS * 2;
    private static final long TURN_LEFT_MS = 450;
    private static final long BUFFER_WAIT_MS = 1000;
    private static final long LONG_WAIT_MS = 3000;
    private static final long FINAL_LEFT_MS = 250;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        intake = hardwareMap.get(DcMotor.class, "intake");
        buffer = hardwareMap.get(DcMotor.class, "buffer");
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        buffer.setDirection(DcMotor.Direction.FORWARD);
        flywheel.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        buffer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flywheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheel.setVelocityPIDFCoefficients(22.0, 0.0, 0.0, 11.7);
        flywheel.setVelocity(0);

        intake.setPower(0);
        buffer.setPower(0);

        telemetry.addData("Status", "TipAuto Ready");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        try {
            flywheel.setVelocity(FLYWHEEL_SPEED);

            driveForward(SLIGHT_FORWARD_MS);

            intake.setPower(INTAKE_POWER);
            buffer.setPower(BUFFER_POWER);
            pause(BUFFER_WAIT_MS);

            buffer.setPower(-BUFFER_POWER);
            pause(250);

            buffer.setPower(BUFFER_POWER);
            pause(BUFFER_WAIT_MS);

            buffer.setPower(-BUFFER_POWER);

            driveForward(AFTER_SHOOT_FORWARD_MS);

            turnLeft(TURN_LEFT_MS);
            driveForward(DOUBLE_FORWARD_MS);

            turnLeft(TURN_LEFT_MS);
            driveForward(AFTER_SHOOT_FORWARD_MS);

            pause(LONG_WAIT_MS);

            driveBackward(DOUBLE_FORWARD_MS);

            turnLeft(TURN_LEFT_MS);
            driveForward(DOUBLE_FORWARD_MS);

            turnLeft(TURN_LEFT_MS);
            driveBackward(AFTER_SHOOT_FORWARD_MS);

            buffer.setPower(BUFFER_POWER);
            pause(BUFFER_WAIT_MS);

            buffer.setPower(-BUFFER_POWER);
            pause(250);

            buffer.setPower(BUFFER_POWER);
            pause(BUFFER_WAIT_MS);

            driveForward(AFTER_SHOOT_FORWARD_MS);
            strafeLeft(DOUBLE_FORWARD_MS);
            driveForward(DOUBLE_FORWARD_MS);
            strafeLeft(FINAL_LEFT_MS);

        } finally {
            stopDrive();
            intake.setPower(0);
            buffer.setPower(0);
            flywheel.setVelocity(0);
        }

        telemetry.addData("Status", "TipAuto Complete");
        telemetry.update();
    }

    private void driveForward(long duration) {
        drive( DRIVE_POWER, DRIVE_POWER,
               DRIVE_POWER, DRIVE_POWER, duration);
    }

    private void driveBackward(long duration) {
        drive(-DRIVE_POWER, -DRIVE_POWER,
              -DRIVE_POWER, -DRIVE_POWER, duration);
    }

    private void strafeLeft(long duration) {
        drive(-DRIVE_POWER, DRIVE_POWER,
               DRIVE_POWER, -DRIVE_POWER, duration);
    }

    private void turnLeft(long duration) {
        drive(-TURN_POWER, TURN_POWER,
              -TURN_POWER, TURN_POWER, duration);
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
