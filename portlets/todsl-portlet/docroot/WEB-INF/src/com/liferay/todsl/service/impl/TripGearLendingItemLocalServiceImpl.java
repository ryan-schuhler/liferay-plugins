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

import com.liferay.todsl.model.TripGearLendingItem;
import com.liferay.todsl.service.base.TripGearLendingItemLocalServiceBaseImpl;
import com.liferay.todsl.service.persistence.TripGearLendingItemUtil;

import java.util.List;

/**
 * The implementation of the trip gear lending item local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.todsl.service.TripGearLendingItemLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Ryan Schuhler
 * @see com.liferay.todsl.service.base.TripGearLendingItemLocalServiceBaseImpl
 * @see com.liferay.todsl.service.TripGearLendingItemLocalServiceUtil
 */
public class TripGearLendingItemLocalServiceImpl
	extends TripGearLendingItemLocalServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.todsl.service.TripGearLendingItemLocalServiceUtil} to access the trip gear lending item local service.
	 */

	public List<TripGearLendingItem> getTripGearLendingItems(long tripId)
		throws Exception {

		List<TripGearLendingItem> tripGearLendingItems =
			TripGearLendingItemUtil.findByTrip(tripId);

		return tripGearLendingItems;
	}

	public List<TripGearLendingItem> getTripGearLendingItemsByLenderMemberId(
			long tripId, long lenderMemberId)
		throws Exception {

		List<TripGearLendingItem> tripGearLendingItems =
			TripGearLendingItemUtil.findByTripLender(tripId, lenderMemberId);

		return tripGearLendingItems;
	}

}