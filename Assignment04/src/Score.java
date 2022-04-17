/**
 * @author Belent Patrus Student Number: 041015657 Due Date: 2022-02-20
 *         Description: a class to make comparing highscore easier Professor
 *         Name: Daniel Comier Assessment: Assignment 4
 *
 */
public class Score implements Comparable<Score> {

	/**
	 * stores the number of points acquired
	 */
	private int points;
	/**
	 * stores the time elapsed
	 */
	private int time;
	/**
	 * name of client
	 */
	private String name;

	/**
	 * Constructor for Score
	 * 
	 * @param points amount of points acquired
	 * @param time   the time elapsed
	 * @param name   of client
	 */
	public Score(int points, int time, String name) {

		this.points = points;
		this.time = time;
		this.name = name;
	}

	/**
	 * getter for points field
	 * 
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * getter for time field
	 * 
	 * @return time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * getter for name field
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The way Score gets compared is points weighed greater then time and time is
	 * tie breaker.
	 *
	 */
	@Override
	public int compareTo(Score score) {
		// TODO Auto-generated method stub
		if (points == score.points) {
			return Integer.compare(score.time, time);
		}
		return Integer.compare(points, score.points);
	}

}
