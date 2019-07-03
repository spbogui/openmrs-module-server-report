package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.Category;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class CategoryFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Category.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Category  category = (Category) o;

        if (category == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "code", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "options", null, "Champ requis");

            if (category.getCode() != null) {
                Category existingCategory = Context.getService(ServerReportService.class).getCategoryByCode(category.getCode());
                if (existingCategory != null && (category.getCategoryId() == null ||
                        (category.getCategoryId() != null && !category.getCategoryId().equals(existingCategory.getCategoryId())) )) {
                    errors.rejectValue("code", null, "Ce code existe déjà pour une autre catégorie");
                }
            }

            if (category.getName() != null) {
                Category existingCategory = Context.getService(ServerReportService.class).getCategoryByName(category.getName());
                if (existingCategory != null && (category.getCategoryId() == null ||
                        (category.getCategoryId() != null && !category.getCategoryId().equals(existingCategory.getCategoryId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour une autre catégorie");
                }
            }
        }
    }
}
