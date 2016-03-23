var selectedUserRow;
var selectedCompanyRow;
function submitAjaxRequest(url, data, dataFunction){
	$.ajax({
		mimeType : 'text/html; charset=utf-8',
		url : url,
		type : 'GET',
		data : data,
		success : function(data){
			dataFunction(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			dataFunction('');
		},
		dataType : "html",
		async : true
	});
}

function deleteUser(selectedUser) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when
		// run from local file
		url : '/TradingPlatform/delete-user',
		type : 'GET',
		data : {
			userId : selectedUser.find('.userId').html()
		},
		success : function(data) {
			console.log(data);
			if (data == "true") {
				selectedUser.hide();
				$('#overlay').hide();
				$('.info-box').hide();
			} else {
				$('.deleteUserErrorMsg').show();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		dataType : "html",
		async : false
	});
}

function loadCompanyOptions(){
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when
		// run from local file
		url : '/TradingPlatform/companyOptions',
		type : 'GET',
		success : function(data) {
			$('#add-request-stockid-input').html(data);
			$('#add-request-stockid-input').chosen({width: "100%"});

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		dataType : "html",
		async : true
	});
}

function loadElement(url, selector) {
	$.ajax({
		mimeType : 'text/html; charset=utf-8', // ! Need set mimeType only when
		// run from local file
		url : url,
		type : 'GET',
		success : function(data) {
			$(selector).html(data);
			$('.company-delete-btn').click(function(event){
				selectedCompanyRow = $(this).closest('tr');
				$('#delete-company-name').text(selectedCompanyRow.find('td:nth-child(1)').text());
			});
			$('.company-edit-btn').click(function (e){
				selectedCompanyRow = $(this).closest('tr');
				$('#edit-company-name-input').val(selectedCompanyRow.find('td:nth-child(1)').text());
			});
			$('.user-delete-btn').click(function(e) {
				selectedUserRow = $(this).closest('tr');
				$('#delete-user-username').text(selectedUserRow.find('td:nth-child(1)').text());
			});
			$('.user-edit-btn').click(function(e) {
				var userRow = $(this).closest('tr');
				var userName = userRow.find('td:nth-child(1)').text();
				var passWord = userRow.find('td:nth-child(2)').text();
				var firstName= userRow.find('td:nth-child(3)').text();
				var lastName= userRow.find('td:nth-child(4)').text();
				var roles = userRow.find('td:nth-child(5)');
				var id = userRow.find('td:nth-child(8)').text();
				
				$('#edit-user-username-input').val(userName);
				$('#edit-user-password-input').val(passWord);
				$('#edit-user-firstname-input').val(firstName);
				$('#edit-user-lastname-input').val(lastName);
				$('#edit-user-id-input').val(id);
				$('#edit-user-admin-input').prop('checked', false);
				$('#edit-user-shareholder-input').prop('checked', false);
				$('#edit-user-broker-input').prop('checked', false);
				$('#edit-user-company-input').prop('checked', false);
				roles.find('p').each(function(){
					if($(this).text() == "admin"){
						$('#edit-user-admin-input').prop('checked', true);
					}
					else if($(this).text()== "shareholder"){
						$('#edit-user-shareholder-input').prop('checked', true);
					}
					else if($(this).text() == "company"){
						$('#edit-user-company-input').prop('checked', true);
					}
					else if($(this).text() == "broker"){
						$('#edit-user-broker-input').prop('checked', true);
					}
				});
			});
			$('.preloader').hide();
			$('#overlay').hide();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('.preloader').hide();
			$('#overlay').hide();
			$(selector).html("<h2>An error occured while loading this page. Please try again.</h2>");
			alert(errorThrown);
		},
		dataType : "html",
		async : true
	});
}

function toggleSidebar() {
	$('#sidebar').toggleClass('expanded-sidebar');
	if($('#sidebar').hasClass('expanded-sidebar')){
		$('#content').css('width', '83.33333333333%');
		$('#content').css('left', '16.66666666666%');
		$('.preloader').css('left', '58.333333333333333%');
	}
	else{
		$('#content').css('width', '100%');
		$('#content').css('left', '0%');
		$('.preloader').css('left', '50%');
	}
}

