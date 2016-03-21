<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Companies</title>
		<jsp:include page="styles.jsp"></jsp:include>
		<link rel="stylesheet" href="resources/css/portfolio2.css">
		<jsp:include page="imports.jsp"></jsp:include>
		<script type="text/javascript" src="resources/js/portfolio2.js"></script>
	</head>
	<body>
		<jsp:include page="navigation.jsp"></jsp:include>
		<div id="content">
			<div id="ajax-content">
				<h2 class="dashboard-page-title">${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}'s Shares</h2>
				<table class="table table-striped" id="share-table">
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
			</div>
		</div>
		
		<jsp:include page="confirmation.jsp"></jsp:include>
	</body>
</html>