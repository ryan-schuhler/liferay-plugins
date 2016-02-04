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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.todsl.model.Trip" %><%@
page import="com.liferay.todsl.model.TripExpense" %><%@
page import="com.liferay.todsl.model.TripGearGroupItem" %><%@
page import="com.liferay.todsl.model.TripGearItem" %><%@
page import="com.liferay.todsl.model.TripGearLendingItem" %><%@
page import="com.liferay.todsl.model.TripMember" %><%@
page import="com.liferay.todsl.model.TripTransportation" %><%@
page import="com.liferay.todsl.service.persistence.TripUtil" %><%@
page import="com.liferay.todsl.service.TripExpenseLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripGearGroupItemLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripGearItemLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripGearLendingItemLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripMemberLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripTransportationLocalServiceUtil" %>

<%@ page import="java.text.SimpleDateFormat" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMM d, yyyy");

String baseURL = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay) + "/-/trip/";

long tripId = ParamUtil.getLong(request, "tripId");

if (tripId == 0) {
	tripId = GetterUtil.getLong(portletPreferences.getValue("preferences--tripId--", "0"));
}

Trip trip = null;

if (tripId != 0) {
	trip = TripLocalServiceUtil.fetchTrip(tripId);
}

long userId = user.getUserId();

TripMember tripMember = TripMemberLocalServiceUtil.getTripMemberByUserId(tripId, userId);
%>