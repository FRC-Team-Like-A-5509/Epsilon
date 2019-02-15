package org.usfirst.frc5509.Epsilon.Swerve;

/**
 * Static configuration for the swerve drive.
 * Gives info that will not change (it's set in the hardware).
 * ex: track width, ticks per 360 rotation, etc.
 */
public class SwerveDriveConfig {
    /**
     * Length between two motors on the same axis
     */
    public double track_width = 23;
    /**
     * Length between the axis between the two motors
     */
    public double wheel_base = 16.5;

    /**
     * How many ticks per 360 rotation of the swerve module
     */
    public double ticks_per_360 = 1024;

    public double getRadius(){
        return Math.sqrt(Math.pow(wheel_base, 2) + Math.pow(track_width, 2));
    }

    public double getTicksPerDegree(){
        return ticks_per_360 / 360.0;
    }
}
