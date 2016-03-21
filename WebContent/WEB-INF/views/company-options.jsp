<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<option value=""></option>
<c:forEach items="${requestScope.companies}" var="company">
	<c:set var="companyDeleted" value="false"/>
	<c:forEach items="${company.authorizedShares}" var="authShares">
		<c:if test="${not empty authShares.timeEnd}">
			<c:set var="companyDeleted" value="true"/>
		</c:if>
	</c:forEach>
	<c:choose>
		<c:when test="${companyDeleted != 'true'}">
			<option value="${company.stockId}">${company.name}</option>
		</c:when>
		<c:otherwise/>
	</c:choose>
</c:forEach>