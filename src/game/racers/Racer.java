/**
 * 
 */
package game.racers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

import game.arenas.Arena;
import utilities.EnumContainer;
import utilities.Fate;
import utilities.Mishap;
import utilities.Point;

/**
 * This abstract class represent a general racer.
 * It contains all the most important methods for a racer, like initialization and movement.
 * 
 * @author Michael Afonin, 310514997
 *
 */
public abstract class Racer extends Observable implements Runnable {
	private static int StaticSN = 1;
	private int serialNumber;
	private String name;
	private Point currentLocation;
	private Point finish;
	private Arena arena;
	private double maxSpeed;
	private double acceleration;
	private double currentSpeed;
	private double failureProbability;
	private EnumContainer.Color color;
	private Mishap mishap;
	private double reductionFactor;

	/**
	 * A constructor for the racer. Gets the racer's name, maximum speed, acceleration and color.
	 * 
	 * @param name
	 * @param maxSpeed
	 * @param acceleration
	 * @param color
	 */
	public Racer (String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
		this.setName(name);
		this.setSerialNumber(Racer.getStaticSN());
		this.setCurrentSpeed(0);
		this.setMaxSpeed(maxSpeed);
		this.setAcceleration(acceleration);
		this.setColor(color);
		this.setMishap(null);
		this.setReductionFactor(acceleration);
		this.setFailureProbability(0.05);
		Racer.setStaticSN(Racer.getStaticSN() + 1);
	}

	/**
	 * Race initiator.
	 * Puts the racer in his initial position (with a vertical gap between racers).
	 * Also sets the racer's arena and finish line.
	 * Called by the initRace method in Arena class.
	 * 
	 * @param arena
	 * @param start
	 * @param finish
	 * @see Arena
	 */
	public void initRace (Arena arena, Point start, Point finish) {
		this.setArena(arena);
		this.setCurrentLocation(start);
		this.setFinish(finish);
	}

	/**
	 * The method does the following actions for each movement of the racer:
	 * 1. Draws a mishap.
	 * 2. If a mishap is bound to happen - generates a new mishap.
	 * 3. The new mishap's fixability, steps to fix and deceleration factor are taken in account for the racer during the next steps of the race.
	 * 
	 * Then the racer is slowed down (if the mishap exists) and his new speed is carried through all his next moves until the mishap is fixed (if fixable).
	 * 
	 * @param friction
	 * @return racer's current location.
	 * @see Mishap
	 * @see Fate
	 */
	public Point move(double friction) {
		//Mishap drawing and generation.
		if (this.getMishap() == null) { //If there's no mishap, draw one.
			if (Fate.breakDown(failureProbability)) {
				//If needed, generates a mishap and prints a message for the current player and for the current mishap.
				this.setMishap(Fate.generateMishap());
				this.setChanged();
				this.notifyObservers(EnumContainer.RacerEvent.BROKENDOWN);
				this.setReductionFactor(this.getAcceleration() * this.getMishap().getReductionFactor()); //Used as a means to customise acceleration.
			}
			else { //If no mishap is to be generated, the values are the default ones.
				this.setMishap(null);
				this.setReductionFactor(acceleration);
			}
		}

		//For an existing mishap - reduce the turns to fix. If fixed - remove mishap.
		if (this.getMishap() != null) {
			if (!getMishap().getFixable()) {
				this.setChanged();
				this.notifyObservers(EnumContainer.RacerEvent.DISABLED);
			} else {
				this.getMishap().nextTurn();
				if (this.getMishap().getTurnsToFix() == 0) {
					this.setMishap(null);
					this.setChanged();
					this.notifyObservers(EnumContainer.RacerEvent.REPAIRED);
					this.setReductionFactor(acceleration);
				}
			}
		}
		if(this.currentLocation.getX()<this.getFinish().getX() ) {
		//Set the current racer's new speed using the current speed, reduction factor and friction.
		this.setCurrentSpeed(this.getCurrentSpeed() + (friction * this.getReductionFactor()));
		//Set racer's location based on the current speed.
		this.getCurrentLocation().setX(this.getCurrentLocation().getX() + this.getCurrentSpeed());
		}
		//If finish line is crossed - call the arena's crossFinishLine method to remove the racer from the active racers array
		if (this.getCurrentLocation().getX() >= this.getFinish().getX()) {
			setCurrentLocation(new Point(getFinish().getX(), this.getCurrentLocation().getY()));
			this.setChanged();
			this.notifyObservers(EnumContainer.RacerEvent.FINISHED);
		}

		return getCurrentLocation();
	}

	@Override
	public synchronized void run() {
		while(!arena.getCompletedRacers().contains(this) &&!arena.getDisabledRacers().contains(this)) {
		this.move(arena.getFriction());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().notify();
		}
		}


	}


	/**
	 * Gets and returns the current racer's class name (the racer's type, essentialy).
	 * @return String of the racer's class name.
	 */
	public String className() {
		return this.getClass().getSimpleName();
	}

	//GETTERS AND SETTERS

	/**
	 * @return the serialNumber
	 */
	public static int getStaticSN() {
		return StaticSN;
	}

	/**
	 * @param the serialNumber to set
	 */
	public static void setStaticSN(int StaticSN) {
		Racer.StaticSN = StaticSN;
	}

	/**
	 * @return the currentSN
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param the currentSN to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the currentLocation
	 */
	public Point getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param the currentLocation to set
	 */
	public void setCurrentLocation(Point currentLocation) {
		this.currentLocation = new Point(currentLocation);
	}

	/**
	 * @return the finish
	 */
	public Point getFinish() {
		return finish;
	}

	/**
	 * @param the finish to set
	 */
	public void setFinish(Point finish) {
		this.finish = new Point(finish);
	}

	/**
	 * @return the arena
	 */
	public Arena getArena() {
		return arena;
	}

	/**
	 * @param the arena to set
	 */
	public void setArena(Arena arena) {
		this.arena = arena;
	}

	/**
	 * @return the maxSpeed
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param the maxSpeed to set
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @return the acceleration
	 */
	public double getAcceleration() {
		return acceleration;
	}

	/**
	 * @param the acceleration to set
	 */
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * @return the currentSpeed
	 */
	public double getCurrentSpeed() {
		return currentSpeed;
	}

	/**
	 * @param the currentSpeed to set
	 */
	public void setCurrentSpeed(double currentSpeed) {
		if (this.currentSpeed < this.maxSpeed)
			this.currentSpeed = currentSpeed;
		else
			this.currentSpeed = this.maxSpeed;

	}

	/**
	 * @return the failureProbability
	 */
	public double getFailureProbability() {
		return failureProbability;
	}

	/**
	 * @param the failureProbability to set
	 */
	public void setFailureProbability(double failureProbability) {
		this.failureProbability = failureProbability;
	}

	/**
	 * @return the color
	 */
	public EnumContainer.Color getColor() {
		return color;
	}

	/**
	 * @param the color to set
	 */
	public void setColor(EnumContainer.Color color) {
		this.color = color;
	}

	/**
	 * @return the mishap
	 */
	public Mishap getMishap() {
		return mishap;
	}

	/**
	 * @param the mishap to set
	 */
	public void setMishap(Mishap mishap) {
		this.mishap = mishap;
	}

	/**
	 * @return the reductionFactor
	 */
	public double getReductionFactor() {
		return reductionFactor;
	}

	/**
	 * @param the reductionFactor to set
	 */
	public void setReductionFactor(double reductionFactor) {
		this.reductionFactor = reductionFactor;
	}
}
