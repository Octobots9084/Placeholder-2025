package frc.robot.Commands.ReefSelection;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystems.Elevator.Elevator;
import frc.robot.Subsystems.Elevator.ElevatorStates;

public class ReefLevelSelection extends InstantCommand {
  private int level;

  public ReefLevelSelection(int level) {
    this.level = level;
  }

  @Override
  public void initialize() {
    Elevator.getInstance().setTargetState(ElevatorStates.values()[level]);
  }
}
