package org.usfirst.frc.team3257.robot;

import java.text.DecimalFormat;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class rangeFinder extends Robot {

	public static void main(Ultrasonic distL, Ultrasonic distR) {
		// TODO Auto-generated method stub
		double rangeL = (distL.getRangeInches()) / 12; // reads the range on the
		// ultrasonic sensor
		double rangeR = (distR.getRangeInches()) / 12;
		// Timer.delay(.5);

		DecimalFormat myFormat = new DecimalFormat("0.0");
		String rangeFinalL = myFormat.format(rangeL);
		String rangeFinalR = myFormat.format(rangeR);

		SmartDashboard.putNumber("Left Distance (ft): ", distL.getRangeInches() / 12);
		SmartDashboard.putNumber("Right Distance (ft): ", distR.getRangeInches() / 12);

	}
	
	public static void approach() {
		align();

		while ((distL.getRangeInches() > 42.0) && (distR.getRangeInches() > 42)) {
			setLeftSpeed(-.3);
			setRightSpeed(.3);
		}
		while ((distL.getRangeInches() > 36.0) && (distL.getRangeInches() < 43) && (distR.getRangeInches() > 36)
	&& (distR.getRangeInches() < 43)) {
			setLeftSpeed(-.25);
			setRightSpeed(.25);
		}
		stop();

	}

	public static void stop() {
		setLeftSpeed(0);
		setRightSpeed(0);
	}

	public static void align() {
		while (distL.getRangeInches() + 1 < distR.getRangeInches()) {
			setLeftSpeed(0.4);
			setRightSpeed(0.4);
		}

		while (distR.getRangeInches() + 1 < distL.getRangeInches()) {
			setLeftSpeed(-0.4);
			setRightSpeed(-0.4);
		}
		stop();

	}



}
