/**
 * 
 */
package game.racers.air;

import game.racers.Racer;
import game.racers.wheeled.Wheeled;
import utilities.EnumContainer.Color;

/**
 * A class that describes an airplane. Inherits from the Racer class.
 * 
 * @author Michael Afonin, 310514997
 * @see Racer
 */
public class Airplane extends Racer implements AerialRacer {

	private Wheeled wheels;
	
	/**
	 * Default constructor.
	 * Sets the name of the airplane as "Airplane #X" where X is the current racer's serial number.
	 * Also default speed is 885, default acceleration is 100 and default color is black.
	 */
	public Airplane() {
		super("Airplane #" + Racer.getStaticSN(), 885, 100, Color.Black);
		this.setWheels(3);
	}
	
	/**
	 * Custom constructor.
	 * Sets a custom name of the airplane.
	 * Also speed, acceleration and color are all custom.
	 */
	public Airplane(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
		this.setWheels(3);
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
}
