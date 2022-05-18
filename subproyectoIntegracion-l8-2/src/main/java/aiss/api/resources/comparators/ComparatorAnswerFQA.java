package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.FQA;

public class ComparatorAnswerFQA implements Comparator<FQA>{

	@Override
	public int compare(FQA o1, FQA o2) {
		// TODO Auto-generated method stub
		return o1.getAnswer().compareTo(o2.getAnswer());
	}

}
