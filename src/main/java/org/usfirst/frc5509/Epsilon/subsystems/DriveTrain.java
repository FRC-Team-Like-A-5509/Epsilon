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

import org.usfirst.frc5509.Epsilon.SwerveController;
import org.usfirst.frc5509.Epsilon.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    private static final int TIMEOUT_MS = 10;
    private final static double WHEELBASE = 16.5; // Length between the axis'
	private final static double TRACKWIDTH = 23; // length between two motors on same axis
	//23, 16.5
    private final static double RADIUS = Math.sqrt(Math.pow(WHEELBASE, 2) + Math.pow(TRACKWIDTH, 2));
    private final static double MAX_SPEED = .75;
    private final static double ENCODER_TICKS = 256; // amount of point in wheel rotation for encoder assuming 1024
                                                     // encoder
    private final static double ROTATION_RATIO = 4; // rotation ratio for gearing (probably)
    private final static double CONVERT = ENCODER_TICKS * ROTATION_RATIO;
    private final static double PIDP = 12;
    private final static double PIDI = 0.0001;
    private final static double PIDD = 0;
    private final static double PIDF = 0;
	private double convertUsed = 0;
	private int tickLimit = 20;
	private int degreeError = 15;
	private boolean canDrive = false;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX frontRightDrive;
    private WPI_TalonSRX backRightDrive;
    private WPI_TalonSRX backLeftDrive;
    private WPI_TalonSRX frontLeftDrive;

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	
	private SwerveController frontRightSwerve;
	private SwerveController frontLeftSwerve;
	private SwerveController backRightSwerve;
	private SwerveController backLeftSwerve;


    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        frontRightDrive = new WPI_TalonSRX(8);
        
        
        
        backRightDrive = new WPI_TalonSRX(7);
        
        
        
        backLeftDrive = new WPI_TalonSRX(2);
        
        
        
        frontLeftDrive = new WPI_TalonSRX(1);
        
        
        

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
	
		frontRightSwerve = new SwerveController(6);
		frontLeftSwerve = new SwerveController(3);
		backRightSwerve = new SwerveController(5);
		backLeftSwerve = new SwerveController(4);

    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new Drive());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
			
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
    // here. Call these from Commands.



    public void writeDefaultValues() {
		SmartDashboard.putNumber("drivetrain_p", PIDP);
		SmartDashboard.putNumber("drivetrain_i", PIDI);
		SmartDashboard.putNumber("drivetrain_d", PIDD);
		SmartDashboard.putNumber("drivetrain_f", PIDF);
		SmartDashboard.putNumber("drivetrain_convert", CONVERT);
    }

	public void drive(double rotation, double x, double y) {

		// calculate x/y components for wheels 1 & 3 as thats all that's needed
		double A = x - rotation * (WHEELBASE / RADIUS);
		double B = x + rotation * (WHEELBASE / RADIUS);
		double C = y - rotation * (TRACKWIDTH / RADIUS);
		double D = y + rotation * (TRACKWIDTH / RADIUS);

		double[] speeds = new double[4];
		double[] rotations = new double[4];

		// calculate speed/rotation for each wheel
		speeds[0] = Math.sqrt(Math.pow(B, 2) + Math.pow(C, 2)); //frontright
		rotations[0] = Math.toDegrees(Math.atan2(B, C));

		// System.out.println("Calculated " + rotations[0]);

		speeds[1] = Math.sqrt(Math.pow(B, 2) + Math.pow(D, 2)); //frontleft
		rotations[1] = Math.toDegrees(Math.atan2(B, D));

		speeds[2] = Math.sqrt(Math.pow(A, 2) + Math.pow(D, 2)); // backleft
		rotations[2] = Math.toDegrees(Math.atan2(A, D));

		speeds[3] = Math.sqrt(Math.pow(A, 2) + Math.pow(C, 2)); //backright
		rotations[3] = Math.toDegrees(Math.atan2(A, C));

		int[] ticks = new int[4];

		for (int i = 0; i < 4; i++) {
			rotations[i] *= -1;
			ticks[i] = (int) (rotations[i] * convertUsed / 360.0);
			rotations[i] += 360;
			rotations[i] %= 360;

		}

		//TODO: Make it not go back to 0 when no input from joystick

		for (int i = 0; i < 4; i++) {

			SmartDashboard.putNumber("Speed " + i, speeds[i]);
			SmartDashboard.putNumber("Rotations " + i, rotations[i]);
			SmartDashboard.putNumber("Ticks " + i, ticks[i]);

		}

		frontRightSwerve.setAngle(rotations[0]);
		frontLeftSwerve.setAngle(rotations[1]);
		backLeftSwerve.setAngle(rotations[2]);
		backRightSwerve.setAngle(rotations[3]);

		if(Math.abs(frontRightSwerve.getError()) < degreeError &&
			Math.abs(frontLeftSwerve.getError()) < degreeError &&
			Math.abs(backLeftSwerve.getError()) < degreeError &&
			Math.abs(backRightSwerve.getError()) < degreeError){

			canDrive = true;

		}
		else{

			canDrive = false;

		}

		// normalize speeds to a good speed;
		speeds = normalizeSpeeds(speeds, x, y);
		
		/*PUT SPEEDS BACK IN*/
		//TODO: Make drive wait until all in position
		
		
		if(canDrive){
			frontLeftDrive.set(speeds[1]);
			frontRightDrive.set(speeds[0]);
			backRightDrive.set(speeds[3]);
			backLeftDrive.set(speeds[2]);
		}
		else{
			frontLeftDrive.set(0);
			frontRightDrive.set(0);
			backRightDrive.set(0);
			backLeftDrive.set(0);
		}
		

	}

	public double[] normalizeSpeeds(double[] speeds, double movementX, double movementY) {
		double maxSpeed = speeds[0];
		double minSpeed = speeds[0];
		// find min and max speeds
		for (int i = 1; i < speeds.length; i++) {
			if (speeds[i] > maxSpeed) {
				maxSpeed = speeds[i];
			}
			if (speeds[i] < minSpeed) {
				minSpeed = speeds[i];
			}
		}
		// normalize to fastest speed
		if (maxSpeed > 1) {
			for (int i = 0; i < speeds.length; i++) {
				speeds[i] /= maxSpeed;
			}
		}
		
		return speeds;
	}

	public void enable(){
		frontRightSwerve.enable();
		frontLeftSwerve.enable();
		backRightSwerve.enable();
		backLeftSwerve.enable();
	}

	public void disable(){
		frontRightSwerve.disable();
		frontLeftSwerve.disable();
		backRightSwerve.disable();
		backLeftSwerve.disable();
	}

}
