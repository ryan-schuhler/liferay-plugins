<%@ include file="/init.jsp" %>

<c:if test="<%= Validator.isNotNull(tripMember) %>">
	<table>
		<tr>
			<th>Title</th>
			<th>Lender</th>
			<th>ItemBorrower</th>
			<th>Options</th>
		</tr>

		<%
		for (TripGearLendingItem tripGearLendingItem : TripGearLendingItemLocalServiceUtil.getTripGearLendingItems(tripId)) {
			long tripGearLendingItemId = tripGearLendingItem.getTripGearLendingItemId();

			TripMember borrower = TripMemberLocalServiceUtil.fetchTripMember(tripGearLendingItem.getItemBorrowerMemberId());
			TripMember lender = TripMemberLocalServiceUtil.fetchTripMember(tripGearLendingItem.getItemLenderMemberId());
		%>

			<portlet:actionURL name="claimTripGearLendingItem" var="claimTripGearLendingItemURL">
				<portlet:param name="itemBorrowerMemberId" value="<%= String.valueOf(tripMember.getTripMemberId()) %>" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripGearLendingItemId" value="<%= String.valueOf(tripGearLendingItemId) %>" />
			</portlet:actionURL>

			<portlet:actionURL name="claimTripGearLendingItem" var="unclaimTripGearLendingItemURL">
				<portlet:param name="itemBorrowerMemberId" value="0" />
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripGearLendingItemId" value="<%= String.valueOf(tripGearLendingItemId) %>" />
			</portlet:actionURL>

			<portlet:actionURL name="deleteTripGearLendingItem" var="deleteTripGearLendingItemURL">
				<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
				<portlet:param name="tripGearLendingItemId" value="<%= String.valueOf(tripGearLendingItemId) %>" />
			</portlet:actionURL>

			<tr>
				<td><%= tripGearLendingItem.getItemTitle() %></td>

				<td>
					<c:if test="<%= Validator.isNotNull(lender) %>">
						<%= lender.getTripMemberName() %>
					</c:if>
				</td>

				<c:choose>
					<c:when test="<%= Validator.isNotNull(borrower) %>">
						<td><%= borrower.getTripMemberName() %></td>
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
			<aui:input name="itemLenderMemberId" type="hidden" value="<%= tripMember.getTripMemberId() %>" />

			<tr>
				<td>
					<aui:input label="" name="itemTitle" />
				</td>
				<td><%= tripMember.getTripMemberName() %></td>
				<td>---</td>
				<td>
					<aui:button type="submit" value="Add Item" />
				</td>
			</tr>
		</form>
	</table>
</c:if>