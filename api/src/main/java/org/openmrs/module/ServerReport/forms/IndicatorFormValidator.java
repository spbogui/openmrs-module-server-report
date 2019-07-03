package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.Indicator;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class IndicatorFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Indicator.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Indicator indicator = (Indicator) o;

        if (indicator == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "code", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "sqlQuery", null, "Champ requis");

            if (indicator.getCode() != null) {
                Indicator existingIndicator = Context.getService(ServerReportService.class).getIndicatorByCode(indicator.getCode());
                if (existingIndicator != null && (indicator.getIndicatorId() == null ||
                        (indicator.getIndicatorId() != null && !indicator.getIndicatorId().equals(existingIndicator.getIndicatorId())) )) {
                    errors.rejectValue("code", null, "Ce code existe déjà pour un autre indicateur");
                }
            }

            if (indicator.getName() != null) {
                Indicator existingIndicator = Context.getService(ServerReportService.class).getIndicatorByName(indicator.getName());
                if (existingIndicator != null && (indicator.getIndicatorId() == null ||
                        (indicator.getIndicatorId() != null && !indicator.getIndicatorId().equals(existingIndicator.getIndicatorId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour un autre indicateur");
                }
            }

            if (indicator.getSqlQuery() != null || !indicator.getSqlQuery().trim().isEmpty()) {
                if (!indicator.getSqlQuery().contains(":locationId")) {
                    errors.rejectValue("sqlQuery", null, "Votre requête n'a pas de paramètre :locationId pour le choix de l'établissement");
                } else
                if (!indicator.getSqlQuery().contains(":endDate") || !indicator.getSqlQuery().contains(":startDate")) {
                    errors.rejectValue("sqlQuery", null, "Votre requête n'a pas de paramètre de date :startDate et/ou :endDate");
                } else
                if (indicator.getSqlQuery().contains("insert ") || indicator.getSqlQuery().contains("delete ")) {
                    errors.rejectValue("sqlQuery", null, "Votre requête n'est pas valide");
                } else {

                    Connection connection = null;
                    Statement stmt = null;

                    try {

                        Properties props = new Properties();
                        props.setProperty("driver.class", "com.mysql.jdbc.Driver");
                        props.setProperty("driver.url", Context.getRuntimeProperties().getProperty("connection.url"));
                        props.setProperty("user", Context.getRuntimeProperties().getProperty("connection.username"));
                        props.setProperty("password", Context.getRuntimeProperties().getProperty("connection.password"));

                        connection = DriverManager.getConnection(props.getProperty("driver.url"), props);

                        String sql = indicator.getSqlQuery();
                        sql = sql.replace(":locationId", "1");

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                        if (sql.contains(":startDate")) {
                            sql = sql.replace(":startDate", "'" + dateFormatter.format(new Date()) + "'");
                        }
                        sql = sql.replace(":endDate", "'" +  dateFormatter.format(new Date()) + "'");

                        stmt = connection.createStatement();
                        stmt.executeQuery(sql);

                    } catch (Exception e) {
                        errors.rejectValue("sqlQuery", null, "Erreur SQL : " + e.getMessage());
                    } finally {
                        try {
                            if (stmt != null)
                                stmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (connection != null) {
                                connection.close();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
