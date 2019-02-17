// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc5509.Epsilon;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5509.Epsilon.Robot;
//LimeLight imports, start
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

//LimeLight imports, end
/**
 *
 */
public class VisionDrive extends Command {
    //public AHRS ahrs;

    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry ta = table.getEntry("ta");
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public VisionDrive() {
        try {
            /* Communicate w/navX-MXP via the MXP SPI Bus. */
            /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
            /*
             * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
             * details.
             */
            //ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
        }
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        //ahrs.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        // If the target is above the crosshair, we are too close, move back
        // If the target is below the crosshair, we aren't close enough, move forward
        // If the target is to the left of the crosshair, we are too far to the right,
        // move left
        // If the target is to the right of the crosshair, we are too far to the left,
        // move right
        double Kp = SmartDashboard.getNumber("Limelight_Kp", -0.05);//-0.1f; // Proportional control constant
        double min_command = SmartDashboard.getNumber("Limelight_min_command", -0.05);//0.05f;
        SmartDashboard.putNumber("Limelight_Kp_value", Kp);//-0.1f; // Proportional control constant
        SmartDashboard.putNumber("Limelight_min_command_value", min_command);//0.05f;
        // LimelightVision vision = new LimelightVision();
        double tx = getXInDegrees();
        double ty = getYInDegrees();
        double allowedError = .07;
    
        // if (joystick1.getRawAxis(9) == 1){
        if (getValidTarget() == 1) {
            double heading_errorX = -tx;
            double heading_errorY = -ty;
            double x_adjust = 0.0; // uses the x values, determines side to side movement
            double y_adjust = 0.0; // uses the y values, determines front to back movement

            if (tx > 1.0) {
                x_adjust = Math.tanh(Kp * heading_errorX - min_command);// target is to the right of the cross hair,
                                                                        // move right
            } else if (tx < 1.0) {
                x_adjust = Math.tanh(Kp * heading_errorX + min_command);// target is to the left of the cross hair,
                                                                             // move left
            }

            if(Math.abs(x_adjust) >= .4){
                x_adjust = Math.signum(x_adjust) * .4;
            }
            if(Math.abs(x_adjust) <= allowedError){
                x_adjust = 0;
            }

            if (ty > 1.0) {
                y_adjust = Math.tanh(-.03 * heading_errorY - min_command);
            } else if (ty < 1.0) {
                y_adjust = Math.tanh((-.03 * heading_errorY + min_command));
            }

            if(Math.abs(y_adjust) >= .4){
                y_adjust = Math.signum(y_adjust) * .4;
            }
            if(Math.abs(y_adjust) <= allowedError){
                y_adjust = 0;
            }
            // calculate the angle we want to turn by
            double angle = 0;//ahrs.getYaw();
            double angleToTurnTo = 0;
            double minDifference = 1000;
            double[] snapAngles = { -180, -120, -90, -60, 0, 60, 90, 120, 180 };
            // first, find the angle to turn to
            for (int i = 0; i < snapAngles.length; i++) {
                if (Math.abs(snapAngles[i] - angle) <= minDifference) {
                    angleToTurnTo = snapAngles[i];
                    minDifference = Math.abs(snapAngles[i] - angle);
                }
            }
            SmartDashboard.putNumber("AngleToTurnTo", angleToTurnTo);
            // strafe_command += x_adjust;
            SmartDashboard.putNumber("x_adjust", x_adjust);
            // frontBack_command += y_adjust;
            SmartDashboard.putNumber("y_adjust", y_adjust);

            // GET TO THE CORRECT ANGLE
            angle = 0;//ahrs.getYaw();
            double angleError = angleToTurnTo - angle;
            double steering_adjust = 0.0f;
            double allowedAngleError = 1.00;
            double turnSpeed = 0.0;
            // Don't turn if we're in the tolerance
            if (Math.abs(angleError) > allowedAngleError) {
                double maxAngle = 30.0;
                double adjustedError = angleError / maxAngle;
                // Clip it:
                turnSpeed = Math.min(adjustedError, 1.0);
                turnSpeed = Math.max(adjustedError, -1.0);
                // Make sure we have *some* speed
                if (Math.abs(turnSpeed) < 0.2) {
                    turnSpeed = 0.2 * Math.signum(turnSpeed);
                }
            }
            if(Math.abs(turnSpeed) >= .4){
                turnSpeed = Math.signum(turnSpeed) * .4;
            }
            SmartDashboard.putNumber("AngleTurnPower", turnSpeed);
            Robot.driveTrain.drive(turnSpeed, x_adjust, y_adjust);
        }
        // }
    }

    public double getYInDegrees() {
        NetworkTableEntry ty = table.getEntry("ty");
        double y = ty.getDouble(0.0);
        return y;
    }

    public double getXInDegrees() {
        NetworkTableEntry tx = table.getEntry("tx");
        double x = tx.getDouble(0.0);
        return x;
    }

    public double getValidTarget() {
        NetworkTableEntry tv = table.getEntry("tv");
        double v = tv.getDouble(0.0);
        return v;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
