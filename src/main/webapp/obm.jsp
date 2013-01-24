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
			<p>Client - 3MVG9Y6d_Btp4xp5zu1u9ukSXppAZHihuyjQidBq9oOlT5zNqjYOTDyhsmG_DmYRAj6v2NVHwuB2y04Xx_qND</p>
			<p>Response Type - code</p>
			<p>redirect uri - https://pure-plateau-6292.herokuapp.com/app/callback.action</p>
			<a href="https://login.salesforce.com/services/oauth2/authorize?response_type=token&client_id=3MVG9Y6d_Btp4xp5zu1u9ukSXppAZHihuyjQidBq9oOlT5zNqjYOTDyhsmG_DmYRAj6v2NVHwuB2y04Xx_qND&redirect_uri=https://pure-plateau-6292.herokuapp.com/app/callback.action&scope=web">Login Using Salesforce OAuth</a>
		</div>
		<div id="footer">
			Powered by <a href="http://www.heroku.com"><img src="images/heroku.png" alt="Heroku" height="20px" align="top"/></a>
		</div>
	</div>
</body>
</html>