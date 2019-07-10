package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;

@Entity
@Table(name = "server_parameter")
public class Parameter extends BaseOpenmrsObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameter_id")
    private Integer parameterId;

    @Column(name = "label", nullable = false, unique = true)
    private String label;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "parameter_data_type", nullable = false, length = 200)
    private String parameterDataType;

    @Override
    public Integer getId() {
        return getParameterId();
    }

    @Override
    public void setId(Integer parameterId) {
        setParameterId(parameterId);
    }

    public Parameter() {
    }

    public Parameter(String name) {
        this.name = name;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameterDataType() {
        return parameterDataType;
    }

    public void setParameterDataType(String parameterDataType) {
        this.parameterDataType = parameterDataType;
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @Column(name = "uuid", length = 38, unique = true, nullable = false)
    @Override
    public String getUuid() {
        return super.getUuid();
    }

    @Override
    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }
}
