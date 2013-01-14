<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta charset="utf-8" />
		<title><tiles:getAsString name="title" /></title>
		<link href="css/obm.css" rel="stylesheet" type="text/css" />
		<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	</head>
	<body>
		<div id="container">
			<tiles:insertAttribute name="header" />
		</div>
		<div id="mainContainer">
			<tiles:insertAttribute name="mainContent" />
		</div>
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
</body>
</html>