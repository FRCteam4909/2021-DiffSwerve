package frc.bionic;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.bionic.swerve.AbstractDrivetrain;

public class TrajectoryUtils {

    SequentialCommandGroup returnCommand;
    /**
     * 
     * @param trajectory The trajectory to follow
     * @param drivetrain The AbstractDrivetrain Object the command should use
     */
    public static void supply(Trajectory trajectory, AbstractDrivetrain drivetrain){
      //Updates Shuffleboard
      SmartDashboard.putBoolean("Supply Running", true);
        //Supplies the current pose constantly
        Supplier<Pose2d> currentPose = () -> drivetrain.getCurrentPose();
        //Sets the kinematics from the AbstractDrivetrain object
        SwerveDriveKinematics kinematics = drivetrain.getKinematics();
        //Makes new PID Controllers
        PIDController xController = new PIDController(1, 0, 0);
        PIDController yController = new PIDController(1, 0, 0);
        ProfiledPIDController thetaController = new ProfiledPIDController(1, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14));
        //Stores the value of .accept
        Consumer<SwerveModuleState[]> outputModuleStates = states -> {};
        //Gets the swerve module states and stores is
        outputModuleStates.accept(drivetrain.getSwerveModuleStates());

        //Calls the followTrajectory method to generate a Command to be run
        new TrajectoryUtils().followTrajectory(trajectory, 
                                                currentPose, 
                                                kinematics, 
                                                xController,
                                                yController, 
                                                thetaController, 
                                                outputModuleStates, 
                                                drivetrain);
      }

      /**
       * 
       * @param startPos The starting position of the robot on the trajectory
       * @param endPos The ending position og the robot on the trajectory
       * @param config The Trajectory config object that defines max velocity and max acceleration
       * @param interiorWaypoints An Arraylist of Translation 2d object which generate the path in between the start and end
       * @return The generated trajectory based off of the given parameters
       */
      public Trajectory generateTrajectory(Pose2d startPos, 
                                            Pose2d endPos, 
                                            TrajectoryConfig config, 
                                            AbstractDrivetrain drivetrain, 
                                            Translation2d... interiorWaypoints) {
        //Updates Shuffleboard
        SmartDashboard.putBoolean("Generate Trajectory Running", true);
        Trajectory returnTrajectory = TrajectoryGenerator.generateTrajectory(startPos, 
                                                                              Arrays.asList(interiorWaypoints), 
                                                                              endPos, 
                                                                              config);
        //Transforms the trajectory to current position of the robot
        return returnTrajectory.transformBy(
                                      new Transform2d(drivetrain.getCurrentPose().getTranslation(), 
                                      Rotation2d.fromDegrees(0)));
      }

      public void followTrajectory(Trajectory trajectory, Supplier<Pose2d> pose, SwerveDriveKinematics kinematics,
      PIDController xController, PIDController yController, ProfiledPIDController thetaController,
      Consumer<SwerveModuleState[]> outputModuleStates, AbstractDrivetrain drivetrain){
        //Updates Shuffleboard
        SmartDashboard.putBoolean("Swerve Controller Command Running", true);
        //Creates a new SwerveControllerCommand object for controlling the robot
        SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(trajectory, 
                                                                                      drivetrain::getCurrentPose, 
                                                                                      kinematics, 
                                                                                      xController, 
                                                                                      yController, 
                                                                                      thetaController, 
                                                                                      drivetrain::actuateModules, 
                                                                                      drivetrain);
        //Resets Initial Odometry to the first Pose in the Trajectory
        drivetrain.resetOdometry(trajectory.getInitialPose()); 
        //Runs the trajectory and then stops the drivetrain
        returnCommand = swerveControllerCommand.andThen(() -> drivetrain.drive(0, 0, 0));
      }

      public Command getSwerveControllerCommand(){
        //Returns the commmand generated by followTrajectory if not null
        if (returnCommand != null) {
          return returnCommand;
        }
        return null;
      }


}