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

import com.liferay.todsl.model.TripGearGroupItem;
import com.liferay.todsl.service.base.TripGearGroupItemLocalServiceBaseImpl;
import com.liferay.todsl.service.persistence.TripGearGroupItemPersistence;
import com.liferay.todsl.service.persistence.TripGearGroupItemUtil;
import com.liferay.todsl.model.TripMember;

import java.util.List;

/**
 * The implementation of the trip group gear item local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.todsl.service.TripGearGroupItemLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Ryan Schuhler
 * @see com.liferay.todsl.service.base.TripGearGroupItemLocalServiceBaseImpl
 * @see com.liferay.todsl.service.TripGearGroupItemLocalServiceUtil
 */
public class TripGearGroupItemLocalServiceImpl
	extends TripGearGroupItemLocalServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.todsl.service.TripGearGroupItemLocalServiceUtil} to access the trip group gear item local service.
	 */

	public List<TripGearGroupItem> getTripGearGroupItems(long tripId)
		throws Exception {

		List<TripGearGroupItem> tripGearGroupItems =
			TripGearGroupItemUtil.findByTrip(tripId);

		return tripGearGroupItems;
	}

	public List<TripMember> getItemClaimTripMembers(long tripGearGroupItemId)
			throws Exception {

		List<TripMember> tripMembers =
			TripGearGroupItemUtil.getTripMembers(tripGearGroupItemId);

		return tripMembers;
	}


}