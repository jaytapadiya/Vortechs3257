package org.usfirst.frc.team3257.robot;

import java.text.DecimalFormat;

public class drive extends Robot {
	static double currentSpeed = 0.0;
	static double desiredSpeed = 0.0;
	static double maxStep = 0.03;		
	
	public static void main() {
		// TODO Auto-generated method stub
		double axisXinitial = xbox.getRawAxis(4); // axis 4 is right joystick x
		// axis
		DecimalFormat myFormat = new DecimalFormat("0.0");
		double axisX = Double.parseDouble(myFormat.format(axisXinitial));
		double leftStickYinitial = xbox.getY();
		double leftStickY = Double.parseDouble(myFormat.format(leftStickYinitial));
		double fl;
		double bl;
		double fr;
		double br;
		double actualY;
		
<<<<<<< HEAD
		double notActualY = Math.pow(leftStickY, 7); // cubic function; makes
=======

		
		double actualY = Math.pow(leftStickY, 7); // cubic function; makes
>>>>>>> 1d54fe3a4b582fabe1022625fb1074f4cb6d53ad
		// sensitivity at lower
		// magnitude less
		// significant and
		// exponentially increases	
		desiredSpeed = actualY;
		if(currentSpeed == desiredSpeed)
		{
			//do nothing
		}
		else if(currentSpeed > desiredSpeed)
		{
			//slow down
			if(currentSpeed - desiredSpeed < maxStep)
			{
				currentSpeed = desiredSpeed;
			}
			else
			{
				currentSpeed = currentSpeed - maxStep;
			}
		}
		else
		{
		// speed up	
			if(desiredSpeed - currentSpeed < maxStep)
			{
				currentSpeed = desiredSpeed;
			}
			else
			{
				currentSpeed = currentSpeed + maxStep;
			}
		}
		actualY = currentSpeed;
		double actualX = 1 - (Math.abs(axisX)) * .5; // this value will be set
		// to the side of motors
		// to which the robot is
		// turning; the inner
		// wheels can turn a
		// maximum of half the
		// speed of the outer
		// wheels
		double accelX = .68199*accel.getY() + -.68199*accel.getZ();
		
		if (Math.abs(accelX) < 1) {
			 actualY = (leftStickY * Math.abs(1 - accelX));
		} else {
		 actualY = 0;
		}
		
		
		if ((Math.abs(axisX) < .2) && (Math.abs(actualY) > .2)) { // 0.2 is used
			// throughout
			// to
			// account
			// for the
			// inaccuracy
			// of the
			// joystick
			fl = actualY;
			bl = actualY;
			fr = -actualY;
			br = -actualY;
		} else if (axisX > 0.2 && (Math.abs(actualY) > 0.2)) {
			fl = actualY;
			bl = actualY;
			// fr = -actualY /2;
			// br = -actualY / 2;
			fr = -(actualY * actualX);
			br = -(actualY * actualX);
		} else if (axisX < -0.2 && (Math.abs(actualY) > 0.2)) {
			fl = (actualY * actualX);
			bl = (actualY * actualX);
			// fl = actualY / 2;
			// bl = actualY /2;
			fr = -actualY;
			br = -actualY;
		} else if ((Math.abs(actualY) < 0.2) && (Math.abs(axisX) > 0.2)) {
			fl = -(axisX);
			bl = -(axisX);
			fr = -(axisX);
			br = -(axisX);
		} else {
			fl = 0;
			fr = 0;
			bl = 0;
			br = 0;
		}

		// fl = (1-axisX) * actualY;
		// bl = -1 * (1-axisX) * actualY;
		//
		System.out.print("left speed: " + fl * .5 + " ");
		System.out.println("right speed: " + br * .5);
<<<<<<< HEAD
		setLeftSpeed(-fl);
=======
		setLeftSpeed(-fl * 0.7);
>>>>>>> 1d54fe3a4b582fabe1022625fb1074f4cb6d53ad
		setRightSpeed(-fr);

	}

}
