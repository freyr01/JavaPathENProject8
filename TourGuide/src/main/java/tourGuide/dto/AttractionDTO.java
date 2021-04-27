package tourGuide.dto;

import gpsUtil.location.Location;

public class AttractionDTO {
	
	private String name;
	private Location userLocation;
	private Location location;
	private Double distance;
	private int rewardPoint;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(Location userLocation) {
		this.userLocation = userLocation;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public int getRewardPoint() {
		return rewardPoint;
	}
	public void setRewardPoint(int rewardPoint) {
		this.rewardPoint = rewardPoint;
	}
	
	
}
