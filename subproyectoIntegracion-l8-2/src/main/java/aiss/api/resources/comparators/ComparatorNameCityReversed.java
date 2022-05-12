package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.City;

public class ComparatorNameCityReversed implements Comparator<City>{

	@Override
	public int compare(City o1, City o2) {
		return o2.getName().compareTo(o1.getName());
	}

}
