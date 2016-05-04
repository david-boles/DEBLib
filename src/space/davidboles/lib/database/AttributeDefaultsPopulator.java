package space.davidboles.lib.database;

import java.io.Serializable;

public abstract class AttributeDefaultsPopulator {
	
	/**
	 * Creates and returns a new AttributesList with default values.
	 * @return The new AttributesList.
	 */
	protected abstract AttributesList getDefaultsCopy();
	
	/**
	 * Calls populateDefaults(toPopulate, "");
	 * @param toPopulate The AttributesList to populate.
	 */
	public void populateDefaults(AttributesList toPopulate) {
		this.populateDefaults(toPopulate, "");
	}
	
	/**
	 * If any prependID+(getDefaultsCopy's attributes' IDs) do not exist in toPopulate, they are populated with the values in getDefaultsCopy().
	 * @param toPopulate The AtributesList to populate.
	 * @param prependID The ID segment to prepend to those in getDefaultsCopy();
	 */
	public void populateDefaults(AttributesList toPopulate, String prependID) {
		AttributesList defaults = this.getDefaultsCopy();
		Attribute<? extends Serializable>[] dAA = defaults.getAll();
		
		for(int a = 0; a < dAA.length; a++) {
			Attribute<? extends Serializable> attribute = dAA[a];
			String id = prependID + attribute.getID();
			
			if(toPopulate.getAttribute(id) == null) toPopulate.addSet(id, attribute.getAttribute());
		}
		
	}
}
