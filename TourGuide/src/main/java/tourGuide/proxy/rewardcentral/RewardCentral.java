package tourGuide.proxy.rewardcentral;

import java.util.UUID;

import feign.Param;
import feign.RequestLine;

public interface RewardCentral {
	
	@RequestLine("GET /getAttractionRewardPoints?attractionId={attractionId}&userId={userId}")
	public String getAttractionRewardPoints(@Param("attractionId") UUID attractionId, @Param("userId") UUID userId);
	
}
