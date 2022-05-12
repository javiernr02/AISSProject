package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.Event;

public class ComparatorOrganizerEvent implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getOrganizer().compareTo(o2.getOrganizer());
	}

}
