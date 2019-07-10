package org.openmrs.module.ServerReport.utils;

import java.util.ArrayList;
import java.util.List;

public class DataSetView {
    private String name;
    private List<IndicatorView> indicatorViews;
    private List<String> columns;

    public DataSetView() {
        this.indicatorViews = new ArrayList<IndicatorView>();
        this.columns = new ArrayList<String>();
    }

    public DataSetView(String name) {
        this.name = name;
        this.indicatorViews = new ArrayList<IndicatorView>();
        this.columns = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IndicatorView> getIndicatorViews() {
        return indicatorViews;
    }

    public void setIndicatorViews(List<IndicatorView> indicatorViews) {
        this.indicatorViews = indicatorViews;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
