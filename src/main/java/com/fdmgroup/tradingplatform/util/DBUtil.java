package com.fdmgroup.tradingplatform.util;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.StoredProcedureQuery;

import com.fdmgroup.tradingplatform.model.entity.IStorable;

public class DBUtil {
	
	private EntityManagerFactory emf = null;
	private EntityManager em = null;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void init() {
		if (emf == null){
			emf = Persistence.createEntityManagerFactory("TradingPlatform");
		}
		em = emf.createEntityManager();
	}
	
	public void destroy(){
		em.close();
		emf.close();
	}

	private Query querySetup(String queryString, Object... fieldValues)
			throws ClassCastException, QueryTimeoutException {
		Query query = em.createNamedQuery(queryString);
		for (int i = 0; i < fieldValues.length - 1; i+=2)
			query.setParameter((String) fieldValues[i], fieldValues[i + 1]);
		return query;
	}

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

	public <T extends IStorable> T findUniqueByFields(
			String queryString, Object... fieldValues) {
		List<T> results = findListByFields(queryString, fieldValues);
		if (results != null && results.size() == 1) {
			return results.get(0);
		} else if (results != null && results.size() == 0) {
			return null;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> runNativeQueryListResult(String queryString, Class<?> type) {
		Query query = em.createNativeQuery(queryString, type);
		return (List<T>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T runNativeQuerySingleResult(String queryString) {
		Query query = em.createNativeQuery(queryString);
		return (T) query.getSingleResult();
	}
	
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
