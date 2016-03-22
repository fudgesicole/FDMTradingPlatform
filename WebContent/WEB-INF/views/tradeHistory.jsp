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
<script type="text/javascript" src="resources/js/history.js"></script>
</head>
<body>
<jsp:include page="navigation.jsp"></jsp:include>
	<div id="content">
		<div id="ajax-content">
			<h2 class="dashboard-page-title">${sessionScope.loggedInUser.firstName} ${sessionScope.loggedInUser.lastName}'s Trade History</h2>
			<table class="table table-striped" id="user-table">
			   <thead>
			     <tr>
			       <th>Company</th>
			       <th>Date</th>
			       <th>Shares</th>
			       <th>Share Price</th>
			       <th>Total Price</th>
			       <th>Buyer</th>
			       <th>Seller</th>
			     </tr>
			   </thead>
			   <tbody>
			   	<c:forEach items="${requestScope.trades}" var="trade">
			      	<tr>
			        	<td>${trade.company.name}</td>
			        	<td>${trade.transactionTime}</td>
			        	<td>${trade.shares}</td>
			        	<td>${trade.sharePrice}</td>
			        	<td>${trade.priceTotal}</td>
			        	<td>${trade.buyer.firstName} ${$trade.buyer.lastName}</td>
			        	<td>${trade.seller.firstName} ${$trade.seller.lastName}</td>
			      	</tr>
			     </c:forEach>
			   </tbody>
			 </table>
		</div>
	</div>
	
	<jsp:include page="confirmation.jsp"></jsp:include>
</body>
</html>