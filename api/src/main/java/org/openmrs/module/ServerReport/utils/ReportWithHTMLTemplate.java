package org.openmrs.module.ServerReport.utils;

import org.openmrs.module.ServerReport.utils.xml.DataSetXmlClass;
import org.openmrs.module.ServerReport.utils.xml.IndicatorXmlClass;
import org.openmrs.module.ServerReport.utils.xml.ReportXmlClass;
import org.openmrs.module.ServerReport.utils.xml.ValueXmlClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ReportWithHTMLTemplate {
    private String reportName;
    private Map<String, String> parameters;

    private String readFileFromHtml(String file) throws IOException {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            try {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append((System.getProperty("line.separator")));
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String transformHmtl(String file, ReportXmlClass reportXmlClass) throws IOException {
        String template = readFileFromHtml(file);
        String replacedTemplate = template.replace("[reportName]", reportXmlClass.getName());

        for (DataSetXmlClass dataSetXmlClass : reportXmlClass.getDataSetXmlClasses()) {
            for (IndicatorXmlClass indicatorXmlClass : dataSetXmlClass.getIndicatorXmlClasses()) {
                for (ValueXmlClass valueXmlClass : indicatorXmlClass.getValueXmlClasses()) {
                    String code = dataSetXmlClass.getCode() + "." + indicatorXmlClass.getiCode() + "." + valueXmlClass.getCoCode();
                    replacedTemplate = replacedTemplate.replace("[" + code + "]", valueXmlClass.getValue().toString());
                }
            }
        }
        return replacedTemplate;
    }
}
