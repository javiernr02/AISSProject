package aiss.model.repository;

import java.util.Collection;

import aiss.model.City;
import aiss.model.Event;
import aiss.model.FQA;


public interface CityRepository {
	
	
	// Events
	public void addEvent(Event e);
	public Collection<Event> getAllEvents();
	public Event getEvent(String eventId);
	public void updateEvent(Event e);
	public void deleteEvent(String eventId);
	public void addFQA(String eventId, String fqaId);
	public void removeFQA(String eventId,String fqaId);
	
	// Cities
	public void addCity(City c);
	public Collection<City> getAllCities();
	public City getCity(String cityId);
	public void updateCity(City c);
	public void deleteCity(String cityId);
	public Collection<Event> getAll(String cityId);
	public void addEvent(String cityId, String eventId);
	public void removeEvent(String cityId, String eventId);
	
	//FQAs
	public void addFQA(FQA f);
	public Collection<FQA> getAllFQAs();
	public  FQA getFQA(String fqaId);
	public void deleteFQA(String fqaId);
	
	

	
	
	
	

}