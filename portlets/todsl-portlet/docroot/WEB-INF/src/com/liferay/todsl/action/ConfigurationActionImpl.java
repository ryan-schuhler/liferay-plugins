package com.liferay.todsl.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.todsl.service.TripLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

/**
 * @author Ryan Schuhler
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
		PortletConfig portletConfig, ActionRequest actionRequest,
		ActionResponse actionResponse) throws Exception {

		PortletPreferences preferences = actionRequest.getPreferences();

		String tripId = ParamUtil.getString(actionRequest, "preferences--tripId--");

		try {
			TripLocalServiceUtil.getTrip(GetterUtil.getLong(tripId));

			preferences.setValue("preferences--tripId--", tripId);

			preferences.store();

			SessionMessages.add(actionRequest, "configurationSaved");
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, "tripDoesNotExist");
		}
	}

}