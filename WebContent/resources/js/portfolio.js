var ownedShares;
$(document).ready(function(){
	$(document).on('click', '.sell-shares-btn', function(event){
		var row = $(this).closest('tr');
		var companyName = row.find('.companyName').text();
		var companyId = row.find('.companyId').text();
		ownedShares = parseInt(row.find('.ownedShares').text());
		$('#sell-company-name').text(companyName);
		$('#sell-company-id').val(companyId);
		$('#add-request-modal').modal('show');
	});
	
	$(document).on('click', '#add-request-submit-btn', function(event){
		var requestedShareCount = parseInt($('#add-request-shares-input').val());
		var minShareCount = parseInt($('#add-request-minshares-input').val());
		
		if(requestedShareCount > ownedShares)
			$('#add-request-shares-input').get(0).setCustomValidity('You do not own enough shares! You own: ' + ownedShares);
		else
			$('#add-request-shares-input').get(0).setCustomValidity('');

		if(requestedShareCount < minShareCount)
			$('#add-request-minshares-input').get(0).setCustomValidity('Must not be greater than shares requested');
		else
			$('#add-request-minshares-input').get(0).setCustomValidity('');
	});
});