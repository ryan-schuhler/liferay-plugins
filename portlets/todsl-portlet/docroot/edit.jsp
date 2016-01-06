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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() && Validator.isNotNull(trip) %>">
		<liferay-ui:success key="updateTripSuccess" message='Success! Your new trip id is:' />
		<liferay-ui:error key="updateTripError" message="Sorry, something went wrong and we couldn't update your trip." />

		<div class="max-full edit-trip">
			<div class="row-fluid">
				<portlet:actionURL name="updateTrip" var="updateTripURL">
					<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				</portlet:actionURL>

				<a href="<%= baseURL %><%= trip.getTripId() %>">< Go to trip</a>

				Trip Id: <%= trip.getTripId() %>

				<aui:form action="<%= updateTripURL %>" method="post">

					<aui:model-context bean="<%= trip %>" model="<%= Trip.class %>" />

					<aui:input name="tripId" type="hidden" />

					<aui:input name="tripTitle" />
					<aui:input name="tripImage" />
					<aui:input name="tripHost" />
					<aui:input name="tripDescription" />
					<aui:input name="tripFriendlyUrl" />
					<aui:input name="tripCostEstimate" />
					<aui:input name="tripStart" />
					<aui:input name="tripEnd" />
					<aui:input name="tripLocation" />
					<aui:input name="tripCapacity" />

					<aui:input name="displayTripCountdown" />
					<aui:input name="displayTripDiscussion" />
					<aui:input name="displayTripExpenses" />
					<aui:input name="displayTripGearItems" />
					<aui:input name="displayTripGearGroupItems" />
					<aui:input name="displayTripGearLendingItems" />
					<aui:input name="displayTripMap" />
					<aui:input name="displayTripTransportation" />
					<aui:input name="displayTripWeather" />

					<aui:button type="submit" value="Submit" />
				</aui:form>
			</div>
		</div>
	</c:when>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		Sorry this trip doesn't exist. <a href="<%=baseURL %>/create">Create</a> a new one.
	</c:when>
	<c:otherwise>
		Please <a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to edit this trip.
	</c:otherwise>
</c:choose>