package game.arenas;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import utilities.Point;

public abstract class Arena implements Observer {

	private final static int MIN_Y_GAP = 80;
	private ArrayList<Racer> activeRacers ;
	private ArrayList<Racer> completedRacers;
	private ArrayList<Racer> brokenRacers;
	private ArrayList<Racer> disabledRacers;

	private double length;
	private final int MAX_RACERS;
	private final double FRICTION;

	/**
	 * 
	 * @param length
	 *            the x value for the finish line
	 * @param maxRacers
	 *            Maximum number of racers
	 * @param friction
	 *            Coefficient of friction
	 * 
	 */
	protected Arena(double length, int maxRacers, double friction) {
		this.length = length;
		this.MAX_RACERS = maxRacers;
		this.FRICTION = friction;
		this.activeRacers = new ArrayList<Racer>();
		this.completedRacers = new ArrayList<Racer>();
		this.brokenRacers=new ArrayList<>();
		this.disabledRacers= new ArrayList<>();
	}

	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if (this.activeRacers.size() == this.MAX_RACERS) {
			throw new RacerLimitException(this.MAX_RACERS, newRacer.getSerialNumber());
		}
		this.activeRacers.add(newRacer);
	}

	@Override
	public synchronized void update(Observable o, Object arg) {
		String condition = arg.toString();
		switch (condition) {
		case "BROKENDOWN":
			this.brokenRacers.add((Racer) o);
			break;
		case "REPAIRED":
			this.brokenRacers.remove((Racer) o);
			break;
		case "DISABLED":
			this.disabledRacers.add((Racer) o);
			this.activeRacers.remove((Racer) o);
			break;
		case "FINISHED":
			this.completedRacers.add((Racer) o);
			this.activeRacers.remove((Racer) o);
			break;
		default:
			break;
		}
	}

	public ArrayList<Racer> getActiveRacers() {
		return activeRacers;
	}

	public ArrayList<Racer> getCompletedRacers() {
		return completedRacers;
	}

	public ArrayList<Racer> getDisabledRacers() {
		return disabledRacers;
	}

	public ArrayList<Racer> getBrokenRacers() {
		return brokenRacers;
	}

	public boolean hasActiveRacers() {
		return this.activeRacers.size() > 0;
	}

	public double getFriction() {
		return this.FRICTION;
	}
	public void initRace() {
		int y = 0;
		for (Racer racer : this.activeRacers) {
			Point s = new Point(0, y);
			Point f = new Point(this.length, y);
			racer.initRace(this, s, f);
			y += Arena.getMinYGap();
		}
	}

	public synchronized void startRace() {
		ExecutorService ex=Executors.newFixedThreadPool(this.activeRacers.size());
		for(Racer racer:this.activeRacers) {
			ex.execute(racer);
			if(this.completedRacers.contains(racer) && this.disabledRacers.contains(racer)) {
				ex.shutdown();
			}
		}
	}

	/**
	 * @return the minYGap
	 */
	public static int getMinYGap() {
		return MIN_Y_GAP;
	}
}
