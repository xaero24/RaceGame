/**
 * 
 */
package game.racers.land;

import game.racers.Racer;
import game.racers.wheeled.Wheeled;
import utilities.EnumContainer.BikeType;
import utilities.EnumContainer.Color;

/**
 * A class that describes a bicycle. Inherits from the Racer class.
 * 
 * @author Michael Afonin, 310514997
 * @see Racer
 */
public class Bicycle extends Racer implements LandRacer {

	private Wheeled wheels;
	private BikeType type;
	
	/**
	 * Default constructor.
	 * Sets the name of the bicycle as "Bicycle #X" where X is the current racer's serial number.
	 * Also default speed is 270, default acceleration is 10 and default color is green.
	 */
	public Bicycle() {
		super("Bicycle #" + Racer.getStaticSN(), 270, 10, Color.Green);
		this.setWheels(2);
		this.setType(BikeType.MOUNTAIN);
	}
	
	/**
	 * Custom constructor.
	 * Sets a custom name of the bicycle.
	 * Also speed, acceleration and color are all custom.
	 */
	public Bicycle(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
		this.setWheels(2);
		this.setType(BikeType.MOUNTAIN);
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
	 * @return the bicycle type
	 */
	public String getType() {
		return "Bicycle Type: " + String.valueOf(type);
	}

	/**
	 * @param the bicycle type to set
	 */
	public void setType(BikeType type) {
		this.type = type;
	}
}
