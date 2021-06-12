package tourGuide;

import static org.junit.Assert.assertTrue;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import feign.Feign;
import feign.gson.GsonDecoder;
import tourGuide.helper.InternalTestHelper;
import tourGuide.proxy.gpsutil.GpsUtil;
import tourGuide.proxy.gpsutil.VisitedLocation;
import tourGuide.proxy.rewardcentral.RewardCentral;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

public class TestExecutorService {
	
	private GpsUtil gpsUtil;
	private RewardCentral rewardCentral;
	
	@Before
	public void init() {
		gpsUtil = Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.gpsutil.GpsUtil.class, TestProperties.gpsUtilSocket);
		rewardCentral = Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.rewardcentral.RewardCentral.class, TestProperties.rewardCentralSocket);
	}

	@Test
	public void testSubmitTrackUserLocationTask_shouldTerminateTheTaskBeforeElapsedTime() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtil, rewardCentral);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Future<VisitedLocation> trackUserTask = tourGuideService.trackUserLocation(user);
		
		tourGuideService.tracker.stopTracking();
		
		Thread.sleep(5000);
		assertTrue(trackUserTask.isDone());
	}

}
