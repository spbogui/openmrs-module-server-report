package org.openmrs.module.ServerReport.web.controller;


import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.ServerReport;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.utils.ServerFunctions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ServerReportRunController {

    private ServerReportService getReportService() {
        return Context.getService(ServerReportService.class);
    }

    @RequestMapping(value = "/module/ServerReport/reportDashboard.form", method = RequestMethod.GET)
    public void reportDashboard(ModelMap model) {
        model.addAttribute("user", Context.getAuthenticatedUser());
    }

    @RequestMapping(value = "/module/ServerReport/reportRun.form", method = RequestMethod.GET)
    public void executeReport(ModelMap model,
                              @RequestParam(required = false, defaultValue = "0") Integer reportId,
                              @RequestParam(required = false, defaultValue = "View") String mode) throws IOException {
        if (Context.isAuthenticated()) {

            if (reportId != 0) {
                mode = "run";
                ServerReport serverReport = getReportService().getServerReport(reportId);
                model.addAttribute("report", serverReport);
                model.addAttribute("json", ServerFunctions.getJsonLocationHierarchy());
            } else {
                model.addAttribute("reports", getReportService().getAllServerReports(false));
            }
        }
        model.addAttribute("mode", mode);
    }
}
