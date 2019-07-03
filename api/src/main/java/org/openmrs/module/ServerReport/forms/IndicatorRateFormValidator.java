package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.IndicatorRate;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class IndicatorRateFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(IndicatorRate.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        IndicatorRate  indicatorRate = (IndicatorRate) o;

        if (indicatorRate == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "code", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "formula", null, "Champ requis");

            if (indicatorRate.getCode() != null) {
                IndicatorRate existingIndicatorRate = Context.getService(ServerReportService.class).getIndicatorRateByCode(indicatorRate.getCode());
                if (existingIndicatorRate != null && (indicatorRate.getIndicatorRateId() == null ||
                        (indicatorRate.getIndicatorRateId() != null && !indicatorRate.getIndicatorRateId().equals(existingIndicatorRate.getIndicatorRateId())) )) {
                    errors.rejectValue("code", null, "Ce code existe déjà pour un autre indicateur");
                }
            }

            if (indicatorRate.getName() != null) {
                IndicatorRate existingIndicatorRate = Context.getService(ServerReportService.class).getIndicatorRateByName(indicatorRate.getName());
                if (existingIndicatorRate != null && (indicatorRate.getIndicatorRateId() == null ||
                        (indicatorRate.getIndicatorRateId() != null && !indicatorRate.getIndicatorRateId().equals(existingIndicatorRate.getIndicatorRateId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour un autre indicateur");
                }
            }

            if (indicatorRate.getFormula() != null) {
                String formula = indicatorRate.getFormula();
            }
        }
    }
}
