package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.IndicatorDataSet;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class IndicatorDataSetFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(IndicatorDataSet.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        IndicatorDataSet  indicatorDataSet = (IndicatorDataSet) o;

        if (indicatorDataSet == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "code", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "indicators", null, "Veuillez sélectionner un ou plusieurs indicateurs");

            if (indicatorDataSet.getCode() != null) {
                IndicatorDataSet existingIndicatorDataSet = Context.getService(ServerReportService.class).getIndicatorDataSetByCode(indicatorDataSet.getCode());
                if (existingIndicatorDataSet != null && (indicatorDataSet.getIndicatorDataSetId() == null ||
                        (indicatorDataSet.getIndicatorDataSetId() != null && !indicatorDataSet.getIndicatorDataSetId().equals(existingIndicatorDataSet.getIndicatorDataSetId())) )) {
                    errors.rejectValue("code", null, "Ce code existe déjà pour un autre ensemble d'indicateur");
                }
            }

            if (indicatorDataSet.getName() != null) {
                IndicatorDataSet existingIndicatorDataSet = Context.getService(ServerReportService.class).getIndicatorDataSetByName(indicatorDataSet.getName());
                if (existingIndicatorDataSet != null && (indicatorDataSet.getIndicatorDataSetId() == null ||
                        (indicatorDataSet.getIndicatorDataSetId() != null && !indicatorDataSet.getIndicatorDataSetId().equals(existingIndicatorDataSet.getIndicatorDataSetId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour un autre ensemble d'indicateur");
                }
            }
        }
    }
}
