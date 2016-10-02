package week2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.ColorAdapter;
import lejos.utility.Delay;

public class LineFollower {
	static EV3 ev3;
	static GraphicsLCD graphicsLCD;
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
	static ColorAdapter colorAdapter = new ColorAdapter(colorSensor);
	static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
	

	
	static float threshold = 25;
	static int rotateSpeed = 10;
	
	public static void main(String[] args){
		
		ev3 = (EV3) BrickFinder.getDefault(); 
		graphicsLCD = ev3.getGraphicsLCD();
		leftMotor.setAcceleration(600);
		rightMotor.setAcceleration(600);
		leftMotor.setSpeed(50);
		rightMotor.setSpeed(50);
		
	    graphicsLCD.clear();
		graphicsLCD.drawString("" + colorAdapter.getColor().getBlue(), graphicsLCD.getWidth()/2, graphicsLCD.getHeight()/2, GraphicsLCD.VCENTER|GraphicsLCD.HCENTER);
	    
		
		while(Button.readButtons() != Button.ID_ESCAPE){
						
			float value = colorAdapter.getColor().getBlue();
			graphicsLCD.clear();
			graphicsLCD.drawString("0 " + colorAdapter.getColor().getBlue(), graphicsLCD.getWidth()/2, graphicsLCD.getHeight()/2, GraphicsLCD.VCENTER|GraphicsLCD.HCENTER);

			leftMotor.setSpeed(500);
			rightMotor.setSpeed(500);
			leftMotor.setAcceleration(6000);
			rightMotor.setAcceleration(6000);
			
			//white
			if (value > threshold){
				while(colorAdapter.getColor().getBlue() >= threshold){
					// right
					leftMotor.rotate(rotateSpeed, true);
					rightMotor.rotate(-rotateSpeed);
					graphicsLCD.clear();
					graphicsLCD.drawString("1 " + colorAdapter.getColor().getBlue(), graphicsLCD.getWidth()/2, graphicsLCD.getHeight()/2, GraphicsLCD.VCENTER|GraphicsLCD.HCENTER);
				}
				
			}
			
			//black
			else{

				while(colorAdapter.getColor().getBlue() <= threshold){
					// left
					leftMotor.rotate(-rotateSpeed, true);
					rightMotor.rotate(	rotateSpeed);
					graphicsLCD.clear();
					graphicsLCD.drawString("2 " + colorAdapter.getColor().getBlue(), graphicsLCD.getWidth()/2, graphicsLCD.getHeight()/2, GraphicsLCD.VCENTER|GraphicsLCD.HCENTER);

				}

				
			}
						
			graphicsLCD.clear();
			graphicsLCD.drawString("3 " + colorAdapter.getColor().getBlue(), graphicsLCD.getWidth()/2, graphicsLCD.getHeight()/2, GraphicsLCD.VCENTER|GraphicsLCD.HCENTER);
			
			leftMotor.rotate(20, true);
			rightMotor.rotate(20);			
			Delay.msDelay(50);
		}
		
	}
}
