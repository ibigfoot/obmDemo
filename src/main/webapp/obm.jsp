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
					Home
				</div>
			</div>
		</div>

		</div>
		<div id="mainContainer">
			<p>
				Welcome to the Salesforce Outbound Messaging demonstration tool. This tool is designed to allow
				you to send an Outbound Message to this application, visually show the message and 
				interact to manually respond, pretending you are some type of integration system.
			</p>
			<p><a href="instructions.jsp"/>Instructions</a> will help you setup your Salesforce Instance and show you how to use this.</p>
			<p><a href="https://github.com/ibigfoot/ForceAPIClient" target="_blank">Code</a> can be found GitHub</p>
			<div id="actionTable">
				<s:iterator value="messages" var="message">
					<s:property value="#message.id"/>
				</s:iterator>
			</div>
		</div>
		<div id="footer">
			Powered by <a href="http://www.heroku.com"><img src="images/heroku.png" alt="Heroku" height="20px" align="top"/></a>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#updateTableBtn").click(function() {
				$.getJSON('/messages', function(data) {
					
					var items = [];
					$.each(data, function(key,val) {
						items.push(key+ ' | ' + val + '||');
					});
						
					alert(items);
					
				});
			})
		});
	</script>
</body>
</html>