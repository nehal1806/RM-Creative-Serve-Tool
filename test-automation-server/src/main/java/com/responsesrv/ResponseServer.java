package com.responsesrv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.ServerUtils;


//Extend HttpServlet class
public class ResponseServer extends HttpServlet {

	public static final long serialVersionUID = 1L; // for updation of the class to new one
	String LOGTAG = "+ [PSOWEBAPP - RESPONSE-SERVER]: ";

	String psoResourcePkg = "/com/inmobi/pso";
	String CONFIGFILE = psoResourcePkg + "/configs/server.properties";
	ServerUtils srvUtils = new ServerUtils();

	// ########################################################################################
	// init - method
	public void init() throws ServletException {
		// Do required initialization


	}	// init()


	// ########################################################################################
	// doGet() - to fetch reqeust
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("\n\n" + LOGTAG + " + Ad Request Ping...");

		/*
		 * need to handle xml as well here:
		 * */
		HashMap<String, String> reqParams = srvUtils.getReqParams(request);	// hash map holding all the url params parsed
		String responseFormat = srvUtils.getResponseFormat(reqParams, "format");
		if (responseFormat.matches("xhtml")) {
			response.setContentType("text/xml; charset=UTF-8"); // Set response content type
			System.out.println(LOGTAG + " Binding response as \"text/xml\"");

		} else {
			response.setContentType("text/html; charset=UTF-8");
			System.out.println(LOGTAG + " Binding response as \"text/html\"");
		}

		//inputRespFile = "/responsesNew/imai_sync/android/banner_320X48_300X50.new";
		String inputRespFile = srvUtils.responseFileFetch(reqParams);

		PrintWriter out = response.getWriter();
		String urlConfigFile = ResponseServer.class.getResource(CONFIGFILE).getFile();
		StringBuilder responseObjDebug = srvUtils.buildResponse(reqParams, urlConfigFile, inputRespFile, out);	// BUILDING RESPONSE CONTENT

		System.out.println("\n" + LOGTAG + "++++++++++++++++ RESPONSE BEGIN +++++++++++++++");
		System.out.println(responseObjDebug.toString());
		System.out.println("\n" + LOGTAG + "++++++++++++++++ RESPONSE END +++++++++++++++\n");

		
		/*	// print all paramters coming from sdk
		srvUtils.getReqParametersFromSdk(request);
		 */
		
	}	// doGet()

	// ##############################################################################
	// post response calll:
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {

		System.out.println(LOGTAG + "[SDK Request] : " + request.getRequestURI());
		System.out.println(LOGTAG + "[SDK Response] : " + response.toString());
		doGet(request, response);	// get response


	}	// doPost()

}	// end class