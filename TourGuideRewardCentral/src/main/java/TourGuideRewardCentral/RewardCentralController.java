package TourGuideRewardCentral;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class RewardCentralController {
	private Logger logger = LoggerFactory.getLogger(RewardCentralController.class);

	private RewardCentralService rewardCentralService;
	
	public RewardCentralController(@Autowired RewardCentralService p_rewardCentralService)
	{
		rewardCentralService = p_rewardCentralService;
	}
	
	/**
	 * Return an attraction reward points as Json
	 * @param attractionId
	 * @param userId
	 * @return String
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	@GetMapping("/getAttractionRewardPoints")
	public String getAttractionRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
		
		logger.info("GET /getAttractionRewardPoints with param; attractionId: {}, userId: {}", attractionId, userId);
		
		return new Gson().toJson(rewardCentralService.getAttractionRewardPoints(attractionId, userId));
	}

}
