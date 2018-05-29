/**
 * 
 */
package factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer.Color;

/**
 * The main race building class.
 * Utilizes singleton DP for the race builder class - only one instance of the game exists.
 * Utilizes dynamic class loading for real-time control over instance creation.
 * 
 * 
 * @author Michael Afonin, 310514997
 *
 */
public class RaceBuilder {
	private static RaceBuilder instance = null;
	private Class<?> cls = null;
	private ClassLoader CL = ClassLoader.getSystemClassLoader();
	Arena arena = null;
	Racer racer = null;
	
	/**
	 * Static method that creates a new race builder instance if none exists.
	 * 
	 * @return the race builder instance
	 */
	public static RaceBuilder getInstance() {
		if (instance == null) {
			instance = new RaceBuilder();
		}
		return instance;
	}
	
	/**
	 * Empty constructor to override the default one.
	 */
	private RaceBuilder() {}
	
	/**
	 * Arena builder that takes the needed arena parameters and utilises reflection to create the arena in real time.
	 * Calls a class loader with the FULL package name of the arena;
	 * Gets the relevant constructor for the arena;
	 * Creates a new arena instance with the given paeameters.
	 * 
	 * @param arenaType
	 * @param length
	 * @param maxRacers
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Arena buildArena(String arenaType, double length, int maxRacers) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		cls=CL.loadClass(arenaType);
		Constructor<?> ctor = cls.getConstructor(double.class, int.class);
		arena = (Arena) ctor.newInstance(length, maxRacers);
		return arena;
	}
	
	/**
	 * Racer builder.
	 * Takes a regular racer's parameters and creates a custom racer using reflection.
	 * The builder passes the exceptions from class loader, c-tor getter and instance creator and passes them on.
	 * This constructor is meant for wheel-less racers.
	 * 
	 * @param racerType
	 * @param name
	 * @param maxSpeed
	 * @param acceleration
	 * @param color
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Racer buildRacer(String racerType, String name, double maxSpeed, double acceleration, Color color) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		cls = CL.loadClass(racerType);
		Constructor<?> ctor = cls.getConstructor(String.class, double.class, double.class, Color.class);
		racer = (Racer) ctor.newInstance(name, maxSpeed, acceleration, color);
		return racer;
	}
}
