package org.openmrs.module.ServerReport.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDataView {
    private String locationName;
    private Map<String, String> parameters;
    private List<DataSetView> dataSetViews;

    public ReportDataView() {
        this.parameters = new HashMap<String, String>();
        this.dataSetViews = new ArrayList<DataSetView>();
    }

    public ReportDataView(String locationName) {
        this.locationName = locationName;
        this.parameters = new HashMap<String, String>();
        this.dataSetViews = new ArrayList<DataSetView>();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<DataSetView> getDataSetViews() {
        return dataSetViews;
    }

    public void setDataSetViews(List<DataSetView> dataSetViews) {
        this.dataSetViews = dataSetViews;
    }
}
