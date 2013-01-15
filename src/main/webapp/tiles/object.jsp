<%@ taglib prefix="s" uri="/struts-tags" %>

This is a single object view.

<s:if test="message != null">
	There has been a problem with the request. <hr />
	<s:property value="message" />
</s:if>
<s:else>
	Account Details - <s:property value="accountName"/>
	<hr />
	<table width="80%">
		<tr>
			<th>Field Name</th>
			<th>API Name</th>
			<th>Value</th>
		</tr>
		<s:iterator value="account.values" var="value">
			<tr>
				<td><s:property value="#value.key.label"/></td>
				<td><s:property value="#value.key.name"/></td>
				<td><input type="text" value='<s:property value="#value[value.key]"/>'/></td>
			</tr>	
		</s:iterator>
	</table>
</s:else>

