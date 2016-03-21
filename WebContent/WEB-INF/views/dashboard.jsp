<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Trading Platform</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="Login page for Trading Platform">
<meta name="author" content="Cole Fudge">

<link href="plugins/bootstrap/bootstrap.css" rel="stylesheet">
<link href="plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">

<!-- Bootstrap core CSS -->
<link rel="icon" href="img/logo-TP.jpg">	
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Amaranth'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Righteous'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/dashboard.css" type="text/css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="plugins/chosen/chosen.css">
</head>
<body>
	<header>
		<i class="fa fa-bars fa-2x sidebar-toggle"></i>
		<div id="title">
			<p>Trading Platform</p>
		</div>
		<div id="user-widget">
			<p id="welcome">Hello, ${sessionScope.user.firstName}
				${sessionScope.user.lastName}</p>
			<div id="power-button">
				<a class="glyphicon glyphicon-off" href="/TradingPlatform/logout"></a>
			</div>
		</div>
	</header>

	<div id="sidebar">
		<ul class="nav main-menu">
			<c:forEach items="${sessionScope.user.roles}" var="role">
				<c:choose>
					<c:when test="${role.name == 'admin'}">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="fa fa-cogs"></i> <span
								class="hidden-xs">Admin Actions</span>
						</a>
							<ul class="sub-menu">
								<li class="inactive"><a class="ajax-link"
									href="/TradingPlatform/users">User List</a></li>
								<li class="inactive"><a class="ajax-link"
									href="/TradingPlatform/userlogs">View User Logs</a>
								</li>
								<li class="inactive"><a class="ajax-link"
									href="/TradingPlatform/syslogs">View System Logs</a>
								</li>
								<li class="inactive" id="user-add-sidebar-btn" data-toggle="modal" data-target="#add-user-modal"><a>Add new user</a></li>
							</ul></li>
					</c:when>
					<c:when test="${role.name == 'shareholder'}">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="fa fa-money"></i> <span
								class="hidden-xs">Shareholder Actions</span></a>
							<ul class="sub-menu">
								<li class="inactive"><a class="ajax-link"
									href="/TradingPlatform/portfolio">Portfolio</a></li>
								<li class="inactive"><a class="ajax-link"
									href="history.jsp">Trade History</a></li>
								<li class="inactive"><a class="ajax-link"
									href="outstanding.jsp">Requests</a></li>
								<li class="inactive" id="request-add-sidebar-btn" data-toggle="modal" data-target="#add-request-modal"><a>Make request</a></li>
							</ul></li>
					</c:when>
					<c:when test="${role.name == 'broker'}">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="fa fa-briefcase"></i> <span
								class="hidden-xs">Broker Actions</span></a>
							<ul class="sub-menu">
								<li class="inactive"><a class="ajax-link"
									href="/TradingPlatform/companies">View Companies</a></li>
								<li class="inactive" id="company-add-sidebar-btn" data-toggle="modal" data-target="#add-company-modal"><a>Add new company</a></li>
							</ul></li>
					</c:when>
					<c:otherwise>
						<!-- Nothing for other roles -->
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
	<!--Start Content-->
	<div id="content">
		<div class="preloader">
			<img src="img/devoops_getdata.gif" class="devoops-getdata"
				alt="preloader" />
		</div>
		<div id="overlay"></div>
		<div id="ajax-content"></div>
		<!--End Content-->
	</div>


	<div id='operation-alert' class='alert alert-danger' role='alert'>
		<button type="button" class='close'><span aria-hidden="true">&times;</span></button>
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		<span class="message" aria-hidden="true"></span>
	</div>

	<div class="modal fade" id="add-user-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Enter New User Details <img class="spinner" src="img/spinner.gif"/></h4>
		             </div>
		             <div class="modal-body">
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Username</span>
	                            <input id="add-user-username-input" name="userName" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Password</span>
	                            <input id="add-user-password-input" name="passWord" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">First Name</span>
	                            <input id="add-user-firstname-input" name="firstName" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Last Name</span>
	                            <input id="add-user-lastname-input" name="lastName" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group" id="user-role-btns">
		                    <div class="btn-group" role="group" aria-label="...">
								<label class="btn btn-danger">
									<input id="add-user-admin-input" type="checkbox" name="roles" value="1" autocomplete="off"> Admin
								</label>
								<label class="btn btn-danger">
									<input id="add-user-shareholder-input" type="checkbox" name="roles" value="2" autocomplete="off"> Shareholder
								</label>
								<label class="btn btn-danger">
									<input id="add-user-company-input" type="checkbox" name="roles" value="3" autocomplete="off"> Company
								</label>
								<label class="btn btn-danger">
									<input id="add-user-broker-input" type="checkbox" name="roles" value="4" autocomplete="off"> Broker
								</label>
							</div>
						</div>
		             <div class="modal-footer ">
		                    <button data-dismiss="modal" type="button" id="add-user-submit-btn" class="btn btn-danger btn-lg" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Add
		                    </button>
		             </div>
		          </div>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>
    
	<div class="modal fade" id="edit-user-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Edit User Details <img class="spinner" src="img/spinner.gif"/></h4>
		             </div>
		             <div class="modal-body">
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Username</span>
	                            <input id="edit-user-username-input" name="userName" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Password</span>
	                            <input id="edit-user-password-input" name="passWord" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">First Name</span>
	                            <input id="edit-user-firstname-input" name="firstName" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Last Name</span>
	                            <input id="edit-user-lastname-input" name="lastName" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
	                    <div class="form-group" id="user-role-btns">
		                    <div class="btn-group" role="group" aria-label="...">
								<label class="btn btn-danger">
									<input id="edit-user-admin-input" type="checkbox" name="roles" value="1" autocomplete="off"> Admin
								</label>
								<label class="btn btn-danger">
									<input id="edit-user-shareholder-input" type="checkbox" name="roles" value="2" autocomplete="off"> Shareholder
								</label>
								<label class="btn btn-danger">
									<input id="edit-user-company-input" type="checkbox" name="roles" value="3" autocomplete="off"> Company
								</label>
								<label class="btn btn-danger">
									<input id="edit-user-broker-input" type="checkbox" name="roles" value="4" autocomplete="off"> Broker
								</label>
							</div>
						</div>
						<input type="hidden" name="id" id="edit-user-id-input">
		             <div class="modal-footer ">
		                    <button data-dismiss="modal" type="button" id="edit-user-submit-btn" class="btn btn-danger btn-lg" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Update
		                    </button>
		             </div>
		          </div>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>

	<div class="modal fade" id="delete-user-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Delete User <span id="delete-user-username"></span>, are you sure? <img class="spinner" src="img/spinner.gif"/></h4>
		             </div>
		             <div class="modal-footer ">
		                    <button type="button" id="delete-user-submit-btn" class="btn btn-danger btn-lg" data-dismiss="modal" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Confirm
		                    </button>
		             </div>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>

	<div class="modal fade" id="add-request-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Enter Request Details <img class="spinner" src="img/spinner.gif"/></h4>
		             </div>
		             <form id="add-request-form" action="request">
		             <div class="modal-body">
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Type</span>
	                            <select required="required" id="add-request-type-input" class="form-control" name="requestType">
									<option value="BUY">Buy</option>
									<option value="SELL">Sell</option>
								</select>
	                        </div> 
	                    </div>
                    <div class="form-group">
                    	<div class="input-group">
                    		<span class="input-group-addon">Company</span>
                    		<select required="required" data-placeholder="Choose a Company..." class="form-control chosen-select"  tabindex="2" id="add-request-stockid-input" name="stockId">
                    			<option value=""></option>
                    		</select>
                        </div> 
                    </div>
                    <div class="form-group">
                    	<div class="input-group">
                    		<span class="input-group-addon" id="basic-addon1">Time In Force</span> 
                    		<select required="required" class="form-control" name="timeInForce" id="add-request-timeinforce-input">
								<option value="DAY ONLY">Day Only</option>
								<option value="GOOD UNTIL CANCELLED">Good Until Cancelled</option>
								<option value="IMMEDIATE OR CANCEL">Immediate Or Cancel</option>
							</select>
                        </div> 
                    </div>
                    <div class="form-group">
                    	<div class="input-group">
                    		<span class="input-group-addon" id="basic-addon1">Shares</span> 
							<input class="form-control" type="number" name="shares" min="0" max="9999999" step="1" required="required" id="add-request-shares-input">
                        </div> 
                    </div>
                    <div class="form-group">
                    	<div class="input-group">
                    		<span class="input-group-addon" id="basic-addon1">Minimum Shares</span>
							<input class="form-control" type="number" name="minShares" min="0" max="9999999" step="1" required="required" id="add-request-minshares-input">
                        </div> 
                    </div>
                    <div class="form-group">
                    	<div class="input-group">
                    		<span class="input-group-addon" id="basic-addon1">Limit Price</span>
							<input class="form-control" type="number" name="limitPrice" min="0" max="999999.9999" step="0.0001" id="add-request-limitprice-input">
                        </div> 
                    </div>
                    <div class="form-group">
                    	<div class="input-group">
                    		<span class="input-group-addon" id="basic-addon1">Stop Price</span>
							<input class="form-control" type="number" name="stopPrice" min="0" max="999999.9999" step="0.0001" id="add-request-stopprice-input">
                        </div> 
                    </div>
		          </div>
		             <div class="modal-footer ">
		             		<p class="error-msg" id="#request-add-error-msg"></p>
		                    <button type="submit" id="add-request-submit-btn" class="btn btn-danger btn-lg" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Add
		                    </button>
		           </div>
			          </form>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>


	<div class="modal fade" id="add-company-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Enter New Company Details <img class="spinner" src="img/spinner.gif"/></h4>
		             </div>
		             <form id="add-company-form" action="/TradingPlatformWeb/createCompany" method="post">
		             <div class="modal-body">
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Name</span>
	                            <input required="required" id="add-company-name-input" maxlength="50" name="name" class="form-control"  type="text"/>
	                        </div> 
	                        <p id="comp-exists-msg">A company with this name already exists.</p>
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Starting Price</span>
	                           <input required="required" id="add-company-start-price-input" class="form-control" min="0" max="999999.9999" step="0.01" name="startingPrice" type="number"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Initial Shares</span>
	                           <input required="required" id="add-company-authorized-shares-input" class="form-control" min="0" max="9999999" step="1" name="sharesAuthorized" type="number"/>
	                        </div> 
	                    </div>
		             </div>
		             <div class="modal-footer ">
		                    <button type="submit" type="button" id="add-company-submit-btn" class="btn btn-danger btn-lg" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Add
		                    </button>
		             </div>
		             </form>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>
    
	<div class="modal fade" id="edit-company-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Edit Company Name  <img class="spinner" src="img/spinner.gif"/></h4>
		             </div>
		             <div class="modal-body">
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon">Name</span>
	                            <input id="edit-company-name-input" name="name" class="form-control"  type="text"/>
	                        </div> 
	                    </div>
		             </div>
		             <div class="modal-footer ">
		                    <button type="button" id="edit-company-submit-btn" data-dismiss="modal" class="btn btn-danger btn-lg" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Update
		                    </button>
		             </div>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>
	
	<div class="modal fade" id="delete-company-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
		      <div class="modal-content">
		             <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
		                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
		                    </button>
		                    <h4 class="modal-title custom_align" id="Heading">Delete company <span id="delete-company-name"></span>, are you sure? <img class="spinner" src="img/spinner.gif"/></h4>
		             </div>
		             <div class="modal-footer ">
		                    <button type="button" id="delete-company-submit-btn" class="btn btn-danger btn-lg" data-dismiss="modal" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Confirm
		                    </button>
		             </div>
		      </div>
		      <!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
    </div>


	<script src="plugins/jquery/jquery.min.js"></script>
	<script src="plugins/bootstrap/bootstrap.min.js"></script>
	<script src="plugins/jquery-ui/jquery-ui.min.js"></script>
	<script src="plugins/chosen/chosen.jquery.js"></script>
	<script src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
	<script src="https://cdn.datatables.net/1.10.11/js/dataTables.bootstrap.min.js"></script>              
	<script src="http://jqueryvalidation.org/files/dist/jquery.validate.min.js"></script>
	<script src="http://jqueryvalidation.org/files/dist/additional-methods.min.js"></script>
	<script type="text/javascript" src="js/dashboard.js"></script>
</body>
</html>