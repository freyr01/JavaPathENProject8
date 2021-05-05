package tourGuide.dto;

public class UserPreferencesDTO {
	
	private String userName;
	private Integer attractionProximity;
	private String currency;
	private Double lowerPricePoint;
	private Double highPricePoint;
	private Integer tripDuration;
	private Integer ticketQuantity;
	private Integer numberOfAdults;
	private Integer numberOfChildren;
	
	public Integer getAttractionProximity() {
		return attractionProximity;
	}
	public void setAttractionProximity(Integer attractionProximity) {
		this.attractionProximity = attractionProximity;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getLowerPricePoint() {
		return lowerPricePoint;
	}
	public void setLowerPricePoint(Double lowerPricePoint) {
		this.lowerPricePoint = lowerPricePoint;
	}
	public Double getHighPricePoint() {
		return highPricePoint;
	}
	public void setHighPricePoint(Double highPricePoint) {
		this.highPricePoint = highPricePoint;
	}
	public Integer getTripDuration() {
		return tripDuration;
	}
	public void setTripDuration(Integer tripDuration) {
		this.tripDuration = tripDuration;
	}
	public Integer getTicketQuantity() {
		return ticketQuantity;
	}
	public void setTicketQuantity(Integer ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	public Integer getNumberOfAdults() {
		return numberOfAdults;
	}
	public void setNumberOfAdults(Integer numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}
	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	
}
