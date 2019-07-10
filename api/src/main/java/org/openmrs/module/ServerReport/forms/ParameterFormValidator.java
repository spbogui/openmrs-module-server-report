package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.Parameter;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ParameterFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Parameter.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Parameter  parameter = (Parameter) o;

        if (parameter == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "parameterDataType", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "label", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");

            if (parameter.getName() != null) {
                Parameter existingCategory = Context.getService(ServerReportService.class).getParameterByName(parameter.getName());
                if (existingCategory != null && (parameter.getParameterId() == null ||
                        (parameter.getParameterId() != null && !parameter.getParameterId().equals(existingCategory.getParameterId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour un autre paramètre");
                }
            }
        }
    }
}
