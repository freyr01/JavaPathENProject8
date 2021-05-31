package tourGuideGpsUtils;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@RestController
public class GpsUtilController {
	
	private Logger logger = LoggerFactory.getLogger(GpsUtilController.class);

	
	private GpsUtilService gpsUtilService;
	public GpsUtilController(@Autowired GpsUtilService p_gpsUtilService ) {
		gpsUtilService = p_gpsUtilService;
	}
	
	/**
	 * Return an user location as Json
	 * @param userId
	 * @return VistedLocation
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	@GetMapping(value = "/getUserLocation")
	public VisitedLocation getUserLocation(@RequestParam UUID userId) {
		logger.info("GET /getUserLocation with param: userId: {}", userId);
		return gpsUtilService.getUserLocation(userId);
	}
	
	/**
	 * Return all attractions registered
	 * @return List<Attraction>
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	@GetMapping(value="/getAttractions")
	public List<Attraction> getAttractions(){
		logger.info("GET /getAttractions");
		return gpsUtilService.getAttractions();
	}

}
