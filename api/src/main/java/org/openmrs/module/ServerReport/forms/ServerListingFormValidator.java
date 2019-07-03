package org.openmrs.module.ServerReport.forms;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.ServerListing;
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

public class ServerListingFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ServerListing.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ServerListing serverListing = (ServerListing) o;

        if (serverListing == null) {
            errors.reject("ServerReport", "general.error");
        } else {
            ValidationUtils.rejectIfEmpty(errors, "name", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "roles", null, "Champ requis");
            ValidationUtils.rejectIfEmpty(errors, "sqlQuery", null, "Champ requis");

            if (serverListing.getName() != null) {
                ServerListing existingIndicator = Context.getService(ServerReportService.class).getServerListingByName(serverListing.getName());
                if (existingIndicator != null && (serverListing.getListingId() == null ||
                        (serverListing.getListingId() != null && !serverListing.getListingId().equals(existingIndicator.getListingId())) )) {
                    errors.rejectValue("name", null, "Ce Nom existe déjà pour un autre Listing");
                }
            }

            if (serverListing.getRoles() != null) {
                StringBuilder notExistingRoles = new StringBuilder();
                for (String role : serverListing.getRoles().split(",")) {
                    if (Context.getUserService().getRole(role.trim()) == null) {
                        notExistingRoles.append(role).append("|");
                    }
                }
                if (notExistingRoles.length() > 0) {
                    errors.rejectValue("roles", null, "Les roles suivants n'existent pas : [" + notExistingRoles.toString().substring(0, notExistingRoles.length() - 1) + "]");
                }
            }

            if (serverListing.getSqlQuery() != null || !serverListing.getSqlQuery().trim().isEmpty()) {
                if (!serverListing.getSqlQuery().contains(":locationId")) {
                    errors.rejectValue("sqlQuery", null, "Votre requête n'a pas de paramètre :locationId pour le choix de l'établissement");
                } else
                if (!serverListing.getSqlQuery().contains(":endDate") && !serverListing.getSqlQuery().contains(":startDate")) {
                    errors.rejectValue("sqlQuery", null, "Votre requête n'a pas de paramètre de date :startDate et/ou :endDate");
                } else
                if (serverListing.getSqlQuery().toUpperCase().contains("insert ") || serverListing.getSqlQuery().toUpperCase().contains("delete ")) {
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

                        String sql = serverListing.getSqlQuery();
                        sql = sql.replace(":locationId", "0");

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                        if (sql.contains(":startDate")) {
                            sql = sql.replace(":startDate", "'" + dateFormatter.format(new Date()) + "'");
                        }
                        sql = sql.replace(":endDate", "'" +  dateFormatter.format(new Date()) + "'");
                        System.out.println(sql);
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
