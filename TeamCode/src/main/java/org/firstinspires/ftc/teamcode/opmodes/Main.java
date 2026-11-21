package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.mecanumMath;
import org.firstinspires.ftc.teamcode.subsystems.innerControl;

@TeleOp(name = "Main TeleOp")
public class Main extends LinearOpMode {
    public DcMotor frontLeft, frontRight, backLeft, backRight;
    public DcMotor intake, buffer;
    public DcMotorEx flywheel;
    public Servo turret;

    private static final double TURRET_SPEED = 1;
    private static final double TURRET_DEADZONE = 0.1;
    private static final double TURRET_CENTER = 0.5;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        intake = hardwareMap.get(DcMotor.class, "intake");
        buffer = hardwareMap.get(DcMotor.class, "buffer");
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        turret = hardwareMap.get(Servo.class, "turret");

        flywheel.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotor.Direction.REVERSE);
        buffer.setDirection(DcMotor.Direction.FORWARD);

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

        innerControl.resetControls();

        double turretPosition = TURRET_CENTER;
        turret.setPosition(turretPosition);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        double lastTime = getRuntime();

        while (opModeIsActive()) {
            mecanumMath.driveMecanum(frontLeft, frontRight, backLeft, backRight, gamepad1);

            innerControl.controlBalls(intake, gamepad1);
            innerControl.controlBuffer(buffer, gamepad1);
            innerControl.controlFlywheel(flywheel, gamepad1);

            double currentTime = getRuntime();
            double deltaTime = Math.max(0.0, Math.min(currentTime - lastTime, 0.1));
            lastTime = currentTime;

            double turretInput = gamepad2.left_stick_x;

            if (Math.abs(turretInput) > TURRET_DEADZONE) {
                turretPosition -= turretInput * TURRET_SPEED * deltaTime;
                turretPosition = Math.max(0.0, Math.min(1.0, turretPosition));
            }

            turret.setPosition(turretPosition);

            telemetry.addData("Flywheel Actual", flywheel.getVelocity());
            telemetry.addData("Buffer Power", buffer.getPower());
            telemetry.addData("Buffer Direction", buffer.getPower() == 0 ? "Stopped" : buffer.getPower() > 0 ? "Reversed" : "Forward");
            telemetry.addData("Turret Position", turretPosition);
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
