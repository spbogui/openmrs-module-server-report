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
import org.openmrs.module.ServerReport.IndicatorDataSet;
import org.openmrs.module.ServerReport.IndicatorRateDataSet;
import org.openmrs.module.ServerReport.Parameter;
import org.openmrs.module.ServerReport.ServerReport;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.ReportFormValidator;
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
public class  ServerReportManageController {

	protected final Log log = LogFactory.getLog(getClass());

	private ServerReportService getReportService() {
		return Context.getService(ServerReportService.class);
	}

	@RequestMapping(value = "/module/ServerReport/manageReport.form", method = RequestMethod.GET)
	public void manage(ModelMap model,
					   @RequestParam(required = false, defaultValue = "0") Integer reportId,
					   @RequestParam(required = false, defaultValue = "0") Integer delId,
					   @RequestParam(required = false, defaultValue = "") String indicatorType,
					   @RequestParam(required = false, defaultValue = "View") String mode) {
		if (Context.isAuthenticated()) {
			if (reportId != 0) {
				mode = "form";
			}

			ServerReport serverReport = new ServerReport();

			if (mode.equals("form")) {

				if (reportId != 0) {
					serverReport = getReportService().getServerReport(reportId);
				}

				model.addAttribute("report", serverReport);

				if (indicatorType.equals("nonCalculated")){
					List<IndicatorDataSet> indicatorDataSets = getReportService().getAllIndicatorDataSets();
					if (serverReport != null)
					{
						indicatorDataSets.removeAll(serverReport.getIndicatorDataSets());
					}
					model.addAttribute("availableIndicatorDataSets", indicatorDataSets);
					model.addAttribute("selectedIndicatorDataSets", serverReport != null ? serverReport.getIndicatorDataSets() : null);
				} else {
					List<IndicatorRateDataSet> indicatorRateDataSets = getReportService().getAllIndicatorRateDataSets();
					if (serverReport != null)
					{
						indicatorRateDataSets.removeAll(serverReport.getIndicatorRateDataSets());
					}
					model.addAttribute("availableIndicatorRateDataSets", indicatorRateDataSets);
					model.addAttribute("selectedIndicatorRateDataSets", serverReport != null ? serverReport.getIndicatorRateDataSets() : null);
				}

				List<Parameter> availableParameters = getReportService().getAllParameters();
				if (serverReport != null) {
					availableParameters.removeAll(serverReport.getParameters());
				}
				model.addAttribute("availableParameters", availableParameters);
				model.addAttribute("selectedParameters", serverReport != null ? serverReport.getParameters() : null);

			}

			model.addAttribute("serverReport", serverReport);

			if (mode.equals("View")) {
				model.addAttribute("reports", getReportService().getAllServerReports(false));
			}
		}

//		model.addAttribute("user", Context.getAuthenticatedUser());
		model.addAttribute("mode", mode);
	}

	@RequestMapping(value = "/module/ServerReport/manageReport.form", method = RequestMethod.POST)
	public String save (HttpServletRequest request,
						@RequestParam(required = false, defaultValue = "") String indicatorType,
						ServerReport serverReport,
						BindingResult result,
						ModelMap model) {

		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();

			new ReportFormValidator().validate(serverReport, result);

			if (!result.hasErrors()) {
				Integer id = serverReport.getReportId();

				if (serverReport.getIndicatorDataSets() != null) {
					Set<IndicatorDataSet> indicatorDataSets = new HashSet<IndicatorDataSet>();
					for (IndicatorDataSet indicatorDataSet :
							serverReport.getIndicatorDataSets()) {
						indicatorDataSets.add(getReportService().getIndicatorDataSetByName(indicatorDataSet.getName()));
					}
					serverReport.setIndicatorDataSets(indicatorDataSets);
				}
				if (serverReport.getIndicatorRateDataSets() != null) {
					Set<IndicatorRateDataSet> indicatorRateDataSets = new HashSet<IndicatorRateDataSet>();
					for (IndicatorRateDataSet indicatorRateDataSet :
							serverReport.getIndicatorRateDataSets()) {
						indicatorRateDataSets.add(getReportService().getIndicatorRateDataSetByName(indicatorRateDataSet.getName()));
					}
					serverReport.setIndicatorRateDataSets(indicatorRateDataSets);
				}

				if (serverReport.getParameters() != null) {
					Set<Parameter> parameters = new HashSet<Parameter>();
					for (Parameter parameter : serverReport.getParameters()) {
						parameters.add(getReportService().getParameterByName(parameter.getName()));
					}
					serverReport.setParameters(parameters);
				}

				if (id == null) {
					getReportService().saveServerReport(serverReport);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Rapport enregistré avec succès !");
				}
				else {
					getReportService().updateServerReport(serverReport);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Rapport modifié avec succès");
				}

				model.addAttribute("mode", "View");
				return "redirect:/module/ServerReport/manageReport.form";
			}
		}


		if (indicatorType.equals("nonCalculated")) {
			List<IndicatorDataSet> availableIndicatorDataSets = getReportService().getAllIndicatorDataSets();
			if (!serverReport.getIndicatorDataSets().isEmpty()) {
				availableIndicatorDataSets.removeAll(serverReport.getIndicatorDataSets());
			}
			model.addAttribute("availableIndicatorDataSets", availableIndicatorDataSets);
			model.addAttribute("selectedIndicatorDataSets", serverReport.getIndicatorDataSets());
		}
		else {
			List<IndicatorRateDataSet> availableIndicatorRateDataSets = getReportService().getAllIndicatorRateDataSets();
			if (!serverReport.getIndicatorRateDataSets().isEmpty()) {
				availableIndicatorRateDataSets.removeAll(serverReport.getIndicatorRateDataSets());
			}
			model.addAttribute("availableIndicatorRateDataSets", availableIndicatorRateDataSets);
			model.addAttribute("selectedIndicatorRateDataSets", serverReport.getIndicatorRateDataSets());
		}

		List<Parameter> availableParameters = getReportService().getAllParameters();
		Set<Parameter> parameters = new HashSet<Parameter>();
		for (Parameter p : serverReport.getParameters()){
			parameters.add(getReportService().getParameterByName(p.getName()));
		}
		availableParameters.removeAll(parameters);
		serverReport.setParameters(parameters);
		model.addAttribute("availableParameters", availableParameters);
		model.addAttribute("selectedParameters", serverReport.getParameters());

		model.addAttribute("mode", "form");
		return null;
	}
}
