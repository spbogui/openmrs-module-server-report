package org.openmrs.module.ServerReport.utils.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "report")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportValue {
    @XmlElement(name = "dataSet")
    private List<DataSetValue> dataSetValues = new ArrayList<DataSetValue>();

    public ReportValue() {
    }

    public List<DataSetValue> getDataSetValues() {
        return dataSetValues;
    }

    public void setDataSetValues(List<DataSetValue> dataSetValues) {
        this.dataSetValues = dataSetValues;
    }
}
