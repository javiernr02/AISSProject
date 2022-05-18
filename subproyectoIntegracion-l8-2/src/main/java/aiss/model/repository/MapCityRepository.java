package aiss.model.repository;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

import aiss.model.City;
import aiss.model.Event;
import aiss.model.FQA;


public class MapCityRepository implements CityRepository{

	Map<String, City> cityMap;
	Map<String, Event> eventMap;
	Map<String,FQA> fqaMap;
	private static MapCityRepository instance=null;
	private int index=0;			
	
	
	public static MapCityRepository getInstance() {
		
		if (instance==null) {
			instance = new MapCityRepository();
			instance.init();
		}
		
		return instance;
	}
	
	public void init() {
		
		cityMap = new HashMap<String,City>();
		eventMap = new HashMap<String,Event>();
		
		// Create events
		Event europaLeagueFinal = new Event();
		europaLeagueFinal.setName("Europa League Final");
		europaLeagueFinal.setDescription("Silver competition of European international football after "
				+ "the Champions League");
		europaLeagueFinal.setOrganizer("Uefa");
		europaLeagueFinal.setCategory("Sports");
		europaLeagueFinal.setLocation("La Cartuja Stadium");
		europaLeagueFinal.setDate("18/05/2022");
		europaLeagueFinal.setPrice("40-200 euros");
		addEvent(europaLeagueFinal);
		
		Event sergioDalmaConcert = new Event();
		sergioDalmaConcert.setName("Sergio Dalma's concert");
		sergioDalmaConcert.setDescription("Singer Sergio Dalma presents his new project in Seville");
		sergioDalmaConcert.setOrganizer("Ayuntamiento de Sevilla");
		sergioDalmaConcert.setCategory("Music");
		sergioDalmaConcert.setLocation("Cartuja Center");
		sergioDalmaConcert.setDate("21/05/2022");
		sergioDalmaConcert.setPrice("30-50 euros");
		addEvent(sergioDalmaConcert);
		
		Event estopaConcert = new Event();
		estopaConcert.setName("Estopa's concert");
		estopaConcert.setDescription("Estopa returns to Seville to give a new concert");
		estopaConcert.setOrganizer("Ayuntamiento de Sevilla");
		estopaConcert.setCategory("Music");
		estopaConcert.setLocation("Cartuja Center");
		estopaConcert.setDate("22/05/2022");
		estopaConcert.setPrice("25 euros");
		addEvent(estopaConcert);
		
		Event artExhibition = new Event();
		artExhibition.setName("Art exhibition");
		artExhibition.setDescription("Guided Tour in the CaixaForum");
		artExhibition.setOrganizer("Ayuntamiento de Sevilla");
		artExhibition.setCategory("Art");
		artExhibition.setLocation("CaixaForum");
		artExhibition.setDate("27/05/2022");
		artExhibition.setPrice("5 euros");
		addEvent(artExhibition);
		
		Event eurovisionSongContest = new Event();
		eurovisionSongContest.setName("Eurovision Song Contest");
		eurovisionSongContest.setDescription("It is an annual television contest in which the "
				+ "participans present a song and fight for get the most votes from the public");
		eurovisionSongContest.setOrganizer("European Broadcasting Union");
		eurovisionSongContest.setCategory("Music");
		eurovisionSongContest.setLocation("Pasport Olimpico");
		eurovisionSongContest.setDate("14/05/2022");
		eurovisionSongContest.setPrice("1300-4000 euros");
		addEvent(eurovisionSongContest);
		
		Event ironMaidenConcert = new Event();
		ironMaidenConcert.setName("Iron Maiden Concert");
		ironMaidenConcert.setDescription("Iron Maiden returns to Spain this summer");
		ironMaidenConcert.setOrganizer("Delegación de arte de cataluña");
		ironMaidenConcert.setCategory("Music");
		ironMaidenConcert.setLocation("Estadi Olimpic Lluis Companys");
		ironMaidenConcert.setDate("29/06/2022");
		ironMaidenConcert.setPrice("100-200 euros");
		addEvent(ironMaidenConcert);
		
		Event mobileWorldCongress = new Event();
		mobileWorldCongress.setName("Mobile World Congress");
		mobileWorldCongress.setDescription("Great fair dedicated to mobile telephony and other "
				+ "technological devices that reviews the possibilities, advances and novelties "
				+ "offered by the sector");
		mobileWorldCongress.setOrganizer("GSM Association");
		mobileWorldCongress.setCategory("Technology");
		mobileWorldCongress.setLocation("Fira Gran Via");
		mobileWorldCongress.setDate("28/02/2022");
		mobileWorldCongress.setPrice("700-4200 euros");
		addEvent(mobileWorldCongress);
		
		Event rolandGarros = new Event();
		rolandGarros.setName("Roland Garros");
		rolandGarros.setDescription("Official tennis tournament that makes up the Grand Slam, "
				+ "played since its inauguration in 1891");
		rolandGarros.setOrganizer("French Tennis Federation");
		rolandGarros.setCategory("Sports");
		rolandGarros.setLocation("Stade Roland Garros");
		rolandGarros.setDate("22/05/2022");
		rolandGarros.setPrice("120-2750 euros");
		addEvent(rolandGarros);
		
		// Create cities
		City seville = new City();
		seville.setName("Seville");
		seville.setDescription("Seville is a city with a continental Mediterranean climate with a "
				+ "large number of attractive places");
		addCity(seville);
		
		City barcelona = new City();
		barcelona.setName("Barcelona");
		barcelona.setDescription("The capital of Catalonia in Spain, is known for its art and "
				+ "architecture. The fantastic Sagrada Familia church and other modernist landmarks "
				+ "designed by Antoni Gaudí adorn the city");
		addCity(barcelona);
		
		City turin = new City();
		turin.setName("Turin");
		turin.setDescription("City in Northern Italy and capital of the Piamonte region, known for "
				+ "its architecture and cuisine");
		addCity(turin);
		
		City paris = new City();
		paris.setName("Paris");
		paris.setDescription("Capital of France and one of the great European cities, it is for many "
				+ "the most romantic and popular tourist destination on the entire planet");
		addCity(paris);
		
		// Add event to cities
		addEvent(seville.getId(), europaLeagueFinal.getId());
		addEvent(seville.getId(), sergioDalmaConcert.getId());
		addEvent(seville.getId(), estopaConcert.getId());
		addEvent(seville.getId(), artExhibition.getId());
		
		addEvent(barcelona.getId(), ironMaidenConcert.getId());
		addEvent(barcelona.getId(), mobileWorldCongress.getId());
		
		addEvent(turin.getId(), eurovisionSongContest.getId());
		
		addEvent(paris.getId(), rolandGarros.getId());
	}
	
