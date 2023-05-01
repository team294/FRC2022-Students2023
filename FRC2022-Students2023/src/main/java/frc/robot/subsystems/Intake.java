// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANid;
import frc.robot.Constants.pneumaticChannels;
import frc.robot.utilities.FileLog;

public class Intake extends SubsystemBase {

  private final WPI_TalonFX intakeMotor;
  private final WPI_TalonFX feederMotor;
  private final DoubleSolenoid solenoid;
  private final String subsystemName;
  private final FileLog log;

  private boolean extended;

  /** Creates a new Intake Subsystem. */
  public Intake(FileLog log) {
    this.log = log;
    intakeMotor = new WPI_TalonFX(CANid.intakeID);
    feederMotor = new WPI_TalonFX(CANid.feederID);
    solenoid = new DoubleSolenoid(CANid.intakePneumaticID, PneumaticsModuleType.REVPH, pneumaticChannels.intakeFoward, pneumaticChannels.intakeBack);
    subsystemName = "Intake";
    extended = false;
  }

  public String getName() {
    return subsystemName;
  }


  /**
   * @param PistonExtended true = extended | false = retracted
   */
  public void setIntakeExtended(boolean PistonExtended){
    extended = PistonExtended;
    solenoid.set(extended ? Value.kForward : Value.kReverse);
  }

  /**
   * @return true = extended | false = retracted
   */
  public boolean isExtended(){
    return extended;
  }

  public void intakeMotorSetPercentOutput(double percent){
    intakeMotor.set(ControlMode.PercentOutput, percent);
  }

  public void feederMotorSetPercentOuput(double percent){
    feederMotor.set(ControlMode.PercentOutput, percent);
  }
  
  public void toggleIntake(){
    setIntakeExtended(!extended);
    intakeMotorSetPercentOutput(extended ? .50 : 0);
    feederMotorSetPercentOuput(extended ? .50 : 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
