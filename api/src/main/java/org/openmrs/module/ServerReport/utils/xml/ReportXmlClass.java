package org.openmrs.module.ServerReport.utils.xml;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "report")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportXmlClass {
    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "dataSet")
    private List<DataSetXmlClass> dataSetXmlClasses = new ArrayList<DataSetXmlClass>();

    public ReportXmlClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataSetXmlClass> getDataSetXmlClasses() {
        return dataSetXmlClasses;
    }

    public void setDataSetXmlClasses(List<DataSetXmlClass> dataSetXmlClasses) {
        this.dataSetXmlClasses = dataSetXmlClasses;
    }
}
