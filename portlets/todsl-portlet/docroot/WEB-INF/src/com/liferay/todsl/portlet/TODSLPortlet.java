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
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.todsl.model.Trip;
import com.liferay.todsl.model.TripExpense;
import com.liferay.todsl.model.TripGearGroupItem;
import com.liferay.todsl.model.TripGearItem;
import com.liferay.todsl.model.TripGearLendingItem;
import com.liferay.todsl.model.TripMember;
import com.liferay.todsl.model.TripTransportation;
import com.liferay.todsl.service.TripExpenseLocalServiceUtil;
import com.liferay.todsl.service.TripGearGroupItemLocalServiceUtil;
import com.liferay.todsl.service.TripGearItemLocalServiceUtil;
import com.liferay.todsl.service.TripGearLendingItemLocalServiceUtil;
import com.liferay.todsl.service.TripLocalServiceUtil;
import com.liferay.todsl.service.TripMemberLocalServiceUtil;
import com.liferay.todsl.service.TripTransportationLocalServiceUtil;

import java.text.DateFormat;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Ryan Schuhler
 */
public class TODSLPortlet extends MVCPortlet {

	public void addTrip(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
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

			String tripDescription = ParamUtil.getString(
				actionRequest, "tripDescription");
			String tripFriendlyUrl = ParamUtil.getString(
				actionRequest, "tripFriendlyUrl");
			String tripTitle = ParamUtil.getString(actionRequest, "tripTitle");

			trip.setTripDescription(tripDescription);
			trip.setTripFriendlyUrl(tripFriendlyUrl);
			trip.setTripTitle(tripTitle);

			TripLocalServiceUtil.addTrip(trip);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, "createTripError");
		}
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
		tripExpense.setExpensePayeeUserId(userId);
		tripExpense.setModifiedDate(new Date());
		tripExpense.setTripId(tripId);
		tripExpense.setUserId(userId);
		tripExpense.setUserName(user.getFullName());

		String expenseTitle = ParamUtil.getString(
			actionRequest, "expenseTitle");
		double expenseCost = ParamUtil.getDouble(actionRequest, "expenseCost");

		tripExpense.setExpenseTitle(expenseTitle);
		tripExpense.setExpenseCost(expenseCost);

		TripExpenseLocalServiceUtil.addTripExpense(tripExpense);

		Trip trip = TripLocalServiceUtil.getTrip(tripId);

		double oldTripTotalCost = trip.getTripTotalCost();

		double newTripTotalCost = oldTripTotalCost + expenseCost;

		trip.setTripTotalCost(newTripTotalCost);

		TripLocalServiceUtil.updateTrip(trip);
	}

	public void addTripGearGroupItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearGroupItemId = CounterLocalServiceUtil.increment(
			TripGearGroupItem.class.getName());

		TripGearGroupItem tripGearGroupItem =
			TripGearGroupItemLocalServiceUtil.createTripGearGroupItem(
				tripGearGroupItemId);

		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripGearGroupItem.setCreateDate(new Date());
		tripGearGroupItem.setModifiedDate(new Date());
		tripGearGroupItem.setTripId(tripId);
		tripGearGroupItem.setUserId(userId);
		tripGearGroupItem.setUserName(user.getFullName());

		int itemQuantity = ParamUtil.getInteger(actionRequest, "itemQuantity");
		String itemTitle = ParamUtil.getString(actionRequest, "itemTitle");

		tripGearGroupItem.setItemQuantity(itemQuantity);
		tripGearGroupItem.setItemTitle(itemTitle);

		TripGearGroupItemLocalServiceUtil.addTripGearGroupItem(
			tripGearGroupItem);
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

	public void addTripMember(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripMemberId = CounterLocalServiceUtil.increment(
			TripMember.class.getName());

		TripMember tripMember = TripMemberLocalServiceUtil.createTripMember(
			tripMemberId);

		String tripMemberEmail = ParamUtil.getString(
			actionRequest, "tripMemberEmail");
		String tripMemberName = ParamUtil.getString(
			actionRequest, "tripMemberName");
		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripMember.setCreateDate(new Date());
		tripMember.setModifiedDate(new Date());
		tripMember.setTripId(tripId);
		tripMember.setUserId(userId);
		tripMember.setUserName(user.getFullName());

		tripMember.setTripMemberInvitedByUserId(userId);
		tripMember.setTripMemberEmail(tripMemberEmail);
		tripMember.setTripMemberName(tripMemberName);

		// Send Email

		TripMemberLocalServiceUtil.addTripMember(tripMember);
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

		tripTransportation.setDriverUserId(userId);

		int capacity = ParamUtil.getInteger(actionRequest, "capacity");

		tripTransportation.setCapacity(capacity);
		tripTransportation.setCount(0);

		TripTransportationLocalServiceUtil.addTripTransportation(
			tripTransportation);
	}

	public void claimTripGearGroupItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearGroupItemId = ParamUtil.getLong(
			actionRequest, "tripGearGroupItemId");

		TripGearGroupItem tripGearGroupItem =
			TripGearGroupItemLocalServiceUtil.fetchTripGearGroupItem(
			tripGearGroupItemId);

		tripGearGroupItem.setModifiedDate(new Date());

		boolean claim = ParamUtil.getBoolean(actionRequest, "claim");
		int itemQuantity = tripGearGroupItem.getItemQuantity();
		int itemCount = tripGearGroupItem.getItemCount();
		String itemClaimUserIds = tripGearGroupItem.getItemClaimUserIds();
		String userId = StringUtil.valueOf(PortalUtil.getUserId(actionRequest));

		if (claim && !StringUtil.contains(itemClaimUserIds, userId)) {
			itemClaimUserIds = StringUtil.add(itemClaimUserIds, userId);

			itemCount++;
		}
		else if (StringUtil.contains(itemClaimUserIds, userId)) {
			itemClaimUserIds = StringUtil.remove(itemClaimUserIds, userId);

			itemCount--;
		}

		tripGearGroupItem.setItemCount(itemCount);
		tripGearGroupItem.setItemClaimUserIds(itemClaimUserIds);

		TripGearGroupItemLocalServiceUtil.updateTripGearGroupItem(
			tripGearGroupItem);
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

		boolean claim = ParamUtil.getBoolean(actionRequest, "claim");
		long userId = 0;

		if (claim) {
			userId = PortalUtil.getUserId(actionRequest);
		}

		tripGearLendingItem.setBorrowerUserId(userId);

		TripGearLendingItemLocalServiceUtil.updateTripGearLendingItem(
			tripGearLendingItem);
	}

	public void claimTripTransportation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripTransportationId = ParamUtil.getLong(
			actionRequest, "tripTransportationId");

		TripTransportation tripTransportation =
			TripTransportationLocalServiceUtil.fetchTripTransportation(
			tripTransportationId);

		tripTransportation.setModifiedDate(new Date());

		boolean claim = ParamUtil.getBoolean(actionRequest, "claim");
		int capacity = tripTransportation.getCapacity();
		int count = tripTransportation.getCount();
		String passengerUserIds = tripTransportation.getPassengerUserIds();
		String userId = StringUtil.valueOf(PortalUtil.getUserId(actionRequest));

		if (claim && !StringUtil.contains(passengerUserIds, userId)) {
			passengerUserIds = StringUtil.add(passengerUserIds, userId);

			count++;
		}
		else if (StringUtil.contains(passengerUserIds, userId)) {
			passengerUserIds = StringUtil.remove(passengerUserIds, userId);

			count--;
		}

		tripTransportation.setCount(count);
		tripTransportation.setPassengerUserIds(passengerUserIds);

		TripTransportationLocalServiceUtil.updateTripTransportation(
			tripTransportation);
	}

	public void deleteTripExpense(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripExpenseId = ParamUtil.getLong(actionRequest, "tripExpenseId");

		TripExpenseLocalServiceUtil.deleteTripExpense(tripExpenseId);

		TripExpense tripExpense = TripExpenseLocalServiceUtil.getTripExpense(
			tripExpenseId);

		double expenseCost = tripExpense.getExpenseCost();

		long tripId = ParamUtil.getLong(actionRequest, "tripId");

		Trip trip = TripLocalServiceUtil.getTrip(tripId);

		double oldTripTotalCost = trip.getTripTotalCost();

		double newTripTotalCost = oldTripTotalCost - expenseCost;

		trip.setTripTotalCost(newTripTotalCost);

		TripLocalServiceUtil.updateTrip(trip);
	}

	public void deleteTripGearGroupItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearGroupItemId = ParamUtil.getLong(
			actionRequest, "tripGearGroupItemId");

		TripGearGroupItemLocalServiceUtil.deleteTripGearGroupItem(
			tripGearGroupItemId);
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

	public void deleteTripMember(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripMemberId = ParamUtil.getLong(actionRequest, "tripMemberId");

		TripMemberLocalServiceUtil.deleteTripMember(tripMemberId);
	}

	public void deleteTripTransportation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripTransportationId = ParamUtil.getLong(
			actionRequest, "tripTransportationId");

		TripTransportationLocalServiceUtil.deleteTripTransportation(
			tripTransportationId);
	}

	public void respondToTripMemberInvitation(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripMemberId = ParamUtil.getLong(actionRequest, "tripMemberId");
		int tripMemberStatus = ParamUtil.getInteger(
			actionRequest, "tripMemberStatus");
		long userId = ParamUtil.getLong(actionRequest, "userId");

		TripMember tripMember = TripMemberLocalServiceUtil.fetchTripMember(
			tripMemberId);

		tripMember.setTripMemberUserId(userId);

		// Set User Permissions/Group

		tripMember.setTripMemberStatus(tripMemberStatus);

		TripMemberLocalServiceUtil.updateTripMember(tripMember);
	}

	public void updateTrip(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			DateFormat dateFormat =
				DateFormatFactoryUtil.getSimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");

			boolean displayTripCountdown = ParamUtil.getBoolean(
				actionRequest, "displayTripCountdown");
			boolean displayTripDiscussion = ParamUtil.getBoolean(
				actionRequest, "displayTripDiscussion");
			boolean displayTripExpenses = ParamUtil.getBoolean(
				actionRequest, "displayTripExpenses");
			boolean displayTripGearItems = ParamUtil.getBoolean(
				actionRequest, "displayTripGearItems");
			boolean displayTripGearLendingItems = ParamUtil.getBoolean(
				actionRequest, "displayTripGearLendingItems");
			boolean displayTripGearGroupItems = ParamUtil.getBoolean(
				actionRequest, "displayTripGearGroupItems");
			boolean displayTripMap = ParamUtil.getBoolean(
				actionRequest, "displayTripMap");
			boolean displayTripTransportation = ParamUtil.getBoolean(
				actionRequest, "displayTripTransportation");
			boolean displayTripWeather = ParamUtil.getBoolean(
				actionRequest, "displayTripWeather");
			Integer tripCapacity = ParamUtil.getInteger(
				actionRequest, "tripCapacity");
			String tripCostEstimate = ParamUtil.getString(
				actionRequest, "tripCostEstimate");
			String tripDescription = ParamUtil.getString(
				actionRequest, "tripDescription");
			Date tripEnd = ParamUtil.getDate(actionRequest, "tripEnd", dateFormat);
			String tripHost = ParamUtil.getString(actionRequest, "tripHost");
			long tripId = ParamUtil.getLong(actionRequest, "tripId");
			String tripImage = ParamUtil.getString(actionRequest, "tripImage");
			String tripLocation = ParamUtil.getString(
				actionRequest, "tripLocation");
			Date tripStart = ParamUtil.getDate(
				actionRequest, "tripStart", dateFormat);
			String tripTitle = ParamUtil.getString(actionRequest, "tripTitle");

			Trip trip = TripLocalServiceUtil.getTrip(tripId);

			trip.setModifiedDate(new Date());

			trip.setTripCapacity(tripCapacity);
			trip.setTripCostEstimate(tripCostEstimate);
			trip.setTripDescription(tripDescription);
			trip.setTripEnd(tripEnd);
			trip.setTripHost(tripHost);
			trip.setTripImage(tripImage);
			trip.setTripLocation(tripLocation);
			trip.setTripStart(tripStart);
			trip.setTripTitle(tripTitle);

			trip.setDisplayTripCountdown(displayTripCountdown);
			trip.setDisplayTripDiscussion(displayTripDiscussion);
			trip.setDisplayTripExpenses(displayTripExpenses);
			trip.setDisplayTripGearItems(displayTripGearItems);
			trip.setDisplayTripGearGroupItems(displayTripGearGroupItems);
			trip.setDisplayTripGearLendingItems(displayTripGearLendingItems);
			trip.setDisplayTripMap(displayTripMap);
			trip.setDisplayTripTransportation(displayTripTransportation);
			trip.setDisplayTripWeather(displayTripWeather);

			TripLocalServiceUtil.updateTrip(trip);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, "updateTripError");
		}
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