package tourGuideGpsUtils;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@RestController
public class GpsUtilController {
	
	
	GpsUtilService gpsUtilService;
	public GpsUtilController(@Autowired GpsUtilService p_gpsUtilService ) {
		gpsUtilService = p_gpsUtilService;
	}
	
	@GetMapping(value = "/getUserLocation")
	public VisitedLocation getUserLocation(@RequestParam UUID userId) {
		return gpsUtilService.getUserLocation(userId);
	}
	
	@GetMapping(value="/getAttractions")
	public List<Attraction> getAttractions(){
		return gpsUtilService.getAttractions();
	}

}
