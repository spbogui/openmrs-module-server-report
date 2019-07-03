package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.CategoryOption;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CategoryOptionFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CategoryOption.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CategoryOption categoryOption = (CategoryOption) o;

        if (categoryOption == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "code", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "sqlQuery", null, "Champ requis");

            if (categoryOption.getCode() != null) {
                CategoryOption existingOption = Context.getService(ServerReportService.class).getOptionByCode(categoryOption.getCode());
                if (existingOption != null && (categoryOption.getOptionId() == null ||
                        (categoryOption.getOptionId() != null && !categoryOption.getOptionId().equals(existingOption.getOptionId())) )) {
                    errors.rejectValue("code", null, "Ce code existe déjà pour une autre option de catégorie");
                }
            }

            if (categoryOption.getName() != null) {
                CategoryOption existingOption = Context.getService(ServerReportService.class).getOptionByName(categoryOption.getName());
                if (existingOption != null && (categoryOption.getOptionId() == null ||
                        (categoryOption.getOptionId() != null && !categoryOption.getOptionId().equals(existingOption.getOptionId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour une autre option de catégorie");
                }
            }
        }
    }
}
