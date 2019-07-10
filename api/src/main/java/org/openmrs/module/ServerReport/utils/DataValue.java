package org.openmrs.module.ServerReport.utils;

public class DataValue {
    private String code;
    private Integer value;

    public DataValue() {
    }

    public DataValue(String code) {
        this.code = code;
    }

    public DataValue(String code, Integer value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
