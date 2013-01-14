<%@ taglib prefix="s" uri="/struts-tags" %>
<p>Welcome to the Salesforce Outbound Messaging demonstration tool.
	This tool is designed to allow you to send an Outbound Message to this
	application, visually show the message and interact to manually
	respond, pretending you are some type of integration system.
</p>

<s:if test="messages.size > 0">
	<table border="1">
		<tr>
			<th>ID</th>
			<th>OrgID</th>
			<th>SessionID</th>
			<th>Actions</th>
		</tr>
		<s:iterator value="messages" var="message">
			<tr>
				<td><s:property value="#message.id"/></td>
				<td><s:property value="#message.orgId"/></td>
				<td><s:property value="#message.sessionId"/></td>
				<td>
					<s:url action="edit" includeParams="get" id="edit">
						<s:param name="messageId" value="#message.id"/>
					</s:url><s:a href="%{edit}">Edit</s:a> &nbsp;
					<s:url action="view" includeParams="get" id="view">
						<s:param name="messageId" value="#message.id"/>
					</s:url><s:a href="%{view}">View</s:a> &nbsp;
					<s:url action="seeobm" includeParams="get" id="seeobm">
						<s:param name="messageId" value="#message.id"/>
					</s:url><s:a href="%{seeobm}">Raw OBM</s:a>
				</td>
			</tr>
		</s:iterator>
	</table>
</s:if>
<s:if test="messages.size == 0">
	We have no Outbound Messages stored.
</s:if>
