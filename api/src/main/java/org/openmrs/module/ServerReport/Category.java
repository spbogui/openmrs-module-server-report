package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "server_category")
public class Category extends BaseOpenmrsObject {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "server_category_category_option",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "category_option_id"))
    private Set<CategoryOption> options = new HashSet<CategoryOption>();

    @Override
    public Integer getId() {
        return getCategoryId();
    }

    @Override
    public void setId(Integer categoryId) {
        setCategoryId(categoryId);
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CategoryOption> getOptions() {
        return options;
    }

    public void setOptions(Set<CategoryOption> options) {
        this.options = options;
    }

    public Category addCategoryOption(CategoryOption option) {
        options.add(option);
        return this;
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
