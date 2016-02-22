<%@ include file="/init.jsp" %>

<c:if test="<%= Validator.isNotNull(tripMember) %>">
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
			long tripTransportationId = tripTransportation.getTripTransportationId();

			TripMember driver = TripMemberLocalServiceUtil.fetchTripMember(tripTransportation.getDriverMemberId());
		%>

			<portlet:actionURL name="claimTripTransportation" var="claimTripTransportationURL">
				<portlet:param name="claim" value="true" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripTransportationPassengerMemberId" value="<%= String.valueOf(tripMember.getTripMemberId()) %>" />
				<portlet:param name="tripTransportationId" value="<%= String.valueOf(tripTransportationId) %>" />
			</portlet:actionURL>

			<portlet:actionURL name="claimTripTransportation" var="unclaimTripTransportationURL">
				<portlet:param name="claim" value="false" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripTransportationPassengerMemberId" value="<%= String.valueOf(tripMember.getTripMemberId()) %>" />
				<portlet:param name="tripTransportationId" value="<%= String.valueOf(tripTransportationId) %>" />
			</portlet:actionURL>

			<portlet:actionURL name="deleteTripTransportation" var="deleteTripTransportationURL">
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripTransportationId" value="<%= String.valueOf(tripTransportationId) %>" />
			</portlet:actionURL>

			<tr>
				<c:choose>
					<c:when test="<%= Validator.isNotNull(driver) %>">
						<td><%= driver.getTripMemberName() %></td>
					</c:when>
					<c:otherwise>
						<td>---</td>
					</c:otherwise>
				</c:choose>

				<td>

					<%
					for (TripMember passenger : tripTransportation.getTripTransportationTripMembers()) {
					%>

						<span><%= passenger.getTripMemberName() %></span>

					<%
					}
					%>

				</td>

				<td><%= tripTransportation.getCapacity() %></td>

				<td><%= tripTransportation.getCapacity() - tripTransportation.getTripTransportationTripMembersSize() %></td>

				<td>
					<c:if test="<%= (tripTransportation.getCapacity() > tripTransportation.getTripTransportationTripMembersSize()) && !tripTransportation.hasTripTransportationTripMember(tripMember.getTripMemberId()) %>">
						<a href="<%= claimTripTransportationURL %>">Claim</a>
					</c:if>
					<c:if test="<%= tripTransportation.hasTripTransportationTripMember(tripMember.getTripMemberId()) %>">
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
			<aui:input name="tripTransportationDriverMemberId" type="hidden" value="<%= tripMember.getTripMemberId() %>" />

			<tr>
				<td><%= tripMember.getTripMemberName() %></td>
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
</c:if>