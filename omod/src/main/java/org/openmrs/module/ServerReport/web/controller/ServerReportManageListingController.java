/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.ServerReport.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.ServerListing;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.ServerListingFormValidator;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The main controller.
 */
@Controller
public class ServerReportManageListingController {
	
	protected final Log log = LogFactory.getLog(getClass());

	private ServerReportService getReportService() {
		return Context.getService(ServerReportService.class);
	}

	@RequestMapping(value = "/module/ServerReport/manageListing.form", method = RequestMethod.GET)
	public void manageIndicator(ModelMap model,
							 @RequestParam(required = false, defaultValue = "0") Integer listingId,
							 @RequestParam(required = false, defaultValue = "0") Integer delId,
							 @RequestParam(required = false, defaultValue = "View") String mode) {

		if (listingId != 0) {
			mode = "form";
		}
		if (delId != 0) {
			ServerListing serverListing = getReportService().getOneServerListing(delId);
			if (serverListing != null) {
				getReportService().removeServerListing(serverListing);
			}
		}

		if (mode.equals("View")) {
			List<ServerListing> indicators  = getReportService().getAllServerListings();
			model.addAttribute("listings", indicators);
		} else {
			ServerListing serverListing = new ServerListing();
			if (listingId != 0) {
				serverListing = getReportService().getOneServerListing(listingId);
			}
			model.addAttribute("serverListing", serverListing);
		}

		model.addAttribute("mode", mode);
	}

	@RequestMapping(value = "/module/ServerReport/manageListing.form", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
					   ServerListing serverListing,
					   BindingResult result,
					   ModelMap modelMap) {
		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();
			new ServerListingFormValidator().validate(serverListing, result);

			if (!result.hasErrors()) {

				Integer id = serverListing.getListingId();

				if (id == null) {
					getReportService().saveServerListing(serverListing);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Listing enregistré avec succès !");
				}
				else {
					getReportService().updateServerListing(serverListing);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Indicateur modifié avec succès");
				}

				modelMap.addAttribute("mode", "View");
				return "redirect:/module/ServerReport/manageListing.form";
			}
		}
		modelMap.addAttribute("mode", "form");
		return null;
	}

}
