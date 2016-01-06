<%@ include file="/init.jsp" %>

<c:if test="<%= trip.getDisplayTripDiscussion() %>">
	<div class="trip-discussion">
		<h3>Discussion</h3>

		<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

		<div class="trip-discussion">
			<liferay-ui:discussion
				className="<%= Trip.class.getName() %>"
				classPK="<%= trip.getTripId() %>"
				formAction="<%= discussionURL %>"
				formName="fm2"
				ratingsEnabled="<%= false %>"
				redirect="<%= themeDisplay.getURLCurrent() %>"
				userId="<%= trip.getUserId() %>"
			/>
		</div>
	</div>
</c:if>