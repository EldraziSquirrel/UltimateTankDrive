// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;

import frc.robot.subsystems.Arm;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Grabber;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Grabber m_GrabberSub = new Grabber();

  private final Arm m_ArmSub = new Arm();

  private final DriveTrain m_driveTrain = new DriveTrain();

    // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController = new CommandXboxController(1);

  RunCommand driveCommand = new RunCommand(()-> m_driveTrain.drive(m_driverController.getLeftY(), m_driverController.getRightX(), .4),m_driveTrain); 

  RunCommand slowDriveCommand = new RunCommand(()-> m_driveTrain.drive(m_driverController.getLeftY(), m_driverController.getRightX(), .2),m_driveTrain);

  InstantCommand openCommand = new InstantCommand(()-> m_GrabberSub.open(), m_GrabberSub );

  InstantCommand closeCommand = new InstantCommand(()-> m_GrabberSub.close(), m_GrabberSub);

  RunCommand liftCommand = new RunCommand(()-> m_ArmSub.lift(m_operatorController.getRightTriggerAxis(), m_operatorController.getLeftTriggerAxis()),m_ArmSub);

  

  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    
  }


  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    m_driveTrain.setDefaultCommand(driveCommand);

    m_ArmSub.setDefaultCommand(liftCommand);

    //m_GrabberSub.setDefaultCommand(openCommand);

    m_driverController.b().whileTrue(slowDriveCommand);
 
    m_operatorController.rightBumper().onTrue(closeCommand);
    m_operatorController.rightBumper().onFalse(openCommand);


    //m_driverController.getHID().setRumble(RumbleType.kBothRumble, 1);


    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
