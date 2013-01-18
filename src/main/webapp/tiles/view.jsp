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
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:if test="hasActionErrors()">
	<s:url action="app/delete" includeParams="get" id="delete"/>
	Would you like to <s:a href="%{delete}">Delete</s:a> stored Outbound Message with ID <s:property value="messageId"/>?
</s:if>
<s:else>
	
	<div id="obmDetails">
		<h2>Outbound Message Received - Details</h2>
		This page shows the details of the Outbound Message as received by this Heroku application.
	</div>
	<div class="details">
		<h3>User Details</h3>
		<table width="100%">
			<tr>
				<td>Full Name</td><td><s:property value="userInfo.userFullName"/></td>
			</tr>
			<tr>
				<td>User Name</td><td><s:property value="userInfo.userName"/></td>
			</tr>
			<tr>
				<td>Email</td><td><s:property value="userInfo.userEmail"/></td>
			</tr>
			<tr>
				<td>Email</td><td><s:property value="userInfo.organizationName"/></td>
			</tr>	
			<tr>
				<td>Locale</td><td><s:property value="userInfo.userLocale"/></td>
			</tr>
			<tr>
				<td>Timezone</td><td><s:property value="userInfo.userTimezone"/></td>
			</tr>	
			<tr>
				<td>User Type</td><td><s:property value="userInfo.userType"/></td>
			</tr>											
		</table>
	</div>
	<div class="details">
		<h3>Message Details</h3>
		<table width="100%">
			<tr>
				<td>Message ID</td><td><s:property value="message.id" /></td>
			</tr>
			<tr>
				<td>Org ID</td><td><s:property value="message.orgId" /></td>
			</tr>
			<tr>
				<td>Session ID</td><td><s:property value="message.sessionId" /></td>
			</tr>	
			<tr>
				<td>Action ID</td><td><s:property value="message.actionId" /></td>
			</tr>
			<tr>
				<td>Date Received</td><td><s:date name="message.dateReceived" format="dd-MM-yyyy hh:mm:ss"/></td>
			</tr>
			<tr>
				<td>Enterprise URL</td><td><s:property value="message.enterpriseURL" /></td>
			</tr>	
			<tr>
				<td>Parnter URL</td><td><s:property value="message.partnerURL" /></td>
			</tr>															
		</table>
	</div>

	<div>
		<h3>Object Details</h3>
		<table>
			<tr>
				<th>Ojbect ID</th>
				<th>Object Name</th>
			</tr>
			<s:iterator value="message.modifiedObjects" var="modifiedObject">
				<tr>
					<td>
						<s:url action="app/showObject" includeParams="get" id="showObject">
							<s:param name="objectId" value="#modifiedObject.objectId"/>
						</s:url><s:a href="%{showObject}" tooltip="Show object details"><s:property value="#modifiedObject.objectId" /></s:a>
					</td>
					<td>
						<s:property value="#modifiedObject.objectName"/>
					</td>
			</s:iterator>
		</table>
	</div>
	<div>
		<h3>Raw XML Message <span id="xmlToggle">hide/show</span></h3>
		<div id="xml" style="display:none">
			<textarea readonly="readonly" cols="80" rows="30">
				<s:property escapeHtml="true" value="message.xmlMessage" />
			</textarea>		
		</div>
		<script type="text/javascript">
			
		$(document).ready(function() {
			$("#xmlToggle").click(function(){
				$("#xml").toggle();
			});
		});
		
		</script>
	</div>
</s:else>