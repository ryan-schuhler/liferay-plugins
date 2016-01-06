<%@ include file="/init.jsp" %>

<table>
	<tr>
		<th>Trip Title</th>
		<th>Trip Id</th>
	</tr>

	<%
	for (Trip curTrip : TripLocalServiceUtil.getTrips(-1, -1)) {
	%>

		<tr>
			<td><%= curTrip.getTripTitle() %></td>
			<td><a href="<%= baseURL %><%= curTrip.getTripId() %>"><%= curTrip.getTripId() %></a></td>
		</tr>

	<%
	}
	%>

</table>