package tourGuide;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.javamoney.moneta.Money;
import org.junit.Test;

import tourGuide.user.User;
import tourGuide.user.UserPreferences;
import tripPricer.Provider;
import tripPricer.TripPricer;

public class TestTripPricer {
	
	@Test
	public void userPreferenceGetSetTest_shouldCorreclySetAndGetAttrs() {
		UserPreferences prefs = new UserPreferences();
		prefs.setAttractionProximity(10);
		prefs.setHighPricePoint(Money.of(BigDecimal.valueOf(100d), "EUR"));
		prefs.setLowerPricePoint(Money.of(BigDecimal.valueOf(20.05), "EUR"));
		prefs.setNumberOfAdults(2);
		prefs.setNumberOfChildren(3);
		prefs.setTicketQuantity(5);
		prefs.setTripDuration(14);
		
		assertEquals(10, prefs.getAttractionProximity());
		assertEquals(Money.of(BigDecimal.valueOf(100d), "EUR"), prefs.getHighPricePoint());
		assertEquals(Money.of(BigDecimal.valueOf(20.05), "EUR"), prefs.getLowerPricePoint());
		assertEquals(2, prefs.getNumberOfAdults());
		assertEquals(3, prefs.getNumberOfChildren());
		assertEquals(5, prefs.getTicketQuantity());
		assertEquals(14, prefs.getTripDuration());
		
	}
	
	@Test
	public void tripPricerTest() {
		final TripPricer tripPricer = new TripPricer();
		
		UserPreferences prefs = new UserPreferences();
		prefs.setAttractionProximity(10);
		prefs.setHighPricePoint(Money.of(BigDecimal.valueOf(100d), "EUR"));
		prefs.setLowerPricePoint(Money.of(BigDecimal.valueOf(20.05), "EUR"));
		prefs.setNumberOfAdults(2);
		prefs.setNumberOfChildren(3);
		prefs.setTicketQuantity(5);
		prefs.setTripDuration(14);
		
		User user = new User(UUID.randomUUID(), "test", "0000", "test@test.com");
		user.setUserPreferences(prefs);
		
		List<Provider> providers = tripPricer.getPrice("", user.getUserId(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), 10);
		
		for(Provider provider : providers) {
			System.out.println(provider.name + ": " + provider.price);
		}
	}

}
