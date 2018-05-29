/**
 * 
 */
package game.racers.naval;

import game.racers.Racer;
import utilities.EnumContainer.BoatType;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.Team;

/**
 * A class that describes a speed boat. Inherits from the Racer class.
 * 
 * @author Michael Afonin, 310514997
 * @see Racer
 */
public class SpeedBoat extends Racer implements NavalRacer {
	
	private BoatType type;
	private Team team;
	
	/**
	 * Default constructor.
	 * Sets the name of the speed boat as "SpeedBoat #X" where X is the current racer's serial number.
	 * Also default speed is 170, default acceleration is 5 and default color is red.
	 */
	public SpeedBoat() {
		super("SpeedBoat #" + Racer.getStaticSN(), 170, 5, Color.Red);
		this.setType(BoatType.SKULLING);
		this.setTeam(Team.DOUBLE);
	}
	
	/**
	 * Custom constructor.
	 * Sets a custom name of the speed boat.
	 * Also speed, acceleration and color are all custom.
	 */
	public SpeedBoat(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
		this.setType(BoatType.SKULLING);
		this.setTeam(Team.DOUBLE);
	}

	/**
	 * @return the speed boat type
	 */
	public String getType() {
		return "Type: " + String.valueOf(type);
	}

	/**
	 * @param the speed boat type to set
	 */
	public void setType(BoatType type) {
		this.type = type;
	}

	/**
	 * @return the speed boat team
	 */
	public String getTeam() {
		return "Team: " + String.valueOf(team);
	}

	/**
	 * @param the speed boat team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
}
