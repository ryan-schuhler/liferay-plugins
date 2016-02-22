<%@ include file="/init.jsp" %>

<%
long rsvpMemberId = ParamUtil.getLong(request, "tripMemberId");

TripMember rsvpTripMember = TripMemberLocalServiceUtil.fetchTripMember(rsvpMemberId);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(rsvpTripMember) %>">

		<%
		long tripMemberInvitedByMemberId = rsvpTripMember.getTripMemberInvitedByMemberId();
		String inviteeName = StringPool.BLANK;

		TripMember tripMemberInvitedByMember = TripMemberLocalServiceUtil.fetchTripMember(tripMemberInvitedByMemberId);

		if (Validator.isNotNull(tripMemberInvitedByMember)) {
			inviteeName = tripMemberInvitedByMember.getTripMemberName();
		}

		String redirect = baseURL + trip.getTripId();
		%>

		You have been invited by <%= inviteeName %> on this trip to <%= trip.getTripLocation() %>.

		Please let us know if you will be joining.

		<c:choose>
			<c:when test="<%= themeDisplay.isSignedIn() %>">
				<portlet:actionURL name="respondToTripMemberInvitation" var="respondToTripMemberInvitationURL">
					<portlet:param name="redirect" value="<%= redirect %>" />
				</portlet:actionURL>

				<form action="<%= respondToTripMemberInvitationURL %>" method="post">

					<aui:model-context bean="<%= rsvpTripMember %>" model="<%= TripMember.class %>" />

					<aui:input name="tripMemberId" type="hidden" value="<%= rsvpMemberId %>" />
					<aui:input name="tripId" type="hidden" value="<%= trip.getTripId() %>" />
					<aui:input name="userId" type="hidden" value="<%= user.getUserId() %>" />

					<aui:field-wrapper name="tripMemberStatus">
						<aui:input inlineLabel="right" label="yes" name="tripMemberStatus" type="radio" value="1" />
						<aui:input inlineLabel="right" label="maybe" name="tripMemberStatus" type="radio" value="3"  />
						<aui:input inlineLabel="right" label="no" name="tripMemberStatus" type="radio" value="2"  />
					</aui:field-wrapper>

					<aui:button type="submit" value="Submit" />
				</form>
			</c:when>
			<c:otherwise>
				Please <a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to respond.
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		Sorry something went wrong.
	</c:otherwise>
</c:choose>