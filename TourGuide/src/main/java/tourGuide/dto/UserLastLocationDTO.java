package tourGuide.dto;

import gpsUtil.location.Location;

public class UserLastLocationDTO {
	
	private String userId;
	private Location lastLocation;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Location getLastLocation() {
		return lastLocation;
	}
	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	} 

}
