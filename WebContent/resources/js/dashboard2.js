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
	$('body .dropdown-toggle').dropdown();
	
	$('.sub-menu li, .dropdown-menu li').click(function(event){
		console.log('here');
		window.open($(this).find('a').attr('href'),"_self");
	});
	$('a').click(function(event){
		event.stopPropagation();
	});
	if ($(window).width() > 992) {
		$('#sidebar').addClass('expanded-sidebar');
	} else {
		$('.sub-menu').addClass('dropdown-menu');
		$('.sub-menu').removeClass('sub-menu');
	}
	$('.sidebar-toggle').click(function() {
		toggleSidebar();
	});
	$(window).resize(function resize() {
		if ($(window).width() < 992) {
			$('.sub-menu').addClass('dropdown-menu');
			$('.sub-menu').removeClass('sub-menu');
		} else {
			$('.dropdown-menu').addClass('sub-menu');
			$('.dropdown-menu').removeClass('dropdown-menu');
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