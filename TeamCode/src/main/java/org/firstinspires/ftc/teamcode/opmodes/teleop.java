package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class teleop {
        public static void drive(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight, Gamepad gamepad1) {
                double drive = -gamepad1.left_stick_y;
                double strafe = gamepad1.left_stick_x;
                double twist = gamepad1.right_stick_x;

                double[] speeds = {
                        (drive + strafe + twist),
                        (drive - strafe - twist),
                        (drive - strafe + twist),
                        (drive + strafe - twist)
                };

                double max = Math.abs(speeds[0]);
                for (int i = 0; i < speeds.length; i++) {
                        if (max < Math.abs(speeds[i])) max = Math.abs(speeds[i]);
                }

                if (max > 1) {
                        for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
                }

                frontLeft.setPower(speeds[0]);
                frontRight.setPower(speeds[1]);
                backLeft.setPower(speeds[2]);
                backRight.setPower(speeds[3]);
        }
}
