/**
 * 
 */
package game.racers.wheeled;

/**
 * The class is used for wheeled racers to keep their wheel amount.
 * 
 * @author Michael Afonin, 310514997
 *
 */
public class Wheeled {
	
	private int numOfWheels;
	
	/**
	 * Constructor that sets the default number of wheels - 0 wheels.
	 */
	public Wheeled() {
		this.setNumOfWheels(0);
	}
	
	/**
	 * Constructor that sets a custom number of wheels.
	 * 
	 * @param numOfWheels
	 */
	public Wheeled(int numOfWheels) {
		this.setNumOfWheels(numOfWheels);
	}
	
	/**
	 * @return the numOfWheels
	 */
	public int getNumOfWheels() {
		return numOfWheels;
	}

	/**
	 * @param the numOfWheels to set
	 */
	public void setNumOfWheels(int numOfWheels) {
		this.numOfWheels = numOfWheels;
	}
}
