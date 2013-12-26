package com.responsesrv;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inmobi.psocommons.TouchstoneParsers;
import com.utils.ServerUtils;


public class Clicks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String LOGTAG = "+ [PSOWEBAPP - CLICK-Server]: ";

	TouchstoneParsers parsers = new TouchstoneParsers();
	ServerUtils srvUtils = new ServerUtils();

	HashMap<String, String> reqParams = new HashMap<String, String>();

	public Clicks() {
		super();
	}


	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {

		System.out.println("\n" + LOGTAG + "+++ Begin Click +++");
		reqParams = srvUtils.getReqParams(request);	// hash map holding all the url params parsed
		//System.out.println(LOGTAG + "Request Param Map: " + reqParams.toString());

		srvUtils.intServerEnv();
		String localRedirectSite = "http://" + srvUtils.hostIP + ":8080/psowebapp/LocalRedirect.jsp";
		String landingPage = "http://" + srvUtils.hostIP + ":8080/psowebapp/LandingPage.jsp";
		String appLaunchProtocol = "adtest://";

		String clickRespType = srvUtils.getResponseFormat(reqParams, "isSync");
		boolean sync = (clickRespType.equalsIgnoreCase("sync") ? true : false);
		System.out.println(LOGTAG + " Platform = " + reqParams.get("platform") + " ; sync = " + sync + "; Redirecting to app-invoke trigger: " + localRedirectSite);
		
		if (sync) {
			if (reqParams.get("platform").equalsIgnoreCase("ios") ) {
				response.sendRedirect(localRedirectSite);	// redirect towards jsp which will launch back the test-app

			} else {
				response.sendRedirect(landingPage);	// redirect towards jsp which will launch back the test-app

			}	// if-else
			
		} else {	
			// in case of async iOS only, else do nothing in case of android
			if (reqParams.get("platform").equalsIgnoreCase("ios") ) {
				response.setHeader("Refresh", "2; URL=" + appLaunchProtocol);
			}
			
		}	// if(sync) - else

		// make a Db entry call
		srvUtils.dbEntry(reqParams, response, "click");
		System.out.println(LOGTAG + "+++ End Click +++");

	}	// doGet()


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//doGet(request, response);

	}	// doPost()


}	// Click.java servlet class

