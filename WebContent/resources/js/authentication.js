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

function testLogin(){
	$.ajax({
		url: "/TradingPlatform/testLogin",
		type: 'GET',
		data: {'userName' : $('#login-username-input').val(),
				'passWord' : $('#login-password-input').val()},
		success: function(data){
			if(data){
				$('#login-form').submit();
			}
			else{
				$('#invalid-login').show();
			}
		},
		error: function(request, status, error){
			console.log('Request: ' + request + ' Status: ' + status + ' Error: ' + error);
		}
	});
}

$(document).ready(function(){
	$('#register-username-input').keyup(addUserExists);
	$('#login-btn').click(function(event){
		event.preventDefault();
		testLogin();
	});
	$('#login-form input').bind("enterKey",function(event){
		event.preventDefault();
		testLogin();
	});
	$('#login-username-input, #login-password-input').keyup(function(){
		$('#invalid-login').hide();
	});
	$('#login-modal-link').click(function(event){
		$('#invalid-login').hide();
	});
	
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
		if($('#admin-box').attr('checked')){
			if($('#shareholder-box').attr('checked') || $('#company-box').attr('checked')  || $('#broker-box').attr('checked') ){
				$('#admin-box').setCustomValidity('You may not combine admin with other roles.');
			}
			else{
				$('#admin-box').setCustomValidity('');
			}
		}
	});
});