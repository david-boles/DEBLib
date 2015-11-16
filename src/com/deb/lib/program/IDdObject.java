package com.deb.lib.program;

public class IDdObject {
	
	private String oID;
	private Object o;
	
	public IDdObject(String ID, Object o) {
		this.oID = ID;
		this.o = o;
	}
	
	protected void setObject(Object o) {this.o = o;}
	
	protected String getID() {return this.oID;}
	protected Object getObject() {return this.o;}
	
}
