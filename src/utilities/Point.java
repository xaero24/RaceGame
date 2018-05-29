/**
 * 
 */
package utilities;

/**
 * Point class, containing X and Y values of a point.
 * 
 * @author Michael Afonin, 310514997
 *
 */
public class Point {
	private double x;
	private double y;
	public static final int MIN_X = 0;
	public static final int MAX_X = 1000000;
	public static final int MIN_Y = 0;
	public static final int MAX_Y = 800;
	
	/**
	 * Defauld constructor for a point.
	 * Sets a (0,0) point.
	 */
	public Point() {
		this(0, 0);
	}
	
	/**
	 * 2-parameter constructor for a point.
	 * Values must be 0<=x<=10000000 and 0<=y<=800.
	 * 
	 * @param x - double value
	 * @param y - double value
	 */
	public Point(double x, double y) {
		if (!(this.setX(x))) {
			this.x = 0;
		}
		if (!(this.setY(y))) {
			this.y = 0;
		}
	}
	
	/**
	 * Copy constructor for a point, implements deep copy.
	 * 
	 * @param other - Point type value
	 */
	public Point(Point other) {
		if (other == null) {
			other = new Point(0, 0);
		}
		this.setX(other.x);
		this.setY(other.y);
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param the x to set, if in valid boundaries.
	 */
	public boolean setX(double x) {
		if (y > MAX_X || y < MIN_X) {
			return false;
		}
		this.x = x;
		return true;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param the y to set, if in valid boundaries.
	 */
	public boolean setY(double y) {
		if (y > MAX_Y || y < MIN_Y) {
			return false;
		}
		this.y = y;
		return true;
	}

	/**
	 * @return the minX
	 */
	public static int getMinX() {
		return MIN_X;
	}

	/**
	 * @return the maxX
	 */
	public static int getMaxX() {
		return MAX_X;
	}

	/**
	 * @return the minY
	 */
	public static int getMinY() {
		return MIN_Y;
	}

	/**
	 * @return the maxY
	 */
	public static int getMaxY() {
		return MAX_Y;
	}
	
	/**
	 * Overrider method for the default toString method.
	 * Returns a string that contains the x and y coordinates a sfollows:
	 * (x,y)
	 */
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

}
