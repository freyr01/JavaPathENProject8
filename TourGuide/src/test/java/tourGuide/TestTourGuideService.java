package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;

import feign.Feign;
import feign.gson.GsonDecoder;
import rewardCentral.RewardCentral;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.UserLastLocationDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.proxy.gpsutil.Attraction;
import tourGuide.proxy.gpsutil.GpsUtil;
import tourGuide.proxy.gpsutil.VisitedLocation;
import tourGuide.proxy.trippricer.Provider;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

public class TestTourGuideService {
	
	GpsUtil gpsUtil;
	
	@Before
	public void init() {
		gpsUtil = Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.gpsutil.GpsUtil.class, "http://localhost:8081");
	}

	@Test
	public void getUserLocation() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		tourGuideService.tracker.stopTracking();
		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}
	
	@Test
	public void addUser() {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		tourGuideService.tracker.stopTracking();
		
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}
	
	@Test
	public void getAllUsers() {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		List<User> allUsers = tourGuideService.getAllUsers();

		tourGuideService.tracker.stopTracking();
		
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}
	
	@Test
	public void trackUser() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(user.getUserId(), visitedLocation.userId);
	}
	
	@Test
	public void getAllUserVisitedLocationTest_shouldReturnListOfDto() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(10);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		tourGuideService.tracker.stopTracking();
		
		List<UserLastLocationDTO> usersLastVisitedLocation = tourGuideService.getAllUserLastVisitedLocation();
		
		assertEquals(10, usersLastVisitedLocation.size());
		
		usersLastVisitedLocation.forEach((userLastLocation) -> assertTrue(userLastLocation.getLastLocation() != null));
	}
	
	@Test
	public void getClosestAttractions_shouldReturn5ClosestAttractionSortedByProximity() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation userLocation = tourGuideService.trackUserLocation(user).get();
		
		Map<Double, Attraction> attractions = tourGuideService.getClosestAttractions(userLocation, 5);
		
		tourGuideService.tracker.stopTracking();
		
		// List assert
		assertEquals(5, attractions.size());
		
		// Sort assert
		Iterator<Double> it = attractions.keySet().iterator();
		while(it.hasNext()) {
			Double value = it.next();
			if(it.hasNext()) {
				assertTrue(value < it.next());
			}
		}
	}
	
	@Test
	public void mapClosestAttractionsToDTOTest_shouldReturnCorreclyFilledDTOlist() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		tourGuideService.trackUserLocation(user).get();
		
		List<AttractionDTO> attractions = tourGuideService.mapClosestAttractionsToDTO(user, 5);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, attractions.size());
		assertNotNull(attractions.get(0).getDistance());
		assertNotNull(attractions.get(0).getLocation());
		assertNotNull(attractions.get(0).getName());
		assertNotNull(attractions.get(0).getRewardPoint());
		assertNotNull(attractions.get(0).getUserLocation());
		
	}
	
	public void getTripDeals() {
		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<Provider> providers = tourGuideService.getTripDeals(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(10, providers.size());
	}
	
	
}
