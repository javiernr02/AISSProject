package aiss.api.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.api.resources.comparators.ComparatorAnswerFQA;
import aiss.api.resources.comparators.ComparatorAnswerFQAReversed;
import aiss.api.resources.comparators.ComparatorQuestionFQA;
import aiss.api.resources.comparators.ComparatorQuestionFQAReversed;
import aiss.model.FQA;
import aiss.model.repository.CityRepository;
import aiss.model.repository.MapCityRepository;

@Path("/fqas")	// Para acceder al recurso .../api/fqas
public class FQAResource {

	public static FQAResource _instance=null;
	CityRepository repository;
	
	private FQAResource(){
		repository=MapCityRepository.getInstance();
	}
	
	public static FQAResource getInstance()
	{
		if(_instance==null)
			_instance=new FQAResource();
		return _instance; 
	}
	
	
	@GET
	@Produces("application/json")
	public Collection<FQA> getAll(@QueryParam("q") String q, @QueryParam("order") String order,
			@QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<FQA> result = new ArrayList<FQA>();
		List<FQA> fqas = repository.getAllFQAs().stream()
				.collect(Collectors.toList());
		int start = offset == null ? 0: offset - 1;
		int end = limit == null ? fqas.size(): start + limit;
		
		// Bloque de código de filtrado y de paginación
		for(int i = start; i < end; i++) {
			if(offset == null || limit == null || fqas.get(i).getQuestion().contains(q) || fqas.get(i).getAnswer().contains(q)) {
				
				result.add(fqas.get(i));
			}
		}
		
		// Bloque de código de ordenación
		if(order != null) {
			if(order.equals("question")) {
				Collections.sort(result, new ComparatorQuestionFQA());
			}
			else if(order.equals("-question")) {
				Collections.sort(result, new ComparatorQuestionFQAReversed());
			}
			else if(order.equals("answer")) {
				Collections.sort(result, new ComparatorAnswerFQA());
			}
			else if(order.equals("-answer")) {
				Collections.sort(result, new ComparatorAnswerFQAReversed());
			}
			else {
				throw new BadRequestException("The order parameter must be 'question', '-question', "
						+ "'answer', '-answer' .");
			}
		}
		
		return result;
		
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public FQA get(@PathParam("id") String fqaId)
	{
		FQA fqa = repository.getFQA(fqaId);
		
		if(fqa == null) {
			throw new NotFoundException("The fqa with id=" + fqaId + " was not found");
		}
		
		return fqa;
	}
	
	@PUT
	@Consumes("application/json")
	public Response updateFQA(FQA fqa) {
		FQA oldFQA = repository.getFQA(fqa.getId());
		
		if(oldFQA == null) {
			throw new NotFoundException("The fqa with id=" + fqa.getId() + " was not found");
		}
		
		// Update question
		if(fqa.getQuestion() != null) {
			oldFQA.setQuestion(fqa.getQuestion());
		}
		
		// Update answer
		if(fqa.getAnswer() != null) {
			oldFQA.setAnswer(fqa.getAnswer());
		}	
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeFQA(@PathParam("id") String fqaId) {
		FQA fqa = repository.getFQA(fqaId);  // Obtenemos la fqa a eliminar
		
		if(fqa == null) {
			throw new NotFoundException("The fqa with id=" + fqaId + " was not found");
		}
		else {
			repository.deleteFQA(fqaId);
		}
		
		return Response.noContent().build();
	}
	
	
	
}
