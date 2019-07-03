package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.IndicatorRateDataSet;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class IndicatorRateDataSetFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(IndicatorRateDataSet.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        IndicatorRateDataSet indicatorRateDataSet = (IndicatorRateDataSet) o;

        if (indicatorRateDataSet == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "code", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "indicatorRates", null, "Veuillez sélectionner un ou plusieurs indicateurs");

            if (indicatorRateDataSet.getCode() != null) {
                IndicatorRateDataSet existingIndicatorRateDataSet = Context.getService(ServerReportService.class).getIndicatorRateDataSetByCode(indicatorRateDataSet.getCode());
                if (existingIndicatorRateDataSet != null && (indicatorRateDataSet.getIndicatorRateDataSetId() == null ||
                        (indicatorRateDataSet.getIndicatorRateDataSetId() != null &&
                                !indicatorRateDataSet.getIndicatorRateDataSetId().equals(existingIndicatorRateDataSet.getIndicatorRateDataSetId())) )) {
                    errors.rejectValue("code", null, "Ce code existe déjà pour un autre groupe d'indicateurs calculés");
                }
            }

            if (indicatorRateDataSet.getName() != null) {
                IndicatorRateDataSet existingIndicatorRateDataSet = Context.getService(ServerReportService.class).getIndicatorRateDataSetByName(indicatorRateDataSet.getName());
                if (existingIndicatorRateDataSet != null && (indicatorRateDataSet.getIndicatorRateDataSetId() == null ||
                        (indicatorRateDataSet.getIndicatorRateDataSetId() != null &&
                                !indicatorRateDataSet.getIndicatorRateDataSetId().equals(existingIndicatorRateDataSet.getIndicatorRateDataSetId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour un autre groupe d'indicateurs calculé");
                }
            }
        }
    }
}
