package space.davidboles.lib.program;

/**
 * A subclass of Logger that overrides all output methods, returning false immediately. This can be used to replace a Logger that you want to not output without it being null to not require error checking.
 * @author David Boles
 *
 */
public class NullLogger extends Logger {
	//Standard logging methods
	/**
	 * Printlns your log preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If logging was successful
	 */
	@Override
	public boolean log(String log) {
		return false;
	}
	
	/**
	 * Logs your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If logging was successful
	 */
	@Override
	public boolean log(String message, Object o) {
		return false;
	}
	
	/**
	 * Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	@Override
	public boolean logMore(String message, Object[] o) {
		return false;
	}
	
	/**
	 * Printlns your error preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If erroring was successful
	 */
	@Override
	public boolean error(String error) {
		return false;
	}
	
	/**
	 * Errors your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If erroring was successful
	 */
	@Override
	public boolean error(String message, Object o) {
		return false;
	}
	
	/**
	 * Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	@Override
	public boolean errorMore(String message, Object[] o) {
		return false;
	}
	
	/**
	 * Errors your message and your exception's stack trace. If you do not want a message, it can be null of blank.
	 * @param customMessage Your message
	 * @param exception Your exception
	 * @return If erroring was successful.
	 */
	@Override
	@SuppressWarnings("null")
	public boolean exception(String customMessage, Exception exception) {
		return false;
	}
	
	//Verbose logging methods
	/**
	 * If verbose true: Printlns your log preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If logging was successful
	 */
	@Override
	public boolean vLog(String log) {
		return false;
	}
	
	/**
	 * If verbose true: Logs your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If logging was successful
	 */
	@Override
	public boolean vLog(String message, Object o) {
		return false;
	}
	
	/**
	 * If verbose true: Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	@Override
	public boolean vLogMore(String message, Object[] o) {
		return false;
	}
	
	/**
	 * If verbose true: Printlns your error preceded by the time in millis inside square brackets.
	 * @param log What you want to be printed
	 * @return If erroring was successful
	 */
	@Override
	public boolean vError(String error) {
		return false;
	}
	
	/**
	 * If verbose true: Errors your message followed by ": " + o.toString.
	 * @param message Your message
	 * @param o Your object
	 * @return If erroring was successful
	 */
	@Override
	public boolean vError(String message, Object o) {
		return false;
	}
	
	/**
	 * If verbose true: Logs your message followed by ": " + o[0].toString and then each additional object preceded by ", ".
	 * @param message Your message
	 * @param o Your objects
	 * @return If logging was successful
	 */
	public boolean vErrorMore(String message, Object[] o) {
		return false;
	}
	
	/**
	 * If verbose true: Errors your message and your exception's stack trace. If you do not want a message, it can be null of blank.
	 * @param customMessage Your message
	 * @param exception Your exception
	 * @return If erroring was successful.
	 */
	public boolean vException(String customMessage, Exception exception) {
		return false;
	}
}
