/** 
 * name: Yuqing Chang
 * student number: 1044862
 * username: yuqchang
 */
import java.util.Random;

public class NimAIPlayer extends NimPlayer implements Testable {
	// you may further extend a class or implement an interface
	// to accomplish the tasks.

	/**
	 * Constructor of NimAIPlayer class
	 */
	public NimAIPlayer(String username, String family_name, String given_name) {
		// Call the constructor of the parent class
		super(username, family_name, given_name);
	}

	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		return move;
	}

	/**
	 * Return the number of stones the AIplayer wants to remove
	 */
	public int removeStone(NimGame game) {
		System.out.println(givenName + "'s turn - remove how many?");
		Random rand = new Random();
		NimPlayer[] players = new NimPlayer[2]; 
		players = game.getPlayer(); 
		int initial = game.getStoneInitial(); 
		int upper = Math.min(initial, game.getStoneUpper()); 
		int k = (initial - 1) % (upper + 1); 
		boolean condition1 = getType(players[0]).equals("NimAIPlayer") && (k == 0);
		boolean condition2 = getType(players[1]).equals("NimAIPlayer") && (k != 0);
		if(condition1 || condition2) {
			for(int i=1; i<= Math.min(game.getStoneLeft(), game.getStoneUpper()); i++)
		    {
				int m = Math.min(game.getStoneLeft()-i, game.getStoneUpper());
				if((game.getStoneLeft()-i) % (m+1) == 0) {
					this.removeNum = i;
					return this.removeNum;
				}
		    }
		}else {
			
				this.removeNum = game.getMin(); 
	
		}
		return this.removeNum;
	}


}
