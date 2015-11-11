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
long memberId = ParamUtil.getLong(request, "memberId");

Member member = MemberLocalServiceUtil.fetchMember(memberId);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(trip) && Validator.isNotNull(member) %>">
		You have been invited by <%= member.getInvitedByUserId() %> on a trip to <%= trip.getLocation() %>.

		Please let us know if you will be joining.

		<c:choose>
			<c:when test="<%= themeDisplay.isSignedIn() %>">
				<portlet:actionURL name="respondToMemberInvitation" var="respondToMemberInvitationURL">
				</portlet:actionURL>

				<form action="<%= respondToMemberInvitationURL %>" method="post">

					<aui:model-context bean="<%= member %>" model="<%= Member.class %>" />

					<aui:input name="memberId" type="hidden" value="<%= memberId %>" />
					<aui:input name="tripId" type="hidden" value="<%= tripId %>" />
					<aui:input name="userId" type="hidden" value="<%= user.getUserId() %>" />

					<aui:field-wrapper name="status">
						<aui:input inlineLabel="right" label="yes" name="status" type="radio" value="1" />
						<aui:input inlineLabel="right" label="no" name="status" type="radio" value="2"  />
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
		Sorry this trip doesnt exist
	</c:otherwise>
</c:choose>