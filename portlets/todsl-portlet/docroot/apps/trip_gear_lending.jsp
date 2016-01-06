<%@ include file="/init.jsp" %>

<h3>Gear Lending</h3>

<table>
	<tr>
		<th>Title</th>
		<th>Lender</th>
		<th>Borrower</th>
		<th>Options</th>
	</tr>

	<%
	for (TripGearLendingItem tripGearLendingItem : TripGearLendingItemLocalServiceUtil.getTripGearLendingItems(tripId)) {

		long borrowerId = tripGearLendingItem.getBorrowerUserId();
		User borrower = null;

		if (borrowerId != 0) {
			borrower = UserLocalServiceUtil.getUserById(borrowerId);
		}

		long lenderId = tripGearLendingItem.getLenderUserId();
		User lender = null;

		if (lenderId != 0) {
			lender = UserLocalServiceUtil.getUserById(lenderId);
		}

		long tripGearLendingItemId = tripGearLendingItem.getTripGearLendingItemId();
	%>

		<portlet:actionURL name="claimTripGearLendingItem" var="claimTripGearLendingItemURL">
			<portlet:param name="claim" value="true" />
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripGearLendingItemId" value="<%= String.valueOf(tripGearLendingItemId) %>" />
		</portlet:actionURL>

		<portlet:actionURL name="claimTripGearLendingItem" var="unclaimTripGearLendingItemURL">
			<portlet:param name="claim" value="false" />
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripGearLendingItemId" value="<%= String.valueOf(tripGearLendingItemId) %>" />
		</portlet:actionURL>

		<portlet:actionURL name="deleteTripGearLendingItem" var="deleteTripGearLendingItemURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripGearLendingItemId" value="<%= String.valueOf(tripGearLendingItemId) %>" />
		</portlet:actionURL>

		<tr>
			<td><%= tripGearLendingItem.getItemTitle() %></td>
			<td><%= lender.getFullName() %></td>

			<c:choose>
				<c:when test="<%= Validator.isNotNull(borrower) %>">
					<td><%= borrower.getFullName() %></td>
					<td><a href="<%= unclaimTripGearLendingItemURL %>">Unclaim</a>
				</c:when>
				<c:otherwise>
					<td>---</td>
					<td><a href="<%= claimTripGearLendingItemURL %>">Claim</a>
				</c:otherwise>
			</c:choose>

			<a href="<%= deleteTripGearLendingItemURL %>">Delete</a></td>
		</tr>

	<%
	}
	%>

	<portlet:actionURL name="addTripGearLendingItem" var="addTripGearLendingItemURL">
		<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
	</portlet:actionURL>

	<form action="<%= addTripGearLendingItemURL %>" method="post">
		<aui:model-context model="<%= TripGearLendingItem.class %>" />
		<aui:input name="tripId" type="hidden" value="<%= trip.getTripId() %>" />
		<tr>
			<td>
				<aui:input label="" name="itemTitle" />
			</td>
			<td><%= user.getFullName() %></td>
			<td>---</td>
			<td>
				<aui:button type="submit" value="Add Item" />
			</td>
		</tr>
	</form>
</table>