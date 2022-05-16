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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorNameCity;
import aiss.api.resources.comparators.ComparatorNameCityReversed;
import aiss.model.repository.MapCityRepository;
import aiss.model.City;
import aiss.model.Event;
import aiss.model.repository.CityRepository;





@Path("/cities")	// Para acceder al recurso .../api/cities
public class CityResource {
	
	/* Singleton */
	private static CityResource _instance=null;
	CityRepository repository;
	
	private CityResource() {
		repository=MapCityRepository.getInstance();

	}
	
	public static CityResource getInstance()
	{
		if(_instance==null)
				_instance=new CityResource();
		return _instance;
	}
	

	@GET
	@Produces("application/json")
	public Collection<City> getAll(@QueryParam("order") String order,
			@QueryParam("isEmpty") Boolean isEmpty, @QueryParam("name") String name)
	{
		List<City> result = new ArrayList<City>();
		
		// Primero bloque de código de filtrado
		for(City city: repository.getAllCities()) {
			// Si no se usa el filtro o si se cumple el filtro por nombre, miramos filtro de isEmpty
			if(name == null || city.getName().equals(name)) {
				// Si no se usa el filtro isEmpty, o está vacío y por lo tanto canciones a null o su 
				// tamaño es cero, o no está vacío y por lo tanto canciones no a null y su tamaño
				// mayor que cero
				if(isEmpty == null 
						|| (isEmpty && (city.getEvents() == null || city.getEvents().size() == 0))
						|| (!isEmpty && (city.getEvents() != null && city.getEvents().size() > 0))) {
					
					result.add(city);
				}
			}
		}
		
		// Bloque de código de ordenación
		if(order != null) {
			if(order.equals("name")) {
				Collections.sort(result, new ComparatorNameCity());
			}
			else if(order.equals("-name")) {
				Collections.sort(result, new ComparatorNameCityReversed());
			}
			else {
				throw new BadRequestException("The order parameter must be 'name' or '-name'.");
			}
		}
		
		return result;
			
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public City get(@PathParam("id") String id)
	{
		City list = repository.getCity(id);
		
		if (list == null) {
			throw new NotFoundException("The city with id="+ id +" was not found");	 // Se envía un error 404		
		}
		
		return list;
	}
	
	
	@GET
	@Path("/totalPrice/{id}")
	@Produces("text/plain")
	public String getTotalPriceCity(@PathParam("id") String id) {
		List<Event> ls = repository.getAll(id).stream()
				.collect(Collectors.toList());
		Integer sumMin = 0;
		Integer sumMax = 0;
		String res = "";
		
		
		for(int i=0; i<ls.size(); i++) {
			String[] tokens = ls.get(i).getPrice().split(" ");
			
			if(ls.get(i).getPrice().contains("-")) {
				String[] nums = tokens[0].split("-");
				Integer n1 = Integer.parseInt(nums[0].trim());
				Integer n2 = Integer.parseInt(nums[1].trim());
				sumMin = sumMin + n1;
				sumMax = sumMax + n2;		
			}
			else {
				Integer n = Integer.parseInt(tokens[0].trim());
				sumMin = sumMin + n;
				sumMax = sumMax + n;
			}
			
		}
		
		res = "Para poder ir a todos los eventos de la ciudad con id = " + id + " (" +
				repository.getCity(id).getName() + ") tendrá que gastarse como mínimo " + sumMin +
				" euros y como máximo " + sumMax + " euros";
		
		return res;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addCity(@Context UriInfo uriInfo, City city) {
		// @Context permite inyectar determinados objetos con info sobre el determinado contexto
		if (city.getName() == null || "".equals(city.getName()))
			throw new BadRequestException("The name of the city must not be null");
		
		if (city.getEvents()!=null)
			throw new BadRequestException("The events property is not editable.");

		repository.addCity(city);

		// Builds the response. Returns the playlist the has just been added.
		// Construimos la URI para indicar donde está ubicada la nueva playlist que acabamos de crear
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(city.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(city);			
		return resp.build();
	}

	
	@PUT
	@Consumes("application/json")
	public Response updateCity(City city) {
		City oldcity = repository.getCity(city.getId());
		if (oldcity == null) {
			throw new NotFoundException("The city with id="+ city.getId() +" was not found");			
		}
		
		if (city.getEvents()!=null)
			throw new BadRequestException("The event property is not editable.");
		
		// Update name
		if (city.getName()!=null)
			oldcity.setName(city.getName());
		
		// Update description
		if (city.getDescription()!=null)
			oldcity.setDescription(city.getDescription());
		
		return Response.noContent().build();  // Mandamos respuesta 204: Okay, sin contenido
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeCity(@PathParam("id") String id) {
		City toberemoved=repository.getCity(id);
		if (toberemoved == null)
			throw new NotFoundException("The city with id="+ id +" was not found");
		else
			repository.deleteCity(id);
		
		return Response.noContent().build();
	}
	
	
	@POST	
	@Path("/{cityId}/{eventId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addEvent(@Context UriInfo uriInfo,@PathParam("cityId") String cityId, @PathParam("eventId") String eventId)
	{				
		
		City city = repository.getCity(cityId);
		Event event = repository.getEvent(eventId);
		
		if (city==null)
			throw new NotFoundException("The city with id=" + cityId + " was not found");
		
		if (event == null)
			throw new NotFoundException("The event with id=" + eventId + " was not found");
		
		if (city.getEvent(eventId)!=null)
			throw new BadRequestException("The event is already included in the city.");
			
		repository.addEvent(cityId, eventId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(cityId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(city);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{cityId}/{eventId}")
	public Response removeEvent(@PathParam("cityId") String cityId, @PathParam("eventId") String eventId) {
		City city = repository.getCity(cityId);
		Event event = repository.getEvent(eventId);
		
		if (city==null)
			throw new NotFoundException("The city with id=" + cityId + " was not found");
		
		if (event == null)
			throw new NotFoundException("The event with id=" + eventId + " was not found");
		
		
		repository.removeEvent(cityId, eventId);		
		
		return Response.noContent().build();
	}
}