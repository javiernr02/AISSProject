package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorDateEvent implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

}
