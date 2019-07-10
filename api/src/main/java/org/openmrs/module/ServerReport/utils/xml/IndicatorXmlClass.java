package org.openmrs.module.ServerReport.utils.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class IndicatorXmlClass {
    @XmlAttribute(name = "iCode")
    private String iCode;
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "value")
    private List<ValueXmlClass> valueXmlClasses;

    public IndicatorXmlClass() {
        this.valueXmlClasses = new ArrayList<ValueXmlClass>();
    }

    public IndicatorXmlClass(String iCode) {
        this.iCode = iCode;
    }

    public String getiCode() {
        return iCode;
    }

    public void setiCode(String iCode) {
        this.iCode = iCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ValueXmlClass> getValueXmlClasses() {
        return valueXmlClasses;
    }

    public void setValueXmlClasses(List<ValueXmlClass> valueXmlClasses) {
        this.valueXmlClasses = valueXmlClasses;
    }
}
