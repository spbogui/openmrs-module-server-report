package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.ServerReport;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ReportFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ServerReport.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ServerReport  report = (ServerReport) o;

        if (report == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "roles", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "parameters", null, "Veuillez sélectionner un ou plusieurs paramètres");

            if (report.getName() != null) {
                ServerReport existingReport = Context.getService(ServerReportService.class).getServerReportByName(report.getName());
                if (existingReport != null ) {
                    if (!existingReport.getUuid().equals(report.getUuid())) {
                        errors.rejectValue("name", null, "Ce Nom existe déjà pour un autre rapport");
                    }
                }
            }

            if (report.getRoles() != null) {
                StringBuilder notExistingRoles = new StringBuilder();
                for (String role : report.getRoles().split(",")) {
                    if (Context.getUserService().getRole(role.trim()) == null) {
                        notExistingRoles.append(role).append("|");
                    }
                }
                if (notExistingRoles.length() > 0) {
                    errors.rejectValue("roles", null, "Les roles suivants n'existent pas : [" + notExistingRoles.toString().substring(0, notExistingRoles.length() - 1) + "]");
                }
            }

        }
    }
}
