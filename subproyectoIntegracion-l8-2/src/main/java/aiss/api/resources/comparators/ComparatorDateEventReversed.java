package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorDateEventReversed implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o2.getDate().compareTo(o1.getDate());
	}

}
