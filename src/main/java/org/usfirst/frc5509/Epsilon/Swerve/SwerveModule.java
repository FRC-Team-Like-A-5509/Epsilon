package org.usfirst.frc5509.Epsilon.Swerve;

/**
 * Represents a single module of the swerve drive
 */
public class SwerveModule {
    /**
     * The ticks the wheel is turned.
     * Positive is clockwise.
     */
    public int turnHeadingTicks;
    public double speedPercentage;

    public void setTurnHeadingDegrees(SwerveDriveConfig config, double degrees){
        turnHeadingTicks = (int)(config.getTicksPerDegree() * degrees);
    }

    public void setTurnHeadingTicks(double ticks){
        turnHeadingTicks = (int) ticks;
    }
}
