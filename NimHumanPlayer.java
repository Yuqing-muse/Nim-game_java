/** 
 * name: Yuqing Chang
 * student number: 1044862
 * username: yuqchang
 */
public class NimHumanPlayer extends NimPlayer {

	/**
	 * Constructor of NimHumanPlayer class
	 */
	public NimHumanPlayer(String username, String family_name, String given_name) {
		//Call the constructor of the parent class
		super(username, family_name, given_name);
	}

	/**
	 * Ask and return the number of stones the player wants to remove
	 */
	public int removeStone(NimGame game) {
		System.out.println(givenName + "'s turn - remove how many?");
		this.removeNum = Nimsys.scan.nextInt();
		return this.removeNum;
	}
}
