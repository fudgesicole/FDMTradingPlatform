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
		<title>Companies</title>
		<jsp:include page="styles.jsp"></jsp:include>
		<jsp:include page="imports.jsp"></jsp:include>
		<script type="text/javascript" src="resources/js/portfolio.js"></script>
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
				      <th>Sell</th>
				      <th style="display:none;">Company ID</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${requestScope.shares}" var="share">
				     	<tr>
					       	<td class="companyName">${share.company.name}</td>
					       	<td class="ownedShares">${share.shareCount}</td>
					       	<td><button type="button" class="btn btn-default sell-shares-btn"><i class="glyphicon glyphicon-usd"></i></button></td>
					       	<td style="display:none;" class="companyId">${share.company.id}</td>
				     	</tr>
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
		                    <h4 class="modal-title custom_align" id="Heading">Sell shares of <span id="sell-company-name"></span></h4>
		             </div>
		             <sf:form id="sell-shares-form" action="sell" method="post" modelAttribute="sellRequest">
		             		<input type="hidden" id="sell-company-id" name="companyId">
				           <div class="modal-body">
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon" id="basic-addon1">Time In Force</span> 
			                    		<form:select required="required" class="form-control" path="timeInForce" id="sell-timeinforce-input">
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