var originalUserName;

function addUserExists(){
	$.ajax({
		url: "/TradingPlatform/userExists",
		type: 'GET',
		data: {'userName' : $('#register-username-input').val()},
		success: function(data){
			if(data){
				$('#register-username-input').get(0).setCustomValidity("Username already in use.");
				$('#user-exists').show();
			}
			else{
				$('#register-username-input').get(0).setCustomValidity("");
				$('#user-exists').hide();
			}
		},
		error: function(request, status, error){
			console.log('Request: ' + request + ' Status: ' + status + ' Error: ' + error);
		}
	});
}
function editUserExists(){
	$.ajax({
		url: "/TradingPlatform/userExists",
		type: 'GET',
		data: {'userName' : $('#edit-userName-input').val()},
		success: function(data){
			if(data || ($('#edit-userName-input').val() == originalUserName)){
				$('#edit-userName-input').get(0).setCustomValidity("Username already in use.");
				$('#edit-user-exists').show();
			}
			else{
				$('#edit-userName-input').get(0).setCustomValidity("");
				$('#edit-user-exists').hide();
			}
		},
		error: function(request, status, error){
			console.log('Request: ' + request + ' Status: ' + status + ' Error: ' + error);
		}
	});
}

$(document).ready(function() {
	$('#register-username-input').keyup(addUserExists);
	$('#edit-userName-input').keyup(editUserExists);
	$('#register-btn').click(function(event){
		if($('#admin-box').get(0).checked){
			if($('#shareholder-box').get(0).checked || $('#company-box').get(0).checked  || $('#broker-box').get(0).checked){
				$('#admin-box').get(0).setCustomValidity('You may not combine admin with other roles.');
			}
			else{
				$('#admin-box').get(0).setCustomValidity('');
			}
		}
		else if(!($('#shareholder-box').get(0).checked || $('#company-box').get(0).checked  || $('#broker-box').get(0).checked || $('#admin-box').get(0).checked)){
			$('#admin-box').get(0).setCustomValidity('You must select a role.');
		}
		else{
			$('#admin-box').get(0).setCustomValidity('');
		}
	});

	$('#register-form input').bind("enterKey", function(event){
		if($('#admin-box').get(0).checked){
			if($('#shareholder-box').get(0).checked || $('#company-box').get(0).checked  || $('#broker-box').get(0).checked){
				$('#admin-box').get(0).setCustomValidity('You may not combine admin with other roles.');
			}
			else{
				$('#admin-box').get(0).setCustomValidity('');
			}
		}
		else if(!($('#shareholder-box').get(0).checked || $('#company-box').get(0).checked  || $('#broker-box').get(0).checked || $('#admin-box').get(0).checked)){
			$('#admin-box').get(0).setCustomValidity('You must select a role.');
		}
		else{
			$('#admin-box').get(0).setCustomValidity('');
		}
	});

	$('#edit-user-submit-btn').click(function(event){
		if($('#edit-admin-box').get(0).checked){
			if($('#edit-shareholder-box').get(0).checked || $('#edit-company-box').get(0).checked  || $('#edit-broker-box').get(0).checked){
				$('#edit-admin-box').get(0).setCustomValidity('You may not combine admin with other roles.');
			}
			else{
				$('#edit-admin-box').get(0).setCustomValidity('');
			}
		}
		else if(!($('#edit-shareholder-box').get(0).checked || $('#edit-company-box').get(0).checked  || $('#edit-broker-box').get(0).checked || $('#edit-admin-box').get(0).checked)){
			$('#edit-admin-box').get(0).setCustomValidity('You must select a role.');
		}
		else{
			$('#edit-admin-box').get(0).setCustomValidity('');
		}
	});
	
	$('#edit-form input').bind("enterKey", function(event){
		if($('#edit-admin-box').get(0).checked){
			if($('#edit-shareholder-box').get(0).checked || $('#edit-company-box').get(0).checked  || $('#edit-broker-box').get(0).checked){
				$('#edit-admin-box').get(0).setCustomValidity('You may not combine admin with other roles.');
			}
			else{
				$('#edit-admin-box').get(0).setCustomValidity('');
			}
		}
		else if(!($('#edit-shareholder-box').get(0).checked || $('#edit-company-box').get(0).checked  || $('#edit-broker-box').get(0).checked || $('#edit-admin-box').get(0).checked)){
			$('#edit-admin-box').get(0).setCustomValidity('You must select a role.');
		}
		else{
			$('#edit-admin-box').get(0).setCustomValidity('');
		}
	});
	
	$(document).on('click', '.user-delete-btn', function(event){
		var id = $(this).closest('tr').find('.userId').text();
		$('#delete-user-id').val(id);
		$('#delete-user-modal').modal('show');
	});
	
	$(document).on('click', '.user-edit-btn', function(event){
		var row = $(this).closest('tr');
		var userName = row.find('.userName').text();
		originalUserName = userName;
		var firstName = row.find('.firstName').text();
		var lastName = row.find('.lastName').text();
		var id = row.find('.userId').text();
		var roles = row.find('.roles').text();
		$('#edit-id-input').val(id)
		$('#edit-userName-input').val(userName)
		$('#edit-firstName-input').val(firstName)
		$('#edit-lastName-input').val(lastName)
		$('#edit-user-modal').modal('show');
		if(roles.indexOf("admin") != -1){
			$('#edit-admin-box').prop('checked', true);
		}
		else
			$('#edit-admin-box').prop('checked', false);
		if(roles.indexOf("shareholder") != -1){
			$('#edit-shareholder-box').prop('checked', true);
		}
		else
			$('#edit-shareholder-box').prop('checked', false);
		if(roles.indexOf("broker") != -1){
			$('#edit-broker-box').prop('checked', true);
		}
		else
			$('#edit-broker-box').prop('checked', false);
		if(roles.indexOf("company") != -1){
			$('#edit-company-box').prop('checked', true);
		}
		else
			$('#edit-company-box').prop('checked', false);
	});
});