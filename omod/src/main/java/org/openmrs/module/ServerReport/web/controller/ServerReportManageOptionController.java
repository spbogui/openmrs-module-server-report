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
import org.openmrs.module.ServerReport.CategoryOption;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.CategoryOptionFormValidator;
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
public class ServerReportManageOptionController {
	
	protected final Log log = LogFactory.getLog(getClass());

	private ServerReportService getReportService() {
		return Context.getService(ServerReportService.class);
	}

	@RequestMapping(value = "/module/ServerReport/manageOption.form", method = RequestMethod.GET)
	public void manageOption(ModelMap model,
							 @RequestParam(required = false, defaultValue = "0") Integer optionId,
							 @RequestParam(required = false, defaultValue = "0") Integer delId,
							 @RequestParam(required = false, defaultValue = "View") String mode) {

		if (optionId != 0) {
			mode = "form";
		}
		if (delId != 0) {
			CategoryOption option = getReportService().getOneCategoryOption(delId);
			if (option != null) {
				getReportService().removeCategoryOption(option);
			}
		}

		if (mode.equals("View")) {
			List<CategoryOption> options  = getReportService().getAllOptions();
			model.addAttribute("options", options);
		} else {
			CategoryOption categoryOption = new CategoryOption();
			if (optionId != 0) {
				categoryOption = getReportService().getOneCategoryOption(optionId);
			}
			model.addAttribute("categoryOption", categoryOption);
		}

		model.addAttribute("mode", mode);
	}

	@RequestMapping(value = "/module/ServerReport/manageOption.form", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
					   @RequestParam(required = false, defaultValue = "0") Integer optionId,
					   CategoryOption categoryOption,
					   BindingResult result,
					   ModelMap modelMap) {
		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();
			new CategoryOptionFormValidator().validate(categoryOption, result);

			if (!result.hasErrors()) {

				Integer id = categoryOption.getOptionId();

				if (id == null) {
					getReportService().saveCategoryOption(categoryOption);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Option de catégorie enregistrée avec succès !");
				}
				else {
					getReportService().updateCategoryOption(categoryOption);
					session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Option de catégorie modifiée avec succès");
				}

				modelMap.addAttribute("mode", "View");
				return "redirect:/module/ServerReport/manageOption.form";
			}
		}
		modelMap.addAttribute("mode", "form");
		return null;
	}

	private Boolean checkingCodeExistence(CategoryOption option) {
		CategoryOption existingOptionWithCode = getReportService().getOptionByCode(option.getCode());
		return checking(option, existingOptionWithCode);
	}

	private Boolean checkingNameExistence(CategoryOption option) {
		CategoryOption existingOptionWithName = getReportService().getOptionByName(option.getName());
		return checking(option, existingOptionWithName);
	}

	private Boolean checking(CategoryOption option, CategoryOption existing) {
		if (existing == null) {
			return false;
		} else {
			if (option.getOptionId() != null) {
				if (existing.getOptionId().equals(option.getOptionId())) {
					return false;
				}
			}
		}
		return true;
	}
}
