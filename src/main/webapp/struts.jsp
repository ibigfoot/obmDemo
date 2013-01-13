<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
  <meta charset="utf-8">
  <title>Outbound Message Handler</title>
  <link href="css/obm.css" rel="stylesheet" type="text/css" />
  <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"> 
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
</head>
<body>
	<div id="container">
		<div id="header">
			<div id="headerContainer">
				<div id="heading">Outbound Message Handler</div>
				<div id="breadcrumb">
					<a href="/obm.jsp">Home</a>->Struts
				</div>
			</div>
		</div>

		</div>
		<div id="mainContainer">
			<p>
				This is the struts application. Need to figure out JSF next.
				
			</p>
			<p>
				<a href="<s:url action='hello'/>">Struts Action </a>
			</p>
		</div>
		<div id="footer">
			Powered by <a href="http://www.heroku.com"><img src="images/heroku.png" alt="Heroku" height="20px" align="top"/></a>
		</div>
	</div>
</body>
</html>