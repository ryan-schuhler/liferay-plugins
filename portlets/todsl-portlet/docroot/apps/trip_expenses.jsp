<%@ include file="/init.jsp" %>

<h3>Cost</h3>

<table>
	<tr>
		<th>Title</th>
		<th>Cost</th>
		<th>Payee</th>
		<th>Options</th>
	</tr>

	<%
	for (TripExpense tripExpense : TripExpenseLocalServiceUtil.getTripExpenses(tripId)) {
		long tripExpenseId = tripExpense.getTripExpenseId();

		long tripExpensePayeeMemberId = tripExpense.getTripExpensePayeeMemberId();

		TripMember tripExpensePayeeMember = TripMemberLocalServiceUtil.fetchTripMember(tripExpensePayeeMemberId);
	%>

		<portlet:actionURL name="deleteTripExpense" var="deleteTripExpenseURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripExpenseId" value="<%= String.valueOf(tripExpenseId) %>" />
			<portlet:param name="tripId" value="<%= String.valueOf(trip.getTripId()) %>" />
		</portlet:actionURL>

		<tr>
			<td><%= tripExpense.getTripExpenseTitle() %></td>
			<td><%= tripExpense.getTripExpenseCost() %></td>
			<td>
				<c:if test="<%= Validator.isNotNull(tripExpensePayeeMember) %>">
					<%= tripExpensePayeeMember.getTripMemberName() %>
				</c:if>
			</td>
			<td><a href="<%= deleteTripExpenseURL %>">Delete</a></td>
		</tr>

	<%
	}
	%>

	<portlet:actionURL name="addTripExpense" var="addTripExpenseURL">
		<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
	</portlet:actionURL>

	<form action="<%= addTripExpenseURL %>" method="post">
		<aui:model-context model="<%= TripExpense.class %>" />
		<aui:input name="tripId" type="hidden" value="<%= trip.getTripId() %>" />
		<aui:input name="tripExpensePayeeMemberId" type="hidden" value="<%= tripMember.getTripMemberId() %>" />

		<tr>
			<td>
				<aui:input label="" name="tripExpenseTitle" />
			</td>
			<td>
				<aui:input label="" name="tripExpenseCost" />
			</td>
			<td><%= tripMember.getTripMemberName() %></td>
			<td>
				<aui:button type="submit" value="Add Expense" />
			</td>
		</tr>

	</form>

	<tr>
		<th>Total</th>
		<th><%= trip.getTripTotalCost() %></th>
		<th></th>
		<th></th>
	</tr>
</table>