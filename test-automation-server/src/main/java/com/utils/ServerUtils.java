package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inmobi.psocommons.RegExpCollection;
import com.inmobi.psocommons.TouchstoneParsers;


public class ServerUtils {

	String LOGTAG = "+ [ServerUtils]: ";
	RegExpCollection regExp = new RegExpCollection();
	SearchUtils searchUtils = new SearchUtils();
	UrlParserUtils urlParser = new UrlParserUtils();
	String psoResourcePkg = "/com/inmobi/pso";

	public String hostIP, testConfigFile, DB_URL, JDBC_DRIVER, USERID, PASSWORD, CLICKS_TABLE, BEACON_TABLE;

	// ########################################################################################################
	public StringBuilder searchAndReplaceInResponses(HashMap<String, String> reqParams, String urlConfigFile, String responseFile) {
		TouchstoneParsers parsers = new TouchstoneParsers();
		HashMap<String, HashMap<String, String>> configPropsMap = parsers.readPropsFile(new File(urlConfigFile));

		// get all click, beacon and click-redirect urls built
		HashMap<String, String> allUrlPaths = buildAllUrls(reqParams, configPropsMap);	// Get all urls
		//System.out.println(LOGTAG + " URLs Hash: " + allUrlPaths.toString());

		StringBuilder finalResponseContent = replaceInResponseContent(responseFile, configPropsMap, allUrlPaths, reqParams);	// TO REPLACE THE CONTENT OF RESPONSE FILES

		return finalResponseContent;
	}


