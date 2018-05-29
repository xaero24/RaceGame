package game.arenas.land;

import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.land.LandRacer;
import utilities.EnumContainer;
import utilities.EnumContainer.GroundSurface;

public class LandArena extends Arena {

	private final static double DEFAULT_FRICTION = 0.5;
	private final static int DEFAULT_MAX_RACERS = 8;
	private final static int DEFAULT_LENGTH = 800;

	EnumContainer.Coverage coverage;
	GroundSurface surface;

	public LandArena() {
		this(DEFAULT_LENGTH, DEFAULT_MAX_RACERS);
	}

	public LandArena(double length, int maxRacers) {
		super(length, maxRacers, DEFAULT_FRICTION);
		this.coverage = EnumContainer.Coverage.GRASS;
		this.surface = EnumContainer.GroundSurface.FLAT;
	}

	@Override
	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if (newRacer instanceof LandRacer) {
			super.addRacer(newRacer);
		} else {
			throw new RacerTypeException(newRacer.className(), "Land");
		}
	}
}
