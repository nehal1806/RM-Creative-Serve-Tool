package com.responsesrv;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inmobi.psocommons.TouchstoneParsers;
import com.utils.FetchData;
import com.utils.HtmlBuilder;
import com.utils.ServerUtils;

public class AdFormatsResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String LOGTAG = "+ [PSOWEBAPP - AdFormatsResults]: ";

	static String psoResourcePkg = "/com/inmobi/pso";
	TouchstoneParsers parsers = new TouchstoneParsers();
	ServerUtils srvUtils = new ServerUtils();
	HashMap<String, String> reqParams = new HashMap<String, String>();

	public AdFormatsResults() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String groupByDimension = request.getParameter("groupbydim");
		String dimValue = request.getParameter("dimvalue");
		
		String DBCONFIG = psoResourcePkg + "/configs/config.properties";
		InputStream inputStream = AdFormatsResults.class.getClassLoader().getResourceAsStream(DBCONFIG);
		Properties prop = new Properties();
		prop.load(inputStream);
		
		StringBuilder responseOut = new StringBuilder();	// response is to be build.
		String title = "Ad Fromats Test Results";
		response.setContentType("text/html");
		String docType =
				"<!doctype html public \"-//w3c//dtd html 4.0 " +
						"transitional//en\">\n";
		responseOut.append(docType +
				"<html>\n" +
				"<head><title>" + title + "</title></head>\n" +
				"<body bgcolor=\"#f0f0f0\">\n" +
				"<h1 align=\"center\">" + title + "</h1>\n");

		// Variables:
		FetchData dbFetcher = new FetchData();
		ArrayList<String> dimensionValues = new ArrayList<String>();
		if (dimValue.equalsIgnoreCase("all")) {
			dimensionValues = dbFetcher.getValuesToQuery( groupByDimension.toLowerCase() ) ;
			
		} else {
			System.out.println("\n" + LOGTAG + " DEBUG: " + dimValue + " => " + prop.getProperty(dimValue));
			if (groupByDimension.equalsIgnoreCase("slot")) {
				dimensionValues.add(prop.getProperty(dimValue));
			} else {
				dimensionValues.add(dimValue);
			}
			
		}	// if-else()
		
		
		// Fetching headersInfo list/table column titles from config.properties
		ArrayList<String> headersInfo = new ArrayList<String>();
		String tableHeaders = prop.getProperty("headers");

		String[] headersList = tableHeaders.split(",");
		for (String header : headersList) {
			headersInfo.add(header);
		}
		
		@SuppressWarnings("unchecked")
		Map<String, ArrayList<String>> resFromDb = new LinkedHashMap();
		resFromDb.put("headers", headersInfo);
		
		// Add data to the data-hash
		for (String inDimension: dimensionValues) {
			System.out.println(LOGTAG + " Dimension: " + inDimension);
			
			resFromDb.put(inDimension, new ArrayList<String>());
			
			try {
				ArrayList<String> inputQueryResults = dbFetcher.testResults(groupByDimension, inDimension);
				resFromDb.put(inDimension, inputQueryResults);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println(LOGTAG + "\n+++++++++++++++++" + "\n" + "Data fetched from Database...... ");
		System.out.println(resFromDb.toString());

		String htmlContent = ""; //HtmlBuilder.build("Test Results");
		htmlContent = "<html>";
		htmlContent += HtmlBuilder.htmlHead("Test results - Ad Formats");
		htmlContent += HtmlBuilder.htmlScriptData();
		htmlContent += HtmlBuilder.htmlStyleData();
		htmlContent += HtmlBuilder.htmlBody(resFromDb);
		htmlContent += "\n" + "</html>";
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(htmlContent);
		out.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
