package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.mecanumMath;
import org.firstinspires.ftc.teamcode.subsystems.innerControl;

@TeleOp(name = "Main TeleOp")
public class Main extends LinearOpMode {
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor intake, buffer;
    public DcMotorEx flywheelOne, flywheelTwo;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        buffer = hardwareMap.get(DcMotor.class, "buffer");

        flywheelOne = hardwareMap.get(DcMotorEx.class, "flywheelOne");
        flywheelTwo = hardwareMap.get(DcMotorEx.class, "flywheelTwo");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        buffer.setDirection(DcMotor.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        buffer.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheelOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        flywheelTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flywheelOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheelTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        flywheelOne.setVelocityPIDFCoefficients(22.0, 0.0, 0.0, 11.7);
        flywheelTwo.setVelocityPIDFCoefficients(22.0, 0.0, 0.0, 11.7);

        innerControl.resetControls();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        while (opModeIsActive()) {
            mecanumMath.driveMecanum(frontLeft, frontRight, backLeft, backRight, gamepad1);

            innerControl.controlFlywheel(flywheelOne, flywheelTwo, gamepad1);
            innerControl.controlBalls(intake, gamepad1);
            innerControl.controlBuffer(buffer, gamepad1);

            telemetry.addData("Flywheel 1 Actual", flywheelOne.getVelocity());
            telemetry.addData("Flywheel 2 Actual", flywheelTwo.getVelocity());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        intake.setPower(0);
        buffer.setPower(0);
        flywheelOne.setPower(0);
        flywheelTwo.setPower(0);
    }
}
