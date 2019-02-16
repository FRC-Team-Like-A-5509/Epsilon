//package com.shalmezad.swervedrive;
package org.usfirst.frc5509.Epsilon.Swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Represents a full heading of where you want to drive the swerve module
 */
public class SwervePIDOutput implements PIDOutput {
    WPI_TalonSRX talon;

    public SwervePIDOutput(WPI_TalonSRX talon) {
        this.talon = talon;
    }

    @Override
    public void pidWrite(double output) {
        this.talon.set(ControlMode.PercentOutput, output);
    }

}
