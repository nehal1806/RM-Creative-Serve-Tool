<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index Page</title>
</head>
<body>
	<script>
		function openPage() {
			var selection = document.form.radios;

			for (i = 0; i < selection.length; i++)

				if (selection[i].checked == true)
					window.location.replace(selection[i].value);
/* 					if (selection[i].value == "view") {
						window.location.replace("view.jsp");
					} else if (selection[i].value == "edit") {
						window.location.replace("edit.jsp");
					} else if (selection[i].value == "delete") {
						window.location.replace("delete.jsp");
					}
 */
 }
	</script>
	<form name="form">
			<img src="/psowebapp/images/index.jpg" align="right" /> <br />
		<h1>PSO Webapp Server: Index page</h1>
	
		<input type="radio" value="resultsParser.jsp" name="radios" onclick="openPage();" > Ad-Formats, Results Parser<br>
		<input type="radio" value="AdResponseGen.jsp" name="radios" onclick="openPage();" > Ad-Formats, Ad-Response Generator Tool<br>
		<input type="radio" value="RMCreativeTool.jsp" name="radios" onclick="openPage();" > RM Creative Serve Tool<br>
		
	</form>

</body>
</html>