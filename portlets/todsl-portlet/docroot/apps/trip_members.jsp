<%@ include file="/init.jsp" %>

<c:if test="<%= Validator.isNotNull(tripMember) %>">
	<div class="row-fluid span8">
		<h4>Going</h4>

		<table>

			<%
				int tripMemberStatus = GetterUtil.getInteger("1");

				for (TripMember member : TripMemberLocalServiceUtil.getTripMembersByStatus(tripId, tripMemberStatus)) {
			%>

			<portlet:actionURL name="respondToTripMemberInvitation" var="acceptTripMemberInvitationURL">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="1" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="declineTripMemberInvitationURL">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="2" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="maybeTripMemberInvitationURL">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="3" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="makeTripMemberAdmin" var="makeTripMemberAdminURL">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberAdminStatus" value="1" />
			</portlet:actionURL>
			<portlet:actionURL name="deleteTripMember" var="deleteTripMemberURL">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			</portlet:actionURL>

			<tr>
				<td><%= member.getTripMemberName() %></td>

				<c:if test="<%= tripMember.getTripMemberAdmin()%>">
					<td>
						<select onchange="window.location=this.value" style="max-width:100px;">
							<option value=""></option>
							<option value="<%= layout.getRegularURL(request) %>/-/trip/<%= trip.getTripId() %>/rsvp/<%= member.getTripMemberId() %>">RSVP</option>
							<option value="<%= acceptTripMemberInvitationURL %>">Accept</option>
							<option value="<%= maybeTripMemberInvitationURL %>">Maybe</option>
							<option value="<%= declineTripMemberInvitationURL %>">Decline</option>
							<option value="<%= makeTripMemberAdminURL %>">Make Admin</option>
							<option value="<%= deleteTripMemberURL %>">Delete</option>
						</select>
					</td>
				</c:if>
			</tr>

			<%
				}
			%>

		</table>

		<h4>Maybe</h4>

		<table>

			<%
				tripMemberStatus = GetterUtil.getInteger("3");

				for (TripMember member : TripMemberLocalServiceUtil.getTripMembersByStatus(tripId, tripMemberStatus)) {
			%>

			<portlet:actionURL name="respondToTripMemberInvitation" var="acceptTripMemberInvitationURL2">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="1" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="declineTripMemberInvitationURL2">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="2" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="maybeTripMemberInvitationURL2">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="3" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="deleteTripMember" var="deleteTripMemberURL2">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			</portlet:actionURL>

			<tr>
				<td><%= member.getTripMemberName() %></td>

				<c:if test="<%= tripMember.getTripMemberAdmin()%>">
					<td>
						<select onchange="window.location=this.value" style="max-width:100px;">
							<option value=""></option>
							<option value="<%= layout.getRegularURL(request) %>/-/trip/<%= trip.getTripId() %>/rsvp/<%= member.getTripMemberId() %>">RSVP</option>
							<option value="<%= acceptTripMemberInvitationURL2 %>">Accept</option>
							<option value="<%= maybeTripMemberInvitationURL2 %>">Maybe</option>
							<option value="<%= declineTripMemberInvitationURL2 %>">Decline</option>
							<option value="<%= deleteTripMemberURL2 %>">Delete</option>
						</select>
					</td>
				</c:if>
			</tr>

			<%
				}
			%>

		</table>

		<h4>Not Going</h4>

		<table>

			<%
				tripMemberStatus = GetterUtil.getInteger("2");

				for (TripMember member : TripMemberLocalServiceUtil.getTripMembersByStatus(tripId, tripMemberStatus)) {
			%>

			<portlet:actionURL name="respondToTripMemberInvitation" var="acceptTripMemberInvitationURL3">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="1" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="declineTripMemberInvitationURL3">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="2" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="maybeTripMemberInvitationURL3">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="3" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="deleteTripMember" var="deleteTripMemberURL3">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			</portlet:actionURL>

			<tr>
				<td><%= member.getTripMemberName() %></td>

				<c:if test="<%= tripMember.getTripMemberAdmin()%>">
					<td>
						<select onchange="window.location=this.value" style="max-width:100px;">
							<option value=""></option>
							<option value="<%= layout.getRegularURL(request) %>/-/trip/<%= trip.getTripId() %>/rsvp/<%= member.getTripMemberId() %>">RSVP</option>
							<option value="<%= acceptTripMemberInvitationURL3 %>">Accept</option>
							<option value="<%= maybeTripMemberInvitationURL3 %>">Maybe</option>
							<option value="<%= declineTripMemberInvitationURL3 %>">Decline</option>
							<option value="<%= deleteTripMemberURL3 %>">Delete</option>
						</select>
					</td>
				</c:if>
			</tr>

			<%
				}
			%>

		</table>

		<h4>Awaiting Response</h4>

		<table>

			<%
				tripMemberStatus = GetterUtil.getInteger("0");

				for (TripMember member : TripMemberLocalServiceUtil.getTripMembersByStatus(tripId, tripMemberStatus)) {
			%>

			<portlet:actionURL name="respondToTripMemberInvitation" var="acceptTripMemberInvitationURL4">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="1" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="declineTripMemberInvitationURL4">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="2" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="respondToTripMemberInvitation" var="maybeTripMemberInvitationURL4">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripMemberStatus" value="3" />
				<portlet:param name="userId" value="<%= String.valueOf(user.getUserId()) %>" />
			</portlet:actionURL>
			<portlet:actionURL name="deleteTripMember" var="deleteTripMemberURL4">
				<portlet:param name="tripMemberId" value="<%= String.valueOf(member.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			</portlet:actionURL>

			<tr>
				<td><%= member.getTripMemberName() %></td>

				<c:if test="<%= tripMember.getTripMemberAdmin()%>">
					<td>
						<select onchange="window.location=this.value" style="max-width:100px;">
							<option value=""></option>
							<option value="<%= layout.getRegularURL(request) %>/-/trip/<%= trip.getTripId() %>/rsvp/<%= member.getTripMemberId() %>">RSVP</option>
							<option value="<%= acceptTripMemberInvitationURL4 %>">Accept</option>
							<option value="<%= maybeTripMemberInvitationURL4 %>">Maybe</option>
							<option value="<%= declineTripMemberInvitationURL4 %>">Decline</option>
							<option value="<%= deleteTripMemberURL4 %>">Delete</option>
						</select>
					</td>
				</c:if>
			</tr>

			<%
				}
			%>

		</table>
	</div>

	<div class="row-fluid span4">
		<h4>Invite a friend</h4>

		<portlet:actionURL name="addTripMember" var="addTripMemberURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
		</portlet:actionURL>

		<form action="<%= addTripMemberURL %>" method="post">
			<aui:model-context model="<%= TripMember.class %>" />
			<aui:input name="tripId" type="hidden" value="<%= trip.getTripId() %>" />
			<aui:input label="Name" name="tripMemberName" />
			<aui:input label="Email" name="tripMemberEmail" />
			<aui:button type="submit" value="Add Member" />
		</form>
	</div>
</c:if>