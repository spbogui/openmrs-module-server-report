package org.openmrs.module.ServerReport.utils.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ReportXml implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        ReportXmlClass reportXmlClass = (ReportXmlClass) o;
        if (!reportXmlClass.getDataSetXmlClasses().isEmpty()) {
            for (DataSetXmlClass dataSetXmlClass : reportXmlClass.getDataSetXmlClasses()) {
                writer.startNode("dataSet");
                writer.addAttribute("dsCode", nullSafeString(dataSetXmlClass.getCode()));
                writer.addAttribute("name", nullSafeString(dataSetXmlClass.getName()));

                if (!dataSetXmlClass.getIndicatorXmlClasses().isEmpty()) {
                    for (IndicatorXmlClass indicatorXmlClass : dataSetXmlClass.getIndicatorXmlClasses()) {
                        writer.startNode("indicator");
                        writer.addAttribute("iCode", indicatorXmlClass.getiCode());
                        writer.addAttribute("name", indicatorXmlClass.getName());

                        if (!indicatorXmlClass.getValueXmlClasses().isEmpty()) {
                            for (ValueXmlClass valueXmlClass : indicatorXmlClass.getValueXmlClasses()) {
                                addOptionalElementWithIdAttribute(
                                        writer,
                                        valueXmlClass.getCoCode(),
                                        String.valueOf(valueXmlClass.getCoName()),
                                        String.valueOf(valueXmlClass.getValue())
                                );
                            }
                        }
                        writer.endNode();
                    }
                }
                writer.endNode();
            }
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(ReportXmlClass.class);
    }

    private static String nullSafeString(Object o) {
        if (o != null)
            return o.toString();
        return "";
    }

    private void addOptionalElement(HierarchicalStreamWriter writer, String nodeName, String value) {
        if (value != null) {
            writer.startNode(nodeName);
            writer.setValue(value);
            writer.endNode();
        }
    }

    private void addOptionalElementWithIdAttribute(
            HierarchicalStreamWriter writer, String coCode, String coName, String value) {
            writer.startNode("value");
            writer.addAttribute("coCode", coCode);
            writer.addAttribute("coName", coName);
            writer.addAttribute("value", value);
            writer.endNode();
    }
}
