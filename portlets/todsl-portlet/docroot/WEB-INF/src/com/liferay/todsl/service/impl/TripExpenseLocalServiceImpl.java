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

package com.liferay.todsl.service.impl;

import com.liferay.todsl.model.TripExpense;
import com.liferay.todsl.service.base.TripExpenseLocalServiceBaseImpl;
import com.liferay.todsl.service.persistence.TripExpenseUtil;

import java.util.List;

/**
 * The implementation of the trip expense local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.todsl.service.TripExpenseLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Ryan Schuhler
 * @see com.liferay.todsl.service.base.TripExpenseLocalServiceBaseImpl
 * @see com.liferay.todsl.service.TripExpenseLocalServiceUtil
 */
public class TripExpenseLocalServiceImpl extends TripExpenseLocalServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.todsl.service.TripExpenseLocalServiceUtil} to access the trip expense local service.
	 */

	public List<TripExpense> getTripExpenses(long tripId) throws Exception {
		List<TripExpense> tripExpenses = TripExpenseUtil.findByTrip(tripId);

		return tripExpenses;
	}

	public List<TripExpense> getTripExpensesByPayeeMemberId(
			long tripId, long tripExpensePayeeMemberId)
		throws Exception {

		List<TripExpense> tripExpenses = TripExpenseUtil.findByTripMember(
			tripId, tripExpensePayeeMemberId);

		return tripExpenses;
	}
}