package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.City;

public class ComparatorNameCity implements Comparator<City>{

	@Override
	public int compare(City o1, City o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
