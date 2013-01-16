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

<s:if test="userInfo == null">
	<div>
		The application was unable to retrieve details. <br />
		<s:property value="errorMessage"/>
		<p>
			Message ID - <s:property value="message.id" /><br />
			Org ID (message) - <s:property value="message.orgId" /><br />
			Session ID - <s:property value="message.sessionId" /><br />
			Action ID - <s:property value="message.actionId" /><br />
			Date Received - <s:property value="message.dateReceived" /><br />
			Enterprise URL - <s:property value="message.enterpriseURL" /><br />
			Parnter URL - <s:property value="message.partnerURL" /><br />
		</p>
	</div>	
</s:if>
<s:else>
	<p>
		This shows us the details of the Outbound Message that was received.
	</p>
	<div>
		User Details
		<hr />
		Full Name - <s:property value="userInfo.fullName"/> <br />
		User Name - <s:property value="userInfo.userName"/><br />
		Email - <s:property value="userInfo.email"/><br />
		Org Name - <s:property value="userInfo.orgName" /><br />
		Locale - <s:property value="userInfo.locale"/><br />
		Timezone - <s:property value="userInfo.timezone"/><br />
		User Type - <s:property value="userInfo.userType"/><br />
	</div>
	<div>
		Message Details
		<hr />
		Message ID - <s:property value="message.id" /><br />
		Org ID (message) - <s:property value="message.orgId" /><br />
		Session ID - <s:property value="message.sessionId" /><br />
		Action ID - <s:property value="message.actionId" /><br />
		Date Received - <s:property value="message.dateReceived" /><br />
		Enterprise URL - <s:property value="message.enterpriseURL" /><br />
		Parnter URL - <s:property value="message.partnerURL" /><br />
	</div>
	<div>
		Object Details
		<hr />
		<s:iterator value="message.modifiedObjects" var="modifiedObject">
			<s:url action="app/showObject" includeParams="get" id="showObject">
				<s:param name="objectId" value="#modifiedObject.objectId"/>
			</s:url>Object ID - <s:a href="%{showObject}"><s:property value="#modifiedObject.objectId"/></s:a><br />

		</s:iterator>
	</div>
</s:else>