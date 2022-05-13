package aiss.model.repository;

import java.util.Collection;

import aiss.model.City;
import aiss.model.Event;


public interface CityRepository {
	
	
	// Events
	public void addEvent(Event e);
	public Collection<Event> getAllEvents();
	public Event getEvent(String eventId);
	public void updateEvent(Event e);
	public void deleteEvent(String eventId);
	
	// Cities
	public void addCity(City c);
	public Collection<City> getAllCities();
	public City getCity(String cityId);
	public void updateCity(City c);
	public void deleteCity(String cityId);
	public Collection<Event> getAll(String cityId);
	public void addEvent(String cityId, String eventId);
	public void removeEvent(String cityId, String eventId); 

	
	
	
	

}