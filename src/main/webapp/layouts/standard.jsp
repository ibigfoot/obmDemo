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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="false" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta charset="utf-8" />
		<title><tiles:getAsString name="title" /></title>
		<link href="/css/obm.css" rel="stylesheet" type="text/css" />
		<link href="https://nav.heroku.com/images/logos/favicon.ico" rel="icon" type="image/x-icon">
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
		<script src="/js/jquery.noty.js"></script>
		<script src="/js/layouts/top.js"></script>
		<script src="/js/layouts/topCenter.js"></script>
		<script src="/js/themes/default.js"></script>
	</head>
	<body>
		<div id="container">
			<tiles:insertAttribute name="header" />
		</div>
		<div id="mainContainer">
			<script type="text/javascript">
				<s:iterator value="actionErrors" var="actionError">
					var errorNote = noty({text: '<s:property value="#actionError"/>', type: 'error'});
				</s:iterator>
				<s:iterator value="actionMessages" var="actionMessage">
					var messageNote = noty({text: '<s:property value="#actionMessage"/>', type: 'success', timeout: '3000'})
				</s:iterator>
			</script>

			<tiles:insertAttribute name="mainContent" />
		</div>
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
</body>
</html>