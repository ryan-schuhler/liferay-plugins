<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<h3>Create a trip</h3>

		<liferay-ui:success key="createTripSuccess" message="Success! Your new trip id is: " />
		<liferay-ui:error key="createTripError" message="Sorry, something went wrong and we couldn't create your trip." />

		<portlet:actionURL name="addTrip" var="addTripURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
		</portlet:actionURL>

		<aui:form action="<%= addTripURL %>" method="post">
			<aui:input label="Title" name="tripTitle" type="text" />
			<aui:input label="Description" name="tripDescription" type="text" />
			<aui:input label="Friendly Url" name="tripFriendlyUrl" type="text" />
			<aui:button type="submit" value="Submit" />
		</aui:form>
	</c:when>
	<c:otherwise>
		Please <a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to create a trip.
	</c:otherwise>
</c:choose>