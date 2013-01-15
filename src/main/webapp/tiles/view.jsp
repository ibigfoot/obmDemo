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