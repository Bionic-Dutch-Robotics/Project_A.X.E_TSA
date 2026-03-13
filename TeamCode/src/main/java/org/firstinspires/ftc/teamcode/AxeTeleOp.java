package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Project AXE TeleOp")
public class AxeTeleOp extends OpMode {

    //private DcMotorEx and CRServo variables

    private DcMotorEx left;
    private DcMotorEx right;
    private DcMotorEx arm;
    private DcMotorEx extender;
    private CRServo pitch;
    private CRServo roll;
    private CRServo close;
    private CRServo pin;
    private CRServo ramp;

    @Override
    public void init() {
        //hardwareMap
        {
            left = hardwareMap.get(DcMotorEx.class, "Left Drive");
            right = hardwareMap.get(DcMotorEx.class, "Right Drive");
            arm = hardwareMap.get(DcMotorEx.class, "Arm");
            extender = hardwareMap.get(DcMotorEx.class, "Extend");
            pitch = hardwareMap.get(CRServo.class, "Pitch");
            roll = hardwareMap.get(CRServo.class, "Roll");
            close = hardwareMap.get(CRServo.class, "Close");
            pin = hardwareMap.get(CRServo.class, "Pin");
            ramp = hardwareMap.get(CRServo.class, "Ramp");
        }

        //drivetrain setMode
        {
            right.setDirection(DcMotorEx.Direction.REVERSE);
            left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        //arm setMode
        {
            arm.setDirection(DcMotorSimple.Direction.FORWARD);
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm.setTargetPosition(0);
        }
        //extender setMode
        {
            extender.setDirection(DcMotorSimple.Direction.FORWARD);
            extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            extender.setTargetPosition(0);
        }

        //setDirection of the 3 function of the claw
        {
            pitch.setDirection(CRServo.Direction.REVERSE);
            ramp.setDirection(CRServo.Direction.REVERSE);
            pin.setDirection(CRServo.Direction.REVERSE);
        }

        //brake and run to position for arm and extender
        {
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }


    }

    @Override
    public void loop() {

        //variables for drive train
        {
            double leftSideOutput = -gamepad1.left_stick_y + -gamepad1.right_stick_x;
            double rightSideOutput = -gamepad1.left_stick_y - -gamepad1.right_stick_x;
        }

        //this is for gamepad2 for the arm and extender

        //arm
        {
            if (gamepad2.left_bumper) {
                arm.setTargetPosition(500);
                arm.setPower(.3);
            } else if (gamepad2.right_bumper) {
                arm.setTargetPosition(0);
                arm.setPower(-.3);
            } else {
                arm.setPower(0);
            }
        }
        //extender
        {
            if (gamepad2.left_bumper) {
                extender.setTargetPosition(300);
                extender.setPower(.5);
            } else if (gamepad2.right_bumper) {
                extender.setTargetPosition(100);
                extender.setPower(-.5);
            } else {
                extender.setPower(0);
            }
        }

        //this is for gamepad2 for the claw

        //close
        {
            if (gamepad2.a) {
                close.setPower(.5);
            } else if (gamepad2.b) {
                close.setPower(-.5);
            } else {
                close.setPower(0);
            }
        }
        //roll
        {
            if (gamepad2.dpad_left) {
                this.roll.setPower(.5);
            } else if (gamepad2.dpad_right) {
                this.roll.setPower(-.5);
            } else {
                this.roll.setPower(0);
            }
        }
        //pitch
        {
            if (gamepad2.dpad_up) {
                this.pitch.setPower(.5);
            } else if (gamepad2.dpad_down) {
                this.pitch.setPower(-.5);
            } else {
                this.pitch.setPower(0);
            }
        }

        //this is for gamepad1 for ramp and drive train

        //pin
        {
            if (gamepad1.a) {
                pin.setPower(.5);
            } else if (gamepad1.b) {
                pin.setPower(-.5);
            } else {
                pin.setPower(0);
            }
        }
        //ramp
        {
            if (gamepad1.dpad_up) {
                ramp.setPower(.5);
            } else if (gamepad1.dpad_down) {
                ramp.setPower(-.5);
            } else {
                ramp.setPower(0);
            }
        }
        //Drive
        {
            this.runDrive(
                    gamepad1.left_stick_y,
                    gamepad1.left_stick_x
            );
        }
        //data for arm and extender
        {
            telemetry.addData("Arm Current Pos", arm.getCurrentPosition());
            telemetry.addData("Extender Current Pos", extender.getCurrentPosition());
            telemetry.addData("Arm Target Pos", arm.getTargetPosition());
            telemetry.addData("Extender Target Pos", extender.getTargetPosition());
            telemetry.addData("Is Arm Moving?", arm.isBusy());
            telemetry.addData("Is Extender Moving?", extender.isBusy());
            telemetry.update();
        }
    }

    public void runDrive(double forwardPower, double turnPower) {

        double leftSideOutput = -forwardPower + -turnPower;
        double rightSideOutput = -forwardPower - -turnPower;

        //output for drive train

        {
            left.setPower(
                    leftSideOutput
            );

            right.setPower(
                    rightSideOutput
            );
        }

        //telemetry for drive train status

        {
            telemetry.addData("Left Stick Y", -gamepad1.left_stick_y);
            telemetry.update();
        }

    }

}

