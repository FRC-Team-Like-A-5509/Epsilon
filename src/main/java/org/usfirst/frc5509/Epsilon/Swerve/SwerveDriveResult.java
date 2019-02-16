//package com.shalmezad.swervedrive;
package org.usfirst.frc5509.Epsilon.Swerve;
/**
 * Represents a result of what the swerve drive is/should-be doing
 */
public class SwerveDriveResult {
    private SwerveModule frontLeftModule;
    private SwerveModule frontRightModule;
    private SwerveModule backLeftModule;
    private SwerveModule backRightModule;

    public SwerveDriveResult(){
        frontLeftModule = new SwerveModule();
        frontRightModule = new SwerveModule();
        backLeftModule = new SwerveModule();
        backRightModule = new SwerveModule();
    }

    public SwerveModule getFrontLeftModule(){
        return frontLeftModule;
    }
    public SwerveModule getFrontRightModule(){
        return frontRightModule;
    }
    public SwerveModule getBackLeftModule(){
        return backLeftModule;
    }
    public SwerveModule getBackRightModule(){
        return backRightModule;
    }

    /**
     * Normalizes the speeds so that the max in a direction is 1.0.
     */
    public void normalizeSpeeds(){
        double maxSpeed = frontLeftModule.speedPercentage;
        maxSpeed = Math.max(maxSpeed, frontRightModule.speedPercentage);
        maxSpeed = Math.max(maxSpeed, backLeftModule.speedPercentage);
        maxSpeed = Math.max(maxSpeed, backRightModule.speedPercentage);
        // normalize to fastest speed
        if (maxSpeed > 1) {
            frontLeftModule.speedPercentage /= maxSpeed;
            frontRightModule.speedPercentage /= maxSpeed;
            backLeftModule.speedPercentage /= maxSpeed;
            backRightModule.speedPercentage /= maxSpeed;
        }
        else if(maxSpeed < -1){
            frontLeftModule.speedPercentage /= maxSpeed * -1;
            frontRightModule.speedPercentage /= maxSpeed * -1;
            backLeftModule.speedPercentage /= maxSpeed * -1;
            backRightModule.speedPercentage /= maxSpeed * -1;
        }
    }
}
