$(document).ready(function(){
	  $('table').DataTable( {
	        "pagingType": "full_numbers",
	        "lengthMenu": [ [5, 10, 25, -1], [5, 10, 25, "All"] ]
	    } );	
}); 