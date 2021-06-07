package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@Ignore
//Can only be used when TripPricer microservice is UP
public class TestProxyTripPricer {
	
	private tourGuide.proxy.trippricer.TripPricer tripPricer;
	
	@Before
	public void init() {
		tripPricer = Feign.builder()
				.encoder(new GsonEncoder())
				.decoder(new GsonDecoder())
				.target(tourGuide.proxy.trippricer.TripPricer.class, "http://localhost:8082");
	}
	
	@Test
	public void tripPricerProxyGetPrice_shouldReturnProviderListUsingProxy() {

		List<tourGuide.proxy.trippricer.Provider> providers = tripPricer.getPrice("", UUID.randomUUID(), 2, 3, 14, 10);
		
		assertEquals(5, providers.size());
	}
	
	@Test
	public void tripPricerProxyGetProvider_shouldReturnRandoomProviderUsingProxy() {
		String provider = tripPricer.getProviderName("", 0);
		
		assertNotNull(provider);
	}

}
