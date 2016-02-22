<%@ include file="/init.jsp" %>

<%
	double tripExpensesTotal = TripExpenseLocalServiceUtil.getTripExpensesTotal(trip.getTripId());
	double memberExpensesTotal = TripExpenseLocalServiceUtil.getTripExpensesTotalByMemberId(trip.getTripId(), tripMember.getTripMemberId());
	int confirmedTripMemberCount = TripMemberLocalServiceUtil.countByTripStatus(trip.getTripId(), 1);
%>

<c:if test="<%= Validator.isNotNull(tripMember) %>">
	<h4>Your Expenses</h4>
	<table>
		<tr>
			<th>Title</th>
			<th>Cost</th>
			<th>Payee</th>
			<th>Options</th>
		</tr>

		<%
		for (TripExpense tripExpense : TripExpenseLocalServiceUtil.getTripExpensesByPayeeMemberId(tripId, tripMember.getTripMemberId())) {
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
			<th>Total You Payed</th>
			<th><%= memberExpensesTotal %></th>
			<th></th>
			<th></th>
		</tr>

		<tr>
			<th>Amount You Owe</th>
			<th><%= (tripExpensesTotal / confirmedTripMemberCount) - memberExpensesTotal %></th>
			<th colspan="2">
				<c:if test="<%= trip.getDisplayTripPayment() && Validator.isNotNull(trip.getTripPayPalEmail()) %>">
					<form method="post" action="https://www.paypal.com/cgi-bin/webscr">
						<input type="hidden" name="button" value="buynow">
						<input type="hidden" name="item_name" value="$title">
						<input type="hidden" name="amount" value="<%= (tripExpensesTotal / confirmedTripMemberCount) - memberExpensesTotal %> %>">
						<input type="hidden" name="shipping" value="0">
						<input type="hidden" name="tax" value="0">
						<input type="hidden" name="notify_url" value="<%= themeDisplay.getURLCurrent() %>">
						<input type="hidden" name="cmd" value="_xclick">
						<input type="hidden" name="business" value="<%= trip.getTripPayPalEmail() %>">
						<input type="hidden" name="bn" value="JavaScriptButton_buynow">
						<input type="hidden" name="env" value="www">
						<button type="submit" class="btn payment-btn paypal-btn">
							Pay Now With PayPal
						</button>
					</form>
				</c:if>
			</th>
		</tr>
	</table>

	<h4>Group Expenses</h4>
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

		<portlet:actionURL name="deleteTripExpense" var="deleteTripExpenseURL2">
			<portlet:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
			<portlet:param name="tripExpenseId" value="<%= String.valueOf(tripExpenseId) %>" />
		</portlet:actionURL>

		<tr>
			<td><%= tripExpense.getTripExpenseTitle() %></td>
			<td><%= tripExpense.getTripExpenseCost() %></td>
			<td>
				<c:if test="<%= Validator.isNotNull(tripExpensePayeeMember) %>">
					<%= tripExpensePayeeMember.getTripMemberName() %>
				</c:if>
			</td>
			<td><a href="<%= deleteTripExpenseURL2 %>">Delete</a></td>
		</tr>

		<%
			}
		%>

		<tr>
			<th>Total</th>
			<th><%= tripExpensesTotal %></th>
			<th></th>
			<th></th>
		</tr>

		<tr>
			<th>Cost Per Person</th>
			<th><%= tripExpensesTotal / confirmedTripMemberCount %></th>
			<th></th>
			<th></th>
		</tr>
	</table>
</c:if>