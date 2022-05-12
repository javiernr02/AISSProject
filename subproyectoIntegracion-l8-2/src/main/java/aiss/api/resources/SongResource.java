package aiss.api.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import aiss.api.resources.comparators.ComparatorAlbumSong;
import aiss.api.resources.comparators.ComparatorAlbumSongReversed;
import aiss.api.resources.comparators.ComparatorArtistSong;
import aiss.api.resources.comparators.ComparatorArtistSongReversed;
import aiss.api.resources.comparators.ComparatorYearSong;
import aiss.api.resources.comparators.ComparatorYearSongReversed;
import aiss.model.Song;
import aiss.model.repository.MapCityRepository;
import aiss.model.repository.CityRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



@Path("/songs")
public class SongResource {

	public static SongResource _instance=null;
	CityRepository repository;
	
	private SongResource(){
		repository=MapCityRepository.getInstance();
	}
	
	public static SongResource getInstance()
	{
		if(_instance==null)
			_instance=new SongResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public Collection<Song> getAll(@QueryParam("q") String q, @QueryParam("order") String order,
			@QueryParam("limit") Integer limit, @QueryParam("offset") Integer offset)
	{
		List<Song> result = new ArrayList<Song>();
		List<Song> songs = repository.getAllSongs().stream()
				.collect(Collectors.toList());
		int start = offset == null ? 0: offset - 1;
		int end = limit == null ? songs.size(): start + limit;
		
		// Bloque de código de filtrado y de paginación
		for(int i = start; i < end; i++) {
			if(songs.get(i).getTitle().contains(q) || songs.get(i).getAlbum().contains(q)
					|| songs.get(i).getArtist().contains(q)) {
				
				result.add(songs.get(i));
			}
		}
		
		/*for(Song song: repository.getAllSongs()) {
			if(q == null || song.getTitle().contains(q) || song.getAlbum().contains(q)
					|| song.getArtist().contains(q)) {
				
				result.add(song);
			}
		}*/
		
		// Bloque de código de ordenación
		if(order != null) {
			if(order.equals("album")) {
				Collections.sort(result, new ComparatorAlbumSong());
			}
			else if(order.equals("-album")) {
				Collections.sort(result, new ComparatorAlbumSongReversed());
			}
			else if(order.equals("artist")) {
				Collections.sort(result, new ComparatorArtistSong());
			}
			else if(order.equals("-artist")) {
				Collections.sort(result, new ComparatorArtistSongReversed());
			}
			else if(order.equals("year")) {
				Collections.sort(result, new ComparatorYearSong());
			}
			else if(order.equals("-year")) {
				Collections.sort(result, new ComparatorYearSongReversed());
			}
			else {
				throw new BadRequestException("The order parameter must be 'album', '-album', "
						+ "'artist', '-artist', 'year', '-year'.");
			}
		}
		
		// Bloque de código de paginación, no funciona no sé por qué
		/*if(limit != null) {
			result.stream().limit(limit).collect(Collectors.toList());
		}
		
		if(offset != null) {
			result.stream().skip(offset).collect(Collectors.toList());
		}*/
		
		return result;
		
	}
	
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Song get(@PathParam("id") String songId)
	{
		Song song = repository.getSong(songId);
		
		if(song == null) {
			throw new NotFoundException("The song with id=" + songId + " was not found");
		}
		
		return song;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addSong(@Context UriInfo uriInfo, Song song) {
		if(song.getTitle() == null || "".equals(song.getTitle())) {
			throw new BadRequestException("The name of the song must not be null");
		}
		
		repository.addSong(song);
		
		// Builds the response. Returns the song that has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(song.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(song);
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateSong(Song song) {
		Song oldSong = repository.getSong(song.getId());
		
		if(oldSong == null) {
			throw new NotFoundException("The song with id=" + song.getId() + " was not found");
		}
		
		// Update title
		if(song.getTitle() != null) {
			oldSong.setTitle(song.getTitle());
		}
		
		// Update artist
		if(song.getArtist() != null) {
			oldSong.setArtist(song.getArtist());
		}
		
		// Update album
		if(song.getAlbum() != null) {
			oldSong.setAlbum(song.getAlbum());
		}
		
		// Update year
		if(song.getYear() != null) {
			oldSong.setYear(song.getYear());
		}
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeSong(@PathParam("id") String songId) {
		Song song = repository.getSong(songId);  // Obtenemos la canción a eliminar
		
		if(song == null) {
			throw new NotFoundException("The song with id=" + songId + " was not found");
		}
		else {
			repository.deleteSong(songId);
		}
		
		return Response.noContent().build();
	}
	
}
