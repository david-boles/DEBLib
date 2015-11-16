package com.deb.lib.program;

import java.util.ArrayList;

public class IDdObjectManager {
	private ArrayList<IDdObject> objects = new ArrayList<IDdObject>();
	
	public IDdObjectManager() {}
	
	public IDdObjectManager(ArrayList<String> ID, ArrayList<Object> in) {
		this.addMultiple(ID, in, true);
	}

	private int locateObject(String oID) {
		for (int cA = 0; cA < objects.size(); cA++) {
			if (objects.get(cA).getID().equals(oID)) return cA;
		}
		
		return -1;
	}
	
	public boolean addObject(String ID, Object in, boolean overwrite) {
		int oPosition = locateObject(ID);
		
		if (oPosition == -1) {
			objects.add(new IDdObject(ID, in));
			return true;
		}else if(overwrite) {
			objects.set(oPosition, new IDdObject(ID, in));
			return true;
		}else {
			return false;
		}
	}
	
	public boolean addMultiple(ArrayList<String> ID, ArrayList<Object> in, boolean overwrite) {
		boolean out = true;
		for(int cA = 0; cA < in.size(); cA++) {
			if (!this.addObject(ID.get(cA), in.get(cA), overwrite)) out = false;
		}
		return out;
	}
	
	public Object getObject(String ID) {
		try {return objects.get(this.locateObject(ID)).getObject();}
		catch(Exception e) {return null;}
	}

	public boolean idUsed(String id) {
		if(locateObject(id) != -1) return true;
		else return false;
	}
	
	public boolean idsContain(String in) {
		for (int cA = 0; cA < objects.size(); cA++) {
			if (objects.get(cA).getID().contains(in)) return true;;
		}
		
		return false;
	}

}
