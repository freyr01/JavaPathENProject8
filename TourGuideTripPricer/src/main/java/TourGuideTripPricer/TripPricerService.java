package TourGuideTripPricer;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TripPricerService {
	
	private TripPricer tripPricer = new TripPricer();

	public List<Provider> getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay,
			int rewardsPoints) {

		return tripPricer.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
	}

	public String getProviderName(String apiKey, int adults) {
		
		return tripPricer.getProviderName(apiKey, adults);
	}

}
