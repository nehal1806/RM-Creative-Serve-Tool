package com.inmobi.creativeservetool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;

import com.inmobi.adserve.creativetool.api.AdRequest;
import com.inmobi.creativeservetool.RMCreativeResponseGenerator;
import com.inmobi.phoenix.exception.InitializationException;
import com.inmobi.phoenix.exception.RepositoryException;

// 	public static byte[] genInterstitialAdResponse(Integer slotID, Integer width, Integer height, String adTag, String landingURL, AdRequest.RequestFormat responseFormat, AdRequest.Os OS, String beaconURL, String ClickURL) {

@SuppressWarnings("serial")
public class CreativeToolRespGenerator extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	 {
		System.out.println("CreativeToolRespGenerator servelet called!");
		String creative_type = request.getParameter("Creative_Type");
        String width = request.getParameter("width");
        String code = "<script src=\"mraid.js\"></script><div id=\"Sprout_LwA9-08qO0NRSw5U_div\" " +
                "data-creativeId=\"LwA9-08qO0NRSw5U\"></div>\n" +
                "<script type=\"text/javascript\">\n" +
                "var _Sprout = _Sprout || {};\n" +
                "\n" +
                "/* 3rd Party Impression Tracker: a tracking pixel URL for tracking 3rd party " +
                "impressions */\n" +
                "_Sprout.impressionTracker = \"PUT_IMPRESSION_TRACKER_HERE\";\n" +
                "\n" +
                "/* 3rd Party Click Tracker: A URL or Macro like %c for third party exit tracking" +
                " */\n" +
                "_Sprout.clickTracker = \"PUT_CLICK_TRACKER_HERE\";\n" +
                "\n" +
                "/* Publisher Label: What you want to call this line-item in Studio reports */\n" +
                "_Sprout.publisherLabel = \"PUT_PUBLISHER_LABEL_HERE\";\n" +
                "\n" +
                "_Sprout._inMobiAdTagTracking={st:new Date().getTime()," +
                "rr:0};_Sprout[\"LwA9-08qO0NRSw5U\"]={querystring:{__im_curl:\"$JS_ESC_BEACON_URL" +
                "\",__im_sdk:\"$SDK_VERSION_ID\",click:\"$JS_ESC_CLICK_URL\"," +
                "adFormat:\"interstitial\"}};var _sproutReadyEvt=document.createEvent(\"Event\");" +
                "_sproutReadyEvt.initEvent(\"sproutReady\",true,true);window.dispatchEvent" +
                "(_sproutReadyEvt);var _Sprout_load=function(){var e=document" +
                ".getElementsByTagName(\"script\"),e=e[e.length-1]," +
                "t=document.createElement(\"script\");t.async=!0;t.type=\"text/javascript\";t" +
                ".src=\"http://edgy.sproutbuilder.com/load/LwA9-08qO0NRSw5U.inmobi.html.js\";e" +
                ".parentNode.insertBefore(t," +
                "e.nextSibling)};\"0\"===window[\"_Sprout\"][\"LwA9-08qO0NRSw5U\"][\"querystring" +
                "\"][\"__im_sdk\"]||\"complete\"===document.readyState?_Sprout_load():window" +
                ".addEventListener(\"load\",_Sprout_load,!1)</script>";
        byte[] ad = null;
		try {
			ad = RMCreativeResponseGenerator.genInterstitialAdResponse(14, 320, 480, code, "www.inmobi.com", AdRequest.RequestFormat.XHTML, AdRequest.Os.ANDROID, "et.w.inmobi.com", "clickurl.w.inmobi.com");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InitializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String s = new String(ad);
        System.out.println("RESP : " + s);
        System.out.println("type: " + creative_type);
        System.out.println("width: " + width);
	 }
}