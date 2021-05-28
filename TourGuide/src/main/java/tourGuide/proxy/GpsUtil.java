package tourGuide.proxy;

import java.util.List;
import java.util.UUID;

import feign.RequestLine;

public interface GpsUtil {
	
	@RequestLine("GET /getAttractions")
	public List<Attraction> getAttractions();
	
	//TODO Externalize this
    public class Location {
        public final double longitude;
        public final double latitude;

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
    
  //TODO Externalize this
	public class Attraction extends Location {
	    public final String attractionName;
	    public final String city;
	    public final String state;
	    public final UUID attractionId;

	    public Attraction(String attractionName, String city, String state, double latitude, double longitude) {
	        super(latitude, longitude);
	        this.attractionName = attractionName;
	        this.city = city;
	        this.state = state;
	        this.attractionId = UUID.randomUUID();
	    }
	}


}
