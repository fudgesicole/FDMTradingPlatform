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
<title>Buy Shares</title>
<jsp:include page="styles.jsp"></jsp:include>
<jsp:include page="imports.jsp"></jsp:include>
<script type="text/javascript" src="resources/js/buyShares.js"></script>
</head>
<body>
	<jsp:include page="navigation.jsp"></jsp:include>
	<div id="content">
		<div id="ajax-content">
			<h2 id="companies-page-title">Buy Shares</h2>
			 <table class="table table-striped" id="company-table">
			   <thead>
			     <tr>
			       <th>Name</th>
			       <th>Buy shares</th>
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
				        	<td class="name">${company.name}</td>
				        	<td><button type="button" class="btn btn-default buy-shares-btn"><i class="glyphicon glyphicon-usd"></i></button></td>
				        	<td class="companyId">${company.id}</td>
				      	</tr>
				      	</c:when>
				      	<c:otherwise/>
				   </c:choose>
			     </c:forEach>
			   </tbody>
			 </table>
		</div>
	</div>
	
	<div class="modal fade" id="add-request-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Request shares of <span id="buy-company-name"></span></h4>
		             </div>
		             <sf:form id="buy-shares-form" action="buy" method="get" modelAttribute="buyRequest">
		             		<input type="hidden" id="buy-company-id" name="companyId">
				           <div class="modal-body">
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon" id="basic-addon1">Time In Force</span> 
			                    		<form:select required="required" class="form-control" path="timeInForce" id="buy-timeinforce-input">
											<form:option value="DAY ONLY">Day Only</form:option>
											<form:option value="GOOD UNTIL CANCELLED">Good Until Cancelled</form:option>
											<form:option value="IMMEDIATE OR CANCEL">Immediate Or Cancel</form:option>
										</form:select>
			                        </div> 
			                    </div>
		                    <div class="form-group">
		                    	<div class="input-group">
		                    		<span class="input-group-addon" id="basic-addon1">Shares</span> 
									<sf:input class="form-control" type="number" path="shareCount" min="0" max="9999999" step="1" required="required" id="add-request-shares-input"/>
		                        </div> 
		                    </div>
		                    <div class="form-group">
		                    	<div class="input-group">
		                    		<span class="input-group-addon" id="basic-addon1">Minimum Shares</span>
									<sf:input class="form-control" type="number" min="0" max="9999999" step="1" required="required" id="add-request-minshares-input" path="minShares"/>
		                        </div> 
		                    </div>
		                    <div class="form-group">
		                    	<div class="input-group">
		                    		<span class="input-group-addon" id="basic-addon1">Limit Price</span>
									<sf:input class="form-control" type="number" min="0" max="999999.9999" step="0.0001" id="add-request-limitprice-input" path="limitPrice"/>
		                        </div> 
		                    </div>
		                    <div class="form-group">
		                    	<div class="input-group">
		                    		<span class="input-group-addon" id="basic-addon1">Stop Price</span>
									<sf:input class="form-control" type="number" min="0" max="999999.9999" step="0.0001" id="add-request-stopprice-input" path="stopPrice"/>
		                        </div> 
		                    </div>
				          </div>
				             <div class="modal-footer ">
				             		<p class="error-msg" id="#request-add-error-msg"></p>
				                    <button type="submit" id="add-request-submit-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Make Request
				                    </button>
				           </div>
			          </sf:form>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>
	
	<jsp:include page="confirmation.jsp"></jsp:include>
</body>
</html>