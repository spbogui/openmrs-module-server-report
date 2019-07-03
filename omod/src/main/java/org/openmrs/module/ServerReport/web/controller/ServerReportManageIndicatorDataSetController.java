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
import org.openmrs.module.ServerReport.Indicator;
import org.openmrs.module.ServerReport.IndicatorDataSet;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.IndicatorDataSetFormValidator;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main controller.
 */
@Controller
public class ServerReportManageIndicatorDataSetController {
	
	protected final Log log = LogFactory.getLog(getClass());

	private ServerReportService getReportService() {
		return Context.getService(ServerReportService.class);
	}

	@RequestMapping(value = "/module/ServerReport/manageIndicatorDataSet.form", method = RequestMethod.GET)
	public void manage(ModelMap model,
							 @RequestParam(required = false, defaultValue = "0") Integer indicatorDataSetId,
							 @RequestParam(required = false, defaultValue = "0") Integer delId,
							 @RequestParam(required = false, defaultValue = "View") String mode) {

		if (indicatorDataSetId != 0) {
			mode = "form";
		}
		if (delId != 0) {
			IndicatorDataSet indicatorDataSet = getReportService().getOneIndicatorDataSet(delId);
			if (indicatorDataSet != null) {
				getReportService().removeIndicatorDataSet(indicatorDataSet);
			}
		}

		List<Indicator> availableIndicators = getReportService().getAllIndicators();

		if (mode.equals("View")) {
			List<IndicatorDataSet> indicatorDataSets  = getReportService().getAllIndicatorDataSets();
			model.addAttribute("indicatorDataSets", indicatorDataSets);
		} else {
			IndicatorDataSet indicatorDataSet = new IndicatorDataSet();
			if (indicatorDataSetId != 0) {
				indicatorDataSet = getReportService().getOneIndicatorDataSet(indicatorDataSetId);
				Set<Indicator> indicators = indicatorDataSet.getIndicators();

				availableIndicators.removeAll(indicators);

				model.addAttribute("indicators", indicators);
			}
			model.addAttribute("indicatorDataSet", indicatorDataSet);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("availableIndicators", availableIndicators);
	}

	@RequestMapping(value = "/module/ServerReport/manageIndicatorDataSet.form", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
					   IndicatorDataSet indicatorDataSet,
					   BindingResult result,
					   ModelMap modelMap) {

		List<Indicator> availableIndicators = getReportService().getAllIndicators();
		if (indicatorDataSet.getIndicators() != null)
			availableIndicators.removeAll(indicatorDataSet.getIndicators());

		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();
			new IndicatorDataSetFormValidator().validate(indicatorDataSet, result);

			if (!result.hasErrors()) {

				Integer id = indicatorDataSet.getIndicatorDataSetId();

				if (!indicatorDataSet.getIndicators().isEmpty()) {
					Set<Indicator> indicators = new HashSet<Indicator>();
					for (Indicator indicator :
							indicatorDataSet.getIndicators()) {
						indicators.add(getReportService().getIndicatorByName(indicator.getName()));
					}
					indicatorDataSet.setIndicators(indicators);
				}


				if (id == null) {
					getReportService().saveIndicatorDataSet(indicatorDataSet);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Catégorie enregistrée avec succès !");
				}
				else {
					getReportService().updateIndicatorDataSet(indicatorDataSet);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Catégorie modifiée avec succès");
				}

				modelMap.addAttribute("mode", "View");
				return "redirect:/module/ServerReport/manageIndicatorDataSet.form";
			}
		}
		modelMap.addAttribute("mode", "form");
		modelMap.addAttribute("availableIndicators", availableIndicators);
		modelMap.addAttribute("selectedIndicators", indicatorDataSet.getIndicators());
		return null;
	}
}
