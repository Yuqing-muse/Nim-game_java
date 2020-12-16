/** 
 * name: Yuqing Chang
 * student number: 1044862
 * username: yuqchang
 */
public class MyException extends Exception {
	private String message = "";

	/**
	 * No-argument constructor of MyException class
	 */
	public MyException() {
		super();
	}

	/**
	 * Constructor of MyException class
	 */
	public MyException(String msg) {
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
