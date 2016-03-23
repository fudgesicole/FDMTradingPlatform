<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Companies</title>
		<jsp:include page="styles.jsp"></jsp:include>
		<link rel="stylesheet" href="resources/css/userList.css">
		<jsp:include page="imports.jsp"></jsp:include>
		<script type="text/javascript" src="resources/js/userList.js"></script>
	</head>
	<body>
		<jsp:include page="navigation.jsp"></jsp:include>
		<div id="content">
			<div id="ajax-content">
				<button id="add-user-modal-btn" data-toggle="modal" data-target="#add-user-modal" class="btn btn-danger glyphicon glyphicon-plus">Add User</button>
				<h2 id="users-page-title">Users</h2>
				<table class="table table-striped" id="user-table">
				  <thead>
				    <tr>
				      <th>Username</th>
				      <th>First Name</th>
				      <th>Last Name</th>
				      <th>Roles</th>
				      <th>Edit</th>
				      <th>Delete</th>
				      <th>Reset password</th>
				      <th class="userId">ID</th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${requestScope.users}" var="user">
				 		<c:if test="${user != sessionScope.user}">
				    	<tr>
				      	<td class="userName">${user.userName}</td>
				      	<td class="firstName">${user.firstName}</td>
				      	<td class="lastName">${user.lastName}</td>
				       <td class="roles">
				      		<c:forEach items="${user.roles}" var="role">
				      			<p>${role.name}</p>
				      		</c:forEach>
				      	</td>
				      	<td><button type="button" class="btn btn-default user-edit-btn"><i class="fa fa-pencil-square-o"></i></button></td>
				      	<td><button type="button" class="btn btn-default user-delete-btn"><i class="fa fa-trash"></i></button></td>
				      	<td><button type="button" class="btn btn-default pw-reset-btn"><i class="glyphicon glyphicon-repeat"></i></button></td>
				      	<td class="userId">${user.id}</td>
				    	</tr>
				    	</c:if>
				   </c:forEach>
				  </tbody>
				</table>
				<script type="text/javascript" src="resources/js/dataTableSetup.js"></script>
			</div>
		</div>
		
		<div class="modal fade" id="add-user-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Enter new user details</h1>
			             </div>
			             <sf:form id="register-form" action="adminAddUser" modelAttribute="user">
				             <div class="modal-body">
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Username</span> 
										<sf:input path="userName" class="input-lg red-glow form-control" type="text" autocomplete="off" name="userName" max-length="50" required="required" id="register-username-input"/>
			                        </div> 
			                    </div>
			                    <p style="display: none;" id="user-exists">*This username is already taken. Please try another.</p>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon input-lg">Password</span> 
										<sf:input path="passWord" class="input-lg red-glow form-control" type="password" name="userName" max-length="50" required="required" id="register-password-input"/>
			                        </div> 
			                    </div>
			                    <div class="form-group">
									<div class="input-group">
										<span class="input-group-addon">Confirm Password</span>
										<input class="red-glow form-control input-lg"
											type="password"
											required="required" id="register-password-confirm-input" />
									</div>
								</div>
								<p style="display: none;" id="password-match-msg">*Passwords do not match</p>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">First Name</span> 
										<sf:input path="firstName" class="input-lg red-glow form-control" type="text" name="firstName" max-length="50" required="required" id="register-firstname-input"/>
			                        </div> 
			                    </div>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Last Name</span> 
										<sf:input path="lastName" class="input-lg red-glow form-control" type="text" name="lastName" max-length="50" required="required" id="register-lastname-input"/>
			                        </div> 
			                    </div>
			                    
			                    <h3>This user is a:</h3>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="admin-box" name="roleNames" value="admin"/>Admin</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="shareholder-box" name="roleNames" value="shareholder"/>Shareholder</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="broker-box" name="roleNames" value="broker"/>Broker</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="company-box" name="roleNames" value="company"/>Company</label>
								</div>			                    

			                  </div>
				             <div class="modal-footer ">
				                    <button type="submit" id="register-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Create user
				                    </button>
				           </div>
			          </sf:form>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
	    </div>
		
		<div class="modal fade" id="delete-user-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Delete User, are you sure?</h1>
			             </div>
			             <sf:form id="delete-user-form" class="no-confirm" action="adminDeleteUser" method="post">
			             	<input type="hidden" id="delete-user-id" name="id"/>
				             <div class="modal-footer ">
				                    <button type="submit" id="register-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Delete user
				                    </button>
				           </div>
			          </sf:form>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
	    </div>

		<div class="modal fade" id="reset-password-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Reset <span id="reset-pw-name"></span>'s password, are you sure?</h1>
			             </div>
			             <sf:form id="reset-password-form" class="no-confirm" action="adminResetPassword" method="post">
			             	<input type="hidden" id="reset-password-user-id" name="id"/>
				             <div class="modal-footer ">
				                    <button type="submit" id="register-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Reset password
				                    </button>
				           </div>
			          </sf:form>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
	    </div>
		
		<div class="modal fade" id="edit-user-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Edit User</h1>
			             </div>
			             <sf:form id="edit-form" action="adminEditUser" modelAttribute="user">
				             <div class="modal-body">
				             	<sf:input type="hidden" id="edit-id-input" path="id"/>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Username</span> 
										<sf:input path="userName" class="red-glow form-control" type="text" autocomplete="off" name="userName" max-length="50" required="required" id="edit-userName-input"/>
			                        </div> 
			                    </div>
			                    <p style="display: none;" id="edit-user-exists">*This username is already taken. Please try another.</p>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">First Name</span> 
										<sf:input path="firstName" class="red-glow form-control" type="text" name="firstName" max-length="50" required="required" id="edit-firstName-input"/>
			                        </div> 
			                    </div>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Last Name</span> 
										<sf:input path="lastName" class="red-glow form-control" type="text" name="lastName" max-length="50" required="required" id="edit-lastName-input"/>
			                        </div> 
			                    </div>
			                    
			                    <h3>This user is a:</h3>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="edit-admin-box" name="roleNames" value="admin"/>Admin</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="edit-shareholder-box" name="roleNames" value="shareholder"/>Shareholder</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="edit-broker-box" name="roleNames" value="broker"/>Broker</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="edit-company-box" name="roleNames" value="company"/>Company</label>
								</div>			                    

			                  </div>
				             <div class="modal-footer ">
				                    <button type="submit" id="edit-user-submit-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Update user
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