package org.openmrs.module.ServerReport.web.controller;


import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.Parameter;
import org.openmrs.module.ServerReport.ServerReport;
import org.openmrs.module.ServerReport.ServerReportRequest;
import org.openmrs.module.ServerReport.ServerReportRequestParameter;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.utils.ServerFunctions;
import org.openmrs.module.ServerReport.utils.xml.ReportXmlClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public void reportParam(ModelMap model,
                              @RequestParam(required = false, defaultValue = "0") Integer reportId,
                              @RequestParam(required = false, defaultValue = "0") Integer reportViewId,
                              @RequestParam(required = false, defaultValue = "0") Integer reportSaveId,
                              @RequestParam(required = false, defaultValue = "0") Integer delId,
                              @RequestParam(required = false, defaultValue = "View") String mode) throws IOException, JAXBException {
        if (Context.isAuthenticated()) {

            if (delId != 0) {
                ServerReportRequest serverReportRequest = getReportService().getOneServerReportRequest(delId);
                if (serverReportRequest != null)
                    getReportService().removeServerRequest(serverReportRequest);
            }

            if (reportSaveId != 0) {
                ServerReportRequest serverReportRequest = getReportService().getOneServerReportRequest(reportSaveId);
                if (serverReportRequest != null) {
                    serverReportRequest.setSaved(true);
                    getReportService().updateReportRequest(serverReportRequest);
                }
            }

            if (reportViewId != 0) {
                mode = "reportView";
                ServerReportRequest serverReportRequest = getReportService().getOneServerReportRequest(reportViewId);

                if (serverReportRequest != null) {

                    String xml = new String(serverReportRequest.getContent(), "UTF-8");

                    JAXBContext jaxbContext = JAXBContext.newInstance(ReportXmlClass.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                    StringReader reader = new StringReader(xml);

                    ReportXmlClass reportXmlClass = (ReportXmlClass) unmarshaller.unmarshal(reader);

//                ReportDataView reportDataView = new ReportDataView(serverReportRequest.getReport().getName());
//                List<DataSetView> dataSetViews = new ArrayList<DataSetView>();
//
//                for (DataSetXmlClass dataSetXmlClass : reportXmlClass.getDataSetXmlClasses()) {
//
//                    IndicatorDataSet indicatorDataSet = getReportService().getIndicatorDataSetByCode(dataSetXmlClass.getCode());
//                    DataSetView dataSetView = new DataSetView(indicatorDataSet.getName());
//                    List<IndicatorView> indicatorViews = new ArrayList<IndicatorView>();
//
//                    for (IndicatorXmlClass indicatorXmlClass : dataSetXmlClass.getIndicatorXmlClasses()) {
//                        Indicator indicator = getReportService().getIndicatorByCode(indicatorXmlClass.getiCode());
//                        IndicatorView indicatorView = new IndicatorView();
//                    }
//                }
                    System.out.println(reportXmlClass);
                    model.addAttribute("reportValues", reportXmlClass);
                    model.addAttribute("request", serverReportRequest);
                    model.addAttribute("parameters", serverReportRequest.getParameters());
                }
            }

            List<ServerReportRequest> reportRequests = new ArrayList<ServerReportRequest>();

            if (reportId != 0) {
                mode = "run";
                ServerReport serverReport = getReportService().getServerReport(reportId);
                model.addAttribute("report", serverReport);
                model.addAttribute("json", ServerFunctions.getJsonLocationHierarchy());
                model.addAttribute("reportRequests", getReportService().getServerReportRequest(reportId, Context.getAuthenticatedUser()));
                model.addAttribute("parameters", serverReport.getParameters());
            } else {
                model.addAttribute("reports", getReportService().getAllServerReports(false));
                model.addAttribute("reportRequests", getReportService().getServerReportRequest(Context.getAuthenticatedUser()));
            }
        }
        model.addAttribute("mode", mode);
    }

    @RequestMapping(value = "/module/ServerReport/reportRun.form", method = RequestMethod.POST)
    public String runReport(HttpServletRequest request,
                            ModelMap modelMap,
                            @RequestParam(required = false, defaultValue = "0") Integer requestId,
                            @RequestParam(required = false, defaultValue = "") Integer reportId
                            ) throws ParseException {

        if (!Context.isAuthenticated()){
            return null;
        }
        Map<String, Object> dataParam = new HashMap<String, Object>();
        ServerReport serverReport = getReportService().getServerReport(reportId);
        Set<ServerReportRequestParameter> requestParameters = new HashSet<ServerReportRequestParameter>();

        ServerReportRequest reportRequest = new ServerReportRequest();

        if (requestId != 0) {
            reportRequest = getReportService().getOneServerReportRequest(requestId);
        }
        for (Parameter parameter : serverReport.getParameters()) {

            if (parameter.getParameterDataType().equals("java.util.Date")){
                dataParam.put(parameter.getName(), new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter(parameter.getName())));
            } else if (parameter.getParameterDataType().equals("java.util.Integer")) {
                dataParam.put(parameter.getName(), Integer.parseInt(request.getParameter(parameter.getName())));
            }

            requestParameters.add(new ServerReportRequestParameter(parameter, request.getParameter(parameter.getName())));
        }

        dataParam.put("locationId", request.getParameter("locationId"));

        reportRequest.setParameters(requestParameters);
        reportRequest.setRequestDate(new Date());
        reportRequest.setReport(serverReport);
        reportRequest.setRequestLocation(Context.getLocationService().getLocation(Integer.parseInt(request.getParameter("locationId"))));
        reportRequest.setUserLocation(getReportService().getUserLocationByUser(Context.getAuthenticatedUser()));
        reportRequest.setRequestPeriodStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("startDate")));
        reportRequest.setRequestPeriodEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("endDate")));

        int startYear = reportRequest.getRequestPeriodStartDate() != null ? reportRequest.getRequestPeriodStartDate().getYear() + 1900 : 0;
        int endYear = reportRequest.getRequestPeriodEndDate() != null ? reportRequest.getRequestPeriodEndDate().getYear() + 1900 : 0;

        String paramName = "";
        if (reportRequest.getRequestPeriodStartDate() != null && reportRequest.getRequestPeriodEndDate() != null) {
            String monthStart = ServerFunctions.getMonthForInt(reportRequest.getRequestPeriodStartDate().getMonth())
                    + " " + (startYear == 0 ? "" : startYear);
            String monthEnd = ServerFunctions.getMonthForInt(reportRequest.getRequestPeriodEndDate().getMonth())
                    + " " + (endYear == 0 ? "" : endYear);
            if (monthStart.equals(monthEnd))
                paramName = " (" + monthStart + ")";
            else {
                paramName = " ("+ monthStart +" - " + monthEnd + ")";
            }
        } else if (reportRequest.getRequestPeriodStartDate() != null && reportRequest.getRequestPeriodEndDate() == null) {
            String monthStart = ServerFunctions.getMonthForInt(reportRequest.getRequestPeriodStartDate().getMonth())
                    + " " + (startYear == 0 ? "" : startYear);
            paramName = " (" + monthStart + ")";
        } else if (reportRequest.getRequestPeriodStartDate() == null && reportRequest.getRequestPeriodEndDate() != null) {
            String monthEnd = ServerFunctions.getMonthForInt(reportRequest.getRequestPeriodEndDate().getMonth())
                    + " " + (endYear == 0 ? "" : endYear);
            paramName = " (" + monthEnd + ")";
        }

        paramName = paramName.substring(0, 1).toUpperCase() + paramName.substring(1);
        reportRequest.setName(getReportService().getServerReport(reportId).getName() + paramName);

        reportRequest.setContent(getReportService().generateRequest(serverReport, dataParam));

        if (reportRequest.getRequestId() == null) {
            getReportService().saveReportRequest(reportRequest);
            for (Parameter parameter : serverReport.getParameters()) {
                ServerReportRequestParameter requestParameter = new ServerReportRequestParameter(parameter, request.getParameter(parameter.getName()));
                requestParameter.setRequest(reportRequest);
                getReportService().saveServerRequestParameter(requestParameter);
            }
        } else {
            getReportService().updateReportRequest(reportRequest);
            for (ServerReportRequestParameter requestParameter : reportRequest.getParameters()) {
                requestParameter.setValue(request.getParameter(requestParameter.getParameter().getName()));
                getReportService().updateServerRequestParameter(requestParameter);
            }
        }

        System.out.println(dataParam);

        return "redirect:/module/ServerReport/reportRun.form?reportId=" + serverReport.getReportId();
    }
}
