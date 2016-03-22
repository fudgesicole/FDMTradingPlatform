<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="styles.jsp"></jsp:include>
<jsp:include page="imports.jsp"></jsp:include>
<script type="text/javascript" src="resources/js/outstanding.js"></script>
</head>
<body>
<jsp:include page="navigation.jsp"></jsp:include>
	<div id="content">
		<div id="ajax-content">
			<h2 class="dashboard-page-title">${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}'s Requests</h2>
			<table class="table table-striped" id="user-table">
				<thead>
					<tr>
						<th>Company</th>
						<th>Shares</th>
						<th>Shares filled</th>
						<th>Minimum Shares</th>
						<th>Date Requested</th>
						<th>Type</th>
						<th>Status</th>
						<th>Time in Force</th>
						<th>Limit Price</th>
						<th>Stop Price</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${requestScope.requests}" var="request">
						<tr>
							<td>${request.company.name}</td>
							<td>${request.shareCount}</td>
							<c:choose>
								<c:when test="${empty request.sharesFilled}">
									<td>N/A</td>
								</c:when>
								<c:otherwise>
									<td>${request.sharesFilled}</td>
								</c:otherwise>
							</c:choose>
							<td>${request.minShares}</td>
							<td>${request.requestDate}</td>
							<td>${request.type}</td>
							<td>${request.status}</td>
							<td>${request.timeInForce}</td>
							<c:choose>
								<c:when test="${empty request.limitPrice}">
									<td>N/A</td>
								</c:when>
								<c:otherwise>
									<td>${request.limitPrice}</td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${empty request.stopPrice}">
									<td>N/A</td>
								</c:when>
								<c:otherwise>
									<td>${request.stopPrice}</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<jsp:include page="confirmation.jsp"></jsp:include>
</body>
</html>