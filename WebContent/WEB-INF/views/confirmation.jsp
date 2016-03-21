<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
		<div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Success</h1>
			             </div>
			             <div class="modal-body">
		                 	<h3>${successMsg}</h3>   
		                 </div>
			             <div class="modal-footer ">
			                    <button id="success-modal-dismiss" data-dismiss="modal" class="btn btn-danger btn-lg" style="width: 100%;">
			                           <span class="glyphicon glyphicon-ok-sign"></span> Dismiss
			                    </button>
			           	 </div>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
    	</div>

		<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Error</h1>
			             </div>
			             <div class="modal-body">
		                 	<h3>${errMsg}</h3>   
		                 </div>
			             <div class="modal-footer ">
			                    <button id="err-modal-dismiss" data-dismiss="modal" class="btn btn-danger btn-lg" style="width: 100%;">
			                           <span class="glyphicon glyphicon-ok-sign"></span> Dismiss
			                    </button>
			           	 </div>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
    	</div>
    	
<c:if test="${not empty successMsg}">
	<script>
		$(document).ready(function(){
			$('#successModal').modal('show');		
		});
	</script>
</c:if>

<c:if test="${not empty errMsg}">
	<script>
		$(document).ready(function(){
			$('#errorModal').modal('show');	
		});
	</script>
</c:if>

<script>
	$(document).ready(function(){
		$('form:not(.no-confirm)').submit(function(){
			if(!confirm("Are you sure?"))
				return false;
		});
	});
</script>