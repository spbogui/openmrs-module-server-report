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
import org.openmrs.module.ServerReport.Category;
import org.openmrs.module.ServerReport.Indicator;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.IndicatorFormValidator;
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
public class ServerReportManageIndicatorController {
	
	protected final Log log = LogFactory.getLog(getClass());

	private ServerReportService getReportService() {
		return Context.getService(ServerReportService.class);
	}

	@RequestMapping(value = "/module/ServerReport/manageIndicators.form", method = RequestMethod.GET)
	public void manageIndicator(ModelMap model,
							 @RequestParam(required = false, defaultValue = "0") Integer indicatorId,
							 @RequestParam(required = false, defaultValue = "0") Integer delId,
							 @RequestParam(required = false, defaultValue = "") String indicatorType,
							 @RequestParam(required = false, defaultValue = "View") String mode) {

		if (indicatorId != 0) {
			mode = "form";
		}
		if (delId != 0) {
			Indicator indicator = getReportService().getOneIndicator(delId);
			if (indicator != null) {
				getReportService().removeIndicator(indicator);
			}
		}

		if (mode.equals("View")) {
			List<Indicator> indicators  = getReportService().getAllIndicators();
			model.addAttribute("indicators", indicators);
		} else {
			Indicator indicator = new Indicator();
			if (indicatorId != 0) {
				indicator = getReportService().getOneIndicator(indicatorId);
			}
			model.addAttribute("indicator", indicator);
			model.addAttribute("categories", getReportService().getAllCategories());
		}

		model.addAttribute("mode", mode);
	}

	@RequestMapping(value = "/module/ServerReport/manageIndicators.form", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
					   Indicator indicator,
					   BindingResult result,
					   ModelMap modelMap) {
		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();
			new IndicatorFormValidator().validate(indicator, result);

			if (!result.hasErrors()) {

				Integer id = indicator.getIndicatorId();

				if (indicator.getCategory() != null) {
					Category category = getReportService().getCategoryByName(indicator.getCategory().getName());
					indicator.setCategory(category);
				}


				if (id == null) {
					getReportService().saveIndicator(indicator);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Indicateur de catégorie enregistrée avec succès !");
				}
				else {
					getReportService().updateIndicator(indicator);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Indicateur de catégorie modifiée avec succès");
				}

				modelMap.addAttribute("mode", "View");
				return "redirect:/module/ServerReport/manageIndicators.form";
			}
		}
		modelMap.addAttribute("categories", getReportService().getAllCategories());
		modelMap.addAttribute("mode", "form");
		return null;
	}

}
