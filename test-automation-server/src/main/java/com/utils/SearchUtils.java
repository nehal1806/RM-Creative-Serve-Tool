package com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUtils {

	String LOGTAG = "+ [SearchUtils]: ";

	// ####################################################################################
	public String searchImageSource(String inputString) {
		Pattern nullClickUrl = Pattern.compile("IMAGE-SOURCE");
		String mPattern = null;

		Matcher imageSourceStr = nullClickUrl.matcher(inputString);
		if (imageSourceStr.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + imageSourceStr.group());
			mPattern = imageSourceStr.group();
		}

		return mPattern;
	}


	// ####################################################################################
	public String searchLandingUrl(String inputString) {
		Pattern nullClickUrl = Pattern.compile("LANDING-PAGE");
		String mPattern = null;

		Matcher landingPageStr = nullClickUrl.matcher(inputString);
		if (landingPageStr.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + landingPageStr.group());
			mPattern = landingPageStr.group();
		}

		return mPattern;
	}


	// ####################################################################################
	public String searchBeaconStringM101(String inputString) {
		Pattern beaconM101 = Pattern.compile("BEACON-URL-101");
		String mPattern = null;

		Matcher beaconM101Str = beaconM101.matcher(inputString);
		if (beaconM101Str.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + beaconM101Str.group());
			mPattern = beaconM101Str.group();
		}

		return mPattern;
	}

	// ####################################################################################
	public String searchBeaconStringM18(String inputString) {
		Pattern beaconM18 = Pattern.compile("BEACON-URL-18");
		String mPattern = null;

		Matcher beaconM18Str = beaconM18.matcher(inputString);
		if (beaconM18Str.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + beaconM18Str.group());
			mPattern = beaconM18Str.group();
		}

		return mPattern;
	}


	// ####################################################################################
	public String searchClickUrl(String inputString) {
		Pattern nullClickUrl = Pattern.compile("CLICK-URL");
		String mPattern = null;

		Matcher clickUrlStr = nullClickUrl.matcher(inputString);
		if (clickUrlStr.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + clickUrlStr.group());
			mPattern = clickUrlStr.group();
		}

		return mPattern;
	}

	// ####################################################################################
	public String searchClickCallbackMethod(String inputString) {
		Pattern nullClickUrl = Pattern.compile("CLICK-URL");
		String mPattern = null;

		//	window['im_3232_clickCallback'] = function() {

		Matcher clickUrlStr = nullClickUrl.matcher(inputString);
		if (clickUrlStr.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + clickUrlStr.group());
			mPattern = clickUrlStr.group();
		}

		return mPattern;
	}

	// ####################################################################################
	public String searchClickXmlUrl(String inputString) {
		Pattern clickXmlUrl = Pattern.compile("CLICK-XML-URL");
		String mPattern = null;

		Matcher clickXmlUrlStr = clickXmlUrl.matcher(inputString);
		if (clickXmlUrlStr.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + clickUrlStr.group());
			mPattern = clickXmlUrlStr.group();
		}

		return mPattern;
	}

	// ####################################################################################
	public String searchNullClickUrl(String inputString) {
		Pattern nullClickUrl = Pattern.compile("'CLICK-URL'");
		String mPattern = null;

		Matcher clickUrlStr = nullClickUrl.matcher(inputString);
		if (clickUrlStr.find()) {
			//System.out.println(LOGTAG + "PATTERN MATCHED: " + clickUrlStr.group());
			mPattern = clickUrlStr.group();
		}

		return mPattern;
	}

	// ####################################################################################
	public String matchClickTrackerMethod(String inputString) {
		//System.out.println("\n+ match input string for ClickCallBack: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 *		window['im_3232_clickCallback'] = function() {
		 *	(window['im_3232_clickCallback'] = function() {) 
		 * */

		Pattern clickCallBackMethod = Pattern.compile("((\\s+)?(window\\['im_3232_clickCallback'\\])(\\s?)(=)(\\s?)(function\\(\\)(\\s?)(\\{)))");

		Matcher clickCallBack = clickCallBackMethod.matcher(inputString);
		if (clickCallBack.find()) {
			System.out.println("PATTERN MATCHED: " + clickCallBack.group());
			mPattern = clickCallBack.group();
		} 

		return mPattern;	
	}


	// ####################################################################################
	public String matchImpressionTrackerMethod(String inputString) {
		//System.out.println("\n+ Trying to match impressionCallBack: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * window['im_3232_impressionCallback'] = function() {
		 * */
		Pattern impCallBackMethod = Pattern.compile("(\\s?)?(window\\['im_3232_impressionCallback'\\])(\\s?)(=)(\\s?)(function\\(\\)(\\s?)(\\{))");
		Matcher impCallBack = impCallBackMethod.matcher(inputString);

		if (impCallBack.find()) {
			//System.out.println("PATTERN MATCHED: " + impCallBack.group());
			mPattern = impCallBack.group();
		}

		return mPattern;	
	}
	
	// ####################################################################################
	public String matchImpressionTrackerByPing(String inputString) {
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * <iframe src="BEACON-URL-111"/>
		 * */
		Pattern impCallBackPing = Pattern.compile("(BEACON-URL-111)");
		Matcher impPing = impCallBackPing.matcher(inputString);

		if (impPing.find()) {
			System.out.println("PATTERN MATCHED: " + impPing.group());
			mPattern = impPing.group();
		}

		return mPattern;	
	}
	
	// ####################################################################################
	public String matchClosePingM3(String inputString) {
		String mPattern = null;

		Pattern closeBeaconPing = Pattern.compile("(BEACON-URL-3)");
		Matcher closePing = closeBeaconPing.matcher(inputString);

		if (closePing.find()) {
			System.out.println("PATTERN MATCHED: " + closePing.group());
			mPattern = closePing.group();
		}
		return mPattern;	
	}

	// ####################################################################################
	public String matchIntersititalClosePing(String inputString) {
		//System.out.println("\n+ match input string for ClickCallBack: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 *	//	(c+="&secs="+Math.round((t()-y)/1E3)) 
		 * */

		Pattern closePingMatch = Pattern.compile("(\\(c\\+\\=)(\")(&secs=)(\")(\\+)(Math.round)(\\(\\()(t\\(\\)-y\\))(/)(1E3\\)\\))");

		Matcher closeButtonPing = closePingMatch.matcher(inputString);
		if (closeButtonPing.find()) {
			//System.out.println("PATTERN MATCHED: " + closeButtonPing.group());
			mPattern = closeButtonPing.group();
		}

		return mPattern;	
	}
	
	// ####################################################################################
	//	public String searchClickUrlFromXhtml(String inputString) {
	//		Pattern nullClickUrlXhtml = Pattern.compile("CLICK-URL");
	//		String mPattern = null;
	//
	//		Matcher clickUrlStrXhtml = nullClickUrlXhtml.matcher(inputString);
	//		if (clickUrlStrXhtml.find()) {
	//			//System.out.println(LOGTAG + "PATTERN MATCHED: " + clickUrlStrXhtml.group());
	//			mPattern = clickUrlStrXhtml.group();
	//		}
	//
	//		return mPattern;
	//	}


	public static void main(String[] args) {
		SearchUtils srcUtils = new SearchUtils();

		String s = "<iframe src=\"BEACON-URL-111\"/>";
		//String s = "window['im_3232_clickCallback'] = function() {";
		srcUtils.matchImpressionTrackerByPing(s);
	}
}
