
package org.usfirst.frc.team3257.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

	public static Joystick stick;
	public static Joystick xbox;


	static Jaguar BL, BR;
	Jaguar ML;
	Jaguar MR;
	static Jaguar FL;
	static Jaguar FR;
	Jaguar winch;
	Jaguar arm;
	DigitalInput limitSwitch;
	AnalogInput pMeter;
	static Ultrasonic distL;
	static Ultrasonic distR;
	boolean done;
	boolean canMoveForward;
	double speedMult;
	Accelerometer accel;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	public void robotInit() {
		oi = new OI();
		canMoveForward = true;
		done = false;
		xbox = new Joystick(0);
		stick = new Joystick(1);
		winch = new Jaguar(5);
		// FR = new Talon(3);
		// // = new Talon(2);
		// BR = new Talon(6);
		//BL = new Talon(0);
		// // ML = new Talon(1);
		//FL = new Talon(5);
		arm = new Jaguar(0);
		FL = new Jaguar(3);
		FR = new Jaguar(2);
		BL = new Jaguar(4);
		BR = new Jaguar(1);
		//limitSwitch = new DigitalInput(1);
		CameraServer server = CameraServer.getInstance();
		server.startAutomaticCapture();

		speedMult = .5;
		distL = new Ultrasonic(3, 2);
		distR = new Ultrasonic(1, 0); // output, input DIO ports

		pMeter = new AnalogInput(0);
	
		accel = new BuiltInAccelerometer();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {

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
		distL.setAutomaticMode(true); // turns on automatic mode
		distR.setAutomaticMode(true);
	}

	public void teleopPeriodic() {
		// System.out.println("test");
		LiveWindow.run();
		//normalDrive();
		drive.main();
		rangeFinder.main(distL, distR);
		if (xbox.getRawButton(2) == true) { // 2=B 
			rangeFinder.align();
		}
		if (xbox.getRawButton(1) == true) { //1=A
			rangeFinder.approach();
		}
//		if (xbox.getRawButton(3) == true) { //3=X
//			//extendArm();
//			
//		}
//		if (xbox.getRawButton(4) == true) { //4=Y
//			//retractArm();
//		}
		
		SmartDashboard.putNumber("X: ", accel.getX());
		SmartDashboard.putNumber("Y: ", accel.getY());
		SmartDashboard.putNumber("Z: ", accel.getZ());
		
		SmartDashboard.putNumber("Adjusted Y", .7071*accel.getY());
		SmartDashboard.putNumber("Adjusted Z: ", -.7071*accel.getZ());
		SmartDashboard.putNumber("X TOTAL G: ", .7071*accel.getY() + -.7071*accel.getZ());
		
		
		arm.set(stick.getY());
		
		//winch.set(stick.getY());
		
		
		
	}

	private void extendArm() {
		System.out.println("Help trapped in robot");
	}

	private void retractArm() {
		System.out.println("#triggered");
	}


	public static void setLeftSpeed(double speed) {
		FL.set(speed);
		// ML.set(speed);
		BL.set(speed);
		//System.out.print("Left: " + speed + ", ");
	}

	public static void setRightSpeed(double speed) {
		FR.set(speed);
		// MR.set(speed);
		BR.set(speed);
		//System.out.println("Right: " + speed);
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
