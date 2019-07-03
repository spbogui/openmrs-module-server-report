package org.openmrs.module.ServerReport.extension.html;

import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.api.constants.ServerReportPrivilegeConstants;
import org.openmrs.module.web.extension.LinkExt;

public class ServerReportGutterLink extends LinkExt {
    @Override
    public String getLabel() {
        return Context.getMessageSourceService().getMessage("ServerReport.gutterTitle");
    }

    @Override
    public String getUrl() {
        return "module/ServerReport/dashboard.form";
    }

    @Override
    public String getRequiredPrivilege() {
        return ServerReportPrivilegeConstants.VIEW_SERVER_REPORT;
    }
}
