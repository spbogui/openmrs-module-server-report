package org.openmrs.module.ServerReport.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ServerReportDataQualityController {
    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(value = "/module/ServerReport/dataQuality.form", method = RequestMethod.GET)
    public void dashboard(ModelMap model) {
        model.addAttribute("user", Context.getAuthenticatedUser());
    }
}
