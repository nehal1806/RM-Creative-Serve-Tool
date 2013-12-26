<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PSO Ad-Response Generator</title>
</head>

<body>

<form method="post" action="adresponsegen"
		enctype="multipart/form-data">
		<img src="/psowebapp/images/index.jpg" align="right" /> <br />
		<h1>PSO - Ad-Response Upload Tool:</h1>

		Relese Tag: <input type="text" name="directory" /> <br />
		<br /> Select your response file : (.zip)<br /> <input type="file"
			name="uploadFile" size="1024" required /><br /> <br /> <input
			type="submit" value="Upload" />

		</form>
</body>
</html>
