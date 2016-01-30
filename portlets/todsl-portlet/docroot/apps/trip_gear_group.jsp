<%@ page import="com.liferay.todsl.service.TripGearGroupItemLocalServiceUtil" %>

<%@ include file="/init.jsp" %>

<h3>Group Gear</h3>

<table>
	<tr>
		<th>Title</th>
		<th>Quantity</th>
		<th>Users</th>
		<th>Needed</th>
		<th>Options</th>
	</tr>

	<%
	for (TripGearGroupItem tripGearGroupItem : TripGearGroupItemLocalServiceUtil.getTripGearGroupItems(tripId)) {

		long tripGearGroupItemId = tripGearGroupItem.getTripGearGroupItemId();
	%>

		<portlet:actionURL name="claimTripGearGroupItem" var="claimTripGearGroupItemURL">
			<portlet:param name="claim" value="true" />
			<portlet:param name="itemClaimTripMemberId" value="<%= String.valueOf(tripMember.getTripMemberId()) %>" />
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripGearGroupItemId" value="<%= String.valueOf(tripGearGroupItemId) %>" />
		</portlet:actionURL>

		<portlet:actionURL name="claimTripGearGroupItem" var="unclaimTripGearGroupItemURL">
			<portlet:param name="claim" value="false" />
			<portlet:param name="itemClaimTripMemberId" value="<%= String.valueOf(tripMember.getTripMemberId()) %>" />
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripGearGroupItemId" value="<%= String.valueOf(tripGearGroupItemId) %>" />
		</portlet:actionURL>

		<portlet:actionURL name="deleteTripGearGroupItem" var="deleteTripGearGroupItemURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripGearGroupItemId" value="<%= String.valueOf(tripGearGroupItemId) %>" />
		</portlet:actionURL>

		<tr>
			<td><%= tripGearGroupItem.getItemTitle() %></td>
			<td><%= tripGearGroupItem.getItemQuantity() %></td>
			<td>

				<%
				for (TripMember itemClaimTripMember : tripGearGroupItem.getItemClaimTripMembers()) {
				%>

					<span><%= itemClaimTripMember.getTripMemberName() %></span>

				<%
				}
				%>

			</td>
			<td><%= tripGearGroupItem.getItemQuantity() - tripGearGroupItem.getItemClaimTripMembersSize() %></td>
			<td>
				<c:if test="<%= (tripGearGroupItem.getItemQuantity() > tripGearGroupItem.getItemClaimTripMembersSize()) && !tripGearGroupItem.hasItemClaimTripMember(tripMember.getTripMemberId()) %>">
					<a href="<%= claimTripGearGroupItemURL %>">Claim</a>
				</c:if>

				<c:if test="<%= tripGearGroupItem.hasItemClaimTripMember(tripMember.getTripMemberId()) %>">
					<a href="<%= unclaimTripGearGroupItemURL %>">Unclaim</a>
				</c:if>

				<a href="<%= deleteTripGearGroupItemURL %>">Delete</a>
			</td>
		</tr>

	<%
	}
	%>

	<portlet:actionURL name="addTripGearGroupItem" var="addTripGearGroupItemURL">
		<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
	</portlet:actionURL>

	<form action="<%= addTripGearGroupItemURL %>" method="post">
		<aui:model-context model="<%= TripGearGroupItem.class %>" />
		<aui:input name="tripId" type="hidden" value="<%= trip.getTripId() %>" />

		<tr>
			<td>
				<aui:input label="" name="itemTitle" />
				</td>
			<td>
				<aui:input label="" name="itemQuantity" />
			</td>
			<td>---</td>
			<td>---</td>
			<td>
				<aui:button type="submit" value="Add Item" />
			</td>
		</tr>
	</form>
</table>