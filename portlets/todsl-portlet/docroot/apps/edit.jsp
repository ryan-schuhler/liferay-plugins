<%@ include file="/init.jsp" %>

<%
	Date tripStart = trip.getTripStart();

	if (Validator.isNull(tripStart)) {
		tripStart = new Date();
	}

	String tripStartYear = DateUtil.getDate(tripStart, "yyyy", request.getLocale());
	String tripStartMonth = DateUtil.getDate(tripStart, "MM", request.getLocale());
	String tripStartDay = DateUtil.getDate(tripStart, "dd", request.getLocale());

	Date tripEnd = trip.getTripEnd();

	if (Validator.isNull(tripEnd)) {
		tripEnd = new Date();
	}

	String tripEndYear = DateUtil.getDate(tripEnd, "yyyy", request.getLocale());
	String tripEndMonth = DateUtil.getDate(tripEnd, "MM", request.getLocale());
	String tripEndDay = DateUtil.getDate(tripEnd, "dd", request.getLocale());
%>

<c:if test="<%= Validator.isNotNull(tripMember) %>">
	<liferay-ui:success key="updateTripSuccess" message="Success! Your new trip id is:" />
	<liferay-ui:error key="updateTripError" message="Sorry, something went wrong and we couldn't update your trip." />

	<portlet:actionURL name="updateTrip" var="updateTripURL">
		<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
	</portlet:actionURL>

	<aui:form action="<%= updateTripURL %>" method="post">

		<aui:model-context bean="<%= trip %>" model="<%= Trip.class %>" />

		<aui:input name="tripId" type="hidden" />

		<aui:input name="tripTitle" />
		<aui:input name="tripImage" />
		<aui:input name="tripHost" />
		<aui:input name="tripDescription" type="textarea" />
		<aui:input name="tripFriendlyUrl" />
		<aui:input name="tripCostEstimate" />
		Start Date
		<liferay-ui:input-date
			name="tripStart"
			yearValue="<%= GetterUtil.getInteger(tripStartYear) %>"
			monthValue="<%= GetterUtil.getInteger(tripStartMonth) - 1 %>"
			dayValue="<%= GetterUtil.getInteger(tripStartDay) %>"
			dayParam="d1"
			monthParam="m1"
			yearParam="y1"
		/>
		End Date
		<liferay-ui:input-date
			name="tripEnd"
			yearValue="<%= GetterUtil.getInteger(tripEndYear) %>"
			monthValue="<%= GetterUtil.getInteger(tripEndMonth) - 1 %>"
			dayValue="<%= GetterUtil.getInteger(tripEndDay) %>"
			dayParam="d2"
			monthParam="m2"
			yearParam="y2"
		/>
		<aui:input name="tripLocation" />
		<aui:input name="tripCapacity" />
		<aui:input name="tripPayPalEmail" />

		<aui:input name="displayTripCountdown" />
		<aui:input name="displayTripDiscussion" />
		<aui:input name="displayTripExpenses" />
		<aui:input name="displayTripGearItems" />
		<aui:input name="displayTripGearGroupItems" />
		<aui:input name="displayTripGearLendingItems" />
		<aui:input name="displayTripMap" />
		<aui:input name="displayTripPayment" />
		<aui:input name="displayTripTransportation" />
		<aui:input name="displayTripWeather" />

		<aui:input name="tripCss" type="textarea" />

		<aui:button type="submit" value="Submit" />
	</aui:form>
</c:if>