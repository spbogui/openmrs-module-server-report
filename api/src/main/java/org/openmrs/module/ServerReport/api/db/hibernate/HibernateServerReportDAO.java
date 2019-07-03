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
package org.openmrs.module.ServerReport.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.ServerReport.*;
import org.openmrs.module.ServerReport.api.db.ServerReportDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * It is a default implementation of  {@link ServerReportDAO}.
 */
public class HibernateServerReportDAO implements ServerReportDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

	@Override
	public UserLocation getUserLocation(Integer locationUserId) {
		return (UserLocation) sessionFactory.getCurrentSession().get(UserLocation.class, locationUserId);
	}

	@Override
	public List<Location> getUserLocation(User user) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserLocation.class);
		UserLocation userLocation = (UserLocation) criteria.add(Restrictions.eq("user", user)).uniqueResult();
		return (List<Location>) userLocation.getLocations();
	}

	@Override
	public UserLocation saveUserLocation(UserLocation userLocation) {
		return null;
	}

	@Override
	public Boolean removeUserLocation(Location location) {
		return null;
	}

	@Override
	public ServerReport getServerReport(Integer serverReportId) {
		return (ServerReport) sessionFactory.getCurrentSession().get(ServerReport.class, serverReportId);
	}

	@Override
	public List<ServerReport> getAllServerReports(Boolean includeVoided) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ServerReport.class);
		List<ServerReport> result = (List<ServerReport>) (includeVoided ? criteria.list() : criteria.add(Restrictions.eq("voided", includeVoided)).list());

		if (Context.getAuthenticatedUser().isSuperUser()) {
			return result;
		} else {
			Set<Role> userRoles = Context.getAuthenticatedUser().getAllRoles();
			List<ServerReport> returnedResult = new ArrayList<ServerReport>();
			for (ServerReport report :
					result) {


				List<Role> roles = new ArrayList<Role>();
				for (String r : report.getRoles().split(",")) {
					roles.add(Context.getUserService().getRole(r));
				}

				for (Role r: roles) {

					if (userRoles.contains(r)) {
						returnedResult.add(report);
						break;
					}
				}
			}

			return returnedResult;
		}
	}

	@Override
	public ServerReport updateServerReport(ServerReport serverReport) {
    	sessionFactory.getCurrentSession().merge(serverReport);
		return serverReport;
	}

	@Override
	public ServerReport saveServerReport(ServerReport serverReport) {
    	sessionFactory.getCurrentSession().saveOrUpdate(serverReport);
		return serverReport;
	}

	@Override
	public Boolean removeServerReport(Integer id) {
    	ServerReport serverReport = getServerReport(id);
    	if (serverReport != null) {
    		serverReport.setVoided(true);
    		serverReport.setVoidedBy(Context.getAuthenticatedUser());
    		sessionFactory.getCurrentSession().saveOrUpdate(serverReport);
    		return true;
		}
		return false;
	}

	@Override
	public List<CategoryOption> getAllOptions() {
		return (List<CategoryOption>) sessionFactory.getCurrentSession().createCriteria(CategoryOption.class).list();
	}

	@Override
	@Transactional
	public CategoryOption saveCategoryOption(CategoryOption categoryOption) {
		sessionFactory.getCurrentSession().saveOrUpdate(categoryOption);
		return categoryOption;
	}

	@Override
	@Transactional
	public CategoryOption getOneCategoryOption(Integer categoryOptionId) {
		return (CategoryOption) sessionFactory.getCurrentSession().get(CategoryOption.class, categoryOptionId);
	}

	@Override
	public CategoryOption getOptionByCode(String code) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CategoryOption.class);
		return (CategoryOption) criteria.add(Restrictions.eq("code", code)).uniqueResult();
	}

	@Override
	public CategoryOption getOptionByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CategoryOption.class);
		return (CategoryOption) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public Category saveCategory(Category category) {
		sessionFactory.getCurrentSession().saveOrUpdate(category);
		return category;
	}

	@Override
	public Category updateCategory(Category category) {
    	sessionFactory.getCurrentSession().merge(category);
		return category;
	}

	@Override
	public Category getOneCategory(Integer categoryId) {
		return (Category) sessionFactory.getCurrentSession().get(Category.class, categoryId);
	}

	@Override
	public List<Category> getAllCategories() {
		return (List<Category>) sessionFactory.getCurrentSession().createCriteria(Category.class).list();
	}

	@Override
	public Category getCategoryByCode(String code) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
		return (Category) criteria.add(Restrictions.eq("code", code)).uniqueResult();
	}

	@Override
	public Category getCategoryByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Category.class);
		return (Category) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public void removeCategory(Category categoryId) {
		sessionFactory.getCurrentSession().delete(categoryId);
	}

	@Override
	public void removeCategoryOption(CategoryOption option) {
		sessionFactory.getCurrentSession().delete(option);
	}

	@Override
	public CategoryOption updateCategoryOption(CategoryOption categoryOption) {
    	sessionFactory.getCurrentSession().merge(categoryOption);
		return categoryOption;
	}

	@Override
	public Indicator getIndicatorByCode(String code) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicator.class);
		return (Indicator) criteria.add(Restrictions.eq("code", code)).uniqueResult();
	}

	@Override
	public Indicator getIndicatorByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicator.class);
		return (Indicator) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public Indicator getOneIndicator(Integer indicatorId) {
		return (Indicator) sessionFactory.getCurrentSession().get(Indicator.class, indicatorId);
	}

	@Override
	public void removeIndicator(Indicator indicator) {
		sessionFactory.getCurrentSession().delete(indicator);
	}

	@Override
	public List<Indicator> getAllIndicators() {
		return (List<Indicator>) sessionFactory.getCurrentSession().createCriteria(Indicator.class).list();
	}

	@Override
	public Indicator saveIndicator(Indicator indicator) {
    	sessionFactory.getCurrentSession().saveOrUpdate(indicator);
		return indicator;
	}

	@Override
	public Indicator updateIndicator(Indicator indicator) {
    	sessionFactory.getCurrentSession().merge(indicator);
		return indicator;
	}

	@Override
	public IndicatorDataSet saveIndicatorDataSet(IndicatorDataSet indicatorDataSet) {
    	sessionFactory.getCurrentSession().saveOrUpdate(indicatorDataSet);
		return indicatorDataSet;
	}

	@Override
	public IndicatorDataSet updateIndicatorDataSet(IndicatorDataSet indicatorDataSet) {
    	sessionFactory.getCurrentSession().merge(indicatorDataSet);
		return indicatorDataSet;
	}

	@Override
	public IndicatorDataSet getOneIndicatorDataSet(Integer indicatorDataSetId) {
		return (IndicatorDataSet) sessionFactory.getCurrentSession().get(IndicatorDataSet.class, indicatorDataSetId);
	}

	@Override
	public List<IndicatorDataSet> getAllIndicatorDataSets() {
		return (List<IndicatorDataSet>) sessionFactory.getCurrentSession().createCriteria(IndicatorDataSet.class).list();
	}

	@Override
	public void removeIndicatorDataSet(IndicatorDataSet indicatorDataSet) {
		sessionFactory.getCurrentSession().delete(indicatorDataSet);
	}

	@Override
	public IndicatorDataSet getIndicatorDataSetByCode(String code) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndicatorDataSet.class);
		return (IndicatorDataSet) criteria.add(Restrictions.eq("code", code)).uniqueResult();
	}

	@Override
	public IndicatorDataSet getIndicatorDataSetByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndicatorDataSet.class);
		return (IndicatorDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public IndicatorRate getIndicatorRateByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndicatorRate.class);
		return (IndicatorRate) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public IndicatorRate getIndicatorRateByCode(String code) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndicatorRate.class);
		return (IndicatorRate) criteria.add(Restrictions.eq("code", code)).uniqueResult();
	}

	@Override
	public IndicatorRate saveIndicatorRate(IndicatorRate indicatorRate) {
    	sessionFactory.getCurrentSession().saveOrUpdate(indicatorRate);
		return indicatorRate;
	}

	@Override
	public IndicatorRate getOneIndicatorRate(Integer indicatorRateId) {
		return (IndicatorRate) sessionFactory.getCurrentSession().get(IndicatorRate.class, indicatorRateId);
	}

	@Override
	public void removeIndicatorRate(IndicatorRate indicatorRate) {
		sessionFactory.getCurrentSession().delete(indicatorRate);
	}

	@Override
	public List<IndicatorRate> getAllIndicatorRates() {
		return (List<IndicatorRate>) sessionFactory.getCurrentSession().createCriteria(IndicatorRate.class).list();
	}

	@Override
	public IndicatorRateDataSet getIndicatorRateDataSetByCode(String code) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndicatorRateDataSet.class);
		return (IndicatorRateDataSet) criteria.add(Restrictions.eq("code", code)).uniqueResult();
	}

	@Override
	public IndicatorRateDataSet getIndicatorRateDataSetByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(IndicatorRateDataSet.class);
		return (IndicatorRateDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public IndicatorRateDataSet getOneIndicatorRateDataSet(Integer indicatorRateId) {
		return (IndicatorRateDataSet) sessionFactory.getCurrentSession().get(IndicatorRateDataSet.class, indicatorRateId);
	}

	@Override
	public void removeIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet) {
		sessionFactory.getCurrentSession().delete(indicatorRateDataSet);
	}

	@Override
	public List<IndicatorRateDataSet> getAllIndicatorRateDataSets() {
		return (List<IndicatorRateDataSet>) sessionFactory.getCurrentSession().createCriteria(IndicatorRateDataSet.class).list();
	}

	@Override
	public IndicatorRateDataSet saveIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet) {
    	sessionFactory.getCurrentSession().saveOrUpdate(indicatorRateDataSet);
		return indicatorRateDataSet;
	}

	@Override
	public IndicatorRateDataSet updateIndicatorRateDataSet(IndicatorRateDataSet indicatorRateDataSet) {
    	sessionFactory.getCurrentSession().merge(indicatorRateDataSet);
		return indicatorRateDataSet;
	}

	@Override
	public Parameter getParameterByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Parameter.class);
		return (Parameter) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public Parameter getOneParameter(Integer parameterId) {
		return (Parameter) sessionFactory.getCurrentSession().get(Parameter.class, parameterId);
	}

	@Override
	public void removeParameter(Parameter parameter) {
		sessionFactory.getCurrentSession().delete(parameter);
	}

	@Override
	public List<Parameter> getAllParameters() {
		return sessionFactory.getCurrentSession().createCriteria(Parameter.class).list();
	}

	@Override
	public Parameter saveParameter(Parameter parameter) {
    	sessionFactory.getCurrentSession().saveOrUpdate(parameter);
		return parameter;
	}

	@Override
	public Parameter updateParameter(Parameter parameter) {
    	sessionFactory.getCurrentSession().merge(parameter);
		return parameter;
	}

	@Override
	public ServerReport getServerReportByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ServerReport.class);
		return (ServerReport) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public ServerListing getServerListingByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ServerListing.class);
		return (ServerListing) criteria.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public ServerListing getOneServerListing(Integer listingId) {
		return (ServerListing) sessionFactory.getCurrentSession().get(ServerListing.class, listingId);
	}

	@Override
	public List<ServerListing> getAllServerListings() {
		List<ServerListing> result = (List<ServerListing>) sessionFactory.getCurrentSession().createCriteria(ServerListing.class).list();

		if (Context.getAuthenticatedUser().isSuperUser()) {
			return result;
		} else {
			Set<Role> userRoles = Context.getAuthenticatedUser().getAllRoles();
			List<ServerListing> returnedResult = new ArrayList<ServerListing>();
			for (ServerListing report :
					result) {


				List<Role> roles = new ArrayList<Role>();
				for (String r : report.getRoles().split(",")) {
					roles.add(Context.getUserService().getRole(r));
				}

				for (Role r: roles) {

					if (userRoles.contains(r)) {
						returnedResult.add(report);
						break;
					}
				}
			}

			return returnedResult;
		}
	}

	@Override
	public void removeServerListing(ServerListing serverListing) {
		sessionFactory.getCurrentSession().delete(serverListing);
	}

	@Override
	public ServerListing saveServerListing(ServerListing serverListing) {
    	sessionFactory.getCurrentSession().saveOrUpdate(serverListing);
		return serverListing;
	}

	@Override
	public ServerListing updateServerListing(ServerListing serverListing) {
    	sessionFactory.getCurrentSession().merge(serverListing);
		return serverListing;
	}

	@Override
	public IndicatorRate updateIndicatorRate(IndicatorRate indicatorRate) {
    	sessionFactory.getCurrentSession().merge(indicatorRate);
		return indicatorRate;
	}

	@Override
	public List<Object> generateListing(Integer listingId, Date startDate, Date endDate, Integer locationId) {
		ServerListing listing = getOneServerListing(listingId);
		String sqlQuery = listing.getSqlQuery();
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);

		if (sqlQuery.contains(":startDate")) {
			query.setParameter("startDate", startDate);
		}
		if (sqlQuery.contains(":endDate")) {
			query.setParameter("endDate", endDate);
		}
		if (sqlQuery.contains(":locationId")) {
			query.setParameter("locationId", locationId);
		}

		return query.list();
	}
}