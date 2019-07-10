package org.openmrs.module.ServerReport.utils.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValueXmlClass {
    @XmlAttribute(name = "coCode")
    private String coCode;
    @XmlAttribute(name = "coName")
    private String coName;
    @XmlAttribute(name = "value")
    private String value;

    public ValueXmlClass() {
    }

    public ValueXmlClass(String coCode, String value) {
        this.coCode = coCode;
        this.value = value;
    }

    public String getCoCode() {
        return coCode;
    }

    public void setCoCode(String coCode) {
        this.coCode = coCode;
    }

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
