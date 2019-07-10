package org.openmrs.module.ServerReport.utils;

import java.util.List;

public class IndicatorView {
    private String name;
    private List<DataValue> dataValues;

    public IndicatorView() {
    }

    public IndicatorView(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataValue> getDataValues() {
        return dataValues;
    }

    public void setDataValues(List<DataValue> dataValues) {
        this.dataValues = dataValues;
    }
}
