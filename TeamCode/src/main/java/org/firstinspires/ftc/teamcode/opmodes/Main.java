package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.mecanumMath;

@TeleOp(name = "Main TeleOp")
public class Main extends LinearOpMode {
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor intake, buffer;
    public DcMotorEx flywheel;

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
        buffer.setDirection(DcMotor.Direction.REVERSE);

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

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        boolean previousR1 = false;
        boolean previousB = false;
        double intakePower = 0;
        double bufferPower = 0;

        double targetSpeed = 911;
        double idleSpeed = 400;

        while (opModeIsActive()) {
            mecanumMath.driveMecanum(frontLeft, frontRight, backLeft, backRight, gamepad1);

            if (gamepad1.right_bumper && !previousR1) {
                intakePower = (intakePower == 0) ? -1 : -intakePower;
            }

            if (gamepad1.left_bumper) {
                intakePower = 0;
            }

            intake.setPower(intakePower);

            if (gamepad1.b && !previousB) {
                bufferPower = (bufferPower == 0) ? 1 : -bufferPower;
            }

            if (gamepad1.x) {
                bufferPower = 0;
            }

            buffer.setPower(bufferPower);

            previousR1 = gamepad1.right_bumper;
            previousB = gamepad1.b;

            if (gamepad1.right_trigger > 0.1) {
                flywheel.setVelocity(targetSpeed);
            } else if (gamepad1.left_trigger > 0.1) {
                flywheel.setVelocity(0);
            } else {
                flywheel.setVelocity(idleSpeed);
            }

            telemetry.addData("Flywheel Actual", flywheel.getVelocity());
            telemetry.update();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        intake.setPower(0);
        buffer.setPower(0);
        flywheel.setPower(0);
    }
}
