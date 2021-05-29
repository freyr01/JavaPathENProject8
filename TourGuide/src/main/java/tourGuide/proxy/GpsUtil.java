package tourGuide.proxy;

import java.util.List;
import java.util.UUID;

import feign.Param;
import feign.RequestLine;

public interface GpsUtil {
	
	@RequestLine("GET /getAttractions")
	public List<Attraction> getAttractions();
	
	@RequestLine("GET /getUserLocation?userId={userId}")
	public VisitedLocation getUserLocation(@Param("userId") UUID userId);

}
