<%@ include file="/init.jsp" %>

<div class="row-fluid">
	<c:if test="<%= Validator.isNotNull(trip.getTripDescription()) %>">
		<h3>Description</h3>
		<p class="section-sub-heading trip-description"><%= trip.getTripDescription() %></p>
	</c:if>
</div>

<div class="row-fluid">
	<c:if test="<%= trip.getDisplayTripCountdown() %>">
		<div class="span6 trip-countdown">
			<%@ include file="/widgets/trip_countdown.jspf" %>
		</div>
	</c:if>

	<c:if test="<%= trip.getDisplayTripWeather() %>">
		<div class="span6 trip-weather">
			<%@ include file="/widgets/trip_weather.jspf" %>
		</div>
	</c:if>
</div>

<div class="row-fluid">
	<c:if test="<%= trip.getDisplayTripMap() %>">
		<div class="span12 trip-map">
			<%@ include file="/widgets/trip_map.jspf" %>
		</div>
	</c:if>
</div>