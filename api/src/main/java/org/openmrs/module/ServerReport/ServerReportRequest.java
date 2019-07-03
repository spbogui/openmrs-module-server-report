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
import org.openmrs.Location;

import javax.persistence.*;
import java.util.Date;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
@Entity
@Table(name = "server_report_request")
public class ServerReportRequest extends ReportAbstract {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_id")
	private Integer requestId;

	@Column(name = "report_name", nullable = false, length = 200)
	private String name;

	@Column(name = "request_date", nullable = false)
	private Date requestDate = new Date();

	@Temporal(TemporalType.DATE)
	@Column(name = "request_period_start_date")
	private Date requestPeriodStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "request_period_end_date")
	private Date requestPeriodEndDate;

	@Column(name = "request_location", nullable = false)
	private Location requestLocation;

	@Column(name = "content", nullable = false)
	private byte[] content;

	@ManyToOne
	@JoinColumn(nullable = false, name = "report_id")
	private ServerReport report;

	@Column(name = "saved")
	private Boolean saved;

	@ManyToOne
	@JoinColumn(nullable = false, name = "user_location_id")
	private Location userLocation;

	public ServerReportRequest() {
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getRequestPeriodStartDate() {
		return requestPeriodStartDate;
	}

	public void setRequestPeriodStartDate(Date requestPeriodStartDate) {
		this.requestPeriodStartDate = requestPeriodStartDate;
	}

	public Date getRequestPeriodEndDate() {
		return requestPeriodEndDate;
	}

	public void setRequestPeriodEndDate(Date requestPeriodEndDate) {
		this.requestPeriodEndDate = requestPeriodEndDate;
	}

	public Location getRequestLocation() {
		return requestLocation;
	}

	public void setRequestLocation(Location requestLocation) {
		this.requestLocation = requestLocation;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public ServerReport getReport() {
		return report;
	}

	public void setReport(ServerReport report) {
		this.report = report;
	}

	public Boolean getSaved() {
		return saved;
	}

	public void setSaved(Boolean saved) {
		this.saved = saved;
	}

	public Location getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(Location userLocation) {
		this.userLocation = userLocation;
	}
}