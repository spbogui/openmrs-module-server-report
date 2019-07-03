package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class ReportAbstract extends BaseOpenmrsData implements Serializable {

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer integer) {
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

    @Basic
    @Access(AccessType.PROPERTY)
    @ManyToOne
    @JoinColumn(name = "creator", nullable = false)
    @Override
    public User getCreator() {
        return super.getCreator();
    }

    @Override
    public void setCreator(User creator) {
        super.setCreator(creator);
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @Column(name = "date_created", nullable = false)
    @Override
    public Date getDateCreated() {
        return super.getDateCreated();
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        super.setDateCreated(dateCreated);
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @ManyToOne
    @JoinColumn(name = "changed_by")
    @Override
    public User getChangedBy() {
        return super.getChangedBy();
    }

    @Override
    public void setChangedBy(User changedBy) {
        super.setChangedBy(changedBy);
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @Column(name = "date_changed")
    @Override
    public Date getDateChanged() {
        return super.getDateChanged();
    }

    @Override
    public void setDateChanged(Date dateChanged) {
        super.setDateChanged(dateChanged);
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @Column(name = "voided", nullable = false)
    @Override
    public Boolean getVoided() {
        return super.getVoided();
    }

    @Override
    public void setVoided(Boolean voided) {
        super.setVoided(voided);
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @Column(name = "date_voided")
    @Override
    public Date getDateVoided() {
        return super.getDateVoided();
    }

    @Override
    public void setDateVoided(Date dateVoided) {
        super.setDateVoided(dateVoided);
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @ManyToOne
    @JoinColumn(name = "voided_by")
    @Override
    public User getVoidedBy() {
        return super.getVoidedBy();
    }

    @Override
    public void setVoidedBy(User voidedBy) {
        super.setVoidedBy(voidedBy);
    }

    @Basic
    @Access(AccessType.PROPERTY)
    @Column(name = "void_reason")
    @Override
    public String getVoidReason() {
        return super.getVoidReason();
    }

    @Override
    public void setVoidReason(String voidReason) {
        super.setVoidReason(voidReason);
    }
}
