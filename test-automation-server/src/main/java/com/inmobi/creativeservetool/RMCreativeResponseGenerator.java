package com.inmobi.creativeservetool;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;

import com.inmobi.adserve.creativetool.api.AdCreative;
import com.inmobi.adserve.creativetool.api.AdMakerFactory;
import com.inmobi.adserve.creativetool.api.AdRequest;
import com.inmobi.phoenix.entity.adgroup.AdGroup;
import com.inmobi.phoenix.entity.advertisement.CreativeEntity;
import com.inmobi.phoenix.entity.advertisement.creative.AdTagCreative;
import com.inmobi.phoenix.entity.advertisement.creative.BannerCreative;
import com.inmobi.phoenix.entity.advertisement.creative.ContentProvider;
import com.inmobi.phoenix.entity.advertisement.creative.CreativeContentType;
import com.inmobi.phoenix.entity.advertisement.creative.CreativeType;
import com.inmobi.phoenix.entity.advertisement.creative.TextCreative;
import com.inmobi.phoenix.exception.InitializationException;
import com.inmobi.phoenix.exception.RepositoryException;

public class RMCreativeResponseGenerator {

private static AdMakerFactory adMakerFactory;
	
	public static byte[] genTextAdResponse(Integer slotID, Integer width, Integer height, String textContent, String landingURL, AdRequest.RequestFormat responseFormat, AdRequest.Os OS, String beaconURL, String ClickURL) throws InitializationException, RepositoryException, IOException, ConfigurationException {
		CreativeEntity textCreativeEntity = new CreativeEntity();
		textCreativeEntity.setCreativeType(CreativeType.TEXT);
		textCreativeEntity.setCreativeContentType(CreativeContentType.TEXT);
		textCreativeEntity.setContentProvider(ContentProvider.SELF);
		textCreativeEntity.setSlotId(slotID);
		textCreativeEntity.setActualWidth(width);
		textCreativeEntity.setActualHeight(height);
		textCreativeEntity.setValue(new TextCreative(textContent));
		textCreativeEntity.setLandingUrl(landingURL);
		AdRequest adRequest = new AdRequest(responseFormat, OS, slotID, height, width);
		AdCreative adCreative = new AdCreative(textCreativeEntity, landingURL, beaconURL, ClickURL, false, true, true, AdGroup.MarketPlace.CPM, true);
	    byte[] ad = adMakerFactory.getAdMaker().makeAd(adRequest, adCreative);
	    return ad;
	}


	public static byte[] genBannerAdResponse(Integer slotID, Integer width, Integer height, String bannerURL, String alternateText, String landingURL, AdRequest.RequestFormat responseFormat, AdRequest.Os OS, String beaconURL, String ClickURL) throws InitializationException, RepositoryException, IOException, ConfigurationException {
		CreativeEntity bannerCreativeEntity = new CreativeEntity();
		bannerCreativeEntity.setCreativeType(CreativeType.BANNER);
		bannerCreativeEntity.setCreativeContentType(CreativeContentType.BANNER);
		bannerCreativeEntity.setContentProvider(ContentProvider.SELF);
		bannerCreativeEntity.setSlotId(slotID);
		bannerCreativeEntity.setActualWidth(width);
		bannerCreativeEntity.setActualHeight(height);
		bannerCreativeEntity.setValue(new BannerCreative(bannerURL, alternateText));
		bannerCreativeEntity.setLandingUrl(landingURL);
		AdRequest adRequest = new AdRequest(responseFormat, OS, slotID, height, width);
		AdCreative adCreative = new AdCreative(bannerCreativeEntity, landingURL, beaconURL, ClickURL, false, true, true, AdGroup.MarketPlace.CPM, true);
		byte[] ad = adMakerFactory.getAdMaker().makeAd(adRequest, adCreative);
	    return ad;
	}

	public static byte[] genExpandableAdResponse(Integer slotID, Integer width, Integer height, String adTag, String landingURL, AdRequest.RequestFormat responseFormat, AdRequest.Os OS, String beaconURL, String ClickURL) throws InitializationException, RepositoryException, IOException, ConfigurationException {
	 CreativeEntity expCreativeEntity = new CreativeEntity();
	 expCreativeEntity.setCreativeType(CreativeType.EXPANDABLE);
	 expCreativeEntity.setCreativeContentType(CreativeContentType.HTML);
	 expCreativeEntity.setContentProvider(ContentProvider.OTHERS);
	 expCreativeEntity.setSlotId(slotID);
	 expCreativeEntity.setActualHeight(height);
	 expCreativeEntity.setActualWidth(width);
	 expCreativeEntity.setValue(new AdTagCreative(adTag, "sprout", false, false, false, false));
	AdRequest adRequest = new AdRequest(responseFormat, OS, slotID, height, width);
	AdCreative adCreative = new AdCreative(expCreativeEntity, landingURL, beaconURL, ClickURL, false, true, true, AdGroup.MarketPlace.CPM, true);
	byte[] ad = adMakerFactory.getAdMaker().makeAd(adRequest, adCreative);
    return ad;
	}
	
	public static byte[] genInterstitialAdResponse(Integer slotID, Integer width, Integer height, String adTag, String landingURL, AdRequest.RequestFormat responseFormat, AdRequest.Os OS, String beaconURL, String ClickURL) throws InitializationException, RepositoryException, IOException, ConfigurationException {
		System.out.println("genInterstitialAdResponse");
		CreativeEntity intCreativeEntity = new CreativeEntity();
		intCreativeEntity.setCreativeType(CreativeType.INTERSTITIAL);
		intCreativeEntity.setCreativeContentType(CreativeContentType.HTML);
		intCreativeEntity.setContentProvider(ContentProvider.OTHERS);
		intCreativeEntity.setSlotId(slotID);
		intCreativeEntity.setActualHeight(height);
		intCreativeEntity.setActualWidth(width);
		intCreativeEntity.setValue(new AdTagCreative(adTag, "sprout", false, false, false, false));
		AdRequest adRequest = new AdRequest(responseFormat, OS, slotID, height, width);
		AdCreative adCreative = new AdCreative(intCreativeEntity, landingURL, beaconURL, ClickURL, false, true, true, AdGroup.MarketPlace.CPM, true);
		byte[] ad = adMakerFactory.getAdMaker().makeAd(adRequest, adCreative);
	    return ad;
	}
	
}
