package com.fdmgroup.tradingplatform.tests.daotests;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;

import org.junit.Test;

import static org.mockito.Mockito.*;

import org.junit.Before;

import static org.junit.Assert.*;

import com.fdmgroup.tradingplatform.model.entity.User;
import com.fdmgroup.tradingplatform.util.DBUtil;

public class DBUtilTest {

	private static EntityManager mockEm;
	private static EntityTransaction mockTransaction;
	private static Query mockQuery;
	private static String queryString = "";
	private static List<User> testList;
	private static User testUser = new User();
	private static DBUtil dbutil;
	
	@Before
	public void setUp(){
		dbutil =  new DBUtil();
		testList = new ArrayList<User>();
		mockEm = mock(EntityManager.class);
		mockTransaction = mock(EntityTransaction.class);
		mockQuery = mock(Query.class);
		when(mockEm.getTransaction()).thenReturn(mockTransaction);
		when(mockEm.createNamedQuery(any(String.class))).thenReturn(mockQuery);
		when(mockQuery.getResultList()).thenReturn(testList);
		when(mockEm.isOpen()).thenReturn(true);
		dbutil.setEm(mockEm);
	}
	
	@Test
	public void testdbutilFindListByFieldsCreatesQueryFromStringArg() {
		dbutil.findListByFields(queryString);
		verify(mockEm).createNamedQuery(queryString);
	}

	@Test
	public void testdbutilFindListByFieldsSetsAllParameters() {
		String fieldName1 = "param1";
		String fieldValue1 = "value1";
		String fieldName2 = "param2";
		String fieldValue2 = "value2";
		dbutil.findListByFields(queryString, fieldName1, fieldValue1, fieldName2, fieldValue2);
		verify(mockQuery).setParameter(fieldName1, fieldValue1);
		verify(mockQuery).setParameter(fieldName2, fieldValue2);
	}

	@Test
	public void testdbutilFindListByFieldsGetsResultsFromQuery() {
		dbutil.findListByFields(queryString);
		verify(mockQuery).getResultList();
	}

	@Test
	public void testdbutilFindListByFieldsReturnsCorrectResults() {
		assertEquals(testList, dbutil.findListByFields(queryString));
	}

	@Test
	public void testdbutilFindListByFieldsReturnsNullWhenNonStringFieldnamePassed() {
		assertNull(dbutil.findListByFields(queryString, 1, 55));
	}
	
	@Test
	public void testdbutilFindListByFieldsReturnsNullOnQueryTimeout() {
		doThrow(new QueryTimeoutException(mockQuery)).when(mockQuery).getResultList();
		assertNull(dbutil.findListByFields(queryString, 1, 55));
	}
	
	@Test
	public void testdbutilFindListByFieldsReturnsNullOnOtherExceptions() {
		when(mockQuery.setParameter(any(String.class), anyObject())).thenThrow(new IllegalStateException());
		assertNull(dbutil.findListByFields(queryString, 1, 55));
	}

	@Test
	public void testFindUniqueByFieldsReturnsNullWhenResultSetEmpty(){
		assertNull(dbutil.findUniqueByFields(queryString));
	}

	@Test
	public void testFindUniqueByFieldsReturnsCorrectResult(){
		testList.add(testUser);
		assertEquals(testUser, dbutil.findUniqueByFields(queryString));
	}

	@Test
	public void testDeleteHelperGetsTransaction(){
		dbutil.deleteHelper(testUser);
		//Once for beginning the transaction and once for commiting
		verify(mockEm, times(2)).getTransaction();
	}
	
	
	@Test
	public void testDeleteHelperReturnsFalseOnIllegalArgumentException(){
		doThrow(new IllegalArgumentException()).when(mockEm).remove(anyObject());
		assertFalse(dbutil.deleteHelper(testUser));
	}

	@Test
	public void testDeleteHelperReturnsTrueOnSuccess(){
		assertTrue(dbutil.deleteHelper(testUser));
	}
	
	@Test
	public void testDeleteRemovesArgument(){
		dbutil.deleteHelper(testUser);
		verify(mockEm).remove(testUser);
	}
	
	@Test
	public void testCreateHelperGetsTransaction(){
		dbutil.createHelper(testUser);
		//Once for beginning the transaction and once for committing
		verify(mockEm, times(2)).getTransaction();
	}
	
	
	@Test
	public void testCreateHelperReturnsFalseOnIllegalArgumentException(){
		doThrow(new IllegalArgumentException()).when(mockEm).persist(anyObject());
		assertFalse(dbutil.createHelper(testUser));
	}

	@Test
	public void testCreateUpdateHelperReturnsUserOnSuccess(){
		assertNotNull(dbutil.createHelper(testUser));
	}
}
