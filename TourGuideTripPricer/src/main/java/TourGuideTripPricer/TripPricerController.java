package TourGuideTripPricer;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tripPricer.Provider;
@RestController
public class TripPricerController {

	private TripPricerService tripPricerService;
	
	public TripPricerController(@Autowired TripPricerService p_tripPricerService) {
		tripPricerService = p_tripPricerService;
	}
	
	@GetMapping("/getPrice")
	public List<Provider> getPrice(@RequestParam String apiKey, @RequestParam UUID attractionId,
			@RequestParam int adults, @RequestParam int children,
			@RequestParam int nightsStay, @RequestParam int rewardsPoints) {
		
		return tripPricerService.getPrice(apiKey, attractionId, adults, children, nightsStay, rewardsPoints);
	}
	
	@GetMapping("/getProviderName")
	public String getProviderName(@RequestParam String apiKey, @RequestParam int adults) {
		
		return tripPricerService.getProviderName(apiKey, adults);
	}

}