	// ################################################################################################################
	// Fetch hostname
	public String getHostIp() {
		//return req.getRemoteHost().toString();

		String hostIp = null;
		Enumeration en = null;
		try {
			en = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while(en.hasMoreElements()){
			NetworkInterface ni=(NetworkInterface) en.nextElement();
			Enumeration ee = ni.getInetAddresses();

			while(ee.hasMoreElements()) {
				InetAddress current_addr = (InetAddress) ee.nextElement();
				if (current_addr.isLoopbackAddress() || (current_addr instanceof Inet6Address) ) {
					continue;
				} else if (current_addr instanceof Inet4Address) {
					System.out.println(LOGTAG + " Obtained Ip: " + current_addr.getHostAddress());
					hostIp = current_addr.getHostAddress();
				}
			}
		}

		return hostIp;

	}

	// ################################################################################################################
	// Initialize server environment settings
	public void intServerEnv() {
		TouchstoneParsers parsers = new TouchstoneParsers();
		String CONFIGFILE = psoResourcePkg + "/configs/server.properties";
		String testConfigFile = ServerUtils.class.getResource(CONFIGFILE).getFile();

		HashMap<String, HashMap<String, String>> configPropsMap = parsers.readPropsFile(new File(testConfigFile));
		hostIP = (configPropsMap.get("URL_PARAMS").get("LOCAL_ENV").equalsIgnoreCase("yes") ? "localhost" : getHostIp());

		// JDBC driver name and database URL
		JDBC_DRIVER="com.mysql.jdbc.Driver";
		DB_URL="jdbc:mysql://" + hostIP + "/psotest";

		// Database credentials
		USERID = configPropsMap.get("SERVER_ENV").get("USERID");
		PASSWORD = configPropsMap.get("SERVER_ENV").get("PASSWORD");

		// Database tables:
		CLICKS_TABLE = configPropsMap.get("SERVER_ENV").get("CLICKS_TABLE");
		BEACON_TABLE = configPropsMap.get("SERVER_ENV").get("BEACON_TABLE");

	}


	// ################################################################################################################
	public StringBuilder buildResponse(HashMap<String, String> reqParams, String urlConfigFile, String inResponseFile, PrintWriter out) {

		// In-memory replacement of the contents of the response files 
		StringBuilder modifiedResponseContent = searchAndReplaceInResponses(reqParams, urlConfigFile, inResponseFile);
		out.println(modifiedResponseContent);

		out.flush();
		out.close();

		return modifiedResponseContent;

	} // buildResponse()


	// ################################################################################################################
	public HashMap<String, String> buildAllUrls(HashMap<String, String> reqParams, HashMap<String, HashMap<String, String>> configMap) {

		HashMap<String, String> urlPaths = new HashMap<String, String>();

		String hostName = (configMap.get("URL_PARAMS").get("LOCAL_ENV").equalsIgnoreCase("yes") ? "localhost" : getHostIp());
		String bannerImgPath = configMap.get("URL_PARAMS").get("IMAGE_SOURCE");
		String serverPort = configMap.get("URL_PARAMS").get("PORT");
		String adReqType = reqParams.get("adtype");
		String creative = reqParams.get("creative");

		// Building URLs
		//http://dummyimage.com/600x400/000/fff
		String imgUrl = "http://" + hostName + ":" + serverPort + bannerImgPath + adReqType + "_" + creative.toLowerCase() + ".png";
		//String imgUrl = "http://" + "dummyimage.com" + "/" + creative.toLowerCase() + "/ebf70d/0f0f0e";
		urlPaths.put("imageUrl", imgUrl);

		// landing-url
		String landingUrl = configMap.get("URL_PARAMS").get("LANDING_PAGE");
		urlPaths.put("landingUrl", landingUrl);

		String clickServerURL =	"http://" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("CLICK_URL") 
				+ "?rqparam=" 
				+ "/test=" + reqParams.get("test")
				+ "/release=" + reqParams.get("release")
				+ "/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "/slotid=" + reqParams.get("slotid") 
				+ "/creative=" + reqParams.get("creative")
				+ "/thirdparty=" + "no";
		urlPaths.put("clickServerURL", clickServerURL);

		String clickServerEscapedURL = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("CLICK_URL_ESCAPED")
				+ "?rqparam="
				+ "\\/test=" + reqParams.get("test")
				+ "\\/release=" + reqParams.get("release")
				+ "\\/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "\\/slotid=" + reqParams.get("slotid") 
				+ "\\/creative=" + reqParams.get("creative")
				+ "\\/thirdparty=" + "no";
		urlPaths.put("clickServerEscapedURL", clickServerEscapedURL);

		String tpClickServerURL = "http://" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("CLICK_URL")
				+ "?rqparam="
				+ "/test=" + reqParams.get("test")
				+ "/release=" + reqParams.get("release")
				+ "/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "/slotid=" + reqParams.get("slotid") 
				+ "/creative=" + reqParams.get("creative")
				+ "/thirdparty=" + "yes";
		urlPaths.put("tpClickServerURL", tpClickServerURL);

		String tpClickServerEscapedURL = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("TP_CLICK_URL_ESCAPED")
				+ "?rqparam="
				+ "\\/test=" + reqParams.get("test")
				+ "\\/release=" + reqParams.get("release")
				+ "\\/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "\\/slotid=" + reqParams.get("slotid") 
				+ "\\/creative=" + reqParams.get("creative")
				+ "\\/thirdparty=" + "yes";
		urlPaths.put("tpClickServerEscapedURL", tpClickServerEscapedURL);

		//?test=testAndroidTemplate1&release=imai_async&platform=android_4.2_sdk370&m=101
		String beaconUrlM18_Escaped = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL_ESCAPED")
				+ "?rqparam=" 
				+ "\\/test=" + reqParams.get("test")
				+ "\\/release=" + reqParams.get("release")
				+ "\\/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "\\/slotid=" + reqParams.get("slotid") 
				+ "\\/creative=" + reqParams.get("creative")
				+ "\\/adtype=" + reqParams.get("adtype");
		urlPaths.put("beaconUrlM18_Escaped", beaconUrlM18_Escaped);

		String beaconUrlM18 = "http://" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL")
				+ "?rqparam=" 
				+ "/test=" + reqParams.get("test")
				+ "/release=" + reqParams.get("release")
				+ "/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "/slotid=" + reqParams.get("slotid") 
				+ "/creative=" + reqParams.get("creative")
				+ "/m=18";
		urlPaths.put("beaconUrlM18", beaconUrlM18);

		String beaconUrlM18Escaped = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL_ESCAPED")
				+ "?rqparam=" 
				+ "\\/test=" + reqParams.get("test")
				+ "\\/release=" + reqParams.get("release")
				+ "\\/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "\\/slotid=" + reqParams.get("slotid") 
				+ "\\/creative=" + reqParams.get("creative")
				+ "\\/m=18";
		urlPaths.put("beaconUrlM18Escaped", beaconUrlM18Escaped);

		String closePingBeaconUrlM3 = "http://" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL")
				+ "?rqparam=" 
				+ "/test=" + reqParams.get("test")
				+ "/release=" + reqParams.get("release")
				+ "/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "/slotid=" + reqParams.get("slotid") 
				+ "/creative=" + reqParams.get("creative")
				+ "/m=3";
		urlPaths.put("closePingBeaconUrlM3", closePingBeaconUrlM3);

		String beaconUrlM101 = "http://" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL")
				+ "?rqparam=" 
				+ "/test=" + reqParams.get("test")
				+ "/release=" + reqParams.get("release")
				+ "/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "/slotid=" + reqParams.get("slotid") 
				+ "/creative=" + reqParams.get("creative")
				+ "/m=101";
		urlPaths.put("beaconUrlM101", beaconUrlM101);

		String beaconUrlM101Escaped = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL_ESCAPED")
				+ "?rqparam=" 
				+ "\\/test=" + reqParams.get("test")
				+ "\\/release=" + reqParams.get("release")
				+ "\\/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "\\/slotid=" + reqParams.get("slotid") 
				+ "\\/creative=" + reqParams.get("creative")
				+ "\\/m=101";
		urlPaths.put("beaconUrlM101Escaped", beaconUrlM101Escaped);

		String thirdpatyBeaconUrlM111 = "http://" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL")
				+ "?rqparam=" 
				+ "/test=" + reqParams.get("test")
				+ "/release=" + reqParams.get("release")
				+ "/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "/slotid=" + reqParams.get("slotid") 
				+ "/creative=" + reqParams.get("creative")
				+ "/m=111";
		urlPaths.put("tpBeaconUrlM111", thirdpatyBeaconUrlM111);

		String thirdpatyBeaconUrlM777Escaped = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL_ESCAPED")
				+ "?rqparam=" 
				+ "\\/test=" + reqParams.get("test")
				+ "\\/release=" + reqParams.get("release")
				+ "\\/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "\\/slotid=" + reqParams.get("slotid") 
				+ "\\/creative=" + reqParams.get("creative")
				+ "\\/m=777";
		urlPaths.put("tpBeaconUrlM777Escaped", thirdpatyBeaconUrlM777Escaped);

		String closePingURLEscaped = "(c=http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("BEACON_URL_ESCAPED")
				+ "?rqparam=" 
				+ "\\/test=" + reqParams.get("test")
				+ "\\/release=" + reqParams.get("release")
				+ "\\/platform=" + reqParams.get("platform") + "_" + reqParams.get("platformVersion") + "_" + reqParams.get("integration") + "_" + reqParams.get("device")
				+ "\\/slotid=" + reqParams.get("slotid") 
				+ "\\/creative=" + reqParams.get("creative")
				+ "\\/m=3)";
		urlPaths.put("closePingURLEscaped", closePingURLEscaped);

		String asyncLandingPage = "http://" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("ASYNC_LANDING_PAGE");
		urlPaths.put("asyncLandingPage", asyncLandingPage);

		// Note: usage of 4-back slashes when replaceAll and 2 when just replace
		String asyncLandingPageEscaped = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("ASYNC_LANDING_PAGE_ESCAPED");
		urlPaths.put("asyncLandingPageEscaped", asyncLandingPageEscaped);

		String iOsLandingPageEscaped = "http:\\/\\/" + hostName + ":" + serverPort + configMap.get("URL_PARAMS").get("iOS_LANDING_PAGE_ESCAPED");
		urlPaths.put("iOsLandingPageEscaped", iOsLandingPageEscaped);

		return urlPaths;

	}

	// ################################################################################################################
	public StringBuilder replaceInResponseContent (String inResponseFile,
			HashMap<String, HashMap<String, String>> configMap,
			HashMap<String, String> urls,
			HashMap<String, String> reqParams) {

		String adType = reqParams.get("adtype");
		String clickActionType = getResponseFormat(reqParams, "");
		String responseFormat = getResponseFormat(reqParams, "format");
		String platform = reqParams.get("platform");
		boolean sync = (clickActionType.equalsIgnoreCase("sync") ? true : false);

		System.out.println(LOGTAG + " Replacing in the reponse data...");
		System.out.println(LOGTAG + "INFO: Response format: " + responseFormat + " Sync (boolean): " + sync);

		StringBuilder fileContent = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(inResponseFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {

				line.trim();	// trimming trailing characters
				if (!line.matches("^\\s*$") ) {	// skip blank lines

					// Changing image source location
					String imgSrcMatch = searchUtils.searchImageSource(line);
					if (imgSrcMatch != null) {
						line = line.replace(imgSrcMatch, urls.get("imageUrl"));
					}

					// Click url remains same in either of the cases, imai or xhtml, sync or async formats
					String clickUrlMatch = searchUtils.searchClickUrl(line);
					if (clickUrlMatch != null ) {
						System.out.println(LOGTAG + " Search and replace click URL: " + clickUrlMatch );
						line = line.replace( clickUrlMatch, urls.get("clickServerEscapedURL"));
					}

					/* Sync: 1 call, which goes and hits click-server and click server redirects it to the landing page.
					 * Async: 2 calls, one to the click-server and the other to the landing page.
					 */
					if (sync) {
						// Specific click/landing page handlers for iOS (return type: adtest://) and android (return type: nil )
						String landingUrlMatch = searchUtils.searchLandingUrl(line);		
						if ( landingUrlMatch != null ) {
							System.out.println(LOGTAG + " Search and replace click URL: " + clickUrlMatch + " Replaced with: " + urls.get("clickServerEscapedURL"));
							line = line.replace(landingUrlMatch, urls.get("clickServerEscapedURL") );	// for special cases like back slash should be replace rather replaceAll()
						}

						// to handle specific case of click-url with single quotes
						String clickUrlWithQuotes = searchUtils.searchNullClickUrl(line);
						if (clickUrlWithQuotes != null) {
							line = line.replaceAll( clickUrlWithQuotes, "null");
						}

					} else {
						// Specific click/landing page handlers for iOS (return type: adtest://) and android (return type: nil )
						String landingUrlMatch = searchUtils.searchLandingUrl(line);
						if ( landingUrlMatch != null ) {
							if ( platform.equalsIgnoreCase("ios")) {						
								line = line.replace(landingUrlMatch, urls.get("iOsLandingPageEscaped") );	// for special cases like back slash should be replace rather replaceAll()	
							}
							else {							
								line = line.replace(landingUrlMatch, urls.get("asyncLandingPageEscaped") );	// for special cases like back slash should be replace rather replaceAll()	
							}
						}


						if ( adType.equalsIgnoreCase("interstitial") ) {
							// click trackers:
							String clickCallbackMethodMatch = searchUtils.matchClickTrackerMethod(line);
							if (clickCallbackMethodMatch != null && responseFormat.equalsIgnoreCase("imai")) {
								String clickTrackers = "window['im_3232_clickCallback'] = function() {"
										+ "\n"
										+ "_im_imai.pingInWebView('"
										+ urls.get("tpClickServerEscapedURL")
										+ "');"
										+ "\n"
										+ "_im_imai.pingInWebView('"
										+ urls.get("clickServerEscapedURL")
										+ "');";
								line = line.replace( clickCallbackMethodMatch, clickTrackers);
							}

							else if (clickCallbackMethodMatch != null && responseFormat.equalsIgnoreCase("xhtml")) {
								String clickTrackers = "window['im_3232_clickCallback'] = function() {"
										+ "\n"
										+ "var newIframe = window['im_3232_newIframe'];"
										+ "\n"
										+ "newIframe('"
										+ urls.get("tpClickServerEscapedURL")
										+ "');"
										+ "\n"
										+ "newIframe('"
										+ urls.get("clickServerEscapedURL")
										+ "');"
										+ "\n";

								line = line.replace( clickCallbackMethodMatch, clickTrackers);

								// impresion trackers method:
								String impressionTrackers = null;
								String impressionCallbackMethodMatch = searchUtils.matchImpressionTrackerMethod(line);
								if (impressionCallbackMethodMatch != null ) {
									if (responseFormat.equalsIgnoreCase("imai")) {
										impressionTrackers = "window['im_3232_impressionCallback'] = function() {"
												+ "\n"
												+ "_im_imai.pingInWebView('"
												+ urls.get("tpBeaconUrlM777Escaped")
												+ "');";

									} else if (responseFormat.equalsIgnoreCase("xhtml")) {
										impressionTrackers = "window['im_3232_impressionCallback'] = function() {"
												+ "var newIframe = window['im_3232_newIframe'];"
												+ "\n"
												+ "newIframe('"
												+ urls.get("tpBeaconUrlM777Escaped")
												+ "');";						
									}
									
									line = line.replace( impressionCallbackMethodMatch, impressionTrackers );
								}


							} else if ( adType.equalsIgnoreCase("banner") ) {
								// Thirdparty clicks and impressions to be implemented for banners

							}	// end if-else ( based on adtype )

						}

					}

					// replacing impressionPing tracking in place of tracker.png
					String impressionTrackerPing = searchUtils.matchImpressionTrackerByPing(line);
					if (impressionTrackerPing != null) {
						System.out.println(LOGTAG + " ++ impressionTrackerPing: " + impressionTrackerPing.toString());
						line = line.replace( impressionTrackerPing, urls.get("tpBeaconUrlM111") );
					}

					// replacing close m=3 ping  BEACON-URL-3
					String closePingTracking = searchUtils.matchClosePingM3(line);
					if (closePingTracking != null) {
						System.out.println(LOGTAG + " ++ Close Ping M=3: " + closePingTracking.toString());
						line = line.replace( closePingTracking, urls.get("closePingBeaconUrlM3") );
					}

					// Beacon URLs:
					String beaconUrlMatch18 = searchUtils.searchBeaconStringM18(line);
					if (beaconUrlMatch18 != null) {
						if (adType.equalsIgnoreCase("interstitial")) {
							System.out.println(LOGTAG + "TYPE: INTERSTITIAL");
							line = line.replace(beaconUrlMatch18, urls.get("beaconUrlM18_Escaped"));
						} else {
							System.out.println(LOGTAG + "TYPE: BANNER");
							line = line.replace(beaconUrlMatch18, urls.get("beaconUrlM18"));
						}
					}

					// Beacon-101, TP impression tracking
					String beaconUrlMatch101 = searchUtils.searchBeaconStringM101(line);
					if (beaconUrlMatch101 != null) {
						line = line.replace(beaconUrlMatch101, urls.get("beaconUrlM101"));
					}						

				}	// end if-else (!line.matches("^\\s*$")

				// Skip blank lines from the input response files
				if (line != null) {
					fileContent.append("\n");
					fileContent.append(line);
				}

			} // end while(line = reader.readLine())

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileContent;
	}


	// ################################################################################################################
	public String responseFileFetch (HashMap<String, String> reqParams) {

		String platform = null;
		String testCaseId = null;

		platform = reqParams.get("platform");
		testCaseId = reqParams.get("test");	// test case id from url param

		String respContentDir = reqParams.get("respdir");
		String adtype = reqParams.get("adtype");
		String creative = reqParams.get("creative");
		String slot = reqParams.get("slotid");
		String[] releaseParams = reqParams.get("release").split("_");

		String responseFormat = releaseParams[0];
		boolean sync = (releaseParams[1].equalsIgnoreCase("sync") ? true : false);

		/* get the path of the response file based on
		 * 1. release=imai_sync or imai_async, then picks banner_slot-id_creative.new
		 * 2. release=xhtml_sync - picks banner_slot-id_creative.new
		 * 3. release=xhtml_async - picks banner_bolt_slot-id_creative.new
		 */

		String[] platformParams = reqParams.get("platform").split("_");
		if (platformParams.length > 1) {
			platform = (platformParams[0] != null) ? platformParams[0] : "-";
		} else {
			platform = platformParams[0];
		}

		// #####################
		// CHANGE /responseNew TO /response AT LATER STAGES
		String psoResourcePkg = "/com/inmobi/pso";
		String inResponseFile = null;

		if (sync) {
			System.out.println(LOGTAG + "Debug: Selecting sync/non-bolt response...");
			inResponseFile = psoResourcePkg + "/responsesNew/" + respContentDir.toLowerCase() + "/" + responseFormat.toLowerCase() + "/" + platform.toLowerCase() + "/" +
					adtype + "_" + slot.toUpperCase() + "_" + creative.toUpperCase() + ".resp";	
		} else {
			System.out.println(LOGTAG + "Debug: Selecting Async/bolt response...");
			inResponseFile = psoResourcePkg + "/responsesNew/"  + respContentDir.toLowerCase() + "/" + responseFormat.toLowerCase() + "/" + platform.toLowerCase() + "/" +
					adtype + "_" + "bolt" + "_" + slot.toUpperCase() + "_" + creative.toUpperCase() + ".resp";
		}			

		System.out.println(LOGTAG + " [testCaseId : response file]: [" + testCaseId + " : " + inResponseFile + "]");

		// TO IMPLEMENT: Fails here when the file is not found... move class.getResource() to some other class
		String responseFile = ServerUtils.class.getResource(inResponseFile).getFile();

		return responseFile;

	}

	// ################################################################################################################
	// get request paramerts
	public HashMap<String, String> getReqParams(HttpServletRequest request) {

		HashMap<String, String> reqParams = new HashMap<String, String>();
		String rqParam = request.getParameter("rqparam");

		if (rqParam != null) {
			String[] paramsList = rqParam.split("/");
			boolean skipFlag = true;

			for (String param : paramsList) {
				if (!skipFlag) {
					String[] paramsPair = param.split("=");
					String[] beaconPair = null;

					// get platform param from platform=? found in req-url
					if (paramsPair[0].equalsIgnoreCase("platform")) {
						String[] platformParams = paramsPair[1].split("_");

						if (platformParams.length > 1) {
							String platform = (platformParams[0] != null) ? platformParams[0] : "-";
							String platformVersion = (platformParams[1] != null) ? platformParams[1] : "-";
							String integration = (platformParams[2] != null) ? platformParams[2] : "-";

							String device = "-";	// take device name as blank in case its not provided at the url
							if (platformParams.length == 4) {
								device = (platformParams[3] != null) ? platformParams[3] : "-";
							}

							reqParams.put("platform", platform);
							reqParams.put("platformVersion", platformVersion);
							reqParams.put("integration", integration);
							reqParams.put("device", device);

						} else {
							reqParams.put("platform", platformParams[0]);

						}	// if-else()

						continue;	// do not run in the else part since platform variable will be duplicated.

					}	// if(platform)-else

					// Get all normal url-params
					if(paramsPair[1].contains("?"))	{
						beaconPair = paramsPair[1].split("\\?");
						reqParams.put(paramsPair[0], beaconPair[0]);
					}else{
						reqParams.put(paramsPair[0], paramsPair[1]);
					}

				}

				skipFlag = false;
			}



		} else {
			System.out.println(LOGTAG + " +++ Oops! No \"rqparam\" found in the url, please check the click url...");

		}	// if-else

		// To get value of beacon-ping m=? from url in case of interstitial where value of "m" is appended during the run time.
		if (request.getParameter("m") != null) {
			reqParams.put("m", request.getParameter("m"));
		} else {
			System.out.println(LOGTAG + "Warning: Value of beacon-ping \"m=?\" is not mentioned in the URL...");
		}

		System.out.println(LOGTAG + "Request Parameters: " + reqParams.toString());
		return reqParams;
	}

	// ##############################################################################################################
	// if the 2nd arg is "format", it will return the respone-format type from release url param
	// if releaes=xhtml_async, it returns xhtml or release=imai_async, returns imai
	public String getResponseFormat(HashMap<String, String> reqParameters, String returnInfo) {
		//System.out.println(LOGTAG + " Req parameters: " + reqParameters.toString());

		String[] releaseParams = reqParameters.get("release").split("_");

		if (returnInfo.equalsIgnoreCase("format")) {
			return releaseParams[0];
		} else if (releaseParams.length > 1) {
			return releaseParams[1];	// to return sync or async type
		} else {
			System.out.println(LOGTAG + " ERROR: Parsing release/response-format params, please check the request URL...");
			return "null";
		}

	}	// getResponseFormat()


	// ##############################################################################################################
	public void dbEntry (HashMap<String, String> reqParams, HttpServletResponse response, String dbTable) {

		intServerEnv();	// init server env
		HashMap<String, String> platformInfo = urlParser.getPlatformInfo(reqParams);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String tpClick = "no";
		if (reqParams.get("thirdparty") != null) {
			tpClick = reqParams.get("thirdparty").toString();	
		}

		// Set response content type
		String clickRespType = getResponseFormat(reqParams, "isSync");		// used to set the title of the click action window
		response.setContentType("text/html");
		String title = clickRespType.toUpperCase() + " - " + dbTable + " Database Entry";

		Connection conn = null;
		Statement stmt = null;

		try{
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USERID, PASSWORD);

			// Execute SQL query
			stmt = conn.createStatement();
			String selectQuery = null;
			String tableToOperate = null;
			Integer beaconPing = null;
			String insertQuery = null;

			if (dbTable.equalsIgnoreCase("click")) {
				selectQuery = "SELECT * FROM " + CLICKS_TABLE 
						+ " WHERE testid='" +  reqParams.get("test").toString() + "'" 
						+ " and " + "slot" + "=" + "'" + reqParams.get("slotid").toString() + "'"
						+ " and " + "creative" + "=" + "'" + reqParams.get("creative").toString() + "'"
						+ " and " + "releasetype" + "=" + "'" + reqParams.get("release").toString() + "'"
						+ " and " + "platform" + "=" + "'" + reqParams.get("platform").toString() + "'"
						+ " and " + "version" + "=" + "'" + reqParams.get("platformVersion").toString() + "'"
						+ " and " + "integration" + "=" + "'" + reqParams.get("integration").toString() + "'"
						+ " and " + "device" + "=" + "'" + reqParams.get("device").toString() + "'"
						+ " and " + "thirdparty" + "=" + "'" + reqParams.get("thirdparty").toString() + "'"
						+ ";";

				tableToOperate = CLICKS_TABLE;

			} else {				
				beaconPing = Integer.parseInt(reqParams.get("m"));
				selectQuery = "SELECT * FROM " + BEACON_TABLE 
						+ " WHERE testid='" +  reqParams.get("test").toString() + "'" 
						+ " and " + "slot" + "=" + "'" + reqParams.get("slotid").toString() + "'"
						+ " and " + "creative" + "=" + "'" + reqParams.get("creative").toString() + "'"
						+ " and " + "releasetype" + "=" + "'" + reqParams.get("release").toString() + "'"
						+ " and " + "platform" + "=" + "'" + reqParams.get("platform").toString() + "'"
						+ " and " + "version" + "=" + "'" + reqParams.get("platformVersion").toString() + "'"
						+ " and " + "integration" + "=" + "'" + reqParams.get("integration").toString() + "'"
						+ " and " + "beacon=" + beaconPing
						+ ";";

				tableToOperate = BEACON_TABLE;

			}	// end try()

			System.out.println(LOGTAG + " SELECT QUERY: " + selectQuery);
			ResultSet selectRes = stmt.executeQuery(selectQuery);

			// Insert into the Db if the record is not found, else update the same record with the timestmap and other details
			if (!selectRes.next()) {
				PreparedStatement pst = null;

				if ( dbTable.equalsIgnoreCase("click") ) {
					System.out.println(LOGTAG + " Inserting into table: " + tableToOperate);

					insertQuery = "INSERT INTO " + CLICKS_TABLE + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(insertQuery);

					pst.setString(1, reqParams.get("test"));
					pst.setString(2, timeStamp);
					pst.setString(3, reqParams.get("slotid").toString());
					pst.setString(4, reqParams.get("creative").toString());
					pst.setString(5, reqParams.get("release").toString());
					pst.setString(6, reqParams.get("platform").toString());
					pst.setString(7, reqParams.get("platformVersion").toString());
					pst.setString(8, reqParams.get("integration").toString());
					pst.setString(9, tpClick);	
					pst.setString(10, reqParams.get("device").toString());	
					pst.setString(11, "PASS");

				} else {
					System.out.println(LOGTAG + " Inserting into table: " + tableToOperate);

					insertQuery = "INSERT INTO " + BEACON_TABLE + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
					pst = conn.prepareStatement(insertQuery);
					System.out.println(LOGTAG + " Insert Query: " + insertQuery.toString());

					pst.setString(1, reqParams.get("test"));
					pst.setString(2, timeStamp);
					pst.setString(3, reqParams.get("slotid").toString());
					pst.setString(4, reqParams.get("creative").toString());
					pst.setString(5, reqParams.get("release").toString());
					pst.setString(6, reqParams.get("platform").toString());
					pst.setString(7, reqParams.get("platformVersion").toString());
					pst.setString(8, reqParams.get("integration").toString());
					pst.setInt(9, Integer.parseInt(reqParams.get("m").toString()) );
					pst.setString(10, reqParams.get("device").toString());	
					pst.setString(11, "PASS");	
				}


				int numRowsChanged = pst.executeUpdate();
				System.out.println(LOGTAG + " Insert successful: Inserted total number of rows - " + numRowsChanged);

				pst.close();
				selectRes.close();

			} else {
				// update query execution part:
				String updateQuery = "UPDATE " + tableToOperate + " SET " + "datetime='" + timeStamp + "'";

				updateQuery += " WHERE testid='" +  reqParams.get("test").toString() + "'" 
						+ " and " + "slot" + "=" + "'" + reqParams.get("slotid").toString() + "'"
						+ " and " + "creative" + "=" + "'" + reqParams.get("creative").toString() + "'"
						+ " and " + "releasetype" + "=" + "'" + reqParams.get("release").toString() + "'" 
						+ " and " + "device" + "=" + "'" + reqParams.get("device") + "'";

				if ( dbTable.equalsIgnoreCase("click") ) {
					updateQuery += " and " + " thirdparty='" + tpClick + "'" + ";";
				} else {
					updateQuery += " and " + " beacon='" + beaconPing + "'" + ";";
				}

				System.out.println(LOGTAG + " Updating table: " + tableToOperate + " UPDATE QUERY: " + updateQuery);
				stmt.executeUpdate(updateQuery);

			}

			System.out.println(LOGTAG + " [DB-Entry]: " + reqParams.get("test").toString() + ", " + timeStamp + ", " + reqParams.get("slotid").toString() 
					+ ", " + reqParams.get("creative").toString() 
					+ ", " + reqParams.get("release").toString() 
					+ ", " + platformInfo.get("platform") 
					+ ", " + platformInfo.get("platformVersion") 
					+ ", " + platformInfo.get("integration")
					+ ", " + reqParams.get("device") 
					+ ", PASS" );

		} catch(SQLException se) {
			se.printStackTrace();

		} catch(Exception e) {
			e.printStackTrace();

		} finally {

			try{
				if(stmt!=null)
					stmt.close();

			} catch(SQLException se2){

			}// nothing we can do
			try {
				if(conn!=null)
					conn.close();

			} catch(SQLException se){
				se.printStackTrace();

			}//end finally try
		} //end try

	}

	// ##############################################################################################
	public void getReqParametersFromSdk(HttpServletRequest request) {

		System.out.println("To out-put All the request parameters received from SDK-Request - ");

		Enumeration enParams = request.getParameterNames(); 
		while(enParams.hasMoreElements()){
			String paramName = (String)enParams.nextElement();
			System.out.println(LOGTAG + " " + paramName + "=" + request.getParameter(paramName));
		}
	}



	// ##############################################################################################
	// main()
	public static void main(String[] args) {


	}

}