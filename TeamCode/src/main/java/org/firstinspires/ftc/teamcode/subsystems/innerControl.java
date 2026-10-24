package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;

public class innerControl {
    private static double targetSpeed = 911;
    private static double idleSpeed = 400;

    private static boolean previousR1 = false;
    private static boolean previousB = false;
    private static double intakePower = 0;
    private static double bufferPower = 0;

    public static void controlFlywheel(DcMotorEx flywheel, Gamepad gamepad) {
        if (gamepad.right_trigger > 0.1) {
            flywheel.setVelocity(targetSpeed);
        } else if (gamepad.left_trigger > 0.1) {
            flywheel.setVelocity(0);
        } else {
            flywheel.setVelocity(idleSpeed);
        }
    }

    public static void controlBalls(DcMotor intake, Gamepad gamepad) {
        if (gamepad.right_bumper && !previousR1) {
            intakePower = (intakePower == 0) ? -1 : -intakePower;
        }

        if (gamepad.left_bumper) {
            intakePower = 0;
        }

        intake.setPower(intakePower);
        previousR1 = gamepad.right_bumper;
    }

    public static void controlBuffer(DcMotor buffer, Gamepad gamepad) {
        if (gamepad.b && !previousB) {
            bufferPower = (bufferPower == 0) ? 1 : -bufferPower;
        }

        if (gamepad.x) {
            bufferPower = 0;
        }

        buffer.setPower(bufferPower);
        previousB = gamepad.b;
    }

    public static void resetControls() {
        previousR1 = false;
        previousB = false;
        intakePower = 0;
        bufferPower = 0;
    }
}
