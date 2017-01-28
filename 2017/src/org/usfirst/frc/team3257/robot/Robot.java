
package org.usfirst.frc.team3257.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.text.DecimalFormat;

import org.usfirst.frc.team3257.robot.commands.ExampleCommand;
import org.usfirst.frc.team3257.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

    Joystick stick, xbox;
    Jaguar FR, FL, BR, BL, ML, MR;
    DigitalInput limitSwitch;
    boolean done;
    boolean canMoveForward;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @SuppressWarnings("deprecation")
	public void robotInit() {
		oi = new OI();
		canMoveForward = true;
		done = false;
        xbox = new Joystick(0);
        stick = new Joystick(1);
        FR = new Jaguar(0);
        MR = new Jaguar(1);
        BR = new Jaguar(2);
        BL = new Jaguar(3);
        ML = new Jaguar(4);
        FL = new Jaguar(5);
        limitSwitch = new DigitalInput(1);
        CameraServer server = CameraServer.getInstance();
        //(CameraServer.kSize640x480);;
        server.startAutomaticCapture();;

    }
    
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
	}
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    public void teleopInit() {
    }
    public void teleopPeriodic() {
    	normalDrive();
    }
    
	public void normalDrive() {
		
		if(limitSwitch.get()){
			System.out.println("The limit switch is pressed?");
			done = true;
				FL.set(0); // multiply value by .5 to make 50% speed
				BL.set(0);
				FR.set(0);
				BR.set(0);
				ML.set(0);
				MR.set(0);
		}
		if(!done){
			System.out.println("The limit switch was released");
			double axisXinitial = xbox.getRawAxis(4); //axis 4 is right joystick x axis
			DecimalFormat myFormat = new DecimalFormat("0.0");
			double axisX = Double.parseDouble(myFormat.format(axisXinitial));
			double leftStickYinitial = xbox.getY();
			double leftStickY = Double.parseDouble(myFormat.format(leftStickYinitial));
			double fl;
			double bl;
			double fr;
			double br;
			
			double actualY = Math.pow(leftStickY, 7.0); //cubic function; makes sensitivity at lower magnitude less significant and exponentially increases
			double actualX = 1 - (Math.abs(axisX))*.5; //this value will be set to the side of motors to which the robot is turning; the inner wheels can turn a maximum of half the speed of the outer wheels
	
			
			if ((Math.abs(axisX) < .2) && (Math.abs(actualY) > .2)) { //0.2 is used throughout to account for the inaccuracy of the joystick
				fl = actualY;
				bl = actualY;
				fr = -actualY;
				br = -actualY;
			} else if (axisX > 0.2 && (Math.abs(actualY) > 0.2)) {
				fl = actualY;
				bl = actualY;
				fr = -(actualY * actualX);
				br = -(actualY * actualX);
			} else if (axisX < -0.2 && (Math.abs(actualY) > 0.2)) {
				fl = (actualY * actualX);
				bl = (actualY * actualX);
				fr = -actualY;
				br = -actualY;
			} else if ((Math.abs(actualY) < 0.2) && (Math.abs(axisX) > 0.2)) {
				fl = -(axisX);
				bl = -(axisX);
				fr = -(axisX);
				br = -(axisX);
			} else	{
				fl = 0;
				fr = 0;
				bl = 0;
				br = 0;
			}

			FL.set(-fl * .5); // multiply value by .5 to make 50% speed
			BL.set(-bl * .5);
			FR.set(-fr * .5);
			BR.set(-br * .5);
			ML.set(-fl * .5);
			MR.set(-br * .5);
		}
	}

    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
