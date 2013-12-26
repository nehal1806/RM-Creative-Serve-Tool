package com.inmobi.di;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextEngineResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String contextEngineHeaderKey = "x-inmobi-ph-url";
	private String contextEngineMockHeaderValue = "";
	private String adResponse = "<AdResponse><Ads number=\"1\"><Ad type=\"text\" actionType=\"2\" actionName=\"call\" width=\"320\" height=\"48\"> <AdURL>http://10.14.100.248:8080/phoenix/click/c.asm/3/t/v8s/g1m/2/b/a5/u/0/0/0/x/d565953e-0130-1000-d74f-000117450003/1/fdba4487</AdURL> <![CDATA[ <div style=\"height:48px;background:url(http://r.w.inmobi.com/IphoneActionsData/background.png);width:100%25;\" id=\"im_c\"> <div style=\"margin:0 auto;width:320px;\"> <div style=\"font-size:13px;font-weight:bold;font-family:helvetica;color:white;float:left;margin:5px;width:260px;max-width:600px;\"> <a style=\"text-decoration:none\" href=\"http://10.14.100.248:8080/phoenix/click/c.asm/3/t/v8s/g1m/2/b/a5/u/0/0/0/x/d565953e-0130-1000-d74f-000117450003/1/fdba4487\" id=\"im_text\">Call free in all the countries!</a> </div> <span style=\"font-family:helvetica;font-size:10px;margin-left:-30px;width:75px;position:absolute;color:#989898;margin-top:32px;\">Ads by <span style=\"color:DF2B31;\">InMobi</span></span> </div> <style> body {margin:0px;} </style> ]]> </Ad></Ads></AdResponse>";

    public ContextEngineResponse() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		contextEngineMockHeaderValue = "http://" + serverName + ":"
				+ serverPort + "/MockServer/ContextEngineMock";
		String enable, interval, duration, seturl;

		interval = request.getParameter("interval");
		duration = request.getParameter("duration");
		enable = request.getParameter("enable");
		seturl = request.getParameter("seturl");

		if ((enable != null)) {
			if (enable.equals("true")) {
				response.setHeader("x-inmobi-ph-enable", enable);
				if (duration != null)
					response.setHeader("x-inmobi-ph-lse-sec", duration);
				if (interval != null)
					response.setHeader("x-inmobi-ph-intvl-sec", interval);
				if (seturl != null) {
					response.setHeader(contextEngineHeaderKey, seturl);
				}
				if (seturl == null) {
					response.setHeader(contextEngineHeaderKey,
							contextEngineMockHeaderValue);
					System.out.println("url set :  "
							+ contextEngineMockHeaderValue);
				}
			}
		}
		response.getWriter().write(adResponse);
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}