
package com.inmobi.psocommons;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Utils {

	//====================================================================================================================================
	
	public static void main(String[] args) {
		deleteDir(new File("/home/archana/INMOBI/github/org-archana/touchstone/Unification/target/results/tmp/dwh"));

	}	
	
	//====================================================================================================================================	
	/* Description: Compare 2 arrays, return boolean
	 * Author: Mukthar.Ahmed@inmobi.com
	 * DAte: 01-Jan'13
	 */
	public static boolean arrayCompare (Object[] expectedArray, Object[] actualArray) {

		boolean compareStatus = false;
		compareStatus = ( expectedArray.length == actualArray.length ) ? true : false;
		if ( expectedArray.length != actualArray.length ) {
			System.out.println("+++ WARNING: Array lengths DO NOT MATCH...");
			System.out.println("+++ Length mismatched: " + expectedArray.length + " To " + actualArray.length);
			return compareStatus;
		}

		int iterationSize = ( expectedArray.length > actualArray.length ) ? expectedArray.length : actualArray.length;
		System.out.println("\n+++ Begin Comparision +++");
		System.out.println("Iteration Size: " + iterationSize + "\nComparing values....");
		
		for (int i = 0; i < iterationSize; i++) {
			System.out.println(expectedArray[i] + " = " + (actualArray[i]));
			if ( expectedArray[i].equals(actualArray[i]) ) {
				compareStatus = true;

			} else {
				System.out.println("+++ WARNING: Values DO NOT MATCH...");
				return compareStatus = false;
			}
		}

		System.out.println("+++ End Comparision +++\n");
		return compareStatus;
	}

	//====================================================================================================================================
	/*
	 * Description: To run any given unix command. Unix commands are explicitly handled using /bin/sh
	 * 				Run's only on unix or unix flavor OS machines
	 * Author: mukthar.ahmed@inmobi.com
	 * Date: 01-Jan'13
	 */

	public static int runCmd (String inCommand) {

		String s = null;
		String[] myCommand = new String[3];
		myCommand[0] = "/bin/sh";
		myCommand[1] = "-c";
		myCommand[2] = inCommand;

		System.out.println("\nCOMMAND: " + inCommand + "\n");
		Process p = null;
		
		try {

			// run the Unix "ps -ef" command
			// using the Runtime exec method:
			p = Runtime.getRuntime().exec(myCommand);
			p.waitFor();

			BufferedReader stdInput = new BufferedReader(new 
					InputStreamReader(p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new 
					InputStreamReader(p.getErrorStream()));

			// read the output from the command
			if ((stdInput.readLine()) != null) { System.out.println("COMMAND OUT:\n");  } 
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			if ((stdError.readLine()) != null) { System.out.println("COMMAND ERROR:\n"); }
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}
		}
		
		catch (Exception e) {
			System.out.println("EXCEPTION OCCURRED: ");
			e.printStackTrace();
		}
		
		return(p.exitValue());
	}	// End runCmd

	//====================================================================================================================================

	// Method to return DateStamp based on the format required.
	public static String GetDateStamp () {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
		//Date date = new Date(0);
		//System.out.println(dateFormat.format(date));

		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));

		return dateFormat.format(cal.getTime());

	} // end getDateStamp()


	//====================================================================================================================================
	// Cat *.* files: 
	public static void catFiles (String inPath, String inFileTypes, String outFile) {
		System.out.println("Concatinate files...");
		System.out.println(inPath + inFileTypes + " TO " + outFile);
		String catCMD	= "cat " + 
						  inPath +
				"/" + inFileTypes + 
				" > " + outFile;
		try {
			Utils.runCmd(catCMD);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	// end catFiles()


	//====================================================================================================================================
	/*
	 * Creates/Deletes a file
	 * fileSystemObject(File file-name, String "create/delete"); 
	 * Author: Mukthar.ahmed@inmobi.com
	 * Date: 28-Dec'12
	 * 
	 */
	public static boolean deleteFile (File fileName) {

		boolean myStatus = false;
		String myMsg = null;
		
		// creates file, if exists
		myStatus = fileName.delete() ;
		if ( myStatus ) {
			myMsg = "Successfully DELETED the file: " + fileName;
			
		} else {
			myMsg = fileName + "Could not delete the file...";

		} // if-else

		System.out.println(myMsg);
		return myStatus;

	} // fileSystemObject()

//===================================================================================================================================
	/*
	 * Deletes a file
	 * deleteFile(String <file-name>); 
	 * Author: Mukthar.ahmed@inmobi.com
	 * Date: 28-Dec'12
	 * 
	 */	
	public static boolean deleteFile (String fileName) {

		boolean myStatus = false;
		String myMsg = null;
		
		File myFile = new File(fileName);
		
		// delete file, if exists
		myStatus = myFile.delete() ;
		if ( myStatus ) {
			myMsg = "Successfully DELETED the file: " + myFile;
			
		} else {
			myMsg = myFile + "Could not delete the file...";

		} // if-else

		System.out.println(myMsg);
		return myStatus;

	} // fileSystemObject()

//====================================================================================================================================
	/*
	 * Creates/Deletes a directory
	 * createDeleteDir(String dir-path, String "create/delete"); 
	 * Author: Mukthar.ahmed@inmobi.com
	 * Date: 28-Dec'12
	 * 
	 */
	public static void createDeleteDir (String dirName, String fsoOperation) {
		System.out.println("Directory to operate on: " + dirName);		
		String myStatus = "Failed due to some error";

		File directory = new File(dirName);

		// creates dir, if exists
		if ( (fsoOperation.toLowerCase().equals("create")) && (!directory.exists()) ) {
			directory.mkdirs();
			myStatus = "Successfully created " + dirName;

		} else if (fsoOperation.toLowerCase().equals("delete")) {
			directory.deleteOnExit();
			myStatus = "Successfully deleted " + dirName;

		} else {
			myStatus = "Directory already exists.. " + dirName;				

		} // if-else

		System.out.println(myStatus);

	} // fileSystemObject()

	//====================================================================================================================================	
	
	public static void uncompress (String compressedBundle, String outputPath, String fileType) {
		String command = null;
		
		if ( fileType.equalsIgnoreCase("zip") ) {
			if ( isOperatingSystem("Linux") ) {
				command = "unzip " + compressedBundle + " -d " + outputPath;
			} else if ( isOperatingSystem("Mac") ) {
				command = "tar -xf " + compressedBundle + " -C " + outputPath;
			}
			
		} else if ( fileType.equalsIgnoreCase("lzo") ) {
			command = "lzop -d " + compressedBundle + " -o " + outputPath;	// untested and un-used.
			
		} else if ( fileType.equalsIgnoreCase("gz") ) {
			command = "gzip -cd " + compressedBundle + " > " + outputPath;
			
		} else if ( fileType.equalsIgnoreCase("tar") ) {
			command = "tar -xf " + compressedBundle + " -C " + outputPath;
			
		}

		runCmd(command);	// execute the final command
	
	} // end uncompress

	//====================================================================================================================================	

	public static int runPig(HashMap<String,String> map,String pigScript) throws InterruptedException{
		
		String CMD = "/usr/bin/pig -x local ";
	
		for ( Object k : map.keySet() ) {
			CMD = CMD.concat(" -param " + k + "=" + map.get(k));
		}
		
		CMD = CMD.concat(" " + pigScript);
		return(Utils.runCmd(CMD));	
	}
	

    //====================================================================================================================================  
    /* Description: compares 2 files. Returns true if files are similar, false if different and null if files are not found
     * Author: Archana@inmobi.com
     * DAte: 01-Jan'13
    */

    public static Boolean diff2File(String file1, String file2) throws Exception {

            String cmd = "diff " + file1 + " " + file2;
            int status = Utils.runCmd(cmd);
            Boolean diffStatus = null;
            if(status == 1) {
                    diffStatus = false;
            } else if(status == 0){
                    diffStatus =  true;
            } else {
                    System.out.println("File not found");
            }
            return diffStatus;
    }
    
	//====================================================================================================================================	
	
	public static void compress (String inFile, String fileType) {
//		@SuppressWarnings("unused")
		String command = null;
		
		if ( fileType.equalsIgnoreCase("lzo") ) {
			command = "lzop " + inFile;	// untested and un-used.
			
		} else if ( fileType.equalsIgnoreCase("gz") ) {
			
		} else if ( fileType.equalsIgnoreCase("tar") ) {
			
		}

		runCmd(command);
	
	} // end uncompress
	
	//====================================================================================================================================	
	
    public static boolean deleteDir(File dir) {
	   
    	if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                        return false;
	                }
	        }
	    }
	    return dir.delete();
    }
    
    //====================================================================================================================================
    /*
     * Description: Unix style of renaming directories.
     * Author: Mukthar Ahmed, M (muthar.ahmed@inmobi.com)
     * Date: 16-Dec'13
     * Enhancement: Currently cannot handle renaming of a dir if its a windows OS
     */
    public static boolean renameDir (String origDirPath, String destDirPath) {
    	boolean successs = false;
    	String mvCmd = "mv " + origDirPath + " " + destDirPath;
    	int rValue = runCmd(mvCmd);
    	if ( rValue == 0 ) {
    		successs = true;
    	} 
		return successs;
    }
    
    //====================================================================================================================================  
    /* Description: Return the OS name
     * Author: Mukthar Ahmed, mukthar.ahmed@inmobi.com
     * Date: 13-Dec'13
    */
    public static boolean isOperatingSystem (String osNameCheck) {
    	boolean osCheckFlag = false;
    	
    	if (System.getProperty("os.name").startsWith(osNameCheck)) {
            osCheckFlag = true;
        }
    	return osCheckFlag;
    }
	
} // End Utils()
