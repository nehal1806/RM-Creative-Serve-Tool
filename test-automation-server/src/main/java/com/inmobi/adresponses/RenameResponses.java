package com.inmobi.adresponses;

import java.io.File;

import com.inmobi.psocommons.Utils;

public class RenameResponses {

	Utils utils = new Utils();
	
	/*	Author: Mukthar Ahmed, M
	 * 	Comment / ToDo (Delete this when changes are completed):
	 * 	1) Change the dir paths to be picked from either as a command line args or from a config input file. 
	 */
	
	// ###########################################################################################################
	public static void main(String[] args) {
		String responseDir = "/Users/mukthar.ahmed/Data2/toDelete/resp/v-1.1/frameInterstitials/responses/";
		responseFileFormatter(responseDir);
	}	// end main()
	
	
	// ###########################################################################################################
	public static void responseFileFormatter (String responseDir) {
		System.out.println("+++ INFO: Formatting/Renaming ad-response files after generating the directory structure.");
		
		String filePath = "";
		String[] responseTypes = { "imai/android", "imai/ios", "xhtml/android", "xhtml/ios" };
		
		// create dir structure for file movements
		createDirStructure(responseDir);	
		
		for ( String response : responseTypes ) {
			filePath = responseDir + "/" + response;
			System.out.println(filePath);
			fileRename(filePath);
		}
	}	// end responseFileFormatter()
	
	
	// ###########################################################################################################
	// 	Author: Mukthar Ahmed, M
	//	createDirStructures: Generates the required test data based empty directory structure
	//	Requirement: Requred to further move all the files within.
	// Date: 26-Nov'13
	// ###############################################################################################
	public static void createDirStructure(String respDir) {
		String[] responseFormats = { "imai", "xhtml" };
		String[] platformType = { "android", "ios" };
		
		for ( String format : responseFormats ) {
			for ( String platform : platformType ) {
				String inDirPath = respDir + "/" + format.toLowerCase() + "/" + platform.toLowerCase();
				
				File path = new File(inDirPath);
				if (!path.exists()) {
					System.out.println("+ Creating directory structures: "  + path);
					path.mkdirs();
				} else {
					System.out.println("+ Warning: Directory structure is already present!!!" + " - " + path);
				}
				
				//	responses/android_imai_banner_* responses/imai/android/
				String fileInRegex = respDir + "/" + platform.toLowerCase() + "_" + format.toLowerCase() + "_" + "banner" + "_*";
				String fileInDirMove = respDir + "/" + format.toLowerCase() + "/" + platform.toLowerCase() + "/";
				String fileMoveCmd = "mv" + " " + fileInRegex + " " + fileInDirMove;
				Utils.runCmd(fileMoveCmd);
				
			}	// end for (platformTypes)
			
			System.out.println("+++ INFO: Done creating directory structre.");
			
		}	// end for(responseFormats)
		
	}
	
	
	// ###############################################################################################
	// 	Author: Mukthar Ahmed, M
	//	fileRename: Renames the files individually based on the formats and the type of data it holds
	//	Requirement: This is used to rename the file names appropriately as per our test run needs.
	// Date: 26-Nov'13
	// ###############################################################################################
	public static void fileRename(String path) {
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

				System.out.println("Length: " + fileNameParams.length);
				if ( fileNameParams.length == 6) {
					String[] myargs = fileNameParams[5].split("\\.");
					String interstitialFile = fileNameParams[3].toLowerCase() + "_" + fileNameParams[4].toUpperCase() + "_" + myargs[0].toUpperCase() + ".resp";
					String mvCmd = "mv " + path + "/" + files + " " + path + "/" + interstitialFile;
					
					System.out.println(mvCmd);
					Utils.runCmd(mvCmd);

				} 
				else if ( fileNameParams.length == 7 ) {
					String[] myargs = fileNameParams[6].split("\\.");
					String interstitialFile = fileNameParams[4].toLowerCase() + "_" + fileNameParams[3] + "_" + fileNameParams[5].toUpperCase() + "_" + myargs[0].toUpperCase() + ".resp";
					String mvCmd = "mv " + path + "/" + files + " " + path + "/" + interstitialFile;
					
					System.out.println(mvCmd);
					Utils.runCmd(mvCmd);

				} // end if-else()
				
			} // end if-else( listOfFiles )
			
		}	// end for()

	}	// end fileRename()
	
}	// Class()

// ##############################################################################################################################
