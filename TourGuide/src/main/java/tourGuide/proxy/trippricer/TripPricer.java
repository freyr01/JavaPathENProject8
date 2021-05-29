package tourGuide.proxy.trippricer;

import java.util.List;
import java.util.UUID;

import feign.Param;
import feign.RequestLine;

public interface TripPricer {
	
	@RequestLine("GET /getPrice?apiKey={apiKey}&attractionId={attractionId}&adults={adults}&children={children}&nightsStay={nightsStay}&rewardsPoints={rewardsPoints}")
	public List<Provider> getPrice(@Param("apiKey") String apiKey, @Param("attractionId") UUID attractionId,
			@Param("adults") int adults, @Param("children") int children,
			@Param("nightsStay") int nightsStay, @Param("rewardsPoints") int rewardsPoints);
	
	@RequestLine("GET /getProviderName?apiKey={apiKey}&adults={adults}")
	public String getProviderName(@Param("apiKey") String apiKey, @Param("adults") int adults);

}
