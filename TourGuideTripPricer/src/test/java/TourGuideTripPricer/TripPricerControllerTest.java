package TourGuideTripPricer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import tripPricer.Provider;


public class TripPricerControllerTest {
	
	private TripPricerService service;
	private TripPricerController controller;
	
	@Before 
	public void init() {
		service = new TripPricerService();
		controller = new TripPricerController(service);
	}
	@Test
	public void getPriceTest_shouldReturnListOfProviders() {
		int numberOfAdults = 2;
		int numberOfChildren = 3;
		int tripDuration = 14;
		
		List<Provider> providers = controller.getPrice("", UUID.randomUUID(), numberOfAdults, 
					numberOfChildren, tripDuration, 10);
		
		assertEquals(5, providers.size());
		
	}
	
	@Test
	public void getProviderNameTest_shouldReturnARandoomProviderName() {
		String provider = controller.getProviderName("", 0);
		
		assertNotNull(provider);
	}

}
