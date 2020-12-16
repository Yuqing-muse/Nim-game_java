/** 
 * name: Yuqing Chang
 * student number: 1044862
 * username: yuqchang
 */
public class Arguments extends Exception {
	private String message = "";

	/**
	 * No-argument constructor of Arguments class
	 */
	public Arguments() {
		super();
	}

	/**
	 * Constructor of Arguments class
	 */
	public Arguments(String msg) {
		super(msg);
		message = msg;
	}

	/**
	 * Get the message of the error
	 */
	public String getMessage() {
		return this.message;
	}
}
