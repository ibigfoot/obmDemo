<%-- 
  Copyright (c) 2013, salesforce.com, inc.
  All rights reserved.
 
  Redistribution and use in source and binary forms, with or without modification, are permitted provided
  that the following conditions are met:
 
     Redistributions of source code must retain the above copyright notice, this list of conditions and the
     following disclaimer.
 
     Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
     the following disclaimer in the documentation and/or other materials provided with the distribution.
 
     Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
     promote products derived from this software without specific prior written permission.
 
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
  PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
  TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
  HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
  POSSIBILITY OF SUCH DAMAGE.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
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