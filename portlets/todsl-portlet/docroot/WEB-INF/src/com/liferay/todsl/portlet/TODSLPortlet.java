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

package com.liferay.todsl.portlet;

import com.liferay.compat.util.bridges.mvc.MVCPortlet;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.todsl.model.Member;
import com.liferay.todsl.model.Trip;
import com.liferay.todsl.model.TripExpense;
import com.liferay.todsl.model.TripGearItem;
import com.liferay.todsl.model.TripGearLendingItem;
import com.liferay.todsl.model.TripTransportation;
import com.liferay.todsl.service.MemberLocalServiceUtil;
import com.liferay.todsl.service.TripExpenseLocalServiceUtil;
import com.liferay.todsl.service.TripGearItemLocalServiceUtil;
import com.liferay.todsl.service.TripGearLendingItemLocalServiceUtil;
import com.liferay.todsl.service.TripLocalServiceUtil;
import com.liferay.todsl.service.TripTransportationLocalServiceUtil;

import java.text.DateFormat;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

/**
 * @author Ryan Schuhler
 */
public class TODSLPortlet extends MVCPortlet {

	public void addMember(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long memberId = CounterLocalServiceUtil.increment(
			Member.class.getName());

		Member member = MemberLocalServiceUtil.createMember(memberId);

		String memberEmail = ParamUtil.getString(actionRequest, "memberEmail");
		String memberName = ParamUtil.getString(actionRequest, "memberName");
		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		member.setCreateDate(new Date());
		member.setModifiedDate(new Date());
		member.setTripId(tripId);
		member.setUserId(userId);
		member.setUserName(user.getFullName());

		member.setInvitedByUserId(userId);
		member.setMemberEmail(memberEmail);
		member.setMemberName(memberName);

		// Send Email

		MemberLocalServiceUtil.addMember(member);
	}

	public void addTrip(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripId = CounterLocalServiceUtil.increment(Trip.class.getName());

		Trip trip = TripLocalServiceUtil.createTrip(tripId);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		trip.setGroupId(themeDisplay.getScopeGroupId());

		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		trip.setCreateDate(new Date());
		trip.setModifiedDate(new Date());
		trip.setUserId(userId);
		trip.setUserName(user.getFullName());

		String title = ParamUtil.getString(actionRequest, "title");
		String overview = ParamUtil.getString(actionRequest, "overview");

		trip.setOverview(overview);
		trip.setTitle(title);

		TripLocalServiceUtil.addTrip(trip);

		PortletPreferences preferences = actionRequest.getPreferences();

		preferences.setValue("tripId", GetterUtil.getString(tripId));
		preferences.store();
	}

	public void addTripExpense(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripExpenseId = CounterLocalServiceUtil.increment(
			TripExpense.class.getName());

		TripExpense tripExpense = TripExpenseLocalServiceUtil.createTripExpense(
			tripExpenseId);

		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripExpense.setCreateDate(new Date());
		tripExpense.setModifiedDate(new Date());
		tripExpense.setTripId(tripId);
		tripExpense.setUserId(userId);
		tripExpense.setUserName(user.getFullName());

		String expenseTitle = ParamUtil.getString(
			actionRequest, "expenseTitle");

		tripExpense.setExpenseTitle(expenseTitle);

		TripExpenseLocalServiceUtil.addTripExpense(tripExpense);
	}

	public void addTripGearItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearItemId = CounterLocalServiceUtil.increment(
			TripGearItem.class.getName());

		TripGearItem tripGearItem =
			TripGearItemLocalServiceUtil.createTripGearItem(tripGearItemId);

		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripGearItem.setCreateDate(new Date());
		tripGearItem.setModifiedDate(new Date());
		tripGearItem.setTripId(tripId);
		tripGearItem.setUserId(userId);
		tripGearItem.setUserName(user.getFullName());

		String itemTitle = ParamUtil.getString(actionRequest, "itemTitle");

		tripGearItem.setItemTitle(itemTitle);

