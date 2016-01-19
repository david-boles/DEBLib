package space.davidboles.lib.program;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * Various useful functions for logging updates and errors. Logger is non static and allows construction with different outputs.
 * @author David Boles
 *
 */
public class Logger {
	public static Logger uLogger = new Logger();
	
	PrintStream out;
	PrintStream err;
	boolean enabled = true;
	boolean verbose = true;
	
	/**
	 * Sets the Logger's PrintStreams to those of System.
	 */
	public Logger() {
		this.out = System.out;
		this.err = System.err;
	}
	
	/**
	 * Sets the Logger's PrintStreams to the input.
	 * @param log The PrintStream for standard logging
	 * @param errorLog The PrintStream for erroring
	 */
	public Logger(PrintStream log, PrintStream errorLog) {
		this.out = log;
		this.err = errorLog;
	}
	
	/**
	 * Calls createNewFile() on input and sets the Logger's PrintStreams to new ones of the Files. Defaults to System's in the case of an IOException.
	 * @param log The file for standard logging
	 * @param errorLog The file for erroring
	 */
	public Logger(File log, File errorLog) {
		try {
			log.createNewFile();
			this.out = new PrintStream(log);
		} catch (IOException e) {
			this.out = System.out;
		}
		
		try {
			errorLog.createNewFile();
			this.err = new PrintStream(errorLog);
		} catch (IOException e) {
			this.err = System.err;
		}
	}
	
	//Standard logging methods
	/**
	 * Printlns your log preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If logging was successful
	 */
	public boolean log(String log) {
		if(this.enabled) {
			this.out.println("[" + System.currentTimeMillis() + "] " + log);
			return true;
		}
		return false;
	}
	
	/**
	 * Logs your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If logging was successful
	 */
	public boolean log(String message, Object o) {
		return this.log(message + ": " + o.toString());
	}
	
	/**
	 * Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	public boolean logMore(String message, Object[] o) {
		String out = message + ": " + o[0].toString();
		for(int i = 1; i < o.length; i++) {
			out += ", " + o[i].toString();
		}
		return this.log(out);
	}
	
	/**
	 * Printlns your error preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If erroring was successful
	 */
	public boolean error(String error) {
		if(this.enabled) {
			this.err.println("[" + System.currentTimeMillis() + "] " + error);
			return true;
		}
		return false;
	}
	
	/**
	 * Errors your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If erroring was successful
	 */
	public boolean error(String message, Object o) {
		return this.error(message + ": " + o);
	}
	
	/**
	 * Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	public boolean errorMore(String message, Object[] o) {
		String out = message + ": " + o[0].toString();
		for(int i = 1; i < o.length; i++) {
			out += ", " + o[i].toString();
		}
		return this.error(out);
	}
	
	/**
	 * Errors your message and your exception's stack trace. If you do not want a message, it can be null of blank.
	 * @param customMessage Your message
	 * @param exception Your exception
	 * @return If erroring was successful.
	 */
	@SuppressWarnings("null")
	public boolean exception(String customMessage, Exception exception) {
		if(this.enabled) {
			this.err.print("Exception occured");
			if(customMessage != null || customMessage.equals("")) {
				this.err.println(", " + customMessage + ": ");
			}else {
				this.err.println(": ");
			}
			exception.printStackTrace(this.err);
			return true;
		}
		return false;
	}
	
	//Verbose logging methods
	/**
	 * If verbose true: Printlns your log preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If logging was successful
	 */
	public boolean vLog(String log) {
		if(this.verbose) this.log(log); return false;
	}
	
	/**
	 * If verbose true: Logs your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If logging was successful
	 */
	public boolean vLog(String message, Object o) {
		if(this.verbose) this.log(message, o); return false;
	}
	
	/**
	 * If verbose true: Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	public boolean vLogMore(String message, Object[] o) {
		if(this.verbose) this.logMore(message, o); return false;
	}
	
	/**
	 * If verbose true: Printlns your error preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If erroring was successful
	 */
	public boolean vError(String error) {
		if(this.verbose) this.error(error); return false;
	}
	
	/**
	 * If verbose true: Errors your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If erroring was successful
	 */
	public boolean vError(String message, Object o) {
		if(this.verbose) this.error(message, o); return false;
	}
	
	/**
	 * If verbose true: Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	public boolean vErrorMore(String message, Object[] o) {
		if(this.verbose) this.errorMore(message, o); return false;
	}
	
	/**
	 * If verbose true: Errors your message and your exception's stack trace. If you do not want a message, it can be null of blank.
	 * @param customMessage Your message
	 * @param exception Your exception
	 * @return If erroring was successful.
	 */
	public boolean vException(String customMessage, Exception exception) {
		if(this.verbose) this.exception(customMessage, exception); return false;
	}
	
	//Setting setters
	/**
	 * Allows you to set whether or not the Logger instance should log.
	 * @param enabled Whether or not it should log
	 * @return Whether or not it was enabled before
	 */
	public boolean setEnabled(boolean enabled) {
		boolean out = this.enabled;
		this.enabled = enabled;
		return out;
	}
	
	/**
	 * Allows you to set whether or not the Logger instance is verbose.
	 * @param verbose Whether or not it should be verbose
	 * @return Whether or not it was verbose before
	 */
	public boolean setVerbose(boolean verbose) {
		boolean out = this.verbose;
		this.verbose = verbose;
		return out;
	}
}
