package aiss.api.resources;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorCategoryEvent;
import aiss.api.resources.comparators.ComparatorCategoryEventReversed;
import aiss.api.resources.comparators.ComparatorDateEvent;
import aiss.api.resources.comparators.ComparatorDateEventReversed;
import aiss.api.resources.comparators.ComparatorDescriptionEvent;
import aiss.api.resources.comparators.ComparatorDescriptionEventReversed;
import aiss.api.resources.comparators.ComparatorLocationEvent;
import aiss.api.resources.comparators.ComparatorLocationEventReversed;
import aiss.api.resources.comparators.ComparatorOrganizerEvent;
import aiss.api.resources.comparators.ComparatorOrganizerEventReversed;
import aiss.api.resources.comparators.ComparatorPriceEvent;
import aiss.api.resources.comparators.ComparatorPriceEventReversed;
import aiss.model.Event;
import aiss.model.repository.CityRepository;
import aiss.model.repository.MapCityRepository;



@Path("/events")	// Para acceder al recurso .../api/events
public class EventResource {

	public static EventResource _instance=null;
	CityRepository repository;
	
	private EventResource(){
		repository=MapCityRepository.getInstance();
	}
	
	public static EventResource getInstance()
	{
		if(_instance==null)
			_instance=new EventResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Event> getAll(@QueryParam("q") String q, @QueryParam("order") String order,
			@QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<Event> result = new ArrayList<Event>();
		List<Event> events = repository.getAllEvents().stream()
				.collect(Collectors.toList());
		int start = offset == null ? 0: offset - 1;
		int end = limit == null ? events.size(): start + limit;
		
		// Bloque de código de filtrado y de paginación
		for(int i = start; i < end; i++) {
			if(offset == null || limit == null || events.get(i).getName().contains(q) || events.get(i).getCategory().contains(q)
					|| events.get(i).getLocation().contains(q)) {
				
				result.add(events.get(i));
			}
		}
		
		// Bloque de código de ordenación
		if(order != null) {
			if(order.equals("category")) {
				Collections.sort(result, new ComparatorCategoryEvent());
			}
			else if(order.equals("-category")) {
				Collections.sort(result, new ComparatorCategoryEventReversed());
			}
			else if(order.equals("date")) {
				Collections.sort(result, new ComparatorDateEvent());
			}
			else if(order.equals("-date")) {
				Collections.sort(result, new ComparatorDateEventReversed());
			}
			else if(order.equals("description")) {
				Collections.sort(result, new ComparatorDescriptionEvent());
			}
			else if(order.equals("-description")) {
				Collections.sort(result, new ComparatorDescriptionEventReversed());
			}
			else if(order.equals("location")) {
				Collections.sort(result, new ComparatorLocationEvent());
			}
			else if(order.equals("-location")) {
				Collections.sort(result, new ComparatorLocationEventReversed());
			}
			else if(order.equals("organizer")) {
				Collections.sort(result, new ComparatorOrganizerEvent());
			}
			else if(order.equals("-organizer")) {
				Collections.sort(result, new ComparatorOrganizerEventReversed());
			}
			else if(order.equals("price")) {
				Collections.sort(result, new ComparatorPriceEvent());
			}
			else if(order.equals("-price")) {
				Collections.sort(result, new ComparatorPriceEventReversed());
			}
			else {
				throw new BadRequestException("The order parameter must be 'category', '-category', "
						+ "'date', '-date', 'description', '-description', 'location', '-location'"
						+ ", 'organizer', '-organizer', 'price', '-price'.");
			}
		}
		
		return result;
		
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Event get(@PathParam("id") String eventId)
	{
		Event event = repository.getEvent(eventId);
		
		if(event == null) {
			throw new NotFoundException("The event with id=" + eventId + " was not found");
		}
		
		return event;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addEvent(@Context UriInfo uriInfo, Event event) {
		if(event.getName() == null || "".equals(event.getName())) {
			throw new BadRequestException("The name of the event must not be null");
		}
		
		repository.addEvent(event);
		
		// Builds the response. Returns the song that has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(event.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(event);
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateEvent(Event event) {
		Event oldEvent = repository.getEvent(event.getId());
		
		if(oldEvent == null) {
			throw new NotFoundException("The event with id=" + event.getId() + " was not found");
		}
		
		// Update name
		if(event.getName() != null) {
			oldEvent.setName(event.getName());
		}
		
		// Update category
		if(event.getCategory() != null) {
			oldEvent.setCategory(event.getCategory());
		}
		
		// Update description
		if(event.getDescription() != null) {
			oldEvent.setDescription(event.getDescription());
		}
		
		// Update organizer
		if(event.getOrganizer() != null) {
			oldEvent.setOrganizer(event.getOrganizer());
		}
		// Update location
		if(event.getLocation() != null) {
			oldEvent.setLocation(event.getLocation());
		}
		// Update date
		if(event.getDate() != null) {
			oldEvent.setDate(event.getDate());
		}
		// Update price
		if(event.getPrice() != null) {
			oldEvent.setPrice(event.getPrice());
		}
				
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeEvent(@PathParam("id") String eventId) {
		Event event = repository.getEvent(eventId);  // Obtenemos el evento a eliminar
		
		if(event == null) {
			throw new NotFoundException("The event with id=" + eventId + " was not found");
		}
		else {
			repository.deleteEvent(eventId);
		}
		
		return Response.noContent().build();
	}
	
}
