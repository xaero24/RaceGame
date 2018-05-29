/**
 * 
 */
package utilities;

import java.util.Random;

/**
 * Decides the fate of the racer.
 * A random seed is planted in the beginning of the program and it's used to generate pseudo-random numbers.
 * These are used to decide whether a player will or will not have a mishap in the future.
 * 
 * @author Michael Afonin, 310514997
 *
 */
public class Fate {

	private static Random rand = new Random();
	
	/**
	 * Gets a seed and sets it as a class seed for future use.
	 * 
	 * @param seed
	 */
	public static void setSeed(int seed) {
		rand.setSeed(seed);
	}
	
	/**
	 * Uses the seed to determine whether or not the mishap is fixable.
	 * 
	 * @return Boolean result for the fixability of the mishap.
	 */
	public static boolean generateFixable() {
		return rand.nextInt(10) > 7;
	}
	
	/**
	 * Generates a reduction factor for the racer's acceleration value.
	 * 
	 * @return a float of the acceleration reduction
	 */
	private static float generateReduction() {
		return rand.nextFloat();
	}
	
	/**
	 * Generates the amount of turns that are left until the mishap is fixed.
	 * 
	 * @return integer value of the turns until fixed
	 */
	private static int generateTurns() {
		return rand.nextInt(5) + 1;
	}
	
	/**
	 * Decides whether the mishap will or will not occur.
	 * 
	 * @return boolean value for the mishap generation
	 */
	public static boolean breakDown(double failureProbability) {
		return rand.nextFloat() <= failureProbability;
		//return rand.nextBoolean();
	}
	
	/**
	 * Mishap generator method.
	 * Calls the Mishap class constructor with the generated random values and generates a new mishap.
	 * 
	 * @return the generated mishap.
	 * @see Mishap
	 */
	public static Mishap generateMishap() {
			return new Mishap(generateFixable(), generateTurns(), generateReduction());
	}

}
