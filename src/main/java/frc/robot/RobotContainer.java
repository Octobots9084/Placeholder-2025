// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Commands.AlgaeRollers.AlgaeRollersManual;
import frc.robot.Commands.CoralRollers.CoralRollersManual;
import frc.robot.Commands.Elevator.ElevatorManual;
import frc.robot.Commands.Wrist.WristManual;
import frc.robot.Commands.swerve.drivebase.TeleopDrive;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Subsystems.AlgaeRollers.AlgaeRollers;
import frc.robot.Subsystems.CoralRollers.CoralRollers;
import frc.robot.Subsystems.Elevator.Elevator;
import frc.robot.Subsystems.Swerve.Swerve;
import frc.robot.Subsystems.Swerve.SwerveIO;
import frc.robot.Subsystems.Swerve.SwerveIOSystem;
import frc.robot.Subsystems.Vision.VisionSubsystem;
import frc.robot.Subsystems.Wrist.Wrist;
import java.util.Optional;
import org.ironmaple.simulation.SimulatedArena;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final SendableChooser<Command> autoChooser;

  // The robot's subsystems and commands are defined here...
  private AlgaeRollers algaeRollers;
  private CoralRollers coralRollers;
  private Elevator elevator;
  private Wrist wrist;
  private Swerve swerve;

  private AlgaeRollersManual algaeRollersManual;
  private CoralRollersManual coralRollersManuel;
  private ElevatorManual elevatorManel;
  private WristManual wristManuel;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        Optional<Alliance> ally = DriverStation.getAlliance();
        if (ally.isPresent()) {
          if (ally.get() == Alliance.Red) {
            Constants.isBlueAlliance = false;
          }
          if (ally.get() == Alliance.Blue) {
            Constants.isBlueAlliance = true;
          }
        }

        // AlgaeRollers.setInstance(new AlgaeRollersIOSystems());
        // algaeRollers = AlgaeRollers.getInstance();

        // CoralRollers.setInstance(new CoralRollersIOSystems());
        // coralRollers = CoralRollers.getInstance();

        // Elevator.setInstance(new ElevatorIOSparkMax());
        // elevator = Elevator.getInstance();

        // Wrist.setInstance(new WristIOSparkMax());
        // wrist = Wrist.getInstance();

        Swerve.setInstance(new SwerveIOSystem());
        swerve = Swerve.getInstance();
        break;

      case SIM:
        Swerve.setInstance(new SwerveIOSystem());
        swerve = Swerve.getInstance();

        // AlgaeRollers.setInstance(
        // new
        // AlgaeRollersIOSim(swerve.getIo().getSwerveDrive().getMapleSimDrive().get()));
        // algaeRollers = AlgaeRollers.getInstance();

        // CoralRollers.setInstance(new CoralRollersIOSim());
        // coralRollers = CoralRollers.getInstance();

        // Elevator.setInstance(new ElevatorIOSim());
        // elevator = Elevator.getInstance();

        // Wrist.setInstance(new WristIOSim());
        // wrist = Wrist.getInstance();

        SimulatedArena.getInstance().resetFieldForAuto();
        break;

      case REPLAY:
        // AlgaeRollers.setInstance(new AlgaeRollersIO() {});
        // algaeRollers = AlgaeRollers.getInstance();

        // CoralRollers.setInstance(new CoralRollersIO() {});
        // coralRollers = CoralRollers.getInstance();

        // Elevator.setInstance(new ElevatorIO() {});
        // elevator = Elevator.getInstance();

        // Wrist.setInstance(new WristIO() {});
        // wrist = Wrist.getInstance();

        Swerve.setInstance(new SwerveIO() {});
        swerve = Swerve.getInstance();
        break;
      default:
        break;
    }

    TeleopDrive closedFieldRel =
        new TeleopDrive(
            () ->
                MathUtil.applyDeadband(
                    -ButtonConfig.driverLeft.getRawAxis(1), OperatorConstants.LEFT_Y_DEADBAND),
            () ->
                MathUtil.applyDeadband(
                    -ButtonConfig.driverLeft.getRawAxis(0), OperatorConstants.LEFT_X_DEADBAND),
            () ->
                MathUtil.applyDeadband(
                    -ButtonConfig.driverRight.getRawAxis(0), OperatorConstants.RIGHT_X_DEADBAND));
    Swerve.getInstance();
    Swerve.getInstance().setDefaultCommand(closedFieldRel);
    autoChooser = AutoBuilder.buildAutoChooser(); // Default auto will be `Commands.none()`
    SmartDashboard.putData("Auto Mode", autoChooser);
    VisionSubsystem.getInstance();
    ButtonConfig buttons = new ButtonConfig();
    buttons.initTeleop();

    // this.algaeRollers = new AlgaeRollers();
    // this.coralRollers = new CoralRollers();
    // this.elevator = new Elevator();
    // this.wrist = new Wrist();

    // this.algaeRollersManual = new AlgaeRollersManual();
    // this.coralRollersManuel = new CoralRollersManual();
    // this.elevatorManel = new ElevatorManual();
    // this.wristManuel = new WristManual();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
