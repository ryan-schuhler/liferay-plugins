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

<%
String appName = ParamUtil.getString(request, "appName", "trip-info");

appName = StringUtil.replace(appName, "-", "_");

String appFile = "/apps/" + appName + ".jsp";
%>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() && Validator.isNotNull(trip) %>">
		<div class="row-fluid">
			<%@ include file="/banner.jspf" %>
		</div>

		<div class="max-full row-fluid">
			<div class="span3 widgets">
				<%@ include file="/widgets.jspf" %>
			</div>

			<div class="display span6">
				<jsp:include page="<%= appFile %>" />
			</div>

			<div class="apps span3">
				<%@ include file="/apps.jspf" %>
			</div>
		</div>
	</c:when>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		Sorry this trip doesn't exist. Create one <a href="<%= baseURL %>create">here</a>.
	</c:when>
	<c:otherwise>
		Please <a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to view this trip.
	</c:otherwise>
</c:choose>

<style type="text/css">
	<%= trip.getTripCss() %>
</style>