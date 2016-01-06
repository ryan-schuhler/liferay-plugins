<%@ include file="/init.jsp" %>

<h3>Ride Sharing</h3>

<table>
		<tr>
			<th>Driver</th>
			<th>Passengers</th>
			<th>Seats</th>
			<th>Remaining</th>
			<th>Options</th>
		</tr>

	<%
	for (TripTransportation tripTransportation : TripTransportationLocalServiceUtil.getTripTransportations(tripId)) {

		long driverId = tripTransportation.getDriverUserId();
		User driver = null;

		if (driverId != 0) {
			driver = UserLocalServiceUtil.getUserById(driverId);
		}

		long tripTransportationId = tripTransportation.getTripTransportationId();
	%>

		<portlet:actionURL name="claimTripTransportation" var="claimTripTransportationURL">
			<portlet:param name="claim" value="true" />
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripTransportationId" value="<%= String.valueOf(tripTransportationId) %>" />
		</portlet:actionURL>

		<portlet:actionURL name="claimTripTransportation" var="unclaimTripTransportationURL">
			<portlet:param name="claim" value="false" />
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripTransportationId" value="<%= String.valueOf(tripTransportationId) %>" />
		</portlet:actionURL>

		<portlet:actionURL name="deleteTripTransportation" var="deleteTripTransportationURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripTransportationId" value="<%= String.valueOf(tripTransportationId) %>" />
		</portlet:actionURL>

		<tr>
			<c:choose>
				<c:when test="<%= Validator.isNotNull(driver) %>">
					<td><%= driver.getFullName() %></td>
				</c:when>
				<c:otherwise>
					<td>---</td>
				</c:otherwise>
			</c:choose>

			<td>

				<%
				for (String item : StringUtil.split(tripTransportation.getPassengerUserIds())) {

					long transportationUserId = GetterUtil.getLong(item);
					String transportationUserName = StringPool.BLANK;

					User transportationUser = UserLocalServiceUtil.fetchUserById(transportationUserId);

					if (Validator.isNotNull(transportationUser)) {
						transportationUserName = transportationUser.getFullName();
					}
				%>

					<span><%= transportationUserName %></span>

				<%
				}
				%>

			</td>

			<td><%= tripTransportation.getCapacity() %></td>

			<td><%= tripTransportation.getCapacity() - tripTransportation.getCount() %></td>

			<td>
				<c:if test="<%= (tripTransportation.getCapacity() > tripTransportation.getCount()) && !StringUtil.contains(tripTransportation.getPassengerUserIds(), String.valueOf(user.getUserId())) %>">
					<a href="<%= claimTripTransportationURL %>">Claim</a>
				</c:if>
				<c:if test="<%= StringUtil.contains(tripTransportation.getPassengerUserIds(), String.valueOf(user.getUserId())) %>">
					<a href="<%= unclaimTripTransportationURL %>">Unclaim</a>
				</c:if>
				<a href="<%= deleteTripTransportationURL %>">Delete</a>
			</td>
		</tr>

	<%
	}
	%>

	<portlet:actionURL name="addTripTransportation" var="addTripTransportationURL">
		<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
	</portlet:actionURL>

	<form action="<%= addTripTransportationURL %>" method="post">
		<aui:model-context model="<%= TripTransportation.class %>" />
		<aui:input name="tripId" type="hidden" value="<%= trip.getTripId() %>" />

		<tr>
			<td><%= user.getFullName() %></td>
			<td>---</td>
			<td>
				<aui:input label="" name="capacity" />
			</td>
			<td>---</td>
			<td>
				<aui:button type="submit" value="Add Car" />
			</td>
		</tr>
	</form>
</table>