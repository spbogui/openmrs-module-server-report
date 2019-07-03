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
package org.openmrs.module.ServerReport.api;

import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.ServerReport.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(ServerReportService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface ServerReportService extends OpenmrsService {
    List<Location> getUserLocations(User currentUser);
    UserLocation getUserLocation(Integer locationUserId);
    UserLocation saveUserLocation(UserLocation userLocation);
    Boolean removeUserLocation(Location location);

    ServerReport getServerReport(Integer serverReportId);
    List<ServerReport> getAllServerReports(Boolean includeVoided);
    ServerReport saveServerReport(ServerReport serverReport);
    ServerReport updateServerReport(ServerReport serverReport);
    Boolean removeServerReport(Integer id);

    List<CategoryOption> getAllOptions();

    CategoryOption saveCategoryOption(CategoryOption categoryOption);
    CategoryOption updateCategoryOption(CategoryOption categoryOption);

    CategoryOption getOneCategoryOption(Integer categoryOptionId);

    CategoryOption getOptionByCode(String code);

    CategoryOption getOptionByName(String name);

    Category saveCategory(Category category);
    Category updateCategory(Category category);

    Category getOneCategory(Integer categoryId);

    List<Category> getAllCategories();

    Category getCategoryByCode(String code);

    Category getCategoryByName(String name);

    void removeCategory(Category categoryId);

    void removeCategoryOption(CategoryOption option);

    Indicator getIndicatorByCode(String code);

    Indicator getIndicatorByName(String name);

    Indicator getOneIndicator(Integer indicatorId);

    void removeIndicator(Indicator indicator);

    List<Indicator> getAllIndicators();

    Indicator saveIndicator(Indicator indicator);
    Indicator updateIndicator(Indicator indicator);

    IndicatorDataSet saveIndicatorDataSet(IndicatorDataSet indicatorDataSet);

    IndicatorDataSet getOneIndicatorDataSet(Integer indicatorDataSetId);

    List<IndicatorDataSet> getAllIndicatorDataSets();

    void removeIndicatorDataSet(IndicatorDataSet indicatorDataSet);

    IndicatorDataSet getIndicatorDataSetByCode(String code);

    IndicatorDataSet getIndicatorDataSetByName(String name);

    IndicatorRate getIndicatorRateByName(String name);

    IndicatorRate getIndicatorRateByCode(String code);

    IndicatorRate saveIndicatorRate(IndicatorRate indicatorRate);
    IndicatorRate updateIndicatorRate(IndicatorRate indicatorRate);

    IndicatorRate getOneIndicatorRate(Integer delId);

    void removeIndicatorRate(IndicatorRate indicatorRate);

    List<IndicatorRate> getAllIndicatorRates();

    IndicatorRateDataSet getIndicatorRateDataSetByCode(String code);

    IndicatorRateDataSet getIndicatorRateDataSetByName(String name);

    IndicatorRateDataSet getOneIndicatorRateDataSet(Integer indicatorRateId);

    void removeIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet);

    List<IndicatorRateDataSet> getAllIndicatorRateDataSets();

    IndicatorRateDataSet saveIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet);
    IndicatorRateDataSet updateIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet);

    Parameter getParameterByName(String name);

    Parameter getOneParameter(Integer parameterId);

    void removeParameter(Parameter parameter);

    List<Parameter> getAllParameters();

    Parameter saveParameter(Parameter parameter);
    Parameter updateParameter(Parameter parameter);

    ServerReport getServerReportByName(String name);

    ServerListing getServerListingByName(String name);

    ServerListing getOneServerListing(Integer listingId);

    List<ServerListing> getAllServerListings();

    void removeServerListing(ServerListing serverListing);

    ServerListing saveServerListing(ServerListing serverListing);
    ServerListing updateServerListing(ServerListing serverListing);

    IndicatorDataSet updateIndicatorDataSet(IndicatorDataSet indicatorDataSet);

    List<Object> generateListing(Integer listingId, Date startDate, Date endDate, Integer locationId);

	/*
	 * Add service methods here
	 * 
	 */
}