<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RM Creative Serve Tool</title>
<script>
	function Back() {
		window.location.replace("index.jsp");
	}
</script>
</head>

<body>
<!--
1. Creative type - Expandable, Interstitial - dropdown
2. Content provider - Sprout, Others - dropdown
3. Slot - 15, 16 etc. - dropdown
4. Width - textbox
5. Height - textbox
6. Response format - html, imai - dropdown
7. Operating System - And, iOS - dropdown
8. Ad Tag - textbox
-->
<form method="post" action="rmtool"
		enctype="multipart/form-data">
		<img src="/psowebapp/images/index.jpg" align="right" /> <br />
		<h1>
			<span style="font-family:comic sans ms,cursive;"><img alt="smiley" height="20" src="http://htmleditor.in/ckeditor/plugins/smiley/images/regular_smile.gif" title="smiley" width="20" />&nbsp;RM Creative Serve Tool&nbsp;<img alt="smiley" height="20" src="http://htmleditor.in/ckeditor/plugins/smiley/images/regular_smile.gif" title="smiley" width="20" /></span></h1>
		<p>
			<span style="font-family:comic sans ms,cursive;">Please enter the following details to obtain for an ad response:</span></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Type of Creative:&nbsp;</span><select name="Creative_Type"><option selected="selected" value="Expandable">Expandable</option><option value="Interstitial">Interstitial</option></select></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Content Provider:&nbsp;</span><select name="Content_provider"><option selected="selected" value="Sprout">Sprout</option><option value="Others">Others</option></select></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Slot size:&nbsp;</span><select name="slot_size"><option value="9">9</option><option value="10">10</option><option value="11">11</option><option value="12">12</option><option value="13">13</option><option selected="selected" value="15">15</option></select></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Width:&nbsp;<input name="width" type="text" value="320" /></span></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Height:&nbsp;<input name="height" type="text" value="50" /></span></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Response Format:&nbsp;</span><select name="response_format"><option value="xhtml">xhtml</option><option selected="selected" value="imai">imai</option></select></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Operating System:&nbsp;</span><select name="os"><option value="Android">Android</option><option selected="selected" value="iOS">iOS</option></select></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Landing URL (optional):&nbsp;</span><input name="landingURL" size="30" type="text" /></p>
		<p>
			<span style="font-family:comic sans ms,cursive;">Ad Code:&nbsp;<textarea name="adtag" style="margin: 2px; width: 197px; height: 46px;"></textarea></span></p>
		<p><br /> <br /> 
			<input type="submit" value="Done" />
			<button type="button" name="Pso Home" onclick="Back();">back</button>

		</form>
</body>
</html>
