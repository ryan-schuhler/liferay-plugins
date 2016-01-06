<%@ include file="/init.jsp" %>

<h3>What to Bring</h3>

<table>
	<tr>
		<th>Title</th>
		<th>Options</th>
	</tr>

	<%
	for (TripGearItem tripGearItem : TripGearItemLocalServiceUtil.getTripGearItems(tripId)) {

		long tripGearItemId = tripGearItem.getTripGearItemId();
	%>

		<portlet:actionURL name="deleteTripGearItem" var="deleteTripGearItemURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripGearItemId" value="<%= String.valueOf(tripGearItemId) %>" />
		</portlet:actionURL>

		<tr>
			<td><%= tripGearItem.getItemTitle() %></td>

			<td><a href="<%= deleteTripGearItemURL %>">Delete</a></td>
		</tr>

	<%
	}
	%>

	<portlet:actionURL name="addTripGearItem" var="addTripGearItemURL">
		<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
	</portlet:actionURL>

	<form action="<%= addTripGearItemURL %>" method="post">
		<aui:model-context model="<%= TripGearItem.class %>" />
		<aui:input name="tripId" type="hidden" value="<%= trip.getTripId() %>" />
		<tr>
			<td>
				<aui:input label="" name="itemTitle" />
			</td>

			<td>
				<aui:button type="submit" value="Add Item" />
			</td>
		</tr>
	</form>

</table>