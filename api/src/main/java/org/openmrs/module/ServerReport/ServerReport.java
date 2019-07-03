/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.ServerReport;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.BaseOpenmrsObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
@Entity
@Table(name = "server_report")
public class ServerReport extends ReportAbstract {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private Integer reportId;

	@Column(name = "report_name", nullable = false, unique = true, length = 200)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "roles")
	private String roles;

	@ManyToMany
	@JoinTable(name = "server_report_indicator_dataset",
			joinColumns = @JoinColumn(name = "report_id"),
			inverseJoinColumns = @JoinColumn(name = "indicator_dataset_id"))
	private Set<IndicatorDataSet> indicatorDataSets = new HashSet<IndicatorDataSet>();

	@ManyToMany
	@JoinTable(name = "server_report_indicator_rate_dataset",
			joinColumns = @JoinColumn(name = "report_id"),
			inverseJoinColumns = @JoinColumn(name = "indicator_rate_dataset_id"))
	private Set<IndicatorRateDataSet> indicatorRateDataSets = new HashSet<IndicatorRateDataSet>();

	@ManyToMany
	@JoinTable(name = "server_report_parameter",
			joinColumns = @JoinColumn(name = "report_id"),
			inverseJoinColumns = @JoinColumn(name = "parameter_id"))
	private Set<Parameter> parameters = new HashSet<Parameter>();

	public ServerReport() {
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<IndicatorDataSet> getIndicatorDataSets() {
		return indicatorDataSets;
	}

	public void setIndicatorDataSets(Set<IndicatorDataSet> indicatorDataSets) {
		this.indicatorDataSets = indicatorDataSets;
	}

	public Set<IndicatorRateDataSet> getIndicatorRateDataSets() {
		return indicatorRateDataSets;
	}

	public void setIndicatorRateDataSets(Set<IndicatorRateDataSet> indicatorRateDataSets) {
		this.indicatorRateDataSets = indicatorRateDataSets;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Set<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<Parameter> parameters) {
		this.parameters = parameters;
	}
}