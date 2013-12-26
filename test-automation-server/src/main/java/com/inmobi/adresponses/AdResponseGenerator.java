package com.inmobi.adresponses;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.inmobi.psocommons.SearchRegEx;
import com.inmobi.psocommons.Utils;




/**
 * Servlet implementation class AdResponseGenerator
 */
public class AdResponseGenerator extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// location to store file uploaded
	private static final String UPLOAD_DIRECTORY = "workspace";
	private static String RELEASE_TAG = "default";
	private static final String EXTRACT_DIRECTORY = "WEB-INF/classes/com/inmobi/pso/responsesNew/";

	// upload settings
	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	private String message = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("++++++++++++ Directory: "  + request.getParameter("directory"));

		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");

	}	// doGet()

	/**
	 * Upon receiving file upload submission, parses the request to read
	 * upload data and saves the file on disk.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			// if not, we stop here
			PrintWriter writer = response.getWriter();
			writer.println("Error: Form must has enctype=multipart/form-data.");
			writer.flush();
			return;
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);        // sets memory threshold - beyond which files are stored in disk
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));        // sets temporary location to store files

		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setFileSizeMax(MAX_FILE_SIZE);        // SETS MAXIMUM SIZE OF UPLOAD FILE
		upload.setSizeMax(MAX_REQUEST_SIZE);        // sets maximum size of request (include file + form data)

		// constructs the directory path to store upload file
		// this path is relative to application's directory
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
		String extractPath = getServletContext().getRealPath("") + File.separator + EXTRACT_DIRECTORY;

		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// parses the request's content to extract file data
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// iterates over form's fields
				for (FileItem item : formItems) {

					if ( item.isFormField() ) {
						RELEASE_TAG = item.getString();
						System.out.println("+ releaseTag: " + RELEASE_TAG);
					}


					// processes only fields that are not form fields
					if (!item.isFormField()) {
						String uploadedFileName = new File(item.getName()).getName();
						String uploadedFile = uploadPath + File.separator + uploadedFileName;

						File storeFile = new File(uploadedFile);	// not in use as of now 
						System.out.println("+++ " + uploadedFileName + "\n+++ storeFile: " + storeFile);

						// saves the file on disk
						item.write(storeFile);
						message = "Upload has been done successfully!";

						processAdResponses(uploadedFile, extractPath);	// process all the ad-responses

					}

				}
			}
		} catch (Exception ex) {
			message = "There was an error: " + ex.getMessage();
		}

		doGet(request, response);

	}	// doPost()

	// ##############################################################################################################################
	/* 	Description: 
	 * 	(1) this method checks for response dir existence
	 * 	(2) extracts zip file to a path
	 * 	(3) rename extracted response dir to release path
	 * 	(4) 
	 */
	public static void processAdResponses(String zipFile, String zipExtractPath) {
		
		// Check if responsesNew dir exists...
		File extractDir = new File(zipExtractPath);
		if (!extractDir.exists()) {
			extractDir.mkdir();
		}

		File releaseDataDir = new File(zipExtractPath + RELEASE_TAG);
		File releaseDataBackup = new File(zipExtractPath + RELEASE_TAG + ".bak");
		
		if ( releaseDataDir.exists() ) {
			System.out.println("+++ INFO: " + releaseDataDir + ", File/extract directory found, backing up the directory...\n + " + releaseDataBackup);
			Utils.deleteDir(releaseDataBackup);
			System.out.println("+ Backing up the original data directory...");
			if ( Utils.renameDir(releaseDataDir.toString(), releaseDataBackup.toString())) {
				System.out.println("+ Successfully renamed... ");
			}
		}
		
		Utils.uncompress(zipFile, zipExtractPath, "zip");		// extract the zip file
		String renameCmd = "mv " + zipExtractPath + "responses" + " " + zipExtractPath + RELEASE_TAG;
		Utils.runCmd(renameCmd);

		adResponseFormatter(zipExtractPath + RELEASE_TAG);		// rename all response files to the final test file names

	}	// processAdResponses()

	// ##############################################################################################################################
	// Description: Renaming ad-responses as per our test needs
	// Author: Mukthar Ahmed, M - mukthar.ahmed@inmobi.com
	// Date: 13-Dec'13
	public static void adResponseFormatter(String responseDir) {
		RenameResponses.responseFileFormatter(responseDir);
		try {
			SearchRegEx.generateResponses(responseDir, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// ##############################################################################################################################   

}
// ##################################################################################################################################