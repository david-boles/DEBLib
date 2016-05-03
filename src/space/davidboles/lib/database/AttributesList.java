package space.davidboles.lib.database;

import java.io.IOException;
import java.io.Serializable;
import java.util.TreeSet;


public class AttributesList implements Serializable {
	private static final long serialVersionUID = 1L;
	protected TreeSet<Attribute<? extends Serializable>> attributes = new TreeSet<Attribute<? extends Serializable>>();
	
	/**
	 * Adds or updates an attribute.
	 * @param a The attribute you are adding or setting.
	 * @return true if this attribute existed in the AttributeList already.
	 */
	public boolean addSet(Attribute<? extends Serializable> a) {
		synchronized(this.attributes) {
			boolean removed = this.attributes.removeIf(new AttributePredicate(a.aID));
			this.attributes.add(a);
			return removed;
		}
	}
	
	/**
	 * Creates a new Attribute object with the arguments and passes it to addSet().
	 * @param id The unique ID of your attribute.
	 * @param data The value of your attribute.
	 * @return What addSet() returns from the new AttributeObject, true if this attribute existed in the AttributeList already.
	 */
	public <T extends Serializable> boolean addSet(String id, T data) {
		synchronized(this.attributes) {
			return this.addSet(new Attribute<T>(id, data));
		}
	}
	
	/**
	 * Removes an Attribute from the AttributeList.
	 * @param id The id of the Attribute you are trying to remove.
	 * @return If it was removed, false if not present.
	 */
	public boolean removeAttribute(String id) {
		synchronized(this.attributes) {
			return this.attributes.removeIf(new AttributePredicate(id));
		}
	}
	
	/**
	 * Gets an attribute from the AttributesList.
	 * @param id The id of the Attribute you are trying to access.
	 * @return The attribute with the ID you requested, null if not in the AttributesList.
	 * @throws ClassCastException If the type of the value of the attribute is not a subtype of the provided type.
	 */
	@SuppressWarnings("unchecked")//TODO see if throwing works
	public <T extends Serializable> Attribute<T> getAttribute(String id) throws ClassCastException {
		synchronized(this.attributes) {
			Attribute<?> a = this.attributes.ceiling(new Attribute<T>(id, null));
			if(a.aID.equals(id)) return (Attribute<T>) a;
			else return null;
		}
	}
	
	/**
	 * Copies the AttributeList into an Array.
	 * @return The copy of the AttributeList.
	 */
	public Attribute<? extends Serializable>[] getAllSafe() {
		synchronized(this.attributes) {
			return this.attributes.toArray(new Attribute<?>[this.attributes.size()]);
		}
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(this.attributes);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		this.attributes = (TreeSet<Attribute<? extends Serializable>>) in.readObject();
	}

}