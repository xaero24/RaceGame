/**
 * 
 */
package game.racers.air;

import game.racers.Racer;
import utilities.EnumContainer.Color;

/**
 * A class that describes a helicopter. Inherits from the Racer class.
 * 
 * @author Michael Afonin, 310514997
 * @see Racer
 */
public class Helicopter extends Racer implements AerialRacer {
	
	/**
	 * Default constructor.
	 * Sets the name of the helicopter as "Helicopter #X" where X is the current racer's serial number.
	 * Also default speed is 400, default acceleration is 50 and default color is blue.
	 */
	public Helicopter() {
		super("Helicopter #" + Racer.getStaticSN(), 400, 50, Color.Blue);
	}
	
	/**
	 * Custom constructor.
	 * Sets a custom name of the helicopter.
	 * Also speed, acceleration and color are all custom.
	 */
	public Helicopter(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
	}
}
