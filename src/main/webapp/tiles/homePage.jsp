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
<p>Welcome to the Salesforce Outbound Messaging demonstration tool.
	This tool is designed to allow you to send an Outbound Message to this
	application, visually show the message and interact to manually
	respond, pretending you are some type of integration system.
</p>

	<s:if test="messages.size > 0">
		<table class="obmTable">
			<tr class="obmHeader">
				<th style="width:5%">ID</th>
				<th  style="width:20%">OrgID</th>
				<th  style="width: 30%">SessionID</th>
				<th style="width: 15%">Object Payload</th>
				<th  style="width:20%">DateReceived</th>
				<th>Actions</th>
			</tr>
			<s:iterator value="messages" var="message">
				<tr>
					<td class="obmCell"><s:property value="#message.id"/></td>
					<td class="obmCell"><s:property value="#message.orgId"/></td>
					<td class="obmCell"><s:property value="#message.sessionId"/></td>
					<td class="obmCell"><s:property value="#message.modifiedObjects.size"/></td>
					<td class="obmCell">
						<!-- Format date to user timezone -->
						<script type="text/javascript">
							var d = new Date('<s:date name="#message.dateReceived" timezone="UTC"/> UTC');
							document.write(d);
						</script>
					</td>
					<td class="obmCell links">
						<s:url action="app/view" includeParams="get" id="view">
							<s:param name="messageId" value="#message.id"/>
						</s:url><s:a href="%{view}">View OBM</s:a> <br />
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
	<script type="text/javascript">
		function formatDateToTimezone(d) {
			var offset = d.get
		}
		var int=self.setInterval(function(){update()},1000);
		var initialRecordCount = <s:property value="recordCount"/>;
		function update() {
			$.post('/countMessages', function(data) {
				if(initialRecordCount != data) {
					initialRecordCount = data;
					alert("We have "+data+" records now");
				}
			});

		}

	</script>