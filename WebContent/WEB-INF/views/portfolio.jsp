<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2 class="dashboard-page-title">${sessionScope.user.firstName} ${sessionScope.user.lastName}'s Shares</h2>
<table class="table table-striped" id="user-table">
  <thead>
    <tr>
      <th>Company</th>
      <th>Shares</th>
    </tr>
  </thead>
  <tbody>
  	<c:forEach items="${requestScope.shares}" var="share">
     	<tr>
       	<td>${share.company.name}</td>
       	<td>${share.shareCount}</td>
     	</tr>
    </c:forEach>
  </tbody>
</table>
