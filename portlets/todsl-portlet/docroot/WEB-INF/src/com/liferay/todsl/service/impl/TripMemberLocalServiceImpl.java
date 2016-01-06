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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.todsl.model.TripMember;
import com.liferay.todsl.service.TripMemberLocalServiceUtil;
import com.liferay.todsl.service.base.TripMemberLocalServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the tripMember local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.todsl.service.TripMemberLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Ryan Schuhler
 * @see com.liferay.todsl.service.base.TripMemberLocalServiceBaseImpl
 * @see com.liferay.todsl.service.TripMemberLocalServiceUtil
 */
public class TripMemberLocalServiceImpl extends TripMemberLocalServiceBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.todsl.service.TripMemberLocalServiceUtil} to access the tripMember local service.
	 */

	public List<TripMember> getTripMembers(long tripId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			TripMember.class);

		Property tripIdProperty = PropertyFactoryUtil.forName("tripId");

		dynamicQuery.add(tripIdProperty.eq(tripId));

		Order order = OrderFactoryUtil.asc("createDate");

		dynamicQuery.addOrder(order);

		List<TripMember> tripMembers = TripMemberLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		return tripMembers;
	}
}