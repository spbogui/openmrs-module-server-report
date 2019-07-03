package org.openmrs.module.ServerReport.utils.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class IndicatorValue {
    @XmlAttribute(name = "iCode")
    private String iCode;
    @XmlAttribute(name = "coCode")
    private String coCode;
    @XmlElement(name = "value")
    private Integer value;

    public IndicatorValue() {
    }

    public IndicatorValue(String iCode, String coCode, Integer value) {
        this.iCode = iCode;
        this.coCode = coCode;
        this.value = value;
    }

    public String getiCode() {
        return iCode;
    }

    public void setiCode(String iCode) {
        this.iCode = iCode;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
