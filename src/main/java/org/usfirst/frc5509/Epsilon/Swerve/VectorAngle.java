package org.usfirst.frc5509.Epsilon.Swerve;

public class VectorAngle {

    private boolean magnitudePositive;
    private double angleInTicks;

    public VectorAngle(boolean mag, double ticks) {

        magnitudePositive = mag;
        angleInTicks = ticks;

    }

    public VectorAngle() {

        magnitudePositive = true;
        angleInTicks = 0;

    }

    public double getAngleInTicks() {
        return angleInTicks;
    }

    public boolean isMagnitudePositive() {
        return magnitudePositive;
    }

    public int vectorDirection() {

        if (isMagnitudePositive()) {
            return 1;
        } else {
            return -1;
        }

    }

    public void setMagnitudePositive(boolean set) {
        magnitudePositive = set;
    }

    public void setAngleInTicks(double angle) {
        angleInTicks = angle;
    }


}
