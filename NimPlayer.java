/** 
 * name: Yuqing Chang
 * student number: 1044862
 * username: yuqchang
 */

import java.io.BufferedWriter;
import java.io.*;

public abstract class NimPlayer {

	// Declaring variables; guarantee data privacy
	protected String userName = "";
	protected String givenName = "";
	protected String familyName = "";
	protected double gamesplayed, gamesWon;
	protected int removeNum; // The number of stones removed
	protected double winRatio; // Winning ratio

	/**
	 * Constructor of NimPlayer class; Initialize all variables
	 */
	public NimPlayer(String username, String family_name, String given_name) {
		this.userName = username;
		this.givenName = given_name;
		this.familyName = family_name;
		this.gamesWon = 0.0;
		this.gamesplayed = 0.0;
		this.winRatio = 0;
	}

	/**
	 * Get userName of a NimPlayer object
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Get family name of a NimPlayer object
	 */
	public String getFamilyName() {
		return this.familyName;
	}

	/**
	 * Get full name of a NimPlayer object
	 */
	public String getGivenName() {
		return this.givenName;
	}
	
	/**
	 * Set the number of games that a NimPlayer object has played
	 */
	public void setGamesPlayed(double games_played) {
		this.gamesplayed = games_played;
	}

	/**
	 * Set the number of games that a NimPlayer object has won
	 */
	public void setGamesWon(double games_won) {
		this.gamesWon = games_won;
	}

	/**
	 * Get winning ratio of a NimPlayer object
	 */
	public double getWinRatio() {
		return this.winRatio;
	}

	/**
	 * Set winning ratio of a NimPlayer object
	 */
	public void setWinRatio(double m) {
		this.winRatio = m;
	}

	/**
	 * Set given_name and family_name of a NimPlayer object
	 */
	public void setName(String new_family_name, String new_given_name) {
		this.givenName = new_given_name;
		this.familyName = new_family_name;
	}

	/**
	 * Get full name of a NimPlayer object
	 */
	public String getFullName() {
		return this.givenName + " " + this.familyName;
	}

	/**
	 * Update the number of games won
	 */
	public void win() {
		this.gamesWon = this.gamesWon + 1;
	}

	/**
	 * Get the number of games won of a NimPlayer object
	 */
	public int getWin() {
		return (int) this.gamesWon;
	}
	
	/**
	 * Get the number of games won of a NimPlayer object
	 */
	public int getPlayed() {
		return (int) this.gamesplayed;
	}

	/**
	 * Reseting a player's statistics
	 */
	public void reset() {
		this.gamesplayed = 0;
		this.gamesWon = 0;
		this.winRatio = 0.0;
	}

	/**
	 * Update the number of games played
	 */
	public void played() {
		gamesplayed = gamesplayed + 1;
	}

	/**
	 * Display information of a NimPlayer object
	 */
	public void display() {
		System.out.println(this.userName + "," + this.givenName + "," + this.familyName + ","
				+ (int) this.gamesplayed + " games," + (int) this.gamesWon + " wins");
	}

	/**
	 * Update winning ratio of a NimPlayer object
	 */
	public void winRatio() {
		if (this.gamesplayed != 0) {
			this.winRatio = this.gamesWon / this.gamesplayed;
		} else
			winRatio = 0;
	}

	/**
	 * Convert the number of games played to string
	 */
	public String convertGamesNum() {
		String num = "";
		int play = (int) gamesplayed;
		if (play / 10 == 0) {
			num = "0" + play;
			return num;
		} else
			return num + play;
	}
	
	/**
	 * Ask and return the number of stones the player wants to remove
	 */
	public abstract int removeStone(NimGame game);
		
	/**
	 * Get the object type of the current object
	 */
	public String getType(Object object) {
		String typeName = object.getClass().getName();
		int length = typeName.lastIndexOf(".");
		String type = typeName.substring(length + 1);
		return type;
	}

}
