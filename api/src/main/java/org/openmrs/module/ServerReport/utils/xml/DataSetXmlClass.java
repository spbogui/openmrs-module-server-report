package org.openmrs.module.ServerReport.utils.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class DataSetXmlClass {
    @XmlAttribute(name = "dsCode")
    private String code;
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "indicator")
    private List<IndicatorXmlClass> indicatorXmlClasses = new ArrayList<IndicatorXmlClass>();

    private List<String> columnList = new ArrayList<String>();

    public DataSetXmlClass() {
    }

    public DataSetXmlClass(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IndicatorXmlClass> getIndicatorXmlClasses() {
        return indicatorXmlClasses;
    }

    public void setIndicatorXmlClasses(List<IndicatorXmlClass> indicatorXmlClasses) {
        this.indicatorXmlClasses = indicatorXmlClasses;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<String> columnList) {
        this.columnList = columnList;
    }
}
