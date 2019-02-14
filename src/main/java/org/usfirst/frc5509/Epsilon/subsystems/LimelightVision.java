// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc5509.Epsilon.subsystems;

import org.usfirst.frc5509.Epsilon.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
//LimeLight imports, start
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//LimeLight imports, end

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class LimelightVision extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    //private final double cameraHeightInInches = 14.25;
    //private final double targetHeightInInches = 29.0;
    //private double robotDistance = 0.0;
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry ta = table.getEntry("ta");
    private double totalArea =0;

    public LimelightVision() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new VisionTest());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        /*
         * tv Whether the limelight has any valid targets (0 or 1) 
         * tx Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees) 
         * ty Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees) 
         * ta Target Area (0% of image to 100% of image) 
         * ts Skew or rotation (-90 degrees to 0 degrees) 
         * tl The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
         */
        
        // post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", getXInDegrees());
        SmartDashboard.putNumber("LimelightY", getYInDegrees());
        //broke average distance when we fixed the camera settings
        for(int i = 0; i < 10; i++){
            totalArea += getDistanceFromTargetInInches();
        }
        SmartDashboard.putNumber("DistanceFromTargetInInches", (totalArea / 10));
        totalArea = 0;
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public double getDistanceFromTargetInInches() {
        return 34.5 - 6.96 * Math.log(ta.getDouble(0.0000000));
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
}
