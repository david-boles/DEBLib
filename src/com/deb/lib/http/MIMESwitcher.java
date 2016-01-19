package com.deb.lib.http;

public class MIMESwitcher {
	
	public static String toMIME(String fileName) {
		String lowName = fileName.toLowerCase();
		for(int i = 0; i < types.length; i++) {
			if(lowName.endsWith(types[i][0])) {
				return types[i][1];
			}
		}
		
		return null;
	}
	
	public static String toExtension(String mime) {
		String lowName = mime.toLowerCase();
		for(int i = 0; i < types.length; i++) {
			if(lowName.endsWith(types[i][1])) {
				return types[i][0];
			}
		}
		
		return null;
	}

	public static final String[][] types =  {
		{".png", "images/png"}
		//TODO add types
	};
}
