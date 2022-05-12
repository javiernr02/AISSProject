package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorCategoryEvent implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getCategory().compareTo(o2.getCategory());
	}

}
