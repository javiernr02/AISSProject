package aiss.model;

import java.util.Date;

public class Event {

	
	private String id;
	private String name;
	private String description;
	private String organizer;
	private Date date;
	private Integer maximumCapacity;
	private Double price;
	private String category;
	
	public Event(String name, String description, String organizer, Date date, Integer maximumCapacity, Double price,
			String category) {
		this.name = name;
		this.description = description;
		this.organizer = organizer;
		this.date = date;
		this.maximumCapacity = maximumCapacity;
		this.price = price;
		this.category = category;
	}

	public Event(String id, String name, String description, String organizer, Date date, Integer maximumCapacity,
			Double price, String category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.organizer = organizer;
		this.date = date;
		this.maximumCapacity = maximumCapacity;
		this.price = price;
		this.category = category;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getMaximumCapacity() {
		return maximumCapacity;
	}

	public void setMaximumCapacity(Integer maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
	
}
