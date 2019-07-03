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
import org.openmrs.module.ServerReport.IndicatorRate;
import org.openmrs.module.ServerReport.IndicatorRateDataSet;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.IndicatorRateDataSetFormValidator;
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
public class ServerReportManageIndicatorRateDataSetController {
	
	protected final Log log = LogFactory.getLog(getClass());

	private ServerReportService getReportService() {
		return Context.getService(ServerReportService.class);
	}

	@RequestMapping(value = "/module/ServerReport/manageIndicatorRateDataSet.form", method = RequestMethod.GET)
	public void manage(ModelMap model,
							 @RequestParam(required = false, defaultValue = "0") Integer indicatorRateDataSetId,
							 @RequestParam(required = false, defaultValue = "0") Integer delId,
							 @RequestParam(required = false, defaultValue = "View") String mode) {

		if (indicatorRateDataSetId != 0) {
			mode = "form";
		}
		if (delId != 0) {
			IndicatorRateDataSet indicatorRateDataSet = getReportService().getOneIndicatorRateDataSet(delId);
			if (indicatorRateDataSet != null) {
				getReportService().removeIndicatorRateDataSet(indicatorRateDataSet);
			}
		}

		List<IndicatorRate> availableIndicatorRates = getReportService().getAllIndicatorRates();

		if (mode.equals("View")) {
			List<IndicatorRateDataSet> indicatorRateDataSets  = getReportService().getAllIndicatorRateDataSets();
			model.addAttribute("indicatorRateDataSets", indicatorRateDataSets);
		} else {
			IndicatorRateDataSet indicatorRateDataSet = new IndicatorRateDataSet();
			if (indicatorRateDataSetId != 0) {
				indicatorRateDataSet = getReportService().getOneIndicatorRateDataSet(indicatorRateDataSetId);
				Set<IndicatorRate> indicatorRates = indicatorRateDataSet.getIndicatorRates();

				availableIndicatorRates.removeAll(indicatorRates);

				model.addAttribute("indicatorRates", indicatorRates);
			}
			model.addAttribute("indicatorRateDataSet", indicatorRateDataSet);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("availableIndicatorRates", availableIndicatorRates);
	}

	@RequestMapping(value = "/module/ServerReport/manageIndicatorRateDataSet.form", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
					   IndicatorRateDataSet indicatorRateDataSet,
					   BindingResult result,
					   ModelMap modelMap) {

		List<IndicatorRate> availableIndicators = getReportService().getAllIndicatorRates();
		if (indicatorRateDataSet.getIndicatorRates() != null)
			availableIndicators.removeAll(indicatorRateDataSet.getIndicatorRates());

		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();
			new IndicatorRateDataSetFormValidator().validate(indicatorRateDataSet, result);

			if (!result.hasErrors()) {

				Integer id = indicatorRateDataSet.getIndicatorRateDataSetId();

				if (!indicatorRateDataSet.getIndicatorRates().isEmpty()) {
					Set<IndicatorRate> indicatorRates = new HashSet<IndicatorRate>();
					for (IndicatorRate indicatorRate :
							indicatorRateDataSet.getIndicatorRates()) {
						indicatorRates.add(getReportService().getIndicatorRateByName(indicatorRate.getName()));
					}
					indicatorRateDataSet.setIndicatorRates(indicatorRates);
				}

				if (id == null) {
					getReportService().saveIndicatorRateDataSet(indicatorRateDataSet);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Groupe d'indicateur enregistré avec succès !");
				}
				else {
					getReportService().updateIndicatorRateDataSet(indicatorRateDataSet);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Groupe d'indicateur modifié avec succès");
				}

				modelMap.addAttribute("mode", "View");
				return "redirect:/module/ServerReport/manageIndicatorRateDataSet.form";
			}
		}
		modelMap.addAttribute("mode", "form");
		modelMap.addAttribute("availableIndicatorRates", availableIndicators);
		modelMap.addAttribute("selectedIndicatorRates", indicatorRateDataSet.getIndicatorRates());
		return null;
	}
}
