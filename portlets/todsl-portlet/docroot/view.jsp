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
		<div class="row-fluid">
			<%@ include file="/banner.jspf" %>
		</div>

		<div class="row-fluid">
			<%@ include file="/trip_info.jspf" %>
		</div>

		<div class="row-fluid">
			<div class="span3">
				<%@ include file="/primary_actions.jspf" %>
			</div>

			<div class="span6">
				<%@ include file="/activity.jspf" %>
			</div>

			<div class="span3">
				<%@ include file="/secondary_actions.jspf" %>
			</div>
		</div>
	</c:when>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		Sorry this trip doesn't exist.
	</c:when>
	<c:otherwise>
		Please <a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to view trip.
	</c:otherwise>
</c:choose>

<style type="text/css">
.aui .todsl-portlet table, .aui .todsl-portlet table tr {
	border: 1px solid gray;
	text-align: center;
}

.aui .todsl-portlet table tr {
	border-bottom-width: 0;
	border-left-width: 0;
}

.aui .todsl-portlet table td, .aui .todsl-portlet table th {
	max-width: 100px;
	padding: 5px;
	vertical-align: middle;
}

.aui td .control-group {
	margin: 0;
}

.aui td .btn {
	font-size: inherit;
	padding: 5px;
}

.aui .todsl-portlet table input {
	box-shadow: none;
	box-sizing: border-box;
	font-size: 1em;
	height: auto;
	margin: 0;
	text-align: center;
	width: 100%;
}
</style>