package TourGuideRewardCentral;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;

public class RewardCentralControllerTest {
	
	private RewardCentralController rewardCentralController;
	private RewardCentralService rewardCentralService;
	
	@Before
	public void init() {
		rewardCentralService = new RewardCentralService();
		rewardCentralController = new RewardCentralController(rewardCentralService);
	}
	
	@Test
	public void getAttractionRewardPointsTest_shouldReturnAttractionRewardPoint() {
		assertNotNull(rewardCentralController.getAttractionRewardPoints(UUID.randomUUID(),UUID.randomUUID()));
	}

}
