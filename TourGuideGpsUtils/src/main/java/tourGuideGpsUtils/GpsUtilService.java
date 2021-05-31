package tourGuideGpsUtils;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.*;

@Service
public class GpsUtilService {
	
	private GpsUtil gpsUtil = new GpsUtil();
	
	/**
	 * Get all attractions registered
	 * @return List<Attraction>
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	public List<Attraction> getAttractions() {
		return gpsUtil.getAttractions();
	}
	
	/**
	 * Get an user last visited location
	 * @param userId
	 * @return VisitedLocation
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	public VisitedLocation getUserLocation(UUID userId) {
		return gpsUtil.getUserLocation(userId);
	}

}
