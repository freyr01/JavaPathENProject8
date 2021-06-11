package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

import feign.Feign;
import feign.gson.GsonDecoder;
import tourGuide.proxy.gpsutil.VisitedLocation;

//Can only be used when GpsUtil microservice is UP
public class TestProxyGpsUtil {
	
	@Test
	public void gpsUtilProxyGetAttractionTest_shouldReturnAttractionsListUsingProxy() {
		tourGuide.proxy.gpsutil.GpsUtil gpsUtilProxy = Feign.builder()
				.decoder(new GsonDecoder())
				.target(tourGuide.proxy.gpsutil.GpsUtil.class, "http://localhost:8081");
		
		assertEquals(26, gpsUtilProxy.getAttractions().size());
	}
	
	@Test
	public void getUtilProxyGetUserLocationTest_shouldReturnUserLocationUsingProxy() {
		tourGuide.proxy.gpsutil.GpsUtil gpsUtilProxy = Feign.builder()
				.decoder(new GsonDecoder())
				.target(tourGuide.proxy.gpsutil.GpsUtil.class, "http://localhost:8081");
		
        VisitedLocation visitedLocation = gpsUtilProxy.getUserLocation(UUID.randomUUID());
        
        assertNotNull(visitedLocation.location);
	}
	


}
