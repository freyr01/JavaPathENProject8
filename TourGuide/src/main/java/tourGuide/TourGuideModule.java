package tourGuide;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.gson.GsonDecoder;
import tourGuide.proxy.gpsutil.GpsUtil;
import tourGuide.proxy.rewardcentral.RewardCentral;
import tourGuide.proxy.trippricer.TripPricer;
import tourGuide.service.RewardsService;

@Configuration
public class TourGuideModule {
	
	@Value("${gpsutil.url}")
	private String gpsUtilUrl;
	
	@Value("${gpsutil.port}")
	private String gpsUtilPort;
	
	@Value("${rewardcentral.url}")
	private String rewardCentralUrl;
	
	@Value("${rewardcentral.port}")
	private String rewardCentralPort;
	
	@Value("${trippricer.url}")
	private String tripPricerUrl;
	
	@Value("${trippricer.port}")
	private String tripPricerPort;
	
	@Bean
	public GpsUtil getGpsUtil() {
		return Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.gpsutil.GpsUtil.class, gpsUtilUrl + ":" + gpsUtilPort);
	}
	
	@Bean
	public RewardsService getRewardsService() {
		return new RewardsService(getGpsUtil(), getRewardCentral());
	}
	
	@Bean
	public RewardCentral getRewardCentral() {
		return Feign.builder().decoder(new GsonDecoder()).target(tourGuide.proxy.rewardcentral.RewardCentral.class, rewardCentralUrl + ":" + rewardCentralPort);
	}
	
	@Bean
	public TripPricer getTripPricer() {
		return Feign.builder()
				.decoder(new GsonDecoder())
				.target(tourGuide.proxy.trippricer.TripPricer.class, tripPricerUrl + ":" + tripPricerPort);
		
	}
	
}
