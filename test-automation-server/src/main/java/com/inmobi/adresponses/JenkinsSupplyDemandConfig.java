package com.inmobi.adresponses;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.inmobi.psocommons.TouchstoneParsers;



public class JenkinsSupplyDemandConfig {

	static String LOGTAG = "[JenkinsSupplyDemandConfing]: ";
	static String psoResourcePkg = "/com/inmobi/pso";
	static String CONFIGFILE = psoResourcePkg + "/configs/jenkins.properties";
	static String jeknkinsDir = "jenkins";
	public String configFile = JenkinsSupplyDemandConfig.class.getResource(CONFIGFILE).getFile();
	//static String CONFIGFILE = psoResourcePkg + "/configs/interstitials_frame.properties";

	//id=interstitial_320X480_320X480.new, platform=android, format=imai, ad_type=banner, slot_width=320, slot_height=480, creative_width=320, creative_height=480
	//id=100, platform=android, format=html, ad_type=banner, slot_width=320, slot_height=480, creative_width=320, creative_height=480
	//id=101, platform=android, format=html, ad_type=banner_bolt, slot_width=320, slot_height=480, creative_width=320, creative_height=480
	
	// #######################################################################################################################
	/* MAIN
	 */
	public static void main(String[] args) {
		// Flags for generating jenkins template files
		boolean bannerTemplate = true;
		boolean interstitialTemplate = true;
		boolean customBannerSino = true;
		boolean customInterstitialWhatsMyIQ = true;
		boolean customInterstitialBase = true;
		boolean customBannerBase = true;
		
		JenkinsSupplyDemandConfig configGenerator = new JenkinsSupplyDemandConfig();
		if ( bannerTemplate ) {
			configGenerator.generateConfigs("Banner");
		}
		
		if ( interstitialTemplate ) {
			configGenerator.generateConfigs("Interstitial");
		}
		
		if ( customBannerSino ) {
			configGenerator.generateConfigs("Banner_Sino");
			
		}

		if ( customInterstitialWhatsMyIQ ) {
			configGenerator.generateConfigs("Interstitial_WhatsMyIQ");
			
		}
		if ( customInterstitialBase ) {
			configGenerator.generateConfigs("Interstitial_Custom");
			
		}
		if ( customBannerBase ) {
			configGenerator.generateConfigs("Banner_Custom");
			
		}
	}	// end main()

	
	// #######################################################################################################################
	/* Config file generator module:
	 */
	public void generateConfigs(String configTag) {
		TouchstoneParsers parser = new TouchstoneParsers();		
		File outFile = new File(jeknkinsDir + "/" + configTag + "_" + "TestParamFile");		// out file where the data is generated.

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(outFile));
		} catch (IOException e) {
			e.printStackTrace();
		}

		HashMap<String, HashMap<String, String>> jenkinConfig = parser.readPropsFile(
				new File(configFile)
				);

		System.out.println(LOGTAG + " Jenkins Configs: " + jenkinConfig.toString());
		
		String[] platformList = jenkinConfig.get(configTag).get("platform").split(",");
		String[] formatList = jenkinConfig.get(configTag).get("format").split(",");
		String[] adTypeList = jenkinConfig.get(configTag).get("adtype").split(",");

		String finalConfigLine = null;

		String[] configTagSplit = configTag.split("_");
		String id = configTagSplit[0];		// id of the template, banner or interstitials
		int slotSizeRange = Integer.parseInt(jenkinConfig.get(configTag).get("totalslots"));		

		for ( String platform : platformList ) {
			for (String format : formatList) {
				for ( String adType : adTypeList ) {

					for ( int slotSize=1; slotSize <= slotSizeRange; slotSize++ ) {
						String[] slotList = jenkinConfig.get(configTag).get("slot" + slotSize).split(",");
						String[] creativeList = jenkinConfig.get(configTag).get("creative" + slotSize).split(",");
						System.out.println(slotList.toString() + " + +++++ ");

						for ( String slot : slotList ) {
							String[] slotParams = slot.split("x");

							for ( String creative : creativeList ) {
								String[] creativeParams = creative.split("x");

								// Change to handle platform-type since jenkins job needs it as iphone or android
								String platformType = ""; 
								if ( platform.equalsIgnoreCase("ios") || platform.equalsIgnoreCase("iphone") ) {
									platformType = "iphone";
								} else {
									platformType = platform;
								}
								
								
								// Change format-type to html since jenkins job needs it as html or imai
								String formatType = "";
								if ( format.equalsIgnoreCase("xhtml") ) {
									formatType = "xhtml";
								} else {
									formatType = "imai";
								}
								
								finalConfigLine = "id=" + platform + "_" + format + "_" + adType + "_" +
										id + "_" + 
										slot + 
										"_" + creative + ".resp" + 
										", " + 
										"platform=" + platformType + 
										", " + "format=" + formatType + 
										", " + "ad_type=" + adType + 
										", " + "slot_width=" + slotParams[0] +
										", " + "slot_height="  + slotParams[1] + 
										", " + "creative_width=" + creativeParams[0] + 
										", " + "creative_height=" + creativeParams[1];


								System.out.println(LOGTAG + " Line: " + finalConfigLine);
								writer.println(finalConfigLine);

							}			

						}
					}
				}
			}
		}

		writer.close();

	} // end generateConfigs()


}
