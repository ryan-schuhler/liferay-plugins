<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
long tripMemberId = ParamUtil.getLong(request, "tripMemberId");

TripMember rsvpTripMember = TripMemberLocalServiceUtil.fetchTripMember(tripMemberId);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(trip) && Validator.isNotNull(rsvpTripMember) %>">

		<%
		long tripMemberInvitedByMemberId = rsvpTripMember.getTripMemberInvitedByMemberId();
		String inviteeName = StringPool.BLANK;

		User tripMemberInvitedByUser = UserLocalServiceUtil.fetchUserById(tripMemberInvitedByMemberId);

		if (Validator.isNotNull(tripMemberInvitedByUser)) {
			inviteeName = tripMemberInvitedByUser.getFullName();
		}

		String redirect = layout.getFriendlyURL() + "/-/trip/" + trip.getTripId();
		%>

		You have been invited by <%= inviteeName %> on a trip to <%= trip.getTripLocation() %>.

		Please let us know if you will be joining.

		<c:choose>
			<c:when test="<%= themeDisplay.isSignedIn() %>">
				<portlet:actionURL name="respondToTripMemberInvitation" var="respondToTripMemberInvitationURL">
					<portlet:param name="redirect" value="<%= redirect %>" />
				</portlet:actionURL>

				<form action="<%= respondToTripMemberInvitationURL %>" method="post">

					<aui:model-context bean="<%= rsvpTripMember %>" model="<%= TripMember.class %>" />

					<aui:input name="tripMemberId" type="hidden" value="<%= tripMemberId %>" />
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
		Sorry this trip doesn't exist.
	</c:otherwise>
</c:choose>