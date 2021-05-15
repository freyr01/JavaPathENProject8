package tourGuide.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.dto.AttractionDTO;
import tourGuide.dto.UserLastLocationDTO;
import tourGuide.helper.InternalTestHelper;
import tourGuide.tracker.Tracker;
import tourGuide.user.User;
import tourGuide.user.UserReward;
import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final GpsUtil gpsUtil;
	private final RewardsService rewardsService;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;
	private final ExecutorService executorService = Executors.newFixedThreadPool(10000);
	
	public TourGuideService(GpsUtil gpsUtil, RewardsService rewardsService) {
		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;
		
		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}
	
	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}
	
	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = null;
		try {
			if(user.getVisitedLocations().size() > 0) {
				visitedLocation = user.getLastVisitedLocation();
				logger.debug("Call getUserLocation for user: " + user + " returning last known location");
			} 
			else {
				logger.debug("Call getUserLocation for user: " + user + " getting current location...");
				visitedLocation = trackUserLocation(user).get();
				
			}
		} catch (InterruptedException | ExecutionException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return visitedLocation;
	}
	
	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}
	
	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}
	
	public void addUser(User user) {
		if(!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}
	
	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	
	public Future<VisitedLocation> trackUserLocation(User user) {
		logger.debug("Creating parallel task getUserLocation for user: " + user);
		Future<VisitedLocation> future = executorService.submit(() -> {
			VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
			logger.debug("Getting userLocation: " + visitedLocation);
			user.addToVisitedLocations(visitedLocation);
			rewardsService.calculateRewards(user);
			return visitedLocation;
		});

		return future;
	}
	
	public ExecutorService getExecutorService() {
		return executorService;
	}
	
	/**
	 * Get the closest attractions from actual user location
	 * @param userLocation actual user location
	 * @param amount Number of attraction to retrieve
	 * @return SortedMap<Double, Attraction> a sorted map of attraction, first is the closest
	 * @author Mathias Lauer
	 * 27 avr. 2021
	 */
	public SortedMap<Double, Attraction> getClosestAttractions(VisitedLocation userLocation, int amount) {
		SortedMap<Double, Attraction> map = new TreeMap<Double, Attraction>();
		SortedMap<Double, Attraction> attractions = new TreeMap<Double, Attraction>();

		//Create a sorted map of all attractions where the key is the distance between the user and the attraction
		for(Attraction attraction : gpsUtil.getAttractions()) {
			Double userDistance = rewardsService.getDistance(userLocation.location, new Location(attraction.latitude, attraction.longitude));
			map.put(userDistance, attraction);
		}
		
		//Extract the amount of attraction we want
		Iterator<Double> it = map.keySet().iterator();
		int i = 0;
		while(i < amount && it.hasNext()) {
			Double distance = it.next();
			Attraction attraction = map.get(distance);
			attractions.put(distance, attraction);
			i++;
		}

		return attractions;
	}
	
	
	/**
	 * Create a list of attraction DTO representing <amount> attractions closest of the user based on user last location registered
	 * @param user
	 * @param amount Number of closest attraction
	 * @author Mathias Lauer
	 * 27 avr. 2021
	 */
	public List<AttractionDTO> mapClosestAttractionsToDTO(User user, int amount)
	{
		SortedMap<Double, Attraction> attractions = getClosestAttractions(user.getLastVisitedLocation(), amount);
		List<AttractionDTO> attractionsDtoList = new ArrayList<AttractionDTO>();
		attractions.forEach((distance, attraction) -> {
			AttractionDTO attractionDto = new AttractionDTO();
			
			attractionDto.setName(attraction.attractionName);
			attractionDto.setLocation(new Location(attraction.latitude, attraction.longitude));
			attractionDto.setUserLocation(user.getLastVisitedLocation().location);
			attractionDto.setDistance(distance);
			attractionDto.setRewardPoint(rewardsService.getRewardPoints(attraction, user));
			
			attractionsDtoList.add(attractionDto);
		});
		
		return attractionsDtoList;
	}
	
	/**
	 * Create a list of all user last visited location
	 * @return List<UserLastLocationDTO>
	 * @author Mathias Lauer
	 * 10 mai 2021
	 */
	public List<UserLastLocationDTO> getAllUserLastVisitedLocation(){
    	List<UserLastLocationDTO> listLocation = new ArrayList<UserLastLocationDTO>();
    	for(User user : getAllUsers()) {
    		UserLastLocationDTO lastLocation = new UserLastLocationDTO();
    		lastLocation.setUserId(user.getUserId().toString());
    		lastLocation.setLastLocation(user.getLastVisitedLocation().location);
    		listLocation.add(lastLocation);
    	}
    	
    	return listLocation;
	}
	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}
	
	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();
	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);
			
			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}
	
	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i-> {
			user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}
	
	private double generateRandomLongitude() {
		double leftLimit = -180;
	    double rightLimit = 180;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	    return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
	
}
