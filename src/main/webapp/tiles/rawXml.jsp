<%@ taglib prefix="s" uri="/struts-tags" %>
<p>Welcome to the Salesforce Outbound Messaging demonstration tool.
	This is the raw XML that was received from the Outbound Message.
</p>
<textarea readonly="readonly" cols="120" rows="50">
<s:property escapeHtml="true" value="message.xmlMessage" />
</textarea>
