package org.openmrs.module.ServerReport.utils.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataSetValue {
    @XmlAttribute(name = "dsCode")
    private String code;
    @XmlElement(name = "indicator")
    private List<IndicatorValue> indicatorValues = new ArrayList<IndicatorValue>();

    public DataSetValue() {
    }

    public DataSetValue(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<IndicatorValue> getIndicatorValues() {
        return indicatorValues;
    }

    public void setIndicatorValues(List<IndicatorValue> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }
}
