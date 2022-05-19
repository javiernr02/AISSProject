package aiss.api.resources;

import java.net.URI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import aiss.api.resources.comparators.ComparatorOrganizerEvent;
import aiss.api.resources.comparators.ComparatorOrganizerEventReversed;
import aiss.model.Event;
import aiss.model.FQA;
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
	public Collection<Event> getAll(@QueryParam("limit") Integer limit, @QueryParam("offset")
			Integer offset, @QueryParam("q") String q, @QueryParam("name") String name,
			@QueryParam("organizer") String organizer, @QueryParam("category") String category,
			@QueryParam("location") String location, @QueryParam("isEmpty") Boolean isEmpty,
			@QueryParam("fields") String fields, @QueryParam("order") String order)
	{
		List<Event> result = new ArrayList<Event>();
		List<Event> events = repository.getAllEvents().stream()
				.collect(Collectors.toList());
		
		int start = offset == null ? 0: offset;
		int end = limit == null ? events.size(): start + limit;
		
		// Bloque de código de filtrado y de paginación
		for(int i = start; i < end; i++) {
			if(q == null || events.get(i).getName().contains(q) || 
					events.get(i).getDescription().contains(q) || events.get(i).getOrganizer().contains(q) || 
					events.get(i).getLocation().contains(q)) {
				
				if(name == null || events.get(i).getName().equals(name)) {
					
					if(organizer == null || events.get(i).getOrganizer().equals(organizer)) {
						
						if(category == null || events.get(i).getCategory().equals(category)) {
							
							if(location == null || events.get(i).getLocation().equals(location)) {
								
								
								if(isEmpty == null
										|| (isEmpty && (events.get(i).getFQAs() == null || 
												events.get(i).getFQAs().size() == 0))
										|| (!isEmpty && (events.get(i).getFQAs() != null && 
												events.get(i).getFQAs().size() > 0))) {
									
									
									result.add(events.get(i));
								
								
									if(fields != null) {
										if(fields.equals("name")) {
											Event eventNew = new Event(events.get(i).getName());
											result.remove(events.get(i));
											result.add(eventNew);
										}
									}
								}
							}
						}
					}
						
					
				}
				
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
			
			else if(order.equals("organizer")) {
				Collections.sort(result, new ComparatorOrganizerEvent());
			}
			else if(order.equals("-organizer")) {
				Collections.sort(result, new ComparatorOrganizerEventReversed());
			}
			else {
				throw new BadRequestException("The order parameter must be 'category', '-category', "
						+ "'date', '-date', 'organizer', '-organizer'.");
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
	
	
	@GET
	@Path("/timeLeft/{id}")
	@Produces("text/plain")
	public String getTimeLeftEvent(@PathParam("id") String eventId) {
		Event event = repository.getEvent(eventId);
		String res="";
		
		if (event == null) {
			throw new NotFoundException("The event with id=" + eventId + " was not found");			
		}
		
		String dateEventStr = event.getDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateEvent = LocalDate.parse(dateEventStr, formatter);
		LocalDate dateNow = LocalDate.now();
		
		if(dateEvent.compareTo(dateNow) > 0) {
			long timeLeft = ChronoUnit.DAYS.between(LocalDate.now(), dateEvent);
			res = "Para el evento con id = " + eventId + " (" + event.getName() + ") faltan " 
					+ timeLeft + " días.";
		}
			
		else if(dateEvent.compareTo(dateNow) < 0) {
			res = "El evento con id = " + eventId + " (" + event.getName() + ") ya ha tenido "
					+ "lugar. Habrá que esperar a la próxima edición.";
		}
		else if(dateEvent.compareTo(dateNow) == 0) {
			res = "El evento con id = " + eventId + " (" + event.getName() + ") es hoy. No"
					+ "olvide coger sus entradas.";
		}
		
		return res;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addEvent(@Context UriInfo uriInfo, Event event) {
		if(event.getName() == null || "".equals(event.getName())) {
			throw new BadRequestException("The name of the event must not be null");
		}
		
		if (event.getFQAs()!=null)
			throw new BadRequestException("The fqas property is not editable.");
		
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
		
		if (event.getFQAs()!=null)
			throw new BadRequestException("The fqas property is not editable.");
		
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
	
	@POST	
	@Path("/{eventId}/{fqaId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addFQA(@Context UriInfo uriInfo,@PathParam("eventId") String eventId, @PathParam("fqaId") String fqaId)
	{				
		
		Event event = repository.getEvent(eventId);
		FQA fqa = repository.getFQA(fqaId);
		if (event == null)
			throw new NotFoundException("The event with id=" + eventId + " was not found");
		
		if (fqa == null)
			throw new NotFoundException("The FQA with id=" + fqaId + " was not found");
		
		if (event.getFQA(fqaId)!=null)
			throw new BadRequestException("The FQA is already included in the event.");
			
		repository.addFQA(eventId, fqaId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(eventId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(event);			
		return resp.build();
	}
	
	@DELETE
	@Path("/{eventId}/{fqaId}")
	public Response removeEvent(@PathParam("eventId") String eventId, @PathParam("fqaId") String fqaId) {
		Event event = repository.getEvent(eventId);
		FQA fqa = repository.getFQA(fqaId);
		
		if (event==null)
			throw new NotFoundException("The event with id=" + eventId + " was not found");
		
		if (fqa == null)
			throw new NotFoundException("The fqa with id=" + fqaId + " was not found");
		
		
		repository.removeFQA(eventId, fqaId);		
		
		return Response.noContent().build();
	}
	
}
