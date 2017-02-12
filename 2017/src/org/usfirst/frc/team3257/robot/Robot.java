
package org.usfirst.frc.team3257.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
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
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
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
	// AnalogInput pMeter;
	AnalogPotentiometer pot;
	double potDegrees;
	static Ultrasonic distL;
	static Ultrasonic distR;
	boolean done;
	boolean canMoveForward;
	double speedMult;
	Accelerometer accel;
	double currentSpeed;
	double desiredSpeed;
	double maxStep;

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

		//pMeter = new AnalogInput(0);
		
		pot = new AnalogPotentiometer(3);
	
		accel = new BuiltInAccelerometer();
		
		currentSpeed = 0.0;
		desiredSpeed = 0.0;
		maxStep = 0.05;
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
		
		SmartDashboard.putNumber("X: ", accel.getX());
		SmartDashboard.putNumber("Y: ", accel.getY());
		SmartDashboard.putNumber("Z: ", accel.getZ());
		// SmartDashboard.putNumber("arm: ", pMeter.getVoltage());
		
		potDegrees = pot.get()*100;
		SmartDashboard.putNumber("Potentiometer: ", potDegrees);
		
//		SmartDashboard.putNumber("Adjusted Y", .7071*accel.getY());
//		SmartDashboard.putNumber("Adjusted Z: ", -.7071*accel.getZ());
		SmartDashboard.putNumber("X TOTAL G: ", .68199*accel.getY() + -.68199*accel.getZ());
		
		if ((potDegrees < 20) && stick.getY() > 0) {

			arm.set(stick.getY()*.5);		
		
		} else if ((potDegrees > 55) && stick.getY() < 0) {
			arm.set(stick.getY() * .5);
		} else if ((potDegrees > 20) && (potDegrees < 55)) {
			arm.set(stick.getY()*.5);
			
		}else {arm.set(0);}
		
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
