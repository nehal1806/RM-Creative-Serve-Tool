package com.inmobi.psocommons;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegExpCollection {

	public static String getCustomMatch(String inputString, String regEx) {

		String mPattern = null;
		Pattern regex = Pattern.compile(regEx);
		Matcher regexMatcher = regex.matcher(inputString);

		while (regexMatcher.find()) {
			//System.out.println("PATTERN MATCHED: " + regexMatcher.group());
			mPattern = regexMatcher.group();
		}

		return mPattern;

	}	//


	public String matchImgSrcPatterntInXHTML(String inputString) {
		System.out.println("\n+ String to search: \n" + inputString);
		String mPattern = null;

		// OR src="http://adtools-a.akamaihd.net/a/c.1.png"
		// to compile the regex => src="http:adoutput.inmobi.com/banner/inmobi/gifs/bang.png"
		Pattern regexImageSrc = Pattern.compile("(src=\")+(http[s]?)?:(\\/\\/)?(\\w+)?(\\.)?(\\w+)+([-])?(\\w+)?(\\.)(\\w+)(/\\w+)+(.png)\"");

		Matcher regexMatcherImageSrc = regexImageSrc.matcher(inputString);

		if (regexMatcherImageSrc.find()) {
			System.out.println("PATTERN MATCHED: " + regexMatcherImageSrc.group());
			mPattern = regexMatcherImageSrc.group();
		}


		return mPattern;

	}	//

	public String matchImgSrcPattern(String inputString) {
		System.out.println("\n+ String to search: \n" + inputString);
		String mPattern = null;

		//	src="http://adoutput.inmobi.com/banner.png"
		// to compile the regex => src="http:adoutput.inmobi.com/banner/inmobi/gifs/bang.png
		Pattern regexImageSrc = Pattern.compile("(" +
				"(src=\")+(http[s]?)+:(\\/\\/)?" +
				"(\\w+[-_]?" +
				"(.)?)" +
				"(\\w+[-_]?" +
				"(.)?)" +
				"(\\w+[-_]?" +
				"(.)?)" +
				"(banner.png)+" + 
				"\"" + 
				")");

		Matcher regexMatcherImageSrc = regexImageSrc.matcher(inputString);

		if (regexMatcherImageSrc.find()) {
			System.out.println("PATTERN MATCHED: " + regexMatcherImageSrc.group());
			mPattern = regexMatcherImageSrc.group();
		}


		return mPattern;

	}	//


	public String matchImgSrcByIpPattern(String inputString) {
		System.out.println("\n+ String to search: \n" + inputString);
		String mPattern = null;

		// to compile the regex => src="http:adoutput.inmobi.com/banner/inmobi/gifs/bang.png
		Pattern regexImageSrcByIp = Pattern.compile("(src=\")+(http[s]?)?:(\\/\\/)?(\\d{1,2})+\\.(\\d{1,2})+\\.(\\d{1,2})+\\.(\\d{1,2})+(:\\d{1,4})?\\/(\\w+)+\\/(\\w+)?(/\\d{1,5})x(\\d{1,5})([_-])?(\\w+)?(\\.)(png|gif)+");
		Matcher regexMatcherImageSrcByIp = regexImageSrcByIp.matcher(inputString);

		if (regexMatcherImageSrcByIp.find()) {
			System.out.println("PATTERN MATCHED: " + regexMatcherImageSrcByIp.group());
			mPattern = regexMatcherImageSrcByIp.group();
		}

		return mPattern;

	}	//


	public String matchMRaidJsPattern(String inputString) {
		System.out.println("\n+ String to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * String src="http:adoutput.inmobi.com/banner/inmobi/gifs/bang.png"
		 * */
		//(.)+(js)+\">\\s<\\/script>
		Pattern mriadJs = Pattern.compile("(<script src=\"(http[s]?)?):(\\/\\/)?" +
				"(\\w+[-_]?)?(\\w+[-_]?)(.)(\\w+[-_]?)(.)(\\w+[-_]?)(.)" +
				"(\\w+)?(\\/)?" +
				"(\\/)+" +
				"(\\w+.js\">)" +
				"(\\s)?(</script>)");
		Matcher regexMraidJs = mriadJs.matcher(inputString);

		if (regexMraidJs.find()) {
			System.out.println("PATTERN MATCHED: " + regexMraidJs.group());
			mPattern = regexMraidJs.group();
		}

		return mPattern;

	}	//


	public String getPropKey(String inputString) {

		String mPattern = null;

		//Pattern regex = Pattern.compile("([a-zA-Z0-9]+[-|_]+[a-zA-Z0-9]+)|[a-zA-Z0-9]+|([A-Za-z]+[-][A-Za-z]+)");
		Pattern regex = Pattern.compile("([\\w\\d]*[-_]+[\\w]*[-_]*[\\w]*)|([a-zA-Z0-9]+)");
		Matcher regexMatcher = regex.matcher(inputString);

		while (regexMatcher.find()) {
			//System.out.println("PATTERN MATCHED: " + regexMatcher.group());
			mPattern = regexMatcher.group();
		}

		return mPattern;

	}	//


	public String matchLandingPage(String inputString) {
		System.out.println("\n+ Searching for Landing-URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE:
		 * var landingUrl = 'http:\\/\\/processed.inmobi.com\\/';";
		 * "var finalLandingUrl = window['im_3232_replaceTimeStamp']('http:\\/\\/processed.inmobi.com\\/');";	
		 * */

		Pattern landingUrl = Pattern.compile("((\\s?)(var landingUrl = )" +
				"(((\\'http[s]?)?:(\\\\\\/\\\\\\/)?(\\w+[-_]?)(.)(\\w+[-_]?)(.)(\\w+[-_]?)(\\\\\\/)(\\w+)(\\?)(\\w+)(=)?(\\d|\\w+[-_]?)?((\\&)?(\\w+)=(\\w+|\\d+))?\\'))|" +
				"((\\s?)(var landingUrl = )(\\'http[s]?)?:)(\\\\\\/\\\\\\/)?(\\w+[-_]?\\w+)?(.)(\\w+[-_]?\\w+)?(.)(\\w+[-_]?\\w+)?(\\\\/)?(\\';)|" +
				"((\\s?)(var landingUrl = )(\\'market?:\\\\/\\\\/url\\\\/\\'))"+
				")");

		Matcher regexLandingPage = landingUrl.matcher(inputString);

		if (regexLandingPage.find()) {
			System.out.println("PATTERN MATCHED: " + regexLandingPage.group());
			mPattern = regexLandingPage.group();
		} 

		return mPattern;

	}	//


	
	public String matchFinalLandingPageXHtml(String inputString) {
		System.out.println("\n+ Searching for FinalLanding-URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE:
		 * var landingUrl = 'http:\\/\\/processed.inmobi.com\\/';";
		 * "var finalLandingUrl = window['im_3232_replaceTimeStamp']('http:\\/\\/processed.inmobi.com\\/');";	
		 * */
		
		Pattern landingUrl = Pattern.compile(
				"((\\s?)(var finalLandingUrl)(\\s?)(=)(\\s?)" +
				"(\\w+)(\\['(\\w+)'\\])(\\('market:\\\\/\\\\/url\\\\/'\\);))");
		Matcher regexLandingPage = landingUrl.matcher(inputString);

		
		/*	This regexp has huge performance issue: it hangs while it its a string like: 	String s = " window['im_3232_replaceTimeStamp'] = function(url) {";
		 * Pattern landingUrl2 = Pattern.compile(
						"((\\s?)(\\w+)+(\\s?)?((\\w+)+)(\\s?)(=)(\\s?)" +
						"(\\w+)(\\['(\\w+)'\\])(\\('market:\\\\/\\\\/url\\\\/'\\);))");
				Matcher regexLandingPage2 = landingUrl2.matcher(inputString);
		*/
		
		if (regexLandingPage.find()) {
			System.out.println("landing url for xhtml format...");
			mPattern = regexLandingPage.group();
		}

		return mPattern;

	}	//

	// Match click url inside function in banner 
	public String matchFunctionClickURL(String inputString) {
		System.out.println("\n+  Click URL inside function to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * _im_imai.pingInWebView('http:\/\/adoutput.inmobi.com\/clickUrl?at=2&am=3');
		 * */
		Pattern nullClickUrl = Pattern.compile("((\\s)(\\w+)(.)(\\w+)\\((\\'http[s]?)?:(\\\\/\\\\/)?(\\w+)(.)(\\w+)(.)(\\w+)(\\\\/)(\\w+)(\\?)(\\w+)=(\\w+|\\d+)?((\\&)?(\\w+)=(\\w+|\\d+))?\\'\\))");

		Matcher regexNullClickUrl = nullClickUrl.matcher(inputString);

		if (regexNullClickUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexNullClickUrl.group());
			mPattern = regexNullClickUrl.group();
		}

		return mPattern;

	}	//
	
	

	public String matchNullClickURL(String inputString) {
		System.out.println("\n+ Null Click URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * String s = " var clickUrl = 'http:\\/\\/adoutput.inmobi.com\\/clickUrl?at=2&am=1'";
		 * http://adoutput.inmobi.com/clickUrl
		 * "var clickUrl = null"
		 * */
		Pattern nullClickUrl = Pattern.compile("(\\s)?(var clickUrl = )(\\s)?(\\w+)+");

		Matcher regexNullClickUrl = nullClickUrl.matcher(inputString);

		if (regexNullClickUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexNullClickUrl.group());
			mPattern = regexNullClickUrl.group();
		}

		return mPattern;

	}	//


	public String matchClickURL(String inputString) {
		System.out.println("\n+ Null Click URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * String s = " var clickUrl = 'http:\\/\\/adoutput.inmobi.com\\/clickUrl?at=2&am=1'";
		 * http://adoutput.inmobi.com/clickUrl
		 * "var clickUrl = null"
		 * v="http:\/\/adoutput.inmobi.com\/clickUrl"
		 * */
		Pattern nullClickUrl = Pattern.compile("((\\s)+(var clickUrl = )((\\w+)+|muks|" +
				"((\\'http[s]?)?:(\\\\\\/\\\\\\/)?(\\w+[-_]?)(.)(\\w+[-_]?)(.)(\\w+[-_]?)(\\\\\\/)(\\w+)(\\?)(\\w+)(=)?(\\d|\\w+[-_]?)?((\\&)?(\\w+)=(\\w+|\\d+))?\\')?)" +
				"|((http[s]?):(\\/\\/)?(\\w+[-_]?)(.)(\\w+[-_]?)(.)(\\w+[-_]?)\\/clickUrl)?)");

		Matcher regexNullClickUrl = nullClickUrl.matcher(inputString);

		if (regexNullClickUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexNullClickUrl.group());
			mPattern = regexNullClickUrl.group();
		}

		return mPattern;

	}	//

	public String matchClickUrlInterstitial(String inputString) {
		System.out.println("\n+ Click URL from Interstitial ad-responses to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * String s = "v=\"http:\\/\\/adoutput.inmobi.com\\/clickUrl\"";
		 * */
//		Pattern nullClickUrl = Pattern.compile("((\\s+)?((x|y|u|v|D)+(=))(\")" +
//				"(http[s]?)(:)" +
//				"((\\\\/\\\\/)|(\\/\\/))" +
//				"(\\w+[-_]?(\\w+)?)(.)(\\w+)(.)(\\w+)" +
//				"((\\\\/)|(/))" +
//				"(clickUrl)" +
//				"(\"))");

		Pattern nullClickUrl = Pattern.compile("((\")" +
				"(http[s]?)(:)" +
				"((\\\\/\\\\/)|(\\/\\/))" +
				"(\\w+[-_]?(\\w+)?)(.)(\\w+)(.)(\\w+)" +
				"((\\\\/)|(/))" +
				"(clickUrl)" +
				"(\"))");

		Matcher regexNullClickUrl = nullClickUrl.matcher(inputString);

		if (regexNullClickUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexNullClickUrl.group());
			mPattern = regexNullClickUrl.group();
		}

		return mPattern;

	}	// end matchClickUrlInterstitial()
	
// ##############################################################################################################################

	/*
	 * Author: Dhiren
	 * Date: 4-Dec-2013
	 * Description: This method does a search and replace of the click URL specific to interstitial templates/ad-response
	 */
	
	public String matchClickURLInXHTML(String inputString) {
		System.out.println("\n+ Null Click URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * http://adoutput.inmobi.com/clickUrl
		 * <a href="http://adoutput.inmobi.com/clickUrl"
		 * */
		Pattern nullClickUrl = Pattern.compile("(<a href=\"http[s]?):(\\/\\/)?(\\w+[-_]?)(.)?(\\w+[-_]?)(.)?(\\w+[-_]?)/clickUrl\""); //(\\w+[-_]?)\\/clickUrl)?");

		Matcher regexNullClickUrl = nullClickUrl.matcher(inputString);

		if (regexNullClickUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexNullClickUrl.group());
			mPattern = regexNullClickUrl.group();
		}

		return mPattern;

	}	//


	public String matchBeaconURLFromScriptM18(String inputString) {
		System.out.println("\n+ Beacon URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * \"http:\\/\\/adoutput.inmbi.com\\/beaconUrl\"";
		 * "http://adoutput.inmbi.com/beaconUrl?m=18"
		 * */

		Pattern beaconUrl = Pattern.compile(("\"") + 
				"(http[s]?)(:)" +
				"((\\\\/\\\\/)|(\\/\\/))" +
				"(\\w+)+([-])?(\\.)?" +
				"(\\w+)+([-])?(\\.)?" +
				"(\\w+)+([-])?(\\.)?" +
				"((\\\\/)|(/))" +
				"(beaconUrl)" +
				"(\\?)?(m=18)?" + 
				"(\")"); 

		Matcher regexBeaconUrl = beaconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//

	public String matchBeaconURLM101(String inputString) {
		System.out.println("\n+ Beacon URL to search: \n" + inputString);
		String mPattern = null;

		// Q="http:\/\/adoutput.inmbi.com\/beaconUrl"
		//String s = "img src=\"http://adoutput.inmbi.com/cscBeaconUrl\"";

		Pattern beasconUrl = Pattern.compile("((img src=\")+(http[s]?)?:(\\/\\/)(\\w+)+([-])?(.)(\\w+)+([-])?(.)(\\w+)+([-])?/cscBeaconUrl\")");

		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//

	
	public String matchBeaconURLFromScriptCloseM3(String inputString) {
		System.out.println("\n+ Beacon URL to search: \n" + inputString);
		String mPattern = null;

		// http://adoutput.inmobi.com/banner.png?m=3
		Pattern beaconUrl = Pattern.compile(("\"") + 
				"(http[s]?)(:)" +
				"((\\\\/\\\\/)|(\\/\\/))" +
				"(\\w+)+([-])?(\\.)?" +
				"(\\w+)+([-])?(\\.)?" +
				"(\\w+)+([-])?(\\.)?" +
				"((\\\\/)|(/))" +
				"(banner.png)" +
				"(\\?)?(m=3)?" + 
				"(\")"); 

		Matcher regexBeaconUrl = beaconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//
	
	
	public String matchClickUrlFromXml(String inputString) {
		System.out.println("\n+ Click URL from xml data: \n" + inputString);
		String mPattern = null;

		//String s = "img src=\"http://adoutput.inmbi.com/cscBeaconUrl\"";

		Pattern beasconUrl = Pattern.compile("(<AdURL>)+(http[s]?)?:(\\/\\/)+(\\w+)+([-])?(.)(\\w+)+([-])?(.)(\\w+)+([-])?/clickUrl(</AdURL>)");
		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//

	public String matchBeacon101FromSyncXhtml(String inputString) {
		System.out.println("\n+ beacon-101 from xml data: \n" + inputString);
		String mPattern = null;
		//	"<img src=\"http://adoutput.inmbi.com/cscBeaconUrl\"";
		Pattern beasconUrl = Pattern.compile("(<img src=\")+(http[s]?)?:(\\/\\/)+(\\w+)+([-])?(.)(\\w+)+([-])?(.)(\\w+)+([-])?/cscBeaconUrl\"");
		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//

	public String matchBeaconURLInXHTML18(String inputString) {
		System.out.println("\n+ Null Beacon URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * String s = "	img src="http://adoutput.inmbi.com/beaconUrl?m=18" "
		 * */

		Pattern beasconUrl = Pattern.compile("(img src=\")+(http[s]?)?:(\\/\\/)?(\\w+)+([-])?(\\.)?(\\w+)+([-])?(\\w+)?(\\.)(\\w+)/(beaconUrl)(\\?)?(m=18)\"");
		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//

	public String matchBeaconURLInXHTML101(String inputString) {
		System.out.println("\n+ Null Beacon URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * String s = "	img src="http://adoutput.inmbi.com/cscBeaconUrl" "
		 * */

		Pattern beasconUrl = Pattern.compile("(img src=\")+(http[s]?)?:(\\/\\/)?(\\w+)+([-])?(\\.)?(\\w+)+([-])?(\\w+)?(\\.)(\\w+)/(cscBeaconUrl)?\"");
		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//


	public String matchClickASyncURLInXHTML(String inputString) {
		System.out.println("\n+ match Click ASync URL In XHTML URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * 
		 * */

		Pattern beasconUrl = Pattern.compile("(newIframe\\('(http[s]?)?:(\\\\/\\\\/)?(\\w+)+([-])?(\\.)?(\\w+)+([-])?(\\.)?(\\w+)+([-])?(\\.)?(\\\\/)?)(\\w+)+([-])?(\\?)(\\w+[-_]?)+(=)(\\d{1,5})?(\\&)(\\w+[-_]?)+(=)(\\d{1,5})?'\\);");
		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//

	public String matchPlainBeacon(String inputString) {
		System.out.println("\n+ match Click ASync URL In XHTML URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * http://adoutput.inmbi.com/cscBeaconUrl
		 * */

		Pattern beasconUrl = Pattern.compile("(img src=\")+(http[s]?)(:)+(\\/\\/)?(\\w+[-_]?)?(.)(\\w+[-_]?)?(.)(\\w+[-_]?)?(\\/)+(cscBeaconUrl)+\"");
		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
		}

		return mPattern;

	}	//


	public String matchTrackingPng(String inputString) {
		//System.out.println("\n+ match Click ASync URL In XHTML URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * <img src=tracking.png/>
		 * */

		Pattern trackingPng = Pattern.compile("<img src=tracking.png/>");
		Matcher regexTrackingPng = trackingPng.matcher(inputString);

		if (regexTrackingPng.find()) {
			System.out.println("PATTERN MATCHED: " + regexTrackingPng.group());
			mPattern = regexTrackingPng.group();
		}

		return mPattern;

	}	//


	// ############################################################################################################
	public String matchBluekaiFrame(String inputString) {
		System.out.println("\n+ match matchBluekaiFrame URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE:
		 * <iframe src=\"http://user.inmobiugc.com/blueKai.html?id=TESTINMOBIUSERID\"
		 * */
		Pattern bluekaiUrl = Pattern.compile("((\\s+)?(<iframe src=\")((http[s]?)(:)+(\\/\\/)?)" +
				"(\\w+)(.)(\\w+)(.)(\\w+)" +
				"(/)" +
				"(\\w+)(.)(html)" +
				"(\\?)" +
				"(id=)(\\w+)(\")(.*)(/>))");
		Matcher regexBluekaiUrl = bluekaiUrl.matcher(inputString);

		if (regexBluekaiUrl.find()) {
			System.out.println("PATTERN MATCHED: " + regexBluekaiUrl.group());
			mPattern = regexBluekaiUrl.group();
		}

		return mPattern;

	}	//



	public String matchMarketUrl(String inputString) {
		System.out.println("\n+ match Click ASync URL In XHTML URL to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * var finalLandingUrl = replaceTimeStamp('market:\/\/url\/');
		 * */

		Pattern trackingPng = Pattern.compile("(var finalLandingUrl = replaceTimeStamp\\('market:)+(\\\\/)+" +
				"(\\w+[\\?]?)" +
				"(\\w+[=]?)?((\\w+)?(.)(\\w+)?(.)(\\w+)?)?" +
				"('\\);)");
		//"(\\w[.-_]?|\\w[\\?]?)+(\\\\/)+('\\));");
		Matcher regexTrackingPng = trackingPng.matcher(inputString);

		if (regexTrackingPng.find()) {
			System.out.println("PATTERN MATCHED: " + regexTrackingPng.group());
			mPattern = regexTrackingPng.group();
		}

		return mPattern;

	}	//

	public String matchUrlRedirect35x(String inputString) {
		System.out.println("\n+ match URL redirect server to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * https:\/\/c.w.inmobi.com\/bolt.html?url=
		 * */
		Pattern urlRedirect35x = Pattern.compile("((http[s]?)(:)+(\\\\/\\\\/)?(\\w+)(.?)(\\w+)+(.?)(\\w+)(.?)(com)(\\\\/)(bolt.html\\?url=))");
		Matcher regexUrlRedirect35x = urlRedirect35x.matcher(inputString);

		if (regexUrlRedirect35x.find()) {
			System.out.println("PATTERN MATCHED: " + regexUrlRedirect35x.group());
			mPattern = regexUrlRedirect35x.group();
		}

		return mPattern;

	}	//



	public boolean iSHtmlbody(String inputString) {
		System.out.println("\n+ RegEx Testing: \n" + inputString);
		String mPattern = null;

		Pattern beasconUrl = Pattern.compile("^(HTTP.*)|" +
				"^(Date:.*)|" +
				"^(Expires:.*)|" +
				"^(Cache-Control:.*)|" +
				"^(Pragma:.*)|" +
				"^(Content-.*)");

		Matcher regexBeaconUrl = beasconUrl.matcher(inputString);

		if (regexBeaconUrl.find()) {

			System.out.println("PATTERN MATCHED: " + regexBeaconUrl.group());
			mPattern = regexBeaconUrl.group();
			System.out.println("+ Matched pattern is: [" + mPattern + "]" );

			return true;

		}

		return false;

	}	//


	public String matchOrientationLock(String inputString) {
		System.out.println("\n+ match URL redirect server to search: \n" + inputString);
		String mPattern = null;

		/* NOTE: 
		 * Matches below type of URLs
		 * https:\/\/c.w.inmobi.com\/bolt.html?url=
		 * */
		Pattern orientationLock = Pattern.compile("(lockOrientation:!0)");
		Matcher regexOrientationLock = orientationLock.matcher(inputString);

		if (regexOrientationLock.find()) {
			System.out.println("PATTERN MATCHED: " + regexOrientationLock.group());
			mPattern = regexOrientationLock.group();
		}

		return mPattern;

	}	//

	public String matchBannerClickUrl(String inputString) {
		System.out.println("\n+ Searching for Click Url in banner template: \n" + inputString);
		String mPattern = null;

		/* NOTE:
		 * 0) a href="http://adoutput.inmobi.com/clickUrl"
		 * 1) imai - 'http:\/\/adoutput.inmobi.com\/clickUrl?at=2&am=3'
		 * 2) xhtml - 'http:\/\/adoutput.inmobi.com\/clickUrl?at=1&am=0'
		 * 3) xhtml, sync - http://adoutput.inmobi.com/clickUrl
		 */

		//Pattern landingUrl = Pattern.compile("((http[s]?)?(://)(\\w+)(.)?(\\w+)?(.)?(\\w+)?(/)(clickUrl)(\"))");

		Pattern landingUrl = Pattern.compile("(" +
				"((http[s]?)(:)+)" +
				"((//)?|(\\\\/\\\\/)?)" +
				"(\\w+)(.)?(\\w+)?(.)?(\\w+)?" +
				"(((/)?)|((\\\\/)?))" +
				"(clickUrl)+" +
				"((((\\?))?)((at=)(\\d)(&)(am=)(\\d))?)" +
				")");
		Matcher regexLandingPage = landingUrl.matcher(inputString);
		if (regexLandingPage.find()) {
			System.out.println("PATTERN MATCHED: " + regexLandingPage.group());
			mPattern = regexLandingPage.group();
		} 

		return mPattern;
	}	//
	
	public static void main(String[] args) {
		RegExpCollection test = new RegExpCollection();
		
		//String s = "http:\\/\\/adoutput.inmobi.com\\/clickUrl?at=1&am=0";
		String s = "http://adoutput.inmobi.com/clickUrl";
		//String s = "";
		
		String regexMap = test.matchBannerClickUrl(s);
		System.out.println("\n Match: " + regexMap);
	}	

}	// end RegExFactory
