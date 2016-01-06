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

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<liferay-ui:success key="configurationSaved" message="Your configuration was saved." />
<liferay-ui:error key="tripDoesNotExist" message="Sorry, your trip does not exist. Please create one." />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
	<aui:input name="preferences--tripId--" type="text" value="<%= tripId %>" />

	<aui:button type="submit" value="Save" />
</aui:form>

<portlet:actionURL name="addTrip" var="addTripURL">
</portlet:actionURL>

<form action="<%= addTripURL %>" method="post">
	<aui:input label="Title" name="tripTitle" type="text" />
	<aui:input label="Description" name="tripDescription" type="text" />
	<aui:input label="Friendly Url" name="tripFriendlyUrl" type="text" />
	<aui:button type="submit" value="Submit" />
</form>