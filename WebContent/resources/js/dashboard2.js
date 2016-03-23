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

function passwordConfirm(){
	var password = $('#change-password-input').val();
	var passwordConfirmation = $('#change-password-confirm-input').val();
	if(password != passwordConfirmation){
		$('#change-password-input').get(0).setCustomValidity('Passwords do not match.');
		$('#password-match-msg').show();
		return false;
	}
	else{
		$('#change-password-input').get(0).setCustomValidity('');
		$('#password-match-msg').hide();
		return true;
	}
}

function checkpw(){
	$.ajax({
		url: "/TradingPlatform/testLogin",
		type: 'GET',
		data: {'userName' : $('#update-password-username-input').val(),
				'passWord' : $('#current-password-input').val()},
		success: function(data){
			if(data){
				if(passwordConfirm()){
					$('#update-password-form').submit();
				}
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

$(document).ready(function() {	
	$('#change-password-input').keyup(passwordConfirm);
	$('#change-password-confirm-input').keyup(passwordConfirm);
	$(document).on('click', '#change-pw-submit-btn', function(event){
		event.preventDefault();
		passwordConfirm();
		checkpw();
	});
	
	$('#current-password-input').keyup(function(event){
		$('#invalid-login').hide();
	});
	
	$('body .dropdown-toggle').dropdown();
	
	
	$('.sidebar-dropdown.sub-menu li, .sidebar-dropdown.dropdown-menu li').click(function(event){
		window.open($(this).find('a').attr('href'),"_self");
	});
	
	$('#sidebar a').click(function(event){
		event.stopPropagation();
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