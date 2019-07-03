package org.openmrs.module.ServerReport.utils;

import java.util.List;

public class LocationHierarchy {
    private Integer id;
    private String name;
    private List<LocationHierarchy> children;

    public LocationHierarchy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
