package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;

@Entity
@Table(name = "server_indicator_rate")
public class IndicatorRate extends BaseOpenmrsObject {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indicator_rate_id")
    private Integer indicatorRateId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @JoinColumn(name = "formula",nullable = false)
    private String formula;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "description")
    private String description;

    @Override
    public Integer getId() {
        return getIndicatorRateId();
    }

    @Override
    public void setId(Integer indicatorRateId) {
        setIndicatorRateId(indicatorRateId);
    }

    public IndicatorRate() {
    }

    public IndicatorRate(String name) {
        this.name = name;
    }

    public Integer getIndicatorRateId() {
        return indicatorRateId;
    }

    public void setIndicatorRateId(Integer indicatorRateId) {
        this.indicatorRateId = indicatorRateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
