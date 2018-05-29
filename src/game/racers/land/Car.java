/**
 * 
 */
package game.racers.land;

import game.racers.Racer;
import game.racers.wheeled.Wheeled;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.EngineType;

/**
 * A class that describes a car. Inherits from the Racer class.
 * 
 * @author Michael Afonin, 310514997
 * @see Racer
 */
public class Car extends Racer implements LandRacer {

	private Wheeled wheels;
	private EngineType engine;
	
	/**
	 * Default constructor.
	 * Sets the name of the car as "Car #X" where X is the current racer's serial number.
	 * Also default speed is 400, default acceleration is 20 and default color is red.
	 */
	public Car() {
		super("Car #" + Racer.getStaticSN(), 400, 20, Color.Red);
		this.setWheels(4);
		this.setEngine(EngineType.FOURSTROKE);
	}
	
	/**
	 * Custom constructor.
	 * Sets a custom name of the car.
	 * Also speed, acceleration and color are all custom.
	 */
	public Car(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
		this.setWheels(4);
		this.setEngine(EngineType.FOURSTROKE);
	}

	/**
	 * @return String that describes the amount of wheels
	 */
	public String getWheels() {
		return "Number of Wheels: " + String.valueOf(wheels.getNumOfWheels());
	}

	/**
	 * @param the wheels to set
	 */
	public void setWheels(int numOfWheels) {
		this.wheels = new Wheeled(numOfWheels);
	}

	/**
	 * @return the engine type
	 */
	public String getEngine() {
		return "Engine Type: " + String.valueOf(engine);
	}

	/**
	 * @param the engine type to set
	 */
	public void setEngine(EngineType engine) {
		this.engine = engine;
	}

}
