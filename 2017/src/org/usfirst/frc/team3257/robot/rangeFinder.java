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

		while ((distL.getRangeInches() > 34.0) && (distR.getRangeInches() > 34)) {
			setLeftSpeed(.3);
			setRightSpeed(-.3);
		}
		while ((distL.getRangeInches() > 24.0) && (distL.getRangeInches() < 35) && (distR.getRangeInches() > 24)
	&& (distR.getRangeInches() < 35)) {
			setLeftSpeed(.25);
			setRightSpeed(-.25);
		}
		stop();

	}

	public static void stop() {
		setLeftSpeed(0);
		setRightSpeed(0);
	}

	public static void align() {
		while (distL.getRangeInches() + 1 < distR.getRangeInches()) {
			setLeftSpeed(-0.2);
			setRightSpeed(-0.2);
		}

		while (distR.getRangeInches() + 1 < distL.getRangeInches()) {
			setLeftSpeed(0.2);
			setRightSpeed(0.2);
		}
		stop();

	}



}
