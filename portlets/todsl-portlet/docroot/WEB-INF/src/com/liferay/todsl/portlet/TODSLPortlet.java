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
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.todsl.service.persistence.TripGearGroupItemUtil;
import com.liferay.todsl.service.persistence.TripTransportationUtil;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

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

		long tripExpensePayeeMemberId = ParamUtil.getLong(actionRequest, "tripExpensePayeeMemberId");
		long tripId = ParamUtil.getLong(actionRequest, "tripId");
		long userId = PortalUtil.getUserId(actionRequest);
		User user = UserLocalServiceUtil.getUser(userId);

		tripExpense.setCreateDate(new Date());
		tripExpense.setTripExpensePayeeMemberId(tripExpensePayeeMemberId);
		tripExpense.setModifiedDate(new Date());
		tripExpense.setTripId(tripId);
		tripExpense.setUserId(userId);
		tripExpense.setUserName(user.getFullName());

		double tripExpenseCost = ParamUtil.getDouble(
			actionRequest, "tripExpenseCost");
		String tripExpenseTitle = ParamUtil.getString(
			actionRequest, "tripExpenseTitle");

		tripExpense.setTripExpenseCost(tripExpenseCost);
		tripExpense.setTripExpenseTitle(tripExpenseTitle);

		TripExpenseLocalServiceUtil.addTripExpense(tripExpense);
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

		long itemLenderMemberId = ParamUtil.getLong(actionRequest, "itemLenderMemberId");
		String itemTitle = ParamUtil.getString(actionRequest, "itemTitle");

		tripGearLendingItem.setItemTitle(itemTitle);
		tripGearLendingItem.setItemLenderMemberId(itemLenderMemberId);

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

		TripMember invitee = TripMemberLocalServiceUtil.getTripMemberByUserId(
			tripId, userId);

		if (Validator.isNotNull(invitee)) {
			long inviteeId = invitee.getTripMemberId();

			tripMember.setTripMemberInvitedByMemberId(inviteeId);
		}

		tripMember.setCreateDate(new Date());
		tripMember.setModifiedDate(new Date());
		tripMember.setTripId(tripId);
		tripMember.setUserId(userId);
		tripMember.setUserName(user.getFullName());

		tripMember.setTripMemberEmail(tripMemberEmail);
		tripMember.setTripMemberName(tripMemberName);

		TripMemberLocalServiceUtil.addTripMember(tripMember);

		Trip trip = TripLocalServiceUtil.getTrip(tripId);

		StringBuilder subject = new StringBuilder();
		subject.append("Trip to ");
		subject.append(trip.getTripLocation());
		subject.append("!");

		StringBuilder message = new StringBuilder();
		message.append("You have been invited on a trip to ");
		message.append(trip.getTripLocation());
		message.append(" by ");
		message.append(invitee.getTripMemberName());
		message.append(". Please RSVP <a href='http://theoutdoors.life/web/todsl/home/-/trip/");
		message.append(tripId);
		message.append("/rsvp/");
		message.append(tripMemberId);
		message.append("'>here</a>.");

		sendEmail(tripMemberEmail, subject.toString(), message.toString());
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

		int capacity = ParamUtil.getInteger(actionRequest, "capacity");
		long tripTransportationDriverMemberId =
			ParamUtil.getLong(actionRequest, "tripTransportationDriverMemberId");

		tripTransportation.setCapacity(capacity);
		tripTransportation.setDriverMemberId(tripTransportationDriverMemberId);

		TripTransportationLocalServiceUtil.addTripTransportation(
			tripTransportation);
	}

	public void calculateMemberExpenses(long tripId, long tripMemberId)
		throws Exception {

		List<TripExpense> tripExpenses =
			TripExpenseLocalServiceUtil.getTripExpensesByPayeeMemberId(
				tripId, tripMemberId);

		double memberTotalExpenses = 0;

		for (TripExpense tripExpense : tripExpenses) {
			memberTotalExpenses += tripExpense.getTripExpenseCost();
		}

		TripMember tripMember = TripMemberLocalServiceUtil.getTripMember(
			tripMemberId);

		tripMember.setTripMemberTotalExpenses(memberTotalExpenses);

		TripMemberLocalServiceUtil.updateTripMember(tripMember);
	}

	public void claimTripGearGroupItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearGroupItemId = ParamUtil.getLong(
			actionRequest, "tripGearGroupItemId");

		TripGearGroupItem tripGearGroupItem =
			TripGearGroupItemLocalServiceUtil.fetchTripGearGroupItem(tripGearGroupItemId);

		boolean claim = ParamUtil.getBoolean(actionRequest, "claim");
		long itemClaimTripMemberId = ParamUtil.getLong(actionRequest, "itemClaimTripMemberId");

		boolean hasMember = tripGearGroupItem.hasItemClaimTripMember(itemClaimTripMemberId);

		if (claim && !hasMember) {
			TripGearGroupItemUtil.addTripMember(tripGearGroupItemId, itemClaimTripMemberId);
		}
		else if (!claim && hasMember) {
			TripGearGroupItemUtil.removeTripMember(tripGearGroupItemId, itemClaimTripMemberId);
		}
	}

	public void claimTripGearLendingItem(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripGearLendingItemId = ParamUtil.getLong(
			actionRequest, "tripGearLendingItemId");

		TripGearLendingItem tripGearLendingItem =
				TripGearLendingItemLocalServiceUtil.fetchTripGearLendingItem(
						tripGearLendingItemId);

		long itemBorrowerMemberId = ParamUtil.getLong(actionRequest, "itemBorrowerMemberId");

		tripGearLendingItem.setItemBorrowerMemberId(itemBorrowerMemberId);

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

		boolean claim = ParamUtil.getBoolean(actionRequest, "claim");
		long tripTransportationPassengerMemberId = ParamUtil.getLong(actionRequest, "tripTransportationPassengerMemberId");

		boolean hasCapacity = tripTransportation.getCapacity() > tripTransportation.getTripTransportationTripMembersSize();
		boolean hasMember = tripTransportation.hasTripTransportationTripMember(tripTransportationPassengerMemberId);

		if (claim && hasCapacity && !hasMember) {
			TripTransportationUtil.addTripMember(tripTransportationId, tripTransportationPassengerMemberId);
		}
		else if (!claim && hasMember) {
			TripTransportationUtil.removeTripMember(tripTransportationId, tripTransportationPassengerMemberId);
		}
	}

	public void deleteTripExpense(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long tripExpenseId = ParamUtil.getLong(actionRequest, "tripExpenseId");

		TripExpenseLocalServiceUtil.deleteTripExpense(tripExpenseId);
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

	public void sendEmail(String toAddress, String subject, String message)
		throws Exception {

		try {
			InternetAddress fromInternetAddress = new InternetAddress(
				"no-reply@theoutdoors.life", "The Outdoors Life");

			InternetAddress toInternetAddress = new InternetAddress(toAddress);

			MailMessage mailMessage = new MailMessage(
				fromInternetAddress, subject, message, true);

			mailMessage.setHTMLFormat(true);

			mailMessage.setTo(toInternetAddress);

			MailServiceUtil.sendEmail(mailMessage);
		}
		catch (Exception e) {
			throw new Exception("Unable to send mail message", e);
		}
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
			boolean displayTripPayment = ParamUtil.getBoolean(
					actionRequest, "displayTripPayment");
			boolean displayTripTransportation = ParamUtil.getBoolean(
				actionRequest, "displayTripTransportation");
			boolean displayTripWeather = ParamUtil.getBoolean(
				actionRequest, "displayTripWeather");
			Integer tripCapacity = ParamUtil.getInteger(
				actionRequest, "tripCapacity");
			String tripCostEstimate = ParamUtil.getString(
				actionRequest, "tripCostEstimate");
			String tripCss = ParamUtil.getString(
				actionRequest, "tripCss");
			String tripDescription = ParamUtil.getString(
				actionRequest, "tripDescription");
			Date tripEnd = ParamUtil.getDate(actionRequest, "tripEnd", dateFormat);
			String tripHost = ParamUtil.getString(actionRequest, "tripHost");
			long tripId = ParamUtil.getLong(actionRequest, "tripId");
			String tripImage = ParamUtil.getString(actionRequest, "tripImage");
			String tripLocation = ParamUtil.getString(
				actionRequest, "tripLocation");
			String tripPayPalEmail = ParamUtil.getString(actionRequest, "tripPayPalEmail");
			Date tripStart = ParamUtil.getDate(
				actionRequest, "tripStart", dateFormat);
			String tripTitle = ParamUtil.getString(actionRequest, "tripTitle");

			Trip trip = TripLocalServiceUtil.getTrip(tripId);

			trip.setModifiedDate(new Date());

			trip.setTripCapacity(tripCapacity);
			trip.setTripCostEstimate(tripCostEstimate);
			trip.setTripCss(tripCss);
			trip.setTripDescription(tripDescription);
			trip.setTripEnd(tripEnd);
			trip.setTripHost(tripHost);
			trip.setTripImage(tripImage);
			trip.setTripLocation(tripLocation);
			trip.setTripPayPalEmail(tripPayPalEmail);
			trip.setTripStart(tripStart);
			trip.setTripTitle(tripTitle);

			trip.setDisplayTripCountdown(displayTripCountdown);
			trip.setDisplayTripDiscussion(displayTripDiscussion);
			trip.setDisplayTripExpenses(displayTripExpenses);
			trip.setDisplayTripGearItems(displayTripGearItems);
			trip.setDisplayTripGearGroupItems(displayTripGearGroupItems);
			trip.setDisplayTripGearLendingItems(displayTripGearLendingItems);
			trip.setDisplayTripMap(displayTripMap);
			trip.setDisplayTripPayment(displayTripPayment);
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

		long itemBorrowerMemberId = ParamUtil.getLong(
			actionRequest, "itemBorrowerMemberId");
		String itemTitle = ParamUtil.getString(actionRequest, "itemTitle");
		long lenderUserId = ParamUtil.getLong(actionRequest, "lenderUserId");

		tripGearLendingItem.setItemBorrowerMemberId(itemBorrowerMemberId);
		tripGearLendingItem.setItemTitle(itemTitle);

		TripGearLendingItemLocalServiceUtil.updateTripGearLendingItem(
			tripGearLendingItem);
	}

	private static Log _log = LogFactoryUtil.getLog(TODSLPortlet.class);

}