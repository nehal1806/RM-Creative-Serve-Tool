package com.inmobi.psocommons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class SearchRegEx {

	/**
	 * @param args
	 * @throws IOException 
	 */

	// Variables (public):
	String mriadURL = "<script src=\"http://inmobisdk-a.akamaihd.net/sdk/android/mraid.js\"> </script>";
	String iOsMriadURL = "<script src=\"http://inmobisdk-a.akamaihd.net/sdk/ios/mraid.js\"> </script>";

	// ####################################################################################################
	// mian():
	public static void main(String[] args) throws IOException {
		// generate ad-responses by search and replace placeholders
		generateResponses("/Users/mukthar.ahmed/Data2/toDelete/resp/v-1.1/frameInterstitials/responses", false);

	}	// main()

	
	// ####################################################################################################
	// generateREsponses: 
	public static void generateResponses(String respBaseDir, boolean runTestMode) throws IOException {
		SearchRegEx reg = new SearchRegEx();
		System.out.println("+++ Replacing dummy URLs to place holders at response-dir: " + respBaseDir);
		
		if (runTestMode) {
			File f1 = new File(respBaseDir + "/" + "imai/android/banner_bolt_300X250_300X250.resp");
			reg.replaceInResponseFile(f1, "android", "imai");
			
			File f2 = new File(respBaseDir + "/" + "imai/android/banner_300X250_300X250.resp");
			reg.replaceInResponseFile(f2, "android", "imai");

		} else {

			String interstitialImaiAndroid = respBaseDir + "/" + "imai/android/";
			List<File> androidFilesImaiSync = getFilesList(interstitialImaiAndroid, "files");
			System.out.println("+ total number of files: " + androidFilesImaiSync.size());
			
			for (File f : androidFilesImaiSync) {
				reg.replaceInResponseFile(f, "android", "imai");
			}

			// Renaming all interstitial files to appropriate names
			fileCopyRenameInterstitials(interstitialImaiAndroid);		// back up the file


			String interstitialImai_iOS =  respBaseDir + "/" + "imai/ios/";
			List<File> iosFiles = getFilesList(interstitialImai_iOS, "files");
			System.out.println("+ total number of files: " + iosFiles.size());
			for (File f : iosFiles) {
				reg.replaceInResponseFile(f, "ios", "imai");
			}

			// Renaming all interstitial files to appropriate names
			fileCopyRenameInterstitials(interstitialImai_iOS);		// back up the file

			String interstitialXhtmlAndroid =  respBaseDir + "/" + "xhtml/android/";
			List<File> androidFilesXhtmlAsync = getFilesList(interstitialXhtmlAndroid, "files");
			System.out.println("+ total number of files: " + androidFilesXhtmlAsync.size());
			for (File f : androidFilesXhtmlAsync) {
				reg.replaceInResponseFile(f, "android", "xhtml");
			}

			// Renaming all interstitial files to appropriate names
			fileCopyRenameInterstitials(interstitialXhtmlAndroid);		// back up the file


			String interstitialXhtml_iOS =  respBaseDir + "/" + "xhtml/ios/";
			List<File> iosFilesXhtmlAsync = getFilesList(interstitialXhtml_iOS, "files");
			System.out.println("+ total number of files: " + iosFilesXhtmlAsync.size());
			for (File f : iosFilesXhtmlAsync) {
				reg.replaceInResponseFile(f, "ios", "xhtml");
			}

			// Renaming all interstitial files to appropriate names
			fileCopyRenameInterstitials(interstitialXhtml_iOS);
		
		}	// end if-else(runTestMode)
		
	} // end generateResponse()
	

	// ####################################################################################################
	// fileCopy:  if 2nd arg not specified, copies to .bak file

	public static List<File> getFilesList (String dirPath, String lookingFor) {
		File workingDir = new File(dirPath);

		List<File> fileList = new ArrayList<File>();
		List<File> dirList = new ArrayList<File>();

		for (File file : workingDir.listFiles()) {
			if (file.isDirectory()) {
				dirList.add(file);

			} else {
				fileList.add(file);

			}
		}

		if (lookingFor.equals("files")) {
			return fileList;
		} else {
			return dirList;
		}

	}

	// ####################################################################################################
	// fileCopy:  if 2nd arg not specified, copies to .bak file
	public File fileCopy (File sourceFile) throws IOException {

		File destFile = new File(sourceFile + ".bak");

		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}
		finally {
			if(source != null) {
				source.close();
			}

			if(destination != null) {
				destination.close();
			}
		}

		return destFile;
	}

	// ####################################################################################################
	// fileCopy:  if 2nd arg not specified, copies to .bak file
	public static void fileCopyRenameInterstitials (String path) throws IOException {

		String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 

		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				System.out.println(files);

				String[] fileNameParams = files.split("_");
				for (String s : fileNameParams) {
					System.out.println(s);
				}

				String interstitialFile = null;
				if ( fileNameParams.length == 4 ) {
					interstitialFile = fileNameParams[0] + "_" + fileNameParams[1] + "_" + fileNameParams[2] + "_" + fileNameParams[3];					
				} else if ( fileNameParams.length == 3 ){
					interstitialFile = fileNameParams[0] + "_" + fileNameParams[1] + "_" + fileNameParams[2];
				}

				String mvCmd = "mv " + path + "/" + files + " " + path + "/" + interstitialFile;
				System.out.println(mvCmd);
				Utils.runCmd(mvCmd);

			}
		}

		//return interstitialFile;

	}

	// ####################################################################################################
	// searchAndReplace:  to search for a string within a file and replace
	public void replaceInResponseFile (File infile, String platform, String format) throws IOException {

		//String file = "int_cpc_1024X768_1024X768.new";
		String backupCmd = "mv " + infile + " " + infile + ".bak";
		Utils.runCmd(backupCmd);
		String backedFile = infile + ".bak";

		System.out.println("\n+++ Search and replace block +++");
		System.out.println("\n+ File: " + infile);

		//boolean wasReplaced = false;
		String line = null;
		RegExpCollection regEx = new RegExpCollection();		// reg-ex collector class
		BufferedReader reader = new BufferedReader(new FileReader(backedFile));
		PrintWriter writer = new PrintWriter(new FileWriter(infile));


		while ((line = reader.readLine()) != null) {

			line.trim();	// trimming trailing characters
			if (!line.matches("^\\s*$") ) {			// skip blank lines

				/* 1) imai - 'http:\/\/adoutput.inmobi.com\/clickUrl?at=2&am=3'
				 * 2) xhtml - 'http:\/\/adoutput.inmobi.com\/clickUrl?at=1&am=0'
				 * 3) xhtml, sync - http://adoutput.inmobi.com/clickUrl
				 */				
				String bannerClickUrlMatch = regEx.matchBannerClickUrl(line);
				if (bannerClickUrlMatch != null) {
					System.out.println("\n bannerClickUrlMatch: " + bannerClickUrlMatch);
					line = line.replace(bannerClickUrlMatch, "CLICK-URL" );		// for special cases like back slash should be replace rather replaceAll()
				}

				if (!format.equalsIgnoreCase("xhtml")) {
					String landingUrlMatch = regEx.matchLandingPage(line);
					if (landingUrlMatch != null) {
						System.out.println("\n landingUrlMatch: " + landingUrlMatch);
						line = line.replace(landingUrlMatch, " var landingUrl = \'LANDING-PAGE\'" );		// for special cases like back slash should be replace rather replaceAll()
					}
				}

				if (format.equalsIgnoreCase("xhtml")) {
					String finalLandingUrlMatch = regEx.matchFinalLandingPageXHtml(line);
					if (finalLandingUrlMatch != null) {
						System.out.println("FinalLanding url match: "  + finalLandingUrlMatch);
						line = line.replace(finalLandingUrlMatch, " var finalLandingUrl = window['im_3232_replaceTimeStamp'](\'LANDING-PAGE\');" );
					}
				}

				// Click URL searcha and replace, specific to banner templates/ad-resposnes.
				String functionClickUrlMatch = regEx.matchFunctionClickURL(line);
				if (functionClickUrlMatch != null) {
					System.out.println("\n+ clickUrlMatch: " + functionClickUrlMatch);
					//line = line.replace( clickUrlMatch, clickURL);
					line = line.replace( functionClickUrlMatch, " _im_imai.pingInWebView('CLICK-URL')");
				}


				// Dhiren: (4-Dec-2013) 
				// This "might" be redundant, need to clean up based on usage/analysis
				// Reason: Its redundant since we are not using any xml formats of ad-responses.
				String clickUrlAtXml = regEx.matchClickUrlFromXml(line);
				if (clickUrlAtXml != null) {
					System.out.println("\n+ Click URL at xhtml file: " + clickUrlAtXml);
					line = line.replace(clickUrlAtXml, "<AdURL>CLICK-URL</AdURL>");
				} 

				String clickUrlMatch = regEx.matchNullClickURL(line);
				if (clickUrlMatch != null) {
					System.out.println("\n+ clickUrlMatch: " + clickUrlMatch);
					//line = line.replace( clickUrlMatch, clickURL);
					line = line.replaceAll( clickUrlMatch, " var clickUrl = 'CLICK-URL'" );
				}

				String clickUrlOnlyMatch = regEx.matchClickUrlInterstitial(line);
				if (clickUrlOnlyMatch != null) {
					System.out.println("\n+ clickUrlMatch: " + clickUrlOnlyMatch);
					//line = line.replace( clickUrlOnlyMatch, "u=\"CLICK-URL\"" );
					line = line.replace( clickUrlOnlyMatch, "\"CLICK-URL\"" );
				}

				String imgSrcMatch = regEx.matchImgSrcPattern(line);
				if (imgSrcMatch != null) {
					System.out.println("\n+ Image search match: " + imgSrcMatch);
					//line = line.replace(imgSrcMatch, imageSrcURL);responses/xhtml/android/banner_bolt_300X250_300X250.resp
					line = line.replaceAll( imgSrcMatch, "src=\"IMAGE-SOURCE\"");
				} 

				// running into android or ios platform
				if (platform.equalsIgnoreCase("android")) {
					String mraidJsUrlMatch = regEx.matchMRaidJsPattern(line);
					if (mraidJsUrlMatch != null) {
						System.out.println("\n+ MRaid URL: " + mraidJsUrlMatch);
						//line = line.replace(mraidJsUrlMatch, "<script src='MRAID-URL'");
						line = line.replaceAll( mraidJsUrlMatch, mriadURL );
					}
				} else {
					String mraidJsUrlMatch = regEx.matchMRaidJsPattern(line);
					if (mraidJsUrlMatch != null) {
						System.out.println("\n+ MRaid URL: " + mraidJsUrlMatch);
						//line = line.replace(mraidJsUrlMatch, "<script src='MRAID-URL'");
						line = line.replaceAll( mraidJsUrlMatch, iOsMriadURL );
					}
				}

				String beaconUrlMatch18 = regEx.matchBeaconURLFromScriptM18(line);
				if (beaconUrlMatch18 != null) {
					System.out.println("\n+ Beacon URL: " + beaconUrlMatch18);
					line = line.replace( beaconUrlMatch18, "\"BEACON-URL-18\"" );
				}

				String beaconUrlMatchM3 = regEx.matchBeaconURLFromScriptCloseM3(line);
				if (beaconUrlMatchM3 != null) {
					System.out.println("\n+ Beacon URL: " + beaconUrlMatchM3);
					line = line.replace( beaconUrlMatchM3, "\"BEACON-URL-3\"" );
				}


				String beaconUrlMatch101 = regEx.matchBeaconURLM101(line);
				if (beaconUrlMatch101 != null) {
					System.out.println("\n+ Beacon URL: " + beaconUrlMatch101);
					//line = line.replace(beaconUrlMatch, beaconURL);
					line = line.replaceAll( beaconUrlMatch101, "img src=\"BEACON-URL-101\"" );
				}

				// <img src=tracking.png/> - set tracking url to null
				String trackingInfo = regEx.matchTrackingPng(line);
				if (trackingInfo != null) {
					System.out.println("\n+ Tracking URL at xhtml file: " + trackingInfo);
					line = line.replace( trackingInfo, "<img src=\"BEACON-URL-111\" style=\"display:none;\"/>" );
				}


				String orientationLock = regEx.matchOrientationLock(line);
				if (orientationLock != null) {
					System.out.println("\n+ Orientation lock: " + orientationLock);
					line = line.replace( orientationLock, "lockOrientation:0" );
				}


				String bluekaiUrl = regEx.matchBluekaiFrame(line);
				if (bluekaiUrl != null) {
					System.out.println("\n+ bluekai URL: " + bluekaiUrl);
					//line = line.replace( bluekaiUrl, "<iframe src=\"www.google.com\"" );
					line = line.replace( bluekaiUrl, "" );
				}

				//	var finalLandingUrl = replaceTimeStamp('market:\/\/details?id=com.inmobi.test');
				String marketUrl = regEx.matchMarketUrl(line);
				if (marketUrl != null) {
					System.out.println("\n+ Click URL at xhtml file: " + marketUrl);
					line = line.replaceAll( marketUrl, "var finalLandingUrl = replaceTimeStamp('LANDING-PAGE');" );
				}

				//	var finalLandingUrl = replaceTimeStamp('market:\/\/details?id=com.inmobi.test');
				String urlRedirect35x = regEx.matchUrlRedirect35x(line);
				if (urlRedirect35x != null) {
					System.out.println("\n+ URL Redirect server match: " + urlRedirect35x);
					line = line.replace( urlRedirect35x, "https://prclick.inmobi.com/redir/" );
				}

				if (!regEx.iSHtmlbody(line)) {
					writer.println(line);	// write the buffered line with searched & replaced pattern.
				}

			}

		}

		// close the file handlers
		reader.close();
		writer.close();

	}	// end searchAndReplace()


	// ####################################################################################################
	// fileCopy:  Copies to the given dest file name. If 2nd arg not specified, copies to .bak file
	public File fileCopy (File sourceFile, File destFile) throws IOException {

		if(!destFile.exists()) {
			destFile.createNewFile();
		}

		FileChannel source = null;
		FileChannel destination = null;

		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(source, 0, source.size());
		}

		finally {
			if(source != null) {
				source.close();
			}
			if(destination != null) {
				destination.close();
			}
		}

		return destFile;
	}


}	// end class()
