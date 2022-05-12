package aiss.model;

import java.util.ArrayList;
import java.util.List;

public class City {
	private String id;
	private String name;
	private String descripion;
	private List<Event> events;
	
	public City(String name) {
		this.name = name;
	}
	
	protected void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripion() {
		return descripion;
	}

	public void setDescripion(String descripion) {
		this.descripion = descripion;
	}

	public List<Event> getEvents() {
		return events;
	}
	public Event getEvent(String id) {
		if(events==null) return null;
		Event event=null;
		for(Event e : events) {
			if(e.getId().equals(id)) {
				event= e;
				break;
			}
		}
		return event;
			
	}
	public void addEvent(Event e) {
		if(events == null) events = new ArrayList<Event>();
		events.add(e);
	}
	public void deleteEvent(Event e) {
		events.remove(e);
	}
	public void deleteEvent(String id) {
		Event event=getEvent(id);
		
		if(events!=null) {
			events.remove(event);
		}
	
	}
	

}
