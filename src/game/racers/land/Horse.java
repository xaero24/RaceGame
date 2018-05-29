/**
 * 
 */
package game.racers.land;

import game.racers.Racer;
import utilities.EnumContainer.Breed;
import utilities.EnumContainer.Color;

/**
 * A class that describes a horse. Inherits from the Racer class.
 * 
 * @author Michael Afonin, 310514997
 * @see Racer
 */
public class Horse extends Racer implements LandRacer {
	
	private Breed breed;
	
	/**
	 * Default constructor.
	 * Sets the name of the horse as "Horse #X" where X is the current racer's serial number.
	 * Also default speed is 50, default acceleration is 3 and default color is black.
	 */
	public Horse() {
		super("Horse #" + Racer.getStaticSN(), 50, 3, Color.Black);
		this.setBreed(Breed.THOROUGHBRED);
	}
	
	/**
	 * Custom constructor.
	 * Sets a custom name of the horse.
	 * Also speed, acceleration and color are all custom.
	 */
	public Horse(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
		this.setBreed(Breed.THOROUGHBRED);
	}

	/**
	 * @return the breed
	 */
	public String getBreed() {
		return "Breed: " + String.valueOf(breed);
	}

	/**
	 * @param the breed to set
	 */
	public void setBreed(Breed breed) {
		this.breed = breed;
	}
}
