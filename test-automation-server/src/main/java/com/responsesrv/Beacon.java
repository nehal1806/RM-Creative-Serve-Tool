package com.responsesrv;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inmobi.psocommons.TouchstoneParsers;
import com.utils.ServerUtils;

/**
 * Servlet implementation class Beacon
 */
public class Beacon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	TouchstoneParsers parsers = new TouchstoneParsers();
	private String LOGTAG = "+ [PSOWEBAPP - BEACON]: ";

	// click-srv-url : http://10.14.100.249:8080/psowebapp/Beacon?test=testAndroidTemplate1&release=imai_async&platform=android_4.2_sdk370&m=101

	// #########################################################################################################
	// constructor
	public Beacon() {
		super();
	}

	// #########################################################################################################
	// doGet() - servlet
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		
		System.out.println("\n" + LOGTAG + "+++ Begin Beacon ++++");
		ServerUtils srvUtils = new ServerUtils();
		HashMap<String, String> reqParams = srvUtils.getReqParams(request);	// hash map holding all the url params parsed
		
		srvUtils.dbEntry(reqParams, response, "beacon");			// make a Db entry call
		
/*		// print all paramters coming from sdk
		srvUtils.getReqParametersFromSdk(request); */		
		
		System.out.println(LOGTAG + "+++ End Beacon ++++");

	}

	// #########################################################################################################
	// doPost() - servlet
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		doGet(request, response);

	}	// doPost()
	
}	// end class
