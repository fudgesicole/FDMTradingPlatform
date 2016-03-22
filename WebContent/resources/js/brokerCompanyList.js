var originalCompanyName;

function addCompanyExists(){
	$.ajax({
		url: "/TradingPlatform/companyExists",
		type: 'GET',
		data: {'name' : $('#add-company-name-input').val()},
		success: function(data){
			if(data){
				$('#add-company-name-input').get(0).setCustomValidity("Name already in use.");
				$('#company-add-exists').show();
			}
			else{
				$('#add-company-name-input').get(0).setCustomValidity("");
				$('#company-add-exists').hide();
			}
		},
		error: function(request, status, error){
			console.log('Request: ' + request + ' Status: ' + status + ' Error: ' + error);
		}
	});
}

function editCompanyExists(){
	$.ajax({
		url: "/TradingPlatform/companyExists",
		type: 'GET',
		data: {'name' : $('#edit-company-name-input').val()},
		success: function(data){
			if(data && ($('#edit-company-name-input').val() != originalCompanyName)){
				$('#edit-company-name-input').get(0).setCustomValidity("Name already in use.");
				$('#company-edit-exists').show();
			}
			else{
				$('#edit-company-name-input').get(0).setCustomValidity("");
				$('#company-edit-exists').hide();
			}
		},
		error: function(request, status, error){
			console.log('Request: ' + request + ' Status: ' + status + ' Error: ' + error);
		}
	});
}

$(document).ready(function() {
	$('#add-company-name-input').keyup(addCompanyExists);
	$('#edit-company-name-input').keyup(editCompanyExists);
	
	$(document).on('click', '.company-delete-btn', function(event){
		var row = $(this).closest('tr');
		var id = row.find('.companyId').text();
		var name = row.find('.companyName').text();
		$('#delete-company-id-input').val(id);
		$('#delete-company-name').text(name);
		$('#delete-company-modal').modal('show');
	});
	
	$(document).on('click', '.company-edit-btn', function(event){
		var row = $(this).closest('tr');
		var id = row.find('.companyId').text();
		var name = row.find('.companyName').text();
		originalCompanyName = name;
		$('#edit-company-id-input').val(id);
		$('#edit-company-name-input').val(name)
		$('#edit-company-name').text(name)
		$('#update-company-modal').modal('show');
	});
});