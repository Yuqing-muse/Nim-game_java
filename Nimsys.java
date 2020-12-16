
/** 
 * name: Yuqing Chang
 * student number: 1044862
 * username: yuqchang
 */
import java.util.Scanner;
import java.util.Arrays;
import java.io.*;
import java.util.*;

public class Nimsys {

	// Create a scanner object to get data from the keyboard
	public static Scanner scan = new Scanner(System.in);
	private static String url = "E:\\data_store\\eclipse_data\\ProjectA\\src\\";
	private static File fil = new File(url + "players.dat");
	private final int MAX = 100;
	private final int PERCENT = 100;
	private final int RANK = 10;

	// Array to store NimPlayer objects
	private static NimPlayer[] player = new NimPlayer[1];
	

	public static void main(String[] args) {
		Nimsys nimsys = new Nimsys();

		if (fil.exists()) {
			player = read_data(fil, player);
			// System.out.println("kk");
		} else {

			try {
				fil.createNewFile();
				FileWriter fileWriter = new FileWriter(url + "players.dat");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// System.out.println(player.length);
		// Display a welcome message
		System.out.println("Welcome to Nim");
		System.out.println();
		System.out.print("$");
		while (true) {
			String input = scan.nextLine();
			try {
				nimsys.command_control(input);
			} catch (MyException e) {
				if (e.getMessage().length() > 0) {
					System.out.println("'" + e.getMessage() + "' is not a valid command.");
					System.out.println();
					System.out.print("$");
				}
			} catch (Arguments a) {
				System.out.println("Incorrect number of arguments supplied to command.");
				System.out.println();
				System.out.print("$");
			}
		}
	}

	/**
	 * Command Control Center
	 */
	public void command_control(String input) throws MyException, Arguments {
		// Split input into commands and parameters
		String[] Str = input.split(" ");
		String command = Str[0];
		String[] arg;
		int c = 0;

		if (Str.length == 1)
			arg = null;
		else
			arg = Str[1].split(",");

		// Combine switch statement to distinguish input commands
		if (command.equals("addplayer"))
			c = 1;
		else if (command.equals("removeplayer"))
			c = 2;
		else if (command.equals("editplayer"))
			c = 3;
		else if (command.equals("resetstats"))
			c = 4;
		else if (command.equals("displayplayer"))
			c = 5;
		else if (command.equals("rankings"))
			c = 6;
		else if (command.equals("startgame"))
			c = 7;
		else if (command.equals("exit"))
			c = 8;
		else if (command.equals("addaiplayer"))
			c = 9;

		switch (c) {
		case 1:
			if (arg.length < 3) {
				throw new Arguments();
			}
			addPlayer(arg);
			break;
		case 2:
			removePlayer(arg);
			break;
		case 3:
			if (arg.length < 3) {
				throw new Arguments();
			}
			editPlayer(arg);
			break;
		case 4:
			resetStats(arg);
			break;
		case 5:
			displayPlayer(arg);
			break;
		case 6:
			rank(arg);
			break;
		case 7:
			if (arg.length < 4) {
				throw new Arguments();
			}
			startGame(arg);
			break;
		case 8:
			exit();
			break;
		case 9:
			if (arg.length < 3) {
				throw new Arguments();
			}
			addAIPlayer(arg);
			break;
		default:
			throw new MyException(command);
		}
	}

	/**
	 * Allows new players to be added to the game
	 */
	public void addPlayer(String[] Str) {
		// The maximum number of players can be set as 100
		if (player.length == MAX) {
			System.out.println("The maximum number of players has been reached!");
			return;
		}
		if (player[0] == null) {
			player[0] = new NimHumanPlayer(Str[0], Str[1], Str[2]);
		} else {
			// Check if the user already exists in the user list
			if (check(Str[0]) == false) {
				NimHumanPlayer p1 = new NimHumanPlayer(Str[0], Str[1], Str[2]);
				player = add(p1, player);
			} else
				System.out.println("The player already exists.");
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Allows new AIplayers to be added to the game
	 */
	public void addAIPlayer(String[] Str) {
		// The maximum number of players can be set as 100
		if (player.length == MAX) {
			System.out.println("The maximum number of players has been reached!");
			return;
		}
		if (player[0] == null) {
			player[0] = new NimAIPlayer(Str[0], Str[1], Str[2]);
		} else {
			// Check if the user already exists in the user list
			if (check(Str[0]) == false) {
				NimAIPlayer p1 = new NimAIPlayer(Str[0], Str[1], Str[2]);
				player = add(p1, player);
			} else
				System.out.println("The player already exists.");
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Allows players to be removed from the game
	 */
	public void removePlayer(String[] Str) {
		boolean ask = true;
		if (Str == null) {
			while (ask == true) {
				System.out.println("Are you sure you want to remove all players? (y/n)");
				String answer = scan.next();
				int flag = 0; // Flag variable identifying whether to remove all data
				if (answer.equals("y"))
					flag = 1;
				else if (answer.equals("n"))
					flag = 2;
				switch (flag) {
				// Remove all players
				case 1:
					int l = player.length;
					for (int i = 0; i < l; i++) {
						player = remove(player);
					}
					ask = false;
					break;
				case 2:
					ask = false;
					break;
				default:
					System.out.println("Please enter correct format.");
				}
			}
		} else {
			if (player[0] == null) {
				System.out.println("The player does not exist.");
			} else {
				// Check if the user exists in the user list
				if (check(Str[0]) == false)
					System.out.println("The player does not exist.");
				else {// Delete the player with the specified username
					for (int i = 0; i < player.length; i++) {
						if (player[i].getUserName().equals(Str[0]))
							player = remove(i, player);
					}
				}
			}
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Allows player details to be edited
	 */
	public void editPlayer(String[] Str) {
		if (player[0] == null) {
			System.out.println("The player does not exist.");
		} else {
			// Check if the user already exists in the user list
			if (check(Str[0]) == false)
				System.out.println("The player does not exist.");
			else {
				for (int i = 0; i < player.length; i++) {
					if (player[i].getUserName().equals(Str[0]))
						player[i].setName(Str[1], Str[2]);
				}
			}
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Allows player statistics to be reset
	 */
	public void resetStats(String[] Str) {
		boolean ask = true;
		if (Str == null) {
			while (ask == true) {
				System.out.println("Are you sure you want to reset all player statistics? (y/n)");
				String answer = scan.next();
				int flag = 0; // Flag variable identifying whether to reset all data
				if (answer.equals("y"))
					flag = 1;
				else if (answer.equals("n"))
					flag = 2;

				switch (flag) {
				case 1:
					for (int i = 0; i < player.length; i++) {
						player[i].reset();
					}
					ask = false;
					break;
				case 2:
					ask = false;
					break;
				default:
					System.out.println("Please enter correct format.");
				}
			}
		} else { // Reset all data of the specified user
			if (player[0] == null) {
				System.out.println("The player does not exist.");
			} else {
				// Check if the user exists in the user list
				if (check(Str[0]) == false)
					System.out.println("The player does not exist.");
				else {
					for (int i = 0; i < player.length; i++) {
						if (player[i].getUserName().equals(Str[0]))
							player[i].reset();
					}
				}
			}
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Displays players' information
	 */
	public void displayPlayer(String[] Str) {
		if (Str == null) {
			// Display information for all players, ordered by username alphabetically
			if (player[0] != null) {
				for (int i = 0; i < player.length; i++) {
					for (int j = 0; j < player.length - 1 - i; j++) {
						if (player[j + 1].getUserName().compareTo(player[j].getUserName())<= 0){
							NimPlayer temp = player[j + 1];
							player[j + 1] = player[j];
							player[j] = temp;
						}
					}
				}
				for (int i = 0; i < player.length; i++)
					player[i].display();
			}
		} else { // Display the information of the specified user
			if (player[0] == null) {
				System.out.println("The player does not exist.");
			} else {
				// Check if the user exists in the user list
				if (check(Str[0]) == false)
					System.out.println("The player does not exist.");
				else {
					for (int i = 0; i < player.length; i++) {
						if (player[i].getUserName().equals(Str[0]))
							player[i].display();
					}
				}
			}
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Outputs a list of player rankings
	 */
	public void rank(String[] Str) {
		int l = 0;
		if (Str == null || Str[0].equals("desc")) {
			bubbleSortDesc(player);
			if (player.length > RANK)
				l = RANK;
			else
				l = player.length;
			for (int i = 0; i < l; i++) {
				// Round the percentages to the nearest integer value
				int ratio = (int) Math.round(PERCENT * player[i].getWinRatio());
				String r = ratio + "%";
				String num = " " + player[i].convertGamesNum() + " games";
				String fullName = player[i].getFullName();
				// Output according to the specified format
				String num_format = String.format("%-10s", num);
				System.out.println(String.format("%-5s", r) + "|" + num_format + "| " + fullName);
			}
		} else {
			bubbleSortAsc(player);
			if (player.length > RANK)
				l = RANK;
			else
				l = player.length;
			for (int i = 0; i < l; i++) {
				// Round the percentages to the nearest integer value
				int ratio = (int) Math.round(PERCENT * player[i].getWinRatio());
				String r = ratio + "%";
				String num = " " + player[i].convertGamesNum() + " games";
				String fullName = player[i].getFullName();
				// Output according to the specified format
				String num_format = String.format("%-10s", num);
				System.out.println(String.format("%-5s", r) + "|" + num_format + "| " + fullName);
			}
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Creates and commences a game of Nim
	 */
	public void startGame(String[] Str) {
		// Check if all players are exist in user list
		if (check(Str[2]) == false || check(Str[3]) == false) {
			System.out.println("One of the players does not exist.");
		} else {
			if (check(Str[2]) && check(Str[3]) == true) {
				int stoneInitial = Integer.parseInt(Str[0]);
				int stoneUpper = Integer.parseInt(Str[1]);
				NimPlayer p1 = findByUsername(Str[2]);
				NimPlayer p2 = findByUsername(Str[3]);

				// Create a new instance of NimGame
				NimGame game = new NimGame(stoneInitial, stoneUpper, p1, p2);
				System.out.println();
				System.out.println("Initial stone count: " + stoneInitial);
				System.out.println("Maximum stone removal: " + stoneUpper);
				System.out.println("Player 1: " + p1.getFullName());
				System.out.println("Player 2: " + p2.getFullName());
				game.games();
			}
		}
		System.out.println();
		System.out.print("$");
	}

	/**
	 * Exits the Nimsys program and store data in .dat file
	 */
	public void exit() {
		// Delete the original file and create a new blank file with the same name
		fil.delete();
		fil = new File(url + "players.dat");

		// Write all current player data to this file
		write_data(fil, player);
		System.out.println();
		System.exit(0);
	}

	/**
	 * Find the corresponding index according to the user name in the array
	 */
	public NimPlayer findByUsername(String n) {
		for (int i = 0; i < player.length; i++) {
			if (n.equals(player[i].getUserName()))
				return player[i];
		}
		System.out.println("The player does not exist.");
		return player[0];
	}

	/**
	 * Sort player array in descending order using bubble sort
	 */
	public NimPlayer[] bubbleSortDesc(NimPlayer[] array) {
		if (array.length == 0)
			return array;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j + 1].getWinRatio() > array[j].getWinRatio()) {
					NimPlayer temp = array[j + 1];
					array[j + 1] = array[j];
					array[j] = temp;
				} else {
					if (array[j + 1].getWinRatio() == array[j].getWinRatio()) {
						// Alphabetize user names when ratios are the same
						if (array[j + 1].getUserName().compareTo(array[j].getUserName()) <= 0) {
							NimPlayer temp = array[j + 1];
							array[j + 1] = array[j];
							array[j] = temp;
						}
					}
				}
			}
		}
		return array;
	}

	/**
	 * Sort player array in ascending order by using bubble sort
	 */
	public NimPlayer[] bubbleSortAsc(NimPlayer[] array) {
		if (array.length == 0)
			return array;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length - 1 - i; j++) {
				if (array[j + 1].getWinRatio() < array[j].getWinRatio()) {
					NimPlayer temp = array[j + 1];
					array[j + 1] = array[j];
					array[j] = temp;
				} else {
					if (array[j + 1].getWinRatio() == array[j].getWinRatio()) {
						// Alphabetize user names when ratios are the same
						if (array[j + 1].getUserName().compareTo(array[j].getUserName()) <= 0) {
							NimPlayer temp = array[j + 1];
							array[j + 1] = array[j];
							array[j] = temp;
						}
					}
				}
			}
		}
		return array;
	}

	/**
	 * Check if the current user name exists in player array
	 */
	public boolean check(String s) {
		for (int i = 0; i < player.length; i++) {
			if (s.equals(player[i].getUserName()))
				return true;
		}
		return false;
	}

	/**
	 * Dynamically add elements at the end of player array
	 */
	public static NimPlayer[] add(NimPlayer element, NimPlayer[] p) {
		NimPlayer[] array = new NimPlayer[p.length + 1];
		for (int i = 0; i < p.length; i++) {
			array[i] = p[i];
		}
		array[p.length] = element;
		return array;
	}

	/**
	 * Dynamically remove the element with the specified index in player array
	 */
	public NimPlayer[] remove(int i, NimPlayer[] p) {
		if (p.length == 1) {
			p[0] = null;
			return p;
		} else {
			NimPlayer[] array = new NimPlayer[p.length - 1];
			for (int m = 0; m < array.length; m++) {
				if (m < i)
					array[m] = p[m];
				else
					array[m] = p[m + 1];
			}
			return array;
		}
	}

	/**
	 * Dynamically remove elements at the end of player array
	 */
	public NimPlayer[] remove(NimPlayer[] p) {
		if (p.length == 1) {
			p[0] = null;
			return p;
		} else {
			NimPlayer[] array = new NimPlayer[p.length - 1];
			for (int m = 0; m < array.length; m++) {
				array[m] = p[m];
			}
			return array;
		}
	}

	/**
	 * Read in the data from the .dat file;
	 * Create new player objects, and fill in the corresponding information
	 */
	public static NimPlayer[] read_data(File file, NimPlayer[] player_before) {
		FileReader input = null;
		BufferedReader reader = null;
		try {
			input = new FileReader(file);
			reader = new BufferedReader(input);
			String line = null;
			// Read line by line
			while ((line = reader.readLine()) != null) {
				NimPlayer p;
				String[] temp = line.split("\t");
				// Create a new player object; Assign values to all variables
				String type = temp[0];
				if(type.equals("NimHumanPlayer"))
					 p = new NimHumanPlayer(temp[1], temp[2], temp[3]);
				else
					 p = new NimAIPlayer(temp[1], temp[2], temp[3]);
				p.setGamesPlayed(Double.valueOf(temp[4]));
				p.setGamesWon(Double.valueOf(temp[5]));
				p.setWinRatio(Double.valueOf(temp[6]));
				
				// Add to player array dynamically
				if (player_before[0] != null)
					player_before = add(p, player_before);
				else
					player_before[0] = p;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return player_before;
	}

	/**
	 * Write all current player data to .dat file
	 */
	public void write_data(File f, NimPlayer[] player) {
		try {
			FileWriter fileWriter = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			for (NimPlayer p : player) {
				/*
				 * Write the information into file in order; 
				 * Separate data of a player with \t;
				 * Different players are separated by blank lines
				 */
				bw.write(p.getType(p) + "\t");
				bw.write(p.getUserName() + "\t");
				bw.write(p.getFamilyName() + "\t");
				bw.write(p.getGivenName() + "\t");
				bw.write( p.getPlayed() + "\t");
				bw.write( p.getWin() + "\t");
				bw.write(p.getWinRatio() + "\t");
				bw.write("\n");
			}
			// Close BufferedWriter
			bw.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
