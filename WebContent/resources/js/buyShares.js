$(document).ready(function() {
	$(document).on('click', '.buy-shares-btn', function(event) {
		var companyId = $(this).closest('tr').find('.companyId').text();
		var companyName = $(this).closest('tr').find('.name').text();
		$('#buy-company-id').val(companyId);
		$('#buy-company-name').text(companyName);
		$('#add-request-modal').modal('show');
	});
});