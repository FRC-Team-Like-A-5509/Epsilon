package org.usfirst.frc5509.Epsilon;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SwerveController implements PIDSource, PIDOutput {

    private WPI_TalonSRX talon;
    private PIDController controller;
    private final static double P = .006;
    private final static double I = .00002;
    private final static double D = 0;
    private static final int TIMEOUT_MS = 10;


    public SwerveController(int id) {

        talon = new WPI_TalonSRX(id);
        controller = new PIDController(P, I, D, this, this);
        controller.setInputRange(0, 360);
        controller.setOutputRange(-1, 1);
        controller.setContinuous(true);

        talon.configSelectedFeedbackSensor(FeedbackDevice.Analog);
        talon.setSelectedSensorPosition(0, 0, TIMEOUT_MS);


    }

    @Override
    public void pidWrite(double output) {
        talon.set(output);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {

    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public double pidGet() {
        double ticks = talon.getSelectedSensorPosition();
        if(ticks <0){

            ticks = 360 + ((ticks/1024) * 360) % 360;

        }
        else{

            ticks = ((ticks/1024) * 360) % 360;

        }
        return ticks;
    }

    public void setAngle(double angle){

        controller.setSetpoint(angle);

    }

    public void enable(){
        controller.enable();
    }

    public void disable(){
        controller.disable();
    }

    public double getError(){
      return controller.getError();
    }


}