$(document).ready(function() {
	loadCompanyOptions();
/*	var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect:true},
		      '.chosen-select-no-single' : {disable_search_threshold:10},
		      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
		      '.chosen-select-width'     : {width:"95%"}
		    }
	for (var selector in config) {
		$(selector).chosen(config[selector]);
	}*/
	
	$('.sidebar-dropdown.sub-menu li, .sidebar-dropdown.dropdown-menu li').click(function(event) {
		if($(this).find('.ajax-link').length == 0)
			return;
		event.preventDefault();
		$('.preloader').show();
		$('#overlay').show();
		var link = $(this).find('.ajax-link');
		var listElement = $(this);
		listElement.removeClass('inactive');
		
		$('.sidebar-dropdown.sub-menu li, .sidebar-dropdown.dropdown-menu li').not(listElement).addClass('inactive');
		var url = link.attr('href');
		loadElement(url, '#ajax-content');
	});
	if ($(window).width() > 992) {
		$('#sidebar').addClass('expanded-sidebar');
	} else {
		$('.sidebar-dropdown.sub-menu').addClass('dropdown-menu');
		$('.sidebar-dropdown.sub-menu').removeClass('sub-menu');
	}
	$('.sidebar-toggle').click(function() {
		toggleSidebar();
	});
	$(window).resize(function resize() {
		if ($(window).width() < 992) {
			$('.sidebar-dropdown.sub-menu').addClass('dropdown-menu');
			$('.sidebar-dropdown.sub-menu').removeClass('sub-menu');
		} else {
			$('.sidebar-dropdown.dropdown-menu').addClass('sub-menu');
			$('.sidebar-dropdown.dropdown-menu').removeClass('dropdown-menu');
		}
	});

	$('#request-add-sidebar-btn').click(function(event) {
		$('#add-request-type-input').val("");
		$('#add-request-stockid-input').val("");
		$('#add-request-timeinforce-input').val("");
		$('#add-request-shares-input').val("");
		$('#add-request-minshares-input').val("");
		$('#add-request-limitprice-input').val("");
		$('#add-request-stopprice-input').val("");
/*		loadElement('/TradingPlatform/companyOptions', '#add-request-stockid-input');
		var config = {
			      '.chosen-select'           : {},
			      '.chosen-select-deselect'  : {allow_single_deselect:true},
			      '.chosen-select-no-single' : {disable_search_threshold:10},
			      '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
			      '.chosen-select-width'     : {width:"95%"}
			    }
		for (var selector in config) {
			$(selector).chosen(config[selector]);
		}*/
	});
	
	// add user stuff
	$('#user-add-sidebar-btn').click(function(event){
		$('#add-user-username-input').val("");
		$('#add-user-password-input').val("");
		$('#add-user-firstname-input').val("");
		$('#add-user-lastname-input').val("");
		$('#add-user-admin-input').prop('checked', false);
		$('#add-user-shareholder-input').prop('checked', false);
		$('#add-user-broker-input').prop('checked', false);
	});
	
	$('#add-user-submit-btn').click(function(event){
		$('.spinner').show();
		selectedRoles = [];
		if($('#add-user-admin-input').prop('checked'))
			selectedRoles.push("1");
		if($('#add-user-shareholder-input').prop('checked'))
			selectedRoles.push("2");
		if($('#add-user-company-input').prop('checked'))
			selectedRoles.push("3");
		if($('#add-user-broker-input').prop('checked'))
			selectedRoles.push("4");
		
		var data = {
				userName : $('#add-user-username-input').val(),
				passWord : $('#add-user-password-input').val(),
				firstName : $('#add-user-firstname-input').val(),
				lastName : $('#add-user-lastname-input').val(),
				roles : selectedRoles
		};
		var requestResult = submitAjaxRequest('/TradingPlatform/addUser', data);
		if(requestResult == ""){
			$('#operation-alert').find('.message').text('An error occured while adding this user. Please try again later.');
			$('#operation-alert').show();
		}
		else if(requestResult != "success"){
			$('#operation-alert').find('.message').text(requestResult);
			$('#operation-alert').show();
		}
		$('.preloader').show();
		$('#overlay').show();
		loadElement('/TradingPlatform/users', '#ajax-content');
		$('.spinner').hide();
	}); 
	
	// edit user stuff
	$('#edit-user-submit-btn').click(function(event){
		$('.spinner').show();
		selectedRoles = [];
		if($('#edit-user-admin-input').prop('checked'))
			selectedRoles.push("1");
		if($('#edit-user-shareholder-input').prop('checked'))
			selectedRoles.push("2");
		if($('#edit-user-broker-input').prop('checked'))
			selectedRoles.push("4");
		
		var data = {
				userName : $('#edit-user-username-input').val(),
				passWord : $('#edit-user-password-input').val(),
				firstName : $('#edit-user-firstname-input').val(),
				lastName : $('#edit-user-lastname-input').val(),
				id : $('#edit-user-id-input').val(),
				roles : selectedRoles
		};
		var requestResult = submitAjaxRequest('/TradingPlatform/updateUser', data);
		if(requestResult == ""){
			$('#operation-alert').find('.message').text('An error occured while updating this user. Please try again later.');
			$('#operation-alert').show();
		}
		else if(requestResult != "success"){
			$('#operation-alert').find('.message').text(requestResult);
			$('#operation-alert').show();
		}
		$('.preloader').show();
		$('#overlay').show();
		loadElement('/TradingPlatform/users', '#ajax-content');
		$('.spinner').hide();
	});

	$('#add-request-submit-btn').click(function(event){
		if($('#add-request-shares-input').val().indexOf('e') > -1 || $('#add-request-shares-input').val().indexOf('.') > -1)
			$('#add-request-shares-input').get(0).setCustomValidity('Must be a whole number.');
		else
			$('#add-request-shares-input').get(0).setCustomValidity('');
		
		if(parseInt($('#add-request-minshares-input').val()) > parseInt($('#add-request-shares-input').val()))
			$('#add-request-minshares-input').get(0).setCustomValidity('Cannot be greater than shares requested');
		else if($('#add-request-minshares-input').val().indexOf('e') > -1 || $('#add-request-minshares-input').val().indexOf('.') > -1)
			$('#add-request-minshares-input').get(0).setCustomValidity('Must be a whole number.');
		else
			$('#add-request-minshares-input').get(0).setCustomValidity('');
		
		if($('#add-request-limitprice-input').val().indexOf('e') > -1)
			$('#add-request-limitprice-input').get(0).setCustomValidity('Must be a number.');
		else
			$('#add-request-limitprice-input').get(0).setCustomValidity('');
		
		if($('#add-request-stopprice-input').val().indexOf('e') > -1)
			$('#add-request-stopprice-input').get(0).setCustomValidity('Must be a number');
		else
			$('#add-request-stopprice-input').get(0).setCustomValidity('');
	});
	
	$("#add-request-form").submit(function (event){
		event.preventDefault();
		$('.spinner').show();
		if($('#add-request-limitprice-input').val() == "")
			requestLimitPrice = null;
		else
			requestLimitPrice = $('#add-request-limitprice-input').val();
		if($('#add-request-stopprice-input').val() == "")
			requestStopPrice = null;
		else
			requestStopPrice = $('#add-request-stopprice-input').val();
		
		var data = {
				requestType : $('#add-request-type-input').val(),
				stockId : $('#add-request-stockid-input').val(),
				timeInForce : $('#add-request-timeinforce-input').val(),
				shares : $('#add-request-shares-input').val(),
				minShares : $('#add-request-minshares-input').val(),
				limitPrice : requestLimitPrice,
				stopPrice : requestStopPrice
		};
		submitAjaxRequest('/TradingPlatform/request', data, function(requestResult){
			if(requestResult == ""){
				alert('An error occured while adding this request. Please try again later.');
			}
			else if(requestResult != "success"){
				alert(requestResult);
			}else{
				$('#add-request-modal').modal('toggle');
				$('.preloader').show();
				$('#overlay').show();
				loadElement('/TradingPlatform/portfolio.jsp', '#ajax-content');
			}
			$('.spinner').hide();
		});
	});
	
	// delete user stuff
	$('#delete-user-submit-btn').click(function(event){
		$('.spinner').show();
		var data = {
				userId : selectedUserRow.find('.userId').text()
		};
		var requestResult = submitAjaxRequest('/TradingPlatform/deleteUser', data);
		$('.spinner').hide();
		if(requestResult == ""){
			$('#operation-alert').find('.message').text('An error occured while deleting this user. Please try again later.');
			$('#operation-alert').show();
		}
		else if(requestResult != "success"){
			$('#operation-alert').find('.message').text(requestResult);
			$('#operation-alert').show();
		}
		$('.preloader').show();
		$('#overlay').show();
		loadElement('/TradingPlatform/users', '#ajax-content');
		$('.spinner').hide();
	});
	
	// edit company stuff
	$('#edit-company-submit-btn').click(function(event){
		$('.spinner').show();
		var data = {
				name : $('#edit-company-name-input').val(),
				id : selectedCompanyRow.find('.companyId').text()
		};
		var requestResult = submitAjaxRequest('/TradingPlatform/editCompany', data);
		if(requestResult == ""){
			$('#operation-alert').find('.message').text('An error occured while updating this company. Please try again later.');
			$('#operation-alert').show();
		}
		else if(requestResult != "success"){
			$('#operation-alert').find('.message').text(requestResult);
			$('#operation-alert').show();
		}
		$('.preloader').show();
		$('#overlay').show();
		loadElement('/TradingPlatform/companies', '#ajax-content');
		$('.spinner').hide();
	}); 
	
	// delete company stuff
	$('#delete-company-submit-btn').click(function(event){
		$('.spinner').show();
		var data = {
				id : selectedCompanyRow.find('.companyId').text()
		};
		var requestResult = submitAjaxRequest('/TradingPlatform/deleteCompany', data) 
		$('.spinner').hide();
		if(requestResult == ""){
			$('#operation-alert').find('.message').text('An error occured while deleting this company. Please try again later.');
			$('#operation-alert').show();
		}
		else if(requestResult != "success"){
			$('#operation-alert').find('.message').text(requestResult);
			$('#operation-alert').show();
		}
		$('.preloader').show();
		$('#overlay').show();
		loadElement('/TradingPlatform/companies', '#ajax-content');
		$('.spinner').hide();
	});
	
	// add company stuff
	$('#company-add-sidebar-btn').click(function(event){
		$('#add-company-name-input').val("");
		$('#add-company-submit-btn').addClass('invalid-btn');
		$('.spinner').hide();
		$('#comp-exists-msg').hide();
		$('#add-company-start-price-input').val("");
		$('#add-company-authorized-shares-input').val("");
	});
	
	$('#add-company-name-input').keyup(function(event){
		submitAjaxRequest('/TradingPlatform/companyExists', {name : $('#add-company-name-input').val()}, function(result){
			if(result == 'true'){
				$('#comp-exists-msg').show();
				$('#add-company-name-input').get(0).setCustomValidity('A company with this name already exists. Please try a different name.');
			}
			else{
				$('#comp-exists-msg').hide();
				$('#add-company-name-input').get(0).setCustomValidity('');
			}
		});
	});
	
	$('#add-company-submit-btn').click(function(event){
		if($('#add-company-authorized-shares-input').val().indexOf('.') != -1 || $('#add-company-authorized-shares-input').val().indexOf('e') != -1)
			$('#add-company-authorized-shares-input').get(0).setCustomValidity('Must be a whole number.');
		else
			$('#add-company-authorized-shares-input').get(0).setCustomValidity('');
		if($('#add-company-start-price-input').val().indexOf('e') != -1)
			$('#add-company-start-price-input').get(0).setCustomValidity('Please enter a number.');
		else
			$('#add-company-start-price-input').get(0).setCustomValidity('');
/*		submitAjaxRequest('/TradingPlatform/companyExists', {name : $('#add-company-name-input').val()}, function(result){
			if(result == 'true')
				$('#add-company-name-input').setCustomValidity('A company with this username already exists. Please try a different name.');
			else
				$('#add-company-name-input').setCustomValidity('');
		});*/
	});
	
	$('#add-company-form').submit(function(event){
		event.preventDefault();
		$('.spinner').show();
		var data = {
			name : $('#add-company-name-input').val(),
			startingPrice : $('#add-company-start-price-input').val(),
			authorizedShares : $('#add-company-authorized-shares-input').val()
		};
		submitAjaxRequest('/TradingPlatform/createCompany', data, function(requestResult){
			if(requestResult == ""){
				alert('An error occured while adding this company. Please try again later.');
			}
			else if(requestResult != "success"){
				alert(requestResult);
			}else{
				$('#add-company-modal').modal('toggle');
				$('.preloader').show();
				$('#overlay').show();
				loadElement('/TradingPlatform/companies', '#ajax-content');
			}
			$('.spinner').hide();
		});
	});
	
	if($('#sidebar').hasClass('expanded-sidebar')){
		$('#content').css('width', '83.33333333333%');
		$('#content').css('left', '16.66666666666%');
		$('.preloader').css('left', '58.333333333333333%');
	}
	else{
		$('#content').css('width', '100%');
		$('#content').css('left', '0%');
		$('.preloader').css('left', '50%');
	}});