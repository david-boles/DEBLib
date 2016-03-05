package space.davidboles.lib.database;

public class Attribute <T> implements Comparable<Attribute<?>>{
	String aID;
	T attribute;
	
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
	 * @return The Attribute instance's ID
	 */
	public String getAID() {
		return this.aID;
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
	public int compareTo(Attribute<?> o) {
		return this.aID.compareTo(((Attribute<?>)o).aID);
	}
}
