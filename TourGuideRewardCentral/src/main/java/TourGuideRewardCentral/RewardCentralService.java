package TourGuideRewardCentral;

import java.util.UUID;

import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

@Service
public class RewardCentralService {

	private RewardCentral rewardCentral = new RewardCentral();
	
	/**
	 * Return an attraction reward points using RewardCentral library
	 * @param attractionId
	 * @param userId
	 * @return int
	 * @author Mathias Lauer
	 * 31 mai 2021
	 */
	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
		
		return rewardCentral.getAttractionRewardPoints(attractionId, userId);
	}

}
