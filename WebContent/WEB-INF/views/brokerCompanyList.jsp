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
		<title>Companies</title>
		<jsp:include page="styles.jsp"></jsp:include>
		<link rel="stylesheet" href="resources/css/brokerCompanyList.css">
		<jsp:include page="imports.jsp"></jsp:include>
		<script type="text/javascript" src="resources/js/brokerCompanyList.js"></script>
	</head>
<body>
	<jsp:include page="navigation.jsp"></jsp:include>
	<div id="content">
		<div id="ajax-content">
			<button id="add-company-modal-btn" data-toggle="modal" data-target="#add-company-modal" class="btn btn-danger glyphicon glyphicon-plus">Add Company</button>
			<h2 id="companies-page-title">Companies</h2>
 			<table class="table table-striped" id="company-table">
				<thead>
			     <tr>
			       <th>Name</th>
			       <th>Starting Price</th>
			       <th>Authorized Shares</th>
			       <th>Edit</th>
			       <th>Delete</th>
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
				        	<td class="companyName">${company.name}</td>
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
			 <script type="text/javascript" src="resources/js/dataTableSetup.js"></script>
		</div>
	</div>
	
	<div class="modal fade" id="add-company-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Enter new company details</h1>
			             </div>
			             <sf:form id="add-company-form" action="brokerAddCompany" modelAttribute="companyAttribute">
				             <div class="modal-body">
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Name</span> 
										<sf:input path="name" class="red-glow form-control" type="text" name="name" max-length="50" required="required" id="add-company-name-input"/>
			                        </div> 
			                    </div>
			                   	<p style="display: none;" id="company-add-exists">*This company name is already taken. Please try another.</p>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Authorized Shares</span> 
										<input class="red-glow form-control" type="number" name="initialAuthorizedShareCount" min="0" max="999999999999" step="1" required="required" id="add-company-share-count-input"/>
			                        </div> 
			                    </div>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Starting Price</span> 
										<sf:input class="red-glow form-control" path="startingPrice" type="number" name="startingPrice" min="0" max="999999.9999" step="0.0001" required="required" id="add-company-price-input"/>
			                        </div> 
			                    </div>
			                    
			                 </div>
				             <div class="modal-footer ">
				                    <button type="submit" id="register-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Create company
				                    </button>
				           	 </div>
			          </sf:form>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
	    </div>
	
	<div class="modal fade" id="update-company-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Editing company <span id="edit-company-name"></span></h1>
			             </div>
			             <sf:form id="update-company-form" action="brokerUpdateCompany" modelAttribute="companyAttribute">
			             	 <sf:input type="hidden" path="id" id="edit-company-id-input"/>
				             <div class="modal-body">
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Name</span> 
										<sf:input path="name" class="red-glow form-control" type="text" name="name" max-length="50" required="required" id="edit-company-name-input"/>
			                        </div> 
			                    </div>
			                 	<p style="display: none;" id="company-edit-exists">*This company name is already taken. Please try another.</p>
			                 </div>
				             <div class="modal-footer ">
				                    <button type="submit" id="register-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Update company
				                    </button>
				           	 </div>
			          </sf:form>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
	    </div>

	<div class="modal fade" id="delete-company-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Deleting company <span id="delete-company-name"></span></h1>
			             </div>
			             <sf:form id="delete-company-form" action="brokerDeleteCompany" modelAttribute="companyAttribute">
			             	 <sf:input type="hidden" path="id" id="delete-company-id-input"/>
				             <div class="modal-footer ">
				                    <button type="submit" id="register-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Delete company
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