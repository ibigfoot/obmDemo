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
	Account Details - <s:property value="account.accountName"/>
	<hr />
	<table width="80%">
		<tr>
			<th>Field Name</th>
			<th>API Name</th>
			<th>Datatype</th>
			<th>Value</th>
		</tr>
		<s:iterator value="account.values" var="map" status="stat">
			<tr>
				<!-- <td>Row <s:property value="#stat.index"/></td> -->
				<td><s:property value="#map.key.label"/></td>
				<td><s:property value="#map.key.name"/></td>
				<td><s:property value="#map.key.type"/></td>
				<td><input type="text" value='<s:property value="#map.value"/>'/></td>
			</tr>	
		</s:iterator>
	</table>
</s:else>

