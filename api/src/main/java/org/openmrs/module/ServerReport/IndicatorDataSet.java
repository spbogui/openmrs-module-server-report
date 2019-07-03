package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "server_indicator_dataset")
public class IndicatorDataSet extends BaseOpenmrsObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indicator_dataset_id")
    private Integer indicatorDataSetId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @ManyToMany
    @JoinTable(name = "server_indicator_dataset_indicator",
            joinColumns = @JoinColumn(name = "indicator_dataset_id"),
            inverseJoinColumns = @JoinColumn(name = "indicator_id"))
    private Set<Indicator> indicators;

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer integer) {

    }

    public IndicatorDataSet(String name) {
        this.name = name;
    }

    public IndicatorDataSet() {

    }

    public Integer getIndicatorDataSetId() {
        return indicatorDataSetId;
    }

    public void setIndicatorDataSetId(Integer indicatorDataSetId) {
        this.indicatorDataSetId = indicatorDataSetId;
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

    public Set<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<Indicator> indicators) {
        this.indicators = indicators;
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
