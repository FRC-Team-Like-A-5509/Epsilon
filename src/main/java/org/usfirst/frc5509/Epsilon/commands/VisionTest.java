// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5509.Epsilon.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc5509.Epsilon.Robot;

//LimeLight imports, start
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//LimeLight imports, end

/**
 *
 */
public class VisionTest extends Command {
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry ta = table.getEntry("ta");
    private double totalArea =0;
    private long numPoints = 0;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public VisionTest() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.limelightVision);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setTimeout(10);
        totalArea = 0;
        numPoints = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        ta = table.getEntry("ta");
        totalArea += ta.getDouble(0.0000000);
        numPoints++;
        double averageArea = totalArea / numPoints;
        SmartDashboard.putNumber("LimelightAverageArea", averageArea);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
       return this.isTimedOut() ;//|| Robot.limelightVision.getDistanceFromTargetInInches() < 4;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.drive(0,0,0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
