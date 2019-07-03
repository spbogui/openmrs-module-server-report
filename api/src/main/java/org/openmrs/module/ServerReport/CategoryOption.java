package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;

@Entity
@Table(name = "server_category_option")
public class CategoryOption extends BaseOpenmrsObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer optionId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "sql_query", nullable = false, unique = true, columnDefinition = "TEXT")
    private String sqlQuery;

    @Override
    public Integer getId() {
        return getOptionId();
    }

    @Override
    public void setId(Integer optionId) {
        setOptionId(optionId);
    }

    public CategoryOption() {
    }

    public CategoryOption(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
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

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
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
