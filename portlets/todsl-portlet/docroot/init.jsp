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
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.model.User" %><%@
page import="com.liferay.portal.service.UserLocalServiceUtil" %><%@
page import="com.liferay.todsl.model.Member" %><%@
page import="com.liferay.todsl.model.Trip" %><%@
page import="com.liferay.todsl.model.TripExpense" %><%@
page import="com.liferay.todsl.model.TripGearItem" %><%@
page import="com.liferay.todsl.model.TripGearLendingItem" %><%@
page import="com.liferay.todsl.model.TripTransportation" %><%@
page import="com.liferay.todsl.service.MemberLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripExpenseLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripGearItemLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripGearLendingItemLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripLocalServiceUtil" %><%@
page import="com.liferay.todsl.service.TripTransportationLocalServiceUtil" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />

<%
long tripId = PrefsParamUtil.getLong(PrefsPropsUtil.getPreferences(), request, "preferences--tripId--");
tripId = GetterUtil.getLong(504);

Trip trip = TripLocalServiceUtil.fetchTrip(tripId);
%>

Trip Id: <%= tripId %>