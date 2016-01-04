package com.deb.lib.database;

import java.util.ArrayList;

public class AttributesList {
	private ArrayList<Attribute<? extends Object>> attributes = new ArrayList<Attribute<? extends Object>>();
	
	/**
	 * Gets all attributes
	 * 
	 * @return ArrayList of all attributes
	 */
	@SuppressWarnings("unchecked")
	public Attribute<? extends Object>[] getAll() {
		return (Attribute<? extends Object>[]) this.attributes.toArray();
	}
	
	/**
	 * Adds/sets an Attribute for the AttributeBuilder instance, prevents multiple values of the same ID.
	 * 
	 * @param a The Attribute
	 * @return The AttributeBuilder instance, allows chaining: new AttributeBuilder().add(...).add(...).add(...)
	 */
	public AttributesList addSet(Attribute<? extends Object> a) {
		for(int pos = 0; pos < attributes.size(); pos++) {
			if(attributes.get(pos).getAID().equals(a.getAID())) {
				attributes.set(pos, a);
			}
		}
		
		attributes.add(a);
		
		return this;
	}
	
	public Attribute<? extends Object> getAttribute(String nameID) {
		Attribute<? extends Object> out = null;
		for(int pos = 0; pos < attributes.size(); pos++) {
			if(attributes.get(pos).getAID().equals(nameID)) out = attributes.get(pos);
		}
		return out;
	}

}