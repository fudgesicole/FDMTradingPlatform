package com.fdmgroup.tradingplatform.util;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.StoredProcedureQuery;

import com.fdmgroup.tradingplatform.model.entity.IStorable;


/**
 * A utility class containing generic methods for JPA storage
 */
public class DBUtil {
	
	private EntityManagerFactory emf = null;
	private EntityManager em = null;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * Initiation method to be run on startup.
	 */
	public void init() {
		if (emf == null){
			emf = Persistence.createEntityManagerFactory("TradingPlatform");
		}
		em = emf.createEntityManager();
	}
	
	/**
	 * Method to close storage handles. Should be called on shutdown.
	 */
	public void destroy(){
		em.close();
		emf.close();
	}

	/**
	 * Setup a query with a given name and set of parameters.
	 * @param queryString The named query to use
	 * @param fieldValues 
	 * 			Contains a list of objects, where every first object must be a string 
	 * 			specifying the corresponding parameter name in the given named query, 
	 * 			and every second object must be the corresponding argument to match to 
	 * 			the parameter before it.
	 * @return The resulting query object.
	 * @throws ClassCastException
	 * @throws QueryTimeoutException
	 */
	private Query querySetup(String queryString, Object... fieldValues)
			throws ClassCastException, QueryTimeoutException {
		Query query = em.createNamedQuery(queryString);
		for (int i = 0; i < fieldValues.length - 1; i+=2)
			query.setParameter((String) fieldValues[i], fieldValues[i + 1]);
		return query;
	}

	
	/**
	 * A generic method for executing dml queries on the database.
	 * @param queryString the named query
	 * @param fieldValues 
	 * 			Contains a list of objects, where every first object must be a string 
	 * 			specifying the corresponding parameter name in the given named query, 
	 * 			and every second object must be the corresponding argument to match to 
	 * 			the parameter before it.
	 * @return true on success, false on failure
	 */
	public <T extends IStorable> boolean dmlHelper(
			String queryString, Object... fieldValues) {
		try {
			// execute update should update or delete some rows if the query was
			// successful
			return (querySetup(queryString, fieldValues).executeUpdate() > 0);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Updates the provided IStorable in the database. ***MUST BE A JPA MANAGED OBJECT
	 * @param t the updated persistent T
	 * @return The updated T, or null on failure.
	 */
	public <T extends IStorable> T updateHelper(T t) {
		T result;
		try {
			em.getTransaction().begin();
			result = em.merge(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * Creates the provided IStorable in the database.
	 * @param t the new T that should be persisted
	 * @return true on success, false on failure.
	 */
	public <T extends IStorable> boolean createHelper(T t) {
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		}  catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Deletes the provided IStorable from the database. ***MUST BE A JPA MANAGED OBJECT
	 * @param t the JPA managed T that should be removed from the database.
	 * @return true on success, false on failure.
	 */
	public <T extends IStorable> boolean deleteHelper(T t) {
		try {
			em.getTransaction().begin();
			em.remove(t);
			em.getTransaction().commit();
		}  catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	/**
	 * A generic method for finding a list of objects from the database.
	 * @param queryString the named query
	 * @param fieldValues 
	 * 			Contains a list of objects, where every first object must be a string 
	 * 			specifying the corresponding parameter name in the given named query, 
	 * 			and every second object must be the corresponding argument to match to 
	 * 			the parameter before it.
	 * @return the list of T's resulting from the query, or null on failure.
	 */
	@SuppressWarnings("unchecked")
	public <T extends IStorable> List<T> findListByFields(
			String queryString, Object... fieldValues) {
		List<T> results = null;
		try {
			results = (List<T>) querySetup(queryString, fieldValues)
					.getResultList();
		}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return results;
	}

	/**
	 * A generic method for finding one unique object from the database.
	 * @param queryString the named query
	 * @param fieldValues 
	 * 			Contains a list of objects, where every first object must be a string 
	 * 			specifying the corresponding parameter name in the given named query, 
	 * 			and every second object must be the corresponding argument to match to 
	 * 			the parameter before it.
	 * @return the T resulting from the query, or null if there are no results or multiple matches exist.
	 */
	public <T extends IStorable> T findUniqueByFields(
			String queryString, Object... fieldValues) {
		List<T> results = findListByFields(queryString, fieldValues);
		if (results != null && results.size() == 1) {
			return results.get(0);
		}
		return null;
	}

	/**
	 * Run a native sql query on the database for a list of results.
	 * @param queryString the native query to run
	 * @param type the type of object the native query should return.
	 * @return The result of the native query
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> runNativeQueryListResult(String queryString, Class<?> type) {
		Query query = em.createNativeQuery(queryString, type);
		return (List<T>) query.getResultList();
	}
	
	
	/**
	 * Run a native sql query on the database for a single result.
	 * @param queryString the native query to run
	 * @param type the type of object the native query should return.
	 * @return The result of the native query
	 */
	@SuppressWarnings("unchecked")
	public <T> T runNativeQuerySingleResult(String queryString) {
		Query query = em.createNativeQuery(queryString);
		return (T) query.getSingleResult();
	}
	
	/**
	 * Run a stored procedure in the database.
	 * @param queryString The NamedStoredProcedureQuery to run
	 * @return true on success, false on failure.
	 */
	public boolean runStoredProcedure(String queryString){
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery(queryString);
		try{
			query.execute();
		}  catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}
}
