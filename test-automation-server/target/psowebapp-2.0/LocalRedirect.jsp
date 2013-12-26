<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" errorPage=""%>
<%
	int timeoutValue = 10;
	String url = "www.google.com";
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sync Test Redirect: Status!</title>
</head>
<body>

	<h2>Sync Test Redirect: Successful...!</h2>
	<img src="/psowebapp/images/InMobi_Office.jpg" />

</body>
</html>

<%
response.setStatus(301);
response.setHeader("Refresh", "5; URL=adtest://");
response.setHeader( "Connection", "close" );
%>