		TripGearItemLocalServiceUtil.addTripGearItem(tripGearItem);
	}

	public void addTripGearLendingItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearLendingItemId = CounterLocalServiceUtil.increment(
			TripGearLendingItem.class.getName());

		TripGearLendingItem tripGearLendingItem =
			TripGearLendingItemLocalServiceUtil.createTripGearLendingItem(
			tripGearLendingItemId);

		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripGearLendingItem.setCreateDate(new Date());
		tripGearLendingItem.setModifiedDate(new Date());
		tripGearLendingItem.setTripId(tripId);
		tripGearLendingItem.setUserId(userId);
		tripGearLendingItem.setUserName(user.getFullName());

		String itemTitle = ParamUtil.getString(actionRequest, "itemTitle");

		tripGearLendingItem.setItemTitle(itemTitle);
		tripGearLendingItem.setLenderUserId(userId);

		TripGearLendingItemLocalServiceUtil.addTripGearLendingItem(
			tripGearLendingItem);
	}

	public void addTripTransportation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripTransportationId = CounterLocalServiceUtil.increment(
			TripTransportation.class.getName());

		TripTransportation tripTransportation =
			TripTransportationLocalServiceUtil.createTripTransportation(
			tripTransportationId);

		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripTransportation.setCreateDate(new Date());
		tripTransportation.setModifiedDate(new Date());
		tripTransportation.setTripId(tripId);
		tripTransportation.setUserId(userId);
		tripTransportation.setUserName(user.getFullName());

		int seats = ParamUtil.getInteger(actionRequest, "seats");

		tripTransportation.setSeats(seats);

		TripTransportationLocalServiceUtil.addTripTransportation(
			tripTransportation);
	}

	public void claimTripGearLendingItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearLendingItemId = ParamUtil.getLong(
			actionRequest, "tripGearLendingItemId");

		TripGearLendingItem tripGearLendingItem =
			TripGearLendingItemLocalServiceUtil.fetchTripGearLendingItem(
			tripGearLendingItemId);

		tripGearLendingItem.setModifiedDate(new Date());

		long userId = PortalUtil.getUserId(actionRequest);

		tripGearLendingItem.setBorrowerUserId(userId);

		TripGearLendingItemLocalServiceUtil.updateTripGearLendingItem(
			tripGearLendingItem);
	}

	public void deleteMember(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long memberId = ParamUtil.getLong(actionRequest, "memberId");

		MemberLocalServiceUtil.deleteMember(memberId);
	}

	public void deleteTripExpense(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripExpenseId = ParamUtil.getLong(actionRequest, "tripExpenseId");

		TripExpenseLocalServiceUtil.deleteTripExpense(tripExpenseId);
	}

	public void deleteTripGearItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearItemId = ParamUtil.getLong(
			actionRequest, "tripGearItemId");

		TripGearItemLocalServiceUtil.deleteTripGearItem(tripGearItemId);
	}

	public void deleteTripGearLendingItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearLendingItemId = ParamUtil.getLong(
			actionRequest, "tripGearLendingItemId");

		TripGearLendingItemLocalServiceUtil.deleteTripGearLendingItem(
			tripGearLendingItemId);
	}

	public void deleteTripTransportation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripTransportationId = ParamUtil.getLong(
			actionRequest, "tripTransportationId");

		TripTransportationLocalServiceUtil.deleteTripTransportation(
			tripTransportationId);
	}

	public void respondToMemberInvitation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long memberId = ParamUtil.getLong(actionRequest, "memberId");
		int status = ParamUtil.getInteger(actionRequest, "status");
		long userId = ParamUtil.getLong(actionRequest, "userId");

		Member member = MemberLocalServiceUtil.fetchMember(memberId);

		member.setMemberUserId(userId);
		// Set User Permissions/Group

		member.setStatus(status);

		MemberLocalServiceUtil.updateMember(member);
	}

	public void unclaimTripGearLendingItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearLendingItemId = ParamUtil.getLong(
			actionRequest, "tripGearLendingItemId");

		TripGearLendingItem tripGearLendingItem =
			TripGearLendingItemLocalServiceUtil.fetchTripGearLendingItem(
				tripGearLendingItemId);

		tripGearLendingItem.setModifiedDate(new Date());

		tripGearLendingItem.setBorrowerUserId(0);

		TripGearLendingItemLocalServiceUtil.updateTripGearLendingItem(
			tripGearLendingItem);
	}

	public void updateTrip(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");

		Integer capacity = ParamUtil.getInteger(actionRequest, "capacity");
		String costEstimate = ParamUtil.getString(
			actionRequest, "costEstimate");
		boolean displayTripAnnouncements = ParamUtil.getBoolean(
			actionRequest, "displayTripAnnouncements");
		boolean displayTripExpenses = ParamUtil.getBoolean(
			actionRequest, "displayTripExpenses");
		boolean displayTripGear = ParamUtil.getBoolean(
			actionRequest, "displayTripGear");
		boolean displayTripGearLending = ParamUtil.getBoolean(
			actionRequest, "displayTripGearLending");
		boolean displayTripQuestions = ParamUtil.getBoolean(
			actionRequest, "displayTripQuestions");
		boolean displayTripTransportation = ParamUtil.getBoolean(
			actionRequest, "displayTripTransportation");
		String image = ParamUtil.getString(actionRequest, "image");
		String location = ParamUtil.getString(actionRequest, "location");
		String overview = ParamUtil.getString(actionRequest, "overview");
		String title = ParamUtil.getString(actionRequest, "title");
		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		Date tripStart = ParamUtil.getDate(
			actionRequest, "tripStart", dateFormat);
		Date tripEnd = ParamUtil.getDate(actionRequest, "tripEnd", dateFormat);

		Trip trip = TripLocalServiceUtil.getTrip(tripId);

		trip.setModifiedDate(new Date());

		trip.setCapacity(capacity);
		trip.setCostEstimate(costEstimate);
		trip.setImage(image);
		trip.setLocation(location);
		trip.setOverview(overview);
		trip.setTitle(title);
		trip.setTripEnd(tripEnd);
		trip.setTripStart(tripStart);

		trip.setDisplayTripAnnouncements(displayTripAnnouncements);
		trip.setDisplayTripExpenses(displayTripExpenses);
		trip.setDisplayTripGear(displayTripGear);
		trip.setDisplayTripGearLending(displayTripGearLending);
		trip.setDisplayTripQuestions(displayTripQuestions);
		trip.setDisplayTripTransportation(displayTripTransportation);

		TripLocalServiceUtil.updateTrip(trip);
	}

	public void updateTripGearLendingItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearLendingItemId = ParamUtil.getLong(
			actionRequest, "tripGearLendingItemId");

		TripGearLendingItem tripGearLendingItem =
			TripGearLendingItemLocalServiceUtil.fetchTripGearLendingItem(
				tripGearLendingItemId);

		tripGearLendingItem.setModifiedDate(new Date());

		long borrowerUserId = ParamUtil.getLong(
			actionRequest, "borrowerUserId");
		String itemTitle = ParamUtil.getString(actionRequest, "itemTitle");
		long lenderUserId = ParamUtil.getLong(actionRequest, "lenderUserId");

		tripGearLendingItem.setBorrowerUserId(borrowerUserId);
		tripGearLendingItem.setItemTitle(itemTitle);

		TripGearLendingItemLocalServiceUtil.updateTripGearLendingItem(
			tripGearLendingItem);
	}

	private static Log _log = LogFactoryUtil.getLog(TODSLPortlet.class);

}