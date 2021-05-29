package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;

import feign.Feign;
import feign.gson.GsonDecoder;
import tourGuide.proxy.VisitedLocation;

public class TestProxy {
	
	@Test
	public void gpsUtilProxyGetAttractionTest_shouldReturnAttractionsListUsingProxy() {
		tourGuide.proxy.GpsUtil gpsUtilProxy = Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.GpsUtil.class, "http://localhost:8081");
		
		assertEquals(26, gpsUtilProxy.getAttractions().size());
	}
	
	@Test
	public void getUtilProxyGetUserLocationTest_shouldReturnUserLocationUsingProxy() {
		tourGuide.proxy.GpsUtil gpsUtilProxy = Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.GpsUtil.class, "http://localhost:8081");
        VisitedLocation visitedLocation = gpsUtilProxy.getUserLocation(UUID.randomUUID().toString());
        
        assertNotNull(visitedLocation.location);
	}

}
