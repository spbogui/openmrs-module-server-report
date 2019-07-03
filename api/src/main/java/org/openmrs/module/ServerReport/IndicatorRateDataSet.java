package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "server_indicator_rate_dataset")
public class IndicatorRateDataSet extends BaseOpenmrsObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indicator_rate_dataset_id")
    private Integer indicatorRateDataSetId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;

    @ManyToMany
    @JoinTable(name = "server_indicator_rate_dataset_indicator_rate",
            joinColumns = @JoinColumn(name = "indicator_rate_dataset_id"),
            inverseJoinColumns = @JoinColumn(name = "indicator_rate_id"))
    private Set<IndicatorRate> indicatorRates;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer integer) {

    }

    public IndicatorRateDataSet() {
    }

    public IndicatorRateDataSet(String name) {
        this.name = name;
    }

    public Integer getIndicatorRateDataSetId() {
        return indicatorRateDataSetId;
    }

    public void setIndicatorRateDataSetId(Integer indicatorRateDataSetId) {
        this.indicatorRateDataSetId = indicatorRateDataSetId;
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

    public Set<IndicatorRate> getIndicatorRates() {
        return indicatorRates;
    }

    public void setIndicatorRates(Set<IndicatorRate> indicatorRates) {
        this.indicatorRates = indicatorRates;
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
