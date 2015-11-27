package com.deb.lib.program;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BasicSettingManager {

	private ArrayList<Object> Settings = new ArrayList<Object>();
	private boolean hasUpdates = false;
	
	@SuppressWarnings("unchecked")
	public boolean readSettings() {
		try {
			ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "/Settings.txt")));
			Settings = (ArrayList<Object>) oin.readObject();
			oin.close();
			return(true);
		}catch(Exception e) {
			return(false);
		}
	}
	
	public boolean writeSettings() {
		try {
			ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(System.getProperty("user.dir") + "/Settings.txt")));
			oout.writeObject(Settings);
			oout.close();
			this.hasUpdates = false;
			return(true);
		}catch(Exception e) {
			return(false);
		}
	}
	
	public String listAll() {
		String out = "";
		
		for (int c = 0; c < Settings.size(); c++) {
			out += c + ": " + Settings.get(c) + "\n";
		}
		
		return out;
	}
	
	public boolean initialize(ArrayList<Object> defaults) {//NOTE: Add code to add new values
		if(!this.readSettings()) {
			
			this.Settings = defaults;
			this.writeSettings();
			return false;
			
		}else if(this.Settings.size() != defaults.size()) {
			
			for(int i = this.Settings.size(); i < defaults.size(); i++) {
				this.Settings.add(defaults.get(i));
			}

			this.writeSettings();
			return false;
			
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSetting(int ID) {
		return (T) Settings.get(ID);
	}
	
	public Object setSetting(int ID, Object o) {
		this.hasUpdates = true;
		return Settings.set(ID, o);
	}
	
	public boolean hasUpdates() {
		return this.hasUpdates;
	}
	
}
