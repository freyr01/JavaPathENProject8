package tourGuide;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import feign.Feign;
import feign.gson.GsonDecoder;

public class TestProxy {
	
	@Test
	public void gpsUtilProxyGetAttractionTest_shouldReturnAttractionsListUsingProxy() {
		tourGuide.proxy.GpsUtil gpsUtilProxy = Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.GpsUtil.class, "http://localhost:8081");
		
		assertEquals(26, gpsUtilProxy.getAttractions().size());
	}

}