	// City related operations
	@Override
	public void addCity(City c) {
		String id = "c" + index++;	
		c.setId(id);
		cityMap.put(id,c);
	}
	
	@Override
	public Collection<City> getAllCities() {
			return cityMap.values();
	}

	@Override
	public City getCity(String id) {
		return cityMap.get(id);
	}
	
	@Override
	public void updateCity(City c) {
		cityMap.put(c.getId(),c);
	}

	@Override
	public void deleteCity(String id) {	
		cityMap.remove(id);
	}
	
	
	@Override
	public Collection<Event> getAll(String cityId) {
		return getCity(cityId).getEvents();
	}

	@Override
	public void addEvent(String cityId, String eventId) {
		City city = getCity(cityId);
		city.addEvent(eventMap.get(eventId));
	}

	
	@Override
	public void removeEvent(String cityId, String eventId) {
		getCity(cityId).deleteEvent(eventId);
	}

	
	// Event related operations
	@Override
	public void addEvent(Event e) {
		String id = "e" + index++;
		e.setId(id);
		eventMap.put(id, e);
	}
	
	@Override
	public Collection<Event> getAllEvents() {
			return eventMap.values();
	}

	@Override
	public Event getEvent(String eventId) {
		return eventMap.get(eventId);
	}

	@Override
	public void updateEvent(Event e) {
		Event event = eventMap.get(e.getId());
		event.setName(e.getName());
		event.setDescription(e.getDescription());
		event.setOrganizer(e.getOrganizer());
		event.setCategory(e.getCategory());
		event.setLocation(e.getLocation());
		event.setDate(e.getDate());
		event.setPrice(e.getPrice());
	}

	@Override
	public void deleteEvent(String eventId) {
		eventMap.remove(eventId);
	}

	@Override
	public void addFQA(FQA f) {
		String id = "f" + index++;
		f.setId(id);
		fqaMap.put(id, f);
	}

	@Override
	public Collection<FQA> getAllFQAs() {
		return fqaMap.values();
	}

	@Override
	public FQA getFQA(String fqaId) {
		return fqaMap.get(fqaId);
	}

	@Override
	public void deleteFQA(String fqaId) {
		fqaMap.remove(fqaId);
		
	}

	@Override
	public void addFQA(String eventId, String fqaId) {
		Event event = getEvent(eventId);
		event.addFQA(fqaMap.get(fqaId));
		
	}

	@Override
	public void removeFQA(String eventId, String fqaId) {
		getEvent(eventId).deleteFQA(fqaId);
		
	}
	
}
