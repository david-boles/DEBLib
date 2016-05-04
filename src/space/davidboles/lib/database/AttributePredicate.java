package space.davidboles.lib.database;

import java.util.function.Predicate;

public class AttributePredicate implements Predicate<Attribute<?>> {

	String id;
	
	public AttributePredicate(String id) {
		this.id = id;
	}
	
	@Override
	public boolean test(Attribute<?> t) {
		return id.equals(t.getID());
	}

	

}
