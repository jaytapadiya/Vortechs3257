package org.usfirst.frc.team3257.robot;

import java.text.DecimalFormat;

public class drive extends Robot {

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

		double actualY = Math.pow(leftStickY, 3); // cubic function; makes
		// sensitivity at lower
		// magnitude less
		// significant and
		// exponentially increases
		double actualX = 1 - (Math.abs(axisX)) * .5; // this value will be set
		// to the side of motors
		// to which the robot is
		// turning; the inner
		// wheels can turn a
		// maximum of half the
		// speed of the outer
		// wheels

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
		setLeftSpeed(-fl * 0.75);
		setRightSpeed(-fr * 0.75);

	}

}
