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
import com.liferay.portal.kernel.util.StringUtil;
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
import com.liferay.todsl.model.TripGroupGearItem;
import com.liferay.todsl.model.TripTransportation;
import com.liferay.todsl.service.MemberLocalServiceUtil;
import com.liferay.todsl.service.TripExpenseLocalServiceUtil;
import com.liferay.todsl.service.TripGearItemLocalServiceUtil;
import com.liferay.todsl.service.TripGearLendingItemLocalServiceUtil;
import com.liferay.todsl.service.TripGroupGearItemLocalServiceUtil;
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

		String tripDescription = ParamUtil.getString(
			actionRequest, "tripDescription");
		String tripFriendlyUrl = ParamUtil.getString(
			actionRequest, "tripFriendlyUrl");
		String tripTitle = ParamUtil.getString(actionRequest, "tripTitle");

		trip.setTripDescription(tripDescription);
		trip.setTripFriendlyUrl(tripFriendlyUrl);
		trip.setTripTitle(tripTitle);

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

	public void addTripGroupGearItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGroupGearItemId = CounterLocalServiceUtil.increment(
			TripGroupGearItem.class.getName());

		TripGroupGearItem tripGroupGearItem =
			TripGroupGearItemLocalServiceUtil.createTripGroupGearItem(tripGroupGearItemId);

		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripGroupGearItem.setCreateDate(new Date());
		tripGroupGearItem.setModifiedDate(new Date());
		tripGroupGearItem.setTripId(tripId);
		tripGroupGearItem.setUserId(userId);
		tripGroupGearItem.setUserName(user.getFullName());

		int itemQuantity = ParamUtil.getInteger(actionRequest, "itemQuantity");
		String itemTitle = ParamUtil.getString(actionRequest, "itemTitle");

		tripGroupGearItem.setItemQuantity(itemQuantity);
		tripGroupGearItem.setItemTitle(itemTitle);

		TripGroupGearItemLocalServiceUtil.addTripGroupGearItem(tripGroupGearItem);
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

	public void claimTripGroupGearItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGroupGearItemId = ParamUtil.getLong(
			actionRequest, "tripGroupGearItemId");

		TripGroupGearItem tripGroupGearItem =
			TripGroupGearItemLocalServiceUtil.fetchTripGroupGearItem(
			tripGroupGearItemId);

		tripGroupGearItem.setModifiedDate(new Date());

		boolean claim = ParamUtil.getBoolean(actionRequest, "claim");
		int itemQuantity = tripGroupGearItem.getItemQuantity();
		int itemCount = tripGroupGearItem.getItemCount();
		String itemClaimUserIds = tripGroupGearItem.getItemClaimUserIds();
		String userId = StringUtil.valueOf(PortalUtil.getUserId(actionRequest));

		if (claim && !StringUtil.contains(itemClaimUserIds, userId)) {
			itemClaimUserIds = StringUtil.add(itemClaimUserIds, userId);

			itemCount++;
		}
		else if (StringUtil.contains(itemClaimUserIds, userId)) {
			itemClaimUserIds = StringUtil.remove(itemClaimUserIds, userId);

			itemCount--;
		}

		tripGroupGearItem.setItemCount(itemCount);
		tripGroupGearItem.setItemClaimUserIds(itemClaimUserIds);

		TripGroupGearItemLocalServiceUtil.updateTripGroupGearItem(
			tripGroupGearItem);
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

	public void deleteTripGroupGearItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGroupGearItemId = ParamUtil.getLong(
			actionRequest, "tripGroupGearItemId");

		TripGroupGearItemLocalServiceUtil.deleteTripGroupGearItem(
			tripGroupGearItemId);
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

	public void updateTrip(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");

		boolean displayTripAnnouncements = ParamUtil.getBoolean(
			actionRequest, "displayTripAnnouncements");
		boolean displayTripCountdown = ParamUtil.getBoolean(
			actionRequest, "displayTripCountdown");
		boolean displayTripDiscussion = ParamUtil.getBoolean(
			actionRequest, "displayTripDiscussion");
		boolean displayTripExpenses = ParamUtil.getBoolean(
			actionRequest, "displayTripExpenses");
		boolean displayTripGear = ParamUtil.getBoolean(
			actionRequest, "displayTripGear");
		boolean displayTripGearLending = ParamUtil.getBoolean(
			actionRequest, "displayTripGearLending");
		boolean displayTripGroupGear = ParamUtil.getBoolean(
			actionRequest, "displayTripGroupGear");
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
		trip.setTripImage(tripImage);
		trip.setTripLocation(tripLocation);
		trip.setTripStart(tripStart);
		trip.setTripTitle(tripTitle);

		trip.setDisplayTripAnnouncements(displayTripAnnouncements);
		trip.setDisplayTripCountdown(displayTripCountdown);
		trip.setDisplayTripDiscussion(displayTripDiscussion);
		trip.setDisplayTripExpenses(displayTripExpenses);
		trip.setDisplayTripGear(displayTripGear);
		trip.setDisplayTripGearLending(displayTripGearLending);
		trip.setDisplayTripGroupGear(displayTripGroupGear);
		trip.setDisplayTripMap(displayTripMap);
		trip.setDisplayTripTransportation(displayTripTransportation);
		trip.setDisplayTripWeather(displayTripWeather);

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