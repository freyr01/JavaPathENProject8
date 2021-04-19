package tourGuide;

import org.junit.Test;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.user.User;
import tourGuide.user.UserReward;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.UUID;


public class TestUser {
	
	@Test
	public void userAddRewardTest_shouldCorreclyAddRewardsWithoutDoublon() {
		User user = new User(UUID.randomUUID(), "test", "0000", "test@test.com");
		
		Attraction attraction = new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D);
		VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), attraction, new Date());
		UserReward reward = new UserReward(visitedLocation, attraction);
		
		Attraction attraction2 = new Attraction("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D);
		VisitedLocation visitedLocation2 = new VisitedLocation(user.getUserId(), attraction2, new Date());
		UserReward reward2 = new UserReward(visitedLocation2, attraction2);
		
		user.addUserReward(reward);
		
		assertEquals(1, user.getUserRewards().size());
		
		user.addUserReward(reward);
		
		assertEquals(1, user.getUserRewards().size());
		
		user.addUserReward(reward2);
		
		assertEquals(2, user.getUserRewards().size());
	}

}
