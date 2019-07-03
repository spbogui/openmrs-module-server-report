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
package org.openmrs.module.ServerReport.api.impl;

import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ServerReport.*;
import org.openmrs.module.ServerReport.api.ServerReportService;
import org.openmrs.module.ServerReport.api.db.ServerReportDAO;

import java.util.Date;
import java.util.List;

/**
 * It is a default implementation of {@link ServerReportService}.
 */
public class ServerReportServiceImpl extends BaseOpenmrsService implements ServerReportService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private ServerReportDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(ServerReportDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public ServerReportDAO getDao() {
	    return dao;
    }

    @Override
    public List<Location> getUserLocations(User currentUser) {
        return dao.getUserLocation(currentUser);
    }

    @Override
    public UserLocation getUserLocation(Integer locationUserId) {
        return dao.getUserLocation(locationUserId);
    }

    @Override
    public UserLocation saveUserLocation(UserLocation userLocation) {
        return dao.saveUserLocation(userLocation);
    }

    @Override
    public Boolean removeUserLocation(Location location) {
        return dao.removeUserLocation(location);
    }

    @Override
    public ServerReport getServerReport(Integer serverReportId) {
        return dao.getServerReport(serverReportId);
    }

    @Override
    public List<ServerReport> getAllServerReports(Boolean includeVoided) {
        return dao.getAllServerReports(false);
    }

    @Override
    public ServerReport saveServerReport(ServerReport serverReport) {
        return dao.saveServerReport(serverReport);
    }

    @Override
    public ServerReport updateServerReport(ServerReport serverReport) {
        return dao.updateServerReport(serverReport);
    }

    @Override
    public Boolean removeServerReport(Integer id) {
        return dao.removeServerReport(id);
    }

    @Override
    public List<CategoryOption> getAllOptions() {
        return dao.getAllOptions();
    }

    @Override
    public CategoryOption saveCategoryOption(CategoryOption categoryOption) {
        return dao.saveCategoryOption(categoryOption);
    }

    @Override
    public CategoryOption updateCategoryOption(CategoryOption categoryOption) {
        return dao.updateCategoryOption(categoryOption);
    }

    @Override
    public CategoryOption getOneCategoryOption(Integer categoryOptionId) {
        return dao.getOneCategoryOption(categoryOptionId);
    }

    @Override
    public CategoryOption getOptionByCode(String code) {
        return dao.getOptionByCode(code);
    }

    @Override
    public CategoryOption getOptionByName(String name) {
        return dao.getOptionByName(name);
    }

    @Override
    public Category saveCategory(Category category) {
        return dao.saveCategory(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return dao.updateCategory(category);
    }

    @Override
    public Category getOneCategory(Integer categoryId) {
        return dao.getOneCategory(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    @Override
    public Category getCategoryByCode(String code) {
        return dao.getCategoryByCode(code);
    }

    @Override
    public Category getCategoryByName(String name) {
        return dao.getCategoryByName(name);
    }

    @Override
    public void removeCategory(Category categoryId) {
        dao.removeCategory(categoryId);
    }

    @Override
    public void removeCategoryOption(CategoryOption option) {
        dao.removeCategoryOption(option);
    }

    @Override
    public Indicator getIndicatorByCode(String code) {
        return dao.getIndicatorByCode(code);
    }

    @Override
    public Indicator getIndicatorByName(String name) {
        return dao.getIndicatorByName(name);
    }

    @Override
    public Indicator getOneIndicator(Integer indicatorId) {
        return dao.getOneIndicator(indicatorId);
    }

    @Override
    public void removeIndicator(Indicator indicator) {
        dao.removeIndicator(indicator);
    }

    @Override
    public List<Indicator> getAllIndicators() {
        return dao.getAllIndicators();
    }

    @Override
    public Indicator saveIndicator(Indicator indicator) {
        return dao.saveIndicator(indicator);
    }

    @Override
    public Indicator updateIndicator(Indicator indicator) {
        return dao.updateIndicator(indicator);
    }

    @Override
    public IndicatorDataSet saveIndicatorDataSet(IndicatorDataSet indicatorDataSet) {
        return dao.saveIndicatorDataSet(indicatorDataSet);
    }

    @Override
    public IndicatorDataSet getOneIndicatorDataSet(Integer indicatorDataSetId) {
        return dao.getOneIndicatorDataSet(indicatorDataSetId);
    }

    @Override
    public List<IndicatorDataSet> getAllIndicatorDataSets() {
        return dao.getAllIndicatorDataSets();
    }

    @Override
    public void removeIndicatorDataSet(IndicatorDataSet indicatorDataSet) {
        dao.removeIndicatorDataSet(indicatorDataSet);
    }

    @Override
    public IndicatorDataSet getIndicatorDataSetByCode(String code) {
        return dao.getIndicatorDataSetByCode(code);
    }

    @Override
    public IndicatorDataSet getIndicatorDataSetByName(String name) {
        return dao.getIndicatorDataSetByName(name);
    }

    @Override
    public IndicatorRate getIndicatorRateByName(String name) {
        return dao.getIndicatorRateByName(name);
    }

    @Override
    public IndicatorRate getIndicatorRateByCode(String code) {
        return dao.getIndicatorRateByCode(code);
    }

    @Override
    public IndicatorRate saveIndicatorRate(IndicatorRate indicatorRate) {
        return dao.saveIndicatorRate(indicatorRate);
    }

    @Override
    public IndicatorRate updateIndicatorRate(IndicatorRate indicatorRate) {
        return dao.updateIndicatorRate(indicatorRate);
    }

    @Override
    public IndicatorRate getOneIndicatorRate(Integer indicatorRateId) {
        return dao.getOneIndicatorRate(indicatorRateId);
    }

    @Override
    public void removeIndicatorRate(IndicatorRate indicatorRate) {
        dao.removeIndicatorRate(indicatorRate);
    }

    @Override
    public List<IndicatorRate> getAllIndicatorRates() {
        return dao.getAllIndicatorRates();
    }

    @Override
    public IndicatorRateDataSet getIndicatorRateDataSetByCode(String code) {
        return dao.getIndicatorRateDataSetByCode(code);
    }

    @Override
    public IndicatorRateDataSet getIndicatorRateDataSetByName(String name) {
        return dao.getIndicatorRateDataSetByName(name);
    }

    @Override
    public IndicatorRateDataSet getOneIndicatorRateDataSet(Integer indicatorRateId) {
        return dao.getOneIndicatorRateDataSet(indicatorRateId);
    }

    @Override
    public void removeIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet) {
        dao.removeIndicatorRateDataSet(indicatorRateDataSet);
    }

    @Override
    public List<IndicatorRateDataSet> getAllIndicatorRateDataSets() {
        return dao.getAllIndicatorRateDataSets();
    }

    @Override
    public IndicatorRateDataSet saveIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet) {
        return dao.saveIndicatorRateDataSet(indicatorRateDataSet);
    }

    @Override
    public IndicatorRateDataSet updateIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet) {
        return dao.updateIndicatorRateDataSet(indicatorRateDataSet);
    }

    @Override
    public Parameter getParameterByName(String name) {
        return dao.getParameterByName(name);
    }

    @Override
    public Parameter getOneParameter(Integer parameterId) {
        return dao.getOneParameter(parameterId);
    }

    @Override
    public void removeParameter(Parameter parameter) {
        dao.removeParameter(parameter);
    }

    @Override
    public List<Parameter> getAllParameters() {
        return dao.getAllParameters();
    }

    @Override
    public Parameter saveParameter(Parameter parameter) {
        return dao.saveParameter(parameter);
    }

    @Override
    public Parameter updateParameter(Parameter parameter) {
        return dao.updateParameter(parameter);
    }

    @Override
    public ServerReport getServerReportByName(String name) {
        return dao.getServerReportByName(name);
    }

    @Override
    public ServerListing getServerListingByName(String name) {
        return dao.getServerListingByName(name);
    }

    @Override
    public ServerListing getOneServerListing(Integer listingId) {
        return dao.getOneServerListing(listingId);
    }

    @Override
    public List<ServerListing> getAllServerListings() {
        return dao.getAllServerListings();
    }

    @Override
    public void removeServerListing(ServerListing serverListing) {
        dao.removeServerListing(serverListing);
    }

    @Override
    public ServerListing saveServerListing(ServerListing serverListing) {
        return dao.saveServerListing(serverListing);
    }

    @Override
    public ServerListing updateServerListing(ServerListing serverListing) {
        return dao.updateServerListing(serverListing);
    }

    @Override
    public IndicatorDataSet updateIndicatorDataSet(IndicatorDataSet indicatorDataSet) {
        return dao.updateIndicatorDataSet(indicatorDataSet);
    }

    @Override
    public List<Object> generateListing(Integer listingId, Date startDate, Date endDate, Integer locationId) {
        return dao.generateListing(listingId, startDate, endDate, locationId);
    }
}