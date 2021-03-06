package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class Event {

	private String id;
	private String name;
	private String description;
	private String organizer;
	private String category;
	private String location;
	private String date;
	private String price;
	private List<FQA> FQAs;
	
	public Event() {	
	}
	
	public Event(String name) {
		this.name = name;
	}
	
	public Event(String name, String description, String organizer, String category, 
			String location, String date, String price) {
		this.name = name;
		this.description = description;
		this.organizer = organizer;
		this.category = category;
		this.location = location;
		this.date = date;
		this.price = price;
	}

	public Event(String id, String name, String description, String organizer, String category, 
			String location, String date, String price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.organizer = organizer;
		this.category = category;
		this.location = location;
		this.date = date;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	public FQA getFQA(String id) {
		if(FQAs==null) return null;
		FQA fqa=null;
		for(FQA f : FQAs) {
			if(f.getId().equals(id)) {
				fqa = f;
				break;
			}
		}
		return fqa;
			
	}
	public void addFQA(FQA f) {
		if(FQAs == null) FQAs = new ArrayList<FQA>();
		FQAs.add(f);
	}
	
	public void deleteFQA(FQA f) {
		FQAs.remove(f);
	}
	
	public void deleteFQA(String id) {
		FQA fqa = getFQA(id);
		
		if(fqa!=null) {
			FQAs.remove(fqa);
		}
	
	}
	
	public List<FQA> getFQAs() {
		return FQAs;
	}
	
	
}
