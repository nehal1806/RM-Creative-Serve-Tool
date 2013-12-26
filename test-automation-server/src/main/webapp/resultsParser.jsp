<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PSO Index Page</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	$(document).ready(function() {
		$('#dimension').change(function(event) {
			var $dim = $("select#dimension").val();
			$.get('ActionServlet', {
				dimension : $dim
			}, function(responseJson) {
				var $select = $('#details');
				$select.find('option').remove();
				$.each(responseJson, function(key, value) {
					$('<option>').val(key).text(value).attr("value", value).appendTo($select);
				});
			});
		});
	});
	
	
	function Back() {
		window.location.replace("index.jsp");
	}
	</script>

</script>
</head>
<body>

	<!--  <form action="adformatresults" method="POST">  -->
	<img src="/psowebapp/images/index.jpg" align="right" /> <br />
	<form action="results" method=GET>

		<h1>PSO webapp server: Results Page</h1>
		Group Results By: <select name="groupbydim" id="dimension">
			<option>Group by...</option>
			<option value="Slot">Slot</option>
			<option value="Creative">Creative</option>
			<option value="Sdk">Sdk</option>
			<option value="Platform">Platform</option>
			<option value="Os-Version">Os-Version</option>
			
		</select> 
		<br /> <br /> 
		Dimension Value: <select name="dimvalue" id="details">
			<option>details</option>
			
		</select> <br /> <br /> <input type="submit" id="submit" value="Submit Query">
		<button type="button" name="Pso Home" onclick="Back();">back</button>
		
	</form>


</body>
</html>