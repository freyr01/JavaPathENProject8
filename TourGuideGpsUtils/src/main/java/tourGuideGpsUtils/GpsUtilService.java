package tourGuideGpsUtils;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.*;

@Service
public class GpsUtilService {
	
	private GpsUtil gpsUtil = new GpsUtil();
	
	public List<Attraction> getAttractions() {
		return gpsUtil.getAttractions();
	}
	
	public VisitedLocation getUserLocation(UUID userId) {
		return gpsUtil.getUserLocation(userId);
	}

}
