<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <h2 id="companies-page-title">Companies</h2>
 <table class="table table-striped" id="company-table">
   <thead>
     <tr>
       <th>Name</th>
       <th>Starting Price</th>
       <th>Authorized Shares</th>
       <th class="companyId">ID</th>
     </tr>
   </thead>
   <tbody>
   	<c:forEach items="${requestScope.companies}" var="company">
   		<c:set var="companyDeleted" value="false"/>
	 	<c:forEach items="${company.authorizedShares}" var="authShares">
	   		<c:if test="${not empty authShares.timeEnd}">
	   			<c:set var="companyDeleted" value="true"/>
	      	</c:if>
      	</c:forEach>
      	<c:choose>
      		<c:when test="${companyDeleted != 'true'}">
	      	<tr>
	        	<td>${company.name}</td>
	        	<td>${company.startingPrice}</td>
		        <td>
		        	<c:set var="total" value="${0}"/>
	  				<c:forEach items="${company.authorizedShares}" var="shares">
	        			<c:set var="total" value="${total + shares.authorized}" />
	  				</c:forEach>
	        		<p>${total}</p>
	        	</td>
	        	<td><button type="button" class="btn btn-default company-edit-btn" data-toggle="modal" data-target="#edit-company-modal"><i class="fa fa-pencil-square-o"></i></button></td>
	        	<td><button type="button" class="btn btn-default company-delete-btn" data-toggle="modal" data-target="#delete-company-modal"><i class="fa fa-trash"></i></button></td>
	        	<td class="companyId">${company.id}</td>
	      	</tr>
	      	</c:when>
	      	<c:otherwise/>
	   </c:choose>
     </c:forEach>
   </tbody>
 </table>
