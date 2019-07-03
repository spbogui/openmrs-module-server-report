package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Location;
import org.openmrs.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user_location")
public class UserLocation extends BaseOpenmrsObject {

    @Override
    public Integer getId() {
        return getUserLocationId();
    }

    @Override
    public void setId(Integer userLocationId) {
        setUserLocationId(userLocationId);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_location_id")
    private Integer userLocationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "user_locations",
            joinColumns = @JoinColumn(name = "user_location_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Collection<Location> locations = new ArrayList<Location>();

    public UserLocation() {
    }

    public Integer getUserLocationId() {
        return userLocationId;
    }

    public void setUserLocationId(Integer userLocationId) {
        this.userLocationId = userLocationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Collection<Location> getLocations() {
        return locations;
    }

    public void setLocations(Collection<Location> locations) {
        this.locations = locations;
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

    public void addUserLocation(Location location) {
        this.locations.add(location);
    }

    public Boolean hasLocation(Location location) {
        if (location != null) {
            Collection<Location> userLocations = this.getLocations();

            for (Iterator<Location> i = userLocations.iterator(); i.hasNext();) {
                if (i.next().getName().equals(location.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public UserLocation addLocation(Location location) {
        if (locations == null) {
            locations = new ArrayList<Location>();
        }
        if (!locations.contains(location) && location != null) {
            locations.add(location);
        }

        return this;
    }

}
