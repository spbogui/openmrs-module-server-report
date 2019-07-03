package org.openmrs.module.ServerReport.web.controller;


import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.ServerListing;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.forms.ListingRunForm;
import org.openmrs.module.ServerReport.utils.ServerFunctions;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ServerReportRunListingController {

    private ServerReportService getReportService() {
        return Context.getService(ServerReportService.class);
    }

    @RequestMapping(value = "/module/ServerReport/listing.form", method = RequestMethod.GET)
    public void executeReport(HttpServletRequest request,
                              ModelMap model,
                              @RequestParam(required = false, defaultValue = "0") Integer listingId,
                              @RequestParam(required = false) Date startDate,
                              @RequestParam(required = false) Date endDate,
                              @RequestParam(required = false, defaultValue = "0") Integer locationId,
                              @RequestParam(required = false, defaultValue = "list") String mode) throws IOException {
        if (Context.isAuthenticated()) {

            HttpSession session = request.getSession();

            if (listingId != 0) {
                ListingRunForm runForm = new ListingRunForm();

                ServerListing serverListing = getReportService().getOneServerListing(listingId);

                runForm.setListingId(listingId);
                runForm.setStartDate(startDate);
                runForm.setEndDate(endDate);
                runForm.setLocationId(locationId);

                if (serverListing.getSqlQuery().contains("startDate")) {
                    model.addAttribute("hasStartDate", true);
                }
                if (serverListing.getSqlQuery().contains("endDate")) {
                    model.addAttribute("hasEndDate", true);
                }
                if (mode.equals("run")) {

                    if (locationId == null) {
                        session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Veuillez sélectionner un établissement/région/district SVP !");
                        mode = "param";
                    }
                    if (serverListing.getSqlQuery().contains(":startDate") && startDate == null) {
                        session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Veuillez saisir la date de début de période SVP !");
                        mode = "param";
                    }
                    if (serverListing.getSqlQuery().contains(":endDate") && endDate == null) {
                        session.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "Veuillez saisir la date de fin de période SVP !");
                        mode = "param";
                    }
                    if (mode.equals("run")) {
                        List<Map<String, Object>> result = ServerFunctions.getQueryMap(serverListing, startDate, endDate, locationId);

                        if (result != null && result.size() > 0) {
                            List<String> columnNames = new ArrayList<String>();
                            Map<String, Object> firstData = result.get(0);
                            for (Map.Entry<String, Object> entry : firstData.entrySet()) {
                                columnNames.add(ServerFunctions.splitCamelCase(entry.getKey()));
                            }

                            model.addAttribute("columnNames", columnNames);
                            model.addAttribute("listingResult", result);
                        }
                        model.addAttribute("listing", serverListing);
                        model.addAttribute("startDate", startDate);
                        model.addAttribute("endDate", endDate);
                        model.addAttribute("location", Context.getLocationService().getLocation(locationId).getName());
                    }
                }

                if (mode.equals("param")) {

                    model.addAttribute("json", ServerFunctions.getJsonLocationHierarchy());
                    model.addAttribute("listing", serverListing);
                    model.addAttribute("runForm", runForm);
                }
            }

            if (mode.equals("list")) {
                model.addAttribute("listings", getReportService().getAllServerListings());
            }
        }

        model.addAttribute("mode", mode);
    }

}
