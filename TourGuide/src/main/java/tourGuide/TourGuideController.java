package tourGuide;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;
import java.util.ArrayList;

import gpsUtil.location.VisitedLocation;
import tourGuide.dto.UserLastLocationDTO;
import tourGuide.dto.UserPreferencesDTO;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserPreferences;
import tripPricer.Provider;

@RestController
public class TourGuideController {
	
	private Logger logger = LoggerFactory.getLogger(TourGuideController.class);

	@Autowired
	TourGuideService tourGuideService;
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }
    
    @RequestMapping("/getLocation") 
    public String getLocation(@RequestParam String userName) {
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.location);
    }

    @RequestMapping("/getNearbyAttractions") 
    /**
     * Return 5 closest attractions based on last visited location of the user as Json
     * @param userName
     * @author Mathias Lauer
     * 10 mai 2021
     */
    public String getNearbyAttractions(@RequestParam String userName) {
    	return JsonStream.serialize(tourGuideService.getClosestAttractionsDTO(getUser(userName), 5));
    }
    
    @RequestMapping("/getRewards") 
    public String getRewards(@RequestParam String userName) {
    	return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    }
    
    @RequestMapping("/getAllCurrentLocations")
    /**
     * Return all users last location registed as Json
     * @author Mathias Lauer
     * 10 mai 2021
     */
    public String getAllCurrentLocations() {
    	return JsonStream.serialize(tourGuideService.getAllUserLastVisitedLocation());
    }
    
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
    	return JsonStream.serialize(providers);
    }
    
    private User getUser(String userName) {
    	return tourGuideService.getUser(userName);
    }
    
    //CRUD implementation for userPreferences management
    @GetMapping("/preferences/{userName}")
    /**
     * Return the user preferences as Json object as body of the response
     * @param userName
     * @return UserPreferencesDTO object
     * @author Mathias Lauer
     * 4 mai 2021
     */
    public UserPreferencesDTO getUserPreferences(@PathVariable("userName") String userName) {
    	User user = getUser(userName);
    	if(user == null) {
    		logger.error("Requested user not found: " + userName);
    		return null;
    	}
    	
    	UserPreferencesDTO dto = new UserPreferencesDTO();
    	user.getUserPreferences().mapTo(dto, userName);
    	
    	return dto;
    }
    
    @PutMapping("/preferences")
    /**
     * Deserialize an UserPreferencesDTO to update user preferences
     * @param userName
     * @param userPreferencesDTO in Json format as body of the request 
     * @return UserPreferencesDTO updated if successful
     * @author Mathias Lauer
     * 4 mai 2021
     */
    public UserPreferencesDTO putUserPreferences(@RequestBody UserPreferencesDTO userPreferencesDTO) {
    	User user = getUser(userPreferencesDTO.getUserName());
    	if(user == null) {
    		logger.error("Requested user not found: " + userPreferencesDTO.getUserName());
    		return null;
    	}
    	
    	user.getUserPreferences().mapFrom(userPreferencesDTO);
    	
    	return getUserPreferences(userPreferencesDTO.getUserName());
    }
   

}