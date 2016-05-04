package space.davidboles.lib.database;

import java.io.IOException;
import java.io.Serializable;

public class Attribute <T extends Serializable> implements Comparable<Attribute<? extends Serializable>>, Serializable{
	private static final long serialVersionUID = 1L;
	private String aID;
	protected T attribute;
	
	/**
	 * Initialize a new Attribute
	 * 
	 * @param aID Attribute ID
	 * @param attribute Attribute object
	 */
	public Attribute (String aID, T attribute) {
		this.aID = aID;
		this.attribute = attribute;
	}
	
	/**
	 * Gets the Attribute instance's ID
	 * 
	 * @return A copy of the Attribute instance's ID
	 */
	public String getID() {
		return new String(this.aID);
	}
	
	/**
	 * Sets the Attribute instance's attribute
	 * 
	 * @param attribute The new Attribute instance's attribute
	 */
	public void setAttribute(T attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * Gets the Attribute instance's attribute
	 * 
	 * @return attribute The Attribute instance's attribute
	 */
	public T getAttribute() {
		return this.attribute;
	}

	@Override
	public int compareTo(Attribute<? extends Serializable> o) {
		return this.aID.compareTo(((Attribute<? extends Serializable>)o).aID);
	}
	
	/**
	 * Writes the ID and attribute to an ObjectOutputStream.
	 * @param out The ObjectOutputStream to write to.
	 * @throws IOException Thrown by out.writeObject(~);
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(this.aID);
		out.writeObject(this.attribute);
	}
	
	/**
	 * Reads the ID and attribute from an ObjectInputStream.
	 * @param in The ObjectInputStream to read from.
	 * @throws IOException Thrown by in.readObject(~);
	 * @throws ClassNotFoundException Thrown by in.readObject(~);
	 */
	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		this.aID = (String) in.readObject();
		this.attribute = (T) in.readObject();
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": {"+this.getID()+", "+this.getAttribute().toString()+"}";
	}
	
}
