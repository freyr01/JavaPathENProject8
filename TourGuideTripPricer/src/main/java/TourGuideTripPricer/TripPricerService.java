package TourGuideTripPricer;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TripPricerService {
	
	private TripPricer tripPricer = new TripPricer();

	/**
	 * Return a list of providers prices using tripPricer API
	 * @author Mathias Lauer
	 * @param apiKey
	 * @param attractionId
	 * @param adults
	 * @param children
	 * @param nightsStay
	 * @param rewardsPoints
	 * @return List<Provider> 
	 * 31 mai 2021
	 */
	public List<Provider> getPrice(String apiKey, UUID attractionId, int adults, int children, int nightsStay,
			int rewardsPoints) {

		return tripPricer.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
	}

	/**
	 * Return a random provider name using tripPricer API
	 * @param apiKey
	 * @param adults
	 * @return String
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	public String getProviderName(String apiKey, int adults) {
		
		return tripPricer.getProviderName(apiKey, adults);
	}

}
