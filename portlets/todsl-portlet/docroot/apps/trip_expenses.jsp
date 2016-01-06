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

		long expensePayeeId = tripExpense.getExpensePayeeUserId();
		User expensePayee = null;

		if (expensePayeeId != 0) {
			expensePayee = UserLocalServiceUtil.getUserById(expensePayeeId);
		}

		long tripExpenseId = tripExpense.getTripExpenseId();
	%>

		<portlet:actionURL name="deleteTripExpense" var="deleteTripExpenseURL">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripExpenseId" value="<%= String.valueOf(tripExpenseId) %>" />
			<portlet:param name="tripId" value="<%= String.valueOf(trip.getTripId()) %>" />
		</portlet:actionURL>

		<tr>
			<td><%= tripExpense.getExpenseTitle() %></td>
			<td><%= tripExpense.getExpenseCost() %></td>
			<td><%= expensePayee.getFullName() %></td>
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

		<tr>
			<td>
				<aui:input label="" name="expenseTitle" />
			</td>
			<td>
				<aui:input label="" name="expenseCost" />
			</td>
			<td><%= user.getFullName() %></td>
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