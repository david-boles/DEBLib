package com.deb.lib.program;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Logger {
	PrintStream out;
	PrintStream err;
	
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
	
	public void log(String log) {
		this.out.println("[" + System.currentTimeMillis() + "] " + log);
	}
	
	public void log(String message, Object o) {
		this.log(message + ": " + o);
	}
	
	public void error(String error) {
		this.err.println("[" + System.currentTimeMillis() + "] " + error);
	}
	
	public void error(String message, Object o) {
		this.error(message + ": " + o);
	}
}
