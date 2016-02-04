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

import com.liferay.todsl.model.TripMember;
import com.liferay.todsl.model.TripTransportation;
import com.liferay.todsl.service.base.TripTransportationLocalServiceBaseImpl;
import com.liferay.todsl.service.persistence.TripTransportationUtil;

import java.util.List;

/**
 * The implementation of the trip transportation local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.todsl.service.TripTransportationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Ryan Schuhler
 * @see com.liferay.todsl.service.base.TripTransportationLocalServiceBaseImpl
 * @see com.liferay.todsl.service.TripTransportationLocalServiceUtil
 */
public class TripTransportationLocalServiceImpl
	extends TripTransportationLocalServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.todsl.service.TripTransportationLocalServiceUtil} to access the trip transportation local service.
	 */

	public List<TripTransportation> getTripTransportations(long tripId)
		throws Exception {

		List<TripTransportation> tripTransportations =
			TripTransportationUtil.findByTrip(tripId);

		return tripTransportations;
	}

	public List<TripTransportation> getTripTransportationsByDriverId(
			long tripId, long driverMemberId)
		throws Exception {

		List<TripTransportation> tripTransportations =
			TripTransportationUtil.findByTripDriver(tripId, driverMemberId);

		return tripTransportations;
	}

	public List<TripMember> getTripTransportationTripMembers(long tripTransportationId)
			throws Exception {

		List<TripMember> tripMembers = TripTransportationUtil.getTripMembers(
			tripTransportationId);

		return tripMembers;
	}

}