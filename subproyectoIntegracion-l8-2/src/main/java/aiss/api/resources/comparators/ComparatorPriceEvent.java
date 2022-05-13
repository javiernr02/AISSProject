package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorPriceEvent implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getPrice().compareTo(o2.getPrice());
	}

}