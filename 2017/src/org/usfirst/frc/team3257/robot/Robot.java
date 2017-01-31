
package org.usfirst.frc.team3257.robot;

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

    Joystick stick, xbox;
    
	Talon FR, FL, BR, BL, ML, MR;
    DigitalInput limitSwitch;
    Ultrasonic distL, distR;
    boolean done;
    boolean canMoveForward;
    double speedMult;

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
        FR = new Talon(3);
        MR = new Talon(2);
        BR = new Talon(6);
        BL = new Talon(0);
        ML = new Talon(1);
        FL = new Talon(5);
        limitSwitch = new DigitalInput(1);
        CameraServer server = CameraServer.getInstance();
        server.startAutomaticCapture();
        
        speedMult = .5;
        distL = new Ultrasonic(5,6);
        distR = new Ultrasonic(3,2); //output, input DIO ports
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
    	distL.setAutomaticMode(true); // turns on automatic mode
    	distR.setAutomaticMode(true);
    }
    public void teleopPeriodic() {
    	LiveWindow.run();
    	normalDrive();
    	rangeFinder();
    	if(stick.getRawButton(2) == true){
    		align();
    	}
    	if(stick.getRawButton(1) == true) {
    		approach();
    	}
    }
    
    public void approach() {
    	align();
    	
    	while ((distL.getRangeInches() > 12.0) && (distR.getRangeInches() > 12.0)) {
    		setLeftSpeed(-.3);
    		setRightSpeed(.3);
    	}
    	while ((distL.getRangeInches() > 2.0) && (distL.getRangeInches() < 13) && (distR.getRangeInches() > 2.0) && (distR.getRangeInches() < 13)) {
    		setLeftSpeed(-.25);
    		setRightSpeed(.25);
    	}
    	stop();
	
    }
    
    private void stop(){
    	setLeftSpeed(0);
    	setRightSpeed(0);
    }
    
    public void align(){
    	while(distL.getRangeInches() + 1 < distR.getRangeInches()){
    		setLeftSpeed(0.4);
    		setRightSpeed(0.4);
    	} 
    	
    	while(distR.getRangeInches() + 1 < distL.getRangeInches()){
    		setLeftSpeed(-0.4);
    		setRightSpeed(-0.4);
    	} 
    	stop();
    	
    }
    
    private void setLeftSpeed(double speed){
		FL.set(speed);
		ML.set(speed);
		BL.set(speed);
    }
    
    private void setRightSpeed(double speed){
		FR.set(speed);
		MR.set(speed);
		BR.set(speed);	
    }
    	
    
    public void rangeFinder() {
    	double rangeL = (distL.getRangeInches())/12; // reads the range on the ultrasonic sensor
    	double rangeR = (distR.getRangeInches())/12;
    	//Timer.delay(.5);
		DecimalFormat myFormat = new DecimalFormat("0.0");
		String rangeFinalL = myFormat.format(rangeL);
		String rangeFinalR = myFormat.format(rangeR);
		
    	SmartDashboard.putNumber("Left Distance (ft): ", distL.getRangeInches());
    	SmartDashboard.putNumber("Right Distance (ft): ", distR.getRangeInches());
//		System.out.print(String.valueOf(distL.getRangeInches()) + " ");
//		System.out.println(String.valueOf(distR.getRangeInches()));
//    	
    }
    
	public void normalDrive() {
		
		//SmartDashboard.getNumber("Percent Speed: ", speedMult);
		
		
		
//		if(limitSwitch.get()){
//			//System.out.println("The limit switch is pressed?");
//			done = true;
//				FL.set(0); // multiply value by .5 to make 50% speed
//				BL.set(0);	
//				FR.set(0);
//				BR.set(0);
//				ML.set(0);
//				MR.set(0);
//		}
//		else {
			//System.out.println("The limit switch was released");
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

			setLeftSpeed(-fl);
			setRightSpeed(-fr);

	//	}
	}

    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
