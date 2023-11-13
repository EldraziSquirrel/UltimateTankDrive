// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {

  private final TalonSRX frontRight = new TalonSRX(18);
  private final TalonSRX frontLeft = new TalonSRX(13);
  private final TalonSRX backRight = new TalonSRX(11);
  private final TalonSRX backLeft = new TalonSRX(12);

  private double scalar;

  /** Creates a new DriveTrain. */
  public DriveTrain() {
    frontRight.configFactoryDefault();
    frontLeft.configFactoryDefault();
    backRight.configFactoryDefault();
    backLeft.configFactoryDefault();

    frontRight.setInverted(InvertType.InvertMotorOutput);
    backRight.setInverted(InvertType.InvertMotorOutput);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    //configureThings(frontLeft);
    //configureThings(frontRight);
    //configureThings(backRight);
    //configureThings(backLeft);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void slowDrive(double drive, double turn, double scalar){
    this.scalar = scalar;
    frontLeft.set(ControlMode.PercentOutput, (drive + turn) * scalar);
    frontRight.set(ControlMode.PercentOutput, (drive - turn) * scalar);
  }

  public void drive( double drive, double turn, double scalar){
    this.scalar = scalar;
    frontLeft.set(ControlMode.PercentOutput, (drive + turn) * scalar);
    frontRight.set(ControlMode.PercentOutput, (drive - turn) * scalar);

  }

  public void configureThings(TalonSRX motor) {

    motor.configVoltageCompSaturation(12);
    motor.enableVoltageCompensation(true);

    SupplyCurrentLimitConfiguration driveSupplyConfig = new SupplyCurrentLimitConfiguration(true, 25, 30, 50);
    motor.configSupplyCurrentLimit(driveSupplyConfig);

    motor.configPeakOutputForward(.2,1000);
    motor.configPeakOutputReverse(.2,1000);

  }
}
