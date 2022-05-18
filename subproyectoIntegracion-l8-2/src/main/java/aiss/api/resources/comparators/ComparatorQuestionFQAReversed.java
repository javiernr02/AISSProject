package aiss.api.resources.comparators;

import java.util.Comparator;

import aiss.model.FQA;

public class ComparatorQuestionFQAReversed implements Comparator<FQA>{

	@Override
	public int compare(FQA o1, FQA o2) {
		// TODO Auto-generated method stub
		return o2.getQuestion().compareTo(o1.getQuestion());
	}

}
