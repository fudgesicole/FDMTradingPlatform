<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h2 class="dashboard-page-title">${sessionScope.user.firstName}
	${sessionScope.user.lastName}'s Requests</h2>
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
		<c:forEach items="${sessionScope.user.requests}" var="request">
			<tr>
				<td>${request.company.name}</td>
				<td>${request.shares}</td>
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
