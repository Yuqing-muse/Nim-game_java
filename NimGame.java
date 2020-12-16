/** 
 * name: Yuqing Chang
 * student number: 1044862
 * username: yuqchang
 */
public class NimGame {

	// Declaring variables; guarantee data privacy
	private int stoneInitial, stoneUpper, stoneLeft;
	private int min;
	// Array to store current NimPlayer objects
	private NimPlayer[] playerGame = new NimPlayer[2];

	/**
	 * Constructor of NimGame class
	 */
	public NimGame(int initialstones, int upperbound, NimPlayer player1, NimPlayer player2) {
		this.stoneInitial = initialstones;
		this.stoneLeft = initialstones;
		this.stoneUpper = upperbound;
		playerGame[0] = player1;
		playerGame[1] = player2;
	}
  
	/**
	 * Get the array of players
	 */
	public NimPlayer[] getPlayer() {
		return this.playerGame;
	}

	/**
	 * Get the the number of remaining stones
	 */
	public int getStoneLeft() {
		return this.stoneLeft;
	}
	
	/**
	 * Get the the number of initial stones
	 */
	public int getStoneInitial() {
		return this.stoneInitial;
	}

	/**
	 * Get the the max number of removing stones
	 */
	public int getStoneUpper() {
		return this.stoneUpper;
	}

	public int getMin() {
		return this.min;
	}
	

	/**
	 * Begain a game of Nim
	 */
	public void games() {
		// Declaring variables
		int remove = 0;
		boolean again = true;
		String winner = ""; // Store the name of the winner

		printStones(stoneLeft);

		/*
		 * If stone_left is 0, then the game is over and announce the winner
		 */
		while (again) {
			for (int i = 0; i < 2; i++) {
				boolean incorrect = true;
				while (incorrect == true) {
					remove = playerGame[i].removeStone(this);
					min = Math.min(stoneLeft, this.stoneUpper);
					// Check if the number of stones removed is valid
					try {
						if (remove > min || remove < 1) {
							System.out.println();
							throw new MyRTException();
						} else
							incorrect = false;
					} catch (MyRTException r) {
						String message = "Invalid move. You must remove between 1 and ";
						System.out.println(message + min + " stones.");
						printStones(stoneLeft);
					}
				}
				stoneLeft = stoneLeft - remove; // Update variable value
				if (stoneLeft == 0) {
					winner = playerGame[1 - i].getFullName();
					playerGame[1 - i].win();
					again = false;
					break;
				}
				printStones(stoneLeft);
			}
		}

		System.out.println();
		System.out.println("Game Over");
		System.out.println(winner + " wins!");

		/*
		 * Update the statistics for the two players
		 */
		playerGame[0].played();
		playerGame[1].played();
		playerGame[0].winRatio();
		playerGame[1].winRatio();
	}

	/**
	 * Print the number of stones, and displaying stones, which will be represented
	 * by asterisks
	 */
	public void printStones(int stone_left) {
		System.out.println();
		String str = "";
		for (int i = 1; i <= stone_left; i++)
			str = str + " " + "*";
		System.out.println(stone_left + " stones left:" + str);
	}

}
