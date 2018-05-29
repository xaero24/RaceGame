/**
 * 
 */
package game.racers.naval;

import game.racers.Racer;
import utilities.EnumContainer.BoatType;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.Team;

/**
 * A class that describes a row boat. Inherits from the Racer class.
 * 
 * @author Michael Afonin, 310514997
 * @see Racer
 */
public class RowBoat extends Racer implements NavalRacer {

	private BoatType type;
	private Team team;
	
	/**
	 * Default constructor.
	 * Sets the name of the row boat as "RowBoat #X" where X is the current racer's serial number.
	 * Also default speed is 75, default acceleration is 10 and default color is red.
	 */
	public RowBoat() {
		super("RowBoat #" + Racer.getStaticSN(), 75, 10, Color.Red);
		this.setType(BoatType.SKULLING);
		this.setTeam(Team.DOUBLE);
	}
	
	/**
	 * Custom constructor.
	 * Sets a custom name of the row boat.
	 * Also speed, acceleration and color are all custom.
	 */
	public RowBoat(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
		this.setType(BoatType.SKULLING);
		this.setTeam(Team.DOUBLE);
	}

	/**
	 * @return the row boat type
	 */
	public String getType() {
		return "Type: " + String.valueOf(type);
	}

	/**
	 * @param the row boat type to set
	 */
	public void setType(BoatType type) {
		this.type = type;
	}

	/**
	 * @return the row boat team
	 */
	public String getTeam() {
		return "Team: " + String.valueOf(team);
	}

	/**
	 * @param the row boat team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
}
