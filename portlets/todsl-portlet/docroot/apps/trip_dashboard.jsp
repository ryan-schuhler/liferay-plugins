<%@ include file="/init.jsp" %>

<div class="row-fluid span6">
	<c:if test="<%= Validator.isNotNull(trip.getTripDescription()) %>">
		<div class="trip-description">
			<h3>Description</h3>
			<p class="section-sub-heading"><%= trip.getTripDescription() %></p>
		</div>
	</c:if>

	<c:if test="<%= trip.getDisplayTripGearItems() %>">
		<div class="trip-gear">
			<%@ include file="/apps/trip_gear.jspf" %>
		</div>
	</c:if>
</div>

<div class="row-fluid span6">
	<c:if test="<%= trip.getDisplayTripDiscussion() %>">
		<div class="trip-discussion">
			<%@ include file="/apps/trip_discussion.jspf" %>
		</div>
	</c:if>
</div>

<div class="row-fluid span12">
	<c:if test="<%= trip.getDisplayTripWeather() %>">
		<div class="span12 trip-weather">
			<%@ include file="/apps/trip_weather.jspf" %>
		</div>
	</c:if>

	<c:if test="<%= trip.getDisplayTripMap() %>">
		<div class="span12 text-center trip-map">
			<%@ include file="/apps/trip_map.jspf" %>
		</div>
	</c:if>
</div>

<c:if test="<%= !themeDisplay.isSignedIn() %>">
	<div class="row-fluid span12">
		<a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to interact with this trip.
	</div>
</c:if>
