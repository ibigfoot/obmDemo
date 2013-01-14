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
			<th>DateReceived</th>
			<th>Actions</th>
		</tr>
		<s:iterator value="messages" var="message">
			<tr>
				<td><s:property value="#message.id"/></td>
				<td><s:property value="#message.orgId"/></td>
				<td><s:property value="#message.sessionId"/></td>
				<td><s:property value="#message.dateReceived"/></td>
				<td>
					<s:url action="app/edit" includeParams="get" id="edit">
						<s:param name="messageId" value="#message.id"/>
					</s:url><s:a href="%{edit}">Edit</s:a> &nbsp;
					<s:url action="app/view" includeParams="get" id="view">
						<s:param name="messageId" value="#message.id"/>
					</s:url><s:a href="%{view}">View</s:a> &nbsp;
					<s:url action="app/showobm" includeParams="get" id="showobm">
						<s:param name="messageId" value="#message.id"/>
					</s:url><s:a href="%{showobm}">Raw OBM</s:a>
					<s:url action="app/delete" includeParams="get" id="delete">
						<s:param name="messageId" value="#message.id"/>
					</s:url><s:a href="%{delete}">Delete OBM</s:a>
				</td>
			</tr>
		</s:iterator>
	</table>
</s:if>
<s:if test="messages.size == 0">
	We have no Outbound Messages stored.
</s:if>
