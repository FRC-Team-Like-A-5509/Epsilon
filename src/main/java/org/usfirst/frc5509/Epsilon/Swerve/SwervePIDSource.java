//package com.shalmezad.swervedrive;
package org.usfirst.frc5509.Epsilon.Swerve;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Represents a full heading of where you want to drive the swerve module
 */
public class SwervePIDSource implements PIDSource {
    WPI_TalonSRX talon;

    public SwervePIDSource(WPI_TalonSRX talon) {
        this.talon = talon;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return null;
    }

    @Override
    public double pidGet() {
        return talon.getSelectedSensorPosition();
    }

}
