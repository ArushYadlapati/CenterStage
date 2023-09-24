package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
/* Without this, this file will not show in the Autonomous section of the REV Driver Hub.
 Note that REV Driver Hub and REV Driver Station are synonymous. */
public class AutonomousOpMode extends LinearOpMode {
    // Defines 4 Mecanum Wheel Motors.
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declares motors using IDs that match the configuration on the REV Control Hub.
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft"); // Front Left Motor.
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft"); // Back Left Motor.
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight"); // Front Right Motor.
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight"); // Back Right Motor.

        // Sets all motors to use encoders.
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Resets encoder value of viper slide motor to 0.
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set the zero power behavior to BRAKE for all motors.
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse the right side motors (including back right) since we are using mecanum wheels.
        // Reverse left motors (including back left) if you are using NeveRests.
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart(); // Wait for the game to start (driver presses PLAY).

        // Commands to move robot goes here
        encoderMove(0, 1);
        encoderMove(0, -1);
        encoderMove(0, 2);
        encoderMove(0, -2);
        encoderMove(0, 4);
        encoderMove(0, -4);
        turn(500);
        turn(500);

        // Display current motor encoder tick positions.
        telemetry.addData("Front Left Encoder", motorFrontLeft.getCurrentPosition());
        telemetry.addData("Back Left Encoder", motorBackLeft.getCurrentPosition());
        telemetry.addData("Front Right Encoder", motorFrontRight.getCurrentPosition());
        telemetry.addData("Back Right Encoder", motorBackRight.getCurrentPosition());
        telemetry.update();
    }

    // Define a method to perform a move action in a specified direction for a given distance in feet.
    // Works best at ~15% maximum speed.
    private void encoderMove(int direction, int distanceInFeet) {
        // Resets encoder value of viper slide motor to 0.
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double frontLeftPower, backLeftPower, frontRightPower, backRightPower;

        int encoderTicks = distanceInFeet * 525; // Every 525 ticks = 1 foot moved.

        // Determine motor powers based on the specified direction.
        switch (direction) {
            case 0:
            case 360: // Move all 4 mecanum wheels forward
                frontLeftPower = 0.15;
                backLeftPower = 0.15;
                frontRightPower = 0.15;
                backRightPower = 0.15;
                break;
            case 45: // Move the top left and bottom right mecanum wheels forward
                frontLeftPower = 0.15;
                backLeftPower = 0.0;
                frontRightPower = 0.0;
                backRightPower = 0.15;
                break;
            case 90: // Move the top left and bottom right forward, bottom left and top right backward
                frontLeftPower = 0.15;
                backLeftPower = -0.15;
                frontRightPower = -0.15;
                backRightPower = 0.15;
                break;
            case 135: // Move the top right and bottom left mecanum wheels backward
                frontLeftPower = 0.0;
                backLeftPower = -0.15;
                frontRightPower = -0.15;
                backRightPower = 0.0;
                break;
            case 180: // Move the top left and bottom right backward, bottom left and top right backward
                frontLeftPower = -0.15;
                backLeftPower = -0.15;
                frontRightPower = -0.15;
                backRightPower = -0.15;
                break;
            case 225: // Move the top left and bottom right backward
                frontLeftPower = -0.15;
                backLeftPower = 0;
                frontRightPower = 0;
                backRightPower = -0.15;
                break;
            case 270: // Move the top left and bottom right backward, bottom left and top right forward
                frontLeftPower = -0.15;
                backLeftPower = 0.15;
                frontRightPower = 0.15;
                backRightPower = -0.15;
                break;
            case 315: // Move the top right and bottom left mecanum wheels forward
                frontLeftPower = 0.0;
                backLeftPower = 0.15;
                frontRightPower = 0.15;
                backRightPower = 0.0;
                break;
            default:
                frontLeftPower = 0.0;
                backLeftPower = 0.0;
                frontRightPower = 0.0;
                backRightPower = 0.0;
                break;
        }

        // Set target encoder positions for each motor based on encoderTicks input.
        int targetFrontLeft = motorFrontLeft.getCurrentPosition() -encoderTicks;
        int targetBackLeft = motorBackLeft.getCurrentPosition() -encoderTicks;
        int targetFrontRight = motorFrontRight.getCurrentPosition() -encoderTicks;
        int targetBackRight = motorBackRight.getCurrentPosition() -encoderTicks;

        // Set target positions for each motor.
        motorFrontLeft.setTargetPosition(targetFrontLeft);
        motorBackLeft.setTargetPosition(targetBackLeft);
        motorFrontRight.setTargetPosition(targetFrontRight);
        motorBackRight.setTargetPosition(targetBackRight);

        // Set motor run mode to RUN_TO_POSITION.
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor powers.
        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);

        // Wait until the motors reach their targets.
        while (motorFrontLeft.isBusy() || motorBackLeft.isBusy() || motorFrontRight.isBusy() || motorBackRight.isBusy()) {
            assert true; // Passes.
        }

        // Stop all motors.
        setMotorPower(0);
    }

    // Method to turn the robot using encoders.
    private void turn(double encoderTicks) {
    // private void turn(double encoderTicks) {
        // ****
        /* Calculate the encoder ticks for the turn

        double wheelbaseWidthInFeet = 1.0; // Adjust this based on your robot's wheelbase width
        double wheelCircumferenceInFeet = 11.875 / 12.0; // Wheel circumference in feet
        double encoderTicks = (Math.PI * wheelbaseWidthInFeet * degrees) / 360.0 / wheelCircumferenceInFeet * 1440.0;

         */

        // Set target positions for the motors to perform the turn.
        int targetFrontLeft = motorFrontLeft.getCurrentPosition() + (int) encoderTicks;
        int targetBackLeft = motorBackLeft.getCurrentPosition() + (int) encoderTicks;
        int targetFrontRight = motorFrontRight.getCurrentPosition() - (int) encoderTicks;
        int targetBackRight = motorBackRight.getCurrentPosition() - (int) encoderTicks;

        // Set target positions for each motor.
        motorFrontLeft.setTargetPosition(targetFrontLeft);
        motorBackLeft.setTargetPosition(targetBackLeft);
        motorFrontRight.setTargetPosition(targetFrontRight);
        motorBackRight.setTargetPosition(targetBackRight);

        // Set motor run mode to RUN_TO_POSITION.
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set motor powers.
        double power = 0.5; // You can adjust the power based on your robot's capabilities.
        motorFrontLeft.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(-power); // Reverse the direction for turning.
        motorBackRight.setPower(-power); // Reverse the direction for turning.

        // Wait until the motors reach their targets.
        while (motorFrontLeft.isBusy() || motorBackLeft.isBusy() || motorFrontRight.isBusy() || motorBackRight.isBusy()) {
            assert true; // Passes.
        }
    }

    // Define methods to set motor powers.
    private void setMotorPower(double power) {
        motorFrontLeft.setPower(-power);
        motorBackLeft.setPower(-power);
        motorFrontRight.setPower(-power);
        motorBackRight.setPower(-power);
    }
}
