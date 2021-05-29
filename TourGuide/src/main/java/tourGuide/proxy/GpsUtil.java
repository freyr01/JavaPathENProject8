package tourGuide.proxy;

import java.util.List;

import feign.RequestLine;

public interface GpsUtil {
	
	@RequestLine("GET /getAttractions")
	public List<Attraction> getAttractions();

}
