<%@ taglib prefix="s" uri="/struts-tags" %>

Here is where we will show the message and edit stuff.
<div>
Message ID - <s:property value="message.id" /><br />
Org ID (message) - <s:property value="message.orgId" /><br />
Session ID - <s:property value="message.sessionId" /><br />
Action ID - <s:property value="message.actionId" /><br />
Date Received - <s:property value="message.dateReceived" /><br />
Enterprise URL - <s:property value="message.enterpriseURL" /><br />
Parnter URL - <s:property value="message.partnerURL" /><br />
<s:iterator value="message.modifiedObjects" var="modifiedObject">
Object ID - <s:property value="#modifiedObject.objectId"/>
</s:iterator>
<hr />
User Info:<br />
	private String orgId;
	private String currencySymbol;
	private String orgName;
	private String profileId;
	private String roleId;
	private int sessionSecondsValid;
	private String email;
	private String fullName;
	private String userId;
	private String language;
	private String locale;
	private String userName;
	private String timezone;
	private String userType;
Org ID (API) - <s:property value="userInfo.orgId"/><br />
Currency Symbol - <s:property value="userInfo.currencySymbol"/><br />
Org Name - <s:property value="userInfo.orgName" /><br />
Profile ID - <s:property value="userInfo.profileId" /><br />
Role ID - <s:property value="userInfo.roleId"/><br />
Session Seconds - <s:property value="userInfo.sessionSecondsValid" /><br />
Email - <s:property value="userInfo.email"/><br />
Full Name - <s:property value="userInfo.fullName"/> <br />
User Id = <s:property value="userInfo.userId"/> <br />
Language - <s:property value="userInfo.language"/> <br />
Locale - <s:property value="userInfo.locale"/><br />
User Name - <s:property value="userInfo.userName"/><br />
Timezone - <s:property value="userInfo.timezone"/><br />
User Type - <s:property value="userInfo.userType"/><br />

</div>