<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() && Validator.isNotNull(trip) %>">

		<%
		String tabs = ParamUtil.getString(request, "tabs", "pre");
		%>

		<div class="banner lazy-load" id="tripBanner" style="background-image: url(<%= trip.getTripImage() %>);">
			<div class="banner-content max-medium">
				<h2 class="banner-heading">
					<%= trip.getTripTitle() %> <time datetime="2015-05-23 20:00/2015-05-25 20:00"><%= trip.getTripStart() %> - <%= trip.getTripEnd() %></time>
				</h2>
			</div>
		</div>

		<nav id="tripNavigation">
			<ul aria-label="Pages" class="nav nav-tabs nav-content" role="menubar">
				<li class='tab <%= tabs.equals("pre") ? "active" : "" %>'>
					<a href="<%= layout.getFriendlyURL() %>/-/trip/<%= trip.getTripId() %>/pre" role="menuitem">Home</a>
				</li>
				<li class='tab <%= tabs.equals("prep") ? "active" : "" %>'>
					<a href="<%= layout.getFriendlyURL() %>/-/trip/<%= trip.getTripId() %>/prep" role="menuitem">Prep</a>
				</li>
				<li class='tab <%= tabs.equals("post") ? "active" : "" %>'>
					<a href="<%= layout.getFriendlyURL() %>/-/trip/<%= trip.getTripId() %>/post" role="menuitem">Post</a>
				</li>
				<li class='tab <%= tabs.equals("edit") ? "active" : "" %>'>
					<a href="<%= layout.getFriendlyURL() %>/-/trip/<%= trip.getTripId() %>/edit" role="menuitem">Edit</a>
				</li>
			</ul>
		</nav>

		<c:choose>
			<c:when test='<%= tabs.equals("edit") %>'>
				<%@ include file="/edit.jspf" %>
			</c:when>
			<c:when test='<%= tabs.equals("prep") %>'>
				<%@ include file="/view_prep.jspf" %>
			</c:when>
			<c:when test='<%= tabs.equals("post") %>'>
				<%@ include file="/view_post.jspf" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/view_pre.jspf" %>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<portlet:actionURL name="addTrip" var="addTripURL">
		</portlet:actionURL>

		<form action="<%= addTripURL %>" method="post">
			<aui:input label="Title" name="tripTitle" type="text" />
			<aui:input label="Description" name="tripDescription" type="text" />
			<aui:input label="Friendly Url" name="tripFriendlyUrl" type="text" />
			<aui:button type="submit" value="Submit" />
		</form>
	</c:when>
	<c:otherwise>
		Please <a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to view trip.
	</c:otherwise>
</c:choose>