package com.deb.lib.program;

/**
 * Various useful functions for logging updates and errors. ULogger uses System.out and System.err.
 * @author David Boles
 *
 */
public class ULogger {
	
	public void log(String log) {
		System.out.println("[" + System.currentTimeMillis() + "] " + log);
	}
	
	public void log(String message, Object o) {
		this.log(message + ": " + o);
	}
	
	public void error(String error) {
		System.err.println("[" + System.currentTimeMillis() + "] " + error);
	}
	
	public void error(String message, Object o) {
		error(message + ": " + o);
	}
	
	@SuppressWarnings("null")
	public void exception(String customMessage, Exception exception) {
		System.err.print("Exception occured");
		if(customMessage != null || customMessage.equals("")) {
			System.err.println(", " + customMessage + ": ");
		}else {
			System.err.println(": ");
		}
		exception.printStackTrace(System.err);
	}
}
