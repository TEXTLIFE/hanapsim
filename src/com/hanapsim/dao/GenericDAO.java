package com.hanapsim.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

abstract class GenericDAO<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	// private static final EntityManagerFactory emf =
	// Persistence.createEntityManagerFactory("POSMediaPU");

//	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("HanapSimPU");
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("HanapSimPU",createEntityManagerFactory());
	private EntityManager em;
	private Class<T> entityClass;

	// Dynamic persistence configuration for Windows and Linux June 14, 2013
	@SuppressWarnings("rawtypes")
	private static Map createEntityManagerFactory() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("javax.persistence.jtaDataSource", "java:comp/env/jdbc/hanapSimDS");
		} catch (Exception e) {
			try {
			} catch (Exception ex) {
				ex.printStackTrace();
				emf.close();
			} finally {
				emf.close();
			}
		}
		return map;
	}

	public void beginTransaction() {
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}

	public void commit() {
		em.getTransaction().commit();
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	public void closeTransaction() {
		em.close();
	}

	public void commitAndCloseTransaction() {
		commit();
		closeTransaction();
	}

	public void flush() {
		em.flush();
	}

	public void joinTransaction() {
		em = emf.createEntityManager();
		em.joinTransaction();
	}

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		em.persist(entity);
	}

	public void delete(T entity) {
		T entityToBeRemoved = em.merge(entity);

		em.remove(entityToBeRemoved);
	}

	public T update(T entity) {
		return em.merge(entity);
	}

	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}

	// added by EET June 19, 2013 ?????
	public T findString(String entityID) {
		return em.find(entityClass, entityID);
	}

	public T findReferenceOnly(int entityID) {
		return em.getReference(entityClass, entityID);
	}

	// added by EET June 19, 2013 ?????
	public T findReferenceOnlyString(String entityID) {
		return em.getReference(entityClass, entityID);
	}

	// Using the unchecked because JPA does not have a
	// em.getCriteriaBuilder().createQuery()<T> method
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}
	
	// Stores Filter by TEST STORES Exempt starting 99
	@SuppressWarnings("unchecked")
	public List<T> listActiveStores() {
		String namedQuery = "select ifnull(s.storeId, h.storeCode) as storeId, ifnull(h.storeCode,'') as storeCode, ifnull(s.storeName, 'Not known') as storeName, "
				+ "ifnull(postalCode,0) as postalCode, ifnull(cutoffTime,'00:00:00') as cutOffTime, ifnull(multiStoreUserId,0) as multiStoreUserId, ifnull(singleStoreUserId,0) as singleStoreUserId, ifnull(isLicenseExpired,0) as isLicenseExpired, ifnull(gps,'') as gps,"
				+ "ifnull(teamViewerId,0) as teamViewerId, ifnull(customerId,0) as customerId, ifnull(phoneNumber,'') as phoneNumber, ifnull(address,'') as address, ifnull(suburb,'') as suburb, ifnull(city,'') as city, ifnull(banner,'') as banner,"
				+ "ifnull(region,'') as region, ifnull(storeContact,'') as storeContact, ifnull(emailAddress,'') as emailAddress "
				+ "FROM order_header AS h JOIN stores AS s ON s.StoreCode = h.StoreCode AND s.IsLicenseExpired = FALSE "
				+ "WHERE LEFT(h.StoreCode,2) = '94' GROUP BY h.StoreCode ORDER BY h.StoreCode ASC; ";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}
	
	// Stores Filter by TEST STORES Exempt starting 99
	@SuppressWarnings("unchecked")
	public List<T> listActiveStoresRecipient() {
		String namedQuery = "call get_store_recipients()";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Mail list for a particular user
	@SuppressWarnings("unchecked")
	public List<T> listParticularMails(String emailAddress) {
		String namedQuery = "call get_received_mails('" + emailAddress + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Dashboard Details Datatable
	@SuppressWarnings("unchecked")
	public List<T> listDashboardDetails(String userName, String startDate, String endDate) {
		String namedQuery = "call get_dashboard_details('" + userName + "','" + startDate + "','" + endDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}
	
	// Dashboard Details Datatable Test Stores ONLY
	@SuppressWarnings("unchecked")
	public List<T> listDashboardDetailsTest(String userName, String startDate, String endDate) {
		String namedQuery = "call get_dashboard_details_test('" + userName + "','" + startDate + "','" + endDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Dashboard Details Breakdown Dialog Widget
	@SuppressWarnings("unchecked")
	public List<T> listDashboardDetailBreakdown(String userName, int storeId, String startDate, String endDate) {
		String namedQuery = "call get_dashboard_detail_breakdown('" + userName + "'," + storeId + ",'" + startDate + "','" + endDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	
	// Dashboard Details Line Graph Dialog Widget April 21, 2014 Monday
	@SuppressWarnings("unchecked")
	public List<T> listDashboardDetailLineGraph(int storeId, Integer monthDate, Integer yearDate) {
		String namedQuery = "call get_dashboard_details_line_graph('" + storeId + "','" + monthDate + "','" + yearDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}
	
	// Dashboard Product
	@SuppressWarnings("unchecked")
	public List<T> listDashboardProduct(String userName, int storeId, String startDate, String endDate) {
		String namedQuery = "call get_dashboard_graph('" + userName + "','" + storeId + "','" + startDate + "','" + endDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Dashboard EPAY
	@SuppressWarnings("unchecked")
	public List<T> listDashboardEpay(String userName, int storeId,
			String startDate, String endDate) {
		String namedQuery = "call get_dashboard_activator_chart('" + userName + "',"
				+ storeId + ",'" + startDate + "','" + endDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}
	
	// Dashboard EPAY Table
	//Added by EET Jan. 21, 2014 Tuesday
	@SuppressWarnings("unchecked")
	public List<T> listDashboardEpayTable(String userName, int storeId, String startDate, String endDate) {
		String namedQuery = "call get_dashboard_activator_details('" + userName + "'," + storeId + ",'" + startDate + "','" + endDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Find Users by Role
	@SuppressWarnings("unchecked")
	public List<T> findUsersByRole(String role) {
		String namedQuery = "call lookup_get_users_by_role('" + role + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Export Data for Aztec user role
	// Edited January 22, 2014 Wednesday
	@SuppressWarnings("unchecked")
	public List<T> exportData(String storeIds, String startDate, String endDate) {
		String namedQuery = "call get_export_data('" + storeIds + "','" + startDate + "','" + endDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> saleData(String currentDate) {
		String namedQuery = "call get_transaction_count('" + currentDate + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}
	
	// Export Data for Activata user role
	// Edited January 27, 2014 Monday
	@SuppressWarnings("unchecked")
	public List<T> exportDataActivata(String storeIds) {
		String namedQuery = "call get_export_store_info('" + storeIds + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}
	
	// Misc Rebate on Dashboard (this is the exemption among all because it returns only 1 record)
	// Added January 23, 2014 Thursday
//	public MiscRebate miscRebate(String userName, String startDate, String endDate) {
//		String namedQuery = "call get_miscellaneous_rebate('" + userName + "','" + startDate + "','" + endDate + "')";
//		Query query = em.createNativeQuery(namedQuery, entityClass);
//		MiscRebate result =  (MiscRebate)query.getSingleResult(); //single result only NOT List
//		return result;
//	}

	// Products ***********************
	@SuppressWarnings("unchecked")
	public List<T> searchProducts(String storeId) {
		String namedQuery = "call get_store_product_components('" + storeId
				+ "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Added by EET June 19, 2013 ?????
	@SuppressWarnings("unchecked")
	public List<T> findStores(String userName) {
		String namedQuery = "call lookup_get_stores('" + userName
				+ "', '0', '0')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// **********To filter out COUNTRY According to COMPANY Value in the
	// StoreGroup May 31, 2013 Monday EET*********
	@SuppressWarnings("unchecked")
	public List<T> listAllMatchResultForLocationCountry(String userName) {
		String namedQuery = "call lookup_get_country('" + userName + "')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// **********To filter out REGIONS According to COUNTRY Value in the
	// StoreGroup May 27, 2013 Monday EET*********
	@SuppressWarnings("unchecked")
	public List<T> listAllMatchResultForLocationRegion(String userName,
			int countryId) {
		String namedQuery = "call lookup_get_regions('" + userName + "', "
				+ countryId + ")";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// **********To filter out Multi Store User
	// July 8, 2013 Monday EET*********
	@SuppressWarnings("unchecked")
	public List<T> listAllMatchResultForMultiStore() {
		String namedQuery = "call lookup_get_users_by_role('MUSTR')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// **********To filter out Single Store User
	// July 8, 2013 Monday EET*********
	@SuppressWarnings("unchecked")
	public List<T> listAllMatchResultForSingleStore() {
		String namedQuery = "call lookup_get_users_by_role('SISTR')";
		Query query = em.createNativeQuery(namedQuery, entityClass);
		List<T> result = query.getResultList();
		return result;
	}

	// Using the unchecked because JPA does not have a
	// query.getSingleResult()<T> method
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
//			System.out.println(result + " " + "####AA");
			
			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + namedQuery);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}