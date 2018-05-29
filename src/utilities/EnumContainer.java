package utilities;

/**
 * Contains all Enums for the game.
 * 
 * To set a field type: 
 * 		EnumContainer.Vision vision;
 * To set a value:
 * 		this.vision = EnumContainer.Vision.Sunny
 * 
 * @author Bar Ohayon
 *
 */
public class EnumContainer {
	//For Aerial Arena:
	public static enum Vision {
		CLOUDS, SUNNY, FOG
	}
	
	public static enum Weather {
		DRY, RAIN, SNOW
	}
	
	public static enum Height {
		LOW, MEDIUM, HIGH
	}
	
	public static enum Wind {
		LOW, MEDIUM, HIGH
	}
	
	//For Naval Arena:
	public static enum Water {
		SALTED, SWEET
	}
	
	public static enum WaterSurface {
		FLAT, WAVY
	}
	
	public static enum Body {
		SEA, LAKE, RIVER, OCEAN
	}
	
	//For Land Arena:
	public static enum Coverage {
		SAND, GRASS, MUD
	}
	
	public static enum GroundSurface {
		FLAT, MOUNTAIN
	}
	
	//Player color:
	public static enum Color {
		Red, Green, Blue, Black, Yellow
	}
	
	//For car engine type:
	public static enum EngineType {
		FOURSTROKE, VTYPE, STRAIGHT, BOXER, ROTARY
	}
	
	//Bicycle type:
	public static enum BikeType {
		MOUNTAIN, HYBRID, CRUISER, ROAD
	}
	
	//Horse breed:
	public static enum Breed {
		THOROUGHBRED, STANDARDBRED, MORGAN, FRIESIAN
	}
	
	//Boat type and team:
	public static enum BoatType {
		SKULLING, SWEEP
	}
	
	public static enum Team {
		SINGLE, DOUBLE, QUAD, EIGHT
	}
	
	//Racer condition after breakdown:
	public static enum RacerEvent {
		BROKENDOWN, REPAIRED, DISABLED, FINISHED
	}

}
