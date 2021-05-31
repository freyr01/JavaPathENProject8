package TourGuideTripPricer;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import tripPricer.Provider;
@RestController
public class TripPricerController {
	
	private Logger logger = LoggerFactory.getLogger(TripPricerController.class);

	private TripPricerService tripPricerService;
	
	// Autowired constructor
	public TripPricerController(@Autowired TripPricerService p_tripPricerService) {
		tripPricerService = p_tripPricerService;
	}
	
	/**
	 * Return a list of providers prices as Json
	 * @param apiKey
	 * @param attractionId
	 * @param adults
	 * @param children
	 * @param nightsStay
	 * @param rewardsPoints
	 * @return List<Provider> 
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	@GetMapping("/getPrice")
	public List<Provider> getPrice(@RequestParam String apiKey, @RequestParam UUID attractionId,
			@RequestParam int adults, @RequestParam int children,
			@RequestParam int nightsStay, @RequestParam int rewardsPoints) {
		
		logger.info("GET /getPrice with param: apiKey: {}, attractionId: {}, adults: {}, children: {}, nightsStay:{}, rewardsPoints: {}",
				apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
		
		return tripPricerService.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
	}
	
	/**
	 * Return a random provider name as Json
	 * @param apiKey
	 * @param adults
	 * @return String provider name
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	@GetMapping("/getProviderName")
	public String getProviderName(@RequestParam String apiKey, @RequestParam int adults) {
		
		logger.info("GET /getPrice with param: apiKey:{}, adults: {}", apiKey, adults);
		
		String provider = tripPricerService.getProviderName(apiKey, adults);
		
		return new Gson().toJson(provider);
	}

}
