<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <h2 class="dashboard-page-title">${sessionScope.user.firstName} ${sessionScope.user.lastName}'s Trade History</h2>
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
   	<c:forEach items="${sessionScope.user.buyTrades}" var="trade">
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
   	<c:forEach items="${sessionScope.user.sellTrades}" var="trade">
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
