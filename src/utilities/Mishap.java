/**
 * 
 */
package utilities;

import java.text.DecimalFormat;

/**
 * A class that describes a mishap that may happen to a racer.
 * 
 * @author Michael Afonin, 310514997
 *
 */
public class Mishap {
	private boolean fixable;
	private double reductionFactor;
	private int turnsToFix;
	
	/**
	 * The default constructor for a mishap. generates a mishap according to the value it gets from Fate class.
	 * 
	 * @param fixable
	 * @param turnsToFix
	 * @param reductionFactor
	 * @see Fate
	 */
	public Mishap(boolean fixable, int turnsToFix, double reductionFactor) {
		this.setFixable(fixable);
		this.setTurnsToFix(turnsToFix);
		this.setReductionFactor(reductionFactor);
	}
	
	/**
	 * If the mishap is fixable, the amount of turns until it is fixed is reduced here.
	 */
	public void nextTurn() {
		if(fixable) this.setTurnsToFix(this.getTurnsToFix() - 1);
	}
	
	// SETTERS AND GETTERS
	
	/**
	 * @return the fixable boolean value
	 */
	public boolean getFixable() {
		return fixable;
	}

	/**
	 * @param the fixable boolean value to set
	 */
	public void setFixable(boolean fixable) {
		this.fixable = fixable;
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

	/**
	 * @return the turnsToFix
	 */
	public int getTurnsToFix() {
		return turnsToFix;
	}

	/**
	 * @param the turnsToFix to set
	 */
	public void setTurnsToFix(int turnsToFix) {
		this.turnsToFix = turnsToFix;
	}
	
	/**
	 * Overrides the original toString method.
	 * Generates a string with the following format and information:
	 * (isFixable, turnsToFix, reductionFactor)
	 * 
	 * @return String that describes the mishap
	 */
	public String toString() {
		return "(" + String.valueOf(fixable) + ", " + String.valueOf(turnsToFix) + ", " + (new DecimalFormat("0.00")).format(reductionFactor) + ")";
	}
}
