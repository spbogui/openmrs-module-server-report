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
import org.openmrs.module.ServerReport.CategoryOption;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.CategoryFormValidator;
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
public class ServerReportManageCategoryController {
	
	protected final Log log = LogFactory.getLog(getClass());

	private ServerReportService getReportService() {
		return Context.getService(ServerReportService.class);
	}

	@RequestMapping(value = "/module/ServerReport/manageCategory.form", method = RequestMethod.GET)
	public void manageOption(ModelMap model,
							 @RequestParam(required = false, defaultValue = "0") Integer categoryId,
							 @RequestParam(required = false, defaultValue = "0") Integer delId,
							 @RequestParam(required = false, defaultValue = "View") String mode) {

		if (categoryId != 0) {
			mode = "form";
		}
		if (delId != 0) {
			Category category = getReportService().getOneCategory(delId);
			if (category != null) {
				getReportService().removeCategory(category);
			}
		}

		List<CategoryOption> availableCategoryOptions = getReportService().getAllOptions();

		if (mode.equals("View")) {
			List<Category> categories  = getReportService().getAllCategories();
			model.addAttribute("categories", categories);
		} else {
			Category category = new Category();
			if (categoryId != 0) {
				category = getReportService().getOneCategory(categoryId);
				Set<CategoryOption> categoryOptions = category.getOptions();

				availableCategoryOptions.removeAll(categoryOptions);

				model.addAttribute("categoryOptions", categoryOptions);
			}
			model.addAttribute("category", category);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("availableOptions", availableCategoryOptions);
	}

	@RequestMapping(value = "/module/ServerReport/manageCategory.form", method = RequestMethod.POST)
	public String save(HttpServletRequest request,
					   Category category,
					   BindingResult result,
					   ModelMap modelMap) {

		List<CategoryOption> availableCategoryOptions = getReportService().getAllOptions();
		if (category.getOptions() != null)
			availableCategoryOptions.removeAll(category.getOptions());

		if (Context.isAuthenticated()) {
			HttpSession session = request.getSession();

			new CategoryFormValidator().validate(category, result);


			if (!result.hasErrors()) {

				Integer id = category.getCategoryId();

				if (!category.getOptions().isEmpty()) {
					Set<CategoryOption> options = new HashSet<CategoryOption>();
					for (CategoryOption option :
							category.getOptions()) {
						options.add(getReportService().getOptionByName(option.getName()));
					}
					category.setOptions(options);
				}

				if (id == null) {
					if (getReportService().saveCategory(category) != null) {
						session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Catégorie enregistrée avec succès !");
					}
				}
				else {
					if (getReportService().updateCategory(category) != null)
						session.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Catégorie modifiée avec succès");
				}

				modelMap.addAttribute("mode", "View");
				return "redirect:/module/ServerReport/manageCategory.form";
			}
		}
		modelMap.addAttribute("mode", "form");
		modelMap.addAttribute("availableOptions", availableCategoryOptions);
		modelMap.addAttribute("selectedOptions", category.getOptions());
		return null;
	}

	/*private Boolean checkingCodeExistence(CategoryOption option) {
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
	}*/
